package com.hme.turman;

import android.app.Application;
import android.content.Context;

import com.hme.turman.utils.LocationUtil;
import com.hme.turman.utils.SharedPreferencesUtil;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

/**
 * Created by diaoqf on 2016/10/28.
 */

public class HmeApplication extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //shared preferences init
        SharedPreferencesUtil.init(this);
        //logger
        Logger.init(BuildConfig.LOGGER_TAG)
                .methodCount(1)
                .logLevel(BuildConfig.DEBUG? LogLevel.FULL : LogLevel.NONE)
                .methodCount(3);

        //极光推送初始化
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        JPushInterface.setLatestNotificationNumber(this,4);  //最多显示4条通知栏信息

        //初始化高德定位
        LocationUtil.init(this);
        //融云初始化
        RongIM.init(this);
    }
}































