package com.hme.turman;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushInterface;

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
        //百度地图初始化
        SDKInitializer.initialize(this);
        //logger
        Logger.init(BuildConfig.LOGGER_TAG)
                .methodCount(1)
                .logLevel(BuildConfig.DEBUG? LogLevel.FULL : LogLevel.NONE)
                .methodCount(3);


        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        JPushInterface.setLatestNotificationNumber(this,4);  //最多显示4条通知栏信息
    }
}
