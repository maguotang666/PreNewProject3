package com.example.administrator.prenewproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.CollectActivity;
import com.example.administrator.prenewproject.bean.CollectBean;
import com.example.administrator.prenewproject.bean.HomeListBean;
import com.example.administrator.prenewproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏页面的适配器
 * Created by maguotang on 2019/4/22
 *
 * @author maguotang
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ClooectHolder> {

    private OnItemListener onItemListener;
    private Context mContext;
    private List<CollectBean.DataBean.DatasBean> mainLists = new ArrayList<>();
    public CollectAdapter(Context context) {
        mContext=context;
    }


    @NonNull
    @Override
    public ClooectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        
        View view=LayoutInflater.from(mContext).inflate(R.layout.collect_item,viewGroup,false);
        
        return new ClooectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClooectHolder clooectHolder, final int position) {

        clooectHolder.tvMainListAuthor.setText(mainLists.get(position).getAuthor());
        clooectHolder.tvMainListTime.setText(mainLists.get(position).getNiceDate());
        clooectHolder.tvMainListTitle.setText(mainLists.get(position).getTitle());
        clooectHolder.tvChaptername.setText(mainLists.get(position).getChapterName());
        clooectHolder.ivCollection.setSelected(true);
        clooectHolder.ll_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.setOnListener(position);
            }
        });
        clooectHolder.ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.setCancelCollect(clooectHolder.getLayoutPosition());
                LogUtils.e("collAdapter-----"+clooectHolder.getLayoutPosition());
            }
        });
    }




    @Override
    public int getItemCount() {
        return mainLists.size();
    }

    public void setCollectData(List<CollectBean.DataBean.DatasBean> homeDatas) {
        mainLists.clear();
        mainLists.addAll(homeDatas);
        notifyDataSetChanged();
    }

    public void unCollect(int position) {
        mainLists.remove(position);
        notifyItemRemoved(position);
    }

    class ClooectHolder extends RecyclerView.ViewHolder{

        TextView       tvMainListType;
        TextView       tvMainListAuthor;
        TextView       tvMainListTime;
        TextView       tvMainListTitle;
        TextView       tvChaptername;
        ImageView      ivCollection;
        RelativeLayout rlMainListType;
        LinearLayout ll_list_item;
        public ClooectHolder(@NonNull View itemView) {
            super(itemView);
            tvMainListType = itemView.findViewById(R.id.tv_main_list_type);
            tvMainListAuthor = itemView.findViewById(R.id.tv_main_list_author);
            tvMainListTime = itemView.findViewById(R.id.tv_main_list_time);
            tvMainListTitle = itemView.findViewById(R.id.tv_main_list_title);
            tvChaptername = itemView.findViewById(R.id.tv_chapterName);
            ivCollection = itemView.findViewById(R.id.iv_collection);
            rlMainListType = itemView.findViewById(R.id.rl_main_list_type);
            ll_list_item=itemView.findViewById(R.id.ll_list_item);
            
        }
    }
    
    
    
    
    

    public interface OnItemListener {
        void setOnListener(int pos);
        void setCancelCollect(int position);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
}
