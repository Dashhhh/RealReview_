package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewCoolDown;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewCoolUp;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewDelete;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewGoodDown;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewGoodUp;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewUsefulDown;
import com.teamnova.ej.realreview.Asynctask.AsyncReviewUsefulUp;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.activity.ShopDetail_Main;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by ej on 2017-11-24.
 */

public class ShopDetail_Main_RV_ReviewAll_Adapter extends RecyclerView.Adapter<ShopDetail_Main_RV_ReviewAll_Viewholder> {


    ArrayList<ShopDetail_Main_RV_ReviewAll_Set> data;
    Context mContext;

    public ShopDetail_Main_RV_ReviewAll_Adapter(ArrayList<ShopDetail_Main_RV_ReviewAll_Set> data, Context mContext) {
        if (data == null) this.data = new ArrayList<>();
        else this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ShopDetail_Main_RV_ReviewAll_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_shop_detail__review__viewing_recycler, parent, false);
        return new ShopDetail_Main_RV_ReviewAll_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(ShopDetail_Main_RV_ReviewAll_Viewholder holder, int position) {
        final int fPosition = position;
        ShopDetail_Main_RV_ReviewAll_Set setData = data.get(position);
        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        String checkUser = pref.getSharedData("isLogged_nick");

        holder.reviewAllGoodBtn.setOnCheckedChangedListener(null);
        holder.reviewAllCoolBtn.setOnCheckedChangedListener(null);
        holder.reviewAllUsefulBtn.setOnCheckedChangedListener(null);

        if (!setData.titleImage.equals("")) {
            Glide.with(mContext).load(setData.titleImage).thumbnail(0.5f)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop(mContext)))
                    .into(holder.reviewAllUserImage);
        } else {
            Glide.with(mContext).load("http://222.122.203.55/realreview/signup/profiledefault/default_food.png").thumbnail(0.5f)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop(mContext)))
                    .into(holder.reviewAllUserImage);
        }

        holder.reviewAllRegdate.setMarkdownText("{fa-clock-o} " + setData.regdate);
        holder.reviewAllUserId.setText(setData.getNick());
        holder.reviewAllReviewCount.setText(setData.getReviewCnt());
        holder.reviewAllUserFollower.setText(setData.getFollowerCnt());
        holder.reviewAllImageCount.setText(setData.getImageCnt());
        holder.reviewAllText.setText(setData.getReviewText());
        holder.reviewAllRating.setRating(setData.getfRating());
        holder.reviewAllUsefulBtn.setMarkdownText("{ty_lightbulb} 유용해요(" + setData.getUsefulCount() + ")");
        holder.reviewAllGoodBtn.setMarkdownText("{md_thumb_up} 멋있어요 (" + setData.getGoodCount() + ")");
        holder.reviewAllCoolBtn.setMarkdownText("{fa_meh_o} 냉정해요(" + setData.getCoolCount() + ")");

        if (setData.isUseful_selectable()) holder.reviewAllUsefulBtn.setSelected(true);
        else holder.reviewAllUsefulBtn.setSelected(false);

        if (setData.isGood_selectable()) holder.reviewAllGoodBtn.setSelected(true);
        else holder.reviewAllGoodBtn.setSelected(false);

        if (setData.isCool_selectable()) holder.reviewAllCoolBtn.setSelected(true);
        else holder.reviewAllCoolBtn.setSelected(false);


        if (setData.locality.equals("1")) {
            holder.reviewAllLocality.setVisibility(View.VISIBLE);
            holder.reviewAllLocality.setBadgeText("Locality");
        } else {
            holder.reviewAllLocality.setVisibility(View.GONE);
        }
        if (setData.nearby.equals("1")) {
            holder.reviewAllCertify.setVisibility(View.VISIBLE);
            holder.reviewAllCertify.setBadgeText("NearBy");
        } else {
            holder.reviewAllCertify.setVisibility(View.GONE);
        }

        Log.d("REVIEW_DELETE", "getView");

        /**
         * Delete Review -> Divide Function
         */

        if (checkUser.equals(setData.getNick())) {
            // TODO - Setting onClickListener for User Layoutw

            holder.reviewAllDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialDialog.Builder(mContext)
                            .title("Warning")
                            .content("정말 REVIEW를 삭제하시겠습니까?")
                            .positiveText("삭제")
                            .negativeText("취소")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    ShopDetail_Main_RV_ReviewAll_Set dataSet = data.get(fPosition);
                                    String aShopID = ShopDetail_Main.SHOP_ID;
                                    String aUserID = dataSet.getUserId();
                                    String aIDX = dataSet.getIdx();

                                    Void conn;
                                    try {
                                        conn = new AsyncReviewDelete(aShopID, aUserID, aIDX, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (TimeoutException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("REVIEW_DELETE", "fPosition :" + fPosition);

                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                }
                            })
                            .show();
                }
            });

        } else {
            holder.reviewAllDelete.setVisibility(View.GONE);
            holder.reviewAllModify.setVisibility(View.GONE);
        }


//        vh.reviewLayoutForLoginUser.setVisibility(View.GONE);

        holder.reviewAllText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        holder.reviewAllUsefulBtn.setOnCheckedChangedListener(new BootstrapButton.OnCheckedChangedListener() {
            @Override
            public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {

                if (isChecked) {
                    Log.d("Useful_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_ReviewAll_Set data1 = data.get(fPosition);
                    String sDataUseful = data1.getUsefulCount();
                    int iDataUseful = Integer.parseInt(sDataUseful);
                    Log.d("Useful_OnCheckedChanged", "ENTER, AllStar");
                    iDataUseful++;
                    data1.setUsefulCount(String.valueOf(iDataUseful));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Log.d("Useful_OnCheckedChanged", "sDataUserID :" + sDataUserID);
                    Log.d("Useful_OnCheckedChanged", "sDataShopID :" + sDataShopID);
                    Log.d("Useful_OnCheckedChanged", "sDataNick   :" + sDataNick);
                    Log.d("Useful_OnCheckedChanged", "sDataIdx    :" + sDataIdx);
                    Void conn;
                    try {
                        conn = new AsyncReviewUsefulUp(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{ty_lightbulb} 유용해요 (" + iDataUseful + ")");
                } else {
                    Log.d("Useful_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_ReviewAll_Set data1 = data.get(fPosition);
                    String sDataUseful = data1.getUsefulCount();
                    int iDataUseful = Integer.parseInt(sDataUseful);
                    Log.d("Useful_OnCheckedChanged", "ENTER, AllStar");
                    iDataUseful--;
                    data1.setUsefulCount(String.valueOf(iDataUseful));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Void conn;
                    try {
                        conn = new AsyncReviewUsefulDown(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{ty_lightbulb} 유용해요 (" + iDataUseful + ")");
                }
            }
        });


        holder.reviewAllGoodBtn.setOnCheckedChangedListener(new BootstrapButton.OnCheckedChangedListener() {
            @Override
            public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {

                if (isChecked) {
                    Log.d("reviewAllGoodBtn_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_ReviewAll_Set data1 = data.get(fPosition);
                    String sDataGood = data1.getGoodCount();
                    int iDatagood = Integer.parseInt(sDataGood);
                    Log.d("reviewAllGoodBtn_OnCheckedChanged", "ENTER, AllStar");
                    iDatagood++;
                    data1.setGoodCount(String.valueOf(iDatagood));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Void conn;
                    try {
                        conn = new AsyncReviewGoodUp(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{md_thumb_up} 멋있어요 (" + iDatagood + ")");
                } else {
                    Log.d("good_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_ReviewAll_Set data1 = data.get(fPosition);
                    String sDatagood = data1.getGoodCount();
                    int iDatagood = Integer.parseInt(sDatagood);
                    Log.d("good_OnCheckedChanged", "ENTER, AllStar");
                    iDatagood--;
                    data1.setGoodCount(String.valueOf(iDatagood));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Void conn;
                    try {
                        conn = new AsyncReviewGoodDown(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{md_thumb_up} 멋있어요 (" + iDatagood + ")");
                }
            }
        });


        holder.reviewAllCoolBtn.setOnCheckedChangedListener(new BootstrapButton.OnCheckedChangedListener() {
            @Override
            public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {

                if (isChecked) {
                    Log.d("reviewCoolBtn_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_ReviewAll_Set data1 = data.get(fPosition);
                    String sDataCool = data1.getCoolCount();
                    int iDataCool = Integer.parseInt(sDataCool);
                    Log.d("reviewCoolBtn_OnCheckedChanged", "ENTER, AllStar");
                    iDataCool++;
                    data1.setCoolCount(String.valueOf(iDataCool));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Void conn;
                    try {
                        conn = new AsyncReviewCoolUp(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{fa_meh_o} 냉정해요(" + iDataCool + ")");
                } else {
                    Log.d("Cool_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_RV_ReviewAll_Set data1 = data.get(fPosition);
                    String sDataCool = data1.getCoolCount();
                    int iDataCool = Integer.parseInt(sDataCool);
                    Log.d("Cool_OnCheckedChanged", "ENTER, AllStar");
                    iDataCool--;
                    data1.setCoolCount(String.valueOf(iDataCool));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick = pref.getSharedData("isLogged_nick");
                    String sDataIdx = data1.getIdx();
                    Void conn;
                    try {
                        conn = new AsyncReviewCoolDown(sDataShopID, sDataUserID, sDataNick, sDataIdx, mContext).execute().get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    bootstrapButton.setMarkdownText("{fa_meh_o} 냉정해요(" + iDataCool + ")");
                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
