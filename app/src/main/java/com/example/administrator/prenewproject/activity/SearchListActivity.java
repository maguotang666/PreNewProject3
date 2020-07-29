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
import com.example.administrator.prenewproject.adapter.ProjectTabAdapter;
import com.example.administrator.prenewproject.adapter.SearchListAdapter;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.bean.SearchListBean;
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
 * 搜索列表页面
 * @author maguotang
 */
public class SearchListActivity extends BaseActivity {


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
    @BindView(R.id.main_rv)
    RecyclerView       mainRv;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout srlMain;
    private SearchListAdapter searchListAdapter;
    private String searchTitle="";
    private int index=0;
    private int indexPager=20;
    private List<SearchListBean.DataBean.DatasBean> searchDatas=new ArrayList<>();
    public int mLastVisibleItemPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void initView() {
        searchTitle=getIntent().getStringExtra("title");
        tvTitle.setText(searchTitle);
        ivBack.setVisibility(View.VISIBLE);
        ivTitleLeft.setVisibility(View.GONE);
        ivTitleRight.setVisibility(View.GONE);
    }


    public RecyclerView.OnScrollListener monScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            if (searchDatas.size() < index+1 * indexPager) {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (searchListAdapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == searchListAdapter.getItemCount()) {
                    //发送网络请求获取更多数据
                    index++;
                    getNetWorkData();
                }
            }

        }
    };



    @Override
    protected void initData() {

        setSwip();
        searchListAdapter=new SearchListAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRv.setLayoutManager(layoutManager);//设置布局管理器，这里选择用竖直的列表

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(this, layoutManager
                .getOrientation(), false, 1);

        mainRv.addItemDecoration(itemDecoration);


        mainRv.setAdapter(searchListAdapter);

        mainRv.addOnScrollListener(monScrollListener);

        searchListAdapter.setOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent=new Intent(SearchListActivity.this,WebActivity.class);
                intent.putExtra("title",searchDatas.get(pos).getTitle());
                intent.putExtra("link",searchDatas.get(pos).getLink());
                startActivity(intent);
            }
        });



        getNetWorkData();
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
                        searchDatas.clear();
                        getNetWorkData();
                    }
                }
        );
    }
    private void getNetWorkData() {
        String path=AppNetConfig.getInstance().DATA_SEARCH+index+AppNetConfig.getInstance().DATA_PUB_NUM_LIST2;
        Map<String,String> map=new HashMap<>();
        map.put("k",searchTitle);

        ServiceShell.postObiectKey(path, map, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                if (srlMain.isRefreshing()) {
                    srlMain.setRefreshing(false);
                }
                SearchListBean searchListBean= CommonUtils.getData(result,SearchListBean.class);

                searchDatas.addAll(searchListBean.getData().getDatas());


                if (searchDatas.size() < (index+1) * indexPager) {

                    if (searchDatas.size() < indexPager) {
                        searchListAdapter.isHaveMoreData(1);
                    } else {
                        searchListAdapter.isHaveMoreData(3);
                    }
                } else if (searchDatas.size() == (index+1) * indexPager) {
                    searchListAdapter.isHaveMoreData(2);
                }

                searchListAdapter.setPubData(searchDatas);

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {
                LogUtils.e("onDisSuccess---"+e.getMessage());
                ToastUtil.show("onDisSuccess---"+e.getMessage());
            }
        });

    }

    @Override
    protected void setListener() {

    }


}
