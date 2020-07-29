package com.example.administrator.prenewproject.utils;

import com.example.administrator.prenewproject.R;

public class DialogUtils {

    public static CustomProgressDialog dialog;

    private DialogUtils() {
    }

    private static DialogUtils dialogUtils;

    public static DialogUtils getInstance() {
        if (dialogUtils == null) {
            synchronized (DialogUtils.class) {
                if (dialogUtils == null) {
                    dialogUtils = new DialogUtils();
                }
            }
        }
        return dialogUtils;
    }


    public void showDialog(){
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = new CustomProgressDialog(AContext.getCurrentActivity(), "上传中。。。", R.drawable.loading);
        dialog.show();
    }


    public void dissDialog(){
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }



}
