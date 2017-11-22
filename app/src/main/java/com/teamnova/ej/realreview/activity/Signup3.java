package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Signup3  extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener{

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int PLACE_PICKER_REQUEST = 1;
    MapView mapView;
    GoogleApiClient mGoogleApiClient;

    String TAG = "Signup3.class";
    String getId = "";
    String getAddress = "";
    String getName = "";
    String getPhoneNumber = "";
    String getWebsiteUri = "";
    String getLatLng = "";
    String getViewport = "";
    String getLocale = "";
    String getPlaceTypes = "";
    String getAttribution = "";
    int getPriceLevel = 0;
    float getRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
//            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }






    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Signup Detail Reset
        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        pref.setSharedData("SIGNUP_Lat", "");
        pref.setSharedData("SIGNUP_Lng", "");
        pref.setSharedData("SIGNUP_SW_Lat","");
        pref.setSharedData("SIGNUP_SW_Lng","");
        pref.setSharedData("SIGNUP_NE_Lat","");
        pref.setSharedData("SIGNUP_NE_Lng","");
        Log.e(TAG, "onDestroy - ENTER");

    }

    @Override
    protected void onStart() {
        super.onStart();


        Dialog_Default dial = new Dialog_Default(this);
        dial.tempCall("거주지 선택", "주로 있는 장소를 검색 후 선택 해 주세요\n참고) 살고있는 장소, 자주가는 장소");


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);

                List<Integer> placeTypes = place.getPlaceTypes();
                Iterator iterator = placeTypes.iterator();
                int i = 0;

                String strPlace = String.valueOf(place);
                String[] splitStrPlace = strPlace.split("=", 0);



                String aSplitStrPlace7 = splitStrPlace[7];
                String bSplitStrPlace7 = splitStrPlace[7];
                Log.e(TAG + "--- aSplitStrPlace7", aSplitStrPlace7);
                Log.e(TAG + "--- bSplitStrPlace7", bSplitStrPlace7);

                aSplitStrPlace7.lastIndexOf("(");
                String[] arraySplitStrPlace7 = aSplitStrPlace7.split(",", 0);
                Log.e(TAG + "1-1 > String[] arraySplitStrPlace7[0]", arraySplitStrPlace7[0]);
                Log.e(TAG + "2-1 > String[] arraySplitStrPlace7[1]", arraySplitStrPlace7[1]);

                arraySplitStrPlace7[0].indexOf("(");
                arraySplitStrPlace7[1].lastIndexOf(")");
                Log.e(TAG + "1-2 > String[] arraySplitStrPlace7[0]", arraySplitStrPlace7[0]);
                Log.e(TAG + "2-2 > String[] arraySplitStrPlace7[1]", arraySplitStrPlace7[1]);


                /**
                 * Split, Lat, Lng
                 */

                String a0 = arraySplitStrPlace7[0];
                String a1 = arraySplitStrPlace7[1];
                String[] aa0 = a0.split("\\(");
                String[] aa1 = a1.split("\\)");
                Log.e(TAG + "defaultShopID -> aa0[0]", aa0[0]);
                Log.e(TAG + "defaultShopID -> aa0[1]", aa0[1] + "\n");
                Log.e(TAG + "defaultTitle -> aa0[0]", aa1[0]);

                //Lat Lng Result
                String lat = aa0[1];
                String lng = aa1[0];



                /**
                 * Split, Viewport (SowthWest, NorthEast)
                 */
                getViewport = String.valueOf(place.getViewport());  // LatLngBounds{southwest=lat/lng: (37.481807019708505,126.97248441970851), northeast=lat/lng: (37.4845049802915,126.97518238029149)}
                Log.e(TAG + "ViewPort Split", String.valueOf(getViewport));

                String[] vpSplit = getViewport.split("\\)",0);  //
                Log.e(TAG + "ViewPort Split", String.valueOf(vpSplit[0]));    // LatLngBounds{southwest=lat/lng: (37.481807019708505,126.97248441970851
                Log.e(TAG + "ViewPort Split", String.valueOf(vpSplit[1]));    // , northeast=lat/lng: (37.4845049802915,126.97518238029149

                String[] vpSWSplit1 = vpSplit[0].split("\\(",0);
                String[] vpNESplit1 = vpSplit[1].split("\\(",0);

                String[] vpSWSplit2 = vpSWSplit1[1].split(",",0);
                String[] vpNESplit2 = vpNESplit1[1].split(",",0);


                // Viewport Result
                String vpSWSplitResultLat = vpSWSplit2[0];
                String vpSWSplitResultLng = vpSWSplit2[1];
                String vpNESplitResultLat = vpNESplit2[0];
                String vpNESplitResultLng = vpNESplit2[1];

                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

                pref.setSharedData("SIGNUP_Lat", lat);
                pref.setSharedData("SIGNUP_Lng", lng);
                pref.setSharedData("SIGNUP_SW_Lat", vpSWSplitResultLat);
                pref.setSharedData("SIGNUP_SW_Lng", vpSWSplitResultLng);
                pref.setSharedData("SIGNUP_NE_Lat", vpNESplitResultLat);
                pref.setSharedData("SIGNUP_NE_Lng", vpNESplitResultLng);



/*
                HashMap<String, String> userPostion = new HashMap<>();

                userPostion.put("lat",lat);
                userPostion.put("lng",lng);
                userPostion.put("SW_Lat",vpSWSplitResultLat);
                userPostion.put("SW_Lng",vpSWSplitResultLng);
                userPostion.put("NE_Lat",vpNESplitResultLat);
                userPostion.put("NE_Lng",vpNESplitResultLng);
                Log.e(TAG + "Hash  ", String.valueOf(userPostion));
                */



                Log.e(TAG + "VIEWPORT, SW 1 ", vpSWSplit2[0]);
                Log.e(TAG + "VIEWPORT, SW 2 ", vpSWSplit2[1]);
                Log.e(TAG + "VIEWPORT, NE 1 ", vpNESplit2[0]);
                Log.e(TAG + "VIEWPORT, NE 2 ", vpNESplit2[1]);




                Log.i(TAG + "Signup3", "place.toString():" + place.toString());
                Log.i(TAG + "Signup3", "place.getId():" + place.getId());
                Log.i(TAG + "Signup3", "place.getAddress():" + place.getAddress());
                Log.i(TAG + "Signup3", "place.getAttributions():" + place.getAttributions());
                Log.i(TAG + "Signup3", "place.getLatLng():" + place.getLatLng());
                Log.i(TAG + "Signup3", "place.getLocale():" + place.getLocale());
                Log.i(TAG + "Signup3", "place.getName():" + place.getName());
                Log.i(TAG + "Signup3", "place.getPhoneNumber():" + place.getPhoneNumber());
                Log.i(TAG + "Signup3", "place.getViewport():" + place.getViewport());

                getId = place.getId();
                getPriceLevel = place.getPriceLevel();



/*
                LatLngBounds mPlace = PlacePicker.getLatLngBounds(data);
                Log.i(TAG + "Signup3", "mPlace.toString() : " + mPlace.toString());
                Log.i(TAG + "Signup3", "mPlace.getClass() : " + mPlace.getClass());
                Log.i(TAG + "Signup3", "mPlace.getCenter() : " + mPlace.getCenter());
*/




                PlaceSelectionListener mPlaceListener = new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {

                        Log.i(TAG + "Signup3", "onPlaceSelected");


                    }

                    @Override
                    public void onError(Status status) {
                        Log.i(TAG + "Signup3", "onError");
                    }
                };
                mPlaceListener.onPlaceSelected(place);
//                String aa = String.valueOf(mPlace.getCenter());

                Intent intent = new Intent(this, Signup4.class);
                startActivity(intent);

            } else if (resultCode == RESULT_CANCELED) {
                Intent intent = new Intent(Signup3.this, Signup2.class);
                startActivity(intent);
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
