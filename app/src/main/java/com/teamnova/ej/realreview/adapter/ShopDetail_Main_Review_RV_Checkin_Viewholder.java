package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-10-26.
 */

public class ShopDetail_Main_Review_RV_Checkin_Viewholder extends RecyclerView.ViewHolder {

    com.beardedhen.androidbootstrap.AwesomeTextView shopDetailCheckinId;
    android.support.v7.widget.CardView shopDetailTopCheckinCard;
    ImageView shopDetailCheckinImage;


    public ShopDetail_Main_Review_RV_Checkin_Viewholder(View itemView) {
        super(itemView);
        shopDetailCheckinId = itemView.findViewById(R.id.shopDetailCheckinId);
        shopDetailTopCheckinCard = itemView.findViewById(R.id.shopDetailTopCheckinCard);
        shopDetailCheckinImage = itemView.findViewById(R.id.shopDetailCheckinImage);
    }
}
