package com.example.myoneandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        textView = (TextView) findViewById(R.id.textView4);
        String username = intent.getStringExtra("username");
        String passowrd = intent.getStringExtra("password");
        if(!username.equals("admin") || !passowrd.equals("admin")){
            textView.setText("登录失败!");
        }else{
            textView.setText("登录成功!");
        }
    }
}
