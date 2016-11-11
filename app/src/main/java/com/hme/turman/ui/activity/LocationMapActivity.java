package com.hme.turman.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import io.rong.message.LocationMessage;

/**
 * Created by diaoqf on 2016/11/10.
 */

public class LocationMapActivity extends BaseActivity {
    static public final int REQUEST_CODE_ASK_PERMISSIONS = 101;

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
    @BindView(R.id.card_layout)
    CardView card_layout;

    private boolean isMovingMarker = false;
    private Handler handler = new Handler();

    private ValueAnimator animator = null;
    private LocationSource.OnLocationChangedListener listener;
    private LatLng myLocation = null;
    private LocationMessage mMsg;
    private BitmapDescriptor successDescripter;
    private Marker centerMarker;

    private AMap map;
    private UiSettings mUiSettings;

    private GeocodeSearch geocodeSearch;
    private AMapLocationClient mapLocationClient = null;
    private AMapLocationClientOption mapLocationClientOption = null;
    private AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                if (listener != null) {
                    listener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                }
                myLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());//获取当前位置经纬度
                location_tx.setText(aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName());//当前位置信息

                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                mMsg = LocationMessage.obtain(latitude, longitude, aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName(), getMapUrl(latitude, longitude));

                addChooseMarker();
            }
        }
    };

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("选择位置");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());
        mapView.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage("您需要在设置里打开位置权限。")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create().show();
                }
                return;
            }
        }

        initMap();
        setUpLocationStyle();
    }

    private void initMap(){
        if (map == null) {
            map = mapView.getMap();
        }
        map.setMapType(AMap.MAP_TYPE_NORMAL); //AMap.LOCATION_TYPE_LOCATE |
        map.setMyLocationEnabled(true);

        map.moveCamera(CameraUpdateFactory.zoomTo(15));    //设置地图的缩放级别

        map.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                if (mapLocationClient == null) {
                    //定位
                    mapLocationClient = new AMapLocationClient(LocationMapActivity.this);
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
                }
                mapLocationClient.startLocation();
            }

            @Override
            public void deactivate() {
                mapLocationClient.stopLocation();
            }
        });
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);

        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if (i == 0) {
                    if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null) {
                        endAnim();
                        centerMarker.setIcon(successDescripter);
                        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                        String formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                        String shortAdd = formatAddress.replace(regeocodeAddress.getProvince(), "").replace(regeocodeAddress.getCity(), "").replace(regeocodeAddress.getDistrict(), "");
                        location_tx.setText(shortAdd);
                        double latitude = regeocodeResult.getRegeocodeQuery().getPoint().getLatitude();
                        double longitude = regeocodeResult.getRegeocodeQuery().getPoint().getLongitude();
                        mMsg = LocationMessage.obtain(latitude, longitude, shortAdd, getMapUrl(latitude, longitude));
                    } else {
                        Logger.i("没有搜索到结果");
                    }
                } else {
                    toast("搜索失败,请检查网络");
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
    }

    private void setUpLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.img_location_now));
        myLocationStyle.strokeWidth(0);
        myLocationStyle.strokeColor(R.color.app_color);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        map.setMyLocationStyle(myLocationStyle);
    }

    private void animMarker() {
        isMovingMarker = false;
        if (animator != null) {
            animator.start();
            return;
        }
        animator = ValueAnimator.ofFloat(mapView.getHeight() / 2, mapView.getHeight() / 2 - 30);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(150);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                centerMarker.setPositionByPixels(mapView.getWidth() / 2, Math.round(value));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                centerMarker.setIcon(successDescripter);
            }
        });
        animator.start();
    }

    private Uri getMapUrl(double x, double y) {
        String url = "http://restapi.amap.com/v3/staticmap?location=" + y + "," + x +
                "&zoom=16&scale=2&size=408*240&markers=mid,,A:" + y + ","
                + x + "&key=" + "ee95e52bf08006f63fd29bcfbcf21df0";
        return Uri.parse(url);
    }

    private void endAnim() {
        if (animator != null && animator.isRunning())
            animator.end();
    }

    private void setMovingMarker() {
        if (isMovingMarker)
            return;

        isMovingMarker = true;
        centerMarker.setIcon(successDescripter);
        hideLocationView();
    }

    private void hideLocationView() {
        ObjectAnimator animLocation = ObjectAnimator.ofFloat(card_layout, "TranslationY", -card_layout.getHeight() * 2);
        animLocation.setDuration(200);
        animLocation.start();
    }

    private void showLocationView() {
        ObjectAnimator animLocation = ObjectAnimator.ofFloat(card_layout, "TranslationY", 0);
        animLocation.setDuration(200);
        animLocation.start();
    }

    private void addChooseMarker() {
        //加入自定义标签
        MarkerOptions centerMarkerOption = new MarkerOptions().position(myLocation).icon(successDescripter);
        centerMarker = map.addMarker(centerMarkerOption);
        centerMarker.setPositionByPixels(mapView.getWidth() / 2, mapView.getHeight() / 2);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CameraUpdate update = CameraUpdateFactory.zoomTo(17f);
                map.animateCamera(update, 1000, new AMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        map.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                            @Override
                            public void onCameraChange(CameraPosition cameraPosition) {
                                if (centerMarker != null) {
                                    setMovingMarker();
                                }
                            }

                            @Override
                            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                                LatLonPoint point = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                                RegeocodeQuery query = new RegeocodeQuery(point, 50, GeocodeSearch.AMAP);
                                geocodeSearch.getFromLocationAsyn(query);
                                if (centerMarker != null) {
                                    animMarker();
                                }
                                showLocationView();
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        }, 1000);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        initMap();
                        setUpLocationStyle();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}





























