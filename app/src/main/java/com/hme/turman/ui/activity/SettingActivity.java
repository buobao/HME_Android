package com.hme.turman.ui.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.CacheData;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.ui.entity.event.CustomEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import io.rong.imkit.RongIM;

/**
 * Created by lebro on 2016/11/5.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.logout)
    TextView logout;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("设置");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());
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
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_setting;
    }
}
