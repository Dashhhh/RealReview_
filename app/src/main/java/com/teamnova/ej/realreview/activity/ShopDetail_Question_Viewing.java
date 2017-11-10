package com.teamnova.ej.realreview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Set;

import java.util.ArrayList;

public class ShopDetail_Question_Viewing extends AppCompatActivity {


    android.support.v7.widget.RecyclerView questionAllRV;
    ArrayList<ShopDetail_Main_RV_QuestionAll_Set> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__answer_viewing);

        init();


    }

    private void init() {





    }
}
