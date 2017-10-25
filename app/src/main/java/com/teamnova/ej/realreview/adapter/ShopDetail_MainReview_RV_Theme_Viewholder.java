package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-10-26.
 */

public class ShopDetail_MainReview_RV_Theme_Viewholder extends RecyclerView.ViewHolder{

    LinearLayout shopDetailTopThemeRoot;
    android.support.v7.widget.CardView shopDetailTopThemeCard;
    TextView shopDetailTopThemeText;


    public ShopDetail_MainReview_RV_Theme_Viewholder(View itemView) {
        super(itemView);
        shopDetailTopThemeRoot = itemView.findViewById(R.id.shopDetailTopThemeRoot);
        shopDetailTopThemeText = itemView.findViewById(R.id.shopDetailTopThemeText);
        shopDetailTopThemeCard = itemView.findViewById(R.id.shopDetailTopThemeCard);
    }
}
