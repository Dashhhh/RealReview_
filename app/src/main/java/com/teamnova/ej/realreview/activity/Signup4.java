package com.teamnova.ej.realreview.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Signup4 extends AppCompatActivity implements View.OnClickListener {

    com.beardedhen.androidbootstrap.AwesomeTextView sign4Image;
    EditText sign4Phone, sign4CertiNumber;
    Button sign4Next, sign4CertiRequest;

    com.beardedhen.androidbootstrap.AwesomeTextView sign4_welcomeText, sign4_welcomeText2;

    public boolean activityFlag = false;
    Handler mHandler = new Handler(Looper.getMainLooper());
    String login_url = "http://222.122.203.55/realreview/signup/register.php?";
    String logged_url = "http://222.122.203.55/realreview/signup/profileLookup.php?id=";
    String logged_url_parse = null;
    String strAnd = "&", strId = "id=", strPw = "pw=", strNick = "nick=",
            strAddress = "address=", strPhone = "phone", strGender = "gender", int_followerCnt = "follower_cnt",
            int_reviewCnt = "review_cnt", int_imageCnt = "image_cnt", int_grade = "grade", strReg_date = "reg_date",
            strDescription = "description", urlParse;

    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON = "realreview";
    private static final String TAG_ID = "id";
    private static final String TAG_PW = "pw";
    private static final String TAG_NICK = "nick";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_PROFILE_IMAGE_PATH = "profile_image_path";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_FOLLOWER_CNT = "follower_cnt";
    private static final String TAG_REVIEW_CNT = "review_cnt";
    private static final String TAG_IMAGE_CNT = "image_cnt";
    private static final String TAG_GRADE = "grade";
    private static final String TAG_REG_DATE = "reg_date";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_LAT = "lng";
    private static final String TAG_LNG = "lat";
    private static final String TAG_SW_LAT = "SW_Lat";
    private static final String TAG_SW_LNG = "SW_Lng";
    private static final String TAG_NE_LAT = "NE_Lat";
    private static final String TAG_NE_LNG = "NE_Lng";
    String tempId, tempPw, tempNick, tempAddress, tempPhone, tempProfileImagePath,
            tempGender, tempFollowerCnt, tempReviewCnt, tempImageCnt, tempGrade, tempRegDate, tempDescription;
    String tempLat;
    String tempLng;
    String tempSWLat;
    String tempSWLng;
    String tempNELat;
    String tempNELng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_signup4);

        init();
        listener();
    }   // onCreate

    private void listener() {

        sign4CertiRequest.setOnClickListener(this);
        sign4Next.setOnClickListener(this);
    }

    private void init() {

        sign4Image = findViewById(R.id.sign4Image);
        sign4Phone = findViewById(R.id.sign4Phone);
        sign4CertiNumber = findViewById(R.id.sign4CertiNumber);
        sign4Next = findViewById(R.id.sign4Next);
        sign4CertiRequest = findViewById(R.id.sign4CertiRequest);
        sign4_welcomeText = findViewById(R.id.sign4_welcomeText);
        sign4_welcomeText2 = findViewById(R.id.sign4_welcomeText2);

    }   // init()

    @Override
    public void onClick(View view) {
        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        switch (view.getId()) {

            case R.id.sign4CertiRequest: {
                break;
            }

            case R.id.sign4Next: {

                signInThread st = null;
                try {
                    st = new signInThread();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                st.start();
                try {
                    st.join();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String prefId = pref.getSharedData("SIGNIN_ID");
                idCheckThread ict = new idCheckThread(prefId);
                ict.start();
                try {
                    ict.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                pref.setSharedData("isLogged", "true");
                pref.setSharedData("isLogged_id", tempId);
                pref.setSharedData("isLogged_pw", tempPw);
                pref.setSharedData("isLogged_nick", tempNick);
                pref.setSharedData("isLogged_address", tempAddress);
                pref.setSharedData("isLogged_phone", tempPhone);
                pref.setSharedData("isLogged_profileImagePath", tempProfileImagePath);
                pref.setSharedData("isLogged_gender", tempGender);
                pref.setSharedData("isLogged_followerCnt", tempFollowerCnt);
                pref.setSharedData("isLogged_reviewCnt", tempReviewCnt);
                pref.setSharedData("isLogged_imageCnt", tempImageCnt);
                pref.setSharedData("isLogged_grade", tempGrade);
                pref.setSharedData("isLogged_regDate", tempRegDate);
                pref.setSharedData("isLogged_description", tempDescription);
                pref.setSharedData("isLogged_lat", tempLat);
                pref.setSharedData("isLogged_lng", tempLng);
                pref.setSharedData("isLogged_SW_Lat", tempSWLat);
                pref.setSharedData("isLogged_SW_Lng", tempSWLng);
                pref.setSharedData("isLogged_NE_Lat", tempNELat);
                pref.setSharedData("isLogged_NE_Lng", tempNELng);

//                pref.setSharedData("SIGNIN_ID","");
                pref.setSharedData("SIGNIN_PW", "");
                pref.setSharedData("SIGNIN_NICK", "");
                pref.setSharedData("SIGNIN_ADDRESS", "");
                Intent intent = new Intent(Signup4.this, Signup5.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
        }
    }   // onClick()


    public class signInThread extends Thread {

        public signInThread() throws UnsupportedEncodingException {

            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
            String time = sdfNow.format(new Date(System.currentTimeMillis()));
            Log.e("TIME", "TIME:" + time);

            SharedPreferenceUtil pref = new SharedPreferenceUtil(getApplicationContext());
            strId += pref.getSharedData("SIGNIN_ID");
            strPw += pref.getSharedData("SIGNIN_PW");
            strNick += pref.getSharedData("SIGNIN_NICK");
            String strAddressEncode = pref.getSharedData("SIGNIN_ADDRESS");
            String str = URLEncoder.encode(strAddressEncode, "UTF-8");
//            strAddress += pref.getSharedData("SIGNIN_ADDRESS");
//            String str = URLEncoder.encode(strAddress, "UTF-8" );
            strAddress += str;

            /**
             *  이하 임시 값
             */

            strPhone = "phone=0100000";
            strGender = "gender=Male";
            int_followerCnt = "follower_cnt=0";
            int_reviewCnt = "review_cnt=0";
            int_imageCnt = "image_cnt=0";
            int_grade = "grade=0";
            strReg_date = "reg_date=" + time;
            strDescription = "description=DEFAULTTTTTT";


            urlParse = login_url + strId + strAnd + strPw + strAnd + strNick + strAnd + strAddress + strAnd + strPhone + strAnd + strGender + strAnd + int_followerCnt
                    + strAnd + int_reviewCnt + strAnd + int_imageCnt + strAnd + int_grade + strAnd + strReg_date + strAnd + strDescription;

            //Add ViewPort Var
            urlParse +=
                    "&lat=" + pref.getSharedData("SIGNUP_Lat")
                            + "&lng=" + pref.getSharedData("SIGNUP_Lng")
                            + "&SW_Lat=" + pref.getSharedData("SIGNUP_SW_Lat")
                            + "&SW_Lng=" + pref.getSharedData("SIGNUP_SW_Lng")
                            + "&NE_Lat=" + pref.getSharedData("SIGNUP_NE_Lat")
                            + "&NE_Lng=" + pref.getSharedData("SIGNUP_NE_Lng");

            Log.d("Position", "SIGNUP_Lat :" + pref.getSharedData("SIGNUP_Lat"));
            Log.d("Position", "SIGNUP_Lng :" + pref.getSharedData("SIGNUP_Lng"));
            Log.d("Position", "SIGNUP_SW_Lat :" + pref.getSharedData("SIGNUP_SW_Lat"));
            Log.d("Position", "SIGNUP_SW_Lng :" + pref.getSharedData("SIGNUP_SW_Lng"));
            Log.d("Position", "SIGNUP_NE_Lat :" + pref.getSharedData("SIGNUP_NE_Lat"));
            Log.d("Position", "SIGNUP_NE_Lng :" + pref.getSharedData("SIGNUP_NE_Lng"));


        }

        public void run() {
            super.run();
            try {
                URL phpUrl = new URL(urlParse);
                StringBuilder jsonHtml = new StringBuilder();

                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
//                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                Log.e("phpURL", "phpURL:" + phpUrl);
                Log.e("urlParse", "urlParse:" + urlParse);
                conn.connect();
                int responseStatusCode = conn.getResponseCode();
                Log.e(TAG, "response code - " + responseStatusCode);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        jsonHtml.append(line + "\n");
                    }
                    Log.d("Position", "jsonHtml :" + jsonHtml);

                    br.close();
                    conn.disconnect();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class idCheckThread extends Thread {

        public idCheckThread(String id) {
            logged_url_parse = logged_url + id;
        }

        public void run() {
            StringBuilder jsonHtml = new StringBuilder();

            super.run();
            try {
                URL phpUrl = new URL(logged_url_parse);

                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
//                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
//                conn.connect();
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
            Log.e(TAG, "jsonHtml - " + jsonHtml);
            try {
                int i = 0;
                JSONObject jObject = new JSONObject(String.valueOf(jsonHtml));
                JSONArray jsonArray = jObject.getJSONArray(TAG_JSON);
                JSONObject item = jsonArray.getJSONObject(i);
                String getId = item.getString(TAG_ID);
                String getPw = item.getString(TAG_PW);
                String getNick = item.getString(TAG_NICK);
                String getAddress = item.getString(TAG_ADDRESS);
                String getPhone = item.getString(TAG_PHONE);
                String getProfileImagePath = item.getString(TAG_PROFILE_IMAGE_PATH);
                String getGender = item.getString(TAG_GENDER);
                String getFollowerCnt = item.getString(TAG_FOLLOWER_CNT);
                String getReviewCnt = item.getString(TAG_REVIEW_CNT);
                String getImageCnt = item.getString(TAG_IMAGE_CNT);
                String getGrade = item.getString(TAG_GRADE);
                String getRegDate = item.getString(TAG_REG_DATE);
                String getDescription = item.getString(TAG_REG_DATE);
                String getLat = item.getString(TAG_LAT);
                String getLng = item.getString(TAG_LNG);
                String getSWLat = item.getString(TAG_SW_LAT);
                String getSWLng = item.getString(TAG_SW_LNG);
                String getNELat = item.getString(TAG_NE_LAT);
                String getNELng = item.getString(TAG_NE_LNG);

                tempId = getId;
                tempPw = getPw;
                tempNick = getNick;
                tempAddress = getAddress;
                tempPhone = getPhone;
                tempProfileImagePath = getProfileImagePath;
                tempGender = getGender;
                tempFollowerCnt = getFollowerCnt;
                tempReviewCnt = getReviewCnt;
                tempImageCnt = getImageCnt;
                tempGrade = getGrade;
                tempRegDate = getRegDate;
                tempDescription = getDescription;
                tempLat = getLat;
                tempLng = getLng;
                tempSWLat = getSWLat;
                tempSWLng = getSWLng;
                tempNELat = getNELat;
                tempNELng = getNELng;


                Log.e(TAG, "jObject - " + jObject);
                Log.e(TAG, "jsonArray - " + jsonArray);
                Log.e(TAG, "item - " + item);
                Log.e(TAG, "getId - " + getId);
                Log.e(TAG, "getPw - " + getPw);
                Log.e(TAG, "getNick - " + getNick);
                Log.e(TAG, "getAddress - " + getAddress);
                Log.e(TAG, "getPhone - " + getPhone);
                Log.e(TAG, "getProfileImagePath - " + getProfileImagePath);
                Log.e(TAG, "getGender - " + getGender);
                Log.e(TAG, "getFollowerCnt - " + getFollowerCnt);
                Log.e(TAG, "getReviewCnt - " + getReviewCnt);
                Log.e(TAG, "getImageCnt - " + getImageCnt);
                Log.e(TAG, "getGrade - " + getGrade);
                Log.e(TAG, "getRegDate - " + getRegDate);
                Log.e(TAG, "getDescription - " + getDescription);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }       // idCheckThread


    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(getString(R.string.app_name))
                .setMessage("정말 App을 종료 하시겠습니까??")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        //Intent intent=new Intent(getApplication(),Login.class);
                        //startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

            }
        })
                .create()
                .show();
    }
}