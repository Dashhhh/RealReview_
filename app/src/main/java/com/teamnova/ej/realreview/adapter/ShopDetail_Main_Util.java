package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

/**
 * Created by ej on 2017-10-18.
 */

public class ShopDetail_Main_Util {

    public static final int ADD_VIEW = 0;
    RelativeLayout RL;


    /**
     * �� �������� �߰��� �並 �����Ͽ� ��ȯ�Ѵ�
     * @param type - �߰��� �� Ÿ��
     * @param mContext - �� ������ ����� Context
     * @return �� ��ü
     */

    public static View getView(int type, Context mContext) {
//        if(type == ADD_VIEW){
//            return getImageView(mContext);
//        }
           return getImageView(mContext);
    }

    /**
     * �ؽ�Ʈ �並 �����Ͽ� ��ȯ�Ѵ�
     * @param mContext - �� ������ ����� Context
     * @return �ؽ�Ʈ ��
     */

    private static ImageView getImageView(Context mContext) {


        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        ImageView iv = new ImageView(mContext);
        Glide.with(mContext).load(pref.getSharedData("VIEWPAGER_TEST3")).into(iv);


        /*
        RelativeLayout rl = new RelativeLayout(mContext);
        TextView tv = new TextView(mContext);
        tv.setText("123123");
        tv.setBackgroundColor(000000);


        RelativeLayout.LayoutParams imageRule = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        iv.setLayoutParams(imageRule);
        iv.addView(iv);

        tv.setTextColor(Color.parseColor("#104616"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM);
        tv.setLayoutParams(layoutParams);
        rl.addView(tv);
*/


        return iv;
    }
}
