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
import android.widget.Toast;

import com.example.administrator.prenewproject.MyApplication;
import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.WebActivity;
import com.example.administrator.prenewproject.adapter.HomeListAdapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.BannerBean;
import com.example.administrator.prenewproject.bean.HomeListBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.view.SpacesItemDecoration;

import org.litepal.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.main_rv)
    RecyclerView       mainRv;
//    @BindView(R.id.srl_main)
    SwipeRefreshLayout srlMain;
    private int                                   index             = 0;
    private HomeListAdapter                       homeListAdapter;
    public  int                                   mLastVisibleItemPosition;
    private List<HomeListBean.DataBean.DatasBean> homeDatas         = new ArrayList<>();
    private List<BannerBean.DataBean>             homeBanners;
    public  RecyclerView.OnScrollListener         monScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            if (homeDatas.size() < (index + 1) * 20) {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (homeListAdapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == homeListAdapter.getItemCount()) {
                    //发送网络请求获取更多数据
                    index++;
                    getNerWorkData();
                }
            }

        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        srlMain=view.findViewById(R.id.srl_main);
    }

    @Override
    protected void initData() {
        setSwip();

        homeListAdapter = new HomeListAdapter(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainRv.setLayoutManager(layoutManager);//设置布局管理器，这里选择用竖直的列表

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getActivity(), layoutManager
                .getOrientation(), false, 1);

        mainRv.addItemDecoration(itemDecoration);

        mainRv.setAdapter(homeListAdapter);

        getNerWorkData();

        mainRv.addOnScrollListener(monScrollListener);


        homeListAdapter.setOnItemListener(new HomeListAdapter.OnItemListener() {
            @Override
            public void setOnListener(int pos) {

                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", homeDatas.get(pos).getTitle());
                intent.putExtra("link", homeDatas.get(pos).getLink());
                startActivity(intent);

            }

            @Override
            public void setOnBannerListener(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", homeBanners.get(position).getTitle());
                intent.putExtra("link", homeBanners.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void setAddCollect(int position) {
                addCollectData(position);
            }

            @Override
            public void setCancelCollect(int position) {
                cancelCollect(position);
            }

        });


    }

    private void cancelCollect(final int position) {
        ServiceShell.postCollectListNoKey(AppNetConfig.getInstance().DATA_UNCOLLECT, homeDatas.get(position).getId(), new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                LogUtils.e("result---" + result);
                ToastUtil.showToMain("已取消收藏");
                homeDatas.get(position).setCollect(false);
                homeListAdapter.notifyItemChanged(position+1,0);
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                LogUtils.e("e---" + e.getMessage());
            }
        });
    }

    private void addCollectData(final int position) {

        ServiceShell.postCollectListNoKey(AppNetConfig.getInstance().DATA_ADD_COLLECT, homeDatas.get(position).getId(), new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                LogUtils.e("result---" + result);
                ToastUtil.showToMain("收藏成功");
                homeDatas.get(position).setCollect(true);
                homeListAdapter.notifyItemChanged(position+1,0);
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                LogUtils.e("e---" + e.getMessage());
            }
        });


    }

    @Override
    protected void setListener(View view) {

    }

    private void setSwip() {
        CommonUtils.getInstance().initSwipeRefresh(srlMain);

        /*
         * 设置手势下拉刷新的监听
         */
        srlMain.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.e("TAG", "开始刷新了");
                        index = 0;
                        homeDatas.clear();
                        getNerWorkData();
                    }
                }
        );
    }


    private void getNerWorkData() {

        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_BANNER, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                BannerBean bannerBean = CommonUtils.getData(result, BannerBean.class);
                homeBanners = bannerBean.getData();
                homeListAdapter.setMainBannerData(homeBanners);

                getNerWorkData2();

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                ToastUtil.show("onDisSuccess---" + e.getMessage());
            }
        });

    }

    private void getNerWorkData2() {
        ServiceShell.getHomeListNoKey(AppNetConfig.getInstance().DATA_HOME_LIST, index, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {

                if (srlMain.isRefreshing()) {
                    srlMain.setRefreshing(false);
                }

                HomeListBean homeListBean = CommonUtils.getData(result, HomeListBean.class);

                homeDatas.addAll(homeListBean.getData().getDatas());

                if (homeListBean.getData().getDatas().size() < (index + 1) * 20) {

                    if (homeListBean.getData().getDatas().size() < 20) {
                        homeListAdapter.isHaveMoreData(1);
                    } else {
                        homeListAdapter.isHaveMoreData(3);
                    }
                } else if (homeListBean.getData().getDatas().size() == (index + 1) * 20) {
                    homeListAdapter.isHaveMoreData(2);
                }


                homeListAdapter.setMainListData(homeDatas);

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                if (srlMain.isRefreshing()) {
                    srlMain.setRefreshing(false);
                }
                ToastUtil.show("onDisSuccess---" + e.getMessage());
            }
        });
    }


}
