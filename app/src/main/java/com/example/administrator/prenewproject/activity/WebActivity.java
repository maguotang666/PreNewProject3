package com.example.administrator.prenewproject.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.view.MyPopupWindow;
import com.example.administrator.prenewproject.view.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author maguotang
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_left)
    ImageView    ivTitleLeft;
    @BindView(R.id.iv_back)
    ImageView    ivBack;
    @BindView(R.id.tv_title)
    TextView     tvTitle;
    @BindView(R.id.iv_title_right)
    ImageView    ivTitleRight;
    @BindView(R.id.iv_three_point)
    ImageView    ivThreePoint;
    @BindView(R.id.webview)
    X5WebView    webview;
    @BindView(R.id.progressBar1)
    ProgressBar  progressBar1;
    @BindView(R.id.ll_acticity_web)
    LinearLayout llActicityWeb;

    private String      title;
    private String      link;
    private TextView tv_share;
    private TextView tv_collect;
    private TextView tv_open_browser;
    private MyPopupWindow myPopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        ivTitleLeft.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivTitleRight.setVisibility(View.GONE);
        ivThreePoint.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        link = getIntent().getStringExtra("link");
        initHardwareAccelerate();
        initPopu();

        tvTitle.setText(title);

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (progressBar1==null){
                    return;
                }

                progressBar1.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar1.setVisibility(View.INVISIBLE);
                }
                LogUtils.e("newProgress---" + newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        webview.loadUrl(link);
    }

    private void initPopu() {

        myPopupWindow= new MyPopupWindow(this, R.layout.popupwindow_one) {
            @Override
            protected void initView(View view) {
                tv_share=view.findViewById(R.id.tv_share);
                tv_collect=view.findViewById(R.id.tv_collect);
                tv_open_browser=view.findViewById(R.id.tv_open_browser);
            }
        };

    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
        ivThreePoint.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        tv_open_browser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_share:
                ToastUtil.show("分享");
                myPopupWindow.popuDiss();
                break;
            case R.id.tv_collect:
                ToastUtil.show("收藏");
                myPopupWindow.popuDiss();
                break;
            case R.id.tv_open_browser:
                myPopupWindow.popuDiss();

                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.iv_three_point:
                myPopupWindow.showAtLocation(llActicityWeb, Gravity.RIGHT | Gravity.TOP, 30, 30);
                break;
            default:

        }
    }

    /**
     *      * 启用硬件加速   
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    /**
     *      * 返回键监听     *     * @param keyCode     * @param event     * @return   
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview != null && webview.canGoBack()) {
                webview.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null)

        {
            webview.destroy();
        }

    }

}
