package com.hme.turman.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
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
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import io.rong.message.LocationMessage;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by AMing on 16/5/9.
 * Company RongCloud
 */
public class LocationMapActivity extends BaseActivity implements LocationSource, GeocodeSearch.OnGeocodeSearchListener, AMapLocationListener, AMap.OnCameraChangeListener {
    static public final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private AMap aMap;
    private Handler handler = new Handler();
    private LocationSource.OnLocationChangedListener listener;
    private LatLng myLocation = null;
    private Marker centerMarker;
    private boolean isMovingMarker = false;
    private BitmapDescriptor successDescripter;
    private GeocodeSearch geocodeSearch;
    private LocationMessage mMsg;
    private boolean isPerview;

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.card_layout)
    CardView card_layout;
    @BindView(R.id.location_tx)
    TextView location_tx;

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
                Logger.i("myLocation:"+myLocation.latitude+","+myLocation.longitude);
                location_tx.setText(aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName());//当前位置信息

                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                mMsg = LocationMessage.obtain(latitude, longitude, aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName(), getMapUrl(latitude, longitude));

                addChooseMarker();
            }
        }
    };

    @Override
    protected String getPageTitle() {
        return "选择地址";
    }

    @Override
    protected boolean showRightMenu() {
        return true;
    }

    @Override
    protected String getRightMenuTitle() {
        return "确定";
    }

    @Override
    protected View.OnClickListener getRightMenuListener() {
        //获取地址返回
        return v->{
            if (mApplication.getLocationCallback() != null) {
                if (mMsg != null) {
                    mApplication.getLocationCallback().onSuccess(mMsg);
                } else {
                    mApplication.getLocationCallback().onFailure("定位失败");
                }
                mApplication.setLocationCallback(null);
            } else {
                //其他获取地址处理
            }
            finish();
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void init(Bundle savedInstanceState) {
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

        initAmap();
        setUpLocationStyle();
    }

    private void initAmap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        if (getIntent().hasExtra("location")) {
            isPerview = true;
            mMsg = getIntent().getParcelableExtra("location");
            card_layout.setVisibility(View.GONE);

            aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(new LatLng(mMsg.getLat(), mMsg.getLng())).title(mMsg.getPoi())
                    .snippet(mMsg.getLat() + "," + mMsg.getLng()).draggable(false));
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(new LatLng(mMsg.getLat(), mMsg.getLng())).zoom(16).bearing(0).tilt(30).build()));
            return;
        }

        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(15);//设置缩放监听
        aMap.moveCamera(cameraUpdate);

        successDescripter = BitmapDescriptorFactory.fromResource(R.drawable.sel_ic_location_big);
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;
        if (mapLocationClient == null) {
            //定位
            mapLocationClient = new AMapLocationClient(LocationMapActivity.this);
            mapLocationClient.setLocationListener(mapLocationListener);
            mapLocationClientOption = new AMapLocationClientOption();
            //高精度模式 (低功耗模式:Battery_Saving, Device_Sensors)
            mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔
//            mapLocationClientOption.setInterval(1000);
            mapLocationClientOption.setOnceLocation(true);
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
        if (mapLocationClient != null) {
            mapLocationClient.stopLocation();
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
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
                toast("没有搜索到结果");
            }
        } else {
            toast("搜索失败,请检查网络");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


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
            Logger.i(aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName() + latitude + "----" + longitude);


            addChooseMarker();
        }
    }

    private void addChooseMarker() {
        //加入自定义标签
        MarkerOptions centerMarkerOption = new MarkerOptions().position(myLocation).icon(successDescripter);
        centerMarker = aMap.addMarker(centerMarkerOption);
        centerMarker.setPositionByPixels(mapView.getWidth() / 2, mapView.getHeight() / 2);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CameraUpdate update = CameraUpdateFactory.zoomTo(17f);
                aMap.animateCamera(update, 1000, new AMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        aMap.setOnCameraChangeListener(LocationMapActivity.this);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        }, 1000);
    }

    private void setMovingMarker() {
        if (isMovingMarker)
            return;

        isMovingMarker = true;
        centerMarker.setIcon(successDescripter);
        hideLocationView();
    }


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


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_location_map;
    }

    private ValueAnimator animator = null;

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

    private void endAnim() {
        if (animator != null && animator.isRunning())
            animator.end();
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

    private void setUpLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.img_location_now));
        myLocationStyle.strokeWidth(0);
        myLocationStyle.strokeColor(R.color.app_color);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        aMap.setMyLocationStyle(myLocationStyle);
    }

    private Uri getMapUrl(double x, double y) {
        String url = "http://restapi.amap.com/v3/staticmap?location=" + y + "," + x +
                "&zoom=16&scale=2&size=408*240&markers=mid,,A:" + y + ","
                + x + "&key=" + "ee95e52bf08006f63fd29bcfbcf21df0";
        Logger.i("getMapUrl:%s", url);
        return Uri.parse(url);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        initAmap();
                        setUpLocationStyle();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
