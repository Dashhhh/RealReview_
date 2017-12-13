package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncQuestionRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Set;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Viewholder;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;
import com.teamnova.ej.realreview.util.TransDateToSimple;

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
    com.beardedhen.androidbootstrap.BootstrapButton questionAllNoQuestionButton, questionAllAnswer, questionAllStar;

    String questionRequestURL = "http://222.122.203.55/realreview/question/questionRequestAll.php";
    private JSONObject jsonObject;
    private String shopId;
    private JSONArray a0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__question__all);
        shopId = ShopDetail_Main.SHOP_ID;
        init();
        listener();
    }

    @Override
    protected void onStart() {
        super.onStart();

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

                boolean curious = getArray.getBoolean("curious");
                Log.d("QUESTION_RV", "JSON Parsing...curious    : " + curious);

                String regdate = getArray.getString("regdate");
                String shopid = getArray.getString("shopid");
                String userid = getArray.getString("userid");
                String question = getArray.getString("question");
                String nick = getArray.getString("nick");
                String idx = getArray.getString("idx");
                String answerCount = getArray.getString("answerCount");
                String metooCount = getArray.getString("metooCount");
                String imagepath = getArray.getString("imagepath");

                String follower_cnt = getArray.getString("follower_cnt");
                String review_cnt = getArray.getString("review_cnt");
                String image_cnt = getArray.getString("image_cnt");



                TransDateToSimple transDate = new TransDateToSimple();
                String simpleDate = transDate.trans(getArray.getJSONObject("datediff"));
                Log.d("SimpleDateCheck", "Before Parsing -  jsonObject1.getJSONObject(\"datediff\"):" + getArray.getJSONObject("datediff"));
                Log.d("SimpleDateCheck", "After Parsing - simpleDate :" + simpleDate);

                ShopDetail_Main_RV_QuestionAll_Set adapterSet = new ShopDetail_Main_RV_QuestionAll_Set(
                        "",
                        follower_cnt,
                        review_cnt,
                        image_cnt,
                        question,
                        simpleDate,
                        nick,
                        idx,
                        answerCount,
                        metooCount,
                        curious,
                        imagepath
                );
                Log.d("QUESTION_RV", "JSON Parsing...shopid  : " + shopid);
                Log.d("QUESTION_RV", "JSON Parsing...userid  : " + userid);
                Log.d("QUESTION_RV", "JSON Parsing...regdate : " + regdate);
                Log.d("QUESTION_RV", "JSON Parsing...nick    : " + nick);
                Log.d("QUESTION_RV", "JSON Parsing...idx    : " + idx);
                Log.d("QUESTION_RV", "JSON Parsing...answerCount    : " + answerCount);
                Log.d("QUESTION_RV", "JSON Parsing...metooCount    : " + metooCount);
                Log.d("QUESTION_RV", "JSON Parsing...imagepath    : " + imagepath);

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

        if (a0.length() == 1) {
            questionAllRV.setVisibility(View.GONE);
            questionAllNoQuestionRoot.setVisibility(View.VISIBLE);
        }
/*

        final GestureDetector gestureDetector = new GestureDetector(ShopDetail_Question_All.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                */
/*
                   버튼 눌렀다가 떼었을때만 TRUE
                 *//*

                return true;
            }
        });


        questionAllRV.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {


                long l = 0;
                int p = 0;
                View v = rv.findChildViewUnder(e.getX(), e.getY());
                View vv = rv.findContainingItemView(v);
                View vvv = rv.findViewById(vv.getId());
                RecyclerView.ViewHolder vvvv = rv.findViewHolderForItemId(l);
                Log.d("addOnItemTouchListener", " v :"   +v);
                Log.d("addOnItemTouchListener", " vv :"  +vv);
                Log.d("addOnItemTouchListener", " vvv :" +vvv);
                Log.d("addOnItemTouchListener", " vvvv :" +vvvv);


                if (v != null && gestureDetector.onTouchEvent(e)) {
                    Log.d("addOnItemTouchListener", " rv.getChildAdapterPosition(v)); :" + rv.getChildAdapterPosition(v));
                    Log.d("addOnItemTouchListener", " rv.getChildItemId(v)); : " + rv.getChildItemId(v));
                    Log.d("addOnItemTouchListener", " rv.getChildViewHolder(v)); :     " + rv.getChildViewHolder(v));
                    Log.d("addOnItemTouchListener", " rv.getChildLayoutPosition(v)); :" + rv.getChildLayoutPosition(v));
                    Log.d("addOnItemTouchListener", " rv.getChildAdapterPosition(v)); :" + rv.getChildAdapterPosition(v));
                    Log.d("addOnItemTouchListener", " rv.getTransitionName()); :" + rv.getTransitionName());
                    Log.d("addOnItemTouchListener", " rv.getScrollState()); :" + rv.getScrollState());
                    Log.d("addOnItemTouchListener", " rv.findViewHolderForAdapterPosition(p)); :" + rv.findViewHolderForAdapterPosition(p));
                    Log.d("addOnItemTouchListener", " rv.findViewHolderForItemId(l)); :" + rv.findViewHolderForItemId(l));

                    String aa = String.valueOf(rv.getChildItemId(v));
                    Log.d("addOnItemTouchListener", "aa :" + aa);
                    Log.d("addOnItemTouchListener", "aa :" + v.getId());


                    // TODO : Move to Main -> Adapter

                }

                switch (v.getId()) {

                    case R.id.questionAllText: {
                        Log.d("RECYCLER_POSITION", "TEXT : rv.getChildAdapterPosition(v)" + rv.getChildAdapterPosition(v));
                        Log.d("RECYCLER_POSITION", "Text : v.getId()" + v.getId());
                        break;
                    }

                    case R.id.questionAllStar: {
                        Log.d("RECYCLER_POSITION", "Star : rv.getChildAdapterPosition(v)" + rv.getChildAdapterPosition(v));
                        break;
                    }

                    case R.id.questionAllAnswer: {
                        Log.d("RECYCLER_POSITION", "Answer : rv.getChildAdapterPosition(v)" + rv.getChildAdapterPosition(v));
                        break;
                    }

                }


                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d("addOnItemTouchListener", "onTouchEvent");

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                Log.d("addOnItemTouchListener", "onRequestDisallowInterceptTouchEvent");

            }
        });
*/

    }

}

