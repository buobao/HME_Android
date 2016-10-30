package com.hme.turman.api;

import com.hme.turman.api.service.TestService;
import com.hme.turman.api.service.UserAccountService;

/**
 * Created by lebro on 2016/10/30.
 */
public class ApiHelper {
    private static UserAccountService userAccountService;


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



    //test
    private static TestService testService;
    public static TestService getTestService() {
        if (userAccountService == null) {
            testService = RetrofitHelper.createRetrofit(null,false,ApiContents.TEST_URL).create(TestService.class);
        }
        return testService;
    }
}
