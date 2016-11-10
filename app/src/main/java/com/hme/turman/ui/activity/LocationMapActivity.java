package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/10.
 */

public class LocationMapActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.location_tx)
    TextView location_tx;

    private AMap map;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("选择位置");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());

        map = mapView.getMap();
        map.setMapType(AMap.MAP_TYPE_NIGHT); //AMap.LOCATION_TYPE_LOCATE |

        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_location_map;
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





























