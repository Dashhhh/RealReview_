package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.bumptech.glide.Glide;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
        final int fPosition = position;
        Log.d("REIVEW_REVIEW", "getView :" + position);
        Context context = viewGroup.getContext();
        ShopDetail_Main_Review_LV_Set setData = data.get(position);
        ShopDetail_Main_Review_LV_ViewHolder vh = new ShopDetail_Main_Review_LV_ViewHolder();
        SharedPreferenceUtil pref = new SharedPreferenceUtil(context);
        String checkUser = pref.getSharedData("isLogged_nick");
        Log.d("REVIEW_VIEWING", "checkUser :" + checkUser);
        Log.d("REVIEW_VIEWING", "pref.getSharedData - isLogged_nick :" + pref.getSharedData("isLogged_nick"));
        Log.d("REVIEW_VIEWING", "pref.getSharedData - isLogged_nick :" + pref.getSharedData("isLogged_nick"));

        if (view == null) {
            TypefaceProvider.registerDefaultIconSets();
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
            vh.reviewGoodBtn = view.findViewById(R.id.revieGoodBtn);
            vh.reviewCoolBtn = view.findViewById(R.id.reviewCoolBtn);
            vh.reviewLayoutForLoginUser = view.findViewById(R.id.reviewLayoutForLoginUser);
            vh.reviewModify = view.findViewById(R.id.reviewModify);
            vh.reviewDelete = view.findViewById(R.id.reviewDelete);
            vh.reviewCertify = view.findViewById(R.id.reviewCertify);
            vh.reviewLocality = view.findViewById(R.id.reviewLocality);
            Log.d("Review_AdaptingChk", "Check view :" + view);
            view.setTag(vh);
        } else {
            vh = (ShopDetail_Main_Review_LV_ViewHolder) view.getTag();
        }

        Log.d("REVIEW_VIEWING", "Adaptr setData(Instance) :" + setData);
        Log.d("REVIEW_VIEWING", "Adapter ArrayList Adater " + position + "번 :" + data.get(position));

        if (!setData.titleImage.equals("")) {
            Glide.with(context).load(setData.titleImage).into(vh.reviewUserImage);

        } else {
            Glide.with(context).load("http://222.122.203.55/realreview/signup/profiledefault/default_food.png").into(vh.reviewUserImage);

        }


        vh.reviewRegdate.setMarkdownText("{fa-clock-o} " + setData.regdate);
        vh.reviewUserId.setText(setData.getNick());
        vh.reviewReviewCount.setText(setData.reviewCnt);
        vh.reviewUserFollower.setText(setData.getFollowerCnt());
        vh.reviewImageCount.setText(setData.getImageCnt());
        vh.reviewText.setText(setData.reviewText);
        vh.reviewRating.setRating(setData.fRating);
        vh.reviewUsefulBtn.setMarkdownText("{ty_lightbulb} 유용해요(" + setData.getUsefulCount() + ")");
        vh.reviewGoodBtn.setMarkdownText("{md_thumb_up} 멋있어요 (" + setData.getGoodCount()+ ")");
        vh.reviewCoolBtn.setMarkdownText("{fa_meh_o} 냉정해요(" + setData.getCoolCount()+ ")");

        if (setData.isUseful_selectable()) vh.reviewUsefulBtn.setSelected(true);
        else vh.reviewUsefulBtn.setSelected(false);

        if (setData.isGood_selectable()) vh.reviewGoodBtn.setSelected(true);
        else vh.reviewGoodBtn.setSelected(false);

        if (setData.isCool_selectable()) vh.reviewCoolBtn.setSelected(true);
        else vh.reviewCoolBtn.setSelected(false);


        if (setData.locality.equals("1")) {
            vh.reviewLocality.setVisibility(View.VISIBLE);
            vh.reviewLocality.setBadgeText("Locality");
        } else {
            vh.reviewLocality.setVisibility(View.GONE);
        }
        if (setData.nearby.equals("1")) {
            vh.reviewCertify.setVisibility(View.VISIBLE);
            vh.reviewCertify.setBadgeText("NearBy");
        } else {
            vh.reviewCertify.setVisibility(View.GONE);
        }

        Log.d("REVIEW_DELETE", "getView");

        /**
         * Delete Review -> Divide Function
         */
        /*

        if (checkUser.equals(setData.getNick())) {
            // TODO - Setting onClickListener for User Layoutw
            vh.reviewLayoutForLoginUser.setVisibility(View.VISIBLE);

            vh.reviewDelete.setOnClickListener(new View.OnClickListener() {
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
                                    ShopDetail_Main_Review_LV_Set dataSet = data.get(fPosition);
                                    String aShopID    = ShopDetail_Main.SHOP_ID;
                                    String aUserID    = dataSet.getUserId();
                                    String aIDX       = dataSet.getIdx();

                                    Void conn;
                                    try {
                                        conn = new AsyncReviewDelete(aShopID, aUserID, aIDX, mContext).execute().get(10000,TimeUnit.MILLISECONDS);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (TimeoutException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("REVIEW_DELETE", "fPosition :" + fPosition );

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
            vh.reviewLayoutForLoginUser.setVisibility(View.GONE);
        }

*/

        vh.reviewLayoutForLoginUser.setVisibility(View.GONE);

        vh.reviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        vh.reviewUsefulBtn.setOnCheckedChangedListener(new BootstrapButton.OnCheckedChangedListener() {
            @Override
            public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {

                if (isChecked) {
                    Log.d("Useful_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_Review_LV_Set data1 = data.get(fPosition);
                    String sDataUseful = data1.getUsefulCount();
                    int iDataUseful = Integer.parseInt(sDataUseful);
                    Log.d("Useful_OnCheckedChanged", "ENTER, AllStar");
                    iDataUseful++;
                    data1.setUsefulCount(String.valueOf(iDataUseful));
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                    String sDataUserID = pref.getSharedData("isLogged_id");
                    String sDataShopID = ShopDetail_Main.SHOP_ID;
                    String sDataNick   = pref.getSharedData("isLogged_nick");
                    String sDataIdx    = data1.getIdx();
                    Log.d("Useful_OnCheckedChanged", "sDataUserID :" + sDataUserID );
                    Log.d("Useful_OnCheckedChanged", "sDataShopID :" + sDataShopID );
                    Log.d("Useful_OnCheckedChanged", "sDataNick   :" + sDataNick   );
                    Log.d("Useful_OnCheckedChanged", "sDataIdx    :" + sDataIdx    );
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
                    ShopDetail_Main_Review_LV_Set data1 = data.get(fPosition);
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


        vh.reviewGoodBtn.setOnCheckedChangedListener(new BootstrapButton.OnCheckedChangedListener() {
            @Override
            public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {

                if (isChecked) {
                    Log.d("reviewGoodBtn_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_Review_LV_Set data1 = data.get(fPosition);
                    String sDataGood = data1.getGoodCount();
                    int iDatagood = Integer.parseInt(sDataGood);
                    Log.d("reviewGoodBtn_OnCheckedChanged", "ENTER, AllStar");
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
                    ShopDetail_Main_Review_LV_Set data1 = data.get(fPosition);
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



        vh.reviewCoolBtn.setOnCheckedChangedListener(new BootstrapButton.OnCheckedChangedListener() {
            @Override
            public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {

                if (isChecked) {
                    Log.d("reviewCoolBtn_OnCheckedChanged", "ENTER, AllStar");
                    ShopDetail_Main_Review_LV_Set data1 = data.get(fPosition);
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
                    ShopDetail_Main_Review_LV_Set data1 = data.get(fPosition);
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


        return view;
    }



    public void addItem(
            String idx,
            String titleImage,
            String followerCnt,
            String reviewCnt,
            String imageCnt,
            String reviewText,
            String regdate,
            String userId,
            String rating,
            String nick,
            float fRating,
            String profileImageURL,
            String locality,
            String nearby,
            String countCool,
            String countGood,
            String countUseful,
            boolean selectableUseful,
            boolean selectableGood,
            boolean selectableCool

    ) {
        ShopDetail_Main_Review_LV_Set addSetData = new ShopDetail_Main_Review_LV_Set();

        addSetData.idx = idx;
        addSetData.titleImage = titleImage;
        addSetData.followerCnt = followerCnt;
        addSetData.reviewCnt = reviewCnt;
        addSetData.imageCnt = imageCnt;
        addSetData.reviewText = reviewText;
        addSetData.regdate = regdate;
        addSetData.userId = userId;
        addSetData.rating = rating;
        addSetData.nick = nick;
        addSetData.fRating = fRating;
        addSetData.titleImage = profileImageURL;
        addSetData.locality = locality;
        addSetData.nearby = nearby;
        addSetData.coolCount = countCool;
        addSetData.goodCount = countGood;
        addSetData.usefulCount = countUseful;
        addSetData.useful_selectable = selectableUseful;
        addSetData.good_selectable = selectableGood;
        addSetData.cool_selectable = selectableCool;


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

    public void clearItem() {

        this.data.clear();

    }

}
