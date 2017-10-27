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

public class ShopDetail_Main_RV_Tip_Viewholder extends RecyclerView.ViewHolder {

    LinearLayout tipRootLinear;
    LinearLayout tipTextLayout, tipProfileLayout;
    ImageView    tipUserImage;
    ImageView    tipCertify;
    TextView     tipUserNick;
    TextView     tipUserFollower;
    TextView     tipReviewCount;
    TextView     tipImageCount;
    TextView     tipText;
    TextView     tipRegdate;


    public ShopDetail_Main_RV_Tip_Viewholder(View itemView) {
        super(itemView);

        tipRootLinear   = itemView.findViewById(R.id.tipRootLinear  );
        tipTextLayout   = itemView.findViewById(R.id.tipTextLayout  );
        tipProfileLayout   = itemView.findViewById(R.id.tipProfileLayout  );
        tipUserImage    = itemView.findViewById(R.id.tipUserImage   );
        tipUserNick     = itemView.findViewById(R.id.tipUserNick    );
        tipUserFollower = itemView.findViewById(R.id.tipUserFollower);
        tipReviewCount  = itemView.findViewById(R.id.tipReviewCount );
        tipImageCount   = itemView.findViewById(R.id.tipImageCount  );
        tipText         = itemView.findViewById(R.id.tipText        );
        tipRegdate      = itemView.findViewById(R.id.tipRegdate     );
        tipCertify      = itemView.findViewById(R.id.tipCertify     );

    }
}
