package com.androidlec.jejukat;

import java.util.ArrayList;

///////장소 상세정보 BEAN/////////

public class Bean_Selected_Place {

    //////field
    private int seq_post;
    private String nickname;
    private String title;
    private String cat1;
    private String cat2;
    private String context;
    private ArrayList<String> images;
    private ArrayList<String> basicinfo;

    private String view;
    private String date;

    //////constructor

    public Bean_Selected_Place(int seq_post, String nickname, String title, String cat1, String cat2, String context, ArrayList<String> images, ArrayList<String> basicinfo, String view, String date) {
        this.seq_post = seq_post;
        this.nickname = nickname;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.context = context;
        this.images = images;
        this.basicinfo = basicinfo;
        this.view = view;
        this.date = date;
    }


//    /////////Method


    public int getSeq_post() {
        return seq_post;
    }

    public void setSeq_post(int seq_post) {
        this.seq_post = seq_post;
    }

    public String getUploader() {
        return nickname;
    }

    public void setUploader(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getBasicinfo() {
        return basicinfo;
    }

    public void setBasicinfo(ArrayList<String> basicinfo) {
        this.basicinfo = basicinfo;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}//================
