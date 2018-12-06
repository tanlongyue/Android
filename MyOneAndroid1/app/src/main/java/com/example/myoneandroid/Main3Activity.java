package com.example.myoneandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = (EditText) findViewById(R.id.editText4);//用户名
        editText2 = (EditText) findViewById(R.id.editText5) ;//密码

        Intent intent=getIntent();//获取传送过来的intent对象
        textView= (TextView)findViewById(R.id.textView3);
        //读取MainActivity里面的消息值
        String data=intent.getStringExtra("消息");
        textView.setText(data);


        //这里是实现用户名和密码登录的方法
        //创建一个监听button提交按钮的监听事件
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this,Main2Activity.class);
                String username = editText.getText().toString().trim();
                String password = editText2.getText().toString().trim();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
    }
}
