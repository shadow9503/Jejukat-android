package com.androidlec.jejukat;

public class Bean_Detail_Schedule_Likelist {

    private String seq_like, seq_post, title, cat1, cat2, image;

    //Constructor
    public Bean_Detail_Schedule_Likelist(String seq_like, String seq_post, String title, String cat1, String cat2, String image) {
        this.seq_like = seq_like;
        this.seq_post = seq_post;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.image = image;
    }


    //Method
    public String getSeq_like() {
        return seq_like;
    }

    public void setSeq_like(String seq_like) {
        this.seq_like = seq_like;
    }


    public String getSeq_post() {
        return seq_post;
    }

    public void setSeq_post(String seq_post) {
        this.seq_post = seq_post;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}//----------------
