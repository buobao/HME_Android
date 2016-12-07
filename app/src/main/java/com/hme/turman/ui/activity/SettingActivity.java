package com.hme.turman.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.CacheData;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.ui.entity.event.CustomEvent;
import com.hme.turman.widgets.FormItemLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import io.rong.imkit.RongIM;

/**
 * Created by lebro on 2016/11/5.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.set_location)
    FormItemLayout set_location;

    @BindView(R.id.logout)
    TextView logout;

    @Override
    protected String getPageTitle() {
        return "设置";
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        logout.setOnClickListener(v-> {
            CacheData.getDefault().setIsLogin(false);
            CacheData.getDefault().setUserNickName("");
            CacheData.getDefault().setUserName("");
            CacheData.getDefault().setUserPortrait("");
            CacheData.getDefault().setUserToken("");
            CacheData.getDefault().setRongToken("");
            //其他数据也一律清空

            //断开融云
            RongIM.getInstance().disconnect();

            //登出成功刷新
            CustomEvent event = new CustomEvent(CustomEvent.LOGIN_EVENT);
            event.setActionDone(false);
            EventBus.getDefault().post(event);
            onBackPressed();
        });

        set_location.setOnItemClickListener(v->{
            Intent intent = new Intent(SettingActivity.this, LocationMapActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_setting;
    }
}
