package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;

import java.util.ArrayList;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Tip_Adapter extends RecyclerView.Adapter<MainMe_MyFeed_Tip_Viewholder> {

    ArrayList<MainMe_MyFeed_Tip_Set> data = new ArrayList<>();
    Context mContext;

    public MainMe_MyFeed_Tip_Adapter(Context mContext, ArrayList<MainMe_MyFeed_Tip_Set> data) {
        this.mContext = mContext;
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    @Override
    public MainMe_MyFeed_Tip_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MainMe_MyFeed_Review_Viewholder", "onCre,ateViewHolder - Enter");
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_main_me_tip_recycler, parent, false);
        return new MainMe_MyFeed_Tip_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(MainMe_MyFeed_Tip_Viewholder holder, int position) {

        MainMe_MyFeed_Tip_Set getData = data.get(position);
        holder.mainMeTipCheckinCount.setMarkdownText("{fa-certificate} 5");
        holder.mainMeTipShopName.setText(getData.shopName);
        holder.mainMeTipCount.setText("상점 전체 Tip : "+getData.shopTipCount);
        holder.mainMeTipText.setText(getData.tip);
        holder.mainMeTipShopThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if(getData.shopImagePath.equals("")) {
            Glide.with(mContext).load(R.drawable.defaulticon).into(holder.mainMeTipShopThumbnail);
        } else {
            Glide.with(mContext).load(getData.shopImagePath).into(holder.mainMeTipShopThumbnail);
        }
        holder.mainMeTipRegdate.setText(getData.regdate);

        Log.d("ASYNCMyFeed_TIP", "getData.shop_id : " + getData.shop_id);
        Log.d("ASYNCMyFeed_TIP", "getData.shopName : " + getData.shopName);
        Log.d("ASYNCMyFeed_TIP", "getData.idx : " + getData.idx);
        Log.d("ASYNCMyFeed_TIP", "getData.locality : " + getData.locality);
        Log.d("ASYNCMyFeed_TIP", "getData.nearby : " + getData.nearby);
        Log.d("ASYNCMyFeed_TIP", "getData.shopidCheck : " + getData.shopidCheck);
        Log.d("ASYNCMyFeed_TIP", "getData.shopTipRowNum : " + getData.shopTipRowNum);
        Log.d("ASYNCMyFeed_TIP", "getData.shopImagePath : " + getData.shopImagePath);
        Log.d("ASYNCMyFeed_TIP", "getData.tip : " + getData.tip);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
