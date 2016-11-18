package com.hme.turman.ui.entity.event;

import java.util.Map;
import java.util.Objects;

/**
 * Created by lebro on 2016/11/1.
 */

public class CustomEvent {
    public static final String LOGIN_EVENT = "login_event";   //登录时间
    public static final String UPDATE_USER_INFO = "update_user_info";   //用户信息更新

    //消息类型
    private String type;
    //表示操作失败或成功的标志，可以是登录或者登出等
    private boolean actionDone;
    //传递的参数列表
    private Map<String, Objects> params;

    public CustomEvent(String type){
        this.type = type;
    }

    public CustomEvent(String type, boolean actionDone, Map<String, Objects> params) {
        this.type = type;
        this.actionDone = actionDone;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActionDone() {
        return actionDone;
    }

    public void setActionDone(boolean actionDone) {
        this.actionDone = actionDone;
    }

    public Map<String, Objects> getParams() {
        return params;
    }

    public void setParams(Map<String, Objects> params) {
        this.params = params;
    }
}
