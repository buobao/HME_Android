package com.hme.turman.api.bean.rong;

import java.io.Serializable;

/**
 * Created by diaoqf on 2016/11/8.
 */

public class RongTokenBean implements Serializable {

    /**
     * code : 200
     * userId : jlk456j5
     * token : sfd9823ihufi
     */

    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
