package com.example.administrator.prenewproject.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.prenewproject.R;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

//微信
public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxcallback);
    }
}
