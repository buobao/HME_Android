package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class FindFragment extends BaseFragment {

    public static final int LIST_VIEW = 0;
    public static final int MAP_VIEW = 1;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;

    private int currentType;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("发现");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("地图");
        currentType = LIST_VIEW;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_find;
    }
}
