package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Review_Viewholder extends RecyclerView.ViewHolder {


    LinearLayout mainMeRootLayout;
    LinearLayout mainMeContentsLayout;
    LinearLayout mainMeProfileLayout;
    ImageView mainMeProfileImage;
    ImageView mainMeShopThumbnail;
    TextView mainMeUserNick;
    TextView mainMeFollowerCount;
    TextView mainMeReviewCount;
    TextView mainMeImageCount;
    com.beardedhen.androidbootstrap.BootstrapBadge mainMeCertify;
    com.beardedhen.androidbootstrap.BootstrapBadge mainMeLocality;
    TextView mainMeWroteType;
    TextView mainMeShopReviewCount;
    TextView mainMeShopName;
    android.support.v7.widget.AppCompatRatingBar mainMeMyRating;
    android.support.v7.widget.AppCompatRatingBar mainMeShopRating;
    com.beardedhen.androidbootstrap.AwesomeTextView mainMeCheckinCount;
    com.beardedhen.androidbootstrap.AwesomeTextView mainMeRegdate, mainMeCoolState, mainMeGoodState, mainMeUsefulState;

    public MainMe_MyFeed_Review_Viewholder(View itemView) {
        super(itemView);


        mainMeRootLayout = itemView.findViewById(R.id.mainMeRootLayout);
        mainMeContentsLayout = itemView.findViewById(R.id.mainMeContentsLayout);
        mainMeProfileLayout = itemView.findViewById(R.id.mainMeProfileLayout);
        mainMeProfileImage = itemView.findViewById(R.id.mainMeProfileImage);
        mainMeShopThumbnail = itemView.findViewById(R.id.mainMeShopThumbnail);
        mainMeUserNick = itemView.findViewById(R.id.mainMeUserNick);
        mainMeFollowerCount = itemView.findViewById(R.id.mainMeFollowerCount);
        mainMeReviewCount = itemView.findViewById(R.id.mainMeReviewCount);
        mainMeImageCount = itemView.findViewById(R.id.mainMeImageCount);
        mainMeCertify = itemView.findViewById(R.id.mainMeCertify);
        mainMeLocality = itemView.findViewById(R.id.mainMeLocality);
        mainMeWroteType = itemView.findViewById(R.id.mainMeWroteType);
        mainMeShopReviewCount = itemView.findViewById(R.id.mainMeShopReviewCount);
        mainMeShopName = itemView.findViewById(R.id.mainMeShopName);
        mainMeMyRating = itemView.findViewById(R.id.mainMeMyRating);
        mainMeShopRating = itemView.findViewById(R.id.mainMeShopRating);
        mainMeCheckinCount = itemView.findViewById(R.id.mainMeCheckinCount);
        mainMeRegdate = itemView.findViewById(R.id.mainMeRegdate);
        mainMeCoolState = itemView.findViewById(R.id.mainMeCoolState);
        mainMeGoodState = itemView.findViewById(R.id.mainMeGoodState);
        mainMeUsefulState = itemView.findViewById(R.id.mainMeUsefulState);

    }
}
