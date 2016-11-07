package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.hme.turman.R;
import com.hme.turman.api.ApiHelper;
import com.hme.turman.api.bean.HelpEventBean;
import com.hme.turman.base.BaseFragment;
import com.hme.turman.ui.adapter.HelpEventAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class FindFragment extends BaseFragment {
    //列表刷新状态
    public static final int STATE_REFRESH = 0;
    public static final int STATE_UNREFRESH = 1;

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

    @BindView(R.id.map)
    MapView mapView;
    private AMap map;
    private UiSettings mUiSettings;

    private int currentType=LIST_VIEW;
    private int mState;

    private int currentPage; //当前加载数据的页码
    private int totalCount; //当前所有数据条数

    private HelpEventAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("发现");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("地图");
        title_menu.setOnClickListener(v->{
            currentType = currentType == LIST_VIEW ? MAP_VIEW:LIST_VIEW;
            if (currentType == MAP_VIEW) {
                currentType = MAP_VIEW;
                swipe_refresh_layout.setVisibility(View.GONE);
            } else {
                currentType = LIST_VIEW;
                swipe_refresh_layout.setVisibility(View.VISIBLE);
            }
        });
        currentPage = 1;
        totalCount = -1;
        mState = STATE_UNREFRESH;

        swipe_refresh_layout.setOnRefreshListener(()->{
            if (mState == STATE_REFRESH) {
                return;
            }
            // 设置顶部正在刷新
            listView.setSelection(0);
            setSwipeRefreshLoadingState();
            currentPage = 1;
            mState = STATE_REFRESH;
            loadData(true);
        });

        swipe_refresh_layout.setColorSchemeResources(
                R.color.app_color, R.color.app_color_1,
                R.color.app_color_2, R.color.app_color_3);

        mAdapter = new HelpEventAdapter();
        mAdapter.setData(new ArrayList<>());
        listView.setAdapter(mAdapter);
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
        //初始化加载数据
        loadData(true);

        initMap(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_find;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 地图初始化
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState){
        //if (map == null) {
            mapView.onCreate(savedInstanceState);

            map = mapView.getMap();
            map.setMapType(AMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.setLocationSource(new LocationSource() {
                @Override
                public void activate(OnLocationChangedListener onLocationChangedListener) {

                }

                @Override
                public void deactivate() {

                }
            });
            mUiSettings = map.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setCompassEnabled(true);
            mUiSettings.setScaleControlsEnabled(true);
            mUiSettings.setMyLocationButtonEnabled(true);
       // }

    }

    /** 设置顶部正在加载的状态 */
    private void setSwipeRefreshLoadingState() {
        if (swipe_refresh_layout != null) {
            swipe_refresh_layout.setRefreshing(true);
            // 防止多次重复刷新
            swipe_refresh_layout.setEnabled(false);
        }
    }

    private void loadData(boolean isReload) {
        if (isReload) {
            currentPage = 1;
        }

        if (totalCount > 0 && mAdapter.getCount() == totalCount) {
            toast("已加载所有数据");
            return;
        }

        HelpEventBean bean = new HelpEventBean();
        bean.setUserImage("http://h.hiphotos.baidu.com/zhidao/pic/item/0eb30f2442a7d9334f268ca9a84bd11372f00159.jpg");
        bean.setTitle("出来放放风！");
        bean.setPushDate("1分钟前");
        bean.setContent("今天阳光好出来晒太阳");
        bean.setAddress("陆家嘴2012号");
        bean.setContentImage(new ArrayList(){
            {
                add("http://desk.fd.zol-img.com.cn/t_s960x600c5/g2/M00/0B/06/Cg-4WlWTSv-IDY_IABZX96sL1csAAGRXQNA9dcAFlgP057.jpg");
                add("http://desk.fd.zol-img.com.cn/t_s960x600c5/g2/M00/0F/09/Cg-4WVU1yCWII32GAAorcDNigN0AAB-TgFcZjsACiuI897.jpg");
                add("http://dl.bizhi.sogou.com/images/2015/02/15/1090141.jpg");
                add("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/05/0E/ChMkJlerCWyIbCAaAAZI9tT87kUAAUU5AOBAnEABkkO122.jpg");
                add("http://pic61.nipic.com/file/20150304/7487939_230316431000_2.jpg");
            }
        });
        List<HelpEventBean> list = new ArrayList<>();
        for (int i = 0;i<10;i++) {
            list.add(bean);
        }
        mAdapter.addData(list);
        totalCount = 30;
        currentPage++;
        if (swipe_refresh_layout.isRefreshing()) {
            swipe_refresh_layout.setRefreshing(false);
            mState = STATE_UNREFRESH;   //设置列表可刷新
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






































