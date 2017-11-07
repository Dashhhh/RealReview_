package com.teamnova.ej.realreview.activity;

import android.graphics.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

public class ShopDetail_Overview_Map extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap mMap;
    private String shopLng;
    private String shopLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__overview__map);

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        String tag = pref.getSharedData("TAG");
        Log.d("DIRECTION_SHOP", "tag First :" + tag);
         shopLat = pref.getSharedData("LAT" + tag);
         shopLng = pref.getSharedData("LNG" + tag);
        Log.d("DIRECTION_SHOP", "tag Second :" + tag);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.overviewMap);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        double lat = Double.parseDouble(shopLat);
        double lng = Double.parseDouble(shopLng);
        LatLng ll = new LatLng(lat, lng);



        mMap.addMarker(new MarkerOptions().position(ll));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 18));

    }
}
