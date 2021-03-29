package com.example.moneyjars.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseBase extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "MoneyJarsDB";
    final static int DATABASE_VERSION = 2;
    final static String TABLE_USER = "USER";
    final static String TABLE_USER_COL_EMAIL        = "Email";
    final static String TABLE_USER_COL_FIRSTNAME    = "FirstName";
    final static String TABLE_USER_COL_LASTNAME     = "LastName";
    final static String TABLE_USER_COL_PASSWORD     = "Password";
    final static String TABLE_USER_COL_ROLE         = "ROLE";
    final static String TABLE_USER_COL_ROLE_ADMIN = "01";
    final static String TABLE_USER_COL_ROLE_USER = "02";


    final static String TABLE_FINANCIAL = "FINANCIAL";
    final static String TABLE_FINANCIAL_COL_FINANCIALID        = "FinancialID";
    final static String TABLE_FINANCIAL_COL_TITLE    = "Title";
    final static String TABLE_FINANCIAL_COL_AMOUNT     = "Amount";
    final static String TABLE_FINANCIAL_COL_ISSUEDATE     = "IssuedDate";
    final static String TABLE_FINANCIAL_COL_TYPE         = "Type";
    final static String TABLE_FINANCIAL_COL_EMAIL         = "Email";
    final static String TABLE_FINANCIAL_COL_CATEGORYID      = "CategoryID";
    final static String TABLE_FINANCIAL_COL_TYPE_EXPENCE_DAY = "01";
    final static String TABLE_FINANCIAL_COL_TYPE_EXPENCE_MONTHLY = "02";

    final static String TABLE_EXPENSE = "EXPENSE";
    final static String TABLE_EXPENSE_COL_FINANCIALID        = "FinancialID";
    final static String TABLE_EXPENSE_COL_NOTE    = "Note";

    final static String TABLE_CATEGORY = "CATEGORY";
    final static String TABLE_CATEGORY_COL_CATEGORYID        = "CategoryID";
    final static String TABLE_CATEGORY_COL_CATEGORYNAME    = "CategoryName";
    final static String TABLE_CATEGORY_COL_CATEGORYTYPE     = "CategoryType";
    final static String TABLE_CATEGORY_COL_CATEGORYTYPE_INCOME = "01";
    final static String TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE = "02";

    final static String TABLE_BIGEXPENSE = "BIGEXPENSE";
    final static String TABLE_BIGEXPENSE_COL_BIGEXPENSEID   = "BigExpenseID";
    final static String TABLE_BIGEXPENSE_COL_TITLE          = "Title";
    final static String TABLE_BIGEXPENSE_COL_AMOUNT         = "Amount";
    final static String TABLE_BIGEXPENSE_COL_ISSUEDATE      = "IssuedDate";
    final static String TABLE_BIGEXPENSE_COL_EMAIL          = "Email";


    public DatabaseBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_USER +" ("+ TABLE_USER_COL_EMAIL +" VARCHAR(30),"+
                TABLE_USER_COL_FIRSTNAME + " VARCHAR(20),"+
                TABLE_USER_COL_LASTNAME + " VARCHAR(20),"+
                TABLE_USER_COL_PASSWORD + " VARCHAR(20),"+
                TABLE_USER_COL_ROLE + " CHAR(2),"+
                "PRIMARY KEY ("+TABLE_USER_COL_EMAIL+"))";
        db.execSQL(query);

        query = "CREATE TABLE "+TABLE_FINANCIAL + " ("+TABLE_FINANCIAL_COL_FINANCIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TABLE_FINANCIAL_COL_TITLE + " VARCHAR(50),"+
                TABLE_FINANCIAL_COL_AMOUNT + " DECIMAL(10,2),"+
                TABLE_FINANCIAL_COL_ISSUEDATE + " DATE,"+
                TABLE_FINANCIAL_COL_TYPE + " CHAR(2),"+
                TABLE_FINANCIAL_COL_EMAIL + " VARCHAR(30),"+
                TABLE_FINANCIAL_COL_CATEGORYID + " INT,"+
                "FOREIGN KEY("+TABLE_FINANCIAL_COL_CATEGORYID+") REFERENCES "+TABLE_CATEGORY+"("+ TABLE_CATEGORY_COL_CATEGORYID +"),"+
                "FOREIGN KEY("+TABLE_FINANCIAL_COL_EMAIL+") REFERENCES "+TABLE_USER+"("+ TABLE_USER_COL_EMAIL +") ON DELETE CASCADE)";
        db.execSQL(query);

        query = "CREATE TABLE "+TABLE_EXPENSE + " ("+TABLE_EXPENSE_COL_FINANCIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TABLE_EXPENSE_COL_NOTE + " VARCHAR(5000),"+
                "FOREIGN KEY("+TABLE_EXPENSE_COL_FINANCIALID+") REFERENCES "+TABLE_FINANCIAL+"("+ TABLE_FINANCIAL_COL_FINANCIALID +") ON DELETE CASCADE)";
        db.execSQL(query);

        query = "CREATE TABLE "+ TABLE_CATEGORY +" ("+ TABLE_CATEGORY_COL_CATEGORYID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TABLE_CATEGORY_COL_CATEGORYNAME + " VARCHAR(50),"+
                TABLE_CATEGORY_COL_CATEGORYTYPE + " CHAR(2))";
        db.execSQL(query);


        query = "CREATE TABLE "+TABLE_BIGEXPENSE + " ("+TABLE_BIGEXPENSE_COL_BIGEXPENSEID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TABLE_BIGEXPENSE_COL_TITLE + " VARCHAR(50),"+
                TABLE_BIGEXPENSE_COL_AMOUNT + " DECIMAL(10,2),"+
                TABLE_BIGEXPENSE_COL_ISSUEDATE + " DATE,"+
                TABLE_BIGEXPENSE_COL_EMAIL + " VARCHAR(30),"+
                "FOREIGN KEY("+TABLE_BIGEXPENSE_COL_EMAIL+") REFERENCES "+TABLE_USER+"("+ TABLE_USER_COL_EMAIL +") ON DELETE CASCADE)";
        db.execSQL(query);

        List<String> queries = new ArrayList<String>();
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10001, 'Selling', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_INCOME+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10002, 'Gifts', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_INCOME+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10003, 'Interest Money', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_INCOME+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10004, 'Award', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_INCOME+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10005, 'Other Income', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_INCOME+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10007, 'Fees & Charges', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10008, 'Insurances', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10009, 'Business', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10010, 'Investment', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10011, 'Education', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10012, 'Books', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10013, 'Family', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10014, 'Pets', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10015, 'Home Services', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10016, 'Home Improvement', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10017, 'Children & Babies', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10018, 'Gifts & Donations', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10019, 'Funeral', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10020, 'Marriage', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10021, 'Health & Fitness', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10022, 'Personal Care', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10023, 'Pharmacy', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10024, 'Doctor', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10025, 'Sports', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10026, 'Travel', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10027, 'Entertainment', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10028, 'Game', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10029, 'Movies', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10030, 'Friends & Lover', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10031, 'Shopping', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10032, 'Electronics', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10033, 'Accessories', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10034, 'Footwear', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10035, 'Clothing', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10036, 'Transportation', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10037, 'Maintenance', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10038, 'Petrol', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10039, 'Parking Fees', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10040, 'Taxi', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10041, 'Bills & Utilities', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10042, 'Rentals', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10043, 'Internet Bill', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10044, 'Television Bill', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10045, 'Gas Bill', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10046, 'Electricity Bill', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10047, 'Water Bill', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10048, 'Phone Bill', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10049, 'Food & Beverage', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10050, 'Cafe', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");
        queries.add("INSERT INTO "+TABLE_CATEGORY+" VALUES(10051, 'Restaurants', '"+TABLE_CATEGORY_COL_CATEGORYTYPE_EXPENSE+"')");



/****************************************************** TEST DATA FOR REPORT START********************************/
        queries.add("insert into user values('test1@hotmail.com', 'Sunghoan', 'We', '1234', '02')");
        queries.add("insert into financial values(200001, 'DayExpense1', 1000, '2021-03-24 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200002, 'DayExpense1', 2000, '2021-03-24 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200003, 'DayExpense1', 3000, '2021-03-24 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200004, 'DayExpense1', 4000, '2021-03-24 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200005, 'DayExpense1', 5000, '2021-03-23 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200006, 'DayExpense1', 6000, '2021-03-23 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200007, 'DayExpense1', 7000, '2021-03-23 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200008, 'DayExpense1', 8000, '2021-03-23 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200009, 'DayExpense1', 9000, '2021-03-22 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200010, 'DayExpense1', 10000, '2021-03-22 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200011, 'DayExpense1', 3000, '2021-03-22 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200012, 'DayExpense1', 4000, '2021-03-22 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200013, 'DayExpense1', 1000, '2021-03-25 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200014, 'DayExpense1', 2000, '2021-03-25 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200015, 'DayExpense1', 3000, '2021-03-25 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200016, 'DayExpense1', 4000, '2021-03-25 10:20:00', '01', 'test1@hotmail.com', '10019')");

        queries.add("insert into financial values(200017, 'DayExpense1', 1000, '2021-02-24 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200018, 'DayExpense1', 2000, '2021-02-24 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200019, 'DayExpense1', 3000, '2021-02-24 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200020, 'DayExpense1', 4000, '2021-02-24 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200021, 'DayExpense1', 5000, '2021-02-23 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200022, 'DayExpense1', 6000, '2021-02-23 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200023, 'DayExpense1', 7000, '2021-02-23 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200024, 'DayExpense1', 8000, '2021-02-23 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200025, 'DayExpense1', 9000, '2021-02-22 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200026, 'DayExpense1', 10000, '2021-02-22 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200027, 'DayExpense1', 3000, '2021-02-22 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200028, 'DayExpense1', 4000, '2021-02-22 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200029, 'DayExpense1', 1000, '2021-02-25 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200030, 'DayExpense1', 2000, '2021-02-25 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200031, 'DayExpense1', 3000, '2021-02-25 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200032, 'DayExpense1', 4000, '2021-02-25 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200033, 'DayExpense1', 1000, '2021-01-24 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200034, 'DayExpense1', 2000, '2021-01-24 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200035, 'DayExpense1', 3000, '2021-01-24 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200036, 'DayExpense1', 4000, '2021-01-24 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200037, 'DayExpense1', 5000, '2021-01-23 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200038, 'DayExpense1', 6000, '2021-01-23 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200039, 'DayExpense1', 7000, '2021-01-23 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200040, 'DayExpense1', 8000, '2021-01-23 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200041, 'DayExpense1', 9000, '2021-01-22 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200042, 'DayExpense1', 10000, '2021-01-22 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200043, 'DayExpense1', 3000, '2021-01-22 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200044, 'DayExpense1', 4000, '2021-01-22 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200045, 'DayExpense1', 1000, '2021-01-25 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200046, 'DayExpense1', 2000, '2021-01-25 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200047, 'DayExpense1', 3000, '2021-01-25 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200048, 'DayExpense1', 4000, '2021-01-25 10:20:00', '01', 'test1@hotmail.com', '10019')");

        queries.add("insert into financial values(200049, 'DayExpense1', 1000, '2020-12-24 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200050, 'DayExpense1', 2000, '2020-12-24 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200051, 'DayExpense1', 3000, '2020-12-24 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200052, 'DayExpense1', 4000, '2020-12-24 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200053, 'DayExpense1', 5000, '2020-12-23 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200054, 'DayExpense1', 6000, '2020-12-23 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200055, 'DayExpense1', 7000, '2020-12-23 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200056, 'DayExpense1', 8000, '2020-12-23 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200057, 'DayExpense1', 9000, '2020-12-22 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200058, 'DayExpense1', 10000, '2020-12-22 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200059, 'DayExpense1', 3000, '2020-12-22 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200060, 'DayExpense1', 4000, '2020-12-22 10:20:00', '01', 'test1@hotmail.com', '10019')");
        queries.add("insert into financial values(200061, 'DayExpense1', 1000, '2020-12-25 00:00:00', '01', 'test1@hotmail.com', '10016')");
        queries.add("insert into financial values(200062, 'DayExpense1', 2000, '2020-12-25 10:00:00', '01', 'test1@hotmail.com', '10017')");
        queries.add("insert into financial values(200063, 'DayExpense1', 3000, '2020-12-25 20:00:00', '01', 'test1@hotmail.com', '10018')");
        queries.add("insert into financial values(200064, 'DayExpense1', 4000, '2020-12-25 10:20:00', '01', 'test1@hotmail.com', '10019')");
        /****************************************************** TEST DATA FOR REPORT END ********************************/

        for(int i=0; i < queries.size(); i++) {
            System.out.println(queries.get(i));
            db.execSQL(queries.get(i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIGEXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINANCIAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

}
