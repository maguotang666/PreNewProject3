package com.example.administrator.prenewproject.Fragment.PubNumFr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.WebActivity;
import com.example.administrator.prenewproject.adapter.PubNumAdapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.PubNumListBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.DialogUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 弘扬
 *
 * @author Administrator
 */
@SuppressLint("ValidFragment")
public class PubNumFragment1 extends BaseFragment {


    public int mLastVisibleItemPosition;
    @BindView(R.id.rv_pub_num1)
    RecyclerView       rvPubNum1;
    @BindView(R.id.srl_pub_num1)
    SwipeRefreshLayout srlPubNum1;
    private PubNumAdapter                           pubNumAdapter;
    private String                                  tabID="";
    private int                                     tabIDIndex        = 0;
    private int                                     urlIndex          = 1;
    private PubNumListBean                          pubNumListBean;
    private List<PubNumListBean.DataBean.DatasBean> pubNumDatas       = new ArrayList<>();
    public  RecyclerView.OnScrollListener           monScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            if (pubNumDatas.size() < urlIndex * 20) {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (pubNumAdapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == pubNumAdapter.getItemCount()) {
                    //发送网络请求获取更多数据
                    urlIndex++;
                    getNerWorkData();
                }
            }

        }
    };


    @SuppressLint("ValidFragment")
    public PubNumFragment1(int pos) {
        super();
        tabIDIndex = pos;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pub_num_fragment1;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        setSwip();

        Bundle bundle = this.getArguments();//得到从Activity传来的数据
        if (bundle != null) {
            tabID = bundle.getString("data");
        }
        if (TextUtils.isEmpty(tabID)){
            tabID = getPushData2(tabIDIndex) + "";
        }

        pubNumAdapter = new PubNumAdapter(getActivity());


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPubNum1.setLayoutManager(layoutManager);//设置布局管理器，这里选择用竖直的列表

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getActivity(), layoutManager
                .getOrientation(), false, 1);

        rvPubNum1.addItemDecoration(itemDecoration);


        rvPubNum1.setAdapter(pubNumAdapter);

        rvPubNum1.addOnScrollListener(monScrollListener);


        if (getUserVisibleHint() && pubNumListBean == null) {
            getNerWorkData();
        }

        pubNumAdapter.setOnItemClickListener(new PubNumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent=new Intent(getActivity(),WebActivity.class);
                intent.putExtra("title",pubNumDatas.get(pos).getTitle());
                intent.putExtra("link",pubNumDatas.get(pos).getLink());
                startActivity(intent);

            }
        });

    }

    private void setSwip() {
        CommonUtils.getInstance().initSwipeRefresh(srlPubNum1);

        /*
         * 设置手势下拉刷新的监听
         */
        srlPubNum1.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.e("TAG", "开始刷新了");
                        urlIndex = 1;
                        pubNumDatas.clear();
                        getNerWorkData();
                    }
                }
        );
    }

    private void getNerWorkData() {

        if (TextUtils.isEmpty(tabID)){
            return;
        }

        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_PUB_NUM_LIST1 + tabID + "/" + urlIndex + AppNetConfig.getInstance().DATA_PUB_NUM_LIST2, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {

                if (srlPubNum1.isRefreshing()) {
                    srlPubNum1.setRefreshing(false);
                }

                pubNumListBean = CommonUtils.getData(result, PubNumListBean.class);

                pubNumDatas.addAll(pubNumListBean.getData().getDatas());

                if (pubNumDatas.size() < urlIndex * 20) {

                    if (pubNumDatas.size() < 20) {
                        pubNumAdapter.isHaveMoreData(1);
                    } else {
                        pubNumAdapter.isHaveMoreData(3);
                    }
                } else if (pubNumDatas.size() == urlIndex * 20) {
                    pubNumAdapter.isHaveMoreData(2);
                }

                pubNumAdapter.setPubData(pubNumDatas);
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
            }
        });
    }

    @Override
    protected void setListener(View view) {


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            getNerWorkData();
        }

        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().onBackPressed();

    }
}
