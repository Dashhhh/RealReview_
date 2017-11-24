package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;


/**
 * Created by ej on 2017-11-24.
 */

public class ShopDetail_Main_RV_ReviewAll_Viewholder extends RecyclerView.ViewHolder {

    LinearLayout reviewAllRootLinear;
    ImageView reviewAllUserImage;
    TextView reviewAllUserId;
    TextView reviewAllUserFollower;
    TextView reviewAllReviewCount;
    TextView reviewAllImageCount;
    TextView reviewAllText;
    com.beardedhen.androidbootstrap.AwesomeTextView reviewAllRegdate;
    com.beardedhen.androidbootstrap.BootstrapButton reviewAllUsefulBtn;
    com.beardedhen.androidbootstrap.BootstrapButton reviewAllGoodBtn;
    com.beardedhen.androidbootstrap.BootstrapButton reviewAllCoolBtn;
    android.support.v7.widget.AppCompatRatingBar reviewAllRating;
    com.beardedhen.androidbootstrap.BootstrapButton reviewAllModify, reviewAllDelete;
    com.beardedhen.androidbootstrap.BootstrapBadge reviewAllCertify, reviewAllLocality;


    public ShopDetail_Main_RV_ReviewAll_Viewholder(View itemView) {
        super(itemView);

//        TypefaceProvider.registerDefaultIconSets();
        reviewAllRootLinear = itemView.findViewById(R.id.reviewAllRootLinear);
        reviewAllUserImage = itemView.findViewById(R.id.reviewAllUserImage);
        reviewAllUserId = itemView.findViewById(R.id.reviewAllUserId);
        reviewAllUserFollower = itemView.findViewById(R.id.reviewAllUserFollower);
        reviewAllReviewCount = itemView.findViewById(R.id.reviewAllReviewCount);
        reviewAllImageCount = itemView.findViewById(R.id.reviewAllImageCount);
        reviewAllText = itemView.findViewById(R.id.reviewAllText);
        reviewAllRegdate = itemView.findViewById(R.id.reviewAllRegdate);
        reviewAllUsefulBtn = itemView.findViewById(R.id.reviewAllUsefulBtn);
        reviewAllGoodBtn = itemView.findViewById(R.id.reviewAllGoodBtn);
        reviewAllCoolBtn = itemView.findViewById(R.id.reviewAllCoolBtn);
        reviewAllRating = itemView.findViewById(R.id.reviewAllRating_inflate);
        reviewAllModify = itemView.findViewById(R.id.reviewAllModify);
        reviewAllDelete = itemView.findViewById(R.id.reviewAllDelete);
        reviewAllCertify = itemView.findViewById(R.id.reviewAllCertify);
        reviewAllLocality = itemView.findViewById(R.id.reviewAllLocality);

    }
}
