package com.example.xuanfuqiu;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * 开始获取安卓手机的权限要求开启权限以及关闭检查是否拥有权限
 * */
public class MainActivity extends AppCompatActivity {

    /**
     * 首先创建一个Intent进行关联Float类
     * */
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this,FloatActivity.class);

        //现在实现第一个按钮的功能. 实现它的单机事件添加一个监听器
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 现在实现判断当前版本号.当前的SDK是否支持
                 * */
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                        startService(intent);
                }else{
                        startFloatingService();
                }
            }
        });

        /**
         * 这里开始实现对隐藏按钮的监听事件
         * */
         findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 /**
                  * 需要先判断Intent是否为空
                  * */
                 if(intent != null){
                        stopService(intent);//停止关闭当前的悬浮球体
                 }
             }
         });
    }

    /**
     * 这个API所支持的版本需要的版本号
     *
     * 这个方法主要用于对悬浮球的控制以及检查是否安装开启悬浮球这权限
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startFloatingService() {
        /**
         * 在这之前我们需要创建一个ArrayUtil的类 用于判断某个服务是否正在运行之中
         *
         * 在这个IF进行判断的为了防止他再次启动一次
         *
         * 对于Toast.makeText这个方法是用于在手机下方显示的提示的文字
         * */
        if(ArrayUtil.isServiceWork(this,"com.example.xuanfuqiu.FloatActivity")){
            Toast.makeText(this,"已经启动了",Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * 这个是用于判断当前悬浮球是否开启了手机的权限如果没有开启权限则需要开启权限才可以使用悬浮球
         * */
        if(!Settings.canDrawOverlays(this)){
            Toast.makeText(this,"当前悬浮球没有权限请开启",Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        }else
            startService(intent);
    }
    /**
     * API版本号 上面已经说过不再多说
     *
     * 这个方法完成之后这个类也就完成了.  开始FloatActivity这个类里复杂的代码了
     * */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == 0){
                if(!Settings.canDrawOverlays(this)){
                    Toast.makeText(this,"授权失败!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"授权成功!",Toast.LENGTH_SHORT).show();
                    startService(intent);
                }
            }
    }
}
