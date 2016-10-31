package com.hme.turman.ui.fragment;

import android.os.Bundle;

import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;
import com.hme.turman.widgets.FormItemLayout;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.my_message)
    FormItemLayout my_message;

    @Override
    protected void init(Bundle savedInstanceState) {
        my_message.setOnItemClickListener(v->{
            toast("我的动态");
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_mine;
    }
}
