package com.example.administrator.prenewproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.prenewproject.NetWork.AStringCallBack;
import com.example.administrator.prenewproject.NetWork.AppNetConfig;
import com.example.administrator.prenewproject.NetWork.ServiceShell;
import com.example.administrator.prenewproject.R;
import com.example.administrator.prenewproject.base.BaseActivity;
import com.example.administrator.prenewproject.bean.HotSearchBean;
import com.example.administrator.prenewproject.utils.CommonUtils;
import com.example.administrator.prenewproject.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 搜索页面
 *
 * @author maguotang
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_title_left)
    ImageView     ivTitleLeft;
    @BindView(R.id.iv_back)
    ImageView     ivBack;
    @BindView(R.id.tv_title)
    TextView      tvTitle;
    @BindView(R.id.et_title)
    EditText      etTitle;
    @BindView(R.id.iv_title_right)
    ImageView     ivTitleRight;
    @BindView(R.id.iv_three_point)
    ImageView     ivThreePoint;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.tv_empty)
    TextView      tvEmpty;
    @BindView(R.id.lv_search_history)
    ListView      lvSearchHistory;
    private List<String>  hotTagNames = new ArrayList<>();
    private HotSearchBean hotSearchBean;
    private LayoutInflater layoutInflater;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;

    }

    @Override
    protected void initView() {

        layoutInflater=LayoutInflater.from(this);
        ivBack.setVisibility(View.VISIBLE);
        ivTitleLeft.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        etTitle.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initData() {
        getNetWorkData();
    }

    private void getNetWorkData() {
        ServiceShell.getObjectNoKey(AppNetConfig.getInstance().DATA_HOT_SEARCH, new AStringCallBack() {
            @Override
            public void onSuccess(String result, int id) {
                hotTagNames.clear();
                hotSearchBean = CommonUtils.getData(result, HotSearchBean.class);

                initTagData(hotSearchBean);

            }

            @Override
            public void onDisSuccess(Call call, Exception e, int id) {

            }
        });
    }

    private void initTagData(HotSearchBean hotSearchBean) {
        for (int i = 0; i < hotSearchBean.getData().size(); i++) {
            hotTagNames.add(hotSearchBean.getData().get(i).getName());
        }

        idFlowlayout.setAdapter(new TagAdapter<String>(hotTagNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) layoutInflater.inflate(R.layout.tag_mane_item,
                        idFlowlayout,false);
                tv.setText(s);
                return tv;

            }
        });


        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {

                searchData(hotTagNames.get(position));
                return true;
            }
        });


    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
        ivTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_title_right:

                if (!TextUtils.isEmpty(etTitle.getText().toString())){
                    searchData(etTitle.getText().toString());
                }

                break;
                default:

        }
    }

    private void searchData(String searchContent) {

        Intent intent=new Intent(SearchActivity.this,SearchListActivity.class);
        intent.putExtra("title",searchContent);
        startActivity(intent);

    }


}
