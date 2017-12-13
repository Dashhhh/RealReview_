package com.teamnova.ej.realreview.Asynctask;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by ej on 2017-10-12.
 */

public class AsyncShopDetailBookmarkCheck extends AsyncTask<Void, Integer, StringBuilder> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    String TestVAR;

    public AsyncShopDetailBookmarkCheck(String urlString) {
        this.urlString = urlString;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected StringBuilder doInBackground(Void... params) {
        // TODO Auto-generated method stub


        StringBuilder jsonHtml = new StringBuilder();
        JSONArray jsonArray;
        try {
            URL phpUrl = new URL(urlString);
            Log.d("AsyncShopDetailBookmarkCheck", "URL:" + urlString);

            HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
            conn.setUseCaches(false);
            int responseStatusCode = conn.getResponseCode();
            Log.e(TAG, "response code - " + responseStatusCode);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    jsonHtml.append(line + "\n");
                }
                br.close();
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonHtml.toString().trim();
        Log.e("AsyncShopDetailBookmarkCheck", "RESULT - " + jsonHtml);
        int i = 0;


        return jsonHtml;
    }

    @Override
    protected void onPostExecute(StringBuilder result) {

    }

}
