package com.example.administrator.prenewproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.bean.NavigationBean;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class Navigation2Adapter extends RecyclerView.Adapter {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<NavigationBean.DataBean> navigation2Datas = new ArrayList<>();

    private OnItemSelectListener onItemSelectListener;

    public Navigation2Adapter(Context fragmentActivity) {
        mContext = fragmentActivity;
        layoutInflater=LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.navigation2_item, viewGroup, false);

        return new Navigation2Holder(view);
    }
    private TagFlowLayout mFlowLayout;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int pos) {
        Navigation2Holder navigation2Holder = (Navigation2Holder) viewHolder;
        navigation2Holder.tvNavigation2Title.setText(navigation2Datas.get(pos).getName());
        mFlowLayout=navigation2Holder.id_flowlayout;
        List<String> tagNames=new ArrayList<>();
        for (int i = 0; i < navigation2Datas.get(pos).getArticles().size(); i++) {
            tagNames.add(navigation2Datas.get(pos).getArticles().get(i).getTitle());
        }
        navigation2Datas.get(pos).setTagNames(tagNames);

        mFlowLayout.setAdapter(new TagAdapter<String>(tagNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) layoutInflater.inflate(R.layout.tag_mane_item,
                        mFlowLayout,false);
                tv.setText(s);
                return tv;

            }
        });


        navigation2Holder.id_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {

                onItemSelectListener.onItemSelect(navigation2Datas.get(pos).getArticles().get(position));


                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return navigation2Datas.size();
    }

    public void setNavigation2Data(List<NavigationBean.DataBean> data) {
        navigation2Datas.addAll(data);
        notifyDataSetChanged();
    }

    class Navigation2Holder extends RecyclerView.ViewHolder {


        TextView tvNavigation2Title;
        TagFlowLayout id_flowlayout;

        public Navigation2Holder(@NonNull View itemView) {
            super(itemView);

            tvNavigation2Title = itemView.findViewById(R.id.tv_navigation2_title);
            id_flowlayout = itemView.findViewById(R.id.id_flowlayout);

        }
    }

    public void setOnItemClickListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    public interface OnItemSelectListener{
        /**
         * 选择item的监听
         * @param data
         */
        void onItemSelect(NavigationBean.DataBean.ArticlesBean data);
    }

}
