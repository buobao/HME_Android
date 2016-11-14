package com.hme.turman;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.hme.turman.base.BaseActivity;

import butterknife.BindView;


/**
 * Created by diaoqf on 2016/10/28.
 */

public class MapActivity extends BaseActivity {
    @BindView(R.id.map)
    MapView mapView;

    private AMap map;

    @Override
    protected int getContentLayout() {
        return R.layout.act_map;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();
        map.setMapType(AMap.MAP_TYPE_NIGHT); //AMap.LOCATION_TYPE_LOCATE |
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
