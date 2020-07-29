package com.example.administrator.prenewproject.NetWork;

import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @author Administrator
 */
public abstract class AStringCallBack extends StringCallback {

    @Override
    public void onError(Call call, Exception e, int id) {
//        DialogUtils.getInstance().dissDialog();
//        LodDialogClass.closeCustomCircleProgressDialog();
        LogUtils.e("TAG onError", "我是异常" + e.getMessage());
        ToastUtil.show(e.getMessage());
        onDisSuccess(call,e,id);

    }

    @Override
    public void onResponse(String response, int id) {
//        LodDialogClass.closeCustomCircleProgressDialog();
//        DialogUtils.getInstance().dissDialog();


        onSuccess(response,id);
    }


    public abstract void onSuccess(String result, int id);


    public abstract void onDisSuccess(Call call, Exception e, int id);



}
