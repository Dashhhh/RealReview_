package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.activity.Main_Test;
import com.teamnova.ej.realreview.activity.ShopDetail_Tip_Submit;
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

/**
 * Created by ej on 2017-10-26.
 */

public class AsyncTipSubmit extends AsyncTask<Void, Integer, Void> {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    private ProgressWheel dialog;
    String TestVAR;
    private Context mContext;
    private String userLocationCheckResult;

    public AsyncTipSubmit(String urlString, ProgressWheel dialog, Context mContext) {
        this.urlString = urlString;
        this.dialog = dialog;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {

        dialog.setInstantProgress(0.64f);
        dialog.setBarColor(Color.BLUE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub

        /**
         * CONDITION - below if
         * USER LOCATION, VIEW PORT 내에 들어오는 조건
         */

        if (Main_Test.LOCATION_USER_LAT > Main_Test.LOCATION_NEAR_RIGHT_LAT &&
                Main_Test.LOCATION_USER_LAT < Main_Test.LOCATION_FAR_LEFT_LAT &&
                Main_Test.LOCATION_USER_LNG > Main_Test.LOCATION_FAR_LEFT_LNG &&
                Main_Test.LOCATION_USER_LNG < Main_Test.LOCATION_NEAR_RIGHT_LNG)
            userLocationCheckResult = "1";    // TRUE

        else userLocationCheckResult = "0";    // FALSE

        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        StringBuilder jsonhtml = new StringBuilder();
        StringBuffer postDataBuilder = new StringBuffer();
        JSONArray jsonArray;

/*
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String delimiter = "--" + boundary + "\r\n";

            URL url = new URL(urlString);
            Log.d("ASYNC_TIP","urlString :" + urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            Log.d("ASYNC_TIP","responseCode :" + responseCode);

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);


            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            postDataBuilder.append("\r\n");
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("shopid", ShopDetail_Tip_Submit.TIP_SHOPID));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("userid", ShopDetail_Tip_Submit.TIP_USERID));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("tip", ShopDetail_Tip_Submit.TIP_TEXT));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("nearby", userLocationCheckResult));
            postDataBuilder.append("\r\n");

            Log.d("ASYNC_TIP","postDataBuilder" + postDataBuilder);

            dos.writeUTF(postDataBuilder.toString());

            *//**
             * REVIEW ID (SHOP ID)
             *//*
            dos.writeShort(0x0d0a);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("shopid");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(ShopDetail_Tip_Submit.TIP_SHOPID);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            *//**
             * REVIEW (REVIEW TEXT)
             *//*
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("userid");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(ShopDetail_Tip_Submit.TIP_USERID);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            *//**
             * REVIEW (USER ID)
             *//*
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("tip");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(ShopDetail_Tip_Submit.TIP_TEXT);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            *//**
             * REVIEW (RATING)
             *//*
            dos.writeBytes (twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("nearby");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(userLocationCheckResult);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);


                // write data
                FileInputStream mFileInputStream = new FileInputStream(imagePath.get(i));

                Log.d("REVIEW_Image", "FOR COUNT :" + i);
                dos = new DataOutputStream(conn.getOutputStream());
                File sourceFile = new File(imagePath.get(i));
                String fileName = new File(imagePath.get(i)).getName();
                if (!sourceFile.isFile()) {
                    Log.d("REVIEW_Image", "sourceFile is not exist!!!! :" + imagePath.get(i));
                }
                Log.d("REVIEW_Image", "mFileInputStream  is " + mFileInputStream);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file" + i + "\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes("Content-Type: application/octet-stream");
                dos.writeBytes(lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 8 * 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                Log.d("REVIEW_Image", "image byte is " + bytesRead);

                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }
                dos.writeBytes(lineEnd);
                mFileInputStream.close();



            dos.writeBytes("\r\n--" + boundary + "--\r\n");
            dos.flush(); // finish upload...
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        */


        try {
            // URL설정, 접속
            URL url = new URL(urlString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            // 전송모드 설정(일반적인 POST방식)
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            // content-type 설정
            http.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            // 전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("shopid").append("=").append(ShopDetail_Tip_Submit.TIP_SHOPID).append("&");
            buffer.append("userid").append("=").append(ShopDetail_Tip_Submit.TIP_USERID).append("&");
            buffer.append("tip").append("=").append(ShopDetail_Tip_Submit.TIP_TEXT).append("&");
            buffer.append("nearby").append("=").append(userLocationCheckResult).append("&");
            buffer.append("nick").append("=").append(ShopDetail_Tip_Submit.TIP_USERNICK);

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
            while((str = bufferReader.readLine()) != null){
                builder.append(str + "\n");
            }
            String result = builder.toString();
            Log.d("TIP_ASYNC", "전송결과 : " + result);

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
