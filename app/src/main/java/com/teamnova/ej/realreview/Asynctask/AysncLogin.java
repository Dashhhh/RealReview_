package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by ej on 2017-11-12.
 */

public class AysncLogin extends AsyncTask<Void, Integer, Void> {

    String id;
    String pw;
    Context mContext;

    public AysncLogin() {
    }

    public AysncLogin(String id, String pw, Context mContext) {
        this.id = id;
        this.pw = pw;
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
