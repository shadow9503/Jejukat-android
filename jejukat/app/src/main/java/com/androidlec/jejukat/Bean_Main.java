package com.androidlec.jejukat;

public class Bean_Main {
    int image;
    String color;
    String title;
    String description;
    String link;

    public Bean_Main(int image, String color, String title, String description) {
        this.image = image;
        this.color = color;
        this.title = title;
        this.description = description;
    }

    public Bean_Main(int image, String link) {
        this.image = image;
        this.link = link;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
