package com.example.administrator.prenewproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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
import com.example.administrator.prenewproject.bean.PubNumListBean;

import java.util.ArrayList;
import java.util.List;

public class PubNumAdapter extends RecyclerView.Adapter {

    private final int HOME_TYPE1 = 0;
    private final int HOME_TYPE2 = 1;
    //加载更多显示与隐藏  1隐藏   2显示   3已经加载完毕
    private int FootisVisi = 1;
    private Context mContext;
    private List<PubNumListBean.DataBean.DatasBean> pubNumDatas = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    public PubNumAdapter(Context activity) {
        mContext = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (viewType == HOME_TYPE2) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_list_item, viewGroup, false);

            return new HomeListHolder(view);
        } else {

            View view = LayoutInflater.from(mContext).inflate(R.layout.foot_view, viewGroup, false);
            return new FootViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        switch (getItemViewType(position)) {


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


                HomeListHolder homeListHolder = (HomeListHolder) viewHolder;

                homeListHolder.rlMainListType.setVisibility(View.GONE);
                homeListHolder.tvMainListAuthor.setText(pubNumDatas.get(position).getAuthor());
                homeListHolder.tvMainListTime.setText(pubNumDatas.get(position).getNiceDate());
                homeListHolder.tvMainListTitle.setText(pubNumDatas.get(position).getTitle());
                homeListHolder.tvSuperchaptername.setText(pubNumDatas.get(position).getSuperChapterName());
                homeListHolder.tvChaptername.setText(pubNumDatas.get(position).getChapterName());
                homeListHolder.ll_list_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position);
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

    public void setPubData(List<PubNumListBean.DataBean.DatasBean> datas) {
        pubNumDatas.clear();
        pubNumDatas.addAll(datas);
        notifyDataSetChanged();

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

    public void isHaveMoreData(int isVisi){
        FootisVisi=isVisi;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }



}
