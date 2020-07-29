package com.example.administrator.prenewproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.bean.BannerBean;
import com.example.administrator.prenewproject.bean.HomeListBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * @author maguotang
 */
public class HomeListAdapter extends RecyclerView.Adapter {

    private final int HOME_TYPE1 = 0;
    private final int HOME_TYPE2 = 1;
    private final int HOME_TYPE3 = 2;
    private Context mContext;

    private List<HomeListBean.DataBean.DatasBean> mainLists = new ArrayList<>();
    private List<BannerBean.DataBean> mainBannerDatas = new ArrayList<>();

    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    //加载更多显示与隐藏  1隐藏   2显示   3已经加载完毕
    private int FootisVisi=1;
    public HomeListAdapter(Context context) {
        mContext = context;
    }
    private OnItemListener onItemListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HOME_TYPE1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_banner_item, parent, false);

            return new HomeBannerHolder(view);
        } else if (viewType == HOME_TYPE3) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.foot_view, parent, false);
            return new FootViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_list_item, parent, false);

            return new HomeListHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case HOME_TYPE1:
                HomeBannerHolder homeBannerHolder = (HomeBannerHolder) holder;

                setBanner(homeBannerHolder);

                break;
            case HOME_TYPE2:
                final HomeListHolder homeListHolder = (HomeListHolder) holder;

                if (mainLists.get(position - 1).isFresh()) {
                    homeListHolder.tvMainListType.setText("新");
                } else if (mainLists.get(position - 1).getTags().size() > 0) {
                    homeListHolder.tvMainListType.setText(mainLists.get(position - 1).getTags().get(0).getName());
                    homeListHolder.rlMainListType.setBackgroundResource(R.drawable.main_list_item2);
                    homeListHolder.tvMainListType.setTextColor(mContext.getResources().getColor(R.color.main_list_item_color3));
                } else {
                    homeListHolder.rlMainListType.setVisibility(View.GONE);
                }
                homeListHolder.tvMainListAuthor.setText(mainLists.get(position - 1).getAuthor());
                homeListHolder.tvMainListTime.setText(mainLists.get(position - 1).getNiceDate());
                homeListHolder.tvMainListTitle.setText(mainLists.get(position - 1).getTitle());
                homeListHolder.tvSuperchaptername.setText(mainLists.get(position - 1).getSuperChapterName());
                homeListHolder.tvChaptername.setText(mainLists.get(position - 1).getChapterName());

                if (mainLists.get(position - 1).isCollect()){
                    homeListHolder.ivCollection.setSelected(true);
                }else {
                    homeListHolder.ivCollection.setSelected(false);
                }

                homeListHolder.ivCollection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mainLists.get(homeListHolder.getLayoutPosition() - 1).isCollect()){
                            onItemListener.setCancelCollect(homeListHolder.getLayoutPosition()-1);
                        }else {
                            onItemListener.setAddCollect(homeListHolder.getLayoutPosition()-1);
                        }
                    }
                });

                homeListHolder.ll_list_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemListener.setOnListener(position-1);
                    }
                });

                break;
            case HOME_TYPE3:

                FootViewHolder footViewHolder= (FootViewHolder) holder;
                if (FootisVisi==2){
                    footViewHolder.rl_foot_view.setVisibility(View.VISIBLE);
                    footViewHolder.tv_add_more.setText("正在加载中...");
                    footViewHolder.load_progress.setVisibility(View.VISIBLE);
                }else if (FootisVisi==3){
                    footViewHolder.rl_foot_view.setVisibility(View.VISIBLE);
                    footViewHolder.tv_add_more.setText("数据已经加载完毕~~");
                    footViewHolder.load_progress.setVisibility(View.GONE);
                }else{
                    footViewHolder.rl_foot_view.setVisibility(View.GONE);
                }

                break;
            default:
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        super.onBindViewHolder(holder, position, payloads);

        if (!payloads.isEmpty()){
            switch (getItemViewType(position)) {
                case HOME_TYPE2:
                    HomeListHolder homeListHolder = (HomeListHolder) holder;
                    if (mainLists.get(position - 1).isCollect()){
                        homeListHolder.ivCollection.setSelected(true);
                    }else {
                        homeListHolder.ivCollection.setSelected(false);
                    }

                    break;

                default:
            }
        }
    }

    private void setBanner(HomeBannerHolder homeBannerHolder) {
        //设置banner样式
        homeBannerHolder.main_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        homeBannerHolder.main_banner.setImageLoader(new GlideImageLoader());

        //设置banner动画效果
        homeBannerHolder.main_banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）

        //设置自动轮播，默认为true
        homeBannerHolder.main_banner.isAutoPlay(true);
        //设置轮播时间
        homeBannerHolder.main_banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        homeBannerHolder.main_banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        homeBannerHolder.main_banner.start();

        if (mainBannerDatas.size() > 0) {
            //设置图片集合
            homeBannerHolder.main_banner.setImages(images);
            homeBannerHolder.main_banner.setBannerTitles(titles);
            //banner设置方法全部调用完毕时最后调用
            homeBannerHolder.main_banner.start();
        }

        homeBannerHolder.main_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                onItemListener.setOnBannerListener(position);
            }
        });


    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return HOME_TYPE1;
        } else if (position == mainLists.size()) {
            return HOME_TYPE3;
        } else {
            return HOME_TYPE2;
        }

    }

    public void isHaveMoreData(int isVisi){
        FootisVisi=isVisi;
    }

    @Override
    public int getItemCount() {
        if (mainBannerDatas.size() == 0) {
            return mainLists.size();
        } else {
            return mainLists.size() + 1;
        }

    }

    public void setMainListData(List<HomeListBean.DataBean.DatasBean> datas) {
        mainLists.clear();
        mainLists.addAll(datas);
        notifyDataSetChanged();
    }

    public void setMainBannerData(List<BannerBean.DataBean> data) {
        mainBannerDatas.clear();
        images.clear();
        titles.clear();
        mainBannerDatas.addAll(data);

        for (int i = 0; i < mainBannerDatas.size(); i++) {
            images.add(mainBannerDatas.get(i).getImagePath());
            titles.add(mainBannerDatas.get(i).getTitle());
        }
    }

    class HomeListHolder extends RecyclerView.ViewHolder {

        TextView tvMainListType;
        TextView tvMainListAuthor;
        TextView tvMainListTime;
        TextView tvMainListTitle;
        TextView tvSuperchaptername;
        TextView tvChaptername;
        ImageView ivCollection;
        RelativeLayout rlMainListType;
        private LinearLayout ll_list_item;

        HomeListHolder(View itemView) {
            super(itemView);

            tvMainListType = itemView.findViewById(R.id.tv_main_list_type);
            tvMainListAuthor = itemView.findViewById(R.id.tv_main_list_author);
            tvMainListTime = itemView.findViewById(R.id.tv_main_list_time);
            tvMainListTitle = itemView.findViewById(R.id.tv_main_list_title);
            tvSuperchaptername = itemView.findViewById(R.id.tv_superChapterName);
            tvChaptername = itemView.findViewById(R.id.tv_chapterName);
            ivCollection = itemView.findViewById(R.id.iv_collection);
            rlMainListType = itemView.findViewById(R.id.rl_main_list_type);
            ll_list_item=itemView.findViewById(R.id.ll_list_item);

        }
    }

    class HomeBannerHolder extends RecyclerView.ViewHolder {

        Banner main_banner;

        HomeBannerHolder(View itemView) {
            super(itemView);

            main_banner = itemView.findViewById(R.id.main_banner);

        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar load_progress;
        private TextView tv_add_more;
        private RelativeLayout rl_foot_view;

        FootViewHolder(View itemView) {
            super(itemView);

            load_progress = itemView.findViewById(R.id.load_progress);
            tv_add_more = itemView.findViewById(R.id.tv_add_more);
            rl_foot_view = itemView.findViewById(R.id.rl_foot_view);

        }
    }

    public interface OnItemListener{
        void setOnListener(int pos);
        void setOnBannerListener(int position);
        void setAddCollect(int position);
        void setCancelCollect(int position);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
}
