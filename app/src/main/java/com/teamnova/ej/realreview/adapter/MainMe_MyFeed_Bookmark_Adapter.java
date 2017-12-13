package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.activity.Main;
import com.teamnova.ej.realreview.activity.ShopDetail_Main;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.ArrayList;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Bookmark_Adapter extends RecyclerView.Adapter<MainMe_MyFeed_Bookmark_Viewholder> {

    ArrayList<MainMe_MyFeed_Bookmark_Set> data = new ArrayList<>();
    Context mContext;

    public MainMe_MyFeed_Bookmark_Adapter(Context mContext, ArrayList<MainMe_MyFeed_Bookmark_Set> data) {
        this.mContext = mContext;
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    @Override
    public MainMe_MyFeed_Bookmark_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MainMe_MyFeed_Bookmark_Viewholder", "onCre,ateViewHolder - Enter");
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_main_me_recycler_bookmark, parent, false);
        return new MainMe_MyFeed_Bookmark_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(MainMe_MyFeed_Bookmark_Viewholder holder, int position) {

        MainMe_MyFeed_Bookmark_Set getData = data.get(position);

        Glide.with(mContext).load(getData.getShopThumbnail()).into(holder.mainMeBookmarkShopThumbnail);
        holder.mainMeBookmarkShopThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.mainMeBookmarkCheckinCount.setMarkdownText("{fa-certificate} " + getData.getCheckinCount());
        holder.mainMeBookmarkWebsite.setMarkdownText("{fa-external-link}  WEB SITE : " + getData.getWebsite());
        holder.mainMeBookmarkCall.setMarkdownText("{fa-phone}  CALL : " + getData.getCall());
        holder.mainMeBookmarkShopTime.setMarkdownText("{fa-home}  Open / Close : " + getData.getShopTime());
        holder.mainMeBookmarkShopName.setText(getData.getShopName());
        holder.mainMeBookmarkRegdate.setMarkdownText("{fa-clock-o} " + getData.getRegdate());
        holder.mainMeBookmarkAddress.setMarkdownText("{fa-compass}  ADDRESS : " + getData.getAddress());

        final int fPosition = position;

        holder.mainMeBookmarkContentsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                pref.setSharedData("TAG", fPosition+"bm");
                String tag = pref.getSharedData("TAG");
                Log.d("MainMe_MyFeed_Bookmark", "holder.Onclick Position: " + fPosition );

                Intent intent = new Intent(mContext, ShopDetail_Main.class);
                intent.putExtra("TAG", String.valueOf(tag));
                mContext.startActivity(intent);

            }
        });


        /**
         * Nearby와 기능이 겹쳐서 Check-In 제거
         */
        holder.mainMeBookmarkCheckinCount.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
