package com.teamnova.ej.realreview.util;

import android.content.Context;

/**
 * Created by ej on 2017-09-26.
 */

public class setLogin {
    private String
            id, pw, nick, address, phone, profileImagePath, gender, followerCnt, reviewCnt
    , grade, imageCnt, regDate, description;

    public setLogin() {

    }



    public void doLogin (Context context) {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(context);

        pref.setSharedData("isLogged", "true");
        pref.setSharedData("isLogged_id", id);
        pref.setSharedData("isLogged_pw", pw);
        pref.setSharedData("isLogged_nick", nick);
        pref.setSharedData("isLogged_address", address);
        pref.setSharedData("isLogged_phone", phone);
        pref.setSharedData("isLogged_profileImagePath", profileImagePath);
        pref.setSharedData("isLogged_gender", gender);
        pref.setSharedData("isLogged_followerCnt", followerCnt);
        pref.setSharedData("isLogged_reviewCnt", reviewCnt);
        pref.setSharedData("isLogged_imageCnt", imageCnt);
        pref.setSharedData("isLogged_grade", grade);
        pref.setSharedData("isLogged_regDate", regDate);
        pref.setSharedData("isLogged_description", description);


    }

    public void doLogout (Context context) {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(context);

        pref.setSharedData("isLogged", "false");
        pref.setSharedData("isLogged_id", "");
        pref.setSharedData("isLogged_pw", "");
        pref.setSharedData("isLogged_nick", "");
        pref.setSharedData("isLogged_address", "");
        pref.setSharedData("isLogged_phone", "");
        pref.setSharedData("isLogged_profileImagePath", "");
        pref.setSharedData("isLogged_gender", "");
        pref.setSharedData("isLogged_followerCnt", "");
        pref.setSharedData("isLogged_reviewCnt", "");
        pref.setSharedData("isLogged_imageCnt", "");
        pref.setSharedData("isLogged_grade", "");
        pref.setSharedData("isLogged_regDate", "");
        pref.setSharedData("isLogged_description", "");


    }

}
