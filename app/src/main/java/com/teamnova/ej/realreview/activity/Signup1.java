package com.teamnova.ej.realreview.activity;

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

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;
import com.teamnova.ej.realreview.util.ValidateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup1 extends AppCompatActivity implements View.OnClickListener {

    EditText sign1Id, sign1Password, sign1PasswordCheck;
    Button sign1Next;
    ImageView sign1Image;
    Boolean idCheckFlag = false;
    public boolean activityFlag = false;
    String url = null, userId = null, userPw = null;
    Handler mHandler = new Handler(Looper.getMainLooper());
    String login_url = "http://222.122.203.55/realreview/signup/chkid2.php?id=";
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON = "realreview";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    String tempId, tempId2;
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$"); // 4자리 ~ 16자리까지 가능

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        sign1Id = (EditText) findViewById(R.id.sign1Id);
        sign1Password = (EditText) findViewById(R.id.sign1Password);
        sign1PasswordCheck = (EditText) findViewById(R.id.sign1PasswordCheck);
        sign1Next = (Button) findViewById(R.id.sign1Next);
        sign1Image = (ImageView) findViewById(R.id.sign1Image);


        sign1Next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign1Next: {

                Dialog_Default dial = new Dialog_Default(this);

                String id = sign1Id.getText().toString();
                tempId = id;

                idCheckThread idCheck = new idCheckThread(id);
                idCheck.start();
                try {
                    idCheck.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "tempNick - " + tempId);
                Log.e(TAG, "tempId2 - " + tempId2);
                if (!tempId.equals(tempId2)) {    // 중복 ID 없음

                    boolean chkEmail = ValidateUtil.validateEmail(tempId);
                    if (chkEmail) {
                        String password = sign1Password.getText().toString();
                        String passwordCheck = sign1PasswordCheck.getText().toString();
                        if (ValidateUtil.validatePassword(password)) {
                            if (password.equals(passwordCheck)) {
                                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                                pref.setSharedData("SIGNIN_ID", id);
                                pref.setSharedData("SIGNIN_PW", password);
                                Intent intent = new Intent(Signup1.this, Signup2.class);
                                startActivity(intent);
                            } else if (!password.equals(passwordCheck)) {
//                                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                };
//                                new AlertDialog.Builder(this).setTitle("비밀번호가 같지 않습니다. 다시 확인해 주시기 바랍니다.").
//                                        setNegativeButton("확인", cancelListener).show();
                                dial.call("Warning", "비밀번호가 같지 않습니다. 다시 확인해 주시기 바랍니다.");

                            } else if (password == "" || passwordCheck == "") {
//                                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                };
//                                new AlertDialog.Builder(this).setTitle("비밀번호를 입력해 주시기 바랍니다.").
//                                        setNegativeButton("확인", cancelListener).show();
                                dial.call("Warning", "비밀번호를 입력해 주시기 바랍니다.");
                            }
                        } else {
//                            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            };
//                            new AlertDialog.Builder(this).setTitle("비밀번호는 4 ~ 16자리로 설정해 주시기 바랍니다.").
//                                    setNegativeButton("확인", cancelListener).show();
                            dial.call("Warning", "비밀번호는 4 ~ 16자리로 설정해 주시기 바랍니다.");
                        }
                    } else {
//                        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        };
//                        new AlertDialog.Builder(this).setTitle("E-mail 형식이 아닙니다.\n예제 - example@exam.com").
//                                setNegativeButton("확인", cancelListener).show();

                        dial.call("Warning", "E-mail 형식이 아닙니다.\n예제 - example@exam.com");

                    }

                } else {
//                    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    };
//                    new AlertDialog.Builder(this).setTitle("이미 가입 된 E-mail 주소 입니다. 아이디 찾기를 이용해 주시기 바랍니다").
//                            setNegativeButton("확인", cancelListener).show();
                    dial.call("Warning", "이미 가입 된 E-mail 주소 입니다. 아이디 찾기를 이용해 주시기 바랍니다");
                }
            }
            break;
        }
    }

    public class idCheckThread extends Thread {

        public idCheckThread(String id) {
            url = login_url + id;
        }

        public void run() {
            StringBuilder jsonHtml = new StringBuilder();

            super.run();
            try {
                URL phpUrl = new URL(url);

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
                tempId2 = getId;

                jsonHtml.setLength(0);  // clear
                getId = "";            // clear

                Log.e(TAG, "jObject - " + jObject);
                Log.e(TAG, "jsonArray - " + jsonArray);
                Log.e(TAG, "item - " + item);
                Log.e(TAG, "getId - " + getId);
                Log.e(TAG, "tempId2 -- getId - " + tempId2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkEmail(String email) {

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;

    }
}