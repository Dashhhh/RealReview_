package com.teamnova.ej.realreview.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamnova.ej.realreview.Asynctask.AsyncShopAdd2HTTP;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopAdd2 extends AppCompatActivity implements View.OnClickListener {


    String shopLat, shopLng, shopAddress, shopSign, shopPhoneNumber, shopWebsite;
    Button shopAdd2Btn;
    MaterialEditText shopAdd2sign, shopAdd2Address, shopAdd2PhoneNumber, shopAdd2Website, shopAdd2OpenTime, shopAdd2CloseTime;
    TextView shopAdd2PlaceType1, shopAdd2PlaceType2, shopAdd2PlaceType3, shopAdd2PlaceType4, shopAdd2PlaceType5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add2);

        getInitSharedData();
        init();

        shopAdd2Btn.setOnClickListener(this);

    }

    private void getInitSharedData() {
        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        shopLat = pref.getSharedData("SHOP_LAT");
        shopLng = pref.getSharedData("SHOP_LNG");
        shopAddress = pref.getSharedData("SHOP_ADDRESS");
        shopSign = pref.getSharedData("SHOP_NAME");
        shopPhoneNumber = pref.getSharedData("SHOP_PHONENUMBER");
        shopWebsite = pref.getSharedData("SHOP_WEBSITE");


    }

    private void init() {

        shopAdd2sign = (MaterialEditText) findViewById(R.id.shopAdd2sign);
        shopAdd2Address = (MaterialEditText) findViewById(R.id.shopAdd2Address);
        shopAdd2PhoneNumber = (MaterialEditText) findViewById(R.id.shopAdd2PhoneNumber);
        shopAdd2Website = (MaterialEditText) findViewById(R.id.shopAdd2Website);
        shopAdd2OpenTime = (MaterialEditText) findViewById(R.id.shopAdd2OpenTime);
        shopAdd2CloseTime = (MaterialEditText) findViewById(R.id.shopAdd2CloseTime);
        shopAdd2Btn = (Button) findViewById(R.id.shopAdd2Btn);
        shopAdd2PlaceType1 = (TextView) findViewById(R.id.shopAdd2PlaceType1);
        shopAdd2PlaceType2 = (TextView) findViewById(R.id.shopAdd2PlaceType2);
        shopAdd2PlaceType3 = (TextView) findViewById(R.id.shopAdd2PlaceType3);
        shopAdd2PlaceType4 = (TextView) findViewById(R.id.shopAdd2PlaceType4);
        shopAdd2PlaceType5 = (TextView) findViewById(R.id.shopAdd2PlaceType5);


        shopAdd2sign.setText(shopSign);
        shopAdd2Address.setText(shopAddress);
        shopAdd2PhoneNumber.setText(shopPhoneNumber);
        shopAdd2Website.setText(shopWebsite);


        if (shopAddress.equals("null")) {
            shopAdd2Address.setText("");
        }
        if (shopWebsite.equals("null")) {
            shopAdd2Website.setText("");
        }
        if (shopPhoneNumber.equals("null")) {
            shopAdd2PhoneNumber.setText("");
        }
        if (shopSign.equals("null")) {
            shopAdd2sign.setText("");
        }
        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        String placeTypes0 = pref.getSharedData("PLACETYPE" + 0);
        String placeTypes1 = pref.getSharedData("PLACETYPE" + 1);
        String placeTypes2 = pref.getSharedData("PLACETYPE" + 2);
        String placeTypes3 = pref.getSharedData("PLACETYPE" + 3);
        String placeTypes4 = pref.getSharedData("PLACETYPE" + 4);

        if (!placeTypes0.isEmpty()) {
            shopAdd2PlaceType1.setText(placeTypes0);
        }
        if (!placeTypes1.isEmpty()) {
            shopAdd2PlaceType2.setText(placeTypes1);
        }
        if (!placeTypes2.isEmpty()) {
            shopAdd2PlaceType3.setText(placeTypes2);
        }
        if (!placeTypes3.isEmpty()) {
            shopAdd2PlaceType4.setText(placeTypes3);
        }
        if (!placeTypes4.isEmpty()) {
            shopAdd2PlaceType5.setText(placeTypes4);
        }
    }


    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(getString(R.string.app_name))
                .setMessage("상점 등록을 취소 하시겠습니까??")
                .setPositiveButton("그만할래요", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        SharedPreferenceUtil pref = new SharedPreferenceUtil(ShopAdd2.this);
                        pref.setSharedData("SHOP_LAT", "");
                        pref.setSharedData("SHOP_LNG", "");
                        pref.setSharedData("SHOP_NAME", "");
                        pref.setSharedData("SHOP_ADDRESS", "");
                        pref.setSharedData("SHOP_PHONENUMBER", "");
                        pref.setSharedData("SHOP_WEBSITE", "");
                        pref.setSharedData("PLACETYPE" + 0, "");
                        pref.setSharedData("PLACETYPE" + 1, "");
                        pref.setSharedData("PLACETYPE" + 2, "");
                        pref.setSharedData("PLACETYPE" + 3, "");
                        pref.setSharedData("PLACETYPE" + 4, "");
                        Intent intent = new Intent(getApplication(), Main_Test.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("계속하기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

            }
        })
                .create()
                .show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.shopAdd2Btn: {


                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

                String a0 = shopAdd2sign.getText().toString();
                String a1 = shopAdd2Address.getText().toString();
                String a2 = shopAdd2PhoneNumber.getText().toString();
                String a3 = shopAdd2Website.getText().toString();
                String openTime = shopAdd2OpenTime.getText().toString();
                String closeTime = shopAdd2CloseTime.getText().toString();

                pref.setSharedData("SHOP_NAME", shopAdd2sign.getText().toString());
                pref.setSharedData("SHOP_ADDRESS", shopAdd2Address.getText().toString());
                pref.setSharedData("SHOP_PHONENUMBER", shopAdd2PhoneNumber.getText().toString());
                pref.setSharedData("SHOP_WEBSITE", shopAdd2Website.getText().toString());

                if (a0.isEmpty() || a1.isEmpty() || a2.isEmpty() || openTime.isEmpty() || closeTime.isEmpty() ) {

                    Dialog_Default dial = new Dialog_Default(this);
                    dial.call("WARNING", "Website를 제외한 나머지 항목을 모두 작성해 주세요.");

                } else {

                    // TODO - EXCEPTION PROCESS of OPEN TIME & CLOSE TIME
                    // TODO - 장소 중복지정 > PLACE ID CHECK

                    String urlDataLat = pref.getSharedData("SHOP_LAT");
                    String urlDataLng = pref.getSharedData("SHOP_LNG");
                    String urlDataName = pref.getSharedData("SHOP_NAME");
                    String urlDataAddress = pref.getSharedData("SHOP_ADDRESS");
                    String urlDataPhoneNumber = pref.getSharedData("SHOP_PHONENUMBER");
                    String urlDataWebsite = pref.getSharedData("SHOP_WEBSITE");
                    String urlDataId = pref.getSharedData("SHOP_ID");
                    String urlDataPriceLevel = pref.getSharedData("SHOP_PRICELEVEL");
                    String urlDataSWLat = pref.getSharedData("SHOP_VIEWPORT_SW_LAT");
                    String urlDataSWLng = pref.getSharedData("SHOP_VIEWPORT_SW_LNG");
                    String urlDataNELat = pref.getSharedData("SHOP_VIEWPORT_NE_LAT");
                    String urlDataNELng = pref.getSharedData("SHOP_VIEWPORT_NE_LNG");
                    String urlDataPlace0 = pref.getSharedData("PLACETYPE" + 0);
                    String urlDataPlace1 = pref.getSharedData("PLACETYPE" + 1);
                    String urlDataPlace2 = pref.getSharedData("PLACETYPE" + 2);
                    String urlDataPlace3 = pref.getSharedData("PLACETYPE" + 3);
                    String urlDataPlace4 = pref.getSharedData("PLACETYPE" + 4);

                    try {
                        String strName       = URLEncoder.encode(urlDataName, "UTF-8");
                        String strAddress = URLEncoder.encode(urlDataAddress, "UTF-8");
                        String strPlace0 = URLEncoder.encode(urlDataPlace0, "UTF-8");
                        String strPlace1 = URLEncoder.encode(urlDataPlace1, "UTF-8");
                        String strPlace2 = URLEncoder.encode(urlDataPlace2, "UTF-8");
                        String strPlace3 = URLEncoder.encode(urlDataPlace3, "UTF-8");
                        String strPlace4 = URLEncoder.encode(urlDataPlace4, "UTF-8");

                        String connectUrl = "http://222.122.203.55/realreview/shopAdd/shopAdd.php?";
                        String lat = "lat=" + urlDataLat;
                        String lng = "&lng=" + urlDataLng;
                        String name = "&name=" + strName;
                        String address = "&address=" + strAddress;
                        String phoneNumber = "&phoneNumber=" + urlDataPhoneNumber;
                        String webSite = "&webSite=" + urlDataWebsite;
                        String id = "&id=" + urlDataId;
                        String priceLevel = "&priceLevel=" + urlDataPriceLevel;
                        String viewportSWLat = "&viewportSWLat=" + urlDataSWLat;
                        String viewportSWLng = "&viewportSWLng=" + urlDataSWLng;
                        String viewportNELat = "&viewportNELat=" + urlDataNELat;
                        String viewportNELng = "&viewportNELng=" + urlDataNELng;
                        String shopOpen = "&shopOpen=" + openTime;
                        String shopClose = "&shopClose=" + closeTime;
                        String place0 = "&place0=" + strPlace0;
                        String place1 = "&place1=" + strPlace1;
                        String place2 = "&place2=" + strPlace2;
                        String place3 = "&place3=" + strPlace3;
                        String place4 = "&place4=" + strPlace4;



                        String urlMerge =
                                connectUrl
                                        + lat
                                        + lng
                                        + name
                                        + address
                                        + phoneNumber
                                        + webSite
                                        + id
                                        + priceLevel
                                        + viewportSWLat
                                        + viewportSWLng
                                        + viewportNELat
                                        + viewportNELng
                                        + shopOpen
                                        + shopClose
                                        + place0
                                        + place1
                                        + place2
                                        + place3
                                        + place4;

                        Log.e("SHOPADD2, URL MERGE :",urlMerge);

                        ProgressWheel progressDialog = new ProgressWheel(this);
                        Void upload;
                        upload= new AsyncShopAdd2HTTP(urlMerge, progressDialog, this).execute().get(10000,TimeUnit.MILLISECONDS);

                    } catch (UnsupportedEncodingException | InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }

                }

                break;

            }

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

    // TODO : 상점 중복 확인 -> 중복 시 ShopAdd1.class Move & 기존 ShopAdd1 정보 FLUSHING

}
