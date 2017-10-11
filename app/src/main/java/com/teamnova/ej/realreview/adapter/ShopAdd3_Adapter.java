package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;

import java.util.ArrayList;

/**
 * Created by ej on 2017-09-28.
 */

public class ShopAdd3_Adapter extends BaseAdapter {
    String TestVAR;
    String TestVAR2;


    public ArrayList<ShopAdd3_SetData> data = new ArrayList<>();
    LayoutInflater inflater = null;
    int lastPosition = -1;

    public ShopAdd3_Adapter(ArrayList<ShopAdd3_SetData> data, Context context) {
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (data != null) ? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int position) {
        return (data != null && (0 <= position && position < data.size()) ? position : 0);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Context context = viewGroup.getContext();
        ShopAdd3_Viewholder vh = new ShopAdd3_Viewholder();

        if(view == null) {

            view = inflater.inflate(R.layout.activity_shop_add3_inflate, viewGroup, false);
            vh.add3ET_Title = view.findViewById(R.id.shopAdd3Title);
            vh.add3ET_Description = view.findViewById(R.id.shopAdd3Description);
            vh.add3ET_Tag = view.findViewById(R.id.shopAdd3Tag);
            vh.add3ET_MenuImage = view.findViewById(R.id.shopAdd3LvImage);
            view.setTag(vh);
        } else {
            vh = (ShopAdd3_Viewholder) view.getTag();
        }

        ShopAdd3_SetData setData = data.get(i);
        if(setData.getShopAdd3Title().isEmpty()){
            vh.add3ET_Title.setText("");
        } else {
            vh.add3ET_Title.setText(setData.getShopAdd3Title());
        }
        if(setData.getShopAdd3Description().isEmpty()){
            vh.add3ET_Description.setText("");
        } else {
            vh.add3ET_Description.setText(setData.getShopAdd3Description());
        }
        if(setData.getShopAdd3Tag().isEmpty()){
            vh.add3ET_Tag.setText("");
        }else{
            vh.add3ET_Tag.setText(setData.getShopAdd3Tag());
        }
        if(setData.getShopAdd3LvImage() == null) {
            Glide.with(context).load("http://222.122.203.55/realreview/signup/profiledefault/default_food.png").into(vh.add3ET_MenuImage);
        } else {
            Glide.with(context).load(setData.getShopAdd3LvImage()).into(vh.add3ET_MenuImage);
        }


        return view;
    }

    public void setShopadd3ItemDatas(ArrayList<ShopAdd3_SetData> data) {
        this.data = data;
        this.notifyDataSetChanged();

    }


}

//TODO - AddItem Method
