package com.androidlec.jejukat;

public class Bean_Detail_Schedule_PickPlacesList {
    private String placeSeq;
    private String placeName;
    private String placeCat1;
    private String placeCat2;
    private String placeAddress;

    public Bean_Detail_Schedule_PickPlacesList(String placeSeq, String placeName, String placeCat1, String placeCat2, String placeAddress) {
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
