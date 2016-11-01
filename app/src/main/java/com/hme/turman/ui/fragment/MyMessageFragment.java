package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class MyMessageFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("消息");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_my_message;
    }
}
