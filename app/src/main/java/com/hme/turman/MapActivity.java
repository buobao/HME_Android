package com.hme.turman;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.utils.LocationUtil;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/10/28.
 */

public class MapActivity extends BaseActivity {
    @BindView(R.id.bmapView)
    MapView mMapView;

    @Override
    protected int getContentLayout() {
        return R.layout.act_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationUtil.init(this);
        LocationUtil.start(2,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}
