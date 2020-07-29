package com.example.administrator.prenewproject.NetWork;

import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

public abstract class AFileCallBack extends FileCallBack{
    public AFileCallBack(String destFileDir, String destFileName) {
        super(destFileDir, destFileName);
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(File response, int id) {

    }
}
