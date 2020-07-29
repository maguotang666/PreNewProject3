package com.example.administrator.prenewproject.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.bean.BackLogToDoBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.LogUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.example.administrator.prenewproject.utils.WorkerUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 新增代办
 *
 * @author maguotang
 */
public class NewplToDoActivity extends BaseActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {


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
    @BindView(R.id.cl_comm)
    ConstraintLayout clComm;
    @BindView(R.id.cb1)
    CheckBox         cb1;
    @BindView(R.id.cb2)
    CheckBox         cb2;
    @BindView(R.id.rl_select_time)
    RelativeLayout   rlSelectTime;
    @BindView(R.id.btn_save)
    Button           btnSave;
    @BindView(R.id.et_new_todo_title)
    EditText         etNewTodoTitle;
    @BindView(R.id.et_new_todo_details)
    EditText         etNewTodoDetails;
    @BindView(R.id.tv1)
    TextView         tv1;
    @BindView(R.id.tv2)
    TextView         tv2;
    @BindView(R.id.tv_new_todo_time)
    TextView         tvNewTodoTime;
    private int todoType=0;
    private String dataTime="";
    private Calendar calendar;
    /**
     * 当前todo的级别，2--一般  1--重要
     */
    private int priority=2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newpl_to_do;
    }

    @Override
    protected void initView() {
        tvTitle.setText("新增");
        ivBack.setVisibility(View.VISIBLE);
        ivTitleLeft.setVisibility(View.GONE);
        ivTitleRight.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        cb1.setChecked(true);
        todoType=getIntent().getIntExtra("todoType",0);
        calendar = Calendar.getInstance();
        dataTime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        tvNewTodoTime.setText(dataTime);
    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
        cb1.setOnClickListener(this);
        cb2.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        rlSelectTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.cb1:

                if (cb2.isChecked()) {
                    cb2.setChecked(false);
                }
                cb1.setChecked(true);
                priority=2;
                break;
            case R.id.cb2:
                if (cb1.isChecked()) {
                    cb1.setChecked(false);
                }
                cb2.setChecked(true);
                priority=1;
                break;
            case R.id.btn_save:
                newplSave();
                break;
            case R.id.rl_select_time:
                showTimePick();
                break;
            default:
        }
    }

    private void showTimePick() {
//        datePicker.setVisibility(View.VISIBLE);

        DatePickerDialog dialog = new DatePickerDialog(NewplToDoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LogUtils.e("onDateSet: year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dataTime=year+"-"+(month+1)+"-"+dayOfMonth;
                        WorkerUtils.postMain(new Runnable() {
                            @Override
                            public void run() {
                                tvNewTodoTime.setText(dataTime);
                            }
                        });
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void newplSave() {

        if (TextUtils.isEmpty(etNewTodoTitle.getText().toString())) {
            ToastUtil.show("标题不能为空");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("title", etNewTodoTitle.getText().toString());
        map.put("content", etNewTodoDetails.getText().toString() == null ? "" : etNewTodoDetails.getText().toString());
        map.put("date", tvNewTodoTime.getText().toString());
        map.put("type", todoType+"");
        map.put("priority", priority+"");

        ServiceShell.postObiectKey(AppNetConfig.getInstance().DATA_NEWPL_TODO, map, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                BackLogToDoBean backLogToDoBean=CommonUtils.getData(result,BackLogToDoBean.class);
                if (backLogToDoBean.getData()!=null){
                    finish();
                }else {
                    ToastUtil.show(backLogToDoBean.getErrorMsg());
                }

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {

            }
        });

    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
       ToastUtil.show("您选择的日期是："+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日!");

    }
}
