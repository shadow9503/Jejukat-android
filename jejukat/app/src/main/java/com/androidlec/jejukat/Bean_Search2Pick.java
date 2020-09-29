package com.androidlec.jejukat;


public class Bean_Search2Pick {

    private String placeSeq;
    private String placeName;
    private String placeCat1;
    private String placeCat2;
    private String placeAddress;
    private String thumnail_place;

    public Bean_Search2Pick(String placeSeq, String placeName, String placeCat1, String placeCat2, String placeAddress) {
        this.placeSeq = placeSeq;
        this.placeName = placeName;
        this.placeCat1 = placeCat1;
        this.placeCat2 = placeCat2;
        this.placeAddress = placeAddress;
    }

    public String getPlaceSeq() {
        return placeSeq;
    }

    public void setPlaceSeq(String placeSeq) {
        this.placeSeq = placeSeq;
    }

    public String getThumnail_place() {
        return thumnail_place;
    }

    public void setThumnail_place(String thumnail_place) {
        this.thumnail_place = thumnail_place;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceCat1() {
        return placeCat1;
    }

    public void setPlaceCat1(String placeCat1) {
        this.placeCat1 = placeCat1;
    }

    public String getPlaceCat2() {
        return placeCat2;
    }

    public void setPlaceCat2(String placeCat2) {
        this.placeCat2 = placeCat2;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }
}
