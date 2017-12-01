package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Tip_Set {


    /**
     * Tip Var
     */

    private String titleImage; // Glide -> Convert URL & Viewing on getView
    private String followerCnt;
    private String reviewCnt;
    private String imageCnt;
    private String reviewText;
    private String regdate;
    private String userNick;
    private String nearby;
    private String locality;
    private String imagepath;

    public MainMe_MyFeed_Tip_Set(String titleImage,
                                 String followerCnt,
                                 String reviewCnt,
                                 String imageCnt,
                                 String reviewText,
                                 String regdate,
                                 String userNick,
                                 String nearby,
                                 String locality,
                                 String imagepath) {
        this.titleImage = titleImage;
        this.followerCnt = followerCnt;
        this.reviewCnt = reviewCnt;
        this.imageCnt = imageCnt;
        this.reviewText = reviewText;
        this.regdate = regdate;
        this.userNick = userNick;
        this.nearby = nearby;
        this.locality = locality;
        this.imagepath = imagepath;
    }


    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getFollowerCnt() {
        return followerCnt;
    }

    public void setFollowerCnt(String followerCnt) {
        this.followerCnt = followerCnt;
    }

    public String getReviewCnt() {
        return reviewCnt;
    }

    public void setReviewCnt(String reviewCnt) {
        this.reviewCnt = reviewCnt;
    }

    public String getImageCnt() {
        return imageCnt;
    }

    public void setImageCnt(String imageCnt) {
        this.imageCnt = imageCnt;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getNearby() {
        return nearby;
    }

    public void setNearby(String nearby) {
        this.nearby = nearby;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
