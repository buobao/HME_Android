package com.hme.turman.api.service;

import com.hme.turman.api.ApiContents;
import com.hme.turman.api.bean.DemoBean;
import com.hme.turman.api.bean.ResponseBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lebro on 2016/10/30.
 */

public interface TestService {
    @GET(ApiContents.TEST)
    Observable<ResponseBean<String>> getTestText();
}
