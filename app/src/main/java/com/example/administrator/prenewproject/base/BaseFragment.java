package com.example.administrator.prenewproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.utils.CustomProgressDialog;
import com.google.gson.Gson;
;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;



/**
 * 基础类
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    private Unbinder unBind;
    protected CustomProgressDialog dialog;
    public Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            try {
                throw new Exception("layout is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dialog = new CustomProgressDialog(getActivity(), "正在加载...", R.drawable.loading);
        unBind = ButterKnife.bind(this, rootView);
        gson=new Gson();
        initView(rootView);
        initData();
        setListener(rootView);
        return rootView;
    }

    /**
     * 返回当前界面布局文件
     */
    protected abstract int getLayoutId();

    /**
     * 此方法描述的是： 初始化所有view
     */
    protected abstract void initView(View view);

    /**
     * 此方法描述的是： 初始化所有数据的方法
     */
    protected abstract void initData();

    /**
     * 此方法描述的是： 设置所有事件监听
     */
    protected abstract void setListener(View view);

//
//    /**
//     * 显示toast
//     *
//     * @param resId
//     */
//    public void showToast(final int resId) {
//        showToast(getString(resId));
//    }


    public <T extends View> T obtainView(int resId) {
        return (T) rootView.findViewById(resId);
    }


    protected void startToActivity(Class<?> cla){
        Intent intent=new Intent(getActivity(),cla);
        startActivity(intent);
    }

    private static Object[] pushData;

    private static List<Integer> pushData2=new ArrayList<>();

    public <T extends Object> T getPushData(int position) {
        return isPushEmpty(position) ? (T) pushData[position] : null;
    }

    public Integer getPushData2(int position) {
        return isPushEmpty2(position) ? pushData2.get(position) : null;
    }

    private boolean isPushEmpty2(int position) {
        return pushData2 != null && pushData2.size() > position;
    }


    private boolean isPushEmpty(int position) {
        return pushData != null && pushData.length > position;
    }


    public void setPullData2(List<Integer> data) {
        pushData2.clear();

        if (pushData2 != null) {
            pushData2.addAll(data) ;
        }
    }

    public void setPullData(Object... data) {
        pushData = null;
//        pushData.clear();
        pushData=data;

//        if (pushData != null) {
//            pushData.addAll(data) ;
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unBind.unbind();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
