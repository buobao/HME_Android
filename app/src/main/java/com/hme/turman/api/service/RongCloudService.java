package com.hme.turman.api.service;

import com.hme.turman.api.ApiContents;
import com.hme.turman.api.bean.rong.RongSimpleBean;
import com.hme.turman.api.bean.rong.RongTokenBean;
import com.hme.turman.api.bean.rong.RongUserStatusBean;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by diaoqf on 2016/11/8.
 */

public interface RongCloudService {

    /**
     * 获取融云token
     * @param params
     * @return
     */
    @POST(ApiContents.RONG_GET_TOKEN)
    @FormUrlEncoded
    Observable<RongTokenBean> getRongToken(@FieldMap Map<String, String> params);

    /**
     * 刷新用户信息
     * @param params
     * @return
     */
    @POST(ApiContents.RONG_REFRESH_USERINFO)
    @FormUrlEncoded
    Observable<RongSimpleBean> refreshUserInfo(@FieldMap Map<String, String> params);

    /**
     * 获取用户在线状态
     * @param userId
     * @return
     */
    @POST(ApiContents.RONG_GET_USER_ONLINE_STATE)
    @FormUrlEncoded
    Observable<RongUserStatusBean> getUserOnlineStatus(@Field("userId") String userId);
}
