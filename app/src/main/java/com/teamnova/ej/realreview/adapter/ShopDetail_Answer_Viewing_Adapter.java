package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.teamnova.ej.realreview.R;

import java.util.ArrayList;

/**
 * Created by ej on 2017-11-10.
 */

public class ShopDetail_Answer_Viewing_Adapter extends RecyclerView.Adapter<ShopDetail_Answer_Viewing_Viewholder_Answer> {


    ArrayList<ShopDetail_Answer_Viewing_Set> data;
    Context mContext;

    public ShopDetail_Answer_Viewing_Adapter(Context mContext, ArrayList<ShopDetail_Answer_Viewing_Set> data) {
        this.mContext = mContext;
        if (data == null) this.data = new ArrayList<>();
        else this.data = data;

    }


    @Override
    public ShopDetail_Answer_Viewing_Viewholder_Answer onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_shop_detail__answer_viewing_recycler1, parent, false);
        return new ShopDetail_Answer_Viewing_Viewholder_Answer(v);

    }

    @Override
    public void onBindViewHolder(ShopDetail_Answer_Viewing_Viewholder_Answer holder, int position) {

        final int fPostion = position;
        ShopDetail_Answer_Viewing_Set dataSet = new ShopDetail_Answer_Viewing_Set();
        dataSet = data.get(position);


        holder.questionViewText.setText(dataSet.getAnswer());
        holder.questionViewImageCount.setText("0");
        holder.questionViewReviewCount.setText("0");
        holder.questionViewUserFollower.setText("0");
        holder.questionViewRegdate.setMarkdownText("{fa-clock-o} "+dataSet.getRegdate());
        holder.questionViewUserNick.setText(dataSet.getNick());

        Glide.with(mContext).load(dataSet.getNick_imagepath()).thumbnail(0.5f).into(holder.questionViewUserImage);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("QUESTION_RV", " Enter - getItemViewType");

        return super.getItemViewType(position);
    }
}
