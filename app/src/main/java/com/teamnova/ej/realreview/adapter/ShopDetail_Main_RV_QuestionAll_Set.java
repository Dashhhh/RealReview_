package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2017-11-04.
 */

public class ShopDetail_Main_RV_QuestionAll_Set {


    String titleImage; // Glide -> Convert URL & Viewing on getView
    String followerCnt;
    String reviewCnt;
    String imageCnt;
    String reviewText;
    String regdate;
    String userNick;


    public ShopDetail_Main_RV_QuestionAll_Set() {
    }

    public ShopDetail_Main_RV_QuestionAll_Set(String titleImage, String followerCnt, String reviewCnt, String imageCnt, String reviewText, String regdate, String userNick) {
        this.titleImage = titleImage;
        this.followerCnt = followerCnt;
        this.reviewCnt = reviewCnt;
        this.imageCnt = imageCnt;
        this.reviewText = reviewText;
        this.regdate = regdate;
        this.userNick = userNick;
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


}
