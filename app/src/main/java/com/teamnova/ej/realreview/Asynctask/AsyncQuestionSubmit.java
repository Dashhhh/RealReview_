package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_SHOPID;
import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_TEXT;
import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_USERID;
import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_USERNICK;

/**
 * Created by ej on 2017-11-04.
 */

public class AsyncQuestionSubmit extends AsyncTask<Void, Integer, Void> {

    private final ProgressWheel dialog;
    private String urlString;
    private String params = "";
    String TestVAR;
    private Context mContext;
    private String userLocationCheckResult;


    public AsyncQuestionSubmit(String urlString, Context mContext) {
        this.urlString = urlString;
        this.mContext = mContext;
        dialog = new ProgressWheel(mContext);
    }

    @Override
    protected void onPreExecute() {

        dialog.setInstantProgress(0.64f);
        dialog.setBarColor(Color.BLUE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub

        StringBuilder jsonhtml = new StringBuilder();
        StringBuffer postDataBuilder = new StringBuffer();
        JSONArray jsonArray;


        try {
            // URL설정, 접속
            URL url = new URL(urlString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            Log.d("AsyncQuestionSubmit", "url : " + url);

            // 전송모드 설정(일반적인 POST방식)
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            // content-type 설정
            http.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            // 전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("shopid").append("=").append(QUESTION_SHOPID).append("&");
            buffer.append("userid").append("=").append(QUESTION_USERID).append("&");
            buffer.append("question").append("=").append(QUESTION_TEXT).append("&");
            buffer.append("nick").append("=").append(QUESTION_USERNICK);


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
            String result = builder.toString();
            Log.d("AsyncQuestionSubmit", "전송결과 : " + result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {


    }


    private void eraseSharedPref(Context mContext) {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        pref.setSharedData("REVIEW_IMAGE_1", "");
        pref.setSharedData("REVIEW_IMAGE_2", "");
        pref.setSharedData("REVIEW_IMAGE_3", "");
        pref.setSharedData("REVIEW_IMAGE_4", "");
        pref.setSharedData("REVIEW_IMAGE_5", "");
        pref.setSharedData("HTTP_REVIEW_ID", "");
        pref.setSharedData("HTTP_REVIEW_REVIEW", "");
        pref.setSharedData("HTTP_REVIEW_USER", "");
        pref.setSharedData("HTTP_REVIEW_RATING", "");

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