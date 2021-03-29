package com.example.moneyjars;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moneyjars.common.DateUtil;
import com.example.moneyjars.helper.DatabaseHelper;
import com.example.moneyjars.helper.ReportDatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReportActivity extends HeaderActivity {
    private ReportDatabaseHelper reportDatabaseHelper;
    private TabLayout tabLayout;
    private Button btnMinus;
    private Button btnPlus;
    private TextView txtDate;
    private LineChart chart;
    private PieChart pieChart;
    private PieData pieData;
    private List<PieEntry> pieEntryList = new ArrayList<>();
    private String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reportDatabaseHelper = new ReportDatabaseHelper(this);
        userEmail = preferences.getString(USER_EMAIL, "test1@hotmail.com");
        tabLayout = findViewById(R.id.tabLayout);
        btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangeData(1);
            }
        });
        btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangeData(-1);
            }
        });
        txtDate = findViewById(R.id.txtDate);
        txtDate.setText(DateUtil.getCurrentDate());
        int pos = 0;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                changeView(pos) ;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        // draw default PieChart
        changeView(pos) ;

    }

    private void changeView(int index) {
        Cursor c = null;
        String resultDate;
        switch (index) {
            case 0:
                resultDate = DateUtil.getCurrentDate();
                txtDate.setText(resultDate);
                c = reportDatabaseHelper.selectDataOfDay(userEmail, resultDate);
                break;
            case 1:
                resultDate = DateUtil.getCurrentMonth();
                txtDate.setText(DateUtil.getCurrentMonth());
                c = reportDatabaseHelper.selectDataOfMonth(userEmail, resultDate);
                break;
            case 2:
                String startMonth = DateUtil.getAddMonth(DateUtil.getCurrentMonth(),-5);
                String endMonth = DateUtil.getCurrentMonth();
                txtDate.setText(startMonth+" ~ " + endMonth);
                c = reportDatabaseHelper.selectDataOfSixMonth(userEmail, startMonth, endMonth);

                break;
        }
        drawPieChart(c);
    }

    private void onClickChangeData(int addCount){
        int position = tabLayout.getSelectedTabPosition();
        String date = txtDate.getText().toString();
        String resultDate;
        Cursor c = null;
        switch (position) {
            case 0:
                resultDate = DateUtil.getAddDate(date, addCount);
                txtDate.setText(resultDate);
                c = reportDatabaseHelper.selectDataOfDay(userEmail, resultDate);
                break;
            case 1:
                resultDate = DateUtil.getAddMonth(date, addCount);
                txtDate.setText(resultDate);
                c = reportDatabaseHelper.selectDataOfMonth(userEmail, resultDate);
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
                txtDate.setText(startMonth + " ~ " + endMonth);
                c = reportDatabaseHelper.selectDataOfSixMonth(userEmail, startMonth, endMonth);
                break;
        }
        drawPieChart(c);
        c.close();
    }


    private void drawPieChart(Cursor c) {
        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        pieEntryList.clear();
        if(c.getCount() > 0) {
            while(c.moveToNext()) {
                pieEntryList.add(new PieEntry(c.getInt(1), c.getString(0)));
            }
            PieDataSet pieDataSet  = new PieDataSet(pieEntryList, "");
            pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            pieData = new PieData(pieDataSet);
            pieData.setValueTextSize(15);
            pieData.setValueTextColor(Color.BLACK);

            pieChart.setData(pieData);
            pieChart.setDrawEntryLabels(false);
            pieChart.setNoDataTextColor(Color.RED);
            pieChart.getDescription().setTextSize(15);
            pieChart.getDescription().setTextColor(Color.BLACK);

        } else {
            pieChart.setData(null);
        }
        pieChart.invalidate();
    }
}