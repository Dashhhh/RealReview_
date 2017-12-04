package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Tip_Viewholder extends RecyclerView.ViewHolder {


    ImageView mainMeTipShopThumbnail;
    LinearLayout mainMeTipRootLayout;
    com.beardedhen.androidbootstrap.AwesomeTextView mainMeTipCheckinCount;
    TextView mainMeTipShopName, mainMeTipCount, mainMeTipText, mainMeTipRegdate
            ;
    RelativeLayout mainMeTipRelative;

    public MainMe_MyFeed_Tip_Viewholder(View itemView) {
        super(itemView);


        mainMeTipRootLayout = itemView.findViewById(R.id.mainMeTipRootLayout);
        mainMeTipCheckinCount = itemView.findViewById(R.id.mainMeTipCheckinCount);
        mainMeTipShopName = itemView.findViewById(R.id.mainMeTipShopName);
        mainMeTipCount = itemView.findViewById(R.id.mainMeTipCount);
        mainMeTipText = itemView.findViewById(R.id.mainMeTipText);
        mainMeTipRelative = itemView.findViewById(R.id.mainMeTipRelative);
        mainMeTipShopThumbnail = itemView.findViewById(R.id.mainMeTipShopThumbnail);
        mainMeTipRegdate = itemView.findViewById(R.id.mainMeTipRegdate);

    }
}
