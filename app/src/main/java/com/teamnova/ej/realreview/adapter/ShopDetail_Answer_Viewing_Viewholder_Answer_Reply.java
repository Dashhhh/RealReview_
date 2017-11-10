package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-11-10.
 */

public class ShopDetail_Answer_Viewing_Viewholder_Answer_Reply extends RecyclerView.ViewHolder {



    LinearLayout questionViewProfileLayout;
    ImageView questionViewUserImage;
    TextView questionViewText, questionViewUserNick, questionViewUserFollower, questionViewReviewCount, questionViewImageCount;
    com.beardedhen.androidbootstrap.AwesomeTextView questionViewRegdate;


    public ShopDetail_Answer_Viewing_Viewholder_Answer_Reply(View itemView) {
        super(itemView);

        questionViewProfileLayout   = itemView.findViewById(R.id.questionViewProfileLayout);
        questionViewUserImage       = itemView.findViewById(R.id.questionViewUserImage    );
        questionViewText            = itemView.findViewById(R.id.questionViewText         );
        questionViewUserNick        = itemView.findViewById(R.id.questionViewUserNick     );
        questionViewUserFollower    = itemView.findViewById(R.id.questionViewUserFollower );
        questionViewReviewCount     = itemView.findViewById(R.id.questionViewReviewCount  );
        questionViewImageCount      = itemView.findViewById(R.id.questionViewImageCount   );
        questionViewRegdate         = itemView.findViewById(R.id.questionViewRegdate      );




    }
}
