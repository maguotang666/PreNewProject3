package com.example.administrator.prenewproject.Fragment.MainFr;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.prenewproject.Fragment.PubNumFr.ProjectFragment1;
import com.example.administrator.prenewproject.Fragment.PubNumFr.PubNumFragment1;
import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.adapter.ProjectTabAdapter;
import com.example.administrator.prenewproject.adapter.TitleFragmentPagerAdapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.PubNumTabBean;
import com.example.administrator.prenewproject.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 项目
 */
public class PojectFragment extends BaseFragment {

    @BindView(R.id.project_tab)
    TabLayout projectTab;
    @BindView(R.id.project_vp)
    ViewPager projectVp;

    private List<Fragment> projectFrs=new ArrayList<>();

    private List<PubNumTabBean.DataBean> pubNumTabDatas = new ArrayList<>();
    private String[] strings = new String[999];

    private List<Integer> dataIDs = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_poject;
    }

    @Override
    protected void initView(View view) {


    }

    @Override
    protected void initData() {


        getProjectTabs();


        //设置tab可以滚动
        projectTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        projectTab.setupWithViewPager(projectVp);



    }

    private void getProjectTabs() {


        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_PROJECT_TAB, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {

                PubNumTabBean pubNumTabBean = CommonUtils.getData(result, PubNumTabBean.class);
                projectFrs.clear();
                pubNumTabDatas.clear();
                assert pubNumTabBean != null;
                pubNumTabDatas.addAll(pubNumTabBean.getData());
                setTabName();

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {

            }
        });

    }

    private void setTabName() {


        for (int i = 0; i < pubNumTabDatas.size(); i++) {
            strings[i] = pubNumTabDatas.get(i).getName();
            dataIDs.add(pubNumTabDatas.get(i).getId());
        }

        for (int i = 0; i < pubNumTabDatas.size(); i++) {
            projectFrs.add(new ProjectFragment1(i));
        }


        setPullData2(dataIDs);


        projectVp.setOffscreenPageLimit(projectFrs.size());

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getFragmentManager(), projectFrs, strings);
        projectVp.setAdapter(adapter);

    }

    @Override
    protected void setListener(View view) {

    }


}
