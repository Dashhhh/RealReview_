package com.teamnova.ej.realreview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

        switch (position) {

            case 1: {

            }


            case 2: {
                
            }

        }


        return null;
    }


    @Override
    public int getCount() {
        return fragmentArrayList == null ? 0:fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentArrayList.add(fragment);
        notifyDataSetChanged();
    }

}