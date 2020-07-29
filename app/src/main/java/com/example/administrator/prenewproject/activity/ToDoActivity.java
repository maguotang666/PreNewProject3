package com.example.administrator.prenewproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.prenewproject.Fragment.MainFr.AlreadyFinishFragment;
import com.example.administrator.prenewproject.Fragment.MainFr.BacklogFragment;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.view.BottomBar;
import com.example.administrator.prenewproject.view.MyPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 代办页面
 *
 * @author maguotang
 */
public class ToDoActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_title_left)
    ImageView        ivTitleLeft;
    @BindView(R.id.iv_back)
    ImageView        ivBack;
    @BindView(R.id.tv_title)
    TextView         tvTitle;
    @BindView(R.id.et_title)
    EditText         etTitle;
    @BindView(R.id.iv_title_right)
    ImageView        ivTitleRight;
    @BindView(R.id.iv_three_point)
    ImageView        ivThreePoint;
    @BindView(R.id.iv_switch)
    ImageView        ivSwitch;
    @BindView(R.id.fm_content_todo)
    FrameLayout      fmContent;
    @BindView(R.id.bottom_bar)
    BottomBar        bottomBar;
    @BindView(R.id.cl_comm)
    ConstraintLayout clComm;
    @BindView(R.id.ll_todo)
    RelativeLayout   llTodo;
    @BindView(R.id.rl_newpl)
    RelativeLayout   rlNewpl;
    private MyPopupWindow myPopupWindow;
    private TextView      tv_only_one;
    private TextView      tv_work;
    private TextView      tv_study;
    private TextView      tv_life;
    private int todoType=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_do;
    }

    @Override
    protected void initView() {

        ivBack.setVisibility(View.VISIBLE);
        ivTitleLeft.setVisibility(View.GONE);
        ivTitleRight.setVisibility(View.GONE);
        ivSwitch.setVisibility(View.VISIBLE);
        tvTitle.setText("只用这一个");
    }

    @Override
    protected void initData() {
        initPopu();
        bottomBar.setContainer(R.id.fm_content_todo)
                .setTitleBeforeAndAfterColor("#999999", "#999999")
                .addItem(BacklogFragment.class,
                        "代办",
                        R.drawable.balcklog,
                        R.drawable.balcklog)
                .addItem(AlreadyFinishFragment.class,
                        "已完成",
                        R.drawable.aler_finish,
                        R.drawable.aler_finish)
                .build();

    }


    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
        ivSwitch.setOnClickListener(this);
        tv_only_one.setOnClickListener(this);
        tv_work.setOnClickListener(this);
        tv_study.setOnClickListener(this);
        tv_life.setOnClickListener(this);
        rlNewpl.setOnClickListener(this);
    }

    private void initPopu() {

        myPopupWindow = new MyPopupWindow(this, R.layout.popu_todo) {
            @Override
            protected void initView(View view) {
                tv_only_one = view.findViewById(R.id.tv_only_one);
                tv_work = view.findViewById(R.id.tv_work);
                tv_study = view.findViewById(R.id.tv_study);
                tv_life = view.findViewById(R.id.tv_life);


            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_switch:

                int[] windowPos = myPopupWindow.calculatePopWindowPos(clComm, llTodo);

                myPopupWindow.showAtLocation(clComm, Gravity.TOP | Gravity.LEFT, windowPos[0], windowPos[1]);

                break;
            case R.id.tv_only_one:
                tvTitle.setText("只用这一个");
                todoType=0;
                tv_only_one.setTextColor(getResources().getColor(R.color.main_list_item_color3));
                tv_work.setTextColor(getResources().getColor(R.color.dark));
                tv_study.setTextColor(getResources().getColor(R.color.dark));
                tv_life.setTextColor(getResources().getColor(R.color.dark));

                Intent intent = new Intent();
                intent.setAction("send_todo");
                intent.putExtra("todo_type", 0);
                sendBroadcast(intent);
                myPopupWindow.popuDiss();
                break;
            case R.id.tv_work:
                tvTitle.setText("工作");
                todoType=1;
                tv_only_one.setTextColor(getResources().getColor(R.color.dark));
                tv_work.setTextColor(getResources().getColor(R.color.main_list_item_color3));
                tv_study.setTextColor(getResources().getColor(R.color.dark));
                tv_life.setTextColor(getResources().getColor(R.color.dark));

                Intent intent2 = new Intent();
                intent2.setAction("send_todo");
                intent2.putExtra("todo_type", 1);
                sendBroadcast(intent2);
                myPopupWindow.popuDiss();
                break;
            case R.id.tv_study:
                tvTitle.setText("学习");
                tv_only_one.setTextColor(getResources().getColor(R.color.dark));
                tv_work.setTextColor(getResources().getColor(R.color.dark));
                tv_study.setTextColor(getResources().getColor(R.color.main_list_item_color3));
                tv_life.setTextColor(getResources().getColor(R.color.dark));

                todoType=2;
                Intent intent3 = new Intent();
                intent3.setAction("send_todo");
                intent3.putExtra("todo_type", 2);
                sendBroadcast(intent3);
                myPopupWindow.popuDiss();
                break;
            case R.id.tv_life:
                tvTitle.setText("生活");
                tv_only_one.setTextColor(getResources().getColor(R.color.dark));
                tv_work.setTextColor(getResources().getColor(R.color.dark));
                tv_study.setTextColor(getResources().getColor(R.color.dark));
                tv_life.setTextColor(getResources().getColor(R.color.main_list_item_color3));
                todoType=3;
                Intent intent4 = new Intent();
                intent4.setAction("send_todo");
                intent4.putExtra("todo_type", 3);
                sendBroadcast(intent4);
                myPopupWindow.popuDiss();
                break;
            case R.id.rl_newpl:

                Intent intent5=new Intent(ToDoActivity.this,NewplToDoActivity.class);
                intent5.putExtra("todoType",todoType);

                startActivityForResult(intent5,1);
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){

        }


    }
}
