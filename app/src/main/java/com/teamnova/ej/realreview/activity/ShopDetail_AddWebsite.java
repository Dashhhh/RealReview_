package com.teamnova.ej.realreview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.teamnova.ej.realreview.Asynctask.AsyncShopDetailWebsiteUpdate;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;
import com.teamnova.ej.realreview.util.ValidateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_AddWebsite extends AppCompatActivity {

    com.beardedhen.androidbootstrap.BootstrapEditText addWebText;
    com.beardedhen.androidbootstrap.BootstrapButton addWebSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__add_website);

        init();
        listener();

    }

    private void init() {

        addWebText = findViewById(R.id.addWebText);
        addWebSubmit = findViewById(R.id.addWebSubmit);

        addWebText.setText("http://www.");

    }

    private void listener() {

        addWebSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Default dial = new Dialog_Default(ShopDetail_AddWebsite.this);
                String url = addWebText.getText().toString();

                if (addWebText.getText().toString().isEmpty()) {
                    Log.d("Website_Check", "Empty");
                    dial.tempCall("OOPS!", "내용을 입력하고 저장하기를 눌러주세요.");
                } else {
                    boolean check = ValidateUtil.validateHttp(addWebText.getText().toString());
                    Log.d("Website_Check", "addWebText : " + addWebText.getText().toString());
                    Log.d("Website_Check", "Validate check : " + check);
                    if (check) {
//                        dial.tempCall("정규식 TRUE", "!");

                        StringBuilder conn;
                        try {

                            SharedPreferenceUtil pref = new SharedPreferenceUtil(getApplicationContext());
                            conn = new AsyncShopDetailWebsiteUpdate(ShopDetail_Main.SHOP_ID, url, pref.getSharedData("isLogged_nick"), pref.getSharedData("isLogged_id"), ShopDetail_AddWebsite.this).execute().get(10000, TimeUnit.MILLISECONDS);
                            Log.d("websiteUpdate", "conn Parsing : " + conn);

                            JSONObject parsing0 = new JSONObject(String.valueOf(conn));
                            Log.d("websiteUpdate", "parsing0 : " + parsing0);

                            JSONArray parsing1 = parsing0.getJSONArray("errorcheck");
                            Log.d("websiteUpdate", "parsing1 : " + parsing1);

                            JSONArray parsing2 = parsing0.getJSONArray("q");
                            Log.d("websiteUpdate", "parsing2 : " + parsing2);

                            JSONArray parsing3 = parsing0.getJSONArray("result");
                            Log.d("websiteUpdate", "parsing3 : " + parsing3);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        finish();

                    } else {
                        dial.tempCall("OOPS!", "내용을 예제와 같이 다시 입력해 주세요!");
                    }
                }
            }
        });
    }
}
