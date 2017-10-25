package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnova.ej.realreview.R;

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-26.
 */

public class ShopDetail_MainReview_RV_Theme_Adapter extends RecyclerView.Adapter<ShopDetail_MainReview_RV_Theme_Viewholder>{

    Context mContext;
    ArrayList<ShopDetail_MainReview_RV_Theme_Set> data;

    public ShopDetail_MainReview_RV_Theme_Adapter(Context mContext, ArrayList<ShopDetail_MainReview_RV_Theme_Set> data) {
        this.mContext = mContext;
        if(data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    @Override
    public ShopDetail_MainReview_RV_Theme_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("DETAIL_THEME", "onCreateViewHolder - Enter");

        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_shop_detail__main_theme_recycler, parent, false);

        return new ShopDetail_MainReview_RV_Theme_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(ShopDetail_MainReview_RV_Theme_Viewholder holder, int position) {
        Log.d("DETAIL_THEME", "onBindViewHolder - Enter");
        ShopDetail_MainReview_RV_Theme_Set setTheme = data.get(position);
        holder.shopDetailTopThemeText.setText(setTheme.getsTheme());
        Log.d("DETAIL_THEME","setTheme.getsTheme() :"+setTheme.getsTheme());

    }

    @Override
    public int getItemCount() {
        Log.d("DETAIL_THEME", "getItemCount - Enter");

        return (data.size() != 0) ? data.size() : 0;
    }

}
