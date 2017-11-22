package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.Asynctask.AsyncTipSubmit;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Tip_Submit extends AppCompatActivity {

    public static String TIP_TEXT = "";
    public static String TIP_SHOPID = "";
    public static String TIP_USERID = "";
    public static String TIP_USERNICK = "";
    com.rengwuxian.materialedittext.MaterialEditText tipText;
    Button tipBtn;
    ProgressWheel progressWheel;
    TextView tipTitle;
    String tipUploadURL = "http://222.122.203.55/realreview/tip/tipUpload.php";
    private String setTipShopId;
    private String setTipUserId;
    private String setTipUserNick;
    ProgressWheel dial ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__tip__submit);

        init();
        setData();
        listener();


    }

    private void init() {
        tipBtn = findViewById(R.id.tipBtn);
        tipText = findViewById(R.id.tipText);
        tipTitle = findViewById(R.id.tipTitle);
        dial = new ProgressWheel(ShopDetail_Tip_Submit.this);
    }


    private void listener() {

        tipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!tipText.equals("")) {
                    TIP_TEXT     = tipText.getText().toString();
                    TIP_SHOPID   = setTipShopId;
                    TIP_USERID   = setTipUserId;
                    TIP_USERNICK = setTipUserNick;

                    Void conn;
                    try {
                        conn = new AsyncTipSubmit(tipUploadURL, dial, ShopDetail_Tip_Submit.this).execute().get(10000, TimeUnit.MILLISECONDS);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }


                } else {

                    Dialog_Default dial = new Dialog_Default(ShopDetail_Tip_Submit.this);
                    dial.tempCall("TIP Submit WARNING", "Tip 내용을 입력한 후 확인을 눌러주세요.");

                }


            }
        });

    }

    private void setData() {


        Intent intent = getIntent();

        String setTipTitle = intent.getExtras().getString("reviewTitle");
        setTipShopId = intent.getExtras().getString("reviewShopId");
        setTipUserId = intent.getExtras().getString("reviewUserId");
        setTipUserNick = intent.getExtras().getString("reviewUserNick");

        tipTitle.setText(setTipTitle);

    }
}
