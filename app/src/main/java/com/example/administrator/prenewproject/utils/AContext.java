package com.example.administrator.prenewproject.utils;


import android.app.Activity;

import com.example.administrator.prenewproject.MyApplication;

/**
 * Created by Administrator on 2017/8/10 0010.  tang
 */

public class AContext {
    private static FileUtil fileUtil;
    private static MyApplication sApplication;
    private static ActivityContext sActivityContext;

    private AContext() {
    }

    private static AContext aContext;

    public static AContext getInstance() {
        if (aContext == null) {
            synchronized (AContext.class) {
                if (aContext == null) {
                    aContext = new AContext();
                }
            }
        }
        return aContext;
    }



    public static MyApplication getApplication() {
        return sApplication;
    }

    public static void init(MyApplication currentApplication) {
        sApplication = currentApplication;
        sActivityContext = new ActivityContext();
        sApplication.registerActivityLifecycleCallbacks(sActivityContext);

        try {
            fileUtil = new FileUtil();
//            fileUtil.createRootFolders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // BaiduTTs.getInstace().setParam();
    }
    public FileUtil getFileUtil() {
        return fileUtil;
    }



    public static Activity getCurrentActivity() {
        return sActivityContext.getCurrentActivity();
    }

}
