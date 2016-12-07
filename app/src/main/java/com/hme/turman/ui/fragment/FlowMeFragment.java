package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/10/31.
 */
public class FlowMeFragment extends BaseFragment {

    @Override
    protected String getPageTitle() {
        return "跟我走";
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_flow_me;
    }
}
