package com.example.moneyjars;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyjars.helper.RegisterExpenseDataBaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RegisterExpensesActivity extends HeaderActivity {

    RegisterExpenseDataBaseHelper registerExpenseDataBaseHelper;
    private String selectedCategoryId;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_expenses);


        registerExpenseDataBaseHelper = new RegisterExpenseDataBaseHelper(this);
        String userEmail = preferences.getString(USER_EMAIL, "test@hotmail.com");
        Button btnSaveRegisterExpense = findViewById(R.id.registerExpenseSave);
        Spinner spinner = findViewById(R.id.spExpenseCategory);
        EditText amount = findViewById(R.id.registerExpenseAmount);
        EditText note = findViewById(R.id.registerExpenseNote);
        Button exit = findViewById(R.id.btnExitddd);
        RadioButton rbtnDay = findViewById(R.id.rBtnDayExpense);
        RadioButton rbtnMonth = findViewById(R.id.rBtnMonExpense);
        TextView selectDate = findViewById(R.id.registerExDate);
        TextView hiddenText = findViewById(R.id.txtHidden);


        HashMap<String, String> spinnerMap = new HashMap<String, String>();
        List spinnerList = new ArrayList();
        Cursor c = registerExpenseDataBaseHelper.getCategories();
        while(c.moveToNext()) {
            spinnerMap.put(c.getString(1),c.getString(0));
            String category = c.getString(1);
            spinnerList.add(category);
        }

         ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList);
         arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(arrayAdapter);
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedCategoryId = spinnerMap.get(spinner.getSelectedItem());
                 hiddenText.setText(selectedCategoryId);
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.moneyjars.RegisterExpensesActivity.this, ExpenseTrackerListActivity.class));
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        com.example.moneyjars.RegisterExpensesActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String newMonth;
                String newDayOfMonth;
                month = month + 1;
                if(month < 10) {
                    newMonth = "0" + String.valueOf(month);
                }else {
                    newMonth = String.valueOf(month);
                }
                if(dayOfMonth < 10) {
                    newDayOfMonth = "0" + String.valueOf(dayOfMonth);
                }else {
                    newDayOfMonth = String.valueOf(dayOfMonth);
                }
                String date = year + "/" + newMonth + "/" + newDayOfMonth;
                selectDate.setText(date);
            }
        };

        btnSaveRegisterExpense.setOnClickListener(new View.OnClickListener() {
            boolean isInsertedFinancial;
            boolean isInsertedExpense;
            String selectedType;

            @Override
            public void onClick(View v) {


                if(rbtnDay.isChecked()) {
                    selectedType = "01";
                    isInsertedFinancial = registerExpenseDataBaseHelper.registerExpenseToFinancial(selectedType,
                            selectDate.getText().toString(), Integer.parseInt(amount.getText().toString()), hiddenText.getText().toString(), userEmail);
                    isInsertedExpense = registerExpenseDataBaseHelper.registerExpenseToExpense(
                            note.getText().toString());


                }else if(rbtnMonth.isChecked()) {
                    selectedType = "02";
                    isInsertedFinancial = registerExpenseDataBaseHelper.registerExpenseToFinancial(selectedType,
                            selectDate.getText().toString(), Integer.parseInt(amount.getText().toString()), hiddenText.getText().toString(), userEmail);
                    isInsertedExpense = registerExpenseDataBaseHelper.registerExpenseToExpense(
                            note.getText().toString());
                }




                if(isInsertedFinancial && isInsertedExpense) {
                    Toast.makeText(com.example.moneyjars.RegisterExpensesActivity.this, "Data added",
                            Toast.LENGTH_LONG).show();
                    selectDate.setText("");
                    amount.setText("");
                    spinner.setSelection(0);
                    note.setText("");

                    startActivity(new Intent(RegisterExpensesActivity.this, ExpenseTrackerListActivity.class));


                }else {
                    Toast.makeText(com.example.moneyjars.RegisterExpensesActivity.this, "Data not added",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}