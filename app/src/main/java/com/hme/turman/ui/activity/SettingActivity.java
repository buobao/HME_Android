package com.hme.turman.ui.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lebro on 2016/11/5.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("设置");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_setting;
    }
}
