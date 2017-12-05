package com.teamnova.ej.realreview.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.Asynctask.AsyncMyFeedRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Question_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Question_Set;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Set;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Tip_Set;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, LocationListener {

    public static String ID;
    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_CAMERA = 2;
    public static boolean UPLOAD_FLAG = false;

    public static double LOCATION_USER_LAT = 0;
    public static double LOCATION_USER_LNG = 0;
    public static double LOCATION_FAR_LEFT_LAT = 0;
    public static double LOCATION_FAR_LEFT_LNG = 0;
    public static double LOCATION_NEAR_RIGHT_LAT = 0;
    public static double LOCATION_NEAR_RIGHT_LNG = 0;

    private Uri mImageCaptureUri;
    /**
     * filePath Upload Thread Var
     */
    Handler mHandler = new Handler(Looper.getMainLooper());
    String login_url = "http://222.122.203.55/realreview/signup/profileimagepath.php?";
    String strAnd = "&", strId = "id=", strPw = "pw=", strNick = "nick=",
            strPath = "profile_image_path=", urlParse;
    String UrParseImage = "";
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON = "realreview";
    private static final String TAG_NICK = "nick";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    String tempNick, tempNick2;


    /**
     * Server Thread Var
     */
    int serverResponseCode = 0;
    ProgressBar dialog = null;
    /**********  File Path *************/
    final String uploadFilePath = "storage/emulated/0/Pictures/";//경로를 모르겠으면, 갤러리 어플리케이션 가서 메뉴->상세 정보
    final String uploadFileName = "Instagram/IMG_20170715_043200_310.jpg"; //전송하고자하는 파일 이름
    TextView messageText;
    Button uploadButton;
    String realPath = "";
    String upLoadServerUri = "http://222.122.203.55/realreview/signup/uploadtoserver.php";  // Set Image File at The SERVER
    /**
     * init VAR
     */

    LinearLayout nearbyLinear, meLinear, meLinearSecond, meProfileStateLayout, meMLinearMyFeed;
    RelativeLayout searchLinear;
    RecyclerView mainMeMyFeedRV, mainMeQuestionRV, mainMeTipRV;
    FrameLayout content;
    ScrollView map_container, meScrollView;
    BottomNavigationView navigation;
    private GoogleMap mMap;
    TextView searchText, meProfileUserNick, meProfileUserAddress;
    com.beardedhen.androidbootstrap.AwesomeTextView meFollowerText, meReviewCount, mainMeImageCount;
    ImageView meProfileImage, followerCntImage, reviewCntImage, imageUploadCnt;
    private String imagePath;
    Button nearbyShopAdd;
    FusedLocationProviderClient mFusedLocationClient;
    private boolean mRequestingLocationUpdates;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    com.arlib.floatingsearchview.FloatingSearchView floatingSearchView;

    com.beardedhen.androidbootstrap.BootstrapButton mainMeBtnReview, mainMeBtnTip, mainMeBtnQuestion, mainMeBtnBookmark;
    //    SupportMapFragment mapFragment;

    /**
     * Receive Location Data
     */


    //위치정보 객체
    LocationManager lm = null;
    //위치정보 장치 이름
    String provider = null;
    private MapFragment mMapFragment;
    private double myPosition_lat;
    private double myPosition_lng;
    private String resultNearRightLng;
    private String resultNearRightLat;
    private String resultFarLeftLat;
    private String resultFarLeftLng;

    public static String locationJson = "";
    public static double MY_POSITION_LAT;
    public static double MY_POSITION_LNG;

    public static StringBuilder HTTP_RECEIVE_SHOPDATA;

    public String replaceTest = "";
    ArrayList<String> fixShopDataList = new ArrayList<>();
    ArrayList<String> keyShopDataList = new ArrayList<>();
    ArrayList<String> valueShopDataList = new ArrayList<>();
    private JSONObject item2;
    private String modifyProfileImagePath;
    private MaterialDialog builder;
    private Toolbar toolbar;
    private TextView meProfileUserId;
    public ArrayList<MainMe_MyFeed_Review_Set> reviewArrayData = new ArrayList<>();
    public ArrayList<MainMe_MyFeed_Question_Set> questionArrayData = new ArrayList<>();
    public ArrayList<MainMe_MyFeed_Tip_Set> tipArrayData = new ArrayList<>();
    private String myFeedURL = "http://222.122.203.55/realreview/myFeed/myFeed.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);
        builder = new MaterialDialog.Builder(this)
                .title("Connecting")
                .content("loading..")
                .progress(true, 0)
                .show();
        init();
        defineBottomNavi();
        listener();
        receiveLocationData();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                            }
                        }
                    });

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {

        }


        Log.d("Main - onCreate", "MY_POSITION_LAT :" + MY_POSITION_LAT);
        Log.d("Main - onCreate", "MY_POSITION_LNG :" + MY_POSITION_LNG);

        LOCATION_USER_LAT = MY_POSITION_LAT;
        LOCATION_USER_LNG = MY_POSITION_LNG;

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

        pref.setSharedData("DIRECTION_USER_LAT", String.valueOf(LOCATION_USER_LAT));
        pref.setSharedData("DIRECTION_USER_LNG", String.valueOf(LOCATION_USER_LNG));
        Log.d("DIRECTION_USER", "DIRECTION_USER_LAT :" + pref.getSharedData("DIRECTION_USER_LAT"));
        Log.d("DIRECTION_USER", "DIRECTION_USER_LNG :" + pref.getSharedData("DIRECTION_USER_LNG"));

        String url = "http://222.122.203.55/realreview/Nearby/latlng.php?";
        String urlMerge = url + "lat_start=" + resultNearRightLat + "&lat_end=" + resultFarLeftLat + "&lng_start=" + resultFarLeftLng + "&lng_end=" + resultNearRightLng;
        ProgressWheel progressDialog = new ProgressWheel(this);
        AsyncMainNearbyLatLngReceive upload = new AsyncMainNearbyLatLngReceive(urlMerge, this);
        upload.execute();


    }   // onCreate

    private void init() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(getApplicationContext());

        searchLinear = findViewById(R.id.searchLinear);
        nearbyLinear = findViewById(R.id.nearbyLinear);
        meLinear = findViewById(R.id.meLinear);
        content = findViewById(R.id.content);
        map_container = findViewById(R.id.map_container);    // onCreate Init
        navigation = findViewById(R.id.navigation);
        meProfileUserNick = findViewById(R.id.meProfileUserNick);
        meProfileUserNick.setText(pref.getSharedData("isLogged_nick"));

        meProfileUserId = findViewById(R.id.meProfileUserId);
        meProfileUserId.setText(pref.getSharedData("isLogged_id"));

        meProfileUserAddress = findViewById(R.id.meProfileUserAddress);
        String userAddressSplit = pref.getSharedData("isLogged_address");
        String[] splitAddress = userAddressSplit.split(" ", 0);
        for (int i = 0; i < splitAddress.length; i++)
            Log.d("MainUserAddress", "splitAddress[" + i + "]: " + splitAddress[i]);
        String userAddressResult = splitAddress[1] + " " + splitAddress[2] + " " + splitAddress[3];
        meProfileUserAddress.setText(userAddressResult);


        meFollowerText = findViewById(R.id.mainMeFollowCount);
        meFollowerText.setMarkdownText("{fa-users} " + pref.getSharedData("isLogged_followerCnt"));

        meReviewCount = findViewById(R.id.mainMeReviewCount);
        meReviewCount.setMarkdownText("{fa-commenting} " + pref.getSharedData("isLogged_reviewCnt"));

        mainMeImageCount = findViewById(R.id.mainMeImageCount);
        mainMeImageCount.setMarkdownText("{fa-camera} " + pref.getSharedData("isLogged_imageCnt"));

        meProfileImage = findViewById(R.id.meProfileImage);
//        mainMeMyFeedRV = findViewById(R.id.mainMeMyFeedRV);
//        mainMeQuestionRV = findViewById(R.id.mainMeQuestionRV);
//        mainMeTipRV = findViewById(R.id.mainMeTipRV);


        /**
         * User Profile -> REVIEW, QUESTION, TIP 나누지 않고 MY FEED로 한번에 보이기
         * Recycler View, Use ViewType!
         */
//        mainMeTipRV = findViewById(R.id.mainMeTipRV);
//        mainMeQuestionRV = findViewById(R.id.mainMeQuestionRV);

        nearbyShopAdd = findViewById(R.id.nearbyShopAdd);
        floatingSearchView = findViewById(R.id.floating_search_view);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setTitle("REAL REVIEW");
        actionBar.setTitle("actionbar");


        mainMeBtnReview = findViewById(R.id.mainMeBtnReview);
        mainMeBtnTip = findViewById(R.id.mainMeBtnTip);
        mainMeBtnQuestion = findViewById(R.id.mainMeBtnQuestion);
        mainMeBtnBookmark = findViewById(R.id.mainMeBtnBookmark);

        mainMeBtnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Main.this, Main_Me_Reviews.class);
                startActivity(intent);
            }
        });
        mainMeBtnTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Main.this, Main_Me_Tip.class);
                startActivity(intent);
            }
        });
        mainMeBtnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Main.this, Main_Me_Question.class);
                startActivity(intent);
            }
        });
        mainMeBtnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Main.this, Main_Me_Bookmark.class);
                startActivity(intent);
            }
        });
    }

    private void defineBottomNavi() {

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_nearby:
                        nearbyLinear.setVisibility(View.VISIBLE);
                        searchLinear.setVisibility(View.GONE);
                        meLinear.setVisibility(View.GONE);
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.mapView);
                        mapFragment.getMapAsync(Main.this);
                        return true;
                    case R.id.navigation_search:
                        nearbyLinear.setVisibility(View.GONE);
                        searchLinear.setVisibility(View.VISIBLE);
                        meLinear.setVisibility(View.GONE);

                        mMapFragment = MapFragment.newInstance();
                        FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.searchLinear, mMapFragment);
                        fragmentTransaction.commit();
                        MapFragment searchFragment = (MapFragment) getFragmentManager()
                                .findFragmentById(R.id.searchMap);
                        Main_Search inst = new Main_Search(Main.this);
                        searchFragment.getMapAsync(inst);

                        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                            @Override
                            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                                //get suggestions based on newQuery
                                //pass them on to the search view
                                floatingSearchView.swapSuggestions(new ArrayList<SearchSuggestion>());
                            }
                        });


                        return true;
                    case R.id.navigation_me:
                        searchLinear.setVisibility(View.GONE);
                        nearbyLinear.setVisibility(View.GONE);
                        meLinear.setVisibility(View.VISIBLE);

//                        myFeedAdapting();

                        return true;
                }
                return false;
            }

        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_nearby);
        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        pref.setSharedData("LOCATION_FLAG", "TRUE");
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.meProfileImage: {    // Bottom - Me

                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(this)
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
                break;

            }

            case R.id.nearbyShopAdd: {
                Intent intent = new Intent(Main.this, ShopAdd1.class);
                startActivity(intent);
                break;
            }

/*
            case R.id.mainMeBtnReview :{
                Intent intent = new Intent (Main.this, Main_Me_Reviews.class);
                startActivity(intent);
                break;
            }
            case R.id.mainMeBtnTip :{
                Intent intent = new Intent (Main.this, Main_Me_Tip.class);
                startActivity(intent);
                break;
            }
            case R.id.mainMeBtnQuestion :{
                Intent intent = new Intent (Main.this, Main_Me_Question.class);
                startActivity(intent);
                break;
            }
            case R.id.mainMeBtnBookmark :{
                Intent intent = new Intent (Main.this, Main_Me_Bookmark.class);
                startActivity(intent);
                break;
            }
*/



            
            
        }

    }


    @Override
    protected void onStart() {
        super.onStart();


        /**
         * Main -> Main_Me_XXX로 분할
         */
//        myFeedAdapting();


    }

    /*
    private void myFeedAdapting() {


        reviewArrayData.clear();

        JSONObject conn;
        try {
            conn = new AsyncMyFeedRequest(myFeedURL, this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("ASYNCMyFeed", "conn : " + conn);

            *//**
             * reviewDefault Parsing
             *//*

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


            *//**
             *  questionDefault Parsing
             *//*

            MainMe_MyFeed_Question_Adapter questionAdapter = new MainMe_MyFeed_Question_Adapter(this, questionArrayData);
            LinearLayoutManager mainMeMyQuestionLayoutManager = new LinearLayoutManager(this);
            mainMeQuestionRV.setLayoutManager(mainMeMyQuestionLayoutManager);
            mainMeQuestionRV.setHasFixedSize(false);
            mainMeQuestionRV.setAdapter(questionAdapter);

            JSONArray myFeedQuestionParsing = conn.getJSONArray("questionDefault");

            for (int i = 0; i < myFeedQuestionParsing.length(); i++) {

                String question_Idx;
                String question_Id_shop;
                String question_Id_user;
                String question_Nick;
                String question_ReviewIdx;
                String type;
                String idx;
                String nick;
                String regdate;
                String shop_id;
                String user_id;
                String question;
                String answerCount;
                String metooCount;
                String shopQuestionCount;
                String address;
                String shopName;
                String callNumber;
                double rating;
                String shopImagePath;

                JSONObject myFeedQuestionParsing2 = myFeedQuestionParsing.getJSONObject(i);
                type = myFeedQuestionParsing2.getString("type");
                idx = myFeedQuestionParsing2.getString("idx");
                nick = myFeedQuestionParsing2.getString("nick");
                regdate = myFeedQuestionParsing2.getString("regdate");
                shop_id = myFeedQuestionParsing2.getString("shop_id");
                user_id = myFeedQuestionParsing2.getString("user_id");
                question = myFeedQuestionParsing2.getString("question");
                answerCount = myFeedQuestionParsing2.getString("answerCount");
                metooCount = myFeedQuestionParsing2.getString("metooCount");
                shopQuestionCount = myFeedQuestionParsing2.getString("shopQuestionCount");
                address = myFeedQuestionParsing2.getString("address");
                shopName = myFeedQuestionParsing2.getString("shopName");
                callNumber = myFeedQuestionParsing2.getString("callNumber");
//                rating = myFeedQuestionParsing2.getDouble("rating");
                shopImagePath = myFeedQuestionParsing2.getString("shopImagePath");


                JSONArray myFeedReviewParsingQuestion = myFeedQuestionParsing2.getJSONArray("metoo_array");
                Log.d("ASYNCMyFeed_REVIEW_Question", "myFeedReviewParsingQuestion : " + myFeedReviewParsingQuestion);
                Log.d("ASYNCMyFeed_REVIEW_Question", "myFeedReviewParsingQuestion2 : " + myFeedQuestionParsing2);
                ArrayList<String> metooArray = new ArrayList<>();
                for (int j = 0; j < myFeedReviewParsingQuestion.length(); j++) {

                    JSONObject myFeedReviewParsingQuestion2 = myFeedReviewParsingQuestion.getJSONObject(j);
                    question_Idx = myFeedReviewParsingQuestion2.getString("idx");
                    question_Id_shop = myFeedReviewParsingQuestion2.getString("id_shop");
                    question_Id_user = myFeedReviewParsingQuestion2.getString("id_user");
                    question_Nick = myFeedReviewParsingQuestion2.getString("nick");
                    question_ReviewIdx = myFeedReviewParsingQuestion2.getString("idx_question");
                    metooArray.add(0, nick);
                }

                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                MainMe_MyFeed_Question_Set addData = new MainMe_MyFeed_Question_Set(
                        regdate, answerCount, metooCount, shopImagePath, metooArray, address, shopName, callNumber, shopQuestionCount, shopImagePath
                );
                questionArrayData.add(0, addData);
            }

            questionAdapter.notifyDataSetChanged();


            *//**
             *  tipDefault Parsing
             *//*


            JSONArray myFeedTipParsing = conn.getJSONArray("tipDefault");
            Log.d("ASYNCMyFeed_TIP", "myFeedTipParsing : " + myFeedTipParsing);

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

            }


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
    */


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "onResume - Enter");
        //위치정보 - Activity LifeCycle 관련 메서드는 무조건 상위 메서드 호출 필요
        /** 이 화면이 불릴 때, 일시정지 해제 처리*/

        //위치정보 객체에 이벤트 연결
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(provider, 5000, 1, this);


        if (UPLOAD_FLAG) {

            new Thread(new Runnable() {
                public void run() {
                    uploadFile(imagePath);
                    Log.e("UploadFile Start -> UrParseImage:", imagePath);
                }
            }).start();

            try {
                filePathThread fpt = new filePathThread();
                fpt.start();
                fpt.join();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            UPLOAD_FLAG = false;
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

        if (builder.isShowing()) builder.dismiss();


    }

    @Override
    protected void onPause() {
        //Activity LifrCycle 관련 메서드는 무조건 상위 메서드 호출 필요
        super.onPause();
        //위치정보 객체에 이벤트 해제
        Log.d("LifeCycle", "onPause - Enter");


        lm.removeUpdates(this);
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Log.d("TEDPermission", "onPermissionDenied");
            }


        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    private void receiveLocationData() {

        /**위치정보 객체를 생성한다.*/
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /** 현재 사용가능한 위치 정보 장치 검색*/
        //위치정보 하드웨어 목록
        Criteria c = new Criteria();
        //최적의 하드웨어 이름을 리턴받는다.
//        provider = lm.getBestProvider(c, true);

        // 최적의 값이 없거나, 해당 장치가 사용가능한 상태가 아니라면,
        //모든 장치 리스트에서 사용가능한 항목 얻기
        if (provider == null || !lm.isProviderEnabled(provider)) {
            // 모든 장치 목록
            List<String> list = lm.getAllProviders();

            for (int i = 0; i < list.size(); i++) {
                //장치 이름 하나 얻기
                String temp = list.get(i);

                //사용 가능 여부 검사
                if (lm.isProviderEnabled(temp)) {
                    provider = temp;
                    break;
                }
            }
        }// (end if)위치정보 검색 끝

        /**마지막으로  조회했던 위치 얻기*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(provider);

        if (location == null) {
            Toast.makeText(this, "사용가능한 위치 정보 제공자가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            //최종 위치에서 부터 이어서 GPS 시작...
            onLocationChanged(location);

        }
    }

    /**
     * 위치가 변했을 경우 호출된다.
     */
    @Override
    public void onLocationChanged(Location location) {
        // 위도, 경도

        myPosition_lat = location.getLatitude();
        myPosition_lng = location.getLongitude();

        Log.d("MYLOG", "ADDRESS CHECK :" + getAddress(myPosition_lat, myPosition_lng));

        String checkAddress = getAddress(myPosition_lat, myPosition_lng);
        ArrayList<String> check = new ArrayList<>();
        check.add(0, checkAddress);
        if(!check.get(0).equals("")) {
            String[] splitAddress = checkAddress.split(" ", 0);
            for (int a = 0; a < splitAddress.length; a++) {
                Log.d("MYLOG", "Address Split :" + splitAddress[a]);
            }
        }
        check.clear();

        MY_POSITION_LAT = location.getLatitude();
        MY_POSITION_LNG = location.getLongitude();

        LOCATION_USER_LAT = MY_POSITION_LAT;
        LOCATION_USER_LNG = MY_POSITION_LNG;
        Log.d("MYLOG", "MY_POSITION_LAT :" + MY_POSITION_LAT + "," + MY_POSITION_LNG);
        Log.d("MYLOG", "LOCATION_USER_LAT :" + LOCATION_USER_LAT + "," + LOCATION_USER_LNG);

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    /**
     * 위도와 경도 기반으로 주소를 리턴하는 메서드
     */
    public String getAddress(double lat, double lng) {
        String address = null;

        //위치정보를 활용하기 위한 구글 API 객체
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //주소 목록을 담기 위한 HashMap
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list == null) {
            Log.e("getAddress", "주소 데이터 얻기 실패");
            return null;
        }

        if (list.size() > 0) {
            Address addr = list.get(0);
            address = addr.getCountryName() + " "
                    + addr.getPostalCode() + " "
                    + addr.getLocality() + " "
                    + addr.getThoroughfare() + " "
                    + addr.getFeatureName();
        }

        return address;


    }

    private void listener() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        String imagePath = pref.getSharedData("isLogged_profileImagePath");

        if (imagePath.isEmpty()) {
            Glide.with(Main.this)
                    .load("http://222.122.203.55/realreview/signup/profiledefault/homeme_default.jpg")
                    .into(meProfileImage);
            meProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            Glide.with(Main.this)
                    .load(imagePath)
                    .into(meProfileImage);
            meProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        meProfileImage.setOnClickListener(this);
        nearbyShopAdd.setOnClickListener(this);

    }


    /**
     * CAMERA
     */


//    /*
//     * 참고 해볼곳
//     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
//     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
//     * http://www.damonkohler.com/2009/02/android-recipes.html
//     * http://www.firstclown.us/tag/android/

    /**
     * 카메라에서 이미지 가져오기
     */

    private void doTakePhotoAction() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "DCIM/AT" + String.valueOf(System.currentTimeMillis())
                + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                mImageCaptureUri);
        intent.putExtra("return-reviewArrayData", true);
//        startActivityForResult(intent, PICK_FROM_CAMERA);
        startActivityForResult(Intent.createChooser(intent, "실행 할 카메라 선택"), PICK_FROM_CAMERA);

    }

//     */

    /**
     * 앨범에서 이미지 가져오기
     */

    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                mImageCaptureUri = data.getData();
//                realPath = getRealPathFromURI(mImageCaptureUri);

            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

//                intent.putExtra("outputX", 1000);
//                intent.putExtra("outputY", 1000);
//                intent.putExtra("aspectX", 1);
//                intent.putExtra("aspectY", 1);
//                intent.putExtra("scale", true);
                intent.putExtra("return-reviewArrayData", true);

//                    realPath = getPath(mImageCaptureUri);
//                    UrParseImage = realPath;
//                    String fileName = new File(realPath).getName();
//                    realPath = fileName;

                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }

            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제
                final Bundle extras = data.getExtras();
                Toast.makeText(this, "CROP FROM CAMERA!", Toast.LENGTH_SHORT).show();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("reviewArrayData");
                    meProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(Main.this)
                            .load(mImageCaptureUri)
                            .into(meProfileImage);

                } else {
                    Dialog_Default dial = new Dialog_Default(this);
                    dial.tempCall("Image Warning", "문제가 발생 했습니다.");
                }
                /**
                 *
                 * Recent Camera Image File PATH
                 */
                String fileName = null;
                File[] listFiles = (new File(Environment.getExternalStorageDirectory() + "/dcim/camera/").listFiles());

                if (listFiles[0].getName().endsWith(".jpg") || listFiles[0].getName().endsWith(".bmp"))
                    fileName = listFiles[0].getName();

                File file = new File(Environment.getExternalStorageDirectory() + "/dcim/" + fileName);

//                Log.e("CAMERA RECENT FILE :", String.valueOf(file));
//                Log.e("CAMERA RECENT FILE(String) :", fileName);
                Log.e("mImageCaptureUri Value --->>> :", String.valueOf(mImageCaptureUri));


                imagePath = getPath(mImageCaptureUri);
                Log.e("Image Path:", imagePath);

//                        uploadFile("/storage/emulated/0/dcim/Camera/7495c41036542d76bfe7aef7d9746641.jpg");
                UPLOAD_FLAG = true;

                // 임시 파일 삭제
//                File f = new File(mImageCaptureUri.getPath());
//
//                if (f.exists()) {
//                    f.delete();
//                }

                break;
            }

        }
    }


    public String getPath(Uri uri) {
        // uri가 null일경우 null반환
        if (uri == null) {
            return null;
        }
        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // URI경로를 반환한다.
        return uri.getPath();
    }   // getPath();


    public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist - Uri:" + upLoadServerUri);
            Log.e("uploadFile", "Source File not exist - FileName :" + fileName);

            return 0;

        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                Log.e("SERVER URL :", String.valueOf(url));
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-reviewArrayData");
                conn.setRequestProperty("Content-Type", "multipart/form-reviewArrayData;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-reviewArrayData; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form reviewArrayData necesssary after file reviewArrayData...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    Log.e("SERVER RESPONSE CODE", String.valueOf(serverResponseCode));

                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();


            } catch (MalformedURLException ex) {

                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
            }
            return serverResponseCode;

        } // End else block
    }   // uploadFile

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class filePathThread extends Thread {

        public filePathThread() throws UnsupportedEncodingException {

            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
            String time = sdfNow.format(new Date(System.currentTimeMillis()));
            Log.e("TIME", "TIME:" + time);

            SharedPreferenceUtil pref = new SharedPreferenceUtil(Main.this);
            strId += pref.getSharedData("isLogged_id");

            String aa = getPath(mImageCaptureUri);
            String fileName = new File(aa).getName();
            Log.e("AA -> Image GET NAME(mImageCaptureUri)", aa);
            String strPathEncode = fileName;
            Log.e("filePathThread", "FILE PATH THREAD - fileName :" + fileName);
            String tempPath = URLEncoder.encode(strPathEncode, "UTF-8");
            strPath += tempPath;

            urlParse = login_url + strId + strAnd + strPath;

        }

        public void run() {
            super.run();
            try {
                URL phpUrl = new URL(urlParse);
                UrParseImage = String.valueOf(phpUrl);
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
//                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                Log.e("filePathThread", "phpURL:" + phpUrl);
                Log.e("filePathThread", "urlParse:" + urlParse);
                conn.connect();
                int responseStatusCode = conn.getResponseCode();
                Log.e("filePathThread", "response code - " + responseStatusCode);
                conn.disconnect();
                strAnd = "&";
                strId = "id=";
                strPw = "pw=";
                strNick = "nick=";
                strPath = "profile_image_path=";

                // 전송 결과값 받기
                InputStreamReader inputStream = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader bufferReader = new BufferedReader(inputStream);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = bufferReader.readLine()) != null) {
                    builder.append(str).append("\n");
                }
                String result = builder.toString();
                Log.d("filePathThread", "전송결과 : " + result);
                Log.d("filePathThread", "conn.getResponseCode() : " + conn.getResponseCode());
                Log.d("filePathThread", "conn.getResponseMessage() : " + conn.getResponseMessage());

                JSONObject jsonObject = new JSONObject(result);
                Log.d("filePathThread", "jsonObject : " + jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("profilePath");
                JSONObject jsonString = jsonArray.getJSONObject(0);
                modifyProfileImagePath = jsonString.getString("imagepath");
                Log.d("filePathThread", "imagePathResponse : " + modifyProfileImagePath);
                SharedPreferenceUtil pref = new SharedPreferenceUtil(Main.this);
                pref.setSharedData("isLogged_profileImagePath", modifyProfileImagePath);


            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferenceUtil pref = new SharedPreferenceUtil(getApplicationContext());

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle", "onStop - Enter");

    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(getString(R.string.app_name))
                .setMessage("App을 종료 하시겠습니까??")
                .setPositiveButton("그만할래요", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        SharedPreferenceUtil pref = new SharedPreferenceUtil(Main.this);
                        finish();
                    }
                }).setNegativeButton("계속하기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

            }
        })
                .create()
                .show();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        ProgressWheel dial = new ProgressWheel(this);
        dial.setBackgroundColor(Color.WHITE);
        dial.spin();


        SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(this);
        String locationFlag = preferenceUtil.getSharedData("LOCATION_FLAG");

        if (locationFlag.equals("TRUE")) {
            navigation.setSelectedItemId(R.id.navigation_nearby);
            preferenceUtil.setSharedData("LOCATION_FLAG", "FALSE");
        }


        mMap = map;
        mMap.setMyLocationEnabled(true);


        LatLng officeFirst = new LatLng(37.48408310967865, 126.97256952524185);

        String cameraPosition = String.valueOf(mMap.getCameraPosition());
        Log.d("onMapReady", "cameraPosition :" + cameraPosition);

        LatLng myPosition = new LatLng(MY_POSITION_LAT, MY_POSITION_LNG);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));


//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        String sFarLeft = String.valueOf(mMap.getProjection().getVisibleRegion().farLeft);
        String sNearRight = String.valueOf(mMap.getProjection().getVisibleRegion().nearRight);

        String[] splitFarLeft = sFarLeft.split("\\(", 0);
        String[] splitNearRight = sNearRight.split("\\(", 0);

        String[] split2FarLeft = splitFarLeft[1].split(",", 0);
        String[] split2NearRight = splitNearRight[1].split(",", 0);

        String[] split3FarLeft = split2FarLeft[1].split("\\)", 0);
        String[] split3NearRight = split2NearRight[1].split("\\)", 0);


        resultFarLeftLat = split2FarLeft[0];
        resultFarLeftLng = split3FarLeft[0];
        resultNearRightLat = split2NearRight[0];
        resultNearRightLng = split3NearRight[0];

        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2FarLeft " + split2FarLeft[0]);
        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2FarLeft " + split3FarLeft[0]);
        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2NearRight " + split2NearRight[0]);
        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2NearRight " + split3NearRight[0]);
        Log.d("MYLOG", "FarLeft:" + sFarLeft);
        Log.d("MYLOG", "NearRight:" + sNearRight);

        LOCATION_FAR_LEFT_LAT = Double.parseDouble(split2FarLeft[0]);
        LOCATION_FAR_LEFT_LNG = Double.parseDouble(split3FarLeft[0]);
        LOCATION_NEAR_RIGHT_LAT = Double.parseDouble(split2NearRight[0]);
        LOCATION_NEAR_RIGHT_LNG = Double.parseDouble(split3NearRight[0]);


        String url = "http://222.122.203.55/realreview/Nearby/latlng.php?";
        String urlMerge = url + "lat_start=" + resultNearRightLat + "&lat_end=" + resultFarLeftLat + "&lng_start=" + resultFarLeftLng + "&lng_end=" + resultNearRightLng;
        ProgressWheel progressDialog = new ProgressWheel(this);
        StringBuilder conn = null;
        try {
            conn = new AsyncMainNearbyLatLngReceive(urlMerge, this).execute().get(5000, TimeUnit.MILLISECONDS);
            JSONObject castingJO = new JSONObject(String.valueOf(conn));
            Log.d("maintest", "1 - castingJO :" + castingJO);
            JSONArray fixJSON = castingJO.getJSONArray("realreview");
            Log.d("maintest", "2 - fixJSON :" + fixJSON);


            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < fixJSON.length(); i++) {
                JSONObject item = fixJSON.getJSONObject(i);
                fixShopDataList.add(String.valueOf(item));
                Log.d("JSON_CHECK", "3 - item :" + i + "번 :" + item);

                item2 = new JSONObject(fixShopDataList.get(i));
                String sLat = item2.getString("latitude");
                String sLng = item2.getString("longtitude");
                double dLat = Double.parseDouble(sLat);
                double dLng = Double.parseDouble(sLng);
                LatLng latLng = new LatLng(dLat, dLng);
                markerOptions.position(latLng)
                        .title(item2.getString("shopName"))
                        .snippet("SHOP OPEN : " + item2.getString("shopOpen") + "\nSHOP CLOSE : " + item2.getString("shopClose"));
                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(i);
                String markerTag = String.valueOf(marker.getTag());
                String a0 = item2.getString("id");
                String a1 = item2.getString("shopName");
                String a2 = item2.getString("address");
                String a3 = item2.getString("latitude");
                String a4 = item2.getString("longtitude");
                String a5 = item2.getString("viewportSouthWestLat");
                String a6 = item2.getString("viewportSouthWestLng");
                String a7 = item2.getString("viewportNorthEastLat");
                String a8 = item2.getString("viewportNorthEastLng");
                String a9 = item2.getString("shopOpen");
                String a10 = item2.getString("shopClose");
                String a11 = item2.getString("shopTheme1");
                String a12 = item2.getString("shopTheme2");
                String a13 = item2.getString("shopTheme3");
                String a14 = item2.getString("shopTheme4");
                String a15 = item2.getString("shopTheme5");
                String a16 = item2.getString("callNumber");
                String websiteNullCheck = item2.getString("webSite");

                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

                pref.setSharedData("ID" + markerTag, item2.getString("id"));
                pref.setSharedData("TITLE" + markerTag, item2.getString("shopName"));
                pref.setSharedData("ADDRESS" + markerTag, item2.getString("address"));
                pref.setSharedData("LAT" + markerTag, item2.getString("latitude"));
                pref.setSharedData("LNG" + markerTag, item2.getString("longtitude"));
                pref.setSharedData("V_SW_LAT" + markerTag, item2.getString("viewportSouthWestLat"));
                pref.setSharedData("V_SW_LNG" + markerTag, item2.getString("viewportSouthWestLng"));
                pref.setSharedData("V_NE_LAT" + markerTag, item2.getString("viewportNorthEastLat"));
                pref.setSharedData("V_NE_LNG" + markerTag, item2.getString("viewportNorthEastLng"));
                pref.setSharedData("OPEN" + markerTag, item2.getString("shopOpen"));
                pref.setSharedData("CLOSE" + markerTag, item2.getString("shopClose"));
                pref.setSharedData("MARKERTAG" + markerTag, item2.getString("shopClose"));
                pref.setSharedData("THEME1" + markerTag, item2.getString("shopTheme1"));
                pref.setSharedData("THEME2" + markerTag, item2.getString("shopTheme2"));
                pref.setSharedData("THEME3" + markerTag, item2.getString("shopTheme3"));
                pref.setSharedData("THEME4" + markerTag, item2.getString("shopTheme4"));
                pref.setSharedData("THEME5" + markerTag, item2.getString("shopTheme5"));
                pref.setSharedData("CALL" + markerTag, item2.getString("callNumber"));
                pref.setSharedData("TAG" + markerTag, String.valueOf(i));
                if (item2.getString("webSite").isEmpty()) {
                    pref.setSharedData("WEB" + markerTag, "");
                } else {
                    pref.setSharedData("WEB" + markerTag, item2.getString("webSite"));
                }

                mMap.setOnInfoWindowClickListener(infoWindowClickListener);

//                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(Marker marker) {
//
//                        Dialog_Default dial = new Dialog_Default(Main.this);
//                        dial.call("MARKER CLICK", "TODO : SHOP DETAIL ACTIVITY");
//
//
//                        return false;
//                    }
//                });


            }

            Log.d("Main, onMapReady", "connLength : " + fixJSON.length());


        } catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
            e.printStackTrace();
            Log.d("maintest", "catch");

        }

        dial.stopSpinning();

    }


    //정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = String.valueOf(marker.getTag());
//            Dialog_Default dial = new Dialog_Default(Main.this);
//            dial.call("MARKER INFO WINDOW CLICK","TODO : MAKE ACTIVITY");

            Log.d("MARKER_TAG", "marker.getTag() :" + markerId);
            SharedPreferenceUtil pref = new SharedPreferenceUtil(Main.this);
            pref.setSharedData("TAG", markerId);
            Intent intent = new Intent(Main.this, ShopDetail_Main.class);
            intent.putExtra("TAG", String.valueOf(marker.getTag()));
            startActivity(intent);

        }
    };


    /**
     * Created by ej on 2017-10-12.
     */

    public static class AsyncMainNearbyLatLngReceive extends AsyncTask<Void, Integer, StringBuilder> {
        public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
        private String urlString;
        private String params = "";
        String TestVAR;
        private MaterialDialog builder;
        Context mContext;

        AsyncMainNearbyLatLngReceive(String urlString, Context mContext) {
            this.urlString = urlString;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            builder = new MaterialDialog.Builder(mContext)
                    .title("Connecting")
                    .content("loading..")
                    .progressIndeterminateStyle(true)
                    .show();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub

            StringBuilder jsonHtml = new StringBuilder();
            JSONArray jsonArray;
            try {
                URL phpUrl = new URL(urlString);
                Log.d("AsyncMainLatLngReceive", "URL:" + urlString);

                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
                conn.setUseCaches(false);
                int responseStatusCode = conn.getResponseCode();
                Log.e(ContentValues.TAG, "response code - " + responseStatusCode);
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
            Log.e(ContentValues.TAG, "jsonHtml - " + jsonHtml);
            try {
                int i = 0;
                JSONObject jObject = new JSONObject(String.valueOf(jsonHtml));
                jsonArray = jObject.getJSONArray("realreview");
                JSONObject item = jsonArray.getJSONObject(i);
                int length = jsonArray.length();

                Log.d("AsyncMainLatLngReceive", "JSONObject jOcject:" + jObject);
                Log.d("AsyncMainLatLngReceive", "JSONArray jsonArray :" + jsonArray);
                Log.d("AsyncMainLatLngReceive", "JSONObject item:" + item);

                Log.d("AsyncMainLatLngReceive", "JSONObject jOcject Length:" + jObject.length());
                Log.d("AsyncMainLatLngReceive", "JSONArray jsonArray Length :" + jsonArray.length());
                Log.d("AsyncMainLatLngReceive", "JSONArray jsonArray Length :" + item.length());


                String getId = item.getString("id");
                String getAddress = item.getString("address");
                String getLat = item.getString("latitude");
                String getLng = item.getString("longtitude");
                String getviewportSouthWestLat = item.getString("viewportSouthWestLat");
                String getViewportSouthWestLng = item.getString("viewportSouthWestLng");
                String getViewportNorthEastLat = item.getString("viewportNorthEastLat");
                String getViewportNorthEastLng = item.getString("viewportNorthEastLng");
                String getShopName = item.getString("shopName");
                String getShopOpen = item.getString("shopOpen");
                String getShopClose = item.getString("shopClose");
                String getShopTheme1 = item.getString("shopTheme1");
                String getShopTheme2 = item.getString("shopTheme2");
                String getShopTheme3 = item.getString("shopTheme3");
                String getShopTheme4 = item.getString("shopTheme4");
                String getShopTheme5 = item.getString("shopTheme5");
                String getCallNumber = item.getString("callNumber");
                String getIndexShopAdd = item.getString("indexShopAdd");
                String getPermanentlyClosed = item.getString("permanentlyClosed");
                String getPriceLevel = item.getString("priceLevel");
                HTTP_RECEIVE_SHOPDATA = jsonHtml;

                return jsonHtml;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            if (builder.isShowing()) builder.dismiss();
        }

    }
}
