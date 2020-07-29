/**
 *
 */
package com.example.administrator.prenewproject.utils;

import android.app.Activity;
import android.widget.Toast;

import com.example.administrator.prenewproject.MyApplication;


public class ToastUtil {

    private static Toast mToast;

    private static ToastUtil toastUtil;

    public static ToastUtil getInstance() {
        if (toastUtil == null) {
            synchronized (ToastUtil.class) {
                if (toastUtil == null) {
                    toastUtil = new ToastUtil();
                }
            }
        }
        return toastUtil;
    }



    public static void showShortToast(final String message) {


        WorkerUtils.postMain(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(MyApplication.mContext, message, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(message);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        });
    }

    public static void show(String info) {


        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.mContext, info, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(info);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();


        //Toast.makeText(context, info, Toast.LENGTH_LONG).show();

    }

    public static void showToMain(final String info) {

        WorkerUtils.postMain(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(MyApplication.mContext, info, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(info);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        });

        //Toast.makeText(context, info, Toast.LENGTH_LONG).show();

    }



}
