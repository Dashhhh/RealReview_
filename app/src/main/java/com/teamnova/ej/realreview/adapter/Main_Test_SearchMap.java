package com.teamnova.ej.realreview.adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ej on 2017-10-12.
 */

public class Main_Test_SearchMap extends Fragment implements OnMapReadyCallback{

    Context mContext;
    GoogleMap mMap;

    public Main_Test_SearchMap(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("FragmentTest","Enter - onCreate");


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FragmentTest","Enter - onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("FragmentTest","Enter - onStop");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(16);
        Log.i("FragmentTest","Enter - Main Test");
        LatLng myPostision = new LatLng(36.48408310967865, 126.97256952524185);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPostision));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

    }
}
