package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncQuestionRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Set;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Question_All extends AppCompatActivity {
    android.support.v7.widget.RecyclerView questionAllRV;
    ArrayList<ShopDetail_Main_RV_QuestionAll_Set> data = new ArrayList<>();
    LinearLayout questionAllNoQuestionRoot;
    com.beardedhen.androidbootstrap.BootstrapButton questionAllNoQuestionButton;

    String questionRequestURL = "http://222.122.203.55/realreview/question/questionRequestAll.php";
    private JSONObject jsonObject;
    private String shopId;
    private JSONArray a0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__question__all);


        Bundle bundle = getIntent().getExtras();
        shopId = bundle.getString("SHOPID");
        init();
        listener();
        initializingData();

    }

    private void init() {

        questionAllNoQuestionRoot = findViewById(R.id.questionAllNoQuestionRoot);
        questionAllNoQuestionRoot.setVisibility(View.GONE);
        questionAllNoQuestionButton = findViewById(R.id.questionAllNoQuestionButton);

    }

    private void listener() {

        questionAllNoQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopDetail_Question_All.this);
                Intent intent = new Intent(ShopDetail_Question_All.this, ShopDetail_Question_Submit.class);
                intent.putExtra("SHOPID", shopId);
                intent.putExtra("reviewTitle", ShopDetail_Main.TITLE);
                intent.putExtra("reviewShopId", ShopDetail_Main.SHOP_ID);
                intent.putExtra("reviewUserId", pref.getSharedData("isLogged_id"));
                intent.putExtra("reviewUserNick", pref.getSharedData("isLogged_nick"));
                startActivity(intent);
                finish();
            }
        });


    }

    private void initializingData() {


        data.clear();
        questionAllRV = findViewById(R.id.questionAllRV);
        ShopDetail_Main_RV_QuestionAll_Adapter adapter = new ShopDetail_Main_RV_QuestionAll_Adapter(this, data);
        LinearLayoutManager themeLayoutSet = new LinearLayoutManager(this);
        questionAllRV.setHasFixedSize(true);
        questionAllRV.setLayoutManager(themeLayoutSet);
        questionAllRV.setAdapter(adapter);

        try {
            jsonObject = new AsyncQuestionRequest(questionRequestURL, shopId, this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("QUESTION_ASYNC", "전송결과 MAIN - jsonObject: " + jsonObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        ShopDetail_Main_RV_QuestionAll_Set dataSet = new ShopDetail_Main_RV_QuestionAll_Set();


        try {
            a0 = jsonObject.getJSONArray("questionresult");


            for (int i = 0; i < a0.length(); i++) {


                JSONObject getArray = a0.getJSONObject(i);
                String regdate = getArray.getString("regdate");
                String shopid = getArray.getString("shopid");
                String userid = getArray.getString("userid");
                String question = getArray.getString("question");
                String nick = getArray.getString("nick");
                ShopDetail_Main_RV_QuestionAll_Set adapterSet = new ShopDetail_Main_RV_QuestionAll_Set("", "0", "0", "0", question, regdate, nick);
                Log.d("TIP_ASYNC", "JSON Parsing...shopid  : " + shopid);
                Log.d("TIP_ASYNC", "JSON Parsing...userid  : " + userid);
                Log.d("TIP_ASYNC", "JSON Parsing...regdate : " + regdate);
                Log.d("TIP_ASYNC", "JSON Parsing...nick    : " + nick);

                data.add(0, adapterSet);

            }

            Log.d("QUESTION_ASYNC", "a0 " + a0);
            String a1 = String.valueOf(a0.getJSONArray(0));
            Log.d("QUESTION_ASYNC", "a1 " + a1);



        } catch (JSONException e) {
            Log.d("QUESTION_ASYNC", "ENTER CATCH ");
            e.printStackTrace();

        }
        Log.d("QUESTION_ASYNC", "a0.length() " + a0.length());

        if(a0.length() ==1) {
            questionAllRV.setVisibility(View.GONE);
            questionAllNoQuestionRoot.setVisibility(View.VISIBLE);
        }

    }
}
