package com.hme.turman.utils;

import android.net.Uri;

import com.hme.turman.CacheData;
import com.orhanobut.logger.Logger;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * Created by diaoqf on 2016/11/8.
 */

public class RongUtil {
    public static void connectServer() {
        //连接融云
        RongIM.connect(CacheData.getDefault().getRongToken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Logger.i("onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Logger.i("connect to RongCloud complete:"+s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Logger.e("connect ro RongCloud error:"+errorCode);
            }
        });

        //设置用户信息
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return new UserInfo(CacheData.getDefault().getUserName(),CacheData.getDefault().getUserName(), Uri.parse(CacheData.getDefault().getUserPortrait()));
            }
        }, true);

        //设置接收消息时，设置附送消息用户信息
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                String sendUserId = message.getSenderUserId();

                //通过id从服务器获取用户信息
                // ===================
                // FLAG_TEST
                //这里设置为测试用户信息

                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String userId) {
                        return new UserInfo(sendUserId,"这个昵称是测试数据", Uri.parse("http://pic1a.nipic.com/2008-08-11/200881183312723_2.jpg"));
                    }
                }, true);


                return true;
            }
        });
    }


}
