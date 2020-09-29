package com.androidlec.jejukat;

import java.util.ArrayList;

public class Bean_Detail_Schedule {



    //////////////////////////////////////////
    private String cat1;
    private String cat2;
    private String title;
    private ArrayList<String> basicinfo;
    private String address;

    public Bean_Detail_Schedule(String cat1, String cat2, String title, String address) {
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.title = title;
        this.address = address;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getBasicinfo() {
        return basicinfo;
    }

    public void setBasicinfo(ArrayList<String> basicinfo) {
        this.basicinfo = basicinfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //////////////////////////////////////////









}
