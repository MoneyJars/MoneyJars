package com.example.moneyjars;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.moneyjars.common.DateUtil;
import com.example.moneyjars.helper.RegisterExpenseDataBaseHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpenseTrackerListActivity extends HeaderActivity {

    RegisterExpenseDataBaseHelper registerExpenseDataBaseHelper;
    private String userEmail;
    private TextView monthlyCost;
    private TextView dailyLimit;
    private TextView remainedDailyLimit;
    private TextView totalDailyExpense;
    private TextView accountAmount;
    private ListView listView;
    private TextView txtDateSelect;
    private Button addExpense;
    private TabLayout tabLayout;
    private Button btnNextDate;
    private Button btnPrevDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_tracker_list);

        registerExpenseDataBaseHelper = new RegisterExpenseDataBaseHelper(this);
        userEmail = preferences.getString(USER_EMAIL, "test1@hotmail.com");
        monthlyCost = findViewById(R.id.txtMontlyCost);
        dailyLimit = findViewById(R.id.txtDailyLimit);
        remainedDailyLimit = findViewById(R.id.txtRmaindDailyLimit);
        totalDailyExpense = findViewById(R.id.txtTotalDailyExpense);
        accountAmount = findViewById(R.id.txtMyAccountAmount);
        txtDateSelect = findViewById(R.id.txtDateSelect);
        addExpense = findViewById(R.id.btnAddExpense);
        tabLayout = findViewById(R.id.tabLayout);
        btnNextDate = findViewById(R.id.btnNextDate);
        btnPrevDate = findViewById(R.id.btnPrevDate);

        Cursor monthlyFixed = registerExpenseDataBaseHelper.getMonthlyFixedCost();
        if(monthlyFixed.getCount() > 0) {
            System.out.println(monthlyFixed.getString(0));
//            monthlyCost.setText("$"+monthlyFixed.getString(0));
        }else {
            monthlyCost.setText("$0");
        }


        btnNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangeData(1);
            }
        });
        btnPrevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangeData(-1);
            }
        });

        String resultDate = DateUtil.getCurrentDate();
        txtDateSelect.setText(resultDate);
        Cursor c = registerExpenseDataBaseHelper.selectDataOfDay(userEmail, resultDate);
        getDataFromDB(c);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.moneyjars.ExpenseTrackerListActivity.this, com.example.moneyjars.RegisterExpensesActivity.class));
            }
        });


    }

    private void changeView(int index) {

        Cursor c = null;
        String resultDate;

        switch (index) {

            case 0:
                resultDate = DateUtil.getCurrentDate();
                txtDateSelect.setText(resultDate);
                c = registerExpenseDataBaseHelper.selectDataOfDay(userEmail, resultDate);
                getDataFromDB(c);
                break;
            case 1:
                resultDate = DateUtil.getCurrentMonth();
                txtDateSelect.setText(resultDate);
                c = registerExpenseDataBaseHelper.selectDataOfMonth(userEmail, resultDate);
                getDataFromDB(c);
                break;
            case 2:
                String startMonth = DateUtil.getAddMonth(DateUtil.getCurrentMonth(), -5);
                String endMonth = DateUtil.getCurrentMonth();
                c = registerExpenseDataBaseHelper.selectDataOfSixMonth(userEmail, startMonth, endMonth);
                txtDateSelect.setText(startMonth + " ~ " + endMonth);
                getDataFromDB(c);
                break;
        }

    }

    private void onClickChangeData(int addCount) {

        int position = tabLayout.getSelectedTabPosition();
        String date = txtDateSelect.getText().toString();
        String resultDate;
        Cursor c = null;

        switch(position) {

            case 0:
                resultDate = DateUtil.getAddDate(date, addCount);
                txtDateSelect.setText(resultDate);
                c = registerExpenseDataBaseHelper.selectDataOfDay(userEmail, resultDate);
                getDataFromDB(c);
                break;
            case 1:
                resultDate = DateUtil.getAddMonth(date, addCount);
                txtDateSelect.setText(resultDate);
                c = registerExpenseDataBaseHelper.selectDataOfMonth(userEmail, resultDate);
                getDataFromDB(c);
                break;
            case 2:
                String [] arryDate = date.split(" ~ ");
                String startMonth = DateUtil.getAddMonth(DateUtil.getCurrentMonth(),-5);
                String endMonth = DateUtil.getCurrentMonth();

                if (addCount > 0) {
                    startMonth = DateUtil.getAddMonth(arryDate[1], -4);
                    endMonth = DateUtil.getAddMonth(arryDate[1], 1);
                } else {
                    startMonth = DateUtil.getAddMonth(arryDate[1], -6);
                    endMonth = DateUtil.getAddMonth(arryDate[1], -1);
                }
                txtDateSelect.setText(startMonth + " ~ " + endMonth);
                c = registerExpenseDataBaseHelper.selectDataOfSixMonth(userEmail, startMonth, endMonth);
                getDataFromDB(c);
                break;
        }


        c.close();

    }

    private void getDataFromDB(Cursor c) {

        List<HashMap<String, String>> list = new ArrayList<>();

        if(c.getCount() > 0) {
            while(c.moveToNext()) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("CategoryId", c.getString(0));
                hashMap.put("Category Name", c.getString(1));
                hashMap.put("Amount", c.getString(2));
                hashMap.put("FinancialId", c.getString(3));
                list.add(hashMap);

            }

        }

        c.close();
        String[] from = {"CategoryId", "Category Name",  "Amount", "FinancialId"};
        int[] to = {R.id.txtCategoryId, R.id.txtCategoryName, R.id.txtAmount, R.id.txtFinancialId};
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), list, R.layout.list_layout, from, to);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExpenseTrackerListActivity.this, ExpenseDetailActivity.class);
                String financialId = ((HashMap)list.get(position)).get("FinancialId").toString();
                String categoryId = ((HashMap)list.get(position)).get("CategoryId").toString();
                intent.putExtra("FinancialId", financialId);
                intent.putExtra("CategoryId", categoryId);
                startActivity(intent);

            }
        });

    }

}