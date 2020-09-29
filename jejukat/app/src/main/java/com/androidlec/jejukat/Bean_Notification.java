package com.androidlec.jejukat;

public class Bean_Notification {

    private int seq_notification;
    private String title;
    private String context;
    private String picture;
    private String date;

    public Bean_Notification(int seq_notification, String title, String context, String picture, String date) {
        this.seq_notification = seq_notification;
        this.title = title;
        this.context = context;
        this.picture = picture;
        this.date = date;
    }

    public int getSeq_notification() {
        return seq_notification;
    }

    public void setSeq_notification(int seq_notification) {
        this.seq_notification = seq_notification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}//----
