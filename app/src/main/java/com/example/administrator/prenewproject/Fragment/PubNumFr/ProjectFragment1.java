package com.example.administrator.prenewproject.Fragment.PubNumFr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.WebActivity;
import com.example.administrator.prenewproject.adapter.ProjectTabAdapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.PubNumListBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 *
 */
@SuppressLint("ValidFragment")
public class ProjectFragment1 extends BaseFragment {

    public int mLastVisibleItemPosition;
    @BindView(R.id.rv_proj)
    RecyclerView rvProj;
    @BindView(R.id.srl_proj)
    SwipeRefreshLayout srlProj;
    private int tabID;
    private int tabIDIndex = 0;
    private int urlIndex = 1;
    private List<PubNumListBean.DataBean.DatasBean> pubNumDatas = new ArrayList<>();
    private int indexPager=15;
    private ProjectTabAdapter projectTabAdapter;
    private PubNumListBean pubNumListBean;

    @SuppressLint("ValidFragment")
    public ProjectFragment1(int pos) {
        super();
        tabIDIndex = pos;
    }

    public RecyclerView.OnScrollListener monScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            if (pubNumDatas.size() < urlIndex * indexPager) {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (projectTabAdapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == projectTabAdapter.getItemCount()) {
                    //发送网络请求获取更多数据
                    urlIndex++;
                    getNerWorkData();
                }
            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_fragment1;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

        setSwip();

        tabID = getPushData2(tabIDIndex);

        projectTabAdapter=new ProjectTabAdapter(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProj.setLayoutManager(layoutManager);//设置布局管理器，这里选择用竖直的列表

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getActivity(), layoutManager
                .getOrientation(), false, 1);

        rvProj.addItemDecoration(itemDecoration);


        rvProj.setAdapter(projectTabAdapter);

        rvProj.addOnScrollListener(monScrollListener);


        if (getUserVisibleHint()&&pubNumListBean==null){
            getNerWorkData();
        }

        projectTabAdapter.setOnItemClickListener(new ProjectTabAdapter.OnItemClickListener() {
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

        CommonUtils.getInstance().initSwipeRefresh(srlProj);

        /*
         * 设置手势下拉刷新的监听
         */
        srlProj.setOnRefreshListener(
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



    private void getNerWorkData() {

        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_PROJECT_LIST1 + urlIndex + AppNetConfig.getInstance().DATA_PROJECT_LIST2 + tabID, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                if (srlProj.isRefreshing()) {
                    srlProj.setRefreshing(false);
                }

                pubNumListBean = CommonUtils.getData(result, PubNumListBean.class);

                pubNumDatas.addAll(pubNumListBean.getData().getDatas());


                if (pubNumDatas.size() < urlIndex * indexPager) {

                    if (pubNumDatas.size() < indexPager) {
                        projectTabAdapter.isHaveMoreData(1);
                    } else {
                        projectTabAdapter.isHaveMoreData(3);
                    }
                } else if (pubNumDatas.size() == urlIndex * indexPager) {
                    projectTabAdapter.isHaveMoreData(2);
                }


                projectTabAdapter.setPubData(pubNumDatas);
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {

            }
        });


    }


}