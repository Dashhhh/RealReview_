package com.teamnova.ej.realreview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamnova.ej.realreview.Asynctask.AsyncShopDetailImageURLRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_Adapter_Backup;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_Review_LV_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_Review_LV_Set;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Main extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {


    android.support.v4.view.ViewPager shopDetailViewPager;
    TextView shopDetailTitle, shopDetailTitleReviewCnt, shopDetailTimeOpen, shopDetailTimeClose, shopDetailQuestion_Question, shopDetailQuestion_Answer;
    android.support.v7.widget.AppCompatRatingBar shopDetailTitleRating, shopDetailRatingReview, shopDetailRatingReview2;
    android.support.v7.widget.RecyclerView shopDetailRVTitleTag, shopDetailRVImage;
    Button shopDetailTopAddPhoto, shopDetailCheckin, shopDetailBookmark, mapAddress, shopDetailCallBtn, shopDetailDirection, shopDetailMenu, shopDetailWebsiteBtn, shopDetailMessageBtn;
    LinearLayout shopDetailProfile, shopDetailProfile2, shopDetailProfile3, viewpagePageMark;
    SupportMapFragment mapFragmentDetail;
    android.support.v7.widget.AppCompatEditText shopDetailQuestion, shopDetailReview;
    ListView shopDetailLVReview;

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.mapFragmentDetail);

    String getTag = "";
    ArrayList<String> stringArrayList = new ArrayList<>();
    ShopDetail_Main_Adapter_Backup viewpagerAdapter;
    LinearLayout ll;
    private final int MAX = 10;
    private int mPrevPosition;
    private int pagerCount;
    /**
     * VIEW PAGER TEST URL
     */
    String u1 = "http://222.122.203.55/realreview/hard/test6.jpg";
    String u2 = "http://222.122.203.55/realreview/hard/test7.jpg";
    String u3 = "http://222.122.203.55/realreview/hard/test8.jpg";
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private String title;


    String a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17;
    private ArrayList<String> shopImageIdList = new ArrayList<>();
    private JSONObject item2;
    private GoogleMap mMap;


    ArrayList<ShopDetail_Main_Review_LV_Set> reviewData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__main);


        init();
        listener();
        checkShopData();
        setShopData();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragmentDetail);
        mapFragment.getMapAsync(this);




        /*
        mPrevPosition = 0;    //���� ������ �� �ʱ�ȭ
        addPageMark();        //���� ������ ǥ���ϴ� �� �߰�
        viewpagePageMark.getChildAt(mPrevPosition).setBackgroundResource(R.drawable.page_not);    //���� �������� �ش��ϴ� ������ ǥ�� �̹��� ����
        */

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapting();


    }

    private void adapting() {


        StringBuilder conn = null;
        ProgressDialog progressDialog = new ProgressDialog(this);


        String urlParse = "http://222.122.203.55/realreview/shopimage/viewpagerImageResponse.php?id=" + a0;

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        viewpagerAdapter = new ShopDetail_Main_Adapter_Backup(this);
        shopDetailViewPager.setAdapter(viewpagerAdapter);

        ShopDetail_Main_Review_LV_Adapter reviewLvAdapter = new ShopDetail_Main_Review_LV_Adapter(this);
        shopDetailLVReview.setAdapter(reviewLvAdapter);

        try {
            ShopDetail_Main_Review_LV_Set dataSet = new ShopDetail_Main_Review_LV_Set();
            conn = new AsyncShopDetailImageURLRequest(urlParse, progressDialog, this).execute().get(10000, TimeUnit.MILLISECONDS);
            String sConn = String.valueOf(conn);
            Log.d("REVIEW_VIEWPAGER_URL", "sConn :" + sConn);


            JSONObject castingJO = new JSONObject(String.valueOf(sConn));
            JSONObject cc = new JSONObject();
            Log.d("REVIEW_VIEWPAGER_URL", "1 - castingJO :" + castingJO);
            JSONArray fixJSON = castingJO.getJSONArray("realreview");   // viewPager
            Log.d("REVIEW_VIEWPAGER_URL", "2 - fixJSON :" + fixJSON);
//            JSONArray fixJSON2 = castingJO.getJSONArray("imagepath");   // viewPager
//            Log.d("REVIEW_VIEWPAGER_URL", "2 - fixJSON :" + fixJSON2);

            for (int i = 0; i < fixJSON.length(); i++) {
                JSONObject item = fixJSON.getJSONObject(i);
                Log.d("REVIEW_VIEWPAGER_URL", "3 - item :" + i + "번 :" + item);
//                item2 = new JSONObject(shopImageIdList.get(i));
//                Log.d("REVIEW_VIEWPAGER_URL", "4 - item2 :" + i + "번 :" + item2);
                String getViewpagerURL = item.getString("imagepath");
                Log.d("REVIEW_VIEWPAGER_URL", "5 - getViewpagerURL :" + i + "번 :" + getViewpagerURL);
                viewpagerAdapter.addItem(getViewpagerURL);
            }


/*

            class DataJson {

                @SerializedName("name")
                public String name;

                @SerializedName("data")
                public Data data;

                class Data{
                    @SerializedName("name")
                    public String name;

                    @SerializedName("age")
                    public int age;

                    @SerializedName("birth")
                    public int birth;
                }

                @SerializedName("info")
                public List<Info> info;

                class Info{
                    @SerializedName("review")
                    public String review;

                    @SerializedName("regDate")
                    public int regDate;

                    @SerializedName("id_user")
                    public int id_user;

                    @SerializedName("rating")
                    public int rating;

//                    @SerializedName("profileimage")
//                    public int profileimage;
                }

            }
            DataJson dataJson= new Gson().fromJson(sConn, DataJson.class);

            for(DataJson.Info info : dataJson.info){

                Log.d("REIVEW_REVIEW", "info.review :"+info.review);
                Log.d("REIVEW_REVIEW", "info.regDate :"+info.regDate);
                Log.d("REIVEW_REVIEW", "info.id_user :"+info.id_user);
                Log.d("REIVEW_REVIEW", "info.rating :"+info.rating);
//                Log.d("REIVEW_REVIEW", "info.profileimage :"+info.profileimage);

            }

*/

            JSONObject reviewJSON = new JSONObject(sConn);
            JSONArray reviewJSONArray = reviewJSON.getJSONArray("info");


/*
            JsonParser parser = new JsonParser();
            JsonObject reviewJSON = new parser.parse(String.valueOf(conn)).getAsJsonObject();
            Log.d("REVIEW_REVIEW","reviewJSON :"+reviewJSON);
            JsonArray jsonArray = reviewJSON.getAsJsonArray("info");
            Log.d("REVIEW_REVIEW","jsonArray :"+jsonArray);
            JsonObject jsonObject1 = jsonArray.get(0).getAsJsonObject();
            Log.d("REVIEW_REVIEW","jsonObject :"+jsonArray);*/
            for (int i = 0; i < reviewJSONArray.length(); i++) {
//                JSONObject infoItem = reviewJSONArray.getJSONObject(i);
                JSONObject jsonObject1 = reviewJSONArray.getJSONObject(i);
                Log.d("REVIEW_REVIEW", "jsonObject1 :" + jsonObject1);
                String getReviewText = jsonObject1.getString("review");
                Log.d("REIVEW_REVIEW", "Casting Info - review :" + i + "번 :" + getReviewText);
                String getRegdate = jsonObject1.getString("regdate");
                Log.d("REIVEW_REVIEW", "Casting Info - getRegdate :" + i + "번 :" + getRegdate);
                String getUserId = jsonObject1.getString("id_user");
                Log.d("REIVEW_REVIEW", "Casting Info - getUserId :" + i + "번 :" + getUserId);
                String getRating = jsonObject1.getString("rating");
                Log.d("REIVEW_REVIEW", "Casting Info - getRating :" + i + "번 :" + getRating);
                String getProfileImageURL = jsonObject1.getString("profileimage");
                Log.d("REIVEW_REVIEW", "Casting Info - getProfileImageURL :" + i + "번 :" + getProfileImageURL);

                dataSet.reviewCnt = "0";
                dataSet.followerCnt = "0";
                dataSet.imageCnt = "0";
                dataSet.reviewText = getReviewText;
                dataSet.regdate = getRegdate;
                dataSet.userId = getUserId;
                dataSet.rating = getRating;
                float ff = Float.parseFloat(getRating);
                dataSet.fRating = ff;
                dataSet.titleImage = getProfileImageURL;
                reviewData.add(0, dataSet);
                reviewLvAdapter.addItem(reviewData);
                reviewLvAdapter.notifyDataSetChanged();
            }

            Log.d("Main_Test, onMapReady", "connLength : " + fixJSON.length());


        } catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
            e.printStackTrace();

        }





/*
        viewpagerAdapter.addItem("http://222.122.203.55/realreview/hard/test6.jpg");
        viewpagerAdapter.addItem("http://222.122.203.55/realreview/hard/test6.jpg");
        viewpagerAdapter.addItem("http://222.122.203.55/realreview/hard/test6.jpg");
*/


        pref.setSharedData("VIEWPAGER_TEST1", u1);
        pref.setSharedData("VIEWPAGER_TEST2", u2);
        pref.setSharedData("VIEWPAGER_TEST3", u3);


        viewpagerAdapter.notifyDataSetChanged();
        pagerCount = viewpagerAdapter.getCount();
        shopDetailViewPager.setCurrentItem(0, true);
        shopDetailViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void addPageMark() {
        ImageView iv = new ImageView(getApplicationContext());    //������ ǥ�� �̹��� �� ����
//        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        iv.setBackgroundResource(R.drawable.page_not);
        Log.d("ViewPager", "addPageMark() - pagerCount" + pagerCount);
        for (int i = 0; i < pagerCount; i++) {
            viewpagePageMark.addView(iv);//LinearLayout�� �߰�
        }
    }

    private void setShopData() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        shopDetailTitle.setText(pref.getSharedData("TITLE" + getTag));
        shopDetailTimeOpen.setText(pref.getSharedData("OPEN" + getTag));
        shopDetailTimeClose.setText(pref.getSharedData("CLOSE" + getTag));


    }

    private void checkShopData() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        Intent intent = getIntent();
        String tagCheck = intent.getExtras().getString("TAG");
        getTag = tagCheck;
        a0 = pref.getSharedData("ID" + tagCheck);
        a1 = pref.getSharedData("TITLE" + tagCheck);
        a2 = pref.getSharedData("ADDRESS" + tagCheck);
        a3 = pref.getSharedData("LAT" + tagCheck);
        a4 = pref.getSharedData("LNG" + tagCheck);
        a5 = pref.getSharedData("V_SW_LAT" + tagCheck);
        a6 = pref.getSharedData("V_SW_LNG" + tagCheck);
        a7 = pref.getSharedData("V_NE_LAT" + tagCheck);
        a8 = pref.getSharedData("V_NE_LNG" + tagCheck);
        a9 = pref.getSharedData("OPEN" + tagCheck);
        a10 = pref.getSharedData("CLOSE" + tagCheck);
        a11 = pref.getSharedData("THEME1" + tagCheck);
        a12 = pref.getSharedData("THEME2" + tagCheck);
        a13 = pref.getSharedData("THEME3" + tagCheck);
        a14 = pref.getSharedData("THEME4" + tagCheck);
        a15 = pref.getSharedData("THEME5" + tagCheck);
        a16 = pref.getSharedData("CALL" + tagCheck);
        a17 = pref.getSharedData("WEB" + tagCheck);

        title = a1;
        mapAddress.setText(a2);


        Log.d("MARKER_TAG", "gIntent[TAG] :" + tagCheck);
        Log.d("MARKER_TAG", "ID :" + a0);
        Log.d("MARKER_TAG", "TITLE :" + a1);
        Log.d("MARKER_TAG", "ADDRESS :" + a2);
        Log.d("MARKER_TAG", "LAT :" + a3);
        Log.d("MARKER_TAG", "LNG :" + a4);
        Log.d("MARKER_TAG", "V_SW_LAT :" + a5);
        Log.d("MARKER_TAG", "V_SW_LNG :" + a6);
        Log.d("MARKER_TAG", "V_NE_LAT :" + a7);
        Log.d("MARKER_TAG", "V_NE_LNG :" + a8);
        Log.d("MARKER_TAG", "OPEN :" + a9);
        Log.d("MARKER_TAG", "CLOSE :" + a10);
        Log.d("MARKER_TAG", "THEME1 :" + a11);
        Log.d("MARKER_TAG", "THEME2 :" + a12);
        Log.d("MARKER_TAG", "THEME3 :" + a13);
        Log.d("MARKER_TAG", "THEME4 :" + a14);
        Log.d("MARKER_TAG", "THEME5 :" + a15);
        Log.d("MARKER_TAG", "CALL :" + a16);
        Log.d("MARKER_TAG", "WEB :" + a17);

    }

    private void init() {

        shopDetailViewPager = (ViewPager) findViewById(R.id.shopDetailViewPager);
        shopDetailTitle = (TextView) findViewById(R.id.shopDetailTitle);
        shopDetailTitleReviewCnt = (TextView) findViewById(R.id.shopDetailTitleReviewCnt);
        shopDetailTimeOpen = (TextView) findViewById(R.id.shopDetailTimeOpen);
        shopDetailTimeClose = (TextView) findViewById(R.id.shopDetailTimeClose);
        shopDetailQuestion_Question = (TextView) findViewById(R.id.shopDetailQuestion_Question);
        shopDetailQuestion_Answer = (TextView) findViewById(R.id.shopDetailQuestion_Answer);
        shopDetailTitleRating = (AppCompatRatingBar) findViewById(R.id.shopDetailTitleRating);
        shopDetailRatingReview = (AppCompatRatingBar) findViewById(R.id.shopDetailRatingReview);
        shopDetailRatingReview2 = (AppCompatRatingBar) findViewById(R.id.shopDetailRatingReview2);
        shopDetailRVTitleTag = (RecyclerView) findViewById(R.id.shopDetailRVTitleTag);
        shopDetailRVImage = (RecyclerView) findViewById(R.id.shopDetailRVImage);
        shopDetailTopAddPhoto = (Button) findViewById(R.id.shopDetailTopAddPhoto);
        shopDetailCheckin = (Button) findViewById(R.id.shopDetailCheckin);
        shopDetailBookmark = (Button) findViewById(R.id.shopDetailBookmark);
        mapAddress = (Button) findViewById(R.id.mapAddress);
        shopDetailCallBtn = (Button) findViewById(R.id.shopDetailCallBtn);
        shopDetailDirection = (Button) findViewById(R.id.shopDetailDirection);
        shopDetailMenu = (Button) findViewById(R.id.shopDetailMenu);
        shopDetailWebsiteBtn = (Button) findViewById(R.id.shopDetailWebsiteBtn);
        shopDetailMessageBtn = (Button) findViewById(R.id.shopDetailMessageBtn);
        shopDetailProfile = (LinearLayout) findViewById(R.id.shopDetailProfile);
        shopDetailProfile2 = (LinearLayout) findViewById(R.id.shopDetailProfile2);
        shopDetailProfile3 = (LinearLayout) findViewById(R.id.shopDetailProfile3);
        viewpagePageMark = (LinearLayout) findViewById(R.id.viewpagePageMark);
        shopDetailQuestion = (AppCompatEditText) findViewById(R.id.shopDetailQuestion);
        shopDetailReview = (AppCompatEditText) findViewById(R.id.shopDetailReview);
        shopDetailLVReview = (ListView) findViewById(R.id.shopDetailLVReview);

    }

    private void listener() {

        shopDetailViewPager.setOnClickListener(this);
        shopDetailTitle.setOnClickListener(this);
        shopDetailTitleReviewCnt.setOnClickListener(this);
        shopDetailTimeOpen.setOnClickListener(this);
        shopDetailTimeClose.setOnClickListener(this);
        shopDetailQuestion_Question.setOnClickListener(this);
        shopDetailQuestion_Answer.setOnClickListener(this);
        /*  Title Rating Bar -> Only used for Display
        shopDetailTitleRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {


            }
        });
        */
        shopDetailRatingReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {


                SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopDetail_Main.this);
                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Review_Submit.class);
                intent.putExtra("reviewRating", v);
                intent.putExtra("reviewTitle", title);
                intent.putExtra("reviewShopId", a0);
                intent.putExtra("reviewUserId", pref.getSharedData("isLogged_id"));
                intent.putExtra("reviewUserNick", pref.getSharedData("isLogged_nick"));
                startActivity(intent);


            }
        });
        shopDetailRatingReview2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopDetail_Main.this);
                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Review_Submit.class);
                intent.putExtra("reviewRating", v);
                intent.putExtra("reviewTitle", title);
                intent.putExtra("reviewShopId", a0);
                intent.putExtra("reviewUserId", pref.getSharedData("isLogged_id"));
                intent.putExtra("reviewUserNick", pref.getSharedData("isLogged_nick"));
                startActivity(intent);

            }
        });
        shopDetailRVTitleTag.setOnClickListener(this);
        shopDetailRVImage.setOnClickListener(this);
        shopDetailTopAddPhoto.setOnClickListener(this);
        shopDetailCheckin.setOnClickListener(this);
        shopDetailBookmark.setOnClickListener(this);
        mapAddress.setOnClickListener(this);
        shopDetailCallBtn.setOnClickListener(this);
        shopDetailDirection.setOnClickListener(this);
        shopDetailMenu.setOnClickListener(this);
        shopDetailWebsiteBtn.setOnClickListener(this);
        shopDetailMessageBtn.setOnClickListener(this);
        shopDetailProfile.setOnClickListener(this);
        shopDetailProfile2.setOnClickListener(this);
        shopDetailProfile3.setOnClickListener(this);
        shopDetailQuestion.setOnClickListener(this);
        shopDetailReview.setOnClickListener(this);
//        shopDetailLVReview.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.shopDetailViewPager: {


                break;
            }

            case R.id.shopDetailTitle: {


                break;
            }

            case R.id.shopDetailTitleReviewCnt: {


                break;
            }

            case R.id.shopDetailTimeOpen: {


                break;
            }

            case R.id.shopDetailTimeClose: {


                break;
            }

            case R.id.shopDetailQuestion_Question: {


                break;
            }

            case R.id.shopDetailQuestion_Answer: {


                break;
            }

            case R.id.shopDetailTitleRating: {

                break;
            }

            case R.id.shopDetailRatingReview: {

                break;
            }

            case R.id.shopDetailRatingReview2: {

                break;
            }

            case R.id.shopDetailRVTitleTag: {

                break;
            }

            case R.id.shopDetailRVImage: {

                break;
            }

            case R.id.shopDetailTopAddPhoto: {

                break;
            }

            case R.id.shopDetailCheckin: {

                break;
            }

            case R.id.shopDetailBookmark: {

                break;
            }

            case R.id.mapAddress: {

                break;
            }

            case R.id.shopDetailCallBtn: {

                break;
            }

            case R.id.shopDetailDirection: {

                break;
            }

            case R.id.shopDetailMenu: {

                break;
            }

            case R.id.shopDetailWebsiteBtn: {

                break;
            }
            case R.id.shopDetailMessageBtn: {

                break;
            }
            case R.id.shopDetailProfile: {

                break;
            }
            case R.id.shopDetailProfile2: {

                break;
            }
            case R.id.shopDetailQuestion: {

                break;
            }

            case R.id.shopDetailReview: {

                SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopDetail_Main.this);
                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Review_Submit.class);
                intent.putExtra("reviewTitle", title);
                intent.putExtra("reviewShopId", a0);
                intent.putExtra("reviewUserId", pref.getSharedData("isLogged_id"));
                intent.putExtra("reviewUserNick", pref.getSharedData("isLogged_nick"));
                startActivity(intent);

                break;
            }


        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        Log.d("DetailMain", "onMapReady = Enter");
        mMap = googleMap;

        Double lat = Double.valueOf(a3);
        Double lng = Double.valueOf(a4);
        Log.d("DetailMain", "LAT" + a3);
        Log.d("DetailMain", "LNG" + a4);
        LatLng latlng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng)
                .title(a1);
        Marker marker = mMap.addMarker(markerOptions);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
    }
}
