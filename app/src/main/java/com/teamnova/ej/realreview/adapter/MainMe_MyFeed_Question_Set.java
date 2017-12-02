package com.teamnova.ej.realreview.adapter;

import java.util.ArrayList;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Question_Set {


    /**
     * Question Var
     */
    private String userTitleImage; // Glide -> Convert URL & Viewing on getView
    private String followerCnt;
    private String reviewCnt;
    private String imageCnt;
    private String reviewText;
    private String regdate;
    private String userNick;
    private String idx;
    private String answerCount;
    private String metooCount;
    private boolean curious;
    private String imagepath;
    ArrayList<String> userMetoo = new ArrayList<>();
    ArrayList<String> userAnswer = new ArrayList<>();
    String address;

    String shopName;
    String callNumber;
    String shopQuestionCount;
    double rating;

    private String shopImagePath;

    public MainMe_MyFeed_Question_Set() {
    }

    public MainMe_MyFeed_Question_Set(
            String regdate,
            String answerCount,
            String metooCount,
            String imagepath,
            ArrayList<String> userMetoo,
            String address,
            String shopName,
            String callNumber,
            String shopQuestionCount,
            String shopImagePath
    ) {
        this.regdate = regdate;
        this.answerCount = answerCount;
        this.metooCount = metooCount;
        this.imagepath = imagepath;
        this.userMetoo = userMetoo;
        this.address = address;
        this.shopName = shopName;
        this.callNumber = callNumber;
        this.shopQuestionCount = shopQuestionCount;
        this.shopImagePath = shopImagePath;
    }

    public String getUserTitleImage() {
        return userTitleImage;
    }

    public void setUserTitleImage(String userTitleImage) {
        this.userTitleImage = userTitleImage;
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

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }

    public String getMetooCount() {
        return metooCount;
    }

    public void setMetooCount(String metooCount) {
        this.metooCount = metooCount;
    }

    public boolean isCurious() {
        return curious;
    }

    public void setCurious(boolean curious) {
        this.curious = curious;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public ArrayList<String> getUserMetoo() {
        return userMetoo;
    }

    public void setUserMetoo(ArrayList<String> userMetoo) {
        this.userMetoo = userMetoo;
    }

    public ArrayList<String> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(ArrayList<String> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getShopQuestionCount() {
        return shopQuestionCount;
    }

    public void setShopQuestionCount(String shopQuestionCount) {
        this.shopQuestionCount = shopQuestionCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getShopImagePath() {
        return shopImagePath;
    }

    public void setShopImagePath(String shopImagePath) {
        this.shopImagePath = shopImagePath;
    }
}
