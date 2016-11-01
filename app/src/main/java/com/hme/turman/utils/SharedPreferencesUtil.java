package com.hme.turman.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.hme.turman.Contents;

import java.util.Set;

/**
 * app 基本缓存数据存储工具
 * Created by diaoqf on 2016/7/8.
 */
public class SharedPreferencesUtil {

    private static Context mContext;

    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences.Editor mSharedEditor;

    public static boolean isInited(){
        return mSharedPreferences != null;
    }

    public static void init(Context context){
        mContext = context;
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(Contents.SP_NAME, Activity.MODE_PRIVATE);
            mSharedEditor = mSharedPreferences.edit();
        }
    }

    public static void saveString(String name, String value) {
        mSharedEditor.putString(name, value);
        mSharedEditor.commit();
    }

    public static void saveInt(String name, int value) {
        mSharedEditor.putInt(name, value);
        mSharedEditor.commit();
    }

    public static void saveFloat(String name, float value) {
        mSharedEditor.putFloat(name, value);
        mSharedEditor.commit();
    }

    public static void saveLong(String name, Long value) {
        mSharedEditor.putLong(name, value);
        mSharedEditor.commit();
    }

    public static void saveBoolean(String name, boolean value) {
        mSharedEditor.putBoolean(name, value);
        mSharedEditor.commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void saveStringSet(String name, Set<String> sets){
        mSharedEditor.putStringSet(name, sets);
        mSharedEditor.commit();
    }

    public static String getString(String name) {
        return mSharedPreferences.getString(name, "");
    }

    public static float getFloat(String name) {
        return mSharedPreferences.getFloat(name,0.0f);
    }

    public static long getLong(String name) {
        return mSharedPreferences.getLong(name, 0);
    }

    public static boolean getBoolean(String name) {
        return mSharedPreferences.getBoolean(name,false);
    }

    public static int getInt(String name) {
        return mSharedPreferences.getInt(name, -1);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String name) {
        return mSharedPreferences.getStringSet(name,null);
    }
}



















