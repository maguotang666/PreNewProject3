package com.example.administrator.prenewproject.Fragment.MainFr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.administrator.prenewproject.adapter.Navigation1Adapter;
import com.example.administrator.prenewproject.adapter.Navigation2Adapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.Navigation1Bean;
import com.example.administrator.prenewproject.bean.NavigationBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.view.CenterLayoutManager;
import com.example.administrator.prenewproject.view.SpacesItemDecoration;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 导航
 *
 * @author Administrator
 */
public class NavigationFragment extends BaseFragment {
    @BindView(R.id.rv_navigation1)
    RecyclerView rvNavigation1;
    @BindView(R.id.rv_navigation2)
    RecyclerView rvNavigation2;
    private Navigation1Adapter navigation1Adapter;
    private Navigation2Adapter navigation2Adapter;

    private List<Navigation1Bean> names = new ArrayList<>();
    private List<NavigationBean.DataBean> navigationDatas = new ArrayList<>();
    private CenterLayoutManager layoutManager1;
    private LinearLayoutManager layoutManager2;
    /**
     * 用户点击的分类在rv的位置
     */
    private int mIndex = 0;
    /**
     * rv是否需要第二次滚动
     */
    private boolean move = false;
    private RecyclerView.State state;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView(View view) {
        state=new RecyclerView.State();
    }

    @Override
    protected void initData() {
        navigation1Adapter = new Navigation1Adapter(getActivity());
        navigation2Adapter = new Navigation2Adapter(getActivity());


        layoutManager1 = new CenterLayoutManager(getActivity());
        rvNavigation1.setLayoutManager(layoutManager1);//设置布局管理器，这里选择用竖直的列表

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getActivity(), layoutManager1
                .getOrientation(), false, 1);

        rvNavigation1.addItemDecoration(itemDecoration);

        rvNavigation1.setAdapter(navigation1Adapter);
        layoutManager2 = new LinearLayoutManager(getActivity());
        rvNavigation2.setLayoutManager(layoutManager2);//设置布局管理器，这里选择用竖直的列表

        rvNavigation2.setAdapter(navigation2Adapter);

        getNetWorkData();


        navigation1Adapter.setonNavi1ItemClickListener(new Navigation1Adapter.OnNavi1ItemClickListener() {
            @Override
            public void selectPos(int pos) {
//                mIndex=pos;
//                moveToPosition(pos);

                layoutManager2.scrollToPositionWithOffset(pos, 0);
                layoutManager2.setStackFromEnd(true);

            }
        });
        rvNavigation2.addOnScrollListener(new RecyclerViewListener());
        navigation2Adapter.setOnItemClickListener(new Navigation2Adapter.OnItemSelectListener() {
            @Override
            public void onItemSelect(NavigationBean.DataBean.ArticlesBean data) {
                Intent intent=new Intent(getActivity(),WebActivity.class);
                intent.putExtra("title",data.getTitle());
                intent.putExtra("link",data.getLink());
                startActivity(intent);

            }
        });

    }

    private void getNetWorkData() {
        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_NAVIGATION_URL, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                NavigationBean navigationBean = CommonUtils.getData(result, NavigationBean.class);
                names.clear();
                navigationDatas.clear();
                if (navigationBean.getData() != null) {

                    navigationDatas.addAll(navigationBean.getData());

                    for (int i = 0; i < navigationDatas.size(); i++) {
                        if (i == 0) {
                            names.add(new Navigation1Bean(navigationDatas.get(i).getName(), true));
                        } else {
                            names.add(new Navigation1Bean(navigationDatas.get(i).getName(), false));
                        }


                    }

                    navigation2Adapter.setNavigation2Data(navigationDatas);
                    navigation1Adapter.setNavigation1Data(names);
                }

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {

            }
        });
    }

    @Override
    protected void setListener(View view) {

    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);


        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState==0){
                //在这里进行第二次滚动（最后的距离）

                //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                int n = layoutManager2.findFirstVisibleItemPosition();
                if (mIndex != n) {
//                layoutManager1.scrollToPositionWithOffset(n,0);
//                layoutManager1.setStackFromEnd(true);
                    layoutManager1.smoothScrollToPosition(recyclerView,state,n);
                    navigation1Adapter.setSelectPos(n);
                    mIndex = n;
                    layoutManager2.scrollToPositionWithOffset(n, 0);
                    layoutManager2.setStackFromEnd(true);
                }
            }
            LogUtils.e("newState-----"+newState);

        }
    }


}
