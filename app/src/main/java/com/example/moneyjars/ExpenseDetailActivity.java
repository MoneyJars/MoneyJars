package com.example.moneyjars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyjars.helper.RegisterExpenseDataBaseHelper;

public class ExpenseDetailActivity extends HeaderActivity {

    RegisterExpenseDataBaseHelper registerExpenseDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        registerExpenseDataBaseHelper = new RegisterExpenseDataBaseHelper(this);

        TextView expenseDetailType = findViewById(R.id.txtExpenseDetailType);
        TextView expenseDetailIssueDate = findViewById(R.id.txtExpenseDetailIssueDate);
        TextView expenseDetailCategory = findViewById(R.id.txtExpenseDeatilCategory);
        TextView expenseDetailAmount = findViewById(R.id.txtExpenseDetailAmout);
        TextView expenseDetailNote = findViewById(R.id.txtExpenseDeatilNote);
        Button btnDelete = findViewById(R.id.btnDeleteDetail);

        Intent intent = getIntent();
        String financialId = intent.getExtras().getString("FinancialId");
        String categoryId = intent.getExtras().getString("CategoryId");
        Cursor c = registerExpenseDataBaseHelper.getExpenseDetail(financialId, categoryId);

        if(c.getCount() > 0) {
            while(c.moveToNext()) {

                if(c.getString(0).equals("01")) {
                    expenseDetailType.setText("Day");
                }else if(c.getString(0).equals("02")) {
                    expenseDetailType.setText("Monthly");
                }
                expenseDetailIssueDate.setText(c.getString(1));
                expenseDetailCategory.setText(c.getString(2));
                expenseDetailAmount.setText("$" + c.getString(3));
                expenseDetailNote.setText(c.getString(4));

            }
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            boolean isDeleted;
            @Override
            public void onClick(View v) {
                isDeleted = registerExpenseDataBaseHelper.deleteRec(financialId);

                if(isDeleted) {
                    Toast.makeText(ExpenseDetailActivity.this, "Data deleted",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ExpenseDetailActivity.this, ExpenseTrackerListActivity.class));
                }else {
                    Toast.makeText(ExpenseDetailActivity.this, "Data not deleted",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}