package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncMyFeedRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Question_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Question_Set;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Set;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main_Me_Reviews extends AppCompatActivity {

    private String myFeedURL = "http://222.122.203.55/realreview/myFeed/myFeed.php";
    public ArrayList<MainMe_MyFeed_Review_Set> reviewArrayData = new ArrayList<>();

    android.support.v7.widget.RecyclerView mainMeMyFeedRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main__me__reviews);

        init();
        myFeedAdapting();



    }

    private void init() {
        mainMeMyFeedRV = findViewById(R.id.mainMeMyFeedRV);
    }


    private void myFeedAdapting() {


        reviewArrayData.clear();

        JSONObject conn;
        try {
            conn = new AsyncMyFeedRequest(myFeedURL, this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("ASYNCMyFeed", "conn : " + conn);

            /**
             * reviewDefault Parsing
             */

            JSONArray myFeedReviewParsing = conn.getJSONArray("reviewDefault");
            Log.d("ASYNCMyFeed_REVIEW", "myFeedReviewParsing : " + myFeedReviewParsing);
            MainMe_MyFeed_Review_Adapter feedAdapter = new MainMe_MyFeed_Review_Adapter(this, reviewArrayData);
            LinearLayoutManager mainMeMyFeedLayoutManager = new LinearLayoutManager(this);
            mainMeMyFeedRV.setLayoutManager(mainMeMyFeedLayoutManager);
            mainMeMyFeedRV.setHasFixedSize(false);
            mainMeMyFeedRV.setAdapter(feedAdapter);
            for (int i = 0; i < myFeedReviewParsing.length(); i++) {

                JSONObject myFeedReviewParsing2 = myFeedReviewParsing.getJSONObject(i);
                String type = myFeedReviewParsing2.getString("type");
                String idx = myFeedReviewParsing2.getString("idx");
                String review = myFeedReviewParsing2.getString("review");
                String regData = myFeedReviewParsing2.getString("regData");
                String id_shop = myFeedReviewParsing2.getString("id_shop");
                String id_user = myFeedReviewParsing2.getString("id_user");
                String rating = myFeedReviewParsing2.getString("rating");
                String nick = myFeedReviewParsing2.getString("nick");
                String shopName = myFeedReviewParsing2.getString("shopName");
                String nearBy = myFeedReviewParsing2.getString("nearby");
                String locality = myFeedReviewParsing2.getString("locality");
                String countUseful = myFeedReviewParsing2.getString("countUseful");
                String countGood = myFeedReviewParsing2.getString("countGood");
                String countCool = myFeedReviewParsing2.getString("countCool");
                String shopReviewCount = myFeedReviewParsing2.getString("shopReviewCount");
                String shopImagePath = myFeedReviewParsing2.getString("shopImagepath");

                String cool_Idx = "";
                String cool_Id_shop = "";
                String cool_Id_user = "";
                String cool_Nick = "";
                String cool_ReviewIdx = "";
                String good_Idx = "";
                String good_Id_shop = "";
                String good_Id_user = "";
                String good_Nick = "";
                String good_ReviewIdx = "";
                String useful_Idx = "";
                String useful_Id_shop = "";
                String useful_Id_user = "";
                String useful_Nick = "";
                String useful_ReviewIdx = "";
                ArrayList<String> userCool = new ArrayList<>();
                ArrayList<String> userGood = new ArrayList<>();
                ArrayList<String> userUseful = new ArrayList<>();

                JSONArray myFeedReviewParsingCool = myFeedReviewParsing2.getJSONArray("cool_array");
                JSONArray myFeedReviewParsingGood = myFeedReviewParsing2.getJSONArray("good_array");
                JSONArray myFeedReviewParsingUseful = myFeedReviewParsing2.getJSONArray("useful_array");

                for (int j = 0; j < myFeedReviewParsingCool.length(); j++) {
                    JSONObject myFeedReviewParsingCool2 = myFeedReviewParsingCool.getJSONObject(j);
                    cool_Idx = myFeedReviewParsingCool2.getString("idx");
                    cool_Id_shop = myFeedReviewParsingCool2.getString("id_shop");
                    cool_Id_user = myFeedReviewParsingCool2.getString("id_user");
                    cool_Nick = myFeedReviewParsingCool2.getString("nick");
                    cool_ReviewIdx = myFeedReviewParsingCool2.getString("idx_review");
                    userCool.add(0, cool_Nick);

                }
                for (int j = 0; j < myFeedReviewParsingGood.length(); j++) {
                    JSONObject myFeedReviewParsingGood2 = myFeedReviewParsingGood.getJSONObject(j);
                    good_Idx = myFeedReviewParsingGood2.getString("idx");
                    good_Id_shop = myFeedReviewParsingGood2.getString("id_shop");
                    good_Id_user = myFeedReviewParsingGood2.getString("id_user");
                    good_Nick = myFeedReviewParsingGood2.getString("nick");
                    good_ReviewIdx = myFeedReviewParsingGood2.getString("idx_review");
                    userGood.add(0, cool_Nick);
                }

                for (int j = 0; j < myFeedReviewParsingUseful.length(); j++) {
                    JSONObject myFeedReviewParsingUseful2 = myFeedReviewParsingUseful.getJSONObject(j);
                    useful_Idx = myFeedReviewParsingUseful2.getString("idx");
                    useful_Id_shop = myFeedReviewParsingUseful2.getString("id_shop");
                    useful_Id_user = myFeedReviewParsingUseful2.getString("id_user");
                    useful_Nick = myFeedReviewParsingUseful2.getString("nick");
                    useful_ReviewIdx = myFeedReviewParsingUseful2.getString("idx_review");
                    userUseful.add(0, useful_Nick);
                }


                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                MainMe_MyFeed_Review_Set addData = new MainMe_MyFeed_Review_Set(
                        type,
                        idx,
                        pref.getSharedData("isLogged_profileImagePath"),
                        review,
                        regData,
                        id_user,
                        rating,
                        nick,
                        nearBy,
                        locality,
                        countCool,
                        countUseful,
                        countGood,
                        shopImagePath,
                        shopName,
                        shopReviewCount,
                        userCool,
                        userGood,
                        userUseful
                );

                reviewArrayData.add(0, addData);
                MainMe_MyFeed_Review_Set checker = reviewArrayData.get(i);
//                Log.d("MainMe_MyFeed_Review_Set", addData.getNick());
//                Log.d("MainMe_MyFeed_Review_Set", addData.getNearby());
//                Log.d("MainMe_MyFeed_Review_Set-addSize", String.valueOf(reviewArrayData.get(i)));
//                Log.d("MainMe_MyFeed_Review_Set-addSize", "checker.getNick :" + checker.getNick());

            }
            feedAdapter.notifyDataSetChanged();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
