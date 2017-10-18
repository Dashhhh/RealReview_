package com.teamnova.ej.realreview.adapter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by ej on 2017-10-18.
 */

public class ShopDetail_Viewpager_Fragment extends Fragment {

    private int mPageNumber;

    public ShopDetail_Viewpager_Fragment() {
    }


    public static ShopDetail_Viewpager_Fragment create(int pageNumber) {
        ShopDetail_Viewpager_Fragment fragment = new ShopDetail_Viewpager_Fragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageNumber = getArguments().getInt("page");



    }


}
