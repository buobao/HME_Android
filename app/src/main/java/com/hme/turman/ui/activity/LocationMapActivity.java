package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.hme.turman.CacheData;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.orhanobut.logger.Logger;

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
    @BindView(R.id.selector)
    ImageView selector;

    private AMap map;
    private UiSettings mUiSettings;

    private AMapLocationClient mapLocationClient = null;
    private AMapLocationClientOption mapLocationClientOption = null;
    private AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Logger.i("当前城市:"+aMapLocation.getCity()+"\n当前位置:"+aMapLocation.getAddress()+"\n当前经纬度:"+aMapLocation.getLatitude()+","+aMapLocation.getLongitude());
            CacheData.getDefault().setUserCurrentAddress(aMapLocation.getAddress());
            CacheData.getDefault().setUserCurrentLocation(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
            map.moveCamera(CameraUpdateFactory.changeLatLng(CacheData.getDefault().getUserCurrentLocation()));
            mapLocationClient.stopLocation();
            location_tx.setText(CacheData.getDefault().getUserCurrentAddress());
        }
    };

    @Override
    protected void init(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);

        title.setText("选择位置");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());

        map = mapView.getMap();
        map.setMapType(AMap.MAP_TYPE_NORMAL); //AMap.LOCATION_TYPE_LOCATE |
        map.setMyLocationEnabled(true);

        map.moveCamera(CameraUpdateFactory.zoomTo(15));    //设置地图的缩放级别

        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.sel_ic_location));
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);
        map.setMyLocationStyle(myLocationStyle);

        mUiSettings = map.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(false);

        //定位
        mapLocationClient = new AMapLocationClient(this);
        mapLocationClient.setLocationListener(mapLocationListener);
        mapLocationClientOption = new AMapLocationClientOption();
        //高精度模式 (低功耗模式:Battery_Saving, Device_Sensors)
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔
        mapLocationClientOption.setInterval(60000);
        //设置定位是否返回地址信息
        mapLocationClientOption.setNeedAddress(true);
        //设置超时时间
        mapLocationClientOption.setHttpTimeOut(10000);
        mapLocationClient.setLocationOption(mapLocationClientOption);
        mapLocationClient.startLocation();
    }

    private void addMylocationMarker() {
        map.moveCamera(CameraUpdateFactory.changeLatLng(CacheData.getDefault().getUserCurrentLocation()));
        //点击定位按钮 能够将地图的中心移动到定位点
//        mListener.onLocationChanged(amapLocation);

        MarkerOptions options = new MarkerOptions();
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.sel_ic_location));

        options.position(CacheData.getDefault().getUserCurrentLocation());
        StringBuffer buffer = new StringBuffer();
        buffer.append(CacheData.getDefault().getUserCurrentAddress());
        //标题
        options.title(buffer.toString());
        //设置多少帧刷新一次图片资源
        options.period(60);

        map.addMarker(options);
        location_tx.setText(CacheData.getDefault().getUserCurrentAddress());
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





























