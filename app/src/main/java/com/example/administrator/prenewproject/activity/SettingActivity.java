package com.example.administrator.prenewproject.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {


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
    @BindView(R.id.iv_bz)
    ImageView ivBz;
    @BindView(R.id.rl_change_font_size)
    RelativeLayout rlChangeFontSize;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.VISIBLE);
        ivTitleLeft.setVisibility(View.GONE);
        ivTitleRight.setVisibility(View.GONE);
        tvTitle.setText("设置");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
        rlChangeFontSize.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_change_font_size:
                startToActivity(ChangeFontSizeActivity.class);
                break;
        }


    }

}
