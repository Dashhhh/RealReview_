package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2018-01-04.
 */

public class ShopDetail_Main_Review_RV_Checkin_Set {

    private String idx;
    private String id_shop;
    private String id_user;
    private String nick;
    private String description;
    private String imageurl;
    private String locationLat;
    private String locationLng;
    private String regdate;


    public ShopDetail_Main_Review_RV_Checkin_Set() {

    }

    public ShopDetail_Main_Review_RV_Checkin_Set(String idx, String id_shop, String id_user, String nick, String description, String imageurl, String locationLat, String locationLng, String regdate) {
        this.idx = idx;
        this.id_shop = id_shop;
        this.id_user = id_user;
        this.nick = nick;
        this.description = description;
        this.imageurl = imageurl;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.regdate = regdate;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
