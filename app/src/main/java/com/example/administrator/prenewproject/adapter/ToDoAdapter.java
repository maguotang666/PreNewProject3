package com.example.administrator.prenewproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.MyApplication;
import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.activity.MainActivity;
import com.example.administrator.prenewproject.activity.ToDoActivity;
import com.example.administrator.prenewproject.bean.BackLogToDoBean;
import com.example.administrator.prenewproject.bean.LoginBean;
import com.example.administrator.prenewproject.listener.slideswaphelper.SlideSwapAction;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.SharedPreferencesUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 代办事件的适配器
 * Created by maguotang on 2019/4/23
 *
 * @author maguotang
 */
public class ToDoAdapter extends RecyclerView.Adapter {

    private OnItemClickListener onItemClickListener;
    private Context             mContext;
    private int                 status = 0;

    public ToDoAdapter(Context toDoActivity, int status) {
        mContext = toDoActivity;
        this.status = status;
    }

    //加载更多显示与隐藏  1隐藏   2显示   3已经加载完毕
    private       int                                      FootisVisi = 1;
    private final int                                      HOME_TYPE1 = 0;
    private final int                                      HOME_TYPE2 = 1;
    private       List<BackLogToDoBean.DataBean.DatasBean> todoDatas;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == HOME_TYPE2) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.foot_view, parent, false);
            return new FootViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent, false);
            return new ToDoTiTleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        int viewType = getItemViewType(position);
        switch (viewType) {
            case HOME_TYPE1:

                ToDoTiTleHolder toDoTiTleHolder = (ToDoTiTleHolder) viewHolder;
                if (position > 0) {

                    if (todoDatas.get(position - 1).getDateStr().equals(todoDatas.get(position).getDateStr())) {
                        toDoTiTleHolder.rl_todo_time.setVisibility(View.GONE);
                    } else {
                        toDoTiTleHolder.rl_todo_time.setVisibility(View.VISIBLE);
                        toDoTiTleHolder.tv_todo_time.setText(todoDatas.get(position).getDateStr());
                    }

                } else {
                    toDoTiTleHolder.tv_todo_time.setText(todoDatas.get(position).getDateStr());
                }

                if (todoDatas.get(position).getPriority() == 1) {
                    toDoTiTleHolder.iv_todo_priority.setVisibility(View.VISIBLE);
                } else {
                    toDoTiTleHolder.iv_todo_priority.setVisibility(View.GONE);
                }

                if (status==0){
                    toDoTiTleHolder.tv_work_finish.setText("复原");
                }else if (status==1){
                    toDoTiTleHolder.tv_work_finish.setText("标记已完成");
                }

                toDoTiTleHolder.tv_work_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, String> map = new HashMap<>();
                        map.put("status", status + "");
                        ServiceShell.postUncollectListNoKey(AppNetConfig.getInstance().DATA_TODO_FINISH_OR_NOFINISH, todoDatas.get(position).getId()
                                , map, new AStringCallBack() {
                                    @Override
                                    public void onSuccess(String result, int id) {

                                        LoginBean loginBean = CommonUtils.getData(result, LoginBean.class);
                                        if (loginBean.getData() != null) {
                                            onItemClickListener.workFinish(position);

                                        } else {
                                            ToastUtil.show(loginBean.getErrorMsg());
                                        }
                                    }

                                    @Override
                                    public void onDisSuccess(Call call, Exception e, int id) {

                                    }
                                });
                    }
                });
                toDoTiTleHolder.tv_delect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServiceShell.postCollectListNoKey(AppNetConfig.getInstance().DATA_TODO_DELECT, todoDatas.get(position).getId()
                                , new AStringCallBack() {
                                    @Override
                                    public void onSuccess(String result, int id) {

                                        LoginBean loginBean = CommonUtils.getData(result, LoginBean.class);
                                        if (loginBean.getData() != null) {
                                            onItemClickListener.workDelect(position);

                                        } else {
                                            ToastUtil.show(loginBean.getErrorMsg());
                                        }
                                    }

                                    @Override
                                    public void onDisSuccess(Call call, Exception e, int id) {

                                    }
                                });

                    }
                });

                toDoTiTleHolder.tv_todo_title.setText(todoDatas.get(position).getTitle());
                toDoTiTleHolder.tv_todo_content.setText(todoDatas.get(position).getContent());

                break;
            case HOME_TYPE2:

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
            default:
        }
    }


//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//
//
//        if (payloads.isEmpty()) {
//            onBindViewHolder(holder, position);
//        } else {
//
//        }
//
//    }

    public void isHaveMoreData(int isVisi) {
        FootisVisi = isVisi;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == todoDatas.size()) {
            return HOME_TYPE2;
        } else {
            return HOME_TYPE1;
        }

    }

    @Override
    public int getItemCount() {
        if (todoDatas == null) {
            return 0;
        } else if (todoDatas.size() == 0) {
            return 0;
        } else {
            return todoDatas.size() + 1;
        }
    }

    public void setToDoData(List<BackLogToDoBean.DataBean.DatasBean> todoData) {
        todoDatas = todoData;
        notifyDataSetChanged();

    }

    class ToDoTiTleHolder extends RecyclerView.ViewHolder implements SlideSwapAction {

        private RelativeLayout rl_todo_time, rl_todo_content;
        private TextView tv_todo_time, tv_todo_title, tv_todo_content, tv_work_finish, tv_delect;
        private ImageView iv_todo_priority;

        public ToDoTiTleHolder(@NonNull View itemView) {
            super(itemView);
            rl_todo_time = itemView.findViewById(R.id.rl_todo_time);
            tv_todo_time = itemView.findViewById(R.id.tv_todo_time);
            tv_todo_title = itemView.findViewById(R.id.tv_todo_title);
            tv_work_finish = itemView.findViewById(R.id.tv_work_finish);
            tv_delect = itemView.findViewById(R.id.tv_delect);
            tv_todo_content = itemView.findViewById(R.id.tv_todo_content);
            iv_todo_priority = itemView.findViewById(R.id.iv_todo_priority);
            rl_todo_content = itemView.findViewById(R.id.rl_todo_content);

        }

        @Override
        public float getActionWidth() {
            //布局隐藏超过父布局的范围的时候这里得不到宽度
            return dip2px(mContext, 240);
        }

        @Override
        public View ItemView() {
            return rl_todo_content;
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


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int pos);

        void workFinish(int pos);

        void workDelect(int pos);
    }


    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
