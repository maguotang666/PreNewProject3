package com.example.administrator.prenewproject.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.administrator.prenewproject.Fragment.MainFr.LeftFragment;
import com.example.administrator.prenewproject.Fragment.MainFr.MainFragment;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.SPUtils;

import butterknife.BindView;

/**
 * 主页面
 *
 * @author maguotang
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.main)
    FrameLayout main;
    @BindView(R.id.left)
    RelativeLayout left;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    private MainFragment mainFragment;
    private LeftFragment leftFragment;
    private float fontSizeScale;

    @Override
    protected int getLayoutId() {

        /**
         * 单独的页面设置沉浸式状态栏的颜色
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
            window.setStatusBarColor(getResources().getColor(R.color.comm_title_color));
        }

        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        fontSizeScale = (float) SPUtils.get(this, CommonUtils.SP_FontScale, 0.0f);
    }

    @Override
    protected void initData() {

        mainFragment = new MainFragment();
        leftFragment = new LeftFragment();
        //添加内容，比较简单的
        getSupportFragmentManager().beginTransaction().replace(R.id.main, mainFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.left, leftFragment).commit();

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    public void showLeftPage() {
        drawerlayout.openDrawer(left);
    }

    @Override
    public Resources getResources() {
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();
        if(fontSizeScale>0.5){
            config.fontScale= fontSizeScale;//1 设置正常字体大小的倍数
        }
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
}
