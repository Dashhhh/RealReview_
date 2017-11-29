package com.teamnova.ej.realreview.util;

import android.content.Context;

/**
 * Created by ej on 2017-09-22.
 */

public class LogCheck {

    public LogCheck() {

    }

    public boolean doLogCheck(Context context) {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(context);
        String check = pref.getSharedData("isLogged");
        Boolean chk = Boolean.valueOf(check);
        return chk;
    }
}
