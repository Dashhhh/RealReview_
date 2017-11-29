package com.teamnova.ej.realreview.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;
import com.teamnova.ej.realreview.Asynctask.AsyncShopDetailImageURLRequest;
import com.teamnova.ej.realreview.Asynctask.AsyncShopPhotoSubmit;
import com.teamnova.ej.realreview.Asynctask.AsyncTipRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopDetail_MainReview_RV_Theme_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_MainReview_RV_Theme_Set;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_Adapter_Backup;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_Photo_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_Photo_Set;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_Tip_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_Tip_Set;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_Review_LV_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_Review_LV_Set;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;
import com.teamnova.ej.realreview.util.ValidateUtil;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ShopDetail_Main extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {


    android.support.v4.view.ViewPager shopDetailViewPager;
    TextView shopDetailTitle, shopDetailTitleReviewCnt, shopDetailTimeOpen, shopDetailTimeClose, shopDetailQuestion_Question, shopDetailQuestion_Answer, shopDetailQuestionImageCount, shopDetailQuestionReviewCount, shopDetailQuestionFollower, shopDetailQuestionNick;
    TextView shopDetailReviewAddImageCount, shopDetailReviewAddReviewCount, shopDetailReviewAddFollower, shopDetailReviewAddUserID;
    TextView shopDetailUserFollowerCount, shopDetailUserReviewCount, shopDetailTipuserImageCount, shopDetailTipUserNick;
    ImageView shopDetailTipUserImage;
    android.support.v7.widget.AppCompatRatingBar shopDetailTitleRating, shopDetailRatingReview, shopDetailRatingReview2;
    android.support.v7.widget.RecyclerView shopDetailRVTitleTag, shopDetailRVImage, shopDetailTipRV;
    Button shopDetailTopAddPhoto, shopDetailCheckin, shopDetailBookmark, mapAddress, shopDetailCallBtn, shopDetailDirection, shopDetailMenu, shopDetailWebsiteBtn, shopDetailMessageBtn;
    LinearLayout shopDetailProfile, shopDetailProfile2, shopDetailProfile3, shopDetailTipProfileLayout, shopDetailQuestionRoot, shopDetailQuestionAllRoot;
    SupportMapFragment mapFragmentDetail;
    android.support.v7.widget.AppCompatEditText shopDetailQuestion, shopDetailReview, shopDetailTip;
    ListView shopDetailLVReview;
    me.relex.circleindicator.CircleIndicator shopDetailIndicator;
    ImageView shopDetailQuestionUserImage, shopDetailAddReviewUserProfile, shopDetailReviewAddUserImage;


    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.mapFragmentDetail);

    String getTag = "";
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


    String defaultShopID, defaultTitle, defaultAddress, defaultLat, defaultLng, defaultVSWLat, defaultVSWLng, defaultVNELat, defaultVNELng, defaultTimeOpen, defaultTimeClose, defaultTheme1, defaultTheme2, defaultTheme3, defaultTheme4, defaultTheme5, defaultCall, defaultWeb;
    private ArrayList<String> shopImageIdList = new ArrayList<>();
    private JSONObject item2;
    private GoogleMap mMap;


    ArrayList<ShopDetail_Main_Review_LV_Set> reviewData = new ArrayList<>();
    ShopDetail_Main_Review_LV_Set dataSet;
    ArrayList<ShopDetail_Main_Review_LV_Set> dataSet_addList = new ArrayList<>();

    float titleRatingPoint;
    int titleRatingPersonCount;
    String defaulUserId;
    private ArrayList<ShopDetail_Main_RV_Photo_Set> imageRVList = new ArrayList<>();
    Button reviewPagingBtn;
    private ArrayList<ShopDetail_MainReview_RV_Theme_Set> themeList = new ArrayList<>();
    private ArrayList<ShopDetail_Main_RV_Tip_Set> tipList = new ArrayList<>();
    private ArrayList<Uri> topPhotoUriList;
    private int reqCode = 1;
    com.beardedhen.androidbootstrap.BootstrapButton shopDetailQuestionAllBtn;
    public static String TITLE;
    public static String SHOP_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__main);


        init();
        listener();
        checkShopData();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragmentDetail);
        mapFragment.getMapAsync(this);


    }

    private void init() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

        shopDetailViewPager = findViewById(R.id.shopDetailViewPager);
        shopDetailTitle = findViewById(R.id.shopDetailTitle);
        shopDetailTitleReviewCnt = findViewById(R.id.shopDetailTitleReviewCnt);
        shopDetailTimeOpen = findViewById(R.id.shopDetailTimeOpen);
        shopDetailTimeClose = findViewById(R.id.shopDetailTimeClose);
        shopDetailQuestion_Question = findViewById(R.id.shopDetailQuestion_Question);
        shopDetailQuestion_Answer = findViewById(R.id.shopDetailQuestion_Answer);
        shopDetailTitleRating = findViewById(R.id.shopDetailTitleRating);
        shopDetailRatingReview = findViewById(R.id.shopDetailRatingReview);
        shopDetailRatingReview2 = findViewById(R.id.shopDetailRatingReview2);
        shopDetailRVTitleTag = findViewById(R.id.shopDetailRVTitleTag);
        shopDetailRVImage = findViewById(R.id.shopDetailRVImage);
        shopDetailTopAddPhoto = findViewById(R.id.shopDetailTopAddPhoto);
        shopDetailCheckin = findViewById(R.id.shopDetailCheckin);
        shopDetailBookmark = findViewById(R.id.shopDetailBookmark);
        mapAddress = findViewById(R.id.mapAddress);
        shopDetailCallBtn = findViewById(R.id.shopDetailCallBtn);
        shopDetailDirection = findViewById(R.id.shopDetailDirection);
        shopDetailMenu = findViewById(R.id.shopDetailMenu);
        shopDetailWebsiteBtn = findViewById(R.id.shopDetailWebsiteBtn);
        shopDetailMessageBtn = findViewById(R.id.shopDetailMessageBtn);
        shopDetailProfile = findViewById(R.id.shopDetailProfile);
        shopDetailProfile2 = findViewById(R.id.shopDetailProfile2);
//        shopDetailProfile3 = (LinearLayout) findViewById(R.id.shopDetailProfile3);
        shopDetailQuestion = findViewById(R.id.shopDetailQuestion);
        shopDetailReview = findViewById(R.id.shopDetailReview);
        shopDetailLVReview = findViewById(R.id.shopDetailLVReview);
        reviewPagingBtn = findViewById(R.id.reviewPagingBtn);
//        reviewPagingBtn.setVisibility(View.GONE);
        shopDetailTip = findViewById(R.id.shopDetailTip);
        shopDetailTipProfileLayout = findViewById(R.id.shopDetailTipRVLayout);
        shopDetailTipRV = findViewById(R.id.shopDetailTipRV);
        shopDetailIndicator = findViewById(R.id.shopDetailIndicator);
        shopDetailQuestionRoot = findViewById(R.id.shopDetailQuestionRoot);
        shopDetailQuestionUserImage = findViewById(R.id.shopDetailQuestionUserImage);
        shopDetailQuestionNick = findViewById(R.id.shopDetailQuestionUserID);
        shopDetailQuestionImageCount = findViewById(R.id.shopDetailQuestionImageCount);
        shopDetailQuestionReviewCount = findViewById(R.id.shopDetailQuestionReviewCount);
        shopDetailQuestionFollower = findViewById(R.id.shopDetailQuestionFollower);
        shopDetailTipUserImage = findViewById(R.id.shopDetailTipUserImage);
        shopDetailUserFollowerCount = findViewById(R.id.shopDetailUserFollowerCount);
        shopDetailUserReviewCount = findViewById(R.id.shopDetailUserReviewCount);
        shopDetailTipuserImageCount = findViewById(R.id.shopDetailTipuserImageCount);
        shopDetailTipUserNick = findViewById(R.id.shopDetailTipUserNick);

        /**
         * Tip User Layout Setting
         */


        Glide.with(this).load(pref.getSharedData("isLogged_profileImagePath")).thumbnail(0.5f)
                .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                .into(shopDetailTipUserImage);

        shopDetailTipUserNick.setText(pref.getSharedData("isLogged_nick"));
        shopDetailTipuserImageCount.setText(pref.getSharedData("isLogged_imageCnt"));
        shopDetailUserReviewCount.setText(pref.getSharedData("isLogged_reviewCnt"));
        shopDetailUserFollowerCount.setText(pref.getSharedData("isLogged_followerCnt"));


        /**
         * Add Question Layout Setting
         */

        Glide.with(this).load(pref.getSharedData("isLogged_profileImagePath")).thumbnail(0.5f)
                .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                .into(shopDetailQuestionUserImage);

        shopDetailQuestionNick.setText(pref.getSharedData("isLogged_nick"));
        shopDetailQuestionImageCount.setText(pref.getSharedData("isLogged_imageCnt"));
        shopDetailQuestionReviewCount.setText(pref.getSharedData("isLogged_reviewCnt"));
        shopDetailQuestionFollower.setText(pref.getSharedData("isLogged_followerCnt"));


        /**
         *  Add Review Layout Setting
         */


        shopDetailReviewAddUserImage = findViewById(R.id.shopDetailReviewAddUserImage);

        shopDetailReviewAddImageCount = findViewById(R.id.shopDetailReviewAddImageCount);
        shopDetailReviewAddReviewCount = findViewById(R.id.shopDetailReviewAddReviewCount);
        shopDetailReviewAddFollower = findViewById(R.id.shopDetailReviewAddFollower);
        shopDetailReviewAddUserID = findViewById(R.id.shopDetailReviewAddUserID);

        shopDetailReviewAddUserID.setText(pref.getSharedData("isLogged_nick"));
        shopDetailReviewAddImageCount.setText(pref.getSharedData("isLogged_imageCnt"));
        shopDetailReviewAddReviewCount.setText(pref.getSharedData("isLogged_reviewCnt"));
        shopDetailReviewAddFollower.setText(pref.getSharedData("isLogged_followerCnt"));

        Glide.with(this).load(pref.getSharedData("isLogged_profileImagePath")).thumbnail(0.5f)
                .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                .into(shopDetailReviewAddUserImage);

        /**
         * Question & Answer Layout GONE !!
         * If you want this Contents Visibility, set The 'Question & Answer'
         */
        shopDetailQuestionRoot.setVisibility(View.GONE);
        shopDetailQuestionAllRoot = findViewById(R.id.shopDetailQuestionAllRoot);
        shopDetailQuestionAllBtn = findViewById(R.id.shopDetailQuestionAllBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void setShopData() {

        titleRatingPoint = titleRatingPoint / titleRatingPersonCount;
        shopDetailTitleRating.setRating(titleRatingPoint);
        shopDetailTitleReviewCnt.setText(String.valueOf(titleRatingPersonCount));
        titleRatingPoint = 0;
        titleRatingPersonCount = 0;

    }

    private void adapting() throws InterruptedException, ExecutionException, TimeoutException, JSONException {


        StringBuilder conn = null;
        ProgressWheel progressDialog = new ProgressWheel(this);


        String urlParse = "http://222.122.203.55/realreview/shopimage/viewpagerImageResponse.php?id=" + defaultShopID + "&userid=" + defaulUserId;

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        viewpagerAdapter = new ShopDetail_Main_Adapter_Backup(this);
        shopDetailViewPager.setAdapter(viewpagerAdapter);
        shopDetailViewPager.setPageTransformer(true, new ParallaxPagerTransformer(R.id.shopDetailViewPagerImage));
        shopDetailIndicator.setViewPager(shopDetailViewPager);
        viewpagerAdapter.registerDataSetObserver(shopDetailIndicator.getDataSetObserver());
        ShopDetail_Main_Review_LV_Adapter reviewLvAdapter = new ShopDetail_Main_Review_LV_Adapter(this, reviewData);
        shopDetailLVReview.setAdapter(reviewLvAdapter);
        reviewLvAdapter.clearItem();


        ShopDetail_Main_RV_Photo_Adapter imageRVAdater = new ShopDetail_Main_RV_Photo_Adapter(this, imageRVList);
        imageRVAdater.clearItem();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        shopDetailRVImage.setHasFixedSize(true);

        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        shopDetailRVImage.setLayoutManager(mStaggeredGridLayoutManager);

        //

        try {
            dataSet = new ShopDetail_Main_Review_LV_Set();
            conn = new AsyncShopDetailImageURLRequest(urlParse, progressDialog).execute().get(10000, TimeUnit.MILLISECONDS);
            String sConn = String.valueOf(conn);
            Log.d("REVIEW_VIEWPAGER_URL", "sConn :" + sConn);

            JSONObject castingJO = new JSONObject(String.valueOf(sConn));
            JSONObject cc = new JSONObject();
            Log.d("REVIEW_VIEWPAGER_URL", "1 - castingJO :" + castingJO);
            JSONArray fixJSON = castingJO.getJSONArray("realreview");   // viewPager
            Log.d("REVIEW_VIEWPAGER_URL", "2 - fixJSON :" + fixJSON);

            JSONObject reviewJSON = new JSONObject(sConn);
            JSONArray reviewJSONArray = reviewJSON.getJSONArray("info");

            if (fixJSON.length() == 0) {

                viewpagerAdapter.addItem("http://222.122.203.55/realreview/shopimage/upload/default_viewpager.png","","");
                imageRVAdater.addItem("http://222.122.203.55/realreview/shopimage/upload/default_viewpager.png");

            }


            ArrayList<String> vpTitle = new ArrayList<>();
            ArrayList<String> vpDetail = new ArrayList<>();
            ArrayList<String> vpImageURL = new ArrayList<>();
            String idx = "0";
            for (int i = 0; i < fixJSON.length(); i++) {


                JSONObject viewpagerText = fixJSON.getJSONObject(i);
                String viewpagerTitle = viewpagerText.getString("nick");
                String viewpagerDetail = viewpagerText.getString("review");
                String viewpagerImageUrl = viewpagerText.getString("imagepath");
                String viewpagerIdx = viewpagerText.getString("idx");
//                idx = viewpagerIdx;


                if(idx.equals(viewpagerIdx)) {
                    Log.d("REVIEW_VIEWPAGER_URL2", "PASS - idx : " +idx + ", viewpagerIdx : "  + viewpagerIdx);
                } else {
                    Log.d("REVIEW_VIEWPAGER_URL2", "viewpagerTitle :" + i + "번 :" + viewpagerTitle);
                    Log.d("REVIEW_VIEWPAGER_URL2", "viewpagerDetail :" + i + "번 :" + viewpagerDetail);
                    Log.d("REVIEW_VIEWPAGER_URL2", "viewpagerImageUrl :" + i + "번 :" + viewpagerImageUrl);
                    vpTitle.add(viewpagerTitle);
                    vpDetail.add(viewpagerDetail);
                    vpImageURL.add(viewpagerImageUrl);
                    idx = viewpagerIdx;
                }


            }

            for (int i = 0; i < fixJSON.length(); i++) {
                ShopDetail_Main_RV_Photo_Set rvSet = new ShopDetail_Main_RV_Photo_Set();
                JSONObject item = fixJSON.getJSONObject(i);

                String viewpagerImageUrl = item.getString("imagepath");
                Log.d("REVIEW_VIEWPAGER_URL", "viewpagerImageUrl :" + i + "번 :" + viewpagerImageUrl);

                if (i <= 5) {
                    vpImageURL.add(viewpagerImageUrl);
                }
                rvSet.imageUrl = viewpagerImageUrl;
                imageRVList.add(rvSet);
            }
            Log.d("REVIEW_VIEWPAGER_URL", "vpTitle - SIZE :" + vpTitle.size());
            Log.d("REVIEW_VIEWPAGER_URL", "vpDetail - SIZE :" +   vpDetail.size() );
            Log.d("REVIEW_VIEWPAGER_URL", "vpImageURL - SIZE :" + vpImageURL.size() );

            for(int i = 0; i < vpTitle.size(); i++) {
                if(i <= 5) {
                    if(vpTitle.size() > 0 && vpDetail.size() > 0){
                        if(!vpTitle.equals("") ||!vpDetail.equals("") && i <= vpTitle.size())
                        viewpagerAdapter.addItem(vpImageURL.get(i), vpTitle.get(i), vpDetail.get(i));
                    } else {
//                        viewpagerAdapter.addItem(vpImageURL.get(i), "", "");
                    }
                } else {
                    break;
                }
            }
            imageRVAdater.notifyDataSetChanged();
            shopDetailRVImage.setAdapter(imageRVAdater);


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


/*
            JsonParser parser = new JsonParser();
            JsonObject reviewJSON = new parser.parse(String.valueOf(conn)).getAsJsonObject();
            Log.d("REVIEW_REVIEW","reviewJSON :"+reviewJSON);
            JsonArray jsonArray = reviewJSON.getAsJsonArray("info");
            Log.d("REVIEW_REVIEW","jsonArray :"+jsonArray);
            JsonObject jsonObject1 = jsonArray.get(0).getAsJsonObject();
            Log.d("REVIEW_REVIEW","jsonObject :"+jsonArray);

            */

            for (int i = 0; i < reviewJSONArray.length(); i++) {
                JSONObject jsonObject1 = reviewJSONArray.getJSONObject(i);
                Log.d("REVIEW_REVIEW", "jsonObject1 :" + jsonObject1);

                String getReviewIdx = jsonObject1.getString("idx");
                Log.d("REIVEW_REVIEW", "Casting Info - idx :" + i + "번 :" + getReviewIdx);

                String getReviewText = jsonObject1.getString("review");
                Log.d("REIVEW_REVIEW", "Casting Info - review :" + i + "번 :" + getReviewText);

                String getRegdate = jsonObject1.getString("regdate");
                Log.d("REIVEW_REVIEW", "Casting Info - getRegdate :" + i + "번 :" + getRegdate);

                String getUserId = jsonObject1.getString("id_user");
                Log.d("REIVEW_REVIEW", "Casting Info - getUserId :" + i + "번 :" + getUserId);

                String getNick = jsonObject1.getString("nick");
                Log.d("REIVEW_REVIEW", "Casting Info - nick :" + i + "번 :" + getNick);

                String getRating = jsonObject1.getString("rating");
                Log.d("REIVEW_REVIEW", "Casting Info - getRating :" + i + "번 :" + getRating);

                String getProfileImageURL = jsonObject1.getString("profileimage");
                Log.d("REIVEW_REVIEW", "Casting Info - getProfileImageURL :" + i + "번 :" + getProfileImageURL);

                String getLocality = jsonObject1.getString("locality");
                Log.d("REIVEW_REVIEW", "Casting Info - getLocality :" + i + "번 :" + getLocality);

                String getNearby = jsonObject1.getString("nearby");
                Log.d("REIVEW_REVIEW", "Casting Info - getNearby :" + i + "번 :" + getNearby);

                String getCountCool = jsonObject1.getString("countCool");
                Log.d("REIVEW_REVIEW", "Casting Info - getCountCool :" + i + "번 :" + getCountCool);

                String getCountGood = jsonObject1.getString("countGood");
                Log.d("REIVEW_REVIEW", "Casting Info - getCountGood :" + i + "번 :" + getCountGood);

                String getCountUseful = jsonObject1.getString("countUseful");
                Log.d("REIVEW_REVIEW", "Casting Info - getCountUseful :" + i + "번 :" + getCountUseful);

                boolean selectableUseful = jsonObject1.getBoolean("useful_selectable");
                Log.d("REIVEW_REVIEW", "Casting Info - useful_selectable :" + i + "번 :" + selectableUseful );

                boolean selectableGood =   jsonObject1.getBoolean("good_selectable");
                Log.d("REIVEW_REVIEW", "Casting Info - good_selectable :" + i + "번 :" + selectableGood );

                boolean selectableCool =  jsonObject1.getBoolean("cool_selectable");
                Log.d("REIVEW_REVIEW", "Casting Info - cool_selectable :" + i + "번 :" + selectableCool );

                String getCountFollower = jsonObject1.getString("follower_cnt");
                String getCountReview = jsonObject1.getString("review_cnt");
                String getCountImage = jsonObject1.getString("image_cnt");


                if (i <= 5) {
                    float ff = Float.parseFloat(getRating);
                    titleRatingPoint += ff;
                    if (reviewJSONArray.length() != 0) titleRatingPersonCount++;
/*
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
*/
//                dataSet_addList.add(dataSet);
                    reviewLvAdapter.addItem(getReviewIdx, getProfileImageURL, getCountFollower, getCountReview, getCountImage, getReviewText,
                            getRegdate, getUserId, getRating, getNick, ff, getProfileImageURL, getLocality, getNearby,
                            getCountCool, getCountGood, getCountUseful,
                            selectableUseful, selectableGood, selectableCool);
                    Log.d("REIVEW_REVIEW", "Casting Info - reviewData (Array List) :" + i + "번 :" + reviewData.get(i));
                }

            }
            if (reviewJSONArray.length() > 2) {

                reviewPagingBtn.setVisibility(View.VISIBLE);
                reviewPagingBtn.setText("리뷰(" + reviewJSONArray.length() + ") 전체 보기");
                reviewPagingBtn.setOnClickListener(this);
            }
            reviewLvAdapter.notifyDataSetChanged();
            ViewGroup.LayoutParams params = shopDetailLVReview.getLayoutParams();
//            setListViewHeightBasedOnItems(shopDetailLVReview);
            listViewHeightSet(reviewLvAdapter ,shopDetailLVReview);
//            reviewLvAdapter.addItem(reviewData);
            Log.d("Main, onMapReady", "connLength : " + fixJSON.length());


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

        themeList.clear();
        StaggeredGridLayoutManager themeLayoutSet = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        ShopDetail_MainReview_RV_Theme_Adapter shopDetailRVTitleAdapter = new ShopDetail_MainReview_RV_Theme_Adapter(this, themeList);
        shopDetailRVTitleTag.setHasFixedSize(true);
        shopDetailRVTitleTag.setLayoutManager(themeLayoutSet);
        shopDetailRVTitleTag.setAdapter(shopDetailRVTitleAdapter);


        pref.getSharedData("THEME1");
        pref.getSharedData("THEME2");
        pref.getSharedData("THEME3");
        pref.getSharedData("THEME4");
        pref.getSharedData("THEME5");
        pref.getSharedData("TAG");

        Log.d("DETAIL_THEME", "pref.getSharedData THEME1번 :" + pref.getSharedData("THEME1"));
        Log.d("DETAIL_THEME", "pref.getSharedData THEME2번 :" + pref.getSharedData("THEME2"));
        Log.d("DETAIL_THEME", "pref.getSharedData THEME3번 :" + pref.getSharedData("THEME3"));
        Log.d("DETAIL_THEME", "pref.getSharedData THEME4번 :" + pref.getSharedData("THEME4"));
        Log.d("DETAIL_THEME", "pref.getSharedData THEME5번 :" + pref.getSharedData("THEME5"));
        Log.d("DETAIL_THEME", "pref.getSharedData TAG :" + pref.getSharedData("TAG"));

        int markerTag = Integer.parseInt(pref.getSharedData("TAG"));
        for (int i = 1; i < 6; i++) {
            Log.d("DETAIL_THEME", "pref.getSharedData THEME" + i + "번 :" + pref.getSharedData("THEME" + i));
            if (!pref.getSharedData("THEME" + i + markerTag).equals("")) {
                if (!pref.getSharedData("THEME" + i + markerTag).equals("설립")) {
                    ShopDetail_MainReview_RV_Theme_Set themeSet = new ShopDetail_MainReview_RV_Theme_Set(pref.getSharedData("THEME" + i + markerTag));
                    Log.d("DETAIL_THEME", "in! pref.getSharedData THEME" + i + "번 :" + pref.getSharedData("THEME" + i + markerTag));
//                    themeSet.setsTheme(pref.getSharedData("THEME"+i+markerTag));
                    themeList.add(themeSet);
                    Log.d("DETAIL_THEME", "in! themeList" + i + "번 :" + themeList.get(i - 1));

                }
            } else {
                break;
            }


        }

        tipList.clear();
        StaggeredGridLayoutManager tipLayoutSet = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        ShopDetail_Main_RV_Tip_Adapter shopDetailRVTipAdapter = new ShopDetail_Main_RV_Tip_Adapter(this, tipList);
        shopDetailTipRV.setHasFixedSize(true);
        shopDetailTipRV.setLayoutManager(tipLayoutSet);
        shopDetailTipRV.setAdapter(shopDetailRVTipAdapter);

        String tipURL = "http://222.122.203.55/realreview/tip/tipRequest.php";
        JSONObject tipConn = null;
        tipConn = new AsyncTipRequest(tipURL, this).execute().get(10000, TimeUnit.MILLISECONDS);
        Log.d("TIP_ASYNC", "Async excute Complete - result JSONObject : " + tipConn);

        JSONArray jsonArray = tipConn.getJSONArray("tipresult");

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject getArray = jsonArray.getJSONObject(i);
            String shopid             = getArray.getString("shopid");
            String userid             = getArray.getString("userid");
            String tip                = getArray.getString("tip");
            String regdate            = getArray.getString("regdate");
            String nearby             = getArray.getString("nearby");
            String nick               = getArray.getString("nick");
            String locality           = getArray.getString("locality");
            String imagepath          = getArray.getString("imagepath");
            String getCountFollower   = getArray.getString("follower_cnt");
            String getCountReview     = getArray.getString("review_cnt");
            String getCountImage      = getArray.getString("image_cnt");


            ShopDetail_Main_RV_Tip_Set adapterSet = new ShopDetail_Main_RV_Tip_Set(imagepath, getCountFollower, getCountReview, getCountImage, tip, regdate, nick, nearby, locality, imagepath);
            Log.d("TIP_ASYNC", "JSON Parsing...shopid  : " + shopid);
            Log.d("TIP_ASYNC", "JSON Parsing...userid  : " + userid);
            Log.d("TIP_ASYNC", "JSON Parsing...tip     : " + tip);
            Log.d("TIP_ASYNC", "JSON Parsing...regdate : " + regdate);
            Log.d("TIP_ASYNC", "JSON Parsing...nearby  : " + nearby);
            Log.d("TIP_ASYNC", "JSON Parsing...nick    : " + nick);
            Log.d("TIP_ASYNC", "JSON Parsing...locality    : " + locality);
            Log.d("TIP_ASYNC", "JSON Parsing...imagepath    : " + imagepath);

            tipList.add(0, adapterSet);

        }

    }
    public void listViewHeightSet(BaseAdapter listAdapter, ListView listView){
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();

            Log.i("listViewHeightSet", "listItem.getMeasuredHeight() :" +listItem.getMeasuredHeight());
//            totalHeight *= 0.98;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount() - 1));
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
        Log.i("listViewHeightSet", "params.height :" +params.height);
        Log.i("listViewHeightSet", "listView.getDividerHeight() :" +listView.getDividerHeight());
    }

    public void setListViewHeightBasedOnItems(ListView listView) {

        // Get list adpter of listview;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int numberOfItems = listAdapter.getCount();

        // Get total height of all items.
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void defaulDataSet() {

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
        defaultShopID = pref.getSharedData("ID" + tagCheck);
        defaulUserId = pref.getSharedData("isLogged_id");
        defaultTitle = pref.getSharedData("TITLE" + tagCheck);
        defaultAddress = pref.getSharedData("ADDRESS" + tagCheck);
        defaultLat = pref.getSharedData("LAT" + tagCheck);
        defaultLng = pref.getSharedData("LNG" + tagCheck);
        defaultVSWLat = pref.getSharedData("V_SW_LAT" + tagCheck);
        defaultVSWLng = pref.getSharedData("V_SW_LNG" + tagCheck);
        defaultVNELat = pref.getSharedData("V_NE_LAT" + tagCheck);
        defaultVNELng = pref.getSharedData("V_NE_LNG" + tagCheck);
        defaultTimeOpen = pref.getSharedData("OPEN" + tagCheck);
        defaultTimeClose = pref.getSharedData("CLOSE" + tagCheck);
        defaultTheme1 = pref.getSharedData("THEME1" + tagCheck);
        defaultTheme2 = pref.getSharedData("THEME2" + tagCheck);
        defaultTheme3 = pref.getSharedData("THEME3" + tagCheck);
        defaultTheme4 = pref.getSharedData("THEME4" + tagCheck);
        defaultTheme5 = pref.getSharedData("THEME5" + tagCheck);
        defaultCall = pref.getSharedData("CALL" + tagCheck);
        defaultWeb = pref.getSharedData("WEB" + tagCheck);

        TITLE = defaultTitle;
        SHOP_ID = defaultShopID;
        title = defaultTitle;
        mapAddress.setText(defaultAddress);


        Log.d("MARKER_TAG", "gIntent[TAG] :" + tagCheck);
        Log.d("MARKER_TAG", "ID :" + defaultShopID);
        Log.d("MARKER_TAG", "TITLE :" + defaultTitle);
        Log.d("MARKER_TAG", "ADDRESS :" + defaultAddress);
        Log.d("MARKER_TAG", "LAT :" + defaultLat);
        Log.d("MARKER_TAG", "LNG :" + defaultLng);
        Log.d("MARKER_TAG", "V_SW_LAT :" + defaultVSWLat);
        Log.d("MARKER_TAG", "V_SW_LNG :" + defaultVSWLng);
        Log.d("MARKER_TAG", "V_NE_LAT :" + defaultVNELat);
        Log.d("MARKER_TAG", "V_NE_LNG :" + defaultVNELng);
        Log.d("MARKER_TAG", "OPEN :" + defaultTimeOpen);
        Log.d("MARKER_TAG", "CLOSE :" + defaultTimeClose);
        Log.d("MARKER_TAG", "THEME1 :" + defaultTheme1);
        Log.d("MARKER_TAG", "THEME2 :" + defaultTheme2);
        Log.d("MARKER_TAG", "THEME3 :" + defaultTheme3);
        Log.d("MARKER_TAG", "THEME4 :" + defaultTheme4);
        Log.d("MARKER_TAG", "THEME5 :" + defaultTheme5);
        Log.d("MARKER_TAG", "CALL :" + defaultCall);
        Log.d("MARKER_TAG", "WEB :" + defaultWeb);

    }

    private void listener() {


        shopDetailViewPager.setOnClickListener(this);
        shopDetailTitle.setOnClickListener(this);
        shopDetailTitleReviewCnt.setOnClickListener(this);
        shopDetailTimeOpen.setOnClickListener(this);
        shopDetailTimeClose.setOnClickListener(this);
        shopDetailQuestion_Question.setOnClickListener(this);
        shopDetailQuestion_Answer.setOnClickListener(this);
        shopDetailQuestion.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN: {

                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                        } else {
                            break;
                        }

                    }
                    case MotionEvent.ACTION_UP: {

                        SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopDetail_Main.this);
                        Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Question_Submit.class);
                        intent.putExtra("reviewTitle", title);
                        intent.putExtra("reviewShopId", defaultShopID);
                        intent.putExtra("reviewUserId", pref.getSharedData("isLogged_id"));
                        intent.putExtra("reviewUserNick", pref.getSharedData("isLogged_nick"));
                        startActivity(intent);
                        break;
                    }

                }

                return false;
            }
        });


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
                intent.putExtra("reviewShopId", defaultShopID);
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
                intent.putExtra("reviewShopId", defaultShopID);
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
        shopDetailQuestion.setOnClickListener(this);

        shopDetailReview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {


                    case MotionEvent.ACTION_DOWN: {

                        if (event.getAction() == MotionEvent.ACTION_UP) {

                        } else {
                            break;
                        }

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopDetail_Main.this);
                        Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Review_Submit.class);
                        intent.putExtra("reviewTitle", title);
                        intent.putExtra("reviewShopId", defaultShopID);
                        intent.putExtra("reviewUserId", pref.getSharedData("isLogged_id"));
                        intent.putExtra("reviewUserNick", pref.getSharedData("isLogged_nick"));
                        startActivity(intent);
                        break;
                    }

                }

                return false;
            }


        });

        shopDetailTip.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {

                        if (event.getAction() == MotionEvent.ACTION_UP) {

                        } else {
                            break;
                        }


                    }

                    case MotionEvent.ACTION_UP: {
                        SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopDetail_Main.this);
                        Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Tip_Submit.class);
                        intent.putExtra("reviewTitle", title);
                        intent.putExtra("reviewShopId", defaultShopID);
                        intent.putExtra("reviewUserId", pref.getSharedData("isLogged_id"));
                        intent.putExtra("reviewUserNick", pref.getSharedData("isLogged_nick"));
                        startActivity(intent);
                        break;
                    }


                }

                return false;
            }
        });


        shopDetailQuestionAllBtn.setOnClickListener(this);
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
//open album
                GalleryConfig config = new GalleryConfig.Build()
                        .limitPickPhoto(5)
                        .singlePhoto(false)
//                        .hintOfPick("")
                        .filterMimeTypes(new String[]{"image/jpeg"})
                        .build();
                GalleryActivity.openActivity(ShopDetail_Main.this, reqCode, config);


                break;
            }

            case R.id.shopDetailCheckin: {

                break;
            }

            case R.id.shopDetailBookmark: {

                break;
            }

            case R.id.mapAddress: {

                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Overview_Map.class);
                startActivity(intent);
                break;
            }

            case R.id.shopDetailCallBtn: {

                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Log.d("TEDPermission","onPermissionGranted");

                        Uri uri = Uri.parse("tel:" + defaultCall);
                        Intent callIntent = new Intent("android.intent.action.CALL");
                        callIntent.setData(uri);
                        startActivity(callIntent);

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Log.d("TEDPermission","onPermissionDenied");
                    }
                };

                TedPermission.with(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .check();


                break;
            }

            case R.id.shopDetailDirection: {

                Intent intent = new Intent(this, ShopDetail_Main_Direction.class);
                startActivity(intent);

                break;
            }

            case R.id.shopDetailMenu: {

                break;
            }

            case R.id.shopDetailWebsiteBtn: {

                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                String tag = pref.getSharedData("TAG");
                String webSite = pref.getSharedData("WEB" + tag);
                String webSite2 = String.valueOf(webSite);
                Log.d("Website_Check", "tag :" + tag);
                Log.d("Website_Check", "webSite :" + webSite);
                Log.d("Website_Check", "webSite2.isEmpty() :" + webSite2.isEmpty());
                Log.d("Website_Check", "webSite2.length() :" + webSite2.length());

                if (webSite2.equals("") || webSite2.isEmpty() || webSite2.equals("null")) {
                    final AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
                    localBuilder.setTitle("OOPS!")
                            .setMessage("해당 상점은 Web Site 정보가 없습니다. 설정해 보시겠나요?")
                            .setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                                }
                            }).setNegativeButton("계속하기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                            Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_AddWebsite.class);
                            startActivity(intent);

                        }
                    })
                            .create()
                            .show();
                } else {
                    Log.d("Website_Check", "NON - NULL");

                    boolean check = ValidateUtil.validateHttp(webSite2);
                    Log.d("Website_Check", "check :" + check);

                    if (check) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webSite2));
                        startActivity(intent);
                    } else {
                        Log.d("Website_Check", "else");

                        DialogInterface.OnClickListener websiteIntent = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_AddWebsite.class);
                                startActivity(intent);
                            }
                        };
                        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        };
                        new AlertDialog.Builder(this)
                                .setTitle("해당 상점은 Web Site 정보가 잘못되었습니다.")
                                .setMessage(pref.getSharedData("isLogged_nick") + "님 께서 다시 제안해 보시겠나요?")
                                .setPositiveButton("좋아요", websiteIntent)
                                .setNegativeButton("취소", cancelListener)
                                .show();
                    }

                }


                break;
            }
            case R.id.shopDetailMessageBtn: {

//                sendSMS(defaultCall, "ShopDetail_Question_Answer_Submit Name"+defaultTitle+"Nickname :"+defaultShopID+"\n문의내용 : ");
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                String smsBody = "ShopDetail_Question_Answer_Submit Name :" + defaultTitle + "\nNickname :" + defaulUserId + "\n문의내용 : ";
                sendIntent.putExtra("sms_body", smsBody); // 보낼 문자
                sendIntent.putExtra("address", defaultCall); // 받는사람 번호
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
                break;
            }
            case R.id.shopDetailProfile: {

                break;
            }
            case R.id.shopDetailProfile2: {

                break;
            }

            case R.id.shopDetailQuestionAllBtn: {

                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Question_All.class);
                intent.putExtra("SHOPID", defaultShopID);
                startActivity(intent);
                break;
            }

            case R.id.reviewPagingBtn : {

                Intent intent = new Intent (ShopDetail_Main.this, ShopDetail_Review_Viewing.class );
                intent.putExtra("SHOPID", defaultShopID);
                startActivity(intent);
                break;

            }

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        Log.d("DetailMain", "onMapReady = Enter");
        mMap = googleMap;

        Double lat = Double.valueOf(defaultLat);
        Double lng = Double.valueOf(defaultLng);
        Log.d("DetailMain", "LAT" + defaultLat);
        Log.d("DetailMain", "LNG" + defaultLng);
        LatLng latlng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng)
                .title(defaultTitle);
        Marker marker = mMap.addMarker(markerOptions);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
    }

    @Override
    protected void onResume() {
        super.onResume();

        defaulDataSet();
        try {
            adapting();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setShopData();

    }

    /*

                SEND && RECEIVE SMS

            public void sendSMS(String smsNumber, String smsText){
                PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
                PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);


                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch(getResultCode()){
                            case Activity.RESULT_OK:
                                // 전송 성공
                                Toast.makeText(ShopDetail_Main.this, "전송 완료", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                // 전송 실패
                                Toast.makeText(ShopDetail_Main.this, "전송 실패", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                // 서비스 지역 아님
                                Toast.makeText(ShopDetail_Main.this, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_RADIO_OFF:
                                // 무선 꺼짐
                                Toast.makeText(ShopDetail_Main.this, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NULL_PDU:
                                // PDU 실패
                                Toast.makeText(ShopDetail_Main.this, "PDU Null", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter("SMS_SENT_ACTION"));

                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch (getResultCode()){
                            case Activity.RESULT_OK:
                                // 도착 완료
                                Toast.makeText(ShopDetail_Main.this, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                                break;
                            case Activity.RESULT_CANCELED:
                                // 도착 안됨
                                Toast.makeText(ShopDetail_Main.this, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter("SMS_DELIVERED_ACTION"));

                SmsManager mSmsManager = SmsManager.getDefault();
                mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
            }
            */
    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "알림 문자 메시지가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    //process result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //list of photos of selece
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        } else {
            Log.d("imagePickCheck", "requestCode :" + requestCode);
            Log.d("imagePickCheck", "resultCode :" + resultCode);
            Log.d("imagePickCheck", "getBytes :" + GalleryActivity.PHOTOS.getBytes());
            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);


            for (int i = 0; i < photos.size(); i++)
                Log.d("imagePickCheck", "List URI :" + photos + i);

            //list of videos of seleced
            List<String> vides = (List<String>) data.getSerializableExtra(GalleryActivity.VIDEO);
            SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
            for (int i = 0; i < photos.size(); i++) {
                Log.d("imagePickCheck", "for - count :" + i);
                String a = photos.get(i);
//                imagePath.add(0, a);
                if (i == 0) {
                    Log.d("imagePickCheck", "Case-1 :" + a);
                    pref.setSharedData("ShopPhoto_Image_1", a);
                }
                if (i == 1) {
                    Log.d("imagePickCheck", "Case-2 :" + a);
                    pref.setSharedData("ShopPhoto_Image_2", a);
                }
                if (i == 2) {
                    Log.d("imagePickCheck", "Case-3 :" + a);
                    pref.setSharedData("ShopPhoto_Image_3", a);
                }
                if (i == 3) {
                    Log.d("imagePickCheck", "Case-4 :" + a);
                    pref.setSharedData("ShopPhoto_Image_4", a);
                }
                if (i == 4) {
                    Log.d("imagePickCheck", "Case-5 :" + a);
                    pref.setSharedData("ShopPhoto_Image_5", a);
                }
            }

            String tag = pref.getSharedData("TAG");
            String iShopId = pref.getSharedData("ID" + tag);

            String iUserId = pref.getSharedData("isLogged_id");
            pref.setSharedData("HTTP_REVIEW_ID", iShopId);
            pref.setSharedData("HTTP_REVIEW_USER", iUserId);
            Log.d("imagePickCheck", "tag :" + tag);
            Log.d("imagePickCheck", "shopID :" + iShopId);
            Log.d("imagePickCheck", "userId :" + iUserId);

            Void conn;
            try {
                conn = new AsyncShopPhotoSubmit(this).execute().get(10000, TimeUnit.MILLISECONDS);
                Log.d("imagePickCheck", "MAIN THREAD conn Check :" + conn);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
