package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.teamnova.ej.realreview.Asynctask.AsyncQuestionSubmit;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Question_Submit extends AppCompatActivity implements View.OnClickListener {

    com.rengwuxian.materialedittext.MaterialEditText questionSubmitText;
    Button questionSubmitBtn;
    String questionURL = "http://222.122.203.55/realreview/question/questionUpload.php?";
    private String setQuestionShopId;
    private String setQuestionUserId;
    private String setQuestionUserNick;
    private String setQuestionTitle;

    public static String QUESTION_SHOPID;
    public static String QUESTION_USERID;
    public static String QUESTION_USERNICK;
    public static String QUESTION_TITLE;
    public static String QUESTION_TEXT;


    @Override
    protected void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__question__submit);

        init();
        listener();
        setData();

    }

    private void setData() {
        Intent intent = getIntent();

        setQuestionTitle = intent.getExtras().getString("reviewTitle");
        setQuestionShopId = intent.getExtras().getString("reviewShopId");
        setQuestionUserId = intent.getExtras().getString("reviewUserId");
        setQuestionUserNick = intent.getExtras().getString("reviewUserNick");


    }

    private void init() {

        questionSubmitText = findViewById(R.id.questionSubmitText);
        questionSubmitBtn = findViewById(R.id.questionSubmitBtn);

    }

    private void listener() {

        questionSubmitText.setOnClickListener(this);
        questionSubmitBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.questionSubmitText: {

                break;
            }
            case R.id.questionSubmitBtn: {

                if (!questionSubmitText.getText().toString().isEmpty() && questionSubmitText != null) {

                    QUESTION_SHOPID = setQuestionShopId;
                    QUESTION_USERID = setQuestionUserId;
                    QUESTION_USERNICK = setQuestionUserNick;
                    QUESTION_TEXT = questionSubmitText.getText().toString();


                    Void conn;
                    try {
                        conn = new AsyncQuestionSubmit(questionURL, ShopDetail_Question_Submit.this).execute().get(10000, TimeUnit.MILLISECONDS);
                        Log.d("AsyncQuestionSubmit", "전송결과 : " + conn);

                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }


                } else {

                    Dialog_Default dial = new Dialog_Default(ShopDetail_Question_Submit.this);
                    dial.tempCall("Question Submit WARNING", "질문 내용을 입력한 후 확인을 눌러주세요.");

                }

                break;
            }


        }


    }
}
