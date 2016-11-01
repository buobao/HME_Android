package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hme.turman.CacheData;
import com.hme.turman.Contents;
import com.hme.turman.DemoEvent;
import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;
import com.hme.turman.ui.entity.event.CustomEvent;
import com.hme.turman.utils.UiUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    @BindView(R.id.user_detail_layout)
    LinearLayout user_detail_layout;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_gender)
    ImageView user_gender;
    @BindView(R.id.user_sign)
    TextView user_sign;
    @BindView(R.id.user_help_times)
    TextView user_help_times;
    @BindView(R.id.user_seek_help_times)
    TextView user_seek_help_times;
    @BindView(R.id.user_ranking_times)
    TextView user_ranking_times;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;
    @BindView(R.id.no_login_text)
    TextView no_login_text;
    @BindView(R.id.login_layout)
    LinearLayout login_layout;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("我的");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("联系我们");
        title_menu.setOnClickListener(v->{toast("联系我们");});

        //判断是否登录
        if (CacheData.getDefault().isLogin()) {
            no_login_text.setVisibility(View.GONE);
            login_layout.setVisibility(View.VISIBLE);
            //读取缓存的用户信息
            user_name.setText(CacheData.getDefault().getUserName());
        }
    }

    @OnClick({R.id.my_message,R.id.my_comments,R.id.my_confirm,R.id.my_wallet,
            R.id.my_help_record,R.id.my_seek_help_record,R.id.my_setting,R.id.user_detail_layout})
    public void onMenuClick(View v){
        if (!CacheData.getDefault().isLogin()) {
            UiUtil.goLogin(getActivity());
            return;
        }
        switch (v.getId()) {
            case R.id.my_message:
                toast("我的动态");
                break;
            case R.id.my_comments:
                toast("我的评价");
                break;
            case R.id.my_confirm:
                toast("身份认证");
                break;
            case R.id.my_wallet:
                toast("我的钱包");
                break;
            case R.id.my_help_record:
                toast("我的帮助记录");
                break;
            case R.id.my_seek_help_record:
                toast("我的求助记录");
                break;
            case R.id.my_setting:
                toast("应用设置");
                break;
            case R.id.title_menu:
                toast("联系我们");
                break;
            case R.id.user_detail_layout:
                toast("到用户信息设置页面");
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_mine;
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CustomEvent event) {
        if (event.getType().equals(CustomEvent.LOGIN_EVENT)) {
            if (event.isActionDone()) {
                no_login_text.setVisibility(View.GONE);
                login_layout.setVisibility(View.VISIBLE);
                user_name.setText(CacheData.getDefault().getUserName());
                if (CacheData.getDefault().getUserGender().equals(Contents.MALE)) {
                    user_gender.setImageDrawable(getResources().getDrawable(R.drawable.ic_male));
                } else {
                    user_gender.setImageDrawable(getResources().getDrawable(R.drawable.ic_female));
                }

            } else {
                no_login_text.setVisibility(View.VISIBLE);
                login_layout.setVisibility(View.GONE);
            }
        }
    }
}
