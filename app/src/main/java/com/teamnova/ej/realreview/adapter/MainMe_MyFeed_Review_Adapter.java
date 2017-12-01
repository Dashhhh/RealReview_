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
    ArrayList<MainMe_MyFeed_Review_Set> data;
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_main_me_review_recycler, parent, false);
        return new MainMe_MyFeed_Review_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(MainMe_MyFeed_Review_Viewholder holder, int position) {


        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        MainMe_MyFeed_Review_Set getData = new MainMe_MyFeed_Review_Set();
        Glide.with(mContext).load(getData.reviewUserThumbnail).thumbnail(0.2f).into(holder.mainMeProfileImage);
        holder.mainMeProfileImage.setScaleType(ImageView.ScaleType.CENTER);
        holder.mainMeUserNick.setText(getData.nick);
        holder.mainMeFollowerCount.setText(pref.getSharedData("isLogged_followerCnt"));
        holder.mainMeImageCount.setText(pref.getSharedData("isLogged_imageCnt"));
        holder.mainMeReviewCount.setText(pref.getSharedData("isLogged_reviewCnt"));

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

        Glide.with(mContext).load(getData.shopImagepath).thumbnail(0.2f).into(holder.mainMeShopThumbnail);
        float shopRating = Float.parseFloat(getData.getRating());
        holder.mainMeShopName.setText(getData.shopName);
        holder.mainMeShopRating.setRating(shopRating);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
