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
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.ArrayList;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Question_Adapter extends RecyclerView.Adapter<MainMe_MyFeed_Question_Viewholder> {

    LayoutInflater inflater = null;
    ArrayList<MainMe_MyFeed_Question_Set> data = new ArrayList<>();
    Context mContext;

    public MainMe_MyFeed_Question_Adapter(Context mContext, ArrayList<MainMe_MyFeed_Question_Set> data) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    @Override
    public MainMe_MyFeed_Question_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MainMe_MyFeed_Review_Viewholder", "onCre,ateViewHolder - Enter");
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_main_me_question_recycler, parent, false);
        return new MainMe_MyFeed_Question_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(MainMe_MyFeed_Question_Viewholder holder, int position) {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        MainMe_MyFeed_Question_Set getData = data.get(position);

        Glide.with(mContext).load(pref.getSharedData("isLogged_profileImagePath")).thumbnail(0.5f).into(holder.mainMeQuestionProfileImage);
        holder.mainMeQuestionUserNick.setText(pref.getSharedData("isLogged_nick"));
        holder.mainMeQuestionFollowerCount.setText(getData.getFollowerCnt());
        holder.mainMeQuestionReviewCount.setText(getData.getReviewCnt());
        holder.mainMeQuestionImageCount.setText(getData.getImageCnt());
        holder.mainMeQuestionWroteType.setText("질문 작성");

        holder.mainMeQuestionRegdate.setMarkdownText("{fa-clock-o} " + getData.getRegdate());
        holder.mainMeQuestionShopName.setText(getData.shopName);
        holder.mainMeQuestionShopRating.setRating(0.0f);

        if (getData.getUserAnswer().size() >= 2) {
            holder.mainMeQuestionAnswerState.setMarkdownText("{fa-check-square} {fa-fa} " + getData.getUserAnswer().get(0) + "님 외 " + (getData.getUserAnswer().size() - 1) + "명이 이 질문에 답변을 달았습니다!");
        } else if (getData.getUserAnswer().size() == 1) {
            holder.mainMeQuestionAnswerState.setMarkdownText("{fa-check-square} {fa-fa} " + getData.getUserAnswer().get(0) + "님이 답변을 주셨군요!");
        } else {
            holder.mainMeQuestionAnswerState.setVisibility(View.GONE);
        }

        if (getData.getUserMetoo().size() >= 2) {
            holder.mainMeQuestionMetooState.setMarkdownText("{fa-check-square} {fa-users} " + getData.getUserMetoo().get(0) + "님 외 " + (getData.getUserMetoo().size() - 1) + "명이 이 질문을 함께 궁금해 하는군요!");
        } else if (getData.getUserMetoo().size() == 1) {
            holder.mainMeQuestionMetooState.setMarkdownText("{fa-check-square} {fa-user} " + getData.getUserMetoo().get(0) + "님이 질문을 함께 궁금해 하는군요!");
        } else {
            holder.mainMeQuestionMetooState.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(pref.getSharedData("isLogged_profileImagePath")).thumbnail(0.5f).into(holder.mainMeQuestionProfileImage);
        holder.mainMeQuestionProfileImage.setScaleType(ImageView.ScaleType.CENTER);
        holder.mainMeQuestionFollowerCount.setText(pref.getSharedData("isLogged_followerCnt"));
        holder.mainMeQuestionImageCount.setText(pref.getSharedData("isLogged_imageCnt"));
        holder.mainMeQuestionReviewCount.setText(pref.getSharedData("isLogged_reviewCnt"));
        Glide.with(mContext).load(getData.getShopImagePath()).thumbnail(0.5f).into(holder.mainMeQuestionShopThumbnail);
        holder.mainMeQuestionShopName.setText(getData.getShopName());
        holder.mainMeQuestionShopQuestionCount.setText("전체 질문 수 : "+getData.getShopQuestionCount());
        Log.d("ASYNCMyFeed_REVIEW_Adapter", "pref.getSharedData(\"isLogged_profileImagePath\") : " + pref.getSharedData("isLogged_profileImagePath"));
        Log.d("ASYNCMyFeed_REVIEW_Adapter", "getData.getUserTitleImage() : " + getData.getUserTitleImage());
        Log.d("ASYNCMyFeed_REVIEW_Adapter", "getData.getShopImagePath() : " + getData.getShopImagePath());
        Log.d("ASYNCMyFeed_REVIEW_Adapter", "getUserAnswer " + getData.getUserAnswer());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
