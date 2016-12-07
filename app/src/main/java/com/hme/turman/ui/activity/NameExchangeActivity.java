package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.CacheData;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.ui.entity.event.CustomEvent;
import com.hme.turman.utils.UiUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/17.
 */

public class NameExchangeActivity extends BaseActivity {
    @BindView(R.id.user_name)
    TextView user_name;

    @Override
    protected String getPageTitle() {
        return "昵称";
    }

    @Override
    protected boolean showRightMenu() {
        return true;
    }

    @Override
    protected String getRightMenuTitle() {
        return "提交";
    }

    @Override
    protected View.OnClickListener getRightMenuListener() {
        return v->{
            if (UiUtil.isNotEmpty(user_name.getText().toString())) {
                CacheData.getDefault().setUserNickName(user_name.getText().toString());
                EventBus.getDefault().post(new CustomEvent(CustomEvent.UPDATE_USER_INFO));
                onBackPressed();
            } else {
                toast("请输入昵称");
            }
        };
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_name_exchange;
    }
}
