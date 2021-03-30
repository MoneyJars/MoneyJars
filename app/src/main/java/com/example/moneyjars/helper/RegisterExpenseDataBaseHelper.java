package com.example.moneyjars.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moneyjars.helper.DatabaseBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RegisterExpenseDataBaseHelper extends DatabaseBase {

    SQLiteDatabase sqLiteDatabase;

    public RegisterExpenseDataBaseHelper(Context context) {
        super(context);
        sqLiteDatabase = super.getWritableDatabase();

    }

    public boolean registerExpenseToFinancial(String dateType, String issueDate, int amount, String selectedCategoryId, String userEmail) {

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        if(dateType == "01") {
            values.put(TABLE_FINANCIAL_COL_TYPE, TABLE_FINANCIAL_COL_TYPE_EXPENCE_DAY);
            values.put(TABLE_FINANCIAL_COL_TITLE, "DayExpense");
        }else if (dateType == "02"){
            values.put(TABLE_FINANCIAL_COL_TYPE, TABLE_FINANCIAL_COL_TYPE_EXPENCE_MONTHLY);
            values.put(TABLE_FINANCIAL_COL_TITLE, "MonthlyExpense");
        }
        values.put(TABLE_FINANCIAL_COL_EMAIL, userEmail);
        values.put(TABLE_FINANCIAL_COL_ISSUEDATE, issueDate);
        values.put(TABLE_FINANCIAL_COL_AMOUNT, amount);
        values.put(TABLE_FINANCIAL_COL_CATEGORYID,selectedCategoryId);

        long r = sqLiteDatabase.insert(TABLE_FINANCIAL, null, values);

        if(r>0) {
            return true;
        }else {
            return false;
        }

    }

    public boolean registerExpenseToExpense(String note) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_EXPENSE_COL_NOTE, note);

        long r = sqLiteDatabase.insert(TABLE_EXPENSE, null, values);

        if(r>0) {
            return true;
        }else {
            return false;
        }

    }


    public Cursor selectDataOfDay(String email, String date) {

        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT B." + TABLE_CATEGORY_COL_CATEGORYID+ ", B."+TABLE_CATEGORY_COL_CATEGORYNAME+", A."+TABLE_FINANCIAL_COL_AMOUNT+", A."+ TABLE_FINANCIAL_COL_FINANCIALID + " FROM " + TABLE_FINANCIAL + " AS A JOIN "+ TABLE_CATEGORY + " AS B ON A." +TABLE_FINANCIAL_COL_CATEGORYID+" = B."+TABLE_CATEGORY_COL_CATEGORYID + " WHERE A."+TABLE_FINANCIAL_COL_EMAIL+" = '"+ email +"' AND " + TABLE_FINANCIAL_COL_ISSUEDATE + " = '" + date + "'";
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

    public Cursor selectDataOfMonth(String email, String month) {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT B." + TABLE_CATEGORY_COL_CATEGORYID+ ", B."+TABLE_CATEGORY_COL_CATEGORYNAME+", sum(A."+TABLE_FINANCIAL_COL_AMOUNT+"), A."+ TABLE_FINANCIAL_COL_FINANCIALID + " FROM " + TABLE_FINANCIAL + " AS A INNER JOIN "+ TABLE_CATEGORY + " AS B ON A." +TABLE_FINANCIAL_COL_CATEGORYID+" = B."+TABLE_CATEGORY_COL_CATEGORYID + " WHERE A."+TABLE_FINANCIAL_COL_EMAIL+" = '"+ email +"' AND " + TABLE_FINANCIAL_COL_ISSUEDATE + " LIKE '%" + month + "%'  GROUP BY A."+TABLE_CATEGORY_COL_CATEGORYID;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

    public Cursor selectDataOfSixMonth(String email, String startMonth, String endMonth) {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT B." + TABLE_CATEGORY_COL_CATEGORYID+ ", B."+TABLE_CATEGORY_COL_CATEGORYNAME+", sum(A."+TABLE_FINANCIAL_COL_AMOUNT+"), A."+ TABLE_FINANCIAL_COL_FINANCIALID + " FROM " + TABLE_FINANCIAL + " AS A INNER JOIN "+ TABLE_CATEGORY + " AS B ON A." +TABLE_FINANCIAL_COL_CATEGORYID+" = B."+TABLE_CATEGORY_COL_CATEGORYID + " WHERE A."+TABLE_FINANCIAL_COL_EMAIL+" = '"+ email +"' AND " + TABLE_FINANCIAL_COL_ISSUEDATE + " BETWEEN '" + startMonth + "/01' AND '" + endMonth + "/31' GROUP BY A."+TABLE_CATEGORY_COL_CATEGORYID;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

    public Cursor getCategories() {

        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT " + TABLE_CATEGORY_COL_CATEGORYID+ ", "+TABLE_CATEGORY_COL_CATEGORYNAME + " FROM " + TABLE_CATEGORY +" WHERE "+TABLE_CATEGORY_COL_CATEGORYTYPE + " = '" +TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE + "'";
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public Cursor getExpenseDetail(String financialId, String categoryId) {

        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT F." + TABLE_FINANCIAL_COL_TYPE + ", F." + TABLE_FINANCIAL_COL_ISSUEDATE + ", C." + TABLE_CATEGORY_COL_CATEGORYNAME + ", F." + TABLE_FINANCIAL_COL_AMOUNT + ", E." + TABLE_EXPENSE_COL_NOTE +
                " FROM " + TABLE_FINANCIAL + " AS F INNER JOIN " + TABLE_EXPENSE + " AS E ON F." + TABLE_FINANCIAL_COL_FINANCIALID + " = E." + TABLE_EXPENSE_COL_FINANCIALID + " INNER JOIN " + TABLE_CATEGORY +
                " AS C ON F." + TABLE_FINANCIAL_COL_CATEGORYID + " = C." + TABLE_CATEGORY_COL_CATEGORYID + " WHERE F." + TABLE_FINANCIAL_COL_FINANCIALID + " = " + financialId + " AND C." + TABLE_CATEGORY_COL_CATEGORYID + " = " + categoryId;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

    public boolean deleteRec(String financialId) {
        sqLiteDatabase = this.getWritableDatabase();
        int d = sqLiteDatabase.delete(TABLE_FINANCIAL, "FinancialID=?", new String[]{financialId});
        int d1 = sqLiteDatabase.delete(TABLE_EXPENSE, "FinancialID=?", new String[]{financialId});

        if(d > 0 && d1 > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Cursor getMonthlyFixedCost() {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT SUM(" + TABLE_FINANCIAL_COL_AMOUNT + ") FROM " + TABLE_FINANCIAL + " WHERE " + TABLE_FINANCIAL_COL_TYPE + " = " +TABLE_FINANCIAL_COL_TYPE_EXPENCE_MONTHLY ;
        System.out.println(query);
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

}