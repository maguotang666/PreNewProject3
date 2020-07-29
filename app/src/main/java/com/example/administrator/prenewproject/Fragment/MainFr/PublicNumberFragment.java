package com.example.administrator.prenewproject.Fragment.MainFr;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.administrator.prenewproject.Fragment.PubNumFr.PubNumFragment1;
import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.adapter.TitleFragmentPagerAdapter;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.bean.PubNumTabBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.DialogUtils;
import com.example.administrator.prenewproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 公众号
 * @author maguotang
 */
public class PublicNumberFragment extends BaseFragment {


    @BindView(R.id.pub_num_tab)
    TabLayout pubNumTab;
    @BindView(R.id.pub_num_vp)
    ViewPager pubNumVp;
    List<Fragment> fragments = new ArrayList<>();

    private List<PubNumTabBean.DataBean> pubNumTabDatas = new ArrayList<>();

    private String[] strings = new String[20];

    private List<Integer> dataIDs = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_public_number;
    }

    @Override
    protected void initView(View view) {



    }

    @Override
    protected void initData() {


        getPubNumData();

        //设置tab可以滚动
        pubNumTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        pubNumTab.setupWithViewPager(pubNumVp);
        pubNumTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.e("tab---"+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getPubNumData() {

        DialogUtils.getInstance().showDialog();

        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_PUB_NUM_TAB, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                DialogUtils.getInstance().dissDialog();
                pubNumTabDatas.clear();
                fragments.clear();
                PubNumTabBean pubNumTabBean = CommonUtils.getData(result, PubNumTabBean.class);
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
            fragments.add(new PubNumFragment1(i));
        }

        setPullData2(dataIDs);

        pubNumVp.setOffscreenPageLimit(fragments.size());

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getFragmentManager(), fragments, strings);
        pubNumVp.setAdapter(adapter);
    }

    @Override
    protected void setListener(View view) {

    }

}
