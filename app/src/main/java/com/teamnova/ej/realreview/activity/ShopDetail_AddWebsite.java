package com.teamnova.ej.realreview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.ValidateUtil;

public class ShopDetail_AddWebsite extends AppCompatActivity {

    com.beardedhen.androidbootstrap.BootstrapEditText addWebText;
    com.beardedhen.androidbootstrap.BootstrapButton addWebSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail__add_website);

        init();
        listener();

    }

    private void init() {

        addWebText = findViewById(R.id.addWebText);
        addWebSubmit = findViewById(R.id.addWebSubmit);

    }

    private void listener() {

        addWebSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Default dial = new Dialog_Default(ShopDetail_AddWebsite.this);

                if (addWebText.getText().toString().isEmpty()) {

                    dial.tempCall("OOPS!", "내용을 예제와 같이 다시 입력해 주세요.");

                } else {

                    boolean check = ValidateUtil.validateHttp(addWebText.getText().toString());
                    if (check) {
                        dial.tempCall("정규식 TRUE","!");
                    } else {
                        dial.tempCall("OOPS!", "내용을 예제와 같이 다시 입력해 주세요!");
                    }


                }

            }
        });

    }


}
