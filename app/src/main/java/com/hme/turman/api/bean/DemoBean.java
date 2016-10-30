package com.hme.turman.api.bean;

import java.io.Serializable;

/**
 * Created by lebro on 2016/10/30.
 */

public class DemoBean implements Serializable {


    /**
     * UserId : String
     * NickName : String
     * Email : String
     * Phone : String
     * LastLoginDateTime : /Date(-62135596800000-0000)/
     * UpdateTime : /Date(-62135596800000-0000)/
     * CreateTime : /Date(-62135596800000-0000)/
     * CreateTime2 : -62135625600
     * UpdateTime2 : -62135625600
     * Status : false
     * SinaAccount : String
     * QQAccount : String
     * WeiXinAccount : String
     * IsPushPost : false
     * IsPushEstate : false
     * IsPushChat : false
     * IsPushSystemMsg : false
     * UserPhotoUrl : ?UserId=String&RM62135596800.jpg
     * AllCount : 0
     * Gender : String
     */

    private String UserId;
    private String NickName;
    private String Email;
    private String Phone;
    private String LastLoginDateTime;
    private String UpdateTime;
    private String CreateTime;
    private long CreateTime2;
    private long UpdateTime2;
    private boolean Status;
    private String SinaAccount;
    private String QQAccount;
    private String WeiXinAccount;
    private boolean IsPushPost;
    private boolean IsPushEstate;
    private boolean IsPushChat;
    private boolean IsPushSystemMsg;
    private String UserPhotoUrl;
    private int AllCount;
    private String Gender;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getLastLoginDateTime() {
        return LastLoginDateTime;
    }

    public void setLastLoginDateTime(String LastLoginDateTime) {
        this.LastLoginDateTime = LastLoginDateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public long getCreateTime2() {
        return CreateTime2;
    }

    public void setCreateTime2(long CreateTime2) {
        this.CreateTime2 = CreateTime2;
    }

    public long getUpdateTime2() {
        return UpdateTime2;
    }

    public void setUpdateTime2(long UpdateTime2) {
        this.UpdateTime2 = UpdateTime2;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getSinaAccount() {
        return SinaAccount;
    }

    public void setSinaAccount(String SinaAccount) {
        this.SinaAccount = SinaAccount;
    }

    public String getQQAccount() {
        return QQAccount;
    }

    public void setQQAccount(String QQAccount) {
        this.QQAccount = QQAccount;
    }

    public String getWeiXinAccount() {
        return WeiXinAccount;
    }

    public void setWeiXinAccount(String WeiXinAccount) {
        this.WeiXinAccount = WeiXinAccount;
    }

    public boolean isIsPushPost() {
        return IsPushPost;
    }

    public void setIsPushPost(boolean IsPushPost) {
        this.IsPushPost = IsPushPost;
    }

    public boolean isIsPushEstate() {
        return IsPushEstate;
    }

    public void setIsPushEstate(boolean IsPushEstate) {
        this.IsPushEstate = IsPushEstate;
    }

    public boolean isIsPushChat() {
        return IsPushChat;
    }

    public void setIsPushChat(boolean IsPushChat) {
        this.IsPushChat = IsPushChat;
    }

    public boolean isIsPushSystemMsg() {
        return IsPushSystemMsg;
    }

    public void setIsPushSystemMsg(boolean IsPushSystemMsg) {
        this.IsPushSystemMsg = IsPushSystemMsg;
    }

    public String getUserPhotoUrl() {
        return UserPhotoUrl;
    }

    public void setUserPhotoUrl(String UserPhotoUrl) {
        this.UserPhotoUrl = UserPhotoUrl;
    }

    public int getAllCount() {
        return AllCount;
    }

    public void setAllCount(int AllCount) {
        this.AllCount = AllCount;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }
}
