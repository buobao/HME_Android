package com.hme.turman.api.bean;

import java.io.Serializable;

/**
 * Created by lebro on 2016/10/30.
 */

public class ResponseBean<T> implements Serializable {
    private boolean success;
    private String message;
    private int code;
    private T result;
    private long total;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
