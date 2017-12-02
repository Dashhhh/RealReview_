package com.teamnova.ej.realreview.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ej on 2017-10-12.
 */

public class Main_Search extends Fragment implements OnMapReadyCallback {

    Context mContext;
    GoogleMap mMap;
    private FloatingSearchView mSearchView;
    String blank = " ";

    public Main_Search(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        Log.i("FragmentTest", "Enter - onCreateView" + blank);

        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("FragmentTest", "Enter - onCreate" + blank);


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FragmentTest", "Enter - onPause" + blank);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("FragmentTest", "Enter - onStop" + blank);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        Log.i("FragmentTest", "Enter - Main Test");
        LatLng myPostision = new LatLng(37.53364562988281, 126.83870697021484);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPostision));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

    }
}
