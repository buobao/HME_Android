package com.hme.turman.utils;

import android.app.Activity;
import android.content.Intent;

import com.hme.turman.ui.activity.LoginActivity;

import java.util.regex.Pattern;

/**
 * Created by diaoqf on 2016/11/1.
 */

public class UiUtil {
    private static String phone_patten = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";

    /**
     * 登录页跳转
     * @param activity
     */
    public static void goLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 电话号码验证
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        Pattern r = Pattern.compile(phone_patten);
        if (r.matcher(phone).matches()) {
            return true;
        }
        return false;
    }

    /**
     * 字符创是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str != null && !str.equals("")) {
            return true;
        }
         return false;
    }
}
