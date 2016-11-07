package com.hme.turman;

import com.amap.api.maps.model.LatLng;
import com.hme.turman.utils.SharedPreferencesUtil;

/**
 * Created by diaoqf on 2016/10/28.
 */

public class CacheData {
    private static volatile CacheData cacheData;

    private CacheData(){}

    public static CacheData getDefault() {
        if (cacheData == null) {
            synchronized (CacheData.class) {
                if (cacheData == null) {
                    cacheData = new CacheData();
                    cacheData.setIsLogin(SharedPreferencesUtil.getBoolean(Contents.IS_LOGIN));
                    if (cacheData.isLogin()) {
                        cacheData.setUserName(SharedPreferencesUtil.getString(Contents.USER_NAME));
                        cacheData.setRongToken(SharedPreferencesUtil.getString(Contents.RONG_TOKEN));
                        cacheData.setUserPhone(SharedPreferencesUtil.getString(Contents.USER_PHONE));
                        cacheData.setUserPsw(SharedPreferencesUtil.getString(Contents.USER_PSW));
                        cacheData.setUserPortrait(SharedPreferencesUtil.getString(Contents.USER_PORTRAIT));
                        cacheData.setUserToken(SharedPreferencesUtil.getString(Contents.USER_TOKEN));
                        cacheData.setUserAddress(SharedPreferencesUtil.getString(Contents.USER_ADDRESS));
                        cacheData.setUserLocation(new LatLng(Double.parseDouble(SharedPreferencesUtil.getString(Contents.USER_LAT)), Double.parseDouble(SharedPreferencesUtil.getString(Contents.USER_LNG))));
//                        cacheData.setUserCurrentLocation();
                        cacheData.setUserWorkAddress(SharedPreferencesUtil.getString(Contents.USER_WORK_ADDRESS));
                        cacheData.setUserWorkLocation(new LatLng(Double.parseDouble(SharedPreferencesUtil.getString(Contents.USER_WORK_LAT)),Double.parseDouble(SharedPreferencesUtil.getString(Contents.USER_WORK_LNG))));

                        //这里需要检测网络
                    }
                }
            }
        }

        return cacheData;
    }


    /**
     * 融云token
     */
    private String rong_token;

    /**
     * 当前是否开启网络
     */
    private boolean hasInternet;

    /**
     * 是否登录
     */
    private boolean is_login;
    /**
     * 登录人名称
     */
    private String user_name;
    /**
     * 登录人性别
     */
    private String user_gender;
    /**
     * 登录人电话
     */
    private String user_phone;
    /**
     * 登录人密码
     */
    private String user_psw;
    /**
     * 登录人token
     */
    private String user_token;
    /**
     * 登录人头像文件地址
     */
    private String user_portrait;
    /**
     * 登录人家庭地址
     */
    private String user_address;
    /**
     * 登录人家庭坐标
     */
    private LatLng user_location;
    /**
     * 登录人当前位置
     */
    private String user_current_address;
    /**
     * 登录人当前坐标
     */
    private LatLng user_current_location;
    /**
     * 登录人工作地址
     */
    private String user_work_address;
    /**
     * 登录人工作坐标
     */
    private LatLng user_work_location;

    public boolean isLogin() {
        return is_login;
    }

    public void setIsLogin(boolean is_login) {
        this.is_login = is_login;
        SharedPreferencesUtil.saveBoolean(Contents.IS_LOGIN, is_login);
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
        SharedPreferencesUtil.saveString(Contents.USER_NAME, user_name);
    }

    public String getUserPhone() {
        return user_phone;
    }

    public void setUserPhone(String user_phone) {
        this.user_phone = user_phone;
        SharedPreferencesUtil.saveString(Contents.USER_PHONE, user_phone);
    }

    public String getUserPsw() {
        return user_psw;
    }

    public void setUserPsw(String user_psw) {
        this.user_psw = user_psw;
        SharedPreferencesUtil.saveString(Contents.USER_PSW, user_psw);
    }

    public String getUserToken() {
        return user_token;
    }

    public void setUserToken(String user_token) {
        this.user_token = user_token;
        SharedPreferencesUtil.saveString(Contents.USER_TOKEN, user_token);
    }

    public String getUserPortrait() {
        return user_portrait;
    }

    public void setUserPortrait(String user_portrait) {
        this.user_portrait = user_portrait;
        SharedPreferencesUtil.saveString(Contents.USER_PORTRAIT, user_portrait);
    }

    public String getUserAddress() {
        return user_address;
    }

    public void setUserAddress(String user_address) {
        this.user_address = user_address;
        SharedPreferencesUtil.saveString(Contents.USER_ADDRESS, user_address);
    }

    public LatLng getUserLocation() {
        return user_location;
    }

    public void setUserLocation(LatLng user_location) {
        this.user_location = user_location;
        SharedPreferencesUtil.saveString(Contents.USER_LAT, user_location.latitude + "");
        SharedPreferencesUtil.saveString(Contents.USER_LNG, user_location.longitude + "");
    }

    public LatLng getUserCurrentLocation() {
        return user_current_location;
    }

    public void setUserCurrentLocation(LatLng user_current_location) {
        this.user_current_location = user_current_location;
    }

    public String getUserWorkAddress() {
        return user_work_address;
    }

    public void setUserWorkAddress(String user_work_address) {
        this.user_work_address = user_work_address;
        SharedPreferencesUtil.saveString(Contents.USER_WORK_ADDRESS, user_work_address);
    }

    public LatLng getUserWorkLocation() {
        return user_work_location;
    }

    public void setUserWorkLocation(LatLng user_work_location) {
        this.user_work_location = user_work_location;
        SharedPreferencesUtil.saveString(Contents.USER_WORK_LAT, user_work_location.latitude + "");
        SharedPreferencesUtil.saveString(Contents.USER_WORK_LNG, user_work_location.longitude + "");
    }

    public String getUserCurrentAddress() {
        return user_current_address;
    }

    public void setUserCurrentAddress(String user_current_address) {
        this.user_current_address = user_current_address;
    }

    public String getUserGender() {
        return user_gender;
    }

    public void setUserGender(String user_gender) {
        this.user_gender = user_gender;
        SharedPreferencesUtil.saveString(Contents.USER_GENDER,user_gender);
    }

    public boolean isHasInternet() {
        return hasInternet;
    }

    public void setHasInternet(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    public String getRongToken() {
        return rong_token;
    }

    public void setRongToken(String rong_token) {
        this.rong_token = rong_token;
        SharedPreferencesUtil.saveString(Contents.RONG_TOKEN,rong_token);
    }
}
