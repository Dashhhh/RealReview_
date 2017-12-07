package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncMyFeedRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Bookmark_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Bookmark_Set;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Tip_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Tip_Set;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main_Me_Bookmark extends AppCompatActivity {


    android.support.v7.widget.RecyclerView mainMeQuestionRV;
    ArrayList<MainMe_MyFeed_Bookmark_Set> data = new ArrayList<>();
    private String myFeedURL = "http://222.122.203.55/realreview/bookmark/shopDetail_bookmarkChecked.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main__me__bookmark);

        init();
        apdapting();


    }

    private void init() {
        mainMeQuestionRV = findViewById(R.id.mainMeQuestionRV);
    }

    private void apdapting() {

        data.clear();

        /**
         *  tipDefault Parsing
         */
        JSONObject conn;

        try {
            conn = new AsyncMyFeedRequest(myFeedURL, this).execute().get(10000, TimeUnit.MILLISECONDS);

            MainMe_MyFeed_Bookmark_Adapter feedAdapter = new MainMe_MyFeed_Bookmark_Adapter(this, data);
            LinearLayoutManager mainMeMyFeedLayoutManager = new LinearLayoutManager(this);
            mainMeQuestionRV.setLayoutManager(mainMeMyFeedLayoutManager);
            mainMeQuestionRV.setHasFixedSize(false);
            mainMeQuestionRV.setAdapter(feedAdapter);


            JSONArray myFeedBookmarkParsing = conn.getJSONArray("bookmarkInfo");

//            for (int h = 0; h < myFeedBookmarkParsing.length(); h++) {
            for (int h = 0; h < 1; h++) {


                Log.d("ASYNCMyFeed_TIP", "myFeedTipParsing : " + myFeedBookmarkParsing);
                for (int i = 0; i < myFeedBookmarkParsing.length(); i++) {

                    JSONObject myFeedBookmarkParsing2 = myFeedBookmarkParsing.getJSONObject(i);


                    String shopThumbnail = myFeedBookmarkParsing2.getString("ShopThumbnail");
                    String checkinCount = myFeedBookmarkParsing2.getString("CheckinCount");
                    String webSite = myFeedBookmarkParsing2.getString("webSite");
                    String call = myFeedBookmarkParsing2.getString("callNumber");
                    String shopTime = myFeedBookmarkParsing2.getString("shopTime");
                    String shopName = myFeedBookmarkParsing2.getString("shopName");
                    String regdate = myFeedBookmarkParsing2.getString("Regdate");
                    String address = myFeedBookmarkParsing2.getString("address");


                    String id = myFeedBookmarkParsing2.getString("id");
//                    String address = myFeedBookmarkParsing2.getString("address");
                    String latitude = myFeedBookmarkParsing2.getString("latitude");
                    String longtitude = myFeedBookmarkParsing2.getString("longtitude");
                    String viewportSouthWestLat = myFeedBookmarkParsing2.getString("viewportSouthWestLat");
                    String viewportSouthWestLng = myFeedBookmarkParsing2.getString("viewportSouthWestLng");
                    String viewportNorthEastLat = myFeedBookmarkParsing2.getString("viewportNorthEastLat");
                    String viewportNorthEastLng = myFeedBookmarkParsing2.getString("viewportNorthEastLng");
//                    String shopName = myFeedBookmarkParsing2.getString("shopName");
                    String shopOpen = myFeedBookmarkParsing2.getString("shopOpen");
                    String shopClose = myFeedBookmarkParsing2.getString("shopClose");
//                    String shopTime = myFeedBookmarkParsing2.getString("shopTime");
                    String shopTheme1 = myFeedBookmarkParsing2.getString("shopTheme1");
                    String shopTheme2 = myFeedBookmarkParsing2.getString("shopTheme2");
                    String shopTheme3 = myFeedBookmarkParsing2.getString("shopTheme3");
                    String shopTheme4 = myFeedBookmarkParsing2.getString("shopTheme4");
                    String shopTheme5 = myFeedBookmarkParsing2.getString("shopTheme5");
                    String callNumber = myFeedBookmarkParsing2.getString("callNumber");
                    String indexShopAdd = myFeedBookmarkParsing2.getString("indexShopAdd");
                    String permanentlyClosed = myFeedBookmarkParsing2.getString("permanentlyClosed");
                    String priceLevel = myFeedBookmarkParsing2.getString("priceLevel");

                    SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

                    pref.setSharedData("ID" + i + "bm", id);
                    pref.setSharedData("TITLE" + i + "bm", shopName);
                    pref.setSharedData("ADDRESS" + i + "bm", address);
                    pref.setSharedData("LAT" + i + "bm", latitude);
                    pref.setSharedData("LNG" + i + "bm", longtitude);
                    pref.setSharedData("V_SW_LAT" + i + "bm", viewportSouthWestLat);
                    pref.setSharedData("V_SW_LNG" + i + "bm", viewportSouthWestLng);
                    pref.setSharedData("V_NE_LAT" + i + "bm", viewportNorthEastLat);
                    pref.setSharedData("V_NE_LNG" + i + "bm", viewportNorthEastLng);
                    pref.setSharedData("OPEN" + i + "bm", shopOpen);
                    pref.setSharedData("CLOSE" + i + "bm", shopClose);
                    pref.setSharedData("MARKERTAG" + i + "bm", shopClose);
                    pref.setSharedData("THEME1" + i + "bm", shopTheme1);
                    pref.setSharedData("THEME2" + i + "bm", shopTheme2);
                    pref.setSharedData("THEME3" + i + "bm", shopTheme3);
                    pref.setSharedData("THEME4" + i + "bm", shopTheme4);
                    pref.setSharedData("THEME5" + i + "bm", shopTheme5);
                    pref.setSharedData("CALL" + i + "bm", callNumber);
                    pref.setSharedData("TAG" + i + "bm", i + "");

                    if (webSite.isEmpty() || webSite.equals("")) {
                        pref.setSharedData("WEB" + i + "bm", "");
                    } else {
                        pref.setSharedData("WEB" + i + "bm", webSite);
                    }

                    MainMe_MyFeed_Bookmark_Set getData = new MainMe_MyFeed_Bookmark_Set(
                            shopThumbnail,
                            checkinCount,
                            webSite,
                            call,
                            shopTime,
                            shopName,
                            regdate,
                            address
                    );

                    data.add(getData);
                }
            }
            feedAdapter.notifyDataSetChanged();
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        } catch (
                ExecutionException e) {
            e.printStackTrace();
        } catch (
                TimeoutException e) {
            e.printStackTrace();
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

    }


}
