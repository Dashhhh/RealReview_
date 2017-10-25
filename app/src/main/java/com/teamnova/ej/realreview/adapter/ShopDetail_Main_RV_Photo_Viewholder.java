package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-10-24.
 */

public class ShopDetail_Main_RV_Photo_Viewholder extends RecyclerView.ViewHolder {
    com.makeramen.roundedimageview.RoundedImageView iv;
    android.support.v7.widget.CardView recyclerviewCard;
    LinearLayout recyclerviewRootLayout;

    public ShopDetail_Main_RV_Photo_Viewholder(View itemView) {
        super(itemView);
        recyclerviewCard = itemView.findViewById(R.id.recyclerviewCard);
        recyclerviewRootLayout = itemView.findViewById(R.id.recyclerviewRootLayout);
        iv = itemView.findViewById(R.id.recyclerviewImageInflate);
    }

}
