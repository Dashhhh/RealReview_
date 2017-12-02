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

public class MainMe_MyFeed_Question_Viewholder extends RecyclerView.ViewHolder {



    LinearLayout mainMeQuestionRootLayout;
    LinearLayout mainMeQuestionContentsLayout;
    LinearLayout mainMeQuestionProfileLayout;
    ImageView mainMeQuestionProfileImage;
    ImageView mainMeQuestionShopThumbnail;
    TextView mainMeQuestionUserNick;
    TextView mainMeQuestionFollowerCount;
    TextView mainMeQuestionReviewCount;
    TextView mainMeQuestionImageCount;
    TextView mainMeQuestionWroteType;
    TextView mainMeQuestionShopQuestionCount;
    TextView mainMeQuestionShopName;
//    android.support.v7.widget.AppCompatRatingBar mainMeQuestionMyRating;
    android.support.v7.widget.AppCompatRatingBar mainMeQuestionShopRating;
//    com.beardedhen.androidbootstrap.AwesomeTextView mainMeQuestionCheckinCount;
    com.beardedhen.androidbootstrap.AwesomeTextView mainMeQuestionRegdate, mainMeQuestionMetooState;
    com.beardedhen.androidbootstrap.AwesomeTextView mainMeQuestionAnswerState;

    public MainMe_MyFeed_Question_Viewholder(View itemView) {
        super(itemView);

        mainMeQuestionRootLayout = itemView.findViewById(R.id.mainMeQuestionRootLayout);
        mainMeQuestionContentsLayout = itemView.findViewById(R.id.mainMeQuestionContentsLayout);
        mainMeQuestionProfileLayout = itemView.findViewById(R.id.mainMeQuestionProfileLayout);
        mainMeQuestionProfileImage = itemView.findViewById(R.id.mainMeQuestionProfileImage);
        mainMeQuestionUserNick = itemView.findViewById(R.id.mainMeQuestionUserNick);
        mainMeQuestionFollowerCount = itemView.findViewById(R.id.mainMeQuestionFollowerCount);
        mainMeQuestionReviewCount = itemView.findViewById(R.id.mainMeQuestionReviewCount);
        mainMeQuestionImageCount = itemView.findViewById(R.id.mainMeQuestionImageCount);
        mainMeQuestionWroteType = itemView.findViewById(R.id.mainMeQuestionWroteType);
        mainMeQuestionShopThumbnail = itemView.findViewById(R.id.mainMeQuestionShopThumbnail);
        mainMeQuestionShopQuestionCount = itemView.findViewById(R.id.mainMeQuestionShopQuestionCount);
        mainMeQuestionShopName = itemView.findViewById(R.id.mainMeQuestionShopName);
//        mainMeQuestionMyRating = itemView.findViewById(R.id.mainMeQuestionMyRating);
        mainMeQuestionShopRating = itemView.findViewById(R.id.mainMeQuestionShopRating);
//        mainMeQuestionCheckinCount = itemView.findViewById(R.id.mainMeQuestionCheckinCount);
        mainMeQuestionRegdate = itemView.findViewById(R.id.mainMeQuestionRegdate);
        mainMeQuestionMetooState = itemView.findViewById(R.id.mainMeQuestionMetooState);
        mainMeQuestionAnswerState = itemView.findViewById(R.id.mainMeQuestionAnswerState);

    }
}
