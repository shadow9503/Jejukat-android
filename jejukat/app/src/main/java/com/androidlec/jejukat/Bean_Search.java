package com.androidlec.jejukat;

import java.util.ArrayList;

public class Bean_Search {

    private String seq_post;
    private String uploader;
    private String title;
    private String cat1;
    private String cat2;
    private String context;
    private ArrayList<String> images;
    private String thumnail_place;
    private ArrayList<String> basicinfo;
    private String address; //추가
    private String view;
    private String date;

    ///nav 추가
    private String seq_viewed;
    private String latestViewedDate;
    private String validation;
    private String uploader_nickname;
    private String uploader_picture;
    private ArrayList<String> includedSeqPosts;


    // 장소 리스트 빈
    public Bean_Search(String cat2) {
        this.cat2 = cat2;
    }

    //검색결과
    public Bean_Search(String seq_post, String uploader, String title, String cat1, String cat2, String thumnail_place, String address, String view, String date) {
        this.seq_post = seq_post;
        this.uploader = uploader;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.thumnail_place = thumnail_place;
        this.address = address;
        this.view = view;
        this.date = date;

    }
    // 내 일정 nickname, picture 추가
    public Bean_Search(String seq_post, String uploader, String uploader_nickname, String uploader_picture, String title, String cat1, String cat2, String thumnail_place, String address, String view, String date, String validation) {
        this.seq_post = seq_post;
        this.uploader = uploader;
        this.uploader_nickname = uploader_nickname;
        this.uploader_picture = uploader_picture;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.thumnail_place = thumnail_place;
        this.address = address;
        this.view = view;
        this.date = date;
        this.validation = validation;
    }


    // 내 장소 validation 추가
    public Bean_Search(String seq_post, String uploader, String title, String cat1, String cat2, String thumnail_place, String address, String view, String date, String validation) {
        this.seq_post = seq_post;
        this.uploader = uploader;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.thumnail_place = thumnail_place;
        this.address = address;
        this.view = view;
        this.date = date;
        this.validation = validation;
    }

    //최근조회한게시물
    public Bean_Search(String seq_viewed, String seq_post, String title, String cat1, String cat2, String address, String thumnail_place, String latestViewedDate) {
        this.seq_post = seq_post;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.thumnail_place = thumnail_place;
        this.address = address;
        this.seq_viewed = seq_viewed;
        this.latestViewedDate = latestViewedDate;
    }

    //일정
    public Bean_Search(String seq_post, String uploader, String uploader_nickname, String uploader_picture, String title, String cat1, String cat2, String view, String date, String thumnail_place, ArrayList<String> includedSeqPosts , String validation) {
        this.seq_post = seq_post;
        this.uploader = uploader;
        this.uploader_nickname = uploader_nickname;
        this.uploader_picture = uploader_picture;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.thumnail_place = thumnail_place;
        this.includedSeqPosts= includedSeqPosts;
        this.view = view;
        this.date = date;
        this.validation = validation;
    }



    //method 최근게시물 + 내장소 권한 + 내일정 (uploader_nickname + uploader_picture ) 추가
    public String getSeq_viewed() {
        return seq_viewed;
    }

    public void setSeq_viewed(String seq_viewed) {
        this.seq_viewed = seq_viewed;
    }

    public String getLatestViewedDate() {
        return latestViewedDate;
    }

    public void setLatestViewedDate(String latestViewedDate) {
        this.latestViewedDate = latestViewedDate;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getUploader_nickname() {
        return uploader_nickname;
    }

    public void setUploader_nickname(String uploader_nickname) {
        this.uploader_nickname = uploader_nickname;
    }

    public String getUploader_picture() {
        return uploader_picture;
    }

    public void setUploader_picture(String uploader_picture) {
        this.uploader_picture = uploader_picture;
    }

    public ArrayList<String> getIncludedSeqPosts() {
        return includedSeqPosts;
    }

    public void setIncludedSeqPosts(ArrayList<String> includedSeqPosts) {
        this.includedSeqPosts = includedSeqPosts;
    }
/////////////////////////////

    public String getSeq_post() {
        return seq_post;
    }

    public void setSeq_post(String seq_post) {
        this.seq_post = seq_post;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
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

    public String getThumnail_place() {
        return thumnail_place;
    }

    public void setThumnail_place(String thumnail_place) {
        this.thumnail_place = thumnail_place;
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
}
