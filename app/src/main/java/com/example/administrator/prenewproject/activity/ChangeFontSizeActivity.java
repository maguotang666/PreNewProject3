package com.example.administrator.prenewproject.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.manger.ActManager;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.DensityUtils;
import com.example.administrator.prenewproject.utils.IntentUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.SPUtils;
import com.example.administrator.prenewproject.view.FontSizeView;
import com.taobao.accs.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 调整字体
 */
public class ChangeFontSizeActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @BindView(R.id.iv_three_point)
    ImageView ivThreePoint;
    @BindView(R.id.iv_switch)
    ImageView ivSwitch;
    @BindView(R.id.cl_comm)
    ConstraintLayout clComm;
    @BindView(R.id.tv_font_size1)
    TextView tvFontSize1;
    @BindView(R.id.iv_font_size)
    ImageView ivFontSize;
    @BindView(R.id.ll_font_size_1)
    RelativeLayout llFontSize1;
    @BindView(R.id.tv_font_size2)
    TextView tvFontSize2;
    @BindView(R.id.tv_font_size3)
    TextView tvFontSize3;
    @BindView(R.id.fsv_font_size)
    FontSizeView fsvFontSize;
    private float fontSizeScale;
    private boolean isChange;//用于监听字体大小是否有改动
    private int defaultPos;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_font_size;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.VISIBLE);
        ivTitleLeft.setVisibility(View.GONE);
        ivTitleRight.setVisibility(View.GONE);
        tvTitle.setText("调整字体");
    }

    @Override
    protected void initData() {
       //滑动返回监听
        fsvFontSize.setChangeCallbackListener(new FontSizeView.OnChangeCallbackListener() {
            @Override
            public void onChangeListener(int position) {
                int dimension = getResources().getDimensionPixelSize(R.dimen.sp_stander);
                //根据position 获取字体倍数
                fontSizeScale = (float) (0.875 + 0.125 * position);
                //放大后的sp单位
                double v = fontSizeScale * (int) DensityUtils.px2sp(ChangeFontSizeActivity.this, dimension);
                //改变当前页面大小
                changeTextSize((int) v);
                isChange = !(position==defaultPos);
            }
        });
        float  scale = (float) SPUtils.get(this, CommonUtils.SP_FontScale, 0.0f);
        if (scale > 0.5) {
            defaultPos = (int) ((scale - 0.875) / 0.125);
        } else {
            defaultPos=1;
        }
        //注意： 写在改变监听下面 —— 否则初始字体不会改变
        fsvFontSize.setDefaultPosition(defaultPos);
    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
    }

    /**
     * 改变textsize 大小
     */
    private void changeTextSize(int dimension) {
        tvFontSize1.setTextSize(dimension);
        tvFontSize2.setTextSize(dimension);
        tvFontSize3.setTextSize(dimension);
    }
    /**
     * 重新配置缩放系数
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale= 1;//1 设置正常字体大小的倍数
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.e("keyCode " +keyCode);
        LogUtils.e("event " +event.getAction());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                if(isChange){
                    SPUtils.put(ChangeFontSizeActivity.this,CommonUtils.SP_FontScale,fontSizeScale);
                    //重启应用
                    ActManager.getAppManager().finishAllActivity();
                    IntentUtils.toActivity(ChangeFontSizeActivity.this, MainActivity.class,true);
                }else{
                    finish();
                }
                break;
        }
    }
}
