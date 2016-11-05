package com.hme.turman.api.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lebro on 2016/11/5.
 */

public class HelpEventBean implements Serializable {
    private String eventId;
    private String userImage;
    private String title;
    private String pushDate;
    private String content;
    private String address;
    private List<String> contentImage;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getContentImage() {
        return contentImage;
    }

    public void setContentImage(List<String> contentImage) {
        this.contentImage = contentImage;
    }
}
