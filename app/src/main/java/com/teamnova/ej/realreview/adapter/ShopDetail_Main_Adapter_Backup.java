package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-18.
 */

public class ShopDetail_Main_Adapter_Backup extends PagerAdapter{

    Context mContext;
    Bitmap vBitmap;
    BitmapFactory.Options options;
    LayoutInflater inflater;
    public ArrayList<ShopAdd3_SetData> shopData = new ArrayList<>();
    public ArrayList<Integer> integerArrayList;


    public ShopDetail_Main_Adapter_Backup() {
    }

    public ShopDetail_Main_Adapter_Backup(Context mContext) {
        super();
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        integerArrayList = new ArrayList<>();

    }
    /**
     * �������� �������� �߰��ϴ� �޼ҵ�
     *
     * @param type - ������ Ÿ��
     */
    public void addItem(int type) {
        integerArrayList.add(type);                //������ ��Ͽ� �߰�
        notifyDataSetChanged();        //�ƴ��Ϳ� ������ ����Ǿ��ٰ� �˸�. �˾Ƽ� ���ΰ�ħ
    }

    @Override
    public int getCount() {
        return integerArrayList == null? 0:integerArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d("ViewPager","isViewFromObject()");
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("ViewPager","instantiateItem()");

 /*       Context context = container.getContext();
        ShopDetail_Main_Adapter_Viewholder vh = new ShopDetail_Main_Adapter_Viewholder();
        View v = null;

        if(v != null) {
            vh = (ShopDetail_Main_Adapter_Viewholder) v.getTag();
        } else {
            v = inflater.inflate(R.layout.activity_shop_detail__main_top_viewpager, container, false);
            vh.imageView = v.findViewById(R.id.shopDetailViewPagerImage);
            vh.textView = v.findViewById(R.id.shopDetailViewPagerText);
            v.setTag(vh);
        }

        String url = integerArrayList.get(position);
        Log.d("ViewPager","url Check :"+url);
        int arSize = getCount();
        Glide.with(mContext).load(url).into(vh.imageView);
        vh.textView.setText(getItemPosition(v)+" of "+arSize);
        container.addView(v);
*/

        View v = ShopDetail_Main_Util.getView(integerArrayList.get(position),mContext);
        container.addView(v);

        return v;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d("ViewPager","getItemPosition()");
        return super.getItemPosition(object);

    }


}
