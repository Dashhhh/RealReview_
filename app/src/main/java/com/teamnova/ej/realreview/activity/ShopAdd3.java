package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopAdd3_Adapter;
import com.teamnova.ej.realreview.adapter.ShopAdd3_SetData;

import java.util.ArrayList;

public class ShopAdd3 extends AppCompatActivity implements View.OnClickListener {

    Button shopAdd3AddMenuBtn, shopAdd3NextBtn;
    ListView shopAdd3LV;
    ArrayList<ShopAdd3_SetData> arMenuList = null;
    ShopAdd3_Adapter shopAdd3_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add3);

        init();
        makeListViewAdapter();
        listener();

        ShopAdd3_SetData arml = new ShopAdd3_SetData();

        arml.setShopAdd3Title("");
        arml.setShopAdd3Description("");
        arml.setShopAdd3Tag("");

        shopAdd3_adapter.data.add(0,arml);
        shopAdd3_adapter.notifyDataSetChanged();
//        shopAdd3LV.setFocusable(false);

    }

    private void makeListViewAdapter() {

        arMenuList = new ArrayList<>();
        shopAdd3_adapter = new ShopAdd3_Adapter(arMenuList, getApplicationContext());

    }

    private void listener() {
        shopAdd3LV.setAdapter(shopAdd3_adapter);
        shopAdd3AddMenuBtn.setOnClickListener(this);
        shopAdd3NextBtn.setOnClickListener(this);
    }

    private void init() {
        shopAdd3LV = (ListView) findViewById(R.id.shopAdd3LV);
        shopAdd3NextBtn = (Button) findViewById(R.id.shopAdd3NextBtn);
        shopAdd3AddMenuBtn = (Button) findViewById(R.id.shopAdd3AddMenuBtn);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.shopAdd3AddMenuBtn : {

                ShopAdd3_SetData arml = new ShopAdd3_SetData();

                int size = shopAdd3_adapter.data.size();


                arml.shopAdd3Title = "";
                arml.shopAdd3Description  = "";
                arml.shopAdd3Tag="";

                shopAdd3_adapter.data.add(0,arml);
                shopAdd3_adapter.notifyDataSetChanged();
                break;

            }


            case R.id.shopAdd3NextBtn : {

                Intent intent = new Intent(ShopAdd3.this, ShopAdd4.class);
                startActivity(intent);
                break;

            }
        }   // switch
    }   // onClick
}
