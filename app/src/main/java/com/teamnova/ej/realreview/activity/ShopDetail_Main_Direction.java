package com.teamnova.ej.realreview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.teamnova.ej.realreview.Asynctask.AsyncDirectionRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Main_Direction extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__main__direction);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.directionMap);
        mapFragment.getMapAsync(this);

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

        String userLat = pref.getSharedData("DIRECTION_USER_LAT");
        String userLng = pref.getSharedData("DIRECTION_USER_LNG");
        String tag = pref.getSharedData("TAG");
        Log.d("DIRECTION_SHOP", "tag First :" + tag);
        String shopLat = pref.getSharedData("LAT" + tag);
        String shopLng = pref.getSharedData("LNG" + tag);
        Log.d("DIRECTION_SHOP", "tag Second :" + tag);

        Log.d("DIRECTION_SHOP", "userLat :" + userLat);
        Log.d("DIRECTION_SHOP", "userLng :" + userLng);
        Log.d("DIRECTION_SHOP", "shopLng :" + shopLat);
        Log.d("DIRECTION_SHOP", "shopLng :" + shopLng);


        JSONObject aa;
        try {
            aa = new AsyncDirectionRequest(userLat, userLng, shopLat, shopLng, this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("DIRECTION", "전송결과 : " + aa);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }
}
