package com.teamnova.ej.realreview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncAnswerSubmit;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Question_Answer_Submit extends AppCompatActivity implements View.OnClickListener {

    com.beardedhen.androidbootstrap.AwesomeTextView shopDetailQuestion_Answer_Questioner, shopDetailQuestion_Answer_Question;
    com.beardedhen.androidbootstrap.BootstrapEditText shopDetailQuestion_Answer_Text;
    com.beardedhen.androidbootstrap.BootstrapButton shopDetailQuestion_Answer_Submit;

    String iUserID, iShopID, iQuestion, iQuestioner, iIdx, iNick;
    String parseUrlParameter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__question__answer__submit);

        bundle();
        init();
        listener();

    }

    private void bundle() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        Bundle bundle = getIntent().getExtras();
        iUserID     = bundle.getString("UserID");
        iShopID     = bundle.getString("ShopID");
        iNick       = pref.getSharedData("isLogged_nick");
        iQuestion   = bundle.getString("Question");
        iQuestioner = bundle.getString("Questioner");
        iIdx        = bundle.getString("Idx");
        Log.d("AsyncAnswerSubmit", "iUserID     : " +iUserID    );
        Log.d("AsyncAnswerSubmit", "iShopID     : " +iShopID    );
        Log.d("AsyncAnswerSubmit", "iNick       : " +iNick      );
        Log.d("AsyncAnswerSubmit", "iQuestion   : " +iQuestion  );
        Log.d("AsyncAnswerSubmit", "iQuestioner : " +iQuestioner);
        Log.d("AsyncAnswerSubmit", "iIdx        : " +iIdx);

    }

    private void init() {
        shopDetailQuestion_Answer_Questioner = findViewById(R.id.shopDetailQuestion_Answer_Questioner);
        shopDetailQuestion_Answer_Question = findViewById(R.id.shopDetailQuestion_Answer_Question);
        shopDetailQuestion_Answer_Text = findViewById(R.id.shopDetailQuestion_Answer_Text);
        shopDetailQuestion_Answer_Submit = findViewById(R.id.shopDetailQuestion_Answer_Submit);

        shopDetailQuestion_Answer_Questioner.setText(iQuestioner);
        shopDetailQuestion_Answer_Question.setMarkdownText(iQuestion);


    }

    private void listener() {
        shopDetailQuestion_Answer_Submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.shopDetailQuestion_Answer_Submit: {

                if (!shopDetailQuestion_Answer_Text.equals("")) {
                    String answer = shopDetailQuestion_Answer_Text.getText().toString();

                    Void conn;

                    try {
                        conn = new AsyncAnswerSubmit(iShopID, iUserID, answer, iIdx, iNick,this).execute().get(10000, TimeUnit.MILLISECONDS);
                        finish();

                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }

                }

                break;
            }

        }

    }
}

