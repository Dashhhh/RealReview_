package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.teamnova.ej.realreview.activity.ShopDetail_Main;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.teamnova.ej.realreview.activity.Intro.HOST_ADDRESS;

/**
 * Created by ej on 2017-10-26.
 */

public class AsyncCheckinDataRequest extends AsyncTask<Void, Integer, JSONObject> {


    private String urlString = HOST_ADDRESS + "checkin/checkin_request.php?";
    private String params = "";
    String TestVAR;
    private Context mContext;
    private String shopId;
    private MaterialDialog builder;

    public AsyncCheckinDataRequest(String shopId, Context mContext) {
        this.mContext = mContext;
        this.shopId = shopId;
    }

    @Override
    protected void onPreExecute() {

        builder = new MaterialDialog.Builder(mContext)
                .title("Connecting")
                .content("loading..")
                .autoDismiss(true)
                .progressIndeterminateStyle(true)
                .show();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO Auto-generated method stub
        JSONObject jsonObject = new JSONObject();
        String result = "";

        /**
         * CONDITION - below if
         * USER LOCATION, VIEW PORT 내에 들어오는 조건
         */
        try {
            // URL설정, 접속


            URL url = new URL(urlString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            Log.d("AsyncCheckinRequest", "URL : " + url);

            // 전송모드 설정(일반적인 POST방식)
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            // content-type 설정
            http.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
            // 전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("id_shop").append("=").append(ShopDetail_Main.SHOP_ID);

            // 서버로 전송
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            // 전송 결과값 받기
            InputStreamReader inputStream = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputStream);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = bufferReader.readLine()) != null) {
                builder.append(str + "\n");
            }
            result = builder.toString();
            Log.d("AsyncCheckinRequest", "전송결과 : " + result);
            Log.d("AsyncCheckinRequest", "http.getResponseCode() : " + http.getResponseCode());
            Log.d("AsyncCheckinRequest", "http.getResponseMessage() : " + http.getResponseMessage());

            jsonObject = new JSONObject(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        builder.dismiss();
    }
}
