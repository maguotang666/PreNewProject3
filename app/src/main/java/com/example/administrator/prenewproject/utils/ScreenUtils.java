package com.example.administrator.prenewproject.utils;

import android.content.Context;

/**
 * Created by maguotang on 2019/4/30
 */
public class ScreenUtils {


    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

}
