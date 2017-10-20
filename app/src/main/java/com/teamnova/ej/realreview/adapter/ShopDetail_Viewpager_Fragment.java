package com.teamnova.ej.realreview.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-10-18.
 */

public class ShopDetail_Viewpager_Fragment extends android.support.v4.app.Fragment {

    private int mPageNumber;
    private int image;
    private Bitmap bitmapImage;
    BitmapFactory.Options options;


    public ShopDetail_Viewpager_Fragment() {
    }


    public static ShopDetail_Viewpager_Fragment create(int pageNumber) {
        Log.d("ViewPager","Fragment - ShopDetail_Viewpager_Fragment create() - Enter");
        ShopDetail_Viewpager_Fragment fragment = new ShopDetail_Viewpager_Fragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ViewPager","Fragment - onCreate() - Enter");

        mPageNumber = getArguments().getInt("page");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.d("ViewPager","Fragment - onCreateView() - Enter");

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_shop_detail__main_top_viewpager, container, false);

//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page, container, false);
//        ((TextView) rootView.findViewById(R.id.number)).setText(mPageNumber + "");
        return rootView;
/*

        options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        bitmapImage = BitmapFactory.decodeResource(rootView.getResources(), image, options);
        ImageView imageView = rootView.findViewById(R.id.shopDetailViewPagerImage);
        Glide.with(container).load("http://222.122.203.55/realreview/hard/test6.jpg").into(imageView);

//        imageView.setImageBitmap(bitmapImage);
        return rootView;
*/


//        return super.onCreateView(inflater, container, savedInstanceState);


    }


}
