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






//        Log.d("RecyclerView(Image)", "onBindViewHolder - Enter");
//
//        final int fPosition = position;
//        final MainMe_MyFeed_Review_Set setUrl = data.get(position);
//        Log.d("RecyclerView(Image)", "data size :" + String.valueOf(data.size()));
//        Log.d("RecyclerView(Image)", "data get position :" + String.valueOf(data.get(position)));
//        Log.d("RecyclerView(Image)", "setUrl :" + setUrl);
//        Log.d("RecyclerView(Image)", "setUrl :" + setUrl.imageUrl);
//        Glide.with(mContext).load(setUrl.imageUrl).into(holder.iv);
//
//        holder.iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String imageURL = setUrl.imageUrl;
//                Intent intent = new Intent(mContext, ShopDetail_PhotoView.class);
//                intent.putExtra("imageURL", imageURL);
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
