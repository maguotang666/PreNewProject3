package com.example.administrator.prenewproject.Fragment.MainFr;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.MyApplication;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.CollectActivity;
import com.example.administrator.prenewproject.activity.SettingActivity;
import com.example.administrator.prenewproject.activity.ToDoActivity;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.manger.ActManager;
import com.example.administrator.prenewproject.utils.PromptDialog;
import com.example.administrator.prenewproject.utils.SharedPreferencesUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 左侧的页面
 *
 * @author maguotang
 */
public class LeftFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.rl_user_img)
    RelativeLayout rlUserImg;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_change_pattern)
    TextView tv_change_pattern;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.rl_tool)
    RelativeLayout rlTool;
    @BindView(R.id.rl_night)
    RelativeLayout rlNight;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_sign_out)
    RelativeLayout rlSignOut;
    private boolean isNightPattern;
    private PromptDialog promptDialog;
    private PromptDialog.DialogCallback dialogCallback = new PromptDialog.DialogCallback() {
        @Override
        public void positive(String positive) {
            ServiceShell.cleanCookies();
            SharedPreferencesUtils.saveString(MyApplication.userName, "");
            SharedPreferencesUtils.saveString(MyApplication.password, "");

            Objects.requireNonNull(getActivity()).finish();
        }

        @Override
        public void negative(String negative) {

        }

        @Override
        public void middleBtn(String middle) {

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.left_fragment;
    }

    @Override
    protected void initView(View view) {

        if (!TextUtils.isEmpty(SharedPreferencesUtils.getSting(MyApplication.userName))) {
            rlSignOut.setVisibility(View.VISIBLE);
            tvUserName.setText(SharedPreferencesUtils.getSting(MyApplication.userName));
        } else {
            rlSignOut.setVisibility(View.GONE);
        }

        if (SharedPreferencesUtils.getBoolean(MyApplication.NightPattern)) {
            isNightPattern = true;
        } else {
            isNightPattern = false;
        }

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void setListener(View view) {
        rlCollection.setOnClickListener(this);
        rlTool.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        rlNight.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        rlSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_collection:
                startToActivity(CollectActivity.class);
                break;
            case R.id.rl_night:
                if (isNightPattern) {
                    isNightPattern=false;
                    tv_change_pattern.setText("日间模式");
                } else {
                    isNightPattern=true;
                    tv_change_pattern.setText("夜间模式");
                }
                break;

            case R.id.rl_about:

                break;
            case R.id.rl_setting:
                startToActivity(SettingActivity.class);
                break;
            case R.id.rl_sign_out:

                if (promptDialog == null) {
                    promptDialog = new PromptDialog(getActivity());
                }
                promptDialog.setTitle("");
                promptDialog.dialogShow("确认退出登录?");
                promptDialog.setCallback(dialogCallback);

                break;
            case R.id.rl_tool:
                startToActivity(ToDoActivity.class);
                break;
            default:
        }
    }
}
