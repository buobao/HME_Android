package com.hme.turman.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hme.turman.CacheData;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.utils.UiUtil;
import com.hme.turman.widgets.SimpleItemLayout;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by diaoqf on 2016/11/2.
 */

public class ZoneActivity extends BaseActivity {

    @BindView(R.id.head_img)
    CircleImageView head_img;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_location)
    TextView user_location;

    @BindView(R.id.user_home_address)
    SimpleItemLayout user_home_address;
    @BindView(R.id.user_active_address)
    SimpleItemLayout user_active_address;
    @BindView(R.id.user_age)
    SimpleItemLayout user_age;
    @BindView(R.id.user_company)
    SimpleItemLayout user_company;
    @BindView(R.id.user_affective_state)
    SimpleItemLayout user_affective_state;
    @BindView(R.id.user_sign)
    SimpleItemLayout user_sign;
    @BindView(R.id.user_hobby)
    SimpleItemLayout user_hobby;

    @Override
    protected String getPageTitle() {
        return "我的主页";
    }

    @Override
    protected boolean showRightMenu() {
        return true;
    }

    @Override
    protected String getRightMenuTitle() {
        return "编辑";
    }

    @Override
    protected View.OnClickListener getRightMenuListener() {
        return v->startActivity(new Intent(this,UserInfoEditActivity.class));
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        user_name.setText(CacheData.getDefault().getUserName());
        String address = CacheData.getDefault().getUserAddress();
        if (UiUtil.isNotEmpty(address)) {
            user_location.setText(address);
        }

    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_zone;
    }
}
