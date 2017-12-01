package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Review_Set {


    /**
     * Review Var
     */
    String idx_review;
    String reviewUserThumbnail; // Glide -> Convert URL & Viewing on getView
    String reviewText;
    String regdate;
    String userId;
    String rating;
    String nick;
    String nearby;
    String locality;
    String coolCount;
    String usefulCount;
    String goodCount;

    String shopImagepath;
    String shopName;

    public MainMe_MyFeed_Review_Set() {
    }

    public MainMe_MyFeed_Review_Set(String idx_review,
                                    String reviewUserThumbnail,
                                    String reviewText,
                                    String regdate,
                                    String userId,
                                    String rating,
                                    String nick,
                                    String nearby,
                                    String locality,
                                    String coolCount,
                                    String usefulCount,
                                    String goodCount,
                                    String shopImagepath,
                                    String shopName
    ) {
        this.idx_review = idx_review;
        this.reviewUserThumbnail = reviewUserThumbnail;
        this.reviewText = reviewText;
        this.regdate = regdate;
        this.userId = userId;
        this.rating = rating;
        this.nick = nick;
        this.nearby = nearby;
        this.locality = locality;
        this.coolCount = coolCount;
        this.usefulCount = usefulCount;
        this.goodCount = goodCount;
        this.shopImagepath = shopImagepath;
        this.shopName = shopName;
    }

    public String getIdx_review() {
        return idx_review;
    }

    public void setIdx_review(String idx_review) {
        this.idx_review = idx_review;
    }

    public String getReviewUserThumbnail() {
        return reviewUserThumbnail;
    }

    public void setReviewUserThumbnail(String reviewUserThumbnail) {
        this.reviewUserThumbnail = reviewUserThumbnail;
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

    public String getShopImagepath() {
        return shopImagepath;
    }

    public void setShopImagepath(String shopImagepath) {
        this.shopImagepath = shopImagepath;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
