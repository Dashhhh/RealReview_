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

public class MainMe_MyFeed_Bookmark_Viewholder extends RecyclerView.ViewHolder {


    ImageView
            mainMeBookmarkShopThumbnail;
    RelativeLayout
            mainMeBookmarkContentsLayout;
    com.beardedhen.androidbootstrap.AwesomeTextView
            mainMeBookmarkCheckinCount,
            mainMeBookmarkWebsite,
            mainMeBookmarkCall,
            mainMeBookmarkShopTime,
            mainMeBookmarkRegdate,
            mainMeBookmarkAddress;
    TextView
            mainMeBookmarkShopName;


    public MainMe_MyFeed_Bookmark_Viewholder(View itemView) {
        super(itemView);

        mainMeBookmarkShopThumbnail = itemView.findViewById(R.id.mainMeBookmarkShopThumbnail);
        mainMeBookmarkContentsLayout = itemView.findViewById(R.id.mainMeBookmarkContentsLayout);
        mainMeBookmarkCheckinCount = itemView.findViewById(R.id.mainMeBookmarkCheckinCount);
        mainMeBookmarkWebsite = itemView.findViewById(R.id.mainMeBookmarkWebsite);
        mainMeBookmarkCall = itemView.findViewById(R.id.mainMeBookmarkCall);
        mainMeBookmarkShopTime = itemView.findViewById(R.id.mainMeBookmarkShopTime);
        mainMeBookmarkShopName = itemView.findViewById(R.id.mainMeBookmarkShopName);
        mainMeBookmarkRegdate = itemView.findViewById(R.id.mainMeBookmarkRegdate);
        mainMeBookmarkAddress = itemView.findViewById(R.id.mainMeBookmarkAddress);

    }
}
