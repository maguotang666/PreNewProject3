package com.example.administrator.prenewproject;

import android.content.Context;
import android.support.annotation.Keep;

import com.example.administrator.prenewproject.utils.LogUtils;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by maguotang on 2019/6/3
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //检查是否又热更新
        SophixManager.getInstance().queryAndLoadNewPatch();
//        LogUtils.e("=----------------------------------------");

    }

    private void initSophix() {
        String appVersion = "1.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {

        }
        // initialize最好放在attachBaseContext最前面
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(null, null, null)
                .setEnableDebug(false)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            LogUtils.e("sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            LogUtils.e("sophix preload patch success. restart app to make effect.");
                        }else {
                            // 其它错误信息, 查看PatchStatus类说明
                            LogUtils.e("code=--1111111111-"+code);
                        }


                        LogUtils.e("code=--22222222222-"+code);
                    }
                }).initialize();

    }
}
