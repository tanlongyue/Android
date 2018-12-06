package com.example.myoneandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Switch mSwitch;
    private TextView mText;
    private EditText editText;
    private EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitch = (Switch) findViewById(R.id.switch3);
        mText = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editText4);//用户名
        editText2 = (EditText) findViewById(R.id.editText5) ;//密码
        // 添加监听
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSwitch.setText(null);
                    mText.setVisibility(View.VISIBLE);
                }else {
                    mSwitch.setText(null);
                    //设置图片隐藏消失
                    mText.setVisibility(View.GONE);
                }
            }
        });

        //从这里开始实现Activity简单的数据传递
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关联两个MainActivity
                    Intent i = new Intent(MainActivity.this, Main3Activity.class);
                    String data = "当你看见这句话的时候就证明简单的数据传递成功了";
                    i.putExtra("消息",data);
                    startActivity(i);
            }
        });



    }
}
