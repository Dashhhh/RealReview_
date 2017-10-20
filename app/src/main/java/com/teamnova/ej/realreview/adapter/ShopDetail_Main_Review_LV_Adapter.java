package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-21.
 */

public class ShopDetail_Main_Review_LV_Adapter extends BaseAdapter {


    public ArrayList<ShopDetail_Main_Review_LV_Set> data = new ArrayList<>();
    LayoutInflater inflater = null;
    int lastPosition = -1;
    Context mContext;

    public ShopDetail_Main_Review_LV_Adapter(Context mContext) {
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (data != null) ? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int position) {
        return (data != null && (0 <= position && position < data.size()) ? position : 0);
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        Context context = viewGroup.getContext();
        ShopDetail_Main_Review_LV_ViewHolder vh = new ShopDetail_Main_Review_LV_ViewHolder();

        if(view == null) {

            view = inflater.inflate(R.layout.activity_shop_detail__main_review_listview, viewGroup, false);
            vh.reviewRootLinear = view.findViewById(R.id.reviewRootLinear);
            vh.reviewUserImage = view.findViewById(R.id.reviewUserImage);
            vh.reviewRegdate= view.findViewById(R.id.reviewRegdate);
            vh.reviewUserId = view.findViewById(R.id.reviewUserId);
            vh.reviewUserFollower = view.findViewById(R.id.reviewUserFollower);
            vh.reviewReviewCount = view.findViewById(R.id.reviewReviewCount);
            vh.reviewImageCount = view.findViewById(R.id.reviewImageCount);
            vh.reviewRating = view.findViewById(R.id.reviewRating);
            vh.reviewText = view.findViewById(R.id.reviewText);
            vh.reviewUsefulBtn = view.findViewById(R.id.reviewUsefulBtn);
            vh.reviewNiceBtn = view.findViewById(R.id.reviewNiceBtn);
            vh.reviewCoolBtn = view.findViewById(R.id.reviewCoolBtn);
            view.setTag(vh);
        } else {
            vh = (ShopDetail_Main_Review_LV_ViewHolder) view.getTag();
        }

        ShopDetail_Main_Review_LV_Set setData = data.get(position);
        if(!setData.titleImage.isEmpty()){
            Glide.with(context).load(setData.titleImage).into(vh.reviewUserImage);
        } else {
            Glide.with(context).load("http://222.122.203.55/realreview/signup/profiledefault/default_food.png").into(vh.reviewUserImage);
        }
        if(!setData.regdate.isEmpty()){
            vh.reviewRegdate.setText(setData.regdate);
        } else {
            vh.reviewRegdate.setText("알 수 없음");
        }
        if(!setData.userId.isEmpty()){
            vh.reviewUserId.setText(setData.userId);
        } else {
            vh.reviewUserId.setText("알 수 없음");
        }
        if(!setData.reviewCnt.isEmpty()){
            vh.reviewReviewCount.setText(setData.reviewCnt);
        } else {
            vh.reviewReviewCount.setText("0");
        }
        if(!setData.followerCnt.isEmpty()){
            vh.reviewUserFollower.setText(setData.followerCnt);
        }else{
            vh.reviewUserFollower.setText("0");
        }
        if(!setData.imageCnt.isEmpty()) {
            vh.reviewImageCount.setText(setData.imageCnt);
        } else {
            vh.reviewImageCount.setText("0");
        }
        if(!setData.reviewText.isEmpty()) {
            vh.reviewText.setText(setData.reviewText);
        } else {
            vh.reviewText.setText("- - - ERROR - - -");
        }
        if(!setData.rating.isEmpty()) {
            vh.reviewRating.setRating(setData.fRating);
        } else {
            vh.reviewRating.setRating(0);
        }
        return view;
    }

    public void addItem(ArrayList<ShopDetail_Main_Review_LV_Set> data){

        this.data = data;
        notifyDataSetChanged();

    }

}
