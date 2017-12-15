/*
package com.teamnova.ej.realreview.not;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.activity.Main;
import com.teamnova.ej.realreview.activity.ShopDetail_Main;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.teamnova.ej.realreview.activity.Main.LOCATION_FAR_LEFT_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_FAR_LEFT_LNG;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_NEAR_RIGHT_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_NEAR_RIGHT_LNG;
import static com.teamnova.ej.realreview.activity.Main.MY_POSITION_LAT;
import static com.teamnova.ej.realreview.activity.Main.MY_POSITION_LNG;

*/
/**
 * Created by ej on 2017-10-12.
 *//*


public class Z_NOTUSED_Main_Search extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback, LocationListener {

    Context mContext;
    GoogleMap mMap;
    private FloatingSearchView mSearchView;
    String blank = " ";


    private double myPosition_lat;
    private double myPosition_lng;
    private String resultNearRightLng;
    private String resultNearRightLat;
    private String resultFarLeftLat;
    private String resultFarLeftLng;
    ArrayList<String> fixShopDataList = new ArrayList<>();
    ArrayList<String> keyShopDataList = new ArrayList<>();
    ArrayList<String> valueShopDataList = new ArrayList<>();
    private JSONObject item2;


    public static String MAIN_SEARCH_LOCATION_FAR_LEFT_LAT;
    public static String MAIN_SEARCH_LOCATION_FAR_LEFT_LNG;
    public static String MAIN_SEARCH_LOCATION_NEAR_RIGHT_LAT;
    public static String MAIN_SEARCH_LOCATION_NEAR_RIGHT_LNG;
    public static String MAIN_SEARCH_SEARCH_POSITION_LAT;
    public static String MAIN_SEARCH_SEARCh_POSITION_LNG;


    public Z_NOTUSED_Main_Search() {
    }

    public Z_NOTUSED_Main_Search(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

*/
/*
        SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(mContext);
        String locationFlag = preferenceUtil.getSharedData("LOCATION_FLAG");

        if (locationFlag.equals("TRUE")) {
            navigation.setSelectedItemId(R.id.navigation_nearby);
            preferenceUtil.setSharedData("LOCATION_FLAG", "FALSE");
        }
*//*


        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        String cameraPosition = String.valueOf(mMap.getCameraPosition());
        Log.d("onMapReady", "cameraPosition :" + cameraPosition);

        LatLng myPosition = new LatLng(MY_POSITION_LAT, MY_POSITION_LNG);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));


//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        String sFarLeft = String.valueOf(mMap.getProjection().getVisibleRegion().farLeft);
        String sNearRight = String.valueOf(mMap.getProjection().getVisibleRegion().nearRight);

        String[] splitFarLeft = sFarLeft.split("\\(", 0);
        String[] splitNearRight = sNearRight.split("\\(", 0);

        String[] split2FarLeft = splitFarLeft[1].split(",", 0);
        String[] split2NearRight = splitNearRight[1].split(",", 0);

        String[] split3FarLeft = split2FarLeft[1].split("\\)", 0);
        String[] split3NearRight = split2NearRight[1].split("\\)", 0);


        resultFarLeftLat = split2FarLeft[0];
        resultFarLeftLng = split3FarLeft[0];
        resultNearRightLat = split2NearRight[0];
        resultNearRightLng = split3NearRight[0];

        Log.d("MainSearch_onMapReady", "SPLIT CHECK LOCATION FAR - NEAR, split2FarLeft " + split2FarLeft[0]);
        Log.d("MainSearch_onMapReady", "SPLIT CHECK LOCATION FAR - NEAR, split2FarLeft " + split3FarLeft[0]);
        Log.d("MainSearch_onMapReady", "SPLIT CHECK LOCATION FAR - NEAR, split2NearRight " + split2NearRight[0]);
        Log.d("MainSearch_onMapReady", "SPLIT CHECK LOCATION FAR - NEAR, split2NearRight " + split3NearRight[0]);
        Log.d("MainSearch_onMapReady", "FarLeft:" + sFarLeft);
        Log.d("MainSearch_onMapReady", "NearRight:" + sNearRight);

        LOCATION_FAR_LEFT_LAT = Double.parseDouble(split2FarLeft[0]);
        LOCATION_FAR_LEFT_LNG = Double.parseDouble(split3FarLeft[0]);
        LOCATION_NEAR_RIGHT_LAT = Double.parseDouble(split2NearRight[0]);
        LOCATION_NEAR_RIGHT_LNG = Double.parseDouble(split3NearRight[0]);


        String url = "http://222.122.203.55/realreview/Nearby/latlng.php?";
        String urlMerge = url + "lat_start=" + resultNearRightLat + "&lat_end=" + resultFarLeftLat + "&lng_start=" + resultFarLeftLng + "&lng_end=" + resultNearRightLng;
        ProgressWheel progressDialog = new ProgressWheel(mContext);
        StringBuilder conn = null;
        try {
            conn = new Main.AsyncMainNearbyLatLngReceive(urlMerge, mContext).execute().get(5000, TimeUnit.MILLISECONDS);
            JSONObject castingJO = new JSONObject(String.valueOf(conn));
            Log.d("MainSearch_onMapReady", "1 - castingJO :" + castingJO);
            JSONArray fixJSON = castingJO.getJSONArray("realreview");
            Log.d("MainSearch_onMapReady", "2 - fixJSON :" + fixJSON);


            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < fixJSON.length(); i++) {
                JSONObject item = fixJSON.getJSONObject(i);
                fixShopDataList.add(String.valueOf(item));
                Log.d("MainSearch_onMapReady", "JSON_CHECK, 3 - item :" + i + "번 :" + item);

                item2 = new JSONObject(fixShopDataList.get(i));
                String sLat = item2.getString("latitude");
                String sLng = item2.getString("longtitude");
                double dLat = Double.parseDouble(sLat);
                double dLng = Double.parseDouble(sLng);
                LatLng latLng = new LatLng(dLat, dLng);
                markerOptions.position(latLng)
                        .title(item2.getString("shopName"))
                        .snippet("SHOP OPEN : " + item2.getString("shopOpen") + "\nSHOP CLOSE : " + item2.getString("shopClose"));
                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(i);
                String markerTag = String.valueOf(marker.getTag());
                String a0 = item2.getString("id");
                String a1 = item2.getString("shopName");
                String a2 = item2.getString("address");
                String a3 = item2.getString("latitude");
                String a4 = item2.getString("longtitude");
                String a5 = item2.getString("viewportSouthWestLat");
                String a6 = item2.getString("viewportSouthWestLng");
                String a7 = item2.getString("viewportNorthEastLat");
                String a8 = item2.getString("viewportNorthEastLng");
                String a9 = item2.getString("shopOpen");
                String a10 = item2.getString("shopClose");
                String a11 = item2.getString("shopTheme1");
                String a12 = item2.getString("shopTheme2");
                String a13 = item2.getString("shopTheme3");
                String a14 = item2.getString("shopTheme4");
                String a15 = item2.getString("shopTheme5");
                String a16 = item2.getString("callNumber");
                String websiteNullCheck = item2.getString("webSite");

                SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);

                pref.setSharedData("ID" + markerTag, item2.getString("id"));
                pref.setSharedData("TITLE" + markerTag, item2.getString("shopName"));
                pref.setSharedData("ADDRESS" + markerTag, item2.getString("address"));
                pref.setSharedData("LAT" + markerTag, item2.getString("latitude"));
                pref.setSharedData("LNG" + markerTag, item2.getString("longtitude"));
                pref.setSharedData("V_SW_LAT" + markerTag, item2.getString("viewportSouthWestLat"));
                pref.setSharedData("V_SW_LNG" + markerTag, item2.getString("viewportSouthWestLng"));
                pref.setSharedData("V_NE_LAT" + markerTag, item2.getString("viewportNorthEastLat"));
                pref.setSharedData("V_NE_LNG" + markerTag, item2.getString("viewportNorthEastLng"));
                pref.setSharedData("OPEN" + markerTag, item2.getString("shopOpen"));
                pref.setSharedData("CLOSE" + markerTag, item2.getString("shopClose"));
                pref.setSharedData("MARKERTAG" + markerTag, item2.getString("shopClose"));
                pref.setSharedData("THEME1" + markerTag, item2.getString("shopTheme1"));
                pref.setSharedData("THEME2" + markerTag, item2.getString("shopTheme2"));
                pref.setSharedData("THEME3" + markerTag, item2.getString("shopTheme3"));
                pref.setSharedData("THEME4" + markerTag, item2.getString("shopTheme4"));
                pref.setSharedData("THEME5" + markerTag, item2.getString("shopTheme5"));
                pref.setSharedData("CALL" + markerTag, item2.getString("callNumber"));
                pref.setSharedData("TAG" + markerTag, String.valueOf(i));
                if (item2.getString("webSite").isEmpty()) {
                    pref.setSharedData("WEB" + markerTag, "");
                } else {
                    pref.setSharedData("WEB" + markerTag, item2.getString("webSite"));
                }
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            String markerId = String.valueOf(marker.getTag());

                            Log.d("MainSearch_onMapReady", "marker.getTag() :" + markerId);
                            SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
                            pref.setSharedData("TAG", markerId);
                            Intent intent = new Intent(mContext, ShopDetail_Main.class);
                            intent.putExtra("TAG", String.valueOf(marker.getTag()));
                            startActivity(intent);
                        }
                    });

//                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(Marker marker) {
//
//                        Dialog_Default dial = new Dialog_Default(Main.mContext);
//                        dial.call("MARKER CLICK", "TODO : SHOP DETAIL ACTIVITY");
//
//
//                        return false;
//                    }
//                });


            }

            Log.d("Main, onMapReady", "connLength : " + fixJSON.length());


        } catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
            e.printStackTrace();
            Log.d("Main, onMapReady", "catch");

        }


    }


    @Override
    public void onLocationChanged(Location location) {
        Log.i("FragmentTest", "Enter - onLocationChanged");

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i("FragmentTest", "Enter - onStatusChanged");

    }

    @Override
    public void onProviderEnabled(String s) {
        Log.i("FragmentTest", "Enter - onProviderEnabled");

    }

    @Override
    public void onProviderDisabled(String s) {
        Log.i("FragmentTest", "Enter - onProviderDisabled");

    }

    @Override
    public void onClick(View view) {
        Log.i("FragmentTest", "Enter - onClick");

    }


}
*/
