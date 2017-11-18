package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.teamnova.ej.realreview.R;

public class ShopDetail_PhotoView extends AppCompatActivity {

    private PhotoView photoView;
    private String imageURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__photo_view);


        Bundle bundle = getIntent().getExtras();
        imageURL = bundle.getString("imageURL");
        init();

        Log.d("ShopDetail_PhotoView", "imageURL :" + imageURL);
    }

    private void init() {
        photoView = (PhotoView) findViewById(R.id.shopDetailPhotoView);
        Glide.with(this).load(imageURL).into(photoView);
    }

}
