package com.hme.turman.api;

/**
 * Created by lebro on 2016/10/30.
 */

public class ApiContents {
    //base init params
    public static final int DEFAULT_TIMEOUT = 5;
    public static final int DEFAULT_WRITE_TIMEOUT = 6;
    public static final int DEFAULT_READ_TIMEOUT = 8;


    //urls
    public static final String BASE_URL = "https://apish.centanet.com/v3/zfapi/json/reply/";
    //rong cloud server
    public static final String RONG_URL = "https://api.cn.ronghub.com";


    //apis
    public static final String DEMO_REQUEST = "UserInfoListRequest";
    //获取融云token
    public static final String RONG_GET_TOKEN = "user/getToken.json";
    //刷新用户信息
    public static final String RONG_REFRESH_USERINFO = "/user/refresh.json";
    //获取用户在线状态
    public static final String RONG_GET_USER_ONLINE_STATE = "/user/checkOnline.json";





    //test
    public static final String TEST_URL = "http://192.168.31.167:8089/travel-agent/users/";
    public static final String TEST = "test";
}
