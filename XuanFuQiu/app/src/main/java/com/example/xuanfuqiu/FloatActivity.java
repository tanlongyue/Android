package com.example.xuanfuqiu;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
/**
 * 这个类主要实现对悬浮球的控制.
 * 以及如果移到屏幕边缘实现显示一个关闭的图片
 * 点击关闭图片则可以关闭这个悬浮框但是程序并不受影响不关闭程序
 * */
public class FloatActivity extends Service {

    /**
     * 首先我们得创建一个WindowsManager 这个是可以使我们把悬浮球显示在屏幕上的接口
     * 也可以让我们对屏幕上面的悬浮球删掉
     *
     * WindowManager.LayoutParams为对悬浮球的布局
     * */
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View floatView;
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 开始获取系统服务窗口
         * */
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        /**
         * 对于这个Build.VERSION.SDK_INT的话 只是一个版本的比较
         * */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            /**
             * 对于WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
             * 系统顶层窗口。显示在其他一切内容之上。
             * 此窗口不能获得输入焦点，否则影响锁屏。
             * */
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            /**
             * 对于 WindowManager.LayoutParams.TYPE_PHONE;
             * 电话窗口。它用于电话交互（特别是呼入）。
             * 它置于所有应用程序之上，状态栏之下。
             * */
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        /**
         * 类似于渐变
         * */
        layoutParams.format = PixelFormat.RGBA_8888;
        /**
         * 对于FLAG_NOT_TOUCH_MODAL
         * 即使在该window在可获得焦点情况下，
         * 仍然把该window之外的任何event发送到该window之后的其他window.
         *
         * 对于FLAG_NOT_FOCUSABLE
         * 让window不能获得焦点，
         * 这样用户快就不能向该window发送按键事件及按钮事件
         * */
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = Gravity.LEFT;//这是设置悬浮球显示的一个位置 第一眼显示的位置
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//设置悬浮球的宽,如果不设置就会造成无法移动的后果
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//设置悬浮球的高,如果不设置的话就会造成无法移动的后果

        floatView = new View(getApplicationContext());//将不会收到Acitivity的生命周期的影响
        /**
         *  View.inflate的作用就是将一个用xml定义的布局文件查找出来
         *
         *  getApplicationContext（) 返回应用的上下文，
         *  生命周期是整个应用，应用摧毁它才摧毁Activity
         * */
        floatView = View.inflate(getApplicationContext(),R.layout.activity_float,null);
        final ImageView image = floatView.findViewById(R.id.logo);
        /**
         * ImageView的监听器
         * */
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭当前的服务
                stopSelf();
            }
        });

        /**
         * floatView 的监听事件实现floatView的image到达屏幕边缘的时候显示关闭按钮
         * */
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image.getVisibility() == View.GONE) {
                    image.setVisibility(View.VISIBLE);
                } else {
                    image.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 这个类为继承了Service 所实现的类 不用去管它
     * */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
        //添加一个试图
        windowManager.addView(floatView, layoutParams);
        //调用方法对悬浮球的大小位置进行进一步的控制
        floatView.setOnTouchListener(new Floating(windowManager,layoutParams));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatView != null) {
            /**
             * 对悬浮球进行删除试图
             * */
            windowManager.removeViewImmediate(floatView);
        }
    }
}
