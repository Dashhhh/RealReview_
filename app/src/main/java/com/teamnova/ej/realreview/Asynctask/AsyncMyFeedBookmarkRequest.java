package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
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

/**
 * Created by ej on 2017-10-26.
 */

public class AsyncMyFeedBookmarkRequest extends AsyncTask<Void, Integer, JSONObject> {


    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    private Context mContext;
    private MaterialDialog builder;

    public AsyncMyFeedBookmarkRequest(String urlString, Context mContext) {
        this.urlString = urlString;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {

        builder = new MaterialDialog.Builder(mContext)
                .title("Connecting")
                .content("loading..")
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
            Log.d("ASYNCBookmark", "URL : " + url);

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
            buffer.append("userid").append("=").append(pref.getSharedData("isLogged_id"));
            buffer.append("userid").append("=").append(pref.getSharedData("isLogged_id"));

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
            Log.d("ASYNCBookmark", "전송결과 : " + result);
            Log.d("ASYNCBookmark", "http.getResponseCode() : " + http.getResponseCode());
            Log.d("ASYNCBookmark", "http.getResponseMessage() : " + http.getResponseMessage());

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

    /**
     * Map 형식으로 Key와 Value를 셋팅한다.
     *
     * @param key   : 서버에서 사용할 변수명
     * @param value : 변수명에 해당하는 실제 값
     * @return
     */
    private static String setValue(String key, String value) {
        return "Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n"
                + value + "\r\n";
    }

    /**
     * 업로드할 파일에 대한 메타 데이터를 설정한다.
     *
     * @param key      : 서버에서 사용할 파일 변수명
     * @param fileName : 서버에서 저장될 파일명
     * @return
     */
    private static String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key
                + "\";filename=\"" + fileName + "\"\r\n";
    }

}
