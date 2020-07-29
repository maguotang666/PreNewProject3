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

import com.bumptech.glide.Glide;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.bean.PubNumListBean;

import java.util.ArrayList;
import java.util.List;

public class ProjectTabAdapter extends RecyclerView.Adapter {

    private final int                                     HOME_TYPE1  = 0;
    private final int                                     HOME_TYPE2  = 1;
    //加载更多显示与隐藏  1隐藏   2显示   3已经加载完毕
    private       int                                     FootisVisi  = 1;
    private       Context                                 mContext;
    private       OnItemClickListener                     onItemClickListener;
    private       List<PubNumListBean.DataBean.DatasBean> pubNumDatas = new ArrayList<>();

    public ProjectTabAdapter(Context pojectFragment) {
        mContext = pojectFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (viewType == HOME_TYPE2) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.project_list_item, viewGroup, false);

            return new ProjectTabHolder(view);
        } else {

            View view = LayoutInflater.from(mContext).inflate(R.layout.foot_view, viewGroup, false);
            return new FootViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int pos) {


        switch (getItemViewType(pos)) {


            case HOME_TYPE1:

                FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
                if (FootisVisi == 2) {
                    footViewHolder.rl_foot_view.setVisibility(View.VISIBLE);
                    footViewHolder.tv_add_more.setText("正在加载中...");
                    footViewHolder.load_progress.setVisibility(View.VISIBLE);
                } else if (FootisVisi == 3) {
                    footViewHolder.rl_foot_view.setVisibility(View.VISIBLE);
                    footViewHolder.tv_add_more.setText("数据已经加载完毕~~");
                    footViewHolder.load_progress.setVisibility(View.GONE);
                } else {
                    footViewHolder.rl_foot_view.setVisibility(View.GONE);
                }


                break;
            case HOME_TYPE2:


                ProjectTabHolder projectTabHolder = (ProjectTabHolder) viewHolder;

                projectTabHolder.tvProjItemAuthor.setText(pubNumDatas.get(pos).getAuthor());
                projectTabHolder.tvProjItemTime.setText(pubNumDatas.get(pos).getNiceDate());
                projectTabHolder.tvProjItemTitle.setText(pubNumDatas.get(pos).getTitle());
                projectTabHolder.tvProjItemContent.setText(pubNumDatas.get(pos).getDesc());

                Glide.with(mContext).load(pubNumDatas.get(pos).getEnvelopePic()).into(projectTabHolder.ivProjItem);

                projectTabHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(pos);
                    }
                });

                break;
            default:
        }


    }


    @Override
    public int getItemViewType(int position) {


        if (position == pubNumDatas.size()) {
            return HOME_TYPE1;
        } else {
            return HOME_TYPE2;
        }
    }

    @Override
    public int getItemCount() {
        if (pubNumDatas.size() == 0) {
            return 0;
        } else {
            return pubNumDatas.size() + 1;
        }
    }


    class FootViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar    load_progress;
        private TextView       tv_add_more;
        private RelativeLayout rl_foot_view;

        FootViewHolder(View itemView) {
            super(itemView);

            load_progress = itemView.findViewById(R.id.load_progress);
            tv_add_more = itemView.findViewById(R.id.tv_add_more);
            rl_foot_view = itemView.findViewById(R.id.rl_foot_view);

        }
    }

    public void setPubData(List<PubNumListBean.DataBean.DatasBean> datas) {
        pubNumDatas.clear();
        pubNumDatas.addAll(datas);
        notifyDataSetChanged();

    }

    class ProjectTabHolder extends RecyclerView.ViewHolder {

        ImageView ivProjItem;
        ImageView ivProjCollection;
        TextView  tvProjItemTitle;
        TextView  tvProjItemContent;
        TextView  tvProjItemAuthor;
        TextView  tvProjItemTime;
        LinearLayout ll_item;


        public ProjectTabHolder(@NonNull View itemView) {
            super(itemView);

            ivProjItem = itemView.findViewById(R.id.iv_proj_item);
            ivProjCollection = itemView.findViewById(R.id.iv_proj_collection);
            tvProjItemTitle = itemView.findViewById(R.id.tv_proj_item_title);
            tvProjItemContent = itemView.findViewById(R.id.tv_proj_item_content);
            tvProjItemAuthor = itemView.findViewById(R.id.tv_proj_item_author);
            tvProjItemTime = itemView.findViewById(R.id.tv_proj_item_time);
            ll_item=itemView.findViewById(R.id.ll_item);
        }
    }


    public void isHaveMoreData(int isVisi) {
        FootisVisi = isVisi;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}