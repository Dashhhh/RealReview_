package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.ArrayList;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Review_Adapter extends RecyclerView.Adapter<MainMe_MyFeed_Review_Viewholder> {

    LayoutInflater inflater = null;
    public ArrayList<MainMe_MyFeed_Review_Set> data;
    Context mContext;

    public MainMe_MyFeed_Review_Adapter(Context mContext, ArrayList<MainMe_MyFeed_Review_Set> data) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    @Override
    public MainMe_MyFeed_Review_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MainMe_MyFeed_Review_Viewholder", "onCre,ateViewHolder - Enter");
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_main_me_recycler_review, parent, false);
        return new MainMe_MyFeed_Review_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(MainMe_MyFeed_Review_Viewholder holder, int position) {


        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        MainMe_MyFeed_Review_Set getData = data.get(position);
        Glide.with(mContext).load(getData.reviewUserThumbnail).thumbnail(0.5f).into(holder.mainMeProfileImage);
        holder.mainMeProfileImage.setScaleType(ImageView.ScaleType.CENTER);
        holder.mainMeUserNick.setText(getData.nick);
        holder.mainMeFollowerCount.setText(pref.getSharedData("isLogged_followerCnt"));
        holder.mainMeImageCount.setText(pref.getSharedData("isLogged_imageCnt"));
        holder.mainMeReviewCount.setText(pref.getSharedData("isLogged_reviewCnt"));

        Log.d("getData.nick", String.valueOf(getData.nick));
        Log.d("getData.nearby", String.valueOf(getData.nearby));


        if (getData.nearby.equals("1")) {
            holder.mainMeCertify.setVisibility(View.VISIBLE);
            holder.mainMeCertify.setBadgeText("NearBy");
        } else {
            holder.mainMeCertify.setVisibility(View.GONE);
        }


        if (getData.locality.equals("1")) {
            holder.mainMeLocality.setVisibility(View.VISIBLE);
            holder.mainMeLocality.setBadgeText("Locality");
        } else {
            holder.mainMeLocality.setVisibility(View.GONE);
        }

        float rating = Float.parseFloat(getData.rating);
        holder.mainMeMyRating.setRating(rating);
        holder.mainMeWroteType.setText("REVIEW 작성");

//        Log.d("mainMeshopImagePath", getData.shopImagepath +", postition : "+ position);
        if(getData.shopImagepath.equals("")) {
            Glide.with(mContext).load(R.drawable.defaulticon).into(holder.mainMeShopThumbnail);
        } else {
            Glide.with(mContext).load(getData.shopImagepath).into(holder.mainMeShopThumbnail);
        }

        holder.mainMeShopThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        float shopRating = Float.parseFloat(getData.getRating());
        holder.mainMeShopName.setText(getData.shopName);
        holder.mainMeShopRating.setRating(shopRating);
        holder.mainMeRegdate.setMarkdownText("{fa-clock-o} " + getData.regdate);
        holder.mainMeShopReviewCount.setText("상점 리뷰 :" + getData.shopReviewCount);
        holder.mainMeCheckinCount.setMarkdownText("{fa-certificate} 5");

        if (getData.userCool.size() >= 2) {
            holder.mainMeCoolState.setMarkdownText("{fa-check-square} " + getData.userCool.get(0) + "님 외 " + (getData.userCool.size() - 1) + "명이 냉정하다고 합니다. {fa-meh-o}");
        } else if (getData.userCool.size() == 1) {
            holder.mainMeCoolState.setMarkdownText("{fa-check-square} " + getData.userCool.get(0) + "님이 냉정하다고 합니다. {fa-meh-o}");
        } else {
            holder.mainMeCoolState.setVisibility(View.GONE);
        }
        if (getData.userGood.size() >= 2) {
            holder.mainMeGoodState.setMarkdownText("{fa-check-square} " + getData.userGood.get(0) + "님 외 " + (getData.userGood.size() - 1) + "명이 좋다고 합니다! {fa-smile-o}");
        } else if (getData.userGood.size() == 1) {
            holder.mainMeGoodState.setMarkdownText("{fa-check-square} " + getData.userGood.get(0) + "님이 좋다고 합니다! {fa-smile-o}");

        } else {
            holder.mainMeGoodState.setVisibility(View.GONE);
        }
        if (getData.userUseful.size() >= 2) {
            holder.mainMeUsefulState.setMarkdownText("{fa-check-square} " + getData.userUseful.get(0) + "님 외 " + (getData.userUseful.size() - 1) + "명이 유용하다고 합니다! {fa-thumbs-o-up}");
        } else if (getData.userUseful.size() == 1) {
            holder.mainMeUsefulState.setMarkdownText("{fa-check-square} " + getData.userUseful.get(0) + "님이 유용하다고 합니다! {fa-thumbs-o-up}");
        } else {
            holder.mainMeUsefulState.setVisibility(View.GONE);
        }


        /**
         *
         * Nearby 기능과 중복되어 Check-In 제거
         *
         */
        holder.mainMeCheckinCount.setVisibility(View.GONE);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

}
