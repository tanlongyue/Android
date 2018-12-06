package com.example.xuanfuqiu;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * 这个类主要实现对悬浮球的坐标控制.
 * */
public class Floating implements View.OnTouchListener {

    /**
     * 首先实现x 和 y的创建
     * */
    private int x;
    private int y;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    public Floating() {
    }

    public Floating(WindowManager windowManager,WindowManager.LayoutParams layoutParams) {
        this.windowManager = windowManager;
        this.layoutParams = layoutParams;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            /**
             * 对于ACTION_DOWN
             * 手指 初次接触到屏幕 时触发。
             */
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                break;
            /**
             * 对于ACTION_MOVE指的是
             * 手指 在屏幕上滑动 时触发，会多次触发。
             * */
            case MotionEvent.ACTION_MOVE:
                int xx = (int) event.getRawX();
                int yy = (int) event.getRawY();
                int xxx = xx - x;
                int yyy = yy - y;
                x = xx;
                y = yy;
                /**
                 * 设置被移动的位置
                 * */
                layoutParams.x = layoutParams.x + xxx;
                layoutParams.y = layoutParams.y + yyy;
                windowManager.updateViewLayout(v, layoutParams);
                break;
            default:
                break;
        }
        return false;
    }
}