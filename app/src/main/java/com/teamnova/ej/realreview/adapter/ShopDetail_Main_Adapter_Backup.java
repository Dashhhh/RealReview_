package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.activity.ShopDetail_Main;
import com.teamnova.ej.realreview.activity.ShopDetail_PhotoView;

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-18.
 */

public class ShopDetail_Main_Adapter_Backup extends PagerAdapter {

    Context mContext;
    Bitmap vBitmap;
    BitmapFactory.Options options;
    LayoutInflater inflater;
    public ArrayList<ShopAdd3_SetData> shopData = new ArrayList<>();
    public ArrayList<String> stringArrayList;
    public ArrayList<String> titleArray;
    public ArrayList<String> detailArray;


    public ShopDetail_Main_Adapter_Backup() {
    }

    public ShopDetail_Main_Adapter_Backup(Context mContext) {
        super();
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        stringArrayList = new ArrayList<>();
        titleArray = new ArrayList<>();
        detailArray = new ArrayList<>();

    }

    public void addItem(String url, String title, String detail) {
        stringArrayList.add(url);
        titleArray.add(title);
        detailArray.add(detail);

        //������ ��Ͽ� �߰�
        notifyDataSetChanged();        //�ƴ��Ϳ� ������ ����Ǿ��ٰ� �˸�. �˾Ƽ� ���ΰ�ħ
    }

    @Override
    public int getCount() {
        return stringArrayList == null ? 0 : stringArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d("ViewPager", "isViewFromObject()");
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("ViewPager", "instantiateItem()");

        final int fPosition = position;
        Context context = container.getContext();
        ShopDetail_Main_Adapter_Viewholder vh = new ShopDetail_Main_Adapter_Viewholder();
        View v = null;

        if (v != null) {
            vh = (ShopDetail_Main_Adapter_Viewholder) v.getTag();
        } else {
            v = inflater.inflate(R.layout.activity_shop_detail__main_top_viewpager, container, false);
            vh.imageView = v.findViewById(R.id.shopDetailViewPagerImage);
            vh.viewpagerTitleText = v.findViewById(R.id.viewpagerTitleText);
            vh.viewpagerDetailText = v.findViewById(R.id.viewpagerDetailText);
            v.setTag(vh);
        }

        String url = stringArrayList.get(position);
        Log.d("ViewPager", "url Check :" + url);
        int arSize = getCount();
        vh.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        vh.imageView.setScaleX(1.0f);
        vh.imageView.setScaleY(1.0f);
        Glide.with(mContext).
                load(url).
                into(vh.imageView);
        container.addView(v);
        vh.viewpagerTitleText.setText(titleArray.get(position));
        vh.viewpagerDetailText.setText(detailArray.get(position));


        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ShopDetail_PhotoView.class);

                intent.putExtra("imageURL",stringArrayList.get(fPosition));

                mContext.startActivity(intent);

            }
        });


        return v;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d("ViewPager", "getItemPosition()");
        return super.getItemPosition(object);

    }


}
