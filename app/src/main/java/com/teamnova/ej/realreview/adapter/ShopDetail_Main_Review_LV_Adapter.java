package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.util.Log;
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

    public ShopDetail_Main_Review_LV_Adapter(Context mContext, ArrayList<ShopDetail_Main_Review_LV_Set> data) {
        this.mContext = mContext;
        if (data == null) {
            this.data = new ArrayList<ShopDetail_Main_Review_LV_Set>();
        } else {
            this.data = data;
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.d("REIVEW_REVIEW", "getCOUNT DATA SIZE :" + data.size());
        return (data != null) ? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        Log.d("REIVEW_REVIEW", "getItem :" + data.get(i));
        return data.get(i);
    }

    @Override
    public long getItemId(int position) {
        Log.d("REIVEW_REVIEW", "getItemId Position" + position);
        return (data != null && (0 <= position && position < data.size()) ? position : 0);
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Log.d("REIVEW_REVIEW", "getView :" + position);
        Context context = viewGroup.getContext();
        ShopDetail_Main_Review_LV_ViewHolder vh = new ShopDetail_Main_Review_LV_ViewHolder();

        if (view == null) {

            view = inflater.inflate(R.layout.activity_shop_detail__main_review_listview, viewGroup, false);
            vh.reviewRootLinear = view.findViewById(R.id.reviewRootLinear);
            vh.reviewUserImage = view.findViewById(R.id.reviewUserImage);
            vh.reviewRegdate = view.findViewById(R.id.reviewRegdate);
            vh.reviewUserId = view.findViewById(R.id.reviewUserId);
            vh.reviewUserFollower = view.findViewById(R.id.reviewUserFollower);
            vh.reviewReviewCount = view.findViewById(R.id.reviewReviewCount);
            vh.reviewImageCount = view.findViewById(R.id.reviewImageCount);
            vh.reviewRating = view.findViewById(R.id.reviewRating_inflate);
            vh.reviewText = view.findViewById(R.id.reviewText);
            vh.reviewUsefulBtn = view.findViewById(R.id.reviewUsefulBtn);
            vh.reviewNiceBtn = view.findViewById(R.id.reviewNiceBtn);
            vh.reviewCoolBtn = view.findViewById(R.id.reviewCoolBtn);
            view.setTag(vh);
        } else {
            vh = (ShopDetail_Main_Review_LV_ViewHolder) view.getTag();
        }

        ShopDetail_Main_Review_LV_Set setData = data.get(position);
        Log.d("REVIEW_VIEWING", "Adaptr setData(Instance) :" + setData);
        Log.d("REVIEW_VIEWING", "Adapter ArrayList Adater " + position + "번 :" + data.get(position));

        Glide.with(context).load("http://222.122.203.55/realreview/signup/profiledefault/default_food.png").into(vh.reviewUserImage);
        vh.reviewRegdate.setText(setData.regdate);
        vh.reviewUserId.setText(setData.userId);
        vh.reviewReviewCount.setText(setData.reviewCnt);
        vh.reviewReviewCount.setText("0");
        vh.reviewUserFollower.setText("0");
        vh.reviewImageCount.setText("0");
        vh.reviewText.setText(setData.reviewText);
        vh.reviewRating.setRating(setData.fRating);
/*

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
*/
        return view;
    }

    public void addItem(String titleImage,
                        String followerCnt,
                        String reviewCnt,
                        String imageCnt,
                        String reviewText,
                        String regdate,
                        String userId,
                        String rating,
                        float fRating) {
        ShopDetail_Main_Review_LV_Set addSetData = new ShopDetail_Main_Review_LV_Set();

        addSetData.titleImage = titleImage;
        addSetData.followerCnt = followerCnt;
        addSetData.reviewCnt = reviewCnt;
        addSetData.imageCnt = imageCnt;
        addSetData.reviewText = reviewText;
        addSetData.regdate = regdate;
        addSetData.userId = userId;
        addSetData.rating = rating;
        addSetData.fRating = fRating;


//        this.data.addAll(data);
        this.data.add(0, addSetData);
        Log.d("REVIEW_VIEWING", "Adapter addItem :" + data);
        for (int i = 0; i < this.data.size(); i++) {
            String checkData = String.valueOf(this.data.get(i));
            Log.d("REVIEW_VIEWING", "addItem !  THIS! Adapter Item :" + checkData);
            Log.d("REVIEW_VIEWING", "addItem !  THIS! Adapter Item(Array List) :" + this.data.get(i));

        }
//        for(int i =0;i<data.size();i++) {
//            String checkData = String.valueOf(data.get(i));
//            Log.d("REVIEW_VIEWING", "addItem !  @Param Data :" + checkData);
//            Log.d("REVIEW_VIEWING", "addItem !  @Param Data(Array List) :" + data.get(i));
//        }

        notifyDataSetChanged();

    }

    public void clearItem () {

        this.data.clear();

    }

}
