package com.example.administrator.prenewproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.bean.KnowLedgeBean;

import java.util.ArrayList;
import java.util.List;

public class KnowLedgeAdapter extends RecyclerView.Adapter{

    private Context mContext;

    private List<KnowLedgeBean.DataBean> knowDatas=new ArrayList<>();

    private String ChildName="";
    private OnItemClickListener onItemClickListener;
    public KnowLedgeAdapter(Context activity) {
        mContext=activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.know_ledge_item, viewGroup,false);
        return new KnowLedgeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int pos) {

        KnowLedgeHolder knowLedgeHolder= (KnowLedgeHolder) viewHolder;

        knowLedgeHolder.tvKnowItemName.setText(knowDatas.get(pos).getName());
        ChildName="";
        for (int i = 0; i < knowDatas.get(pos).getChildren().size(); i++) {
            ChildName += knowDatas.get(pos).getChildren().get(i).getName()+"  ";

        }
        knowLedgeHolder.tvKnowItemContent.setText(ChildName);
        knowLedgeHolder.rl_know_ledge_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return knowDatas.size();
    }

    public void setKnowData(List<KnowLedgeBean.DataBean> data) {
        knowDatas.clear();
        knowDatas.addAll(data);
        notifyDataSetChanged();
    }


    class KnowLedgeHolder extends RecyclerView.ViewHolder{

        TextView tvKnowItemName;
        TextView tvKnowItemContent;
        RelativeLayout rl_know_ledge_item;

        public KnowLedgeHolder(@NonNull View itemView) {
            super(itemView);

            tvKnowItemName =itemView.findViewById(R.id.tv_know_item_name);
            tvKnowItemContent =itemView.findViewById(R.id.tv_know_item_content);
            rl_know_ledge_item=itemView.findViewById(R.id.rl_know_ledge_item);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }


}
