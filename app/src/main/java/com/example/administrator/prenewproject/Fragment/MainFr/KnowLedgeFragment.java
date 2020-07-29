package com.example.administrator.prenewproject.Fragment.MainFr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.prenewproject.MyApplication;
import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.KnowLedgeDetailsActivity;
import com.example.administrator.prenewproject.adapter.HomeListAdapter;
import com.example.administrator.prenewproject.adapter.KnowLedgeAdapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.KnowLedgeBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.view.SpacesItemDecoration;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 知识体系
 * @author maguotang
 */
public class KnowLedgeFragment extends BaseFragment {


    @BindView(R.id.rv_know)
    RecyclerView rvKnow;
    @BindView(R.id.srl_know)
    SwipeRefreshLayout srlKnow;

    private KnowLedgeAdapter knowLedgeAdapter;
    private List<KnowLedgeBean.DataBean> knowDatas;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know_ledge;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

        setSwip();

        knowLedgeAdapter = new KnowLedgeAdapter(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvKnow.setLayoutManager(layoutManager);//设置布局管理器，这里选择用竖直的列表

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getActivity(), layoutManager
                .getOrientation(), false, 1);

        rvKnow.addItemDecoration(itemDecoration);

        rvKnow.setAdapter(knowLedgeAdapter);
        knowLedgeAdapter.setOnItemClickListener(new KnowLedgeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent=new Intent(getActivity(),KnowLedgeDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",(Serializable)knowDatas.get(pos).getChildren());
                bundle.putString("title",knowDatas.get(pos).getName());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        getNerWorkData();

    }

    @Override
    protected void setListener(View view) {

    }

    private void setSwip() {
        CommonUtils.getInstance().initSwipeRefresh(srlKnow);

        /*
         * 设置手势下拉刷新的监听
         */
        srlKnow.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        LogUtils.e("开始刷新了");

                        getNerWorkData();
                    }
                }
        );
    }

    private void getNerWorkData() {
        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_KNOW, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                if (srlKnow.isRefreshing()){
                    srlKnow.setRefreshing(false);
                }

                KnowLedgeBean knowLedgeBean=CommonUtils.getData(result, KnowLedgeBean.class);
                knowDatas=knowLedgeBean.getData();
                knowLedgeAdapter.setKnowData(knowDatas);

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                if (srlKnow.isRefreshing()){
                    srlKnow.setRefreshing(false);
                }
                ToastUtil.show("网络出错");
            }
        });
    }
}
