package com.example.administrator.prenewproject.utils;

import android.content.Context;

import com.example.administrator.prenewproject.view.CustomProgressDialog;


public class LodDialogClass {

	private static com.example.administrator.prenewproject.view.CustomProgressDialog customDialog;

	// 显示自定义加载对话框
	public static com.example.administrator.prenewproject.view.CustomProgressDialog showCustomCircleProgressDialog(Context context, String msg, String title) {
		System.out.println("peoiijvkjvjdkjgkdslhglds");
		if (customDialog != null) {
			try {
				customDialog.cancel();
				customDialog = null;
			} catch (Exception e) {
			}
		}

		customDialog = CustomProgressDialog.createDialog(context);
		// dialog.setIndeterminate(false);
		// dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		customDialog.setCancelable(true);// 是否可用用"返回键"取消
		customDialog.setTitle(title);
		customDialog.setMessage(msg);

		try {
			customDialog.show();
		} catch (Exception e) {
		}

		customDialog.setCanceledOnTouchOutside(false);// 设置dilog点击屏幕空白处不消失
		return customDialog;
	}

	// 关闭自定义加载对话框
	public static void closeCustomCircleProgressDialog() {
		if (customDialog != null) {
			try {
				customDialog.cancel();
				customDialog = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}