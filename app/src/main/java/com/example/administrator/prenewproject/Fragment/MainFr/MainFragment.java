package com.example.administrator.prenewproject.Fragment.MainFr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.MainActivity;
import com.example.administrator.prenewproject.activity.SearchActivity;
import com.example.administrator.prenewproject.base.BaseFragment;
import com.example.administrator.prenewproject.view.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author maguotang
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @BindView(R.id.fm_content)
    FrameLayout fmContent;
    @BindView(R.id.bottom_bar_main)
    BottomBar bottomBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        setBottomBar();
    }

    @Override
    protected void setListener(View view) {
        ivTitleLeft.setOnClickListener(this);
        ivTitleRight.setOnClickListener(this);
    }


    private void setBottomBar() {
        bottomBar.setContainer(R.id.fm_content)
                .setTitleBeforeAndAfterColor("#999999", "#999999")
                .addItem(HomeFragment.class,
                        "首页",
                        R.drawable.home_select_s,
                        R.drawable.home_select_n)
                .addItem(KnowLedgeFragment.class,
                        "知识体系",
                        R.drawable.ic_kbow_s,
                        R.drawable.ic_know_n)
                .addItem(PublicNumberFragment.class,
                        "公众号",
                        R.drawable.ic_public_num_s,
                        R.drawable.ic_public_num_n)
                .addItem(NavigationFragment.class,
                        "导航",
                        R.drawable.ic_navigation_s,
                        R.drawable.ic_navigation_n)
                .addItem(PojectFragment.class,
                        "项目",
                        R.drawable.ic_project_s,
                        R.drawable.ic_project_n)
                .build();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.showLeftPage();
                break;
            case R.id.iv_title_right:

                startToActivity(SearchActivity.class);

                break;
            default:


        }
    }
}
