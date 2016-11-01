package com.hme.turman.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.hme.turman.CacheData;
import com.orhanobut.logger.Logger;

/**
 * Created by diaoqf on 2016/11/1.
 */

public class LocationUtil {
    private static Context context;
    private static AMapLocationClient mapLocationClient = null;
    private static AMapLocationClientOption mapLocationClientOption = null;
    private static AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Logger.i("当前城市:"+aMapLocation.getCity()+"\n当前位置:"+aMapLocation.getAddress()+"\n当前经纬度:"+aMapLocation.getLatitude()+","+aMapLocation.getLongitude());
            CacheData.getDefault().setUserCurrentAddress(aMapLocation.getAddress());
            CacheData.getDefault().setUserCurrentLocation(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
        }
    };

    public static void init(Context context){
        mapLocationClient = new AMapLocationClient(context);
        mapLocationClient.setLocationListener(mapLocationListener);

        mapLocationClientOption = new AMapLocationClientOption();
        //高精度模式 (低功耗模式:Battery_Saving, Device_Sensors)
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔
        mapLocationClientOption.setInterval(10000);
        //设置定位是否返回地址信息
        mapLocationClientOption.setNeedAddress(true);
        //设置超时时间
        mapLocationClientOption.setHttpTimeOut(10000);

        mapLocationClient.setLocationOption(mapLocationClientOption);

        //开始定位
        mapLocationClient.startLocation();
    }

}





































