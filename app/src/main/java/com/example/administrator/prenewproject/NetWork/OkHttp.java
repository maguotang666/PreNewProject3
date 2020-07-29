package com.example.administrator.prenewproject.NetWork;

import com.example.administrator.prenewproject.utils.AContext;
import com.example.administrator.prenewproject.utils.CustomProgressDialog;
import com.example.administrator.prenewproject.utils.LodDialogClass;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OkHttp {
    public static CustomProgressDialog dialog;

    /**
     * 多文件上传
     * @param url
     * @param params
     * @param stringCallback
     * @param paths
     */
    public static void uploadFiles(String url, Map<String, String> params, final AStringCallBack stringCallback,
                                   List<String> paths) {

//        DialogUtils.getInstance().showDialog();
//        LodDialogClass.showCustomCircleProgressDialog(AContext.getCurrentActivity(), "请求中，请稍后...", null);
        final Map<String, File> fileMap = new HashMap<>();
        for (int i = 0; i < paths.size(); i++) {
            fileMap.put(paths.get(i), new File(AContext.getInstance().getFileUtil().getDownloadPath(), paths.get(i)));
        }

        OkHttpUtils.post().url(url).tag(getTag()).params(params).files("mFile",fileMap)
                .build().execute(stringCallback);
    }

    public static Object getTag() {

        return AContext.getCurrentActivity().getClass();
    }

    public static void getObject(String url, Map<String, String> params, AStringCallBack stringCallback){


//        DialogUtils.getInstance().showDialog();
//        LodDialogClass.showCustomCircleProgressDialog(AContext.getCurrentActivity(), "请求中，请稍后...", null);

        OkHttpUtils.post().url(url).tag(getTag()).params(params).build().execute(stringCallback);

    }
    public static void getObjectNoKey(String url,  AStringCallBack stringCallback){

//        LodDialogClass.showCustomCircleProgressDialog(AContext.getCurrentActivity(), "请求中，请稍后...", null);
//        DialogUtils.getInstance().showDialog();

        OkHttpUtils.get().url(url).tag(getTag()).build().execute(stringCallback);

    }


    public static void postObjectKey(String url, Map<String, String>  params1,  AStringCallBack stringCallback){

        LodDialogClass.showCustomCircleProgressDialog(AContext.getCurrentActivity(), "请求中，请稍后...", null);
//        DialogUtils.getInstance().showDialog();

        OkHttpUtils.post().url(url).params(params1).tag(getTag()).build().execute(stringCallback);

    }


    public static void postObjectNoKey(String url, AStringCallBack stringCallback){

//        LodDialogClass.showCustomCircleProgressDialog(AContext.getCurrentActivity(), "请求中，请稍后...", null);
//        DialogUtils.getInstance().showDialog();

        OkHttpUtils.post().url(url).tag(getTag()).build().execute(stringCallback);

    }



    public static void postLoginKey(String url, Map<String, String> params1, AStringCallBack callback) {
//        LodDialogClass.showCustomCircleProgressDialog(AContext.getCurrentActivity(), "请求中，请稍后...", null);
//        DialogUtils.getInstance().showDialog();

        OkHttpUtils.post().url(url).params(params1).tag(getTag()).build().execute(callback);

    }
}
