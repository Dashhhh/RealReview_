package com.teamnova.ej.realreview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ej on 2017-10-18.
 */

public class ShopDetail_Main_Adapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();


    public ShopDetail_Main_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Log.d("ViewPager","Adapter - getItem() - Enter");

        switch (position) {

            case 1: {

            }


            case 2: {
                
            }

        }


        return ShopDetail_Viewpager_Fragment.create(position);

    }


    @Override
    public int getCount() {
        Log.d("ViewPager","Adapter - getCoutn() - Enter");

        return fragmentArrayList == null ? 0:fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentArrayList.add(fragment);
        notifyDataSetChanged();
    }

}