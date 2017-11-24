package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.teamnova.ej.realreview.Asynctask.AsyncAlsoCuriousRequestDown;
import com.teamnova.ej.realreview.Asynctask.AsyncAlsoCuriousRequestUp;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.activity.ShopDetail_Main;
import com.teamnova.ej.realreview.activity.ShopDetail_Question_Answer_Submit;
import com.teamnova.ej.realreview.activity.ShopDetail_Answer_Viewing;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

        final int fPosition = position;
        ShopDetail_Main_RV_QuestionAll_Set dataSet = new ShopDetail_Main_RV_QuestionAll_Set();
        dataSet = data.get(position);


        holder.questionAllImageCount.setText("0");
        holder.questionAllReviewCount.setText("0");
        holder.questionAllUserFollower.setText("0");
        holder.questionAllRegdate.setMarkdownText("{fa-clock-o} "+dataSet.getRegdate());
        holder.questionAllText.setText(dataSet.getReviewText());
        holder.questionAllUserNick.setText(dataSet.getUserNick());
        holder.questionAllReplyBtn.setMarkdownText("{fa-wechat}   전체 답변보기(" + dataSet.getAnswerCount() + ")");
        holder.questionAllStar.setMarkdownText("{fa-star}   나도 궁금해요(" + dataSet.getMetooCount() + ")");
        Glide.with(mContext).load(dataSet.getImagepath()).thumbnail(0.5f)
                .apply(RequestOptions.bitmapTransform(new CircleCrop(mContext)))
                .into(holder.questionAllUserImage);


        Log.d("ASYNC_questionAll", "onBindViewHolder getRegdate : " + dataSet.getRegdate());
        Log.d("ASYNC_questionAll", "onBindViewHolder getReviewText : " + dataSet.getReviewText());
        Log.d("ASYNC_questionAll", "onBindViewHolder getUserNick : " + dataSet.getUserNick());

        if (dataSet.isCurious()) holder.questionAllStar.setSelected(true);
        else holder.questionAllStar.setSelected(false);
        Log.d("ASYNC_questionAll", "onBindViewHolder dataSet.isCurious() : " + dataSet.getUserNick());

        holder.questionAllReplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("questionAllReplyBtn", "ENTER, questionAllReplyBtn");
                ShopDetail_Main_RV_QuestionAll_Set data1 = data.get(fPosition);
                SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                String sDataNick         = data1.getUserNick();
                String sDataShopID      = ShopDetail_Main.SHOP_ID;
                String sDataQuestionIdx = data1.getIdx();
                String sDataQuestion    = data1.getReviewText();
                String sDataRegdata     = data1.getRegdate();
                String sDataFollowerCnt    = data1.getFollowerCnt();
                String sDataReviewCnt      = data1.getReviewCnt();
                String sDataImageCnt       = data1.getImageCnt();


                Log.d("questionAllReplyBtn", "sDataNick       " + sDataNick       );
                Log.d("questionAllReplyBtn", "sDataShopID     " + sDataShopID     );
                Log.d("questionAllReplyBtn", "sDataQuestionIdx" + sDataQuestionIdx);
                Log.d("questionAllReplyBtn", "sDataQuestion   " + sDataQuestion   );
                Log.d("questionAllReplyBtn", "sDataRegdata       " + sDataRegdata    );
                Log.d("questionAllReplyBtn", "sDataFollowerCnt   " + sDataFollowerCnt);
                Log.d("questionAllReplyBtn", "sDataReviewCnt     " + sDataReviewCnt  );
                Log.d("questionAllReplyBtn", "sDataImageCnt      " + sDataImageCnt   );


                Intent intent = new Intent(mContext, ShopDetail_Answer_Viewing.class);
                intent.putExtra("nick", sDataNick);
                intent.putExtra("shopid", sDataShopID);
                intent.putExtra("questionIdx", sDataQuestionIdx);
                intent.putExtra("question",sDataQuestion);
                intent.putExtra("regdate",sDataRegdata);
                intent.putExtra("followerCnt",sDataFollowerCnt);
                intent.putExtra("reviewCnt",sDataReviewCnt);
                intent.putExtra("imageCnt",sDataImageCnt);
                mContext.startActivity(intent);



            }
        });

        holder.questionAllStar.setOnCheckedChangedListener(new BootstrapButton.OnCheckedChangedListener() {
            @Override
            public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {

                if (isChecked) {
                    Log.d("QUESTION_RV_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_QuestionAll_Set data1 = data.get(fPosition);
                    String sDataMetoo = data1.getMetooCount();
                    int iDataMetoo = Integer.parseInt(sDataMetoo);
                    Log.d("QUESTION_RV_OnCheckedChanged", "ENTER, AllStar");
                    iDataMetoo++;
                    data1.setMetooCount(String.valueOf(iDataMetoo));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Void conn;
                    try {
                        conn = new AsyncAlsoCuriousRequestUp(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{fa-star}   나도 궁금해요(" + iDataMetoo + ")");
                } else {
                    Log.d("QUESTION_RV_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_QuestionAll_Set data1 = data.get(fPosition);
                    String sDataMetoo = data1.getMetooCount();
                    int iDataMetoo = Integer.parseInt(sDataMetoo);
                    Log.d("QUESTION_RV_OnCheckedChanged", "ENTER, AllStar");
                    iDataMetoo--;
                    data1.setMetooCount(String.valueOf(iDataMetoo));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Void conn;
                    try {
                        conn = new AsyncAlsoCuriousRequestDown(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{fa-star}   나도 궁금해요(" + iDataMetoo + ")");
                }


            }
        });

        holder.questionAllAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                Log.d("QUESTION_RV", "ENTER, Answer");
                Log.d("QUESTION_RV", "Arraylist " + fPosition + "번 :" + data.get(fPosition));
                ShopDetail_Main_RV_QuestionAll_Set data1 = data.get(fPosition);
                String sDataUserID = pref.getSharedData("isLogged_id");
                String sDataShopID = ShopDetail_Main.SHOP_ID;
                String sDataQuestioner = data1.getUserNick();
                String sDataQuestion = data1.getReviewText();
                String sDataIdx = data1.getIdx();
                Log.d("QUESTION_RV", " sDataUserID - " + fPosition + "번 :" + sDataUserID);
                Log.d("QUESTION_RV", " sDataShopID - " + fPosition + "번 :" + sDataShopID);
                Log.d("QUESTION_RV", " sDataQuestion - " + fPosition + "번 :" + sDataQuestion);
                Log.d("QUESTION_RV", " sDataQuestioner - " + fPosition + "번 :" + sDataQuestioner);
                Log.d("QUESTION_RV", " sDataIdx - " + fPosition + "번 :" + sDataIdx);

                Intent intent = new Intent(mContext, ShopDetail_Question_Answer_Submit.class);
                intent.putExtra("UserID", sDataUserID);
                intent.putExtra("ShopID", sDataShopID);
                intent.putExtra("Question", sDataQuestion);
                intent.putExtra("Questioner", sDataQuestioner);
                intent.putExtra("Idx", sDataIdx);
                mContext.startActivity(intent);


            }
        });


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
