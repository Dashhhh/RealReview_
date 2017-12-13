package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncQuestionRequest;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewRequest;
import com.teamnova.ej.realreview.Asynctask.AsyncShopDetailImageURLRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Set;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_ReviewAll_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_ReviewAll_Set;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_Review_LV_Set;
import com.teamnova.ej.realreview.util.TransDateToSimple;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Review_All extends AppCompatActivity {


    android.support.v7.widget.RecyclerView reviewAllRV;
    private String shopId;
    private String reviewRequestURL = "http://222.122.203.55/realreview/reviewAll.php";
    ArrayList<ShopDetail_Main_RV_ReviewAll_Set> data = new ArrayList<>();
    private JSONArray a0;
    ShopDetail_Main_RV_ReviewAll_Set dataSet;
    private float titleRatingPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__review__viewing);
        shopId = ShopDetail_Main.SHOP_ID;
        init();

    }

    private void init() {
        dataSet = new ShopDetail_Main_RV_ReviewAll_Set();
        reviewAllRV = findViewById(R.id.reviewAllRV);

        data.clear();
        ShopDetail_Main_RV_ReviewAll_Adapter adapter = new ShopDetail_Main_RV_ReviewAll_Adapter(data, this);
        LinearLayoutManager themeLayoutSet = new LinearLayoutManager(this);
        reviewAllRV.setHasFixedSize(true);
        reviewAllRV.setLayoutManager(themeLayoutSet);
        reviewAllRV.setAdapter(adapter);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new AsyncReviewRequest(reviewRequestURL, shopId, this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("QUESTION_ASYNC", "전송결과 MAIN - jsonObject: " + jsonObject);
            JSONArray reviewJSONArray = jsonObject.getJSONArray("info");
            for (int i = 0; i < reviewJSONArray.length(); i++) {
                JSONObject jsonObject1 = reviewJSONArray.getJSONObject(i);
                Log.d("ReviewAll", "jsonObject1 :" + jsonObject1);

                String getReviewIdx = jsonObject1.getString("idx");
                Log.d("ReviewAll", "Casting Info - idx :" + i + "번 :" + getReviewIdx);

                String getReviewText = jsonObject1.getString("review");
                Log.d("ReviewAll", "Casting Info - review :" + i + "번 :" + getReviewText);

                String getRegdate = jsonObject1.getString("regdate");
                Log.d("ReviewAll", "Casting Info - getRegdate :" + i + "번 :" + getRegdate);

                String getUserId = jsonObject1.getString("id_user");
                Log.d("ReviewAll", "Casting Info - getUserId :" + i + "번 :" + getUserId);

                String getNick = jsonObject1.getString("nick");
                Log.d("ReviewAll", "Casting Info - nick :" + i + "번 :" + getNick);

                String getRating = jsonObject1.getString("rating");
                Log.d("ReviewAll", "Casting Info - getRating :" + i + "번 :" + getRating);

                String getProfileImageURL = jsonObject1.getString("profileimage");
                Log.d("ReviewAll", "Casting Info - getProfileImageURL :" + i + "번 :" + getProfileImageURL);

                String getLocality = jsonObject1.getString("locality");
                Log.d("ReviewAll", "Casting Info - getLocality :" + i + "번 :" + getLocality);

                String getNearby = jsonObject1.getString("nearby");
                Log.d("ReviewAll", "Casting Info - getNearby :" + i + "번 :" + getNearby);

                String getCountCool = jsonObject1.getString("countCool");
                Log.d("ReviewAll", "Casting Info - getCountCool :" + i + "번 :" + getCountCool);

                String getCountGood = jsonObject1.getString("countGood");
                Log.d("ReviewAll", "Casting Info - getCountGood :" + i + "번 :" + getCountGood);

                String getCountUseful = jsonObject1.getString("countUseful");
                Log.d("ReviewAll", "Casting Info - getCountUseful :" + i + "번 :" + getCountUseful);

                boolean selectableUseful = jsonObject1.getBoolean("useful_selectable");
                Log.d("ReviewAll", "Casting Info - useful_selectable :" + i + "번 :" + selectableUseful);

                boolean selectableGood = jsonObject1.getBoolean("good_selectable");
                Log.d("ReviewAll", "Casting Info - good_selectable :" + i + "번 :" + selectableGood);

                boolean selectableCool = jsonObject1.getBoolean("cool_selectable");
                Log.d("ReviewAll", "Casting Info - cool_selectable :" + i + "번 :" + selectableCool);

                String getCountFollower = jsonObject1.getString("follower_cnt");
                String getCountReview = jsonObject1.getString("review_cnt");
                String getCountImage = jsonObject1.getString("image_cnt");

                TransDateToSimple transDate = new TransDateToSimple();
                String simpleDate = transDate.trans(jsonObject1.getJSONObject("datediff"));
                Log.d("SimpleDateCheck", "Before Parsing -  jsonObject1.getJSONObject(\"datediff\"):" + jsonObject1.getJSONObject("datediff"));
                Log.d("SimpleDateCheck", "After Parsing - simpleDate :" + simpleDate);


                ShopDetail_Main_RV_ReviewAll_Set setData = new ShopDetail_Main_RV_ReviewAll_Set(getReviewIdx, getProfileImageURL,getCountFollower, getCountReview, getCountImage, getReviewText, simpleDate,getUserId, getRating, 0.0f, getNick, getNearby, getLocality, getCountCool, getCountUseful, getCountGood, selectableUseful, selectableGood, selectableCool);
                data.add(setData);
//
//                reviewLvAdapter.addItem(getReviewIdx, getProfileImageURL, getCountFollower, getCountReview, getCountImage, getReviewText,
//                        getRegdate, getUserId, getRating, getNick, ff, getProfileImageURL, getLocality, getNearby,
//                        getCountCool, getCountGood, getCountUseful,
//                        selectableUseful, selectableGood, selectableCool);
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (TimeoutException e1) {
            e1.printStackTrace();
        }

    }
}

