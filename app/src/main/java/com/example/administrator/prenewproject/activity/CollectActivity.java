package com.example.administrator.prenewproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.adapter.CollectAdapter;
import com.example.administrator.prenewproject.adapter.HomeListAdapter;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.bean.CollectBean;
import com.example.administrator.prenewproject.bean.HomeListBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 收藏页面
 *
 * @author maguotang
 */
public class CollectActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_left)
    ImageView          ivTitleLeft;
    @BindView(R.id.iv_back)
    ImageView          ivBack;
    @BindView(R.id.tv_title)
    TextView           tvTitle;
    @BindView(R.id.et_title)
    EditText           etTitle;
    @BindView(R.id.iv_title_right)
    ImageView          ivTitleRight;
    @BindView(R.id.iv_three_point)
    ImageView          ivThreePoint;
    @BindView(R.id.rv_collect)
    RecyclerView       rvCollect;
    @BindView(R.id.srl_collect)
    SwipeRefreshLayout srlCollect;
    private int index = 0;

    private CollectAdapter                        collectAdapter;
    private CollectActivity                       collectActivity;
    private List<CollectBean.DataBean.DatasBean> homeDatas = new ArrayList<>();
    public  int                                   mLastVisibleItemPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }


    public RecyclerView.OnScrollListener monScrollListener = new RecyclerView.OnScrollListener() {
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
            if (collectAdapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == collectAdapter.getItemCount()) {
                    //发送网络请求获取更多数据
                    index++;
                    getNerWorkData();
                }
            }

        }
    };


    @Override
    protected void initView() {
        tvTitle.setText("收藏");
        ivTitleLeft.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivTitleRight.setVisibility(View.GONE);

        collectActivity = this;

    }

    @Override
    protected void initData() {
        setSwip();
        collectAdapter = new CollectAdapter(this);
        //设置布局管理器，这里选择用竖直的列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCollect.setLayoutManager(layoutManager);

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(this, layoutManager
                .getOrientation(), false, 1);

        rvCollect.addItemDecoration(itemDecoration);

        rvCollect.setAdapter(collectAdapter);

        getNerWorkData();

        rvCollect.addOnScrollListener(monScrollListener);


        collectAdapter.setOnItemListener(new CollectAdapter.OnItemListener() {
            @Override
            public void setOnListener(int pos) {

                Intent intent = new Intent(collectActivity, WebActivity.class);
                intent.putExtra("title", homeDatas.get(pos).getTitle());
                intent.putExtra("link", homeDatas.get(pos).getLink());
                startActivity(intent);

            }

            @Override
            public void setCancelCollect(int position) {
                LogUtils.e("collActivity-----"+position);
                cancelCollect(position);

            }
        });

    }

    private void cancelCollect(final int position) {

        Map<String,String> map=new HashMap<>();
        map.put("originId",homeDatas.get(position).getOriginId()+"");

        ServiceShell.postUncollectListNoKey(AppNetConfig.getInstance().DATA_MY_UNCOLLECT, homeDatas.get(position).getId(), map,new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                ToastUtil.showToMain("已取消收藏");
                homeDatas.remove(position);
                collectAdapter.unCollect(position);
            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                LogUtils.e("e---" + e.getMessage());
            }
        });
    }


    private void getNerWorkData() {

        ServiceShell.getHomeListNoKey(AppNetConfig.getInstance().DATA_COLLECT, index, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                if (srlCollect.isRefreshing()) {
                    srlCollect.setRefreshing(false);
                }

                CollectBean homeListBean = CommonUtils.getData(result, CollectBean.class);

                homeDatas.addAll(homeListBean.getData().getDatas());

                collectAdapter.setCollectData(homeDatas);

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                LogUtils.e("e----" + e.getMessage());
                ToastUtil.show("onDisSuccess---" + e.getMessage());
            }
        });

    }

    private void setSwip() {
        CommonUtils.getInstance().initSwipeRefresh(srlCollect);

        /*
         * 设置手势下拉刷新的监听
         */
        srlCollect.setOnRefreshListener(
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

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:

        }
    }
}
