package com.example.administrator.prenewproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.SearchListActivity;
import com.example.administrator.prenewproject.bean.SearchListBean;
import com.example.administrator.prenewproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maguotang on 2019/4/10
 * @author maguotang
 */
public class SearchListAdapter extends RecyclerView.Adapter {
    private SearchListActivity searchListActivity;
    private List<SearchListBean.DataBean.DatasBean> searchDatas=new ArrayList<>();
    private       OnItemClickListener                     onItemClickListener;
    private final int                                     HOME_TYPE1  = 0;
    private final int                                     HOME_TYPE2  = 1;
    //加载更多显示与隐藏  1隐藏   2显示   3已经加载完毕
    private       int                                     FootisVisi  = 1;
    public SearchListAdapter(SearchListActivity searchListActivity) {
        this.searchListActivity=searchListActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == HOME_TYPE2) {
            View view = LayoutInflater.from(searchListActivity).inflate(R.layout.search_list_item, viewGroup, false);

            return new MyViewHolder(view);
        } else {

            View view = LayoutInflater.from(searchListActivity).inflate(R.layout.foot_view, viewGroup, false);
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

                MyViewHolder homeListHolder = (MyViewHolder) viewHolder;

                if (searchDatas.get(position).isFresh()) {
                    homeListHolder.tvMainListType.setText("新");
                } else if (searchDatas.get(position).getTags().size() > 0) {
                    homeListHolder.tvMainListType.setText(searchDatas.get(position).getTags().get(0).getName());
                    homeListHolder.rlMainListType.setBackgroundResource(R.drawable.main_list_item2);
                    homeListHolder.tvMainListType.setTextColor(searchListActivity.getResources().getColor(R.color.main_list_item_color3));
                } else {
                    homeListHolder.rlMainListType.setVisibility(View.GONE);
                }
                homeListHolder.tvMainListAuthor.setText(searchDatas.get(position).getAuthor());
                homeListHolder.tvMainListTime.setText(searchDatas.get(position).getNiceDate());
                //TextView使用HTML处理字体样式、显示图片
                homeListHolder.tvMainListTitle.setText(Html.fromHtml(searchDatas.get(position ).getTitle()));
                LogUtils.e("Html------"+searchDatas.get(position ).getTitle());
                homeListHolder.tvSuperchaptername.setText(searchDatas.get(position ).getSuperChapterName());
                homeListHolder.tvChaptername.setText(searchDatas.get(position).getChapterName());

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


    @Override
    public int getItemCount() {
        if (searchDatas.size() == 0) {
            return 0;
        } else {
            return searchDatas.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == searchDatas.size()) {
            return HOME_TYPE1;
        } else {
            return HOME_TYPE2;
        }
    }

    public void setPubData(List<SearchListBean.DataBean.DatasBean> datas) {
        searchDatas.clear();
        searchDatas.addAll(datas);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        TextView       tvMainListType;
        TextView       tvMainListAuthor;
        TextView       tvMainListTime;
        TextView       tvMainListTitle;
        TextView       tvSuperchaptername;
        TextView       tvChaptername;
        ImageView      ivCollection;
        RelativeLayout rlMainListType;
        private LinearLayout ll_list_item;

        MyViewHolder(View itemView) {
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
