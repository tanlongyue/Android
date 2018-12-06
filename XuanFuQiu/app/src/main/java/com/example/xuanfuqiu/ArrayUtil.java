package com.example.xuanfuqiu;

import android.app.ActivityManager;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 当前类用于判断当前是否开启悬浮球
 * */
public class ArrayUtil {

        /**
         * 创建一个boolean的方法  判断程序的悬浮球是否在运行
         * return true 为运行
         * return false为没有运行
         * 方法名为isServiceWork 顾名思义
         * */
        public static boolean isServiceWork(@NotNull Context context, String uName){
            boolean isok = false;//一开始为没有运行
            /**
             * 这里为获取当前服务
             * */
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> list = activityManager.getRunningServices(40);
            if (list.size()<0)
                return false;

            for (int i = 0; i < list.size(); i++) {
                String mName = list.get(i).service.getClassName();
                if (uName.equals(mName)){
                    isok = true;
                    break;
                }
            }
            return isok;
        }
}
