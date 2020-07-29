package com.example.administrator.prenewproject.base;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.manger.ActManager;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.CustomProgressDialog;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @Description:界面的抽取，包括标题、网络、图片加载
 * @author: Andruby
 * @date: 2016年7月7日 下午4:46:44
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    /**service绑定成功*/
    private static final int SERVICE_OK = 0x1;

    /**数据更新*/
    private static final int DATA_UPDATE = 0x2;
    /**
     * 图片加载
     */
    public ActionBar actionBar;
    private Unbinder unBind;
//    public CustomProgressDialog dialog;
    public Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBeforeLayout();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        translucentStatusBar();
        mContext = this;
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ActManager.addActivity(this);
        unBind = ButterKnife.bind(this);
//        dialog = new CustomProgressDialog(this, "正在加载...", R.drawable.loading);
        gson=new Gson();
        initView();
        initData();
        setListener();
    }

    protected void setBeforeLayout() {
    }
    /**更新View数据*/
//    protected abstract void updateViewData(int requestID,String resultStr);

    /**
     * 返回当前界面布局文件
     */
    protected abstract int getLayoutId();

    /**
     * 此方法描述的是： 初始化所有view
     */
    protected abstract void initView();

    /**
     * 此方法描述的是： 初始化所有数据的方法
     */
    protected abstract void initData();

    /**
     * 此方法描述的是： 设置所有事件监听
     */
    protected abstract void setListener();

    @Override
    protected void onResume() {
        super.onResume();
    }


    public <T extends View> T obtainView(int resId) {
        return (T) findViewById(resId);
    }



    protected void startToActivity(Class<?> cla){
        Intent intent=new Intent(this,cla);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind.unbind();
//        CommonUtils.getInstance().hindDialog();
        ActManager.finishActivity(this);
    }

    /**
     * 沉浸式状态栏
     */
    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

    }

}
