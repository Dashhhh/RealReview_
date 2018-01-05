package com.teamnova.ej.realreview.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewSubmit;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;
import com.werb.pickphotoview.PickPhotoView;
import com.werb.pickphotoview.util.PickConfig;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Review_Submit extends AppCompatActivity implements View.OnClickListener {

    android.support.v7.widget.AppCompatRatingBar reviewRating;
    MaterialEditText reviewEditText;
    TextView reviewTitle;
    private Button reviewCamera, reviewSubmit;
    private float iRating = 0;
    private String iTitle = "", iUserId = "", iNick ="", iShopId = "";
    LinearLayout reviewImageLayout;
    public final static int GALLERY_PICK = 200;
    ImageView reviewImage1, reviewImage2, reviewImage3, reviewImage4, reviewImage5;
    List<Uri> mSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__review__submit);

        getBundleData();
        init();
        listener();
        reviewImageLayout.setVisibility(View.GONE);

    }

    private void getBundleData() {

        Bundle bundle = getIntent().getExtras();
        iRating = bundle.getFloat("reviewRating");
        iTitle = bundle.getString("reviewTitle");
        iShopId = bundle.getString("reviewShopId");
        iUserId = bundle.getString("reviewUserId");
        iNick = bundle.getString("reviewUserNick");


        Log.d("Check_Intent", "iRating :" + iRating);
        Log.d("Check_Intent", "iTitle :" + iTitle);

    }

    private void listener() {

        reviewCamera.setOnClickListener(this);
        reviewSubmit.setOnClickListener(this);


    }

    private void init() {
        reviewTitle = findViewById(R.id.reviewTitle);
        reviewTitle.setText(iTitle);
        reviewRating = findViewById(R.id.reviewRating);
        reviewRating.setRating(iRating);
        reviewImageLayout = findViewById(R.id.reviewImageLayout);
        reviewImage1 = findViewById(R.id.reviewImage1);
        reviewImage2 = findViewById(R.id.reviewImage2);
        reviewImage3 = findViewById(R.id.reviewImage3);
        reviewImage4 = findViewById(R.id.reviewImage4);
        reviewImage5 = findViewById(R.id.reviewImage5);
        reviewCamera = findViewById(R.id.reviewCamera);
        reviewSubmit = findViewById(R.id.reviewSubmit);
        reviewEditText = findViewById(R.id.reviewEditText);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.reviewCamera: {

                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                        Log.d("TEDPermission","onPermissionGranted");

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Log.d("TEDPermission","onPermissionDenied");
                    }


                };

                TedPermission.with(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
                /*
                Matisse.from(ShopDetail_Review_Submit.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(5)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.progress_circle_radius))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .theme(R.style.Matisse_Zhihu)
                        .forResult(GALLERY_PICK);
                */

                new PickPhotoView.Builder(ShopDetail_Review_Submit.this)
                        .setPickPhotoSize(5)   //select max size
                        .setShowCamera(true)  //is show camera
                        .setShowCamera(true)
                        .setSpanCount(3)       //SpanCount
                        .setLightStatusBar(true)  // custom theme
                        .setStatusBarColor("#ffffff")   // custom statusBar
                        .setToolbarColor("#ffffff")   // custom toolbar
                        .setToolbarIconColor("#000000")   // custom toolbar icon
                        .setSelectIconColor("#00C07F")  // custom select icon
                        .start();
                break;
            }

            case R.id.reviewSubmit: {

                String text = String.valueOf(reviewEditText.getText().toString());

                if(!(reviewEditText.getText().toString().equals("") || text == null)){

                    if(reviewImageLayout.getVisibility() == View.VISIBLE) {

                        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                        pref.setSharedData("HTTP_REVIEW_ID",iShopId);
                        pref.setSharedData("HTTP_REVIEW_REVIEW", text);
                        pref.setSharedData("HTTP_REVIEW_USER",iUserId);
                        pref.setSharedData("HTTP_REVIEW_RATING", String.valueOf(iRating));
                        pref.setSharedData("HTTP_REVIEW_NICK", String.valueOf(iNick));
                        Void conn;
                        try {
                            conn = new AsyncReviewSubmit(this).execute().get(10000, TimeUnit.MILLISECONDS);
                            Log.d("REVIEW_Image","MAIN THREAD conn Check :"+conn);
                            finish();
                        } catch (InterruptedException | ExecutionException | TimeoutException e) {
                            e.printStackTrace();
                        }

                    } else {

                        Dialog_Default dial = new Dialog_Default(this);
                        dial.callMaterialDefault("Warning", "하나 이상의 사진을 넣어주세요.");
                    }
                } else {

                    Dialog_Default dial = new Dialog_Default(this);
                    dial.callMaterialDefault("Warning", "내용을 입력해 주세요");
                }

                break;

            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (data == null) {
            return;
        }
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            ArrayList<String> selectPaths = data.getStringArrayListExtra(PickConfig.INTENT_IMG_LIST_SELECT);
            int size = selectPaths.size();
            Log.d("REVIEW_Image", "selectPaths Size :"+size);
//            ArrayList<String> imagePath = new ArrayList<>();
            SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

//            Log.d("REVIEW_Image", "imageURL Length :"+imagePath.size());


            for (int i=0;i<size;i++){
                Log.d("REVIEW_Image", "for - count :"+i);

                String a = selectPaths.get(i);
//                imagePath.add(0, a);

                if (i == 0) {
                    Log.d("REVIEW_Image", "Case-1 :" + a);
                    Glide.with(this).load(a).into(reviewImage1);
                    pref.setSharedData("REVIEW_IMAGE_1",a);
                } else if (i == 1) {

                    Log.d("REVIEW_Image", "Case-2 :" + a);
                    Glide.with(this).load(a).thumbnail(0.5f).into(reviewImage2);
                    pref.setSharedData("REVIEW_IMAGE_2",a);
                } else if (i == 2) {
                    Log.d("REVIEW_Image", "Case-3 :" + a);
                    Glide.with(this).load(a).thumbnail(0.5f).into(reviewImage3);
                    pref.setSharedData("REVIEW_IMAGE_3",a);
                } else if (i == 3) {
                    Log.d("REVIEW_Image", "Case-4 :" + a);
                    Glide.with(this).load(a).thumbnail(0.5f).into(reviewImage4);
                    pref.setSharedData("REVIEW_IMAGE_4",a);
                } else if (i == 4) {
                    Log.d("REVIEW_Image", "Case-5 :" + a);
                    Glide.with(this).load(a).thumbnail(0.5f).into(reviewImage5);
                    pref.setSharedData("REVIEW_IMAGE_5",a);
                }

            }

            Log.d("REVIEW_Image", "- - -END FOR- - -");
            if(size < 5) {
                for(int i = size; i<=4;i++){
                    Log.d("REVIEW_Image", "잉여분 삭제 :" + i);
                    pref.setSharedData("REVIEW_IMAGE_"+(size+1),"");
                }
            }

            switch (size){

                case 0: {
                    Log.d("REVIEW_Image", "imageView 복구 case[" +size+"]" );
                    Glide.with(this).load(R.drawable.default_placeholder).into(reviewImage1);
                }
                case 1: {
                    Log.d("REVIEW_Image", "imageView 복구 case[" +size+"]" );
                    Glide.with(this).load(R.drawable.default_placeholder).into(reviewImage2);
                }
                case 2: {
                    Log.d("REVIEW_Image", "imageView 복구 case[" +size+"]" );
                    Glide.with(this).load(R.drawable.default_placeholder).into(reviewImage3);
                }
                case 3: {
                    Log.d("REVIEW_Image", "imageView 복구 case[" +size+"]" );
                    Glide.with(this).load(R.drawable.default_placeholder).into(reviewImage4);
                }
                case 4: {
                    Log.d("REVIEW_Image", "imageView 복구 case[" +size+"]" );
                    Glide.with(this).load(R.drawable.default_placeholder).into(reviewImage5);
                }

            }

            reviewImageLayout.setVisibility(View.VISIBLE);


        } else if (requestCode == GALLERY_PICK && resultCode == RESULT_OK){
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }
}
