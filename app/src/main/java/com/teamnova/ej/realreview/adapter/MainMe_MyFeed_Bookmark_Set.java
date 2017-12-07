package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Bookmark_Set {


    /**
     * Tip Var
     */

    private String shopThumbnail;
    private String checkinCount;
    private String website;
    private String call;
    private String shopTime;
    private String shopName;
    private String regdate;
    private String address;


    public MainMe_MyFeed_Bookmark_Set() {
    }

    public MainMe_MyFeed_Bookmark_Set(

            String shopThumbnail,
            String checkinCount,
            String website,
            String call,
            String shopTime,
            String shopName,
            String regdate,
            String address

    ) {


        this.shopThumbnail = shopThumbnail;
        this.checkinCount = checkinCount;
        this.website = website;
        this.call = call;
        this.shopTime = shopTime;
        this.shopName = shopName;
        this.regdate = regdate;
        this.address = address;

    }


    public String getShopThumbnail() {
        return shopThumbnail;
    }

    public void setShopThumbnail(String shopThumbnail) {
        this.shopThumbnail = shopThumbnail;
    }

    public String getCheckinCount() {
        return checkinCount;
    }

    public void setCheckinCount(String checkinCount) {
        this.checkinCount = checkinCount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getShopTime() {
        return shopTime;
    }

    public void setShopTime(String shopTime) {
        this.shopTime = shopTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
