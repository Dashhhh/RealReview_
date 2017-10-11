package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.Iterator;
import java.util.List;

public class ShopAdd1 extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    int PLACE_PICKER_REQUEST = 1;

    MapView mapView;
    GoogleApiClient mGoogleApiClient;


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
        setContentView(R.layout.activity_shop_add1);


        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        pref.setSharedData("SHOP_LAT", "");
        pref.setSharedData("SHOP_LNG", "");
        pref.setSharedData("SHOP_NAME", "");
        pref.setSharedData("SHOP_ADDRESS", "");
        pref.setSharedData("SHOP_PHONENUMBER", "");
        pref.setSharedData("SHOP_WEBSITE", "");

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

//                Dialog_Default dial = new Dialog_Default(ShopAdd1.this);


                Place place = PlacePicker.getPlace(this, data);

                List<Integer> placeTypes = place.getPlaceTypes();
                Iterator iterator = placeTypes.iterator();
                int i = 0;
                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                while (iterator.hasNext()) {
                    Log.e("PlaceTypes, int Count : ", String.valueOf(i));

                    int element = (Integer) iterator.next();
                    Log.e("PlaceTypes, Integer Value", i + "번->" + element);
                    String strElement = Integer.toString(element);
                    Log.e("PlaceTypes, strElement ", i + "번->" + strElement);
                    String placeType = pref.getSharedData(strElement);
                    Log.e("PlaceTypes, SharedPreference Data ", i + "번->" + placeType);
                    pref.setSharedData("PLACETYPE" + i, placeType);
                    i++;
                }
//                Log.e("PlaceTypes - PLACE PICKER REQUEST", String.valueOf(placeTypes));

                String strPlace = String.valueOf(place);
//                dial.call("strPlace", strPlace);
                String[] splitStrPlace = strPlace.split("=", 0);
//                Log.e("splitStrPlace", splitStrPlace[0]);   // PlaceEntity{id
//                Log.e("splitStrPlace", splitStrPlace[1]);   // ChIJvfnODzWgfDUR2quYsvZn2rE, placeTypes
//                Log.e("splitStrPlace", splitStrPlace[2]);   // [7, 38, 88, 1013, 34], locale
//                Log.e("splitStrPlace", splitStrPlace[3]);   // null, name
//                Log.e("splitStrPlace", splitStrPlace[4]);   // 던킨도너츠 사당점, address
//                Log.e("splitStrPlace", splitStrPlace[5]);   // 대한민국 서울특별시 동작구 사당동 266-7, phoneNumber
//                Log.e("splitStrPlace", splitStrPlace[6]);   //  +82 2-3471-3839, latlng
//                Log.e("splitStrPlace", splitStrPlace[7]);   // lat/lng: (37.483156,126.9738334), viewport
//                Log.e("splitStrPlace", splitStrPlace[8]);   // LatLngBounds{southwest
//                Log.e("splitStrPlace", splitStrPlace[9]);   // lat/lng: (37.481807019708505,126.97248441970851), northeast
//                Log.e("splitStrPlace", splitStrPlace[10]);  // lat/lng: (37.4845049802915,126.97518238029149)}, websiteUri
//                Log.e("splitStrPlace", splitStrPlace[11]);  // http://www.dunkindonuts.co.kr/, isPermanentlyClosed
//                Log.e("splitStrPlace", splitStrPlace[12]);  // false, priceLevel
//                Log.e("splitStrPlace", splitStrPlace[13]);  // -1}


                List<Integer> ada = place.getPlaceTypes();
                List<Integer> aaaA = place.getPlaceTypes();
                String aaaAA = String.valueOf(aaaA);
                Log.e("PlaceType (LIST)--> ", String.valueOf(aaaA));
                Log.e("PlaceType (STRING) --> ", aaaAA);


                String aSplitStrPlace7 = splitStrPlace[7];
                String bSplitStrPlace7 = splitStrPlace[7];
                Log.e("--- aSplitStrPlace7", aSplitStrPlace7);
                Log.e("--- bSplitStrPlace7", bSplitStrPlace7);

                aSplitStrPlace7.lastIndexOf("(");
                String[] arraySplitStrPlace7 = aSplitStrPlace7.split(",", 0);
                Log.e("1-1 > String[] arraySplitStrPlace7[0]", arraySplitStrPlace7[0]);
                Log.e("2-1 > String[] arraySplitStrPlace7[1]", arraySplitStrPlace7[1]);

                arraySplitStrPlace7[0].indexOf("(");
                arraySplitStrPlace7[1].lastIndexOf(")");
                Log.e("1-2 > String[] arraySplitStrPlace7[0]", arraySplitStrPlace7[0]);
                Log.e("2-2 > String[] arraySplitStrPlace7[1]", arraySplitStrPlace7[1]);


                /**
                 * Split, Lat, Lng
                 */

                String a0 = arraySplitStrPlace7[0];
                String a1 = arraySplitStrPlace7[1];
                String[] aa0 = a0.split("\\(");
                String[] aa1 = a1.split("\\)");
                Log.e("a0 -> aa0[0]", aa0[0]);
                Log.e("a0 -> aa0[1]", aa0[1] + "\n");
                Log.e("a1 -> aa0[0]", aa1[0]);
                String lat = aa0[1];
                String lng = aa1[0];


                /**
                 * Split, Address
                 */

                String address01 = splitStrPlace[5];
                String[] aAddress = address01.split(",");
                address01 = aAddress[0];
                address01 = address01.replace("대한민국 ", "");
                address01 = address01.replace("대한민국", "");
                Log.e("address01", address01);


                /**
                 * Split, Name
                 */

                String name01 = splitStrPlace[4];
                Log.e("name01", name01);
                aAddress = name01.split(",");
                name01 = aAddress[0];
                Log.e("name01", name01);

//                String name02 = String.valueOf(place.getName());
//                Log.e("name02", name01);


                /**
                 * Split, PhoneNumber
                 */

                String phoneNumber01 = splitStrPlace[6];
                aAddress = phoneNumber01.split(",", 0);
                phoneNumber01 = aAddress[0];
                phoneNumber01 = phoneNumber01.replace("+82 ", "0");
                Log.e("phoneNumber01", phoneNumber01);

                /**
                 * Split, WEBSITE (HTTP URL)
                 */

                String url01 = splitStrPlace[11];
                aAddress = url01.split(",", 0);
                url01 = aAddress[0];
                Log.e("url01", url01);


                /**
                 * Split, Viewport (SowthWest, NorthEast)
                 */
                getViewport = String.valueOf(place.getViewport());  // LatLngBounds{southwest=lat/lng: (37.481807019708505,126.97248441970851), northeast=lat/lng: (37.4845049802915,126.97518238029149)}
                Log.e("ViewPort Split", String.valueOf(getViewport));

                String[] vpSplit = getViewport.split("\\)",0);  //
                Log.e("ViewPort Split", String.valueOf(vpSplit[0]));    // LatLngBounds{southwest=lat/lng: (37.481807019708505,126.97248441970851
                Log.e("ViewPort Split", String.valueOf(vpSplit[1]));    // , northeast=lat/lng: (37.4845049802915,126.97518238029149

                String[] vpSWSplit1 = vpSplit[0].split("\\(",0);
                String[] vpNESplit1 = vpSplit[1].split("\\(",0);

                String[] vpSWSplit2 = vpSWSplit1[1].split(",",0);
                String[] vpNESplit2 = vpNESplit1[1].split(",",0);

                String vpSWSplitResultLat = vpSWSplit2[0];
                String vpSWSplitResultLng = vpSWSplit2[1];
                String vpNESplitResultLat = vpNESplit2[0];
                String vpNESplitResultLng = vpNESplit2[1];


                Log.e("VIEWPORT, SW 1 ", vpSWSplit2[0]);
                Log.e("VIEWPORT, SW 2 ", vpSWSplit2[1]);
                Log.e("VIEWPORT, NE 1 ", vpNESplit2[0]);
                Log.e("VIEWPORT, NE 2 ", vpNESplit2[1]);

                getId = place.getId();
                getPriceLevel = place.getPriceLevel();
//                Log.e("GOOGLE PLACE INFO -> getId            ", getId);
//                Log.e("GOOGLE PLACE INFO -> getViewport      ", getViewport);
//                Log.e("GOOGLE PLACE INFO -> getPriceLevel   ", String.valueOf(getPriceLevel));
//                getAttribution = String.valueOf(place.getAttributions());
//                Log.e("GOOGLE PLACE INFO -> getAttribution ", getAttribution);
//                getRating = place.getRating();
//                getAddress = String.valueOf(place.getAddress());
//                getName = String.valueOf(place.getName());
//                getPhoneNumber = String.valueOf(place.getPhoneNumber());
//                getWebsiteUri = String.valueOf(place.getWebsiteUri());
//                getLatLng = String.valueOf(place.getLatLng());
//                getLocale = String.valueOf(place.getLocale());
//                getPlaceTypes = String.valueOf(place.getPlaceTypes());
//                Log.e("GOOGLE PLACE INFO -> getAddress       ", getAddress);
//                Log.e("GOOGLE PLACE INFO -> getName          ", getName);
//                Log.e("GOOGLE PLACE INFO -> getPhoneNumber   ", getPhoneNumber);
//                Log.e("GOOGLE PLACE INFO -> getWebsiteUri    ", getWebsiteUri);
//                Log.e("GOOGLE PLACE INFO -> getLatLng        ", getLatLng);
//                Log.e("GOOGLE PLACE INFO -> getPlaceTypes    ", getPlaceTypes);
//                Log.e("GOOGLE PLACE INFO -> getRating      ", String.valueOf(getRating));

                pref.setSharedData("SHOP_LAT", lat);
                pref.setSharedData("SHOP_LNG", lng);
                pref.setSharedData("SHOP_NAME", name01);
                pref.setSharedData("SHOP_ADDRESS", address01);
                pref.setSharedData("SHOP_PHONENUMBER", phoneNumber01);
                pref.setSharedData("SHOP_WEBSITE", url01);
                pref.setSharedData("SHOP_ID",getId);
                pref.setSharedData("SHOP_PRICELEVEL", String.valueOf(getPriceLevel));
                pref.setSharedData("SHOP_VIEWPORT_SW_LAT",vpSWSplitResultLat);
                pref.setSharedData("SHOP_VIEWPORT_SW_LNG",vpSWSplitResultLng);
                pref.setSharedData("SHOP_VIEWPORT_NE_LAT",vpNESplitResultLat);
                pref.setSharedData("SHOP_VIEWPORT_NE_LNG",vpNESplitResultLng);

                LatLngBounds mPlace = PlacePicker.getLatLngBounds(data);
//                Log.e("Place place ", "PlacePicker.getPlace(this, data) - " + place);
//                Log.e("LatLngBounds mPlace ", "PlacePicker.getLatLngBounds(data) - " + mPlace);

                PlaceSelectionListener mPlaceListener = new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        String placeSelectedString = String.valueOf(place);
                        Dialog_Default dial = new Dialog_Default(ShopAdd1.this);
//                        dial.call("!!", String.valueOf(place));
//                        Log.e("onPlaceSelected", "Place place - " + place);
//                        Log.e("onPlaceSelected", "String placeSelectedString- " + placeSelectedString);

                    }

                    @Override
                    public void onError(Status status) {
                        Dialog_Default dial = new Dialog_Default(ShopAdd1.this);
//                        dial.call("ERROR", "onPlaceSelected ERROR, CHECK IT");
                    }
                };


                mPlaceListener.onPlaceSelected(place);
//                Log.e("mPlaceListener", "mPlaceListener.onPlaceSelected(place); " + mPlaceListener);


                String aa = String.valueOf(mPlace.getCenter());
//                dial.call("!", "Return By MARKER POSITION LATLNG > " + aa);  /**Return By MARKER POSITION LATLNG*/
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, ShopAdd2.class);
                startActivity(intent);

            } else if (resultCode == RESULT_CANCELED) {
                Intent intent = new Intent(ShopAdd1.this, Main_Test.class);
                startActivity(intent);
            }
        }
    }

//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.activity_shop_add1_inflate, container, false);
//
//
//        mapView = (MapView)layout.findViewById(R.id.map);
//        mapView.getMapAsync(this);
//
//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                Location location = new Location("");
//                location.setLatitude(place.getLatLng().latitude);
//                location.setLongitude(place.getLatLng().longitude);
//
//                setCurrentLocation(location, place.getName().toString(), place.getAddress().toString());
//            }
//
//
//
//            @Override
//            public void onError(Status status) {
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });
//
//        return layout;
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
