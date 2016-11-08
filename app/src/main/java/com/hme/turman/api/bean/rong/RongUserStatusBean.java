package com.hme.turman.api.bean.rong;

import java.io.Serializable;

/**
 * Created by diaoqf on 2016/11/8.
 */

public class RongUserStatusBean implements Serializable {
    private int code;
    private String status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
