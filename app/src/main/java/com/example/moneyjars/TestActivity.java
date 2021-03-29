package com.example.moneyjars;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyjars.helper.DatabaseHelper;

public class TestActivity extends HeaderActivity {
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        databaseHelper = new DatabaseHelper(this);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnView = findViewById(R.id.btnView);
        TextView txtView = findViewById(R.id.txtView);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtFName = findViewById(R.id.txtFName);
        EditText txtLName = findViewById(R.id.txtLName);
        EditText txtPwd = findViewById(R.id.txtPwd);
        EditText txtRole = findViewById(R.id.txtRole);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(1);
                boolean isInserted = databaseHelper.addUserRecord(txtEmail.getText().toString(),txtFName.getText().toString(), txtLName.getText().toString(), txtPwd.getText().toString(), txtRole.getText().toString());
                if(isInserted) {
                    Toast.makeText(TestActivity.this,"course added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TestActivity.this,"course not added", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(2);
                StringBuilder str = new StringBuilder();
                Cursor c = databaseHelper.viewUserData();
                if(c.getCount() > 0) {
                    while(c.moveToNext()) {
                        str.append("Email : "+ c.getString(0));
                        str.append(" Name : " + c.getString(1));
                        str.append(" " + c.getString(2));
                        str.append(" Pwd : " + c.getString(3));
                        str.append(" Role : " + c.getString(4));

                        str.append("\n");
                    }
                    txtView.setText(str);
                }
                c.close();
            }
        });
    }
}
