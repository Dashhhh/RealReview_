package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.activity.ShopDetail_PhotoView;

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-24.
 */

public class ShopDetail_Main_RV_Photo_Adapter extends RecyclerView.Adapter<ShopDetail_Main_RV_Photo_Viewholder> {
    LayoutInflater inflater = null;
    String imageUrl;
    Context mContext;
    ArrayList<ShopDetail_Main_RV_Photo_Set> data;

    public ShopDetail_Main_RV_Photo_Adapter(Context mContext, ArrayList<ShopDetail_Main_RV_Photo_Set> data) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    @Override
    public ShopDetail_Main_RV_Photo_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("RecyclerView(Image)", "onCre,ateViewHolder - Enter");
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_shop_detail__main_photo_recycler, parent, false);
        return new ShopDetail_Main_RV_Photo_Viewholder(v);
    }

    @Override
    public void onBindViewHolder(ShopDetail_Main_RV_Photo_Viewholder holder, int position) {
        Log.d("RecyclerView(Image)", "onBindViewHolder - Enter");

        final int fPosition = position;
        final ShopDetail_Main_RV_Photo_Set setUrl = data.get(position);
        Log.d("RecyclerView(Image)", "data size :" + String.valueOf(data.size()));
        Log.d("RecyclerView(Image)", "data get position :" + String.valueOf(data.get(position)));
        Log.d("RecyclerView(Image)", "setUrl :" + setUrl);
        Log.d("RecyclerView(Image)", "setUrl :" + setUrl.imageUrl);
        Glide.with(mContext).load(setUrl.imageUrl).into(holder.iv);

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imageURL = setUrl.imageUrl;
                Intent intent = new Intent(mContext, ShopDetail_PhotoView.class);
                intent.putExtra("imageURL", imageURL);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("RecyclerView(Image)", "getItemCount - Enter");

        return data.size();
    }


    public void addItem(String imageUrl) {
        Log.d("RecyclerView(Image)", "addItem - Enter");
        ShopDetail_Main_RV_Photo_Set set = new ShopDetail_Main_RV_Photo_Set(imageUrl);
        set.imageUrl= imageUrl;

        this.data.add(0, set);
        for (int i = 0; i < this.data.size(); i++) {
            String checkData = String.valueOf(this.data.get(i));
            Log.d("RecyclerView(Image)", "addItem Value :"+ imageUrl );
            Log.d("RecyclerView(Image)", "addItem - set checkData :" +checkData);
            Log.d("RecyclerView(Image)", "addItem - set.imageUrl Value :" +set.imageUrl);

        }
    }

    public void noti () {
        notifyDataSetChanged();
    }

    public void clearItem() {
        this.data.clear();
    }
}
