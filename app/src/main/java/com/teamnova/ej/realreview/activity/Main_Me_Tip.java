package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncMyFeedRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Set;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Tip_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Tip_Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main_Me_Tip extends AppCompatActivity {
    public ArrayList<MainMe_MyFeed_Tip_Set> tipArrayData = new ArrayList<>();
    private String myFeedURL = "http://222.122.203.55/realreview/myFeed/myFeed.php";
    private android.support.v7.widget.RecyclerView mainMeTipRV;
    ArrayList<MainMe_MyFeed_Tip_Set> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main__me__tip);

        init();
        myFeedAdapting();

    }

    private void init() {

        mainMeTipRV = findViewById(R.id.mainMeTipRV);

    }


    private void myFeedAdapting() {

        data.clear();

        /**
         *  tipDefault Parsing
         */
        JSONObject conn;

        try {
            conn = new AsyncMyFeedRequest(myFeedURL, this).execute().get(10000, TimeUnit.MILLISECONDS);

            JSONArray myFeedTipParsing = conn.getJSONArray("tipDefault");
            Log.d("ASYNCMyFeed_TIP", "myFeedTipParsing : " + myFeedTipParsing);
            MainMe_MyFeed_Tip_Adapter feedAdapter = new MainMe_MyFeed_Tip_Adapter(this, data);
            LinearLayoutManager mainMeMyFeedLayoutManager = new LinearLayoutManager(this);
            mainMeTipRV.setLayoutManager(mainMeMyFeedLayoutManager);
            mainMeTipRV.setHasFixedSize(false);
            mainMeTipRV.setAdapter(feedAdapter);
            for (int i = 0; i < myFeedTipParsing.length(); i++) {

                JSONObject myFeedTipParsing2 = myFeedTipParsing.getJSONObject(i);
                String type = myFeedTipParsing2.getString("type");
                String idx = myFeedTipParsing2.getString("idx");
                String shop_id = myFeedTipParsing2.getString("shop_id");
                String user_id = myFeedTipParsing2.getString("user_id");
                String tip = myFeedTipParsing2.getString("tip");
                String regdate = myFeedTipParsing2.getString("regdate");
                String nearby = myFeedTipParsing2.getString("nearby");
                String nick = myFeedTipParsing2.getString("nick");
                String locality = myFeedTipParsing2.getString("locality");
                String shopTipCount = myFeedTipParsing2.getString("shopTipCount");
                String shopidCheck = myFeedTipParsing2.getString("shopidCheck");
                String shopImagePath = myFeedTipParsing2.getString("shopImagePath");
                String shopTipRowNum = myFeedTipParsing2.getString("shopTipRowNum");
                String shopName = myFeedTipParsing2.getString("shopName");


                MainMe_MyFeed_Tip_Set getData = new MainMe_MyFeed_Tip_Set(
                        type,
                        idx,
                        shop_id,
                        user_id,
                        tip,
                        regdate,
                        nearby,
                        nick,
                        locality,
                        shopTipCount,
                        shopidCheck,
                        shopImagePath,
                        shopTipRowNum,
                        shopName
                );

                data.add(0, getData);
            }
            feedAdapter.notifyDataSetChanged();
        } catch (
                InterruptedException e)
        {
            e.printStackTrace();
        } catch (
                ExecutionException e)
        {
            e.printStackTrace();
        } catch (
                TimeoutException e)
        {
            e.printStackTrace();
        } catch (
                JSONException e)
        {
            e.printStackTrace();
        }
    }
}



