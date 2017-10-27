package com.teamnova.ej.realreview.activity;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.teamnova.ej.realreview.Asynctask.AsyncDirectionRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Login extends AppCompatActivity implements View.OnClickListener {

    String logged_url = "http://222.122.203.55/realreview/signup/profileLookup.php?id=";
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
    private static final String TAG_RESPONSE = "response";
    String tempId, tempPw, tempNick, tempAddress, tempPhone, tempProfileImagePath,
            tempGender, tempFollowerCnt, tempReviewCnt, tempImageCnt, tempGrade, tempRegDate, tempDescription;

    EditText loginIdText, loginPasswordText;
    Button loginBtn;
    TextView loginSignIn, loginText;
    ImageView idImage, passwordImage;
    Boolean idCheckFlag = false;
    public boolean activityFlag = false;
    String url = null, userId = null, userPw = null;
    Handler mHandler = new Handler(Looper.getMainLooper());
    String login_url = "http://222.122.203.55/realreview/signup/login.php?";

    String logged_url_parse = null;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        listener();


        JSONObject aa;
        try {
            aa = new AsyncDirectionRequest("asd","aasda",this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("DIRECTION", "전송결과 : " + aa);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }

    private void listener() {

        loginIdText.setOnClickListener(this);
        loginPasswordText.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        loginSignIn.setOnClickListener(this);
        loginText.setOnClickListener(this);
        idImage.setOnClickListener(this);
        passwordImage.setOnClickListener(this);

    }

    private void init() {

        loginIdText = (EditText) findViewById(R.id.loginIdText);
        loginPasswordText = (EditText) findViewById(R.id.loginPasswordText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginSignIn = (TextView) findViewById(R.id.loginSignIn);
        loginText = (TextView) findViewById(R.id.loginText);
        idImage = (ImageView) findViewById(R.id.idImage);
        passwordImage = (ImageView) findViewById(R.id.passwordImage);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.loginBtn: {

                Dialog_Default dial = new Dialog_Default(this);
                dialog = new ProgressDialog(this);
                dialog.setMessage("SHOP DATA Check");
                dialog.setIndeterminate(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                String id = loginIdText.getText().toString();
                String pw = loginPasswordText.getText().toString();

                loginThread loginThread = new loginThread(id, pw);
                loginThread.start();
                try {
                    loginThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("onClick, tempId", "tempId :" + tempId);
                Log.e("onClick, tempPw", "tempPw:" + tempPw);

                String tempIdStr = tempId;
                String tempPwStr = tempPw;

                if (!id.equals("") && !pw.equals("")) {
                    if (id.equals(tempIdStr)) {
                        if (tempPwStr.equals(pw)) {

                            idCheckThread ict = new idCheckThread(tempIdStr);
                            ict.start();
                            try {
                                ict.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                            pref.setSharedData("isLogged_id", tempId);
                            pref.setSharedData("isLogged_pw", tempPw);
                            pref.setSharedData("isLogged_nick", tempNick);
                            pref.setSharedData("isLogged_address", tempAddress);
                            pref.setSharedData("isLogged_phone", tempPhone);
                            pref.setSharedData("isLogged_gender", tempGender);
                            pref.setSharedData("isLogged_followerCnt", tempFollowerCnt);
                            pref.setSharedData("isLogged_profileImagePath", tempProfileImagePath);
                            pref.setSharedData("isLogged_reviewCnt", tempReviewCnt);
                            pref.setSharedData("isLogged_imageCnt", tempImageCnt);
                            pref.setSharedData("isLogged_grade", tempGrade);
                            pref.setSharedData("isLogged_regDate", tempRegDate);
                            pref.setSharedData("isLogged_description", tempDescription);
                            dialog.dismiss();
                            Toast.makeText(this, tempNick + ", Welcome Back", Toast.LENGTH_SHORT).show();
                            tempNick = "";
                            Intent intent = new Intent(getApplicationContext(), Main_Test.class);
                            startActivity(intent);
//                        } else if (!tempIdStr.isEmpty() && tempPwStr.equals("")) {
//                            dial.call("Warning", "비밀번호를 입력 후 로그인해 주시기 바랍니다.");
//                            loginPasswordText.setText("");
                        } else if (!tempPwStr.equals(pw)) {
                            dialog.dismiss();
                            dial.call("Warning", "로그인 정보가 틀립니다. 정보를 확인해 주시기 바랍니다.");
                            loginPasswordText.setText("");
                        }

                    } else {
                        dialog.dismiss();
                        dial.call("Warning", "가입 된 ID가 없습니다. 로그인 정보를 확인해 주시기 바랍니다.");
                    }
                } else if (id.equals("") || pw.equals("")) {
                    dialog.dismiss();
                    dial.call("Warning", "아이디와 비밀번호를 입력 한 후 로그인 버튼을 눌러주세요.");
                }
                tempId = "";
                tempIdStr = "";
                tempPw = "";
                pw = "";

                break;
            }
            case R.id.loginSignIn: {
                Intent intent = new Intent(getApplicationContext(), Signup1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }


    }


    public class loginThread extends Thread {
        StringBuilder jsonHtml = new StringBuilder();

        public loginThread(String id, String pw) {
            url = login_url + "id=" + id + "&" + "pw=" + pw;
            Log.e("URL", "URL:" + url);

        }

        public void run() {
            super.run();
            try {
                URL phpUrl = new URL(url);

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
//                        Log.e("jsonHtml - ReadLine", String.valueOf(jsonHtml));
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
                JSONObject jObject = new JSONObject(String.valueOf(jsonHtml));
                JSONArray jsonArray = jObject.getJSONArray(TAG_JSON);
//                if (!jObject.isNull("realreview")){
//                }
                JSONObject item = jsonArray.getJSONObject(0);
                String getId = item.getString(TAG_ID);
                String getPw = item.getString(TAG_PW);
                String getNick = item.getString("nick");
                tempId = getId;
                tempPw = getPw;
                tempNick = getNick;
                Log.e(TAG, "item - " + item);
                Log.e(TAG, "getId - " + getId);
                Log.e(TAG, "jObject - " + jObject);
                Log.e(TAG, "jsonArray - " + jsonArray);
                Log.e(TAG, "tempId - " + tempId);
                Log.e(TAG, "tempId !!- " + tempId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            show(jsonHtml.toString());
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
                String getDescription = item.getString(TAG_DESCRIPTION);

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
    }
}
