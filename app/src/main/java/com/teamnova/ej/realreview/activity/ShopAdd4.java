package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

public class ShopAdd4 extends AppCompatActivity implements View.OnClickListener {

    Button shopAdd4NextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add4);

        init();
        listener();
    }

    private void listener() {
        shopAdd4NextBtn.setOnClickListener(this);
    }

    private void init() {
        shopAdd4NextBtn = (Button) findViewById(R.id.shopAdd4NextBtn);
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.shopAdd4NextBtn : {

                Intent intent = new Intent(ShopAdd4.this, Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                pref.setSharedData("PLACETYPE" + 0,"");
                pref.setSharedData("PLACETYPE" + 1,"");
                pref.setSharedData("PLACETYPE" + 2,"");
                pref.setSharedData("PLACETYPE" + 3,"");
                pref.setSharedData("PLACETYPE" + 4,"");
                pref.setSharedData("SHOP_LAT", "");
                pref.setSharedData("SHOP_LNG", "");
                pref.setSharedData("SHOP_NAME", "");
                pref.setSharedData("SHOP_ADDRESS", "");
                pref.setSharedData("SHOP_PHONENUMBER", "");
                pref.setSharedData("SHOP_WEBSITE", "");
                break;
            }

        }

    }
}
