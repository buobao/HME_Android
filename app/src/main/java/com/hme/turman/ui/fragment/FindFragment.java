package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
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

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;
    @BindView(R.id.listview)
    ListView listView;

    private int currentType;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("发现");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("地图");
        currentType = LIST_VIEW;

        swipe_refresh_layout.setOnRefreshListener(()->{

        });

        swipe_refresh_layout.setColorSchemeResources(
                R.color.app_color, R.color.app_color_1,
                R.color.app_color_2, R.color.app_color_3);

        listView.setOnItemClickListener((parent, view, position, id) -> {

        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_find;
    }
}
