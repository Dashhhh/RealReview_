package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.teamnova.ej.realreview.R;

public class ShopDetail_Main extends AppCompatActivity implements View.OnClickListener {


    android.support.v4.view.ViewPager shopDetailViewPager;
    TextView shopDetailTitle, shopDetailTitleReviewCnt, shopDetailTimeOpen, shopDetailTimeClose, shopDetailQuestion_Question, shopDetailQuestion_Answer;
    android.support.v7.widget.AppCompatRatingBar shopDetailTitleRating, shopDetailRatingReview, shopDetailRatingReview2;
    android.support.v7.widget.RecyclerView shopDetailRVTitleTag, shopDetailRVImage;
    Button shopDetailTopAddPhoto, shopDetailCheckin, shopDetailBookmark, mapAddress, shopDetailCallBtn, shopDetailDirection, shopDetailMenu, shopDetailWebsiteBtn, shopDetailMessageBtn;
    LinearLayout shopDetailProfile, shopDetailProfile2, shopDetailProfile3;
    SupportMapFragment mapFragmentDetail;
    android.support.v7.widget.AppCompatEditText shopDetailQuestion, shopDetailReview;
    ListView shopDetailLVReview;

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.mapFragmentDetail);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__main);

        init();
        listener();



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
        shopDetailTitleRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Review_Submit.class);
                intent.putExtra("reviewRating",v);
                startActivity(intent);

            }
        });
        shopDetailRatingReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Review_Submit.class);
                intent.putExtra("reviewRating",v);
                startActivity(intent);

            }
        });
        shopDetailRatingReview2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                Intent intent = new Intent(ShopDetail_Main.this, ShopDetail_Review_Submit.class);
                intent.putExtra("reviewRating",v);
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

                break;
            }



        }

    }
}
