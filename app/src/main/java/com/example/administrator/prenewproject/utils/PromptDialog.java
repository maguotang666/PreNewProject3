package com.example.administrator.prenewproject.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
//import android.support.v7.app.AlertDialog;

/***@Description:自定义对话框
 * @author Administrator
 */
public class PromptDialog {
    private AlertDialog creatDia;
    //输入账号和密码的对话框
    private AlertDialog  inPutPwDialog;
    public PromptDialog(Context context) {
        this.mContext = context;
    }

    public void setCallback(DialogCallback callback) {
        mCallback = callback;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public void dialogShow(final String message) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
//             .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(title)//设置对话框的标题
                .setMessage(message)//设置对话框的内容
                //设置对话框的按钮
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.positive(message);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCallback.negative("");
                                dialog.dismiss();
                            }
                        }
                ).create();
        dialog.show();
    }

    //自定义布局
    public void dialogShow( View view) {

        inPutPwDialog = new AlertDialog.Builder(mContext)
//             .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(title)//设置对话框的标题
                .setView(view)
                .setCancelable(true)
                //设置对话框的按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.positive("");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCallback.negative("");
                            }
                        }
                ).create();
        inPutPwDialog.show();

    }


    private Context mContext;
    private DialogCallback mCallback;

    public static interface DialogCallback {
        /**
         * positive  正的
         * 确定
         */
        void positive(String positive);

        /***
         * Negative 负的
         *
         * 取消
         */
        void negative(String negative);

        //中间的按钮
        void middleBtn(String middle);
    }

    //三按钮的对话框
    public void dialogThreeBtnShow(final String message, final int index) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

            dialog.setTitle(title);//设置对话框的标题
            dialog.setMessage(message);//设置对话框的内容
//             .setIcon(R.mipmap.icon)//设置标题的图片
            dialog.setCancelable(false);
            dialog.setPositiveButton("返回", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {

                    String send = String.valueOf(index);
                    dialog.dismiss();
                    mCallback.positive(send);
                }
            });
            //	中间的按钮
            dialog.setNeutralButton("中断", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.dismiss();
                    mCallback.middleBtn("");

                }
            });
            //	第三个按钮
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.dismiss();
                    mCallback.negative("");

                }
            });


            creatDia = dialog.create();


            creatDia.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //只显示一个按钮
    public void dialogOneBtnShow(final String message, final int index) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

            dialog.setTitle(title);//设置对话框的标题
            dialog.setMessage(message);//设置对话框的内容
//             .setIcon(R.mipmap.icon)//设置标题的图片
            dialog.setCancelable(false);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {

                    String send = String.valueOf(index);
                    dialog.dismiss();
                    mCallback.positive(send);
                }
            });

            creatDia = dialog.create();


            creatDia.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //一步到位
    public PromptDialog showDialog(Context context, String title, View view, String msg, DialogCallback dialogCallback) {

        PromptDialog promptDialog = new PromptDialog(context);

        promptDialog.setTitle(title);
        if (!TextUtils.isEmpty(msg)) {
            promptDialog.dialogShow(msg);
        } else {
            promptDialog.dialogShow(view);
        }

        promptDialog.setCallback(dialogCallback);
        return promptDialog;
    }

}
