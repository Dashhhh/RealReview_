package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;

public class _DemoTest extends AppCompatActivity implements View.OnClickListener {

    Button demoTestDialogue, fregmentTest, facebookKey, bottomNavigation, glideTest, mainActivityTest, shopDetailTest, materialDialogTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__demo_test);

        init();
        listener();

    }

    private void init() {

        demoTestDialogue = (Button) findViewById(R.id.demoTestDialogue);
        fregmentTest = (Button) findViewById(R.id.fregmentTest);
        facebookKey = (Button) findViewById(R.id.facebookKey);
        bottomNavigation = (Button) findViewById(R.id.bottomNavigation);
        glideTest = (Button) findViewById(R.id.glideTest);
        mainActivityTest = (Button) findViewById(R.id.mainActivityTest);
        shopDetailTest = (Button) findViewById(R.id.shopDetailTest);
        materialDialogTest = findViewById(R.id.materialDialogTest);

    }

    private void listener() {
        demoTestDialogue.setOnClickListener(this);
        fregmentTest.setOnClickListener(this);
        facebookKey.setOnClickListener(this);
        bottomNavigation.setOnClickListener(this);
        glideTest.setOnClickListener(this);
        mainActivityTest.setOnClickListener(this);
        shopDetailTest.setOnClickListener(this);
        materialDialogTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.demoTestDialogue: {

                Dialog_Default dialogDefault = new Dialog_Default(this);
                dialogDefault.tempCall("제목입니다", "내용입니다.");
                break;
            }

            case R.id.fregmentTest: {

//                Intent intent = new Intent(getApplicationContext(), MainActivityFragment.class);
//                startActivity(intent);
                break;
            }

            case R.id.facebookKey: {

                Intent intent = new Intent(getApplicationContext(), _FacebookHashKey.class);
                startActivity(intent);
                break;
            }

            case R.id.bottomNavigation: {

                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
                break;
            }
            case R.id.glideTest: {

                Intent intent = new Intent(getApplicationContext(), _ServerImage.class);
                startActivity(intent);
                break;
            }
            case R.id.mainActivityTest: {

                Intent intent = new Intent(getApplicationContext(), Z_NOTUSED__Main_Nearby.class);
                startActivity(intent);
                break;
            }
            case R.id.shopDetailTest: {

                Intent intent = new Intent(getApplicationContext(), ShopDetail_Main.class);
                startActivity(intent);
                break;
            }
            case R.id.materialDialogTest : {

                Intent intent = new Intent(getApplicationContext(), ShopDetail_Main.class);
                startActivity(intent);
                break;


            }

        }

    }
}
