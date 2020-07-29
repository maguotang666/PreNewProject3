package com.example.administrator.prenewproject;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.prenewproject.utils.AContext;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;


import org.android.agoo.huawei.HuaWeiRegister;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application{

    public static Context mContext;

    public static String userName="pre_new_user_name";
    public static String password="pre_new_psss_word";
    public static String NightPattern="night_pattern";
    private String TAG="TAG";
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        AContext.init(this);

        //设置具体的证书
//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(证书的inputstream, null, null);
        //设置可访问所有的https网站
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        //cookie持久化
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(this));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()

//                .addInterceptor(new LoggerInterceptor("TAG"))
                .writeTimeout(60, TimeUnit.SECONDS)//设置写入超时时间
                .readTimeout(100, TimeUnit.SECONDS)//读取超时时间
                .connectTimeout(60, TimeUnit.SECONDS)//链接超时时间
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

//        initPush();

    }

    private void initPush() {

        UMConfigure.init(this, "5ce60ab24ca357b25c000917","MywanAndroid",UMConfigure.DEVICE_TYPE_PHONE, "66e42101cce0d136e93d1d108e370c40");

        PlatformConfig.setWeixin("wx7c513225e2bb2e3a", "4d2d7593d00044be9f68f0180aeb2c38");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setQQZone("1109974011", "9Hs6rz11SZ9ebxpK");

        //初始化华为push
        HuaWeiRegister.register(this);
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);

        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                Log.e(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
//            }
//
//        });

        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
                                getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){

            @Override
            public void dealWithCustomAction(Context context, UMessage msg){
                Log.e(TAG,"click");
            }

        };

        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }
}
