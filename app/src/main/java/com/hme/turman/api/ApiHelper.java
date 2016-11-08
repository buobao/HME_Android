package com.hme.turman.api;

import com.hme.turman.Contents;
import com.hme.turman.api.bean.rong.RongSimpleBean;
import com.hme.turman.api.bean.rong.RongTokenBean;
import com.hme.turman.api.bean.rong.RongUserStatusBean;
import com.hme.turman.api.service.RongCloudService;
import com.hme.turman.api.service.TestService;
import com.hme.turman.api.service.UserAccountService;
import com.hme.turman.utils.RetrofitUtil;
import com.hme.turman.utils.SHA1;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lebro on 2016/10/30.
 */
public class ApiHelper {
    private static UserAccountService userAccountService;
    private static RongCloudService rongCloudService;


    /**
     * 用户中心api service
     * @return
     */
    public static UserAccountService getUserAccountService(){
        if (userAccountService == null) {
            userAccountService = RetrofitHelper.createRetrofit(null,false,ApiContents.BASE_URL).create(UserAccountService.class);
        }
        return userAccountService;
    }

    /**
     * 融云api service
     * @return
     */
    public static RongCloudService getRongCloudService() {
        if (rongCloudService == null) {
            Map<String, String> headers = new HashMap<>();
            long nonce = System.currentTimeMillis();
            long timestamp = System.currentTimeMillis();
            headers.put("App-Key",Contents.RONG_KEY);
            headers.put("nonce",nonce+"");
            headers.put("timestamp",timestamp+"");
            headers.put("signature", SHA1.encode(Contents.RONG_SECRET+nonce+timestamp));
            rongCloudService = RetrofitHelper.createRetrofit(headers, false, ApiContents.RONG_URL).create(RongCloudService.class);
        }
        return rongCloudService;
    }

    /**
     * 获取融云token
     * @param params
     * @return
     */
    public static Observable<String> getRongToken(Map<String,String> params){
        return getRongCloudService().getRongToken(params)
                .compose(RetrofitUtil.applyIoSchedulers())
                .compose(new Observable.Transformer<RongTokenBean, String>() {
                    @Override
                    public Observable<String> call(Observable<RongTokenBean> result) {
                        return result.flatMap(new Func1<RongTokenBean, Observable<String>>() {
                            @Override
                            public Observable<String> call(RongTokenBean rongToken) {
                                if (rongToken != null && rongToken.getCode() == 200) {
                                    return Observable.just(rongToken.getToken());
                                }
                                return Observable.empty();
                            }
                        });
                    }
                });
    }

    /**
     * 刷新融云用户信息
     * @param params
     * @return
     */
    public static void refreshUserInfo(Map<String, String> params) {
        getRongCloudService().refreshUserInfo(params)
                .compose(RetrofitUtil.applyIoSchedulers())
                .subscribe(result->{
                    if (result != null && result.getCode() == 200) {
                        Logger.i("RongCloud:刷新用户信息成功.");
                    } else {
                        Logger.i("RongCloud:刷新用户信息失败.");
                    }
                }, throwable -> throwable.printStackTrace());
    }

    /**
     * 获取用户在线状态
     * @param userId
     * @return
     */
    public static Observable<RongUserStatusBean> getUserOnlineStatus(String userId) {
        return getRongCloudService().getUserOnlineStatus(userId)
                .compose(RetrofitUtil.applyIoSchedulers());
    }





    //test
    private static TestService testService;
    private static TestService getTestService() {
        if (userAccountService == null) {
            testService = RetrofitHelper.createRetrofit(null,false,ApiContents.TEST_URL).create(TestService.class);
        }
        return testService;
    }

    public static Observable<String> getTestText() {
        return getTestService().getTestText()
                .compose(RetrofitUtil.applyIoSchedulers())
                .compose(RetrofitUtil.handleResult());
    }
}
