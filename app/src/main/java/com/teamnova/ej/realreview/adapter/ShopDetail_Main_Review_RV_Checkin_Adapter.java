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

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-26.
 */

public class ShopDetail_Main_Review_RV_Checkin_Adapter extends RecyclerView.Adapter<ShopDetail_Main_Review_RV_Checkin_Viewholder> {

    Context mContext;
    ArrayList<ShopDetail_Main_Review_RV_Checkin_Set> data;

    public ShopDetail_Main_Review_RV_Checkin_Adapter(Context mContext, ArrayList<ShopDetail_Main_Review_RV_Checkin_Set> data) {
        this.mContext = mContext;
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    @Override
    public ShopDetail_Main_Review_RV_Checkin_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("DETAIL_Checkin", "onCreateViewHolder - Enter");

        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_shop_detail__main_checkin_recycler, parent, false);
        return new ShopDetail_Main_Review_RV_Checkin_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(ShopDetail_Main_Review_RV_Checkin_Viewholder holder, int position) {
        Log.d("DETAIL_Checkin", "onBindViewHolder - Enter");
        ShopDetail_Main_Review_RV_Checkin_Set setCheckin = data.get(position);

        holder.shopDetailCheckinId.setMarkdownText("{fa-user-circle} " + setCheckin.getNick());
        Glide.with(mContext).load(setCheckin.getImageurl()).into(holder.shopDetailCheckinImage);
        holder.shopDetailCheckinImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //TODO : USER 정보 수정

    }

    @Override
    public int getItemCount() {
        Log.d("DETAIL_Checkin", "getItemCount - Enter");

        return data.size();
    }

}
