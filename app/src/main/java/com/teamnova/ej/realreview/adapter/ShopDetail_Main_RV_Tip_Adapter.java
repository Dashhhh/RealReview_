package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-26.
 */

public class ShopDetail_Main_RV_Tip_Adapter extends RecyclerView.Adapter<ShopDetail_Main_RV_Tip_Viewholder> {

    ArrayList<ShopDetail_Main_RV_Tip_Set> data;
    Context mContext;

    public ShopDetail_Main_RV_Tip_Adapter(Context mContext, ArrayList<ShopDetail_Main_RV_Tip_Set> data) {
        this.mContext = mContext;
        if (data == null) this.data = new ArrayList<>();
        else this.data = data;

    }

    @Override
    public ShopDetail_Main_RV_Tip_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_shop_detail__main_tip_recycler, parent, false);
        return new ShopDetail_Main_RV_Tip_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(ShopDetail_Main_RV_Tip_Viewholder holder, int position) {

        ShopDetail_Main_RV_Tip_Set dataSet = new ShopDetail_Main_RV_Tip_Set();
        dataSet = data.get(position);


        holder.tipImageCount.setText("0");
        holder.tipReviewCount.setText("0");
        holder.tipUserFollower.setText("0");
        holder.tipRegdate.setMarkdownText("{fa-clock-o} " + dataSet.getRegdate());
        holder.tipText.setText(dataSet.getReviewText());
        holder.tipUserNick.setText(dataSet.getUserNick());
        Glide.with(mContext).load("http://222.122.203.55/realreview/signup/profiledefault/homeme_default.jpg").into(holder.tipUserImage);
        Glide.with(mContext).load(dataSet.imagepath).into(holder.tipUserImage);

        Log.d("ASYNC_TIP", "dataSet.nearby : " + dataSet.nearby);
        if (dataSet.nearby.equals("1")) {
            Log.d("ASYNC_TIP", "dataSet.nearby TRUE(1) : " + dataSet.nearby);
            holder.tipCertify.setVisibility(View.VISIBLE);
            holder.tipCertify.setBadgeText("NearBy");
//            holder.tipTextLayout.setBackgroundResource(R.drawable.shape_bg_pink_rounded_rect);
//            holder.tipProfileLayout.setBackgroundResource(R.drawable.shape_bg_pink_rounded_rect);
//            holder.tipText.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipUserNick.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipRegdate.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipUserFollower.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipReviewCount.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipImageCount.setTextColor(Color.parseColor("#ffffff"));
        } else {
            Log.d("ASYNC_TIP", "dataSet.nearby FALSE(0) : " + dataSet.nearby);
            holder.tipCertify.setVisibility(View.GONE);

        }


        if (dataSet.locality.equals("1")) {
            Log.d("ASYNC_TIP", "dataSet.locality TRUE(1) : " + dataSet.locality);
            holder.tipLocality.setVisibility(View.VISIBLE);
            holder.tipLocality.setBadgeText("Locality");
//            holder.tipTextLayout.setBackgroundResource(R.drawable.shape_bg_pink_rounded_rect);
//            holder.tipProfileLayout.setBackgroundResource(R.drawable.shape_bg_pink_rounded_rect);
//            holder.tipText.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipUserNick.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipRegdate.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipUserFollower.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipReviewCount.setTextColor(Color.parseColor("#ffffff"));
//            holder.tipImageCount.setTextColor(Color.parseColor("#ffffff"));
        } else {
            Log.d("ASYNC_TIP", "dataSet.locality FALSE(0) : " + dataSet.locality);
            holder.tipLocality.setVisibility(View.GONE);

        }


        Log.d("TIP_ASYNC", "onBindViewHolder getRegdate : " + dataSet.getRegdate());
        Log.d("TIP_ASYNC", "onBindViewHolder getReviewText : " + dataSet.getReviewText());
        Log.d("TIP_ASYNC", "onBindViewHolder getUserNick : " + dataSet.getUserNick());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
