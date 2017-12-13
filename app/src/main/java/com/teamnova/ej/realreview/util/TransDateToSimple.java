package com.teamnova.ej.realreview.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ej on 2017-12-13.
 */

public class TransDateToSimple {

    String date;

    public TransDateToSimple() {
    }

    public String trans(JSONObject regdate) throws JSONException {


        String year = regdate.getString("y");
        String month = regdate.getString("m");
        String day = regdate.getString("d");
        String hour = regdate.getString("h");
        String minute = regdate.getString("i");
        String second = regdate.getString("s");
        String f = regdate.getString("f");


        if (!year.equals("0")) {
            date = year + "년 전";
        } else if (!month.equals("0")) {
            date = month + "달 전";
        } else if (!day.equals("0")) {
            date = day + "일 전";
        } else if (!hour.equals("0")) {
            date = hour + "시간 전";
        } else if (!minute.equals("0")) {
            date = minute + "분 전";
        } else if (!second.equals("0")) {
            date = "방금 전";
        } else if (second.equals("0")) {
            date = "방금 전";
        }


        return date;

    }


}
