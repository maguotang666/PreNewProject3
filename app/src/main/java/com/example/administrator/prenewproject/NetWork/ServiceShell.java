package com.example.administrator.prenewproject.NetWork;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;

import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;

public class ServiceShell {

    /**
     * 上传文件
     *
     * @param params1
     * @param callback
     * @param paths
     */
    public static void uploadFiles(Map<String, String> params1, AStringCallBack callback, List<String> paths) {
        OkHttp.uploadFiles(AppNetConfig.getInstance().DATA_URL, params1, callback, paths);
    }

    public static void getObject(Map<String, String> params1, AStringCallBack callback) {
        OkHttp.getObject(AppNetConfig.getInstance().Data_url_1, params1, callback);
    }

    public static void getObjectNoKey(String url, AStringCallBack callback) {
        OkHttp.getObjectNoKey(url, callback);
    }

    //获取首页列表数据
    public static void getHomeListNoKey(String url, int index, AStringCallBack callback) {
        OkHttp.getObjectNoKey(url + index + AppNetConfig.getInstance().DATA_HOME_LIST2, callback);
    }

    //获取收藏列表数据
    public static void postCollectListNoKey(String url, int index, AStringCallBack callback) {
        OkHttp.postObjectNoKey(url + index + AppNetConfig.getInstance().DATA_HOME_LIST2, callback);
    }


    //取消收藏数据
    public static void postUncollectListNoKey(String url, int index,Map<String, String> params1, AStringCallBack callback) {
        OkHttp.postObjectKey(url + index + AppNetConfig.getInstance().DATA_HOME_LIST2,params1, callback);
    }


    //获取首页列表数据
    public static void postObiectKey(String url, Map<String, String> params1, AStringCallBack callback) {
        OkHttp.postObjectKey(url, params1, callback);
    }


    //获取首页列表数据
    public static void postLoginKey(String url, Map<String, String> params1, AStringCallBack callback) {
        OkHttp.postLoginKey(url, params1, callback);
    }




    /**
     * 获取cookies
     */
    public static String getCookiesStr() {
        String cookiesInfo = "";
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl) {
            CookieStore cookieStore = ((CookieJarImpl) cookieJar).getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
            for (Cookie cookie : cookies) {
                cookiesInfo = cookiesInfo + cookie.name() + ":" + cookie.value() + ";";
            }
        }
        return cookiesInfo;
    }

    /**
     * 清除cookies
     */
    public static void cleanCookies() {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        ((CookieJarImpl) cookieJar).getCookieStore().removeAll();

    }

}
