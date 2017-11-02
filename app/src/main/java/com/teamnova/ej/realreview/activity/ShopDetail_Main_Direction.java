package com.teamnova.ej.realreview.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.Asynctask.AsyncDirectionRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Main_Direction extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONObject directionRootJSONObject;
    private String polyline2;
    String start_locationStringLat;
    String start_locationStringLng;
    private String end_locationStringLat;
    private String end_locationStringLng;
    private String start_address;
    private String end_address;
    private String durationString;
    private String distanceString;
    private String departure_timeString;
    private String arrival_timeParse;
    private double bounds2NELat;
    private double bounds2NELng;
    private double bounds2SWLat;
    private double bounds2SWLng;
    private LatLngBounds latLngBounds;
    private LatLng boundsNE;
    private LatLng boundsSW;

    com.beardedhen.androidbootstrap.AwesomeTextView directionDepartureTime, directionArrivalTime, directionDuration, directionDistance, directionStartAddress, directionEndAddress;
    private boolean distanceFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TypefaceProvider.registerDefaultIconSets();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__main__direction);

        init();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

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


        try {
            directionRootJSONObject = new AsyncDirectionRequest(userLat, userLng, shopLat, shopLng, this).execute().get(10000, TimeUnit.MILLISECONDS);
            directionParseBounds();
            directionParse2();
            directionParseOverviewPolyline();
            directionVarCheck();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.directionMap);
        mapFragment.getMapAsync(this);

    }

    private void init() {
        directionDepartureTime = findViewById(R.id.directionDepartureTime);
        directionArrivalTime = findViewById(R.id.directionArrivalTime);
        directionDuration = findViewById(R.id.directionDuration);
        directionDistance = findViewById(R.id.directionDistance);
        directionStartAddress = findViewById(R.id.directionStartAddress);
        directionEndAddress = findViewById(R.id.directionEndAddress);

    }

    private void directionParseBounds() throws JSONException {
        JSONArray aaJSONObject = directionRootJSONObject.getJSONArray("routes");
        Log.d("directionParseBounds", "전송결과 : " + directionRootJSONObject);
        Log.d("directionParseBounds", "전송결과 aaJSONObject: " + aaJSONObject);
        JSONObject aaaJSONArray = aaJSONObject.getJSONObject(0);
        Log.d("directionParseBounds", "전송결과 aaaJSONArray : " + aaaJSONArray);
        JSONObject bounds = aaaJSONArray.getJSONObject("bounds");
        Log.d("directionParseBounds", "전송결과 bounds : " + bounds);

        JSONObject boundsNE = bounds.getJSONObject("northeast");
        Log.d("directionParseBounds", "전송결과 boundsNE : " + boundsNE);
        JSONObject boundsSW = bounds.getJSONObject("southwest");
        Log.d("directionParseBounds", "전송결과 boundsSW  : " + boundsSW);
        bounds2NELat = boundsNE.getDouble("lat");
        bounds2NELng = boundsNE.getDouble("lng");
        bounds2SWLat = boundsSW.getDouble("lat");
        bounds2SWLng = boundsSW.getDouble("lng");
        Log.d("directionParseBounds", "전송결과 bounds2NE: " + bounds2NELat);
        Log.d("directionParseBounds", "전송결과 bounds2NE: " + bounds2NELng);
        Log.d("directionParseBounds", "전송결과 bounds2SW : " + bounds2SWLat);
        Log.d("directionParseBounds", "전송결과 bounds2SW : " + bounds2SWLng);
//
//        bounds2SWLat -= 0.05;
//        bounds2SWLng += 0.05;
//        bounds2NELat += 0.05;
//        bounds2NELng -= 0.05;

    }

    private void directionParse2() throws JSONException {

        JSONArray aaJSONObject = directionRootJSONObject.getJSONArray("routes");
        Log.d("directionParse2", "전송결과 : " + directionRootJSONObject);
        Log.d("directionParse2", "전송결과 aaJSONObject: " + aaJSONObject);

        JSONObject aaaJSONArray = aaJSONObject.getJSONObject(0);
        JSONArray legs1 = aaaJSONArray.getJSONArray("legs");
        JSONObject legs2 = legs1.getJSONObject(0);

        JSONObject arrival_time = legs2.getJSONObject("arrival_time");
        arrival_timeParse = arrival_time.getString("text");
        Log.d("directionParse2", "전송결과 aaaJSONArray     : " + aaaJSONArray);
        Log.d("directionParse2", "전송결과 legs1            : " + legs1);
        Log.d("directionParse2", "전송결과 legs2            : " + legs2);
        Log.d("directionParse2", "전송결과 arrival_time     : " + arrival_time);
        Log.d("directionParse2", "전송결과 arrival_timeParse: " + arrival_timeParse);

        JSONObject departure_time = legs2.getJSONObject("departure_time");
        departure_timeString = departure_time.getString("text");
        Log.d("directionParse2", "전송결과  departure_time      : " + departure_time);
        Log.d("directionParse2", "전송결과  departure_timeString: " + departure_timeString);

        JSONObject distance = legs2.getJSONObject("distance");
        distanceString = distance.getString("text");
        Log.d("directionParse2", "전송결과  distance       : " + distance);
        Log.d("directionParse2", "전송결과  distanceString : " + distanceString);

        JSONObject duration = legs2.getJSONObject("duration");
        durationString = duration.getString("text");
        Log.d("directionParse2", "전송결과   duration       : " + duration);
        Log.d("directionParse2", "전송결과   durationString : " + durationString);

        end_address = legs2.getString("end_address");
        Log.d("directionParse2", "전송결과 end_address: " + end_address);

        start_address = legs2.getString("start_address");
        Log.d("directionParse2", "전송결과 start_address: " + start_address);

        JSONObject end_location = legs2.getJSONObject("end_location");
        end_locationStringLat = end_location.getString("lat");
        end_locationStringLng = end_location.getString("lng");
        Log.d("directionParse2", "전송결과  end_location         : " + end_location);
        Log.d("directionParse2", "전송결과  end_locationStringLat: " + end_locationStringLat);
        Log.d("directionParse2", "전송결과  end_locationStringLng: " + end_locationStringLng);

        JSONObject start_location = legs2.getJSONObject("start_location");
        start_locationStringLat = start_location.getString("lat");
        start_locationStringLng = start_location.getString("lng");
        Log.d("directionParse2", "전송결과 start_location         : " + start_location);
        Log.d("directionParse2", "전송결과 start_locationStringLat: " + start_locationStringLat);
        Log.d("directionParse2", "전송결과 start_locationStringLng: " + start_locationStringLng);

    }

    private void directionParseOverviewPolyline() throws JSONException {


        JSONArray aaJSONObject = directionRootJSONObject.getJSONArray("routes");
        Log.d("directionParseOverviewPolyline", "전송결과 : " + directionRootJSONObject);
        Log.d("directionParseOverviewPolyline", "전송결과 aaJSONObject: " + aaJSONObject);

        JSONObject aaaJSONArray = aaJSONObject.getJSONObject(0);
        JSONObject polyline = aaaJSONArray.getJSONObject("overview_polyline");
        polyline2 = polyline.getString("points");
        Log.d("directionParseOverviewPolyline", "전송결과  aaaJSONArray: " + aaaJSONArray);
        Log.d("directionParseOverviewPolyline", "전송결과  polyline    : " + polyline);
        Log.d("directionParseOverviewPolyline", "전송결과  polyline2   : " + polyline2);


    }

    private void directionVarCheck() throws JSONException {
        Log.d("directionVarCheck", "전송결과 start_locationStringLat: " + start_locationStringLat);
        Log.d("directionVarCheck", "전송결과 start_locationStringLng: " + start_locationStringLng);
        Log.d("directionVarCheck", "전송결과 end_locationStringLat : " + end_locationStringLat);
        Log.d("directionVarCheck", "전송결과 end_locationStringLng : " + end_locationStringLng);
        Log.d("directionVarCheck", "전송결과 start_address : " + start_address);
        Log.d("directionVarCheck", "전송결과 end_address : " + end_address);
        Log.d("directionVarCheck", "전송결과 durationString : " + durationString);
        Log.d("directionVarCheck", "전송결과 distanceString : " + distanceString);
        Log.d("directionVarCheck", "전송결과 departure_timeString : " + departure_timeString);
        Log.d("directionVarCheck", "전송결과 arrival_timeParse : " + arrival_timeParse);
        Log.d("directionVarCheck", "전송결과 bounds2NELat : " + bounds2NELat);
        Log.d("directionVarCheck", "전송결과 bounds2NELng : " + bounds2NELng);
        Log.d("directionVarCheck", "전송결과 bounds2SWLat : " + bounds2SWLat);
        Log.d("directionVarCheck", "전송결과 bounds2SWLng : " + bounds2SWLng);
        Log.d("directionVarCheck", "전송결과 polyline2   : " + polyline2);

        boundsNE = new LatLng(bounds2NELat, bounds2NELng);
        boundsSW = new LatLng(bounds2SWLat, bounds2SWLng);
        latLngBounds = new LatLngBounds(boundsSW, boundsNE);

        float[] distanceResult = new float[10];
        double distanceStartLat = Double.parseDouble(start_locationStringLat);
        double distanceStartLng = Double.parseDouble(start_locationStringLng);
        double distanceEndLat = Double.parseDouble(end_locationStringLat);
        double distanceEndLng = Double.parseDouble(end_locationStringLng);

        Log.d("directionVarCheck", "distanceStartLat : " + String.valueOf(distanceStartLat));
        Log.d("directionVarCheck", "distanceStartLng : " + String.valueOf(distanceStartLng));
        Log.d("directionVarCheck", "distanceEndLat   : " + String.valueOf(distanceEndLat));
        Log.d("directionVarCheck", "distanceEndLng   : " + String.valueOf(distanceEndLng));

        Location.distanceBetween(distanceStartLat,
                distanceStartLng,
                distanceEndLat,
                distanceEndLng, distanceResult);

        Log.d("directionVarCheck", "전송결과 distanceResult0 : " + String.valueOf(distanceResult[0]));
        Log.d("directionVarCheck", "전송결과 distanceResult1 : " + String.valueOf(distanceResult[1]));
        Log.d("directionVarCheck", "전송결과 distanceResult2 : " + String.valueOf(distanceResult[2]));


        directionDepartureTime.setText(departure_timeString);
        directionArrivalTime.setText(arrival_timeParse);
        directionDuration.setText(durationString);
        directionDistance.setText(distanceString);
        directionStartAddress.setText(start_address);
        directionEndAddress.setText(end_address);


        if (distanceResult[0] < 1000) {
            Dialog_Default dial = new Dialog_Default(this);
            dial.tempCall("Warning", "직선거리 1km 미만은 정상적인 정보가 제공 되지 않습니다. 양해 부탁 드립니다.");
            distanceFlag = false;
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
        for (; latLngBounds == null; ) {
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));


        List<LatLng> poly = PolyUtil.decode(polyline2);
        Log.d("onMapReady", " poly : " + poly);

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .addAll(poly)
                .width(5)
                .color(Color.RED));


        double startLat = Double.parseDouble(start_locationStringLat);
        double startLng = Double.parseDouble(start_locationStringLng);
        double endLat = Double.parseDouble(end_locationStringLat);
        double endLng = Double.parseDouble(end_locationStringLng);

        LatLng startLocationLL = new LatLng(startLat, startLng);
        LatLng endLocationLL = new LatLng(endLat, endLng);

        mMap.addMarker(new MarkerOptions().position(startLocationLL));
        mMap.addMarker(new MarkerOptions().position(endLocationLL));

/*
        LatLngBounds latLngBounds = new LatLngBounds(boundsSW, boundsNE);
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .positionFromBounds(latLngBounds);
*/

    }


}
