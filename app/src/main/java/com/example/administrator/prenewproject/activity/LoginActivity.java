package com.example.administrator.prenewproject.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.prenewproject.MyApplication;
import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.bean.LoginBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.PermissionsLogUtils;
import com.example.administrator.prenewproject.utils.SharedPreferencesUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.view.CircleImageView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author maguotang
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.email)
    AutoCompleteTextView email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.email_sign_in_button)
    Button emailSignInButton;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.ag_password)
    EditText agPassword;
    @BindView(R.id.til_ag)
    TextInputLayout tilAg;
    @BindView(R.id.ll_login_wechat)
    LinearLayout llLoginWechat;
    @BindView(R.id.ll_login_qq)
    LinearLayout llLoginQq;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
//        email.setText("999999999999");
        detectionAuthority();
    }

    @Override
    protected void setListener() {
        emailSignInButton.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        llLoginQq.setOnClickListener(this);
        llLoginWechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:

                if (tilAg.getVisibility() == View.VISIBLE) {

                    if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())
                            && !TextUtils.isEmpty(agPassword.getText().toString())) {
                        Register();
                    } else {
                        ToastUtil.show("账号或密码不能为空");
                    }

                } else {

                    if (!TextUtils.isEmpty(email.getText().toString())
                            && !TextUtils.isEmpty(password.getText().toString())) {
                        login();
                    } else {
                        ToastUtil.show("账号或密码不能为空");
                    }

                }

                break;
            case R.id.tv_register:

                showRegister();

                break;
            case R.id.ll_login_wechat:
                loginWechat();
                break;
            case R.id.ll_login_qq:
                loginQQ();

                break;
            default:
        }
    }
    private void loginQQ() {

        UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);

    }

    private void loginWechat() {

        UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);

    }

    private void login() {

        Map<String, String> map = new HashMap<>();
        map.put("username", email.getText().toString());
        map.put("password", password.getText().toString());

        ServiceShell.postLoginKey(AppNetConfig.getInstance().DATA_LOGIN, map, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                LoginBean loginBean = CommonUtils.getData(result, LoginBean.class);
                if (loginBean.getData() != null) {

                    SharedPreferencesUtils.saveString(MyApplication.userName, email.getText().toString());
                    SharedPreferencesUtils.saveString(MyApplication.password, password.getText().toString());

                    startToActivity(MainActivity.class);
                    finish();
                } else {
                    ToastUtil.show(loginBean.getErrorMsg());
                }
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {


            }
        });

    }

    private void Register() {
        Map<String, String> map = new HashMap<>();
        map.put("username", email.getText().toString());
        map.put("password", password.getText().toString());
        map.put("repassword", agPassword.getText().toString());

        ServiceShell.postObiectKey(AppNetConfig.getInstance().DATA_REGISTER, map, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                LoginBean loginBean = CommonUtils.getData(result, LoginBean.class);
                if (loginBean.getData() != null) {

                    SharedPreferencesUtils.saveString(MyApplication.userName, email.getText().toString());
                    SharedPreferencesUtils.saveString(MyApplication.password, password.getText().toString());

                    startToActivity(MainActivity.class);
                    finish();
                } else {
                    ToastUtil.show(loginBean.getErrorMsg());
                }
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {


            }
        });

    }

    private void showRegister() {

        if (tilAg.getVisibility() == View.VISIBLE) {
            tilAg.setVisibility(View.GONE);
            emailSignInButton.setText("登陆");
            tvRegister.setText("没有账户?去注册");
        } else {
            tilAg.setVisibility(View.VISIBLE);
            emailSignInButton.setText("注册");
            tvRegister.setText("已有账户?去登陆");
        }

    }


    //获取权限
    private void detectionAuthority() {


        if (Build.VERSION.SDK_INT < 23) {
            return;
        }

        if (!PermissionsLogUtils.isAppliedPermission(MyApplication.mContext, Manifest.permission.ACCESS_FINE_LOCATION) ||
                !PermissionsLogUtils.isAppliedPermission(MyApplication.mContext, Manifest.permission.RECORD_AUDIO) ||
                !PermissionsLogUtils.isAppliedPermission(MyApplication.mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !PermissionsLogUtils.isAppliedPermission(MyApplication.mContext, Manifest.permission.CAMERA) ||
                !PermissionsLogUtils.isAppliedPermission(MyApplication.mContext, Manifest.permission.CALL_PHONE) ||
                !PermissionsLogUtils.isAppliedPermission(MyApplication.mContext, Manifest.permission.READ_PHONE_STATE)) {

            EasyPermissions.requestPermissions(this,
                    "你可能需要一些权限才能开始工作哦!",
                    R.string.postive_button,
                    R.string.negative_button,
                    1,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE);
        }

    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(mContext, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
