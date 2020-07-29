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
import com.example.administrator.prenewproject.bean.Navigation1Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class Navigation1Adapter extends RecyclerView.Adapter{
    private Context mContext;

    private List<Navigation1Bean> navigation1Names=new ArrayList<>();
    private int preSelectPos=0;
    private OnNavi1ItemClickListener onNavi1ItemClickListener;

    public Navigation1Adapter(Context fragmentActivity) {
        mContext=fragmentActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.navigation1_item,viewGroup,false);

        return new Navigation1Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int pos) {
        Navigation1Holder navigation1Holder= (Navigation1Holder) viewHolder;
        navigation1Holder.tvNavigation1Content.setText(navigation1Names.get(pos).getName());

        navigation1Names.get(preSelectPos).setSelect(true);

        if (navigation1Names.get(pos).isSelect()){
            navigation1Holder.rlNavigation1.setBackgroundResource(R.color.white_color);
            navigation1Holder.tvNavigation1Content.setTextColor(mContext.getResources().getColor(R.color.main_list_item_color3));
        }else {
            navigation1Holder.rlNavigation1.setBackgroundResource(R.color.navigation1_bg);
            navigation1Holder.tvNavigation1Content.setTextColor(mContext.getResources().getColor(R.color.colorDialogBG));
        }

        navigation1Holder.rlNavigation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos==preSelectPos){
                    return;
                }
                navigation1Names.get(preSelectPos).setSelect(false);
                navigation1Names.get(pos).setSelect(true);
                preSelectPos=pos;
                onNavi1ItemClickListener.selectPos(pos);
                notifyDataSetChanged();
            }
        });


    }
    public void setonNavi1ItemClickListener(OnNavi1ItemClickListener onNavi1ItemClickListener){
        this.onNavi1ItemClickListener=onNavi1ItemClickListener;
    }


    @Override
    public int getItemCount() {
        return navigation1Names.size();
    }

    public void setNavigation1Data(List<Navigation1Bean> names) {
        navigation1Names.addAll(names);
        notifyDataSetChanged();
    }

    public void setSelectPos(int pos) {
        navigation1Names.get(preSelectPos).setSelect(false);
        preSelectPos=pos;
        notifyDataSetChanged();
    }


    public interface OnNavi1ItemClickListener{
        void selectPos(int pos);
    }


    class Navigation1Holder extends RecyclerView.ViewHolder{
        TextView tvNavigation1Content;
        RelativeLayout rlNavigation1;
        public Navigation1Holder(@NonNull View itemView) {
            super(itemView);

            tvNavigation1Content =itemView.findViewById(R.id.tv_navigation1_content);
            rlNavigation1 =itemView.findViewById(R.id.rl_navigation1);


        }
    }

}
