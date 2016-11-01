package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;

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

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("我的");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("联系我们");
        title_menu.setOnClickListener(v->{toast("联系我们");});

        user_detail_layout.setOnClickListener(v->{toast("到用户信息设置页面");});
    }

    @OnClick({R.id.my_message,R.id.my_comments,R.id.my_confirm,R.id.my_wallet,
            R.id.my_help_record,R.id.my_seek_help_record,R.id.my_setting})
    public void onMenuClick(View v){
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
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_mine;
    }
}
