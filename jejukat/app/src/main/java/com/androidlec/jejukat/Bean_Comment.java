package com.androidlec.jejukat;

public class Bean_Comment {

    //선언
    private int seq_cmt;
    private int seq_post;
    private String seq_user;
    private String nickname;
    private String picture; // 프로필사진
    private String comment;
    private String date;
    private int validation;

//    private String profile_comment_civ;
//    private String nickname_comment_tv;
//    private String insertdate_comment_tv;
//    private String contents_comment_tv;


    //Constructor
    public Bean_Comment(int seq_cmt, int seq_post, String seq_user, String nickname, String picture, String comment, String date, int validation) {
        this.seq_cmt = seq_cmt;
        this.seq_post = seq_post;
        this.seq_user = seq_user;
        this.nickname = nickname;
        this.picture = picture;
        this.comment = comment;
        this.date = date;
        this.validation = validation;
    }

    //Method
    public int getSeq_cmt() {
        return seq_cmt;
    }

    public void setSeq_cmt(int seq_cmt) {
        this.seq_cmt = seq_cmt;
    }

    public int getSeq_post() {
        return seq_post;
    }

    public void setSeq_post(int seq_post) {
        this.seq_post = seq_post;
    }

    public String getSeq_user() {
        return seq_user;
    }

    public void setSeq_user(String seq_user) {
        this.seq_user = seq_user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValidation() {
        return validation;
    }

    public void setValidation(int validation) {
        this.validation = validation;
    }
}//===================
