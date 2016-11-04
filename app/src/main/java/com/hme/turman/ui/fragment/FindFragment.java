package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.api.ApiHelper;
import com.hme.turman.base.BaseFragment;

import butterknife.BindView;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class FindFragment extends BaseFragment {

    public static final int LIST_VIEW = 0;  //显示列表
    public static final int MAP_VIEW = 1;   //显示地图

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;
    @BindView(R.id.listview)
    ListView listView;

    private int currentType;

    private int currentPage; //当前加载数据的页码
    private int totalCount; //当前所有数据条数

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("发现");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("地图");
        currentType = LIST_VIEW;
        currentPage = 1;
        totalCount = 0;

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
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        loadData(false);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    lastItem = firstVisibleItem + visibleItemCount - 1 ;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_find;
    }

    private void loadData(boolean isReload) {
        if (isReload) {
            currentPage = 1;
        }

        //服务器获取数据
//        addTask(ApiHelper.getTestText().subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//            }
//        }));


    }
}






































