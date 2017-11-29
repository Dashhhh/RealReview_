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

public class Signup2 extends AppCompatActivity implements View.OnClickListener {

    ImageView sign2Image;
    EditText sign2Nick;
    Button sign2Check;
    Boolean idCheckFlag = false;
    public boolean activityFlag = false;
    String url = null, userId = null, userPw = null;
    Handler mHandler = new Handler(Looper.getMainLooper());
    String login_url = "http://222.122.203.55/realreview/signup/chknick.php?nick=";
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON = "realreview";
    private static final String TAG_NICK = "nick";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    String tempNick, tempNick2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        sign2Image = findViewById(R.id.sign2Image);
        sign2Nick = findViewById(R.id.sign2Nick);
        sign2Check = findViewById(R.id.sign2Check);

        sign2Check.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sign2Check: {
                String nick = sign2Nick.getText().toString();
                tempNick = nick;
                boolean nickVali;
                if (!tempNick.isEmpty()) {
                    ValidateUtil vUtil = new ValidateUtil();
                    Boolean bNIck = ValidateUtil.validateNick(nick);
                    if (bNIck) {
                        nickCheckThread nickCheck = new nickCheckThread(nick);
                        nickCheck.start();
                        try {
                            nickCheck.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (tempNick2 == null || tempNick2 == "") {
                            SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                            pref.setSharedData("SIGNIN_NICK", nick);
                            Intent intent = new Intent(Signup2.this, Signup3.class);
                            startActivity(intent);

                        } else {
                            Dialog_Default dial = new Dialog_Default(this);
                            dial.tempCall("Warning", "중복되는 닉네임 입니다. 다른 닉네임을 입력해 주시기 바랍니다.");
                        }
                    }   // nick Validate
                    else {
                        Dialog_Default dial = new Dialog_Default(this);
                        dial.tempCall("Warning", "닉네임은 영문, 숫자 5~12자 이내 입력 가능합니다.");
                    }
                } else {
                    Dialog_Default dial = new Dialog_Default(this);
                    dial.tempCall("Warning", "닉네임을 입력해 주시기 바랍니다.");
                }
                Log.e("tempNICK", "tempNick:" + tempNick);
                Log.e("tempNICK2", "tempNick2:" + tempNick2);
                Log.e("nick", "nick:" + nick);


                /**
                 * Nick Validate
                 * 강제 True (Temp)
                 */
//                nickVali = true;
//                if (nick.isEmpty()) {
//                    Dialog_Default dial = new Dialog_Default(this);
//                    dial.call("Warning","닉네임을 입력해 주시기 바랍니다.");
//                } else {
//                    if (!nickVali) {
//                        Dialog_Default dial = new Dialog_Default(this);
//                        dial.call("Warning","닉네임 형식이 아닙니다.\n3~8글자 이내 한글영문만 가능합니다.");
//                    } else {
//                        if (!tempNick.equals(tempNick2)) {
//                            SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
//                            pref.setSharedData("SIGNIN_NICK",nick);
//                            Intent intent = new Intent(Signup2.this, Signup3.class);
//                            startActivity(intent);
//                        } else if (!tempNick.isEmpty()) {
//                            Dialog_Default dial = new Dialog_Default(this);
//                            dial.call("Warning","중복 되는 닉네임 입니다. 다른 닉네임을 사용해 주시기 바랍니다");
//                        }
//                    }
//                }

                sign2Nick.setText("");
                tempNick = "";
                tempNick2 = "";

                break;
            }   // case sign2Check
        }       // switch
    }   //onClick

    public class nickCheckThread extends Thread {

        public nickCheckThread(String nick) {
            url = login_url + nick;
        }

        public void run() {
            super.run();
            StringBuilder jsonHtml = new StringBuilder();
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
                String getNick = item.getString(TAG_NICK);
                tempNick2 = getNick;

                jsonHtml.setLength(0);  // clear
                getNick = "";          // clear


                Log.e(TAG, "jObject - " + jObject);
                Log.e(TAG, "jsonArray - " + jsonArray);
                Log.e(TAG, "item - " + item);
                Log.e(TAG, "getNick - " + getNick);
                Log.e(TAG, "tempNick2 -- getNick - " + tempNick2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkNick(String checkNickPattern) {

        String regex = "/^[a-z0-9_-]{5,12}$/";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(checkNickPattern);
        boolean isNormal = m.matches();
        return isNormal;

    }
}
