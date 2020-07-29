package com.example.administrator.prenewproject.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.prenewproject.Fragment.PubNumFr.PubNumFragment1;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.adapter.TitleFragmentPagerAdapter;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.bean.KnowLedgeBean;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.view.MyPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 知识体系详情
 *
 * @author maguotang
 */
public class KnowLedgeDetailsActivity extends BaseActivity implements View.OnClickListener {
    List<KnowLedgeBean.DataBean.ChildrenBean> childDatas;
    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @BindView(R.id.iv_three_point)
    ImageView ivThreePoint;
    @BindView(R.id.pub_num_tab)
    TabLayout pubNumTab;
    @BindView(R.id.pub_num_vp)
    ViewPager pubNumVp;


    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.ll_know)
    LinearLayout llKnow;

    private String[] strings = new String[999];

    private List<Integer> dataIDs = new ArrayList<>();
    private String title;
    private MyPopupWindow myPopupWindow;
    private TextView tv_share;
    private TextView tv_collect;
    private TextView tv_open_browser;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_know_ledge_details;
    }

    @Override
    protected void initView() {
        childDatas = (List<KnowLedgeBean.DataBean.ChildrenBean>) getIntent().getSerializableExtra("data");
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        ivTitleLeft.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivTitleRight.setVisibility(View.GONE);
        ivThreePoint.setVisibility(View.VISIBLE);
        setTabName();
    }

    @Override
    protected void initData() {

        //设置tab可以滚动
        pubNumTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        pubNumTab.setupWithViewPager(pubNumVp);
        initPopu();
    }

    private void initPopu() {

        myPopupWindow = new MyPopupWindow(this, R.layout.popupwindow_one) {
            @Override
            protected void initView(View view) {
                tv_share = view.findViewById(R.id.tv_share);
                tv_collect = view.findViewById(R.id.tv_collect);
                tv_open_browser = view.findViewById(R.id.tv_open_browser);
                tv_collect.setVisibility(View.GONE);
                tv_open_browser.setVisibility(View.GONE);
            }
        };

    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
        ivThreePoint.setOnClickListener(this);
        tv_share.setOnClickListener(this);
    }

    private void setTabName() {
        for (int i = 0; i < childDatas.size(); i++) {
            strings[i] = childDatas.get(i).getName();
            dataIDs.add(childDatas.get(i).getId());
        }

        for (int i = 0; i < childDatas.size(); i++) {
            PubNumFragment1 pubNumFragment1 = new PubNumFragment1(i);
            Bundle bundle = new Bundle();
            bundle.putString("data", childDatas.get(i).getId() + "");
            //数据传递到fragment
            pubNumFragment1.setArguments(bundle);
            fragments.add(pubNumFragment1);
        }

        pubNumVp.setOffscreenPageLimit(fragments.size());

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), fragments, strings);
        pubNumVp.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_three_point:
                myPopupWindow.showAtLocation(llKnow, Gravity.RIGHT | Gravity.TOP, 30, 30);
                break;
            case R.id.tv_share:

                shareMore();
//                ToastUtil.show("分享");
                myPopupWindow.popuDiss();
                break;
            default:
        }
    }

    private void shareMore() {
        UMWeb web = new UMWeb("https://developer.umeng.com/sdk/android");
        web.setTitle("This is music title");//标题
//        web.setThumb(thumb);  //缩略图
        web.setDescription("my description");//描述

        new ShareAction(KnowLedgeDetailsActivity.this).withMedia(web).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();


    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.show("成功了");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.show("失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.show("取消了");

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
