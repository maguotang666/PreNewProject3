package com.example.administrator.prenewproject.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;


/**
 * @author http://blog.csdn.net/finddreams
 * 网络请求进度加载框
 */
public class CustomProgressDialog extends Dialog {

    private AnimationDrawable mAnimation;
    private Context mContext;
    private ImageView mImageView;
    private String mLoadingTip;
    private TextView mLoadingTv;
    private int count = 0;
    private String oldLoadingTip;
    private int mResid;
    private AnimationDrawable ad;

    public CustomProgressDialog(Context context, String content, int id) {
        super(context, R.style.Transparent);
        this.mContext = context;
        this.mLoadingTip = content;
        this.mResid = id;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        initView();
        initData();
    }

    private void initData() {

        mImageView.setBackgroundResource(mResid);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        mLoadingTv.setText(mLoadingTip);
    }

    public void setContent(String str) {
        if (mLoadingTv!=null&&mImageView!=null){
            mLoadingTip = str;
            initData();
        }

    }

    private void initView() {
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
    }

}
