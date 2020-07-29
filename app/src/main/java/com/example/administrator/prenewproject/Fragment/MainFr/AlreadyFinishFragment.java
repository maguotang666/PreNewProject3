package com.example.administrator.prenewproject.Fragment.MainFr;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.example.administrator.prenewproject.adapter.ToDoAdapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.BackLogToDoBean;
import com.example.administrator.prenewproject.bean.ToDoBean;
import com.example.administrator.prenewproject.listener.slideswaphelper.PlusItemSlideCallback;
import com.example.administrator.prenewproject.listener.slideswaphelper.WItemTouchHelperPlus;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * 已完成页面
 *
 * @author maguotang
 */
public class AlreadyFinishFragment extends BaseFragment {


    @BindView(R.id.alrea_finish_rv)
    RecyclerView       alreaFinishRv;
    @BindView(R.id.srl_alrea_finish)
    SwipeRefreshLayout srlAlreaFinish;
    private ToDoAdapter                              toDoAdapter;
    private int                                      index             = 0;
    private int                                      indexPager        = 20;
    private int                                      todo_type         = 0;
    public  int                                      mLastVisibleItemPosition;
    private List<BackLogToDoBean.DataBean.DatasBean> todoDatas         = new ArrayList<>();
    private DynamicReceiver                          dynamicReceiver;
    public  RecyclerView.OnScrollListener            monScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            if (todoDatas.size() < (index + 1) * 20) {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (toDoAdapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == toDoAdapter.getItemCount()) {
                    //发送网络请求获取更多数据
                    index++;
                    getNetWorkData();
                }
            }

        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_already_finish;
    }

    @Override
    protected void initView(View view) {
//实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("send_todo");
        dynamicReceiver = new DynamicReceiver();
        //注册广播接收
        getActivity().registerReceiver(dynamicReceiver, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unregisterReceiver(dynamicReceiver);

    }

    @Override
    protected void initData() {
        setSwip();
        toDoAdapter = new ToDoAdapter(getActivity(), 0);

        //设置布局管理器，这里选择用竖直的列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        alreaFinishRv.setLayoutManager(layoutManager);

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getActivity(), layoutManager
                .getOrientation(), false, 1);

        alreaFinishRv.addItemDecoration(itemDecoration);

        alreaFinishRv.addOnScrollListener(monScrollListener);
        alreaFinishRv.setAdapter(toDoAdapter);

        toDoAdapter.setOnItemClickListener(new ToDoAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(int pos) {

            }

            @Override
            public void workFinish(int pos) {

                todoDatas.remove(pos);
                if (todoDatas.size() == 0) {
                    toDoAdapter.notifyDataSetChanged();
                    return;
                }
                //通知移除该条
                toDoAdapter.notifyItemRemoved(pos);
                //更新适配器这条后面列表的变化
                toDoAdapter.notifyItemRangeChanged(pos, todoDatas.size() - pos);


            }

            @Override
            public void workDelect(int pos) {
                todoDatas.remove(pos);
                if (todoDatas.size() == 0) {
                    toDoAdapter.notifyDataSetChanged();
                    return;
                }
                //通知移除该条
                toDoAdapter.notifyItemRemoved(pos);
                //更新适配器这条后面列表的变化
                toDoAdapter.notifyItemRangeChanged(pos, todoDatas.size() - pos);
            }
        });

        //作为一个ItemDecoration 写入的
        PlusItemSlideCallback callback = new PlusItemSlideCallback();
        callback.setType(WItemTouchHelperPlus.SLIDE_ITEM_TYPE_ITEMVIEW);
        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
        extension.attachToRecyclerView(alreaFinishRv);

        getNetWorkData();
    }

    @Override
    protected void setListener(View view) {

    }

    private void getNetWorkData() {

        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_ALREADY_TODO_LIST + todo_type + AppNetConfig.getInstance().DATA_NOT_AND_ALREADY_TODO_LIST + index, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                if (srlAlreaFinish.isRefreshing()) {
                    srlAlreaFinish.setRefreshing(false);
                }

                BackLogToDoBean toDoBean = CommonUtils.getData(result, BackLogToDoBean.class);
                todoDatas.addAll(toDoBean.getData().getDatas());

                if (toDoBean.getData().getDatas().size() < (index + 1) * 20) {

                    if (toDoBean.getData().getDatas().size() < 20) {
                        toDoAdapter.isHaveMoreData(1);
                    } else {
                        toDoAdapter.isHaveMoreData(3);
                    }
                } else if (toDoBean.getData().getDatas().size() == (index + 1) * 20) {
                    toDoAdapter.isHaveMoreData(2);
                }

                toDoAdapter.setToDoData(todoDatas);
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                if (srlAlreaFinish.isRefreshing()) {
                    srlAlreaFinish.setRefreshing(false);
                }
                ToastUtil.show("onDisSuccess---" + e.getMessage());
            }
        });
    }

    private void setSwip() {

        CommonUtils.getInstance().initSwipeRefresh(srlAlreaFinish);

        /*
         * 设置手势下拉刷新的监听
         */
        srlAlreaFinish.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.e("TAG", "开始刷新了");
                        index = 0;
                        todoDatas.clear();
                        getNetWorkData();
                    }
                }
        );

    }

    private class DynamicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("send_todo")) {
                todo_type = intent.getIntExtra("todo_type", 0);
                index = 0;
                todoDatas.clear();
                getNetWorkData();
            }

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        LogUtils.e("hidden---222--"+hidden);

        if (!hidden) {
            todoDatas.clear();
            getNetWorkData();
        }
    }




}
