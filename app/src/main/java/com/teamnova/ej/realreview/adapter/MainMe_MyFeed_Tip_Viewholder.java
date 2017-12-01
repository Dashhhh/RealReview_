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

public class MainMe_MyFeed_Tip_Viewholder extends RecyclerView.ViewHolder {


    private LinearLayout mainMeRootLayout;
    private LinearLayout mainMeContentsLayout;
    private LinearLayout mainMeProfileLayout;
    private ImageView mainMeProfileImage;
    private ImageView mainMeShopThumbnail;
    private TextView mainMeUserNick;
    private TextView mainMeFollowerCount;
    private TextView mainMeReviewCount;
    private TextView mainMeImageCount;
    private TextView mainMeCertify;
    private TextView reviewLocality;
    private TextView mainMeWroteType;
    private TextView mainMeShopReviewCount;
    private TextView mainMeShopName;
    private android.support.v7.widget.AppCompatRatingBar mainMeMyRating;
    private android.support.v7.widget.AppCompatRatingBar mainMeShopRating;
    private com.beardedhen.androidbootstrap.AwesomeTextView mainMeCheckinCount;
    private com.beardedhen.androidbootstrap.AwesomeTextView mainMeRegdate;

    public MainMe_MyFeed_Tip_Viewholder(View itemView) {
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
        reviewLocality = itemView.findViewById(R.id.mainMeLocality);
        mainMeWroteType = itemView.findViewById(R.id.mainMeWroteType);
        mainMeShopReviewCount = itemView.findViewById(R.id.mainMeShopReviewCount);
        mainMeShopName = itemView.findViewById(R.id.mainMeShopName);
        mainMeMyRating = itemView.findViewById(R.id.mainMeMyRating);
        mainMeShopRating = itemView.findViewById(R.id.mainMeShopRating);
        mainMeCheckinCount = itemView.findViewById(R.id.mainMeCheckinCount);
        mainMeRegdate = itemView.findViewById(R.id.mainMeRegdate);

    }
}
