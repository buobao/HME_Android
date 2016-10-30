package com.hme.turman.api.service;

import com.hme.turman.api.ApiContents;
import com.hme.turman.api.bean.DemoBean;
import com.hme.turman.api.bean.ResponseBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lebro on 2016/10/30.
 */

public interface UserAccountService {

    @GET(ApiContents.DEMO_REQUEST)
    Observable<ResponseBean<List<DemoBean>>> getUserInfoList(@QueryMap(encoded = true)Map<String, String> params);
}
