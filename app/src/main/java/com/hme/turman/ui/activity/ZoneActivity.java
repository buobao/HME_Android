package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/2.
 */

public class ZoneActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("我的主页");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("编辑");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());

        title_menu.setOnClickListener(v->{
            toast("到编辑页面");
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_zone;
    }
}
