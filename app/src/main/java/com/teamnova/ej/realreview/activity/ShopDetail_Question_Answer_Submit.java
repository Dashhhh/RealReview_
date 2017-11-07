package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.R;

public class ShopDetail_Question_Answer_Submit extends AppCompatActivity implements View.OnClickListener {

    com.beardedhen.androidbootstrap.AwesomeTextView shopDetailQuestion_Answer_Questioner, shopDetailQuestion_Answer_Question;
    com.beardedhen.androidbootstrap.BootstrapEditText shopDetailQuestion_Answer_Text;
    com.beardedhen.androidbootstrap.BootstrapButton shopDetailQuestion_Answer_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__question__answer__submit);


        init();
        listener();

    }

    private void init() {
        shopDetailQuestion_Answer_Questioner = findViewById(R.id.shopDetailQuestion_Answer_Questioner);
        shopDetailQuestion_Answer_Question = findViewById(R.id.shopDetailQuestion_Answer_Question);
        shopDetailQuestion_Answer_Text = findViewById(R.id.shopDetailQuestion_Answer_Text);
        shopDetailQuestion_Answer_Submit = findViewById(R.id.shopDetailQuestion_Answer_Submit);

    }

    private void listener() {
        shopDetailQuestion_Answer_Submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.shopDetailQuestion_Answer_Submit : {



                break;
            }

        }

    }
}
