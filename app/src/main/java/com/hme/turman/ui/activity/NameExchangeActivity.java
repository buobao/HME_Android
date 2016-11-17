package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/17.
 */

public class NameExchangeActivity extends BaseActivity {
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("昵称");
        title_menu.setText("提交");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setOnClickListener(v->{

        });
        back.setOnClickListener(v->onBackPressed());
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_name_exchange;
    }
}
