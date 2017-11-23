package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2017-10-21.
 */

public class ShopDetail_Main_Review_LV_Set {

    public String idx;
    public String titleImage; // Glide -> Convert URL & Viewing on getView
    public String followerCnt;
    public String reviewCnt;
    public String imageCnt;
    public String reviewText;
    public String regdate;
    public String userId;
    public String rating;
    public float fRating;
    public String nick;
    public String nearby;
    public String locality;

    public String coolCount;
    public String usefulCount;
    public String goodCount;

    boolean useful_selectable;
    boolean good_selectable;
    boolean cool_selectable;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public float getfRating() {
        return fRating;
    }

    public void setfRating(float fRating) {
        this.fRating = fRating;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public String getCoolCount() {
        return coolCount;
    }

    public void setCoolCount(String coolCount) {
        this.coolCount = coolCount;
    }

    public String getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(String usefulCount) {
        this.usefulCount = usefulCount;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    public boolean isUseful_selectable() {
        return useful_selectable;
    }

    public void setUseful_selectable(boolean useful_selectable) {
        this.useful_selectable = useful_selectable;
    }

    public boolean isGood_selectable() {
        return good_selectable;
    }

    public void setGood_selectable(boolean good_selectable) {
        this.good_selectable = good_selectable;
    }

    public boolean isCool_selectable() {
        return cool_selectable;
    }

    public void setCool_selectable(boolean cool_selectable) {
        this.cool_selectable = cool_selectable;
    }
}