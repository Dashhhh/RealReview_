package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;

import java.util.ArrayList;

import static com.beardedhen.androidbootstrap.TypefaceProvider.registerDefaultIconSets;

/**
 * Created by ej on 2017-11-04.
 */

public class ShopDetail_Main_RV_QuestionAll_Adapter extends RecyclerView.Adapter<ShopDetail_Main_RV_QuestionAll_Viewholder> {

    ArrayList<ShopDetail_Main_RV_QuestionAll_Set> data;
    Context mContext;

    public ShopDetail_Main_RV_QuestionAll_Adapter(Context mContext, ArrayList<ShopDetail_Main_RV_QuestionAll_Set> data) {
        this.mContext = mContext;
        if (data == null) this.data = new ArrayList<>();
        else this.data = data;

    }

    @Override
    public ShopDetail_Main_RV_QuestionAll_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_shop_detail__question__all_recycler, parent, false);
        return new ShopDetail_Main_RV_QuestionAll_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(ShopDetail_Main_RV_QuestionAll_Viewholder holder, int position) {

        ShopDetail_Main_RV_QuestionAll_Set dataSet = new ShopDetail_Main_RV_QuestionAll_Set();
        dataSet = data.get(position);


        holder.questionAllImageCount.setText("0");
        holder.questionAllReviewCount.setText("0");
        holder.questionAllUserFollower.setText("0");
        holder.questionAllRegdate.setText(dataSet.getRegdate());
        holder.questionAllText.setText(dataSet.getReviewText());
        holder.questionAllUserNick.setText(dataSet.getUserNick());
        Glide.with(mContext).load("http://222.122.203.55/realreview/signup/profiledefault/homeme_default.jpg").into(holder.questionAllUserImage);

        Log.d("ASYNC_questionAll", "onBindViewHolder getRegdate : " + dataSet.getRegdate());
        Log.d("ASYNC_questionAll", "onBindViewHolder getReviewText : " + dataSet.getReviewText());
        Log.d("ASYNC_questionAll", "onBindViewHolder getUserNick : " + dataSet.getUserNick());

//
//        holder.questionAllAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("TESTTTT~","ENTER");
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
