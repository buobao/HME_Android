package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/14.
 */

public class UserInfoEditActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;

    @Override
    protected void init(Bundle savedInstanceState) {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());
        title.setText("资料编辑");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("提交");
        title_menu.setOnClickListener(v->{
            //提交处理
        });

        

    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_user_info_edit;
    }
}
