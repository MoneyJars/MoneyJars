package com.example.moneyjars;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HeaderActivity extends AppCompatActivity {
    public final static String USER_INFO = "UserInfo";
    public final static String USER_EMAIL = "email";
    public final static String USER_TYPE = "type";
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        preferences = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        String email = preferences.getString(USER_EMAIL, null);
        if (email == null || email.isEmpty()) {
// TODO remove comment
//            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    public void onClickMenu(View view) {
        System.out.println(1);
        Toast.makeText(HeaderActivity.this,"MAIN MENU", Toast.LENGTH_LONG).show();

    }

    public void onClickUserInfo(View view) {
        System.out.println(2);
        Toast.makeText(HeaderActivity.this,"USER INFO", Toast.LENGTH_LONG).show();

    }
}