package com.teamnova.ej.realreview.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.teamnova.ej.realreview.R;

public class Main_Nearby extends AppCompatActivity implements View.OnClickListener {

    LinearLayout bottomNearby, bottomSearch, bottomMe, bottomMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nearby);


        init();
        listener();

    }

    private void init() {
//        bottomNearby = (LinearLayout) findViewById(R.id.bottomNearby);
        bottomSearch = (LinearLayout) findViewById(R.id.bottomSearch);
        bottomMe = (LinearLayout) findViewById(R.id.bottomMe);
        bottomMore = (LinearLayout) findViewById(R.id.bottomMore);


    }

    private void listener() {
//        bottomNearby.setOnClickListener(this);
        bottomSearch.setOnClickListener(this);
        bottomMe.setOnClickListener(this);
        bottomMore.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
//            case R.id.bottomNearby: {
//                Intent intent = new Intent(Main_Nearby.this, Main_Nearby.class);
//                startActivity(intent);
//                break;
//            }

            case R.id.bottomSearch: {
                Intent intent = new Intent(Main_Nearby.this, Main_Search.class);
                startActivity(intent);
                break;
            }

            case R.id.bottomMe: {
                Intent intent = new Intent(Main_Nearby.this, Main_Me.class);
                startActivity(intent);
                break;
            }

            case R.id.bottomMore: {
                Intent intent = new Intent(Main_Nearby.this, Main_More.class);
                startActivity(intent);
                break;
            }

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
                .setMessage("정말 App을 종료 하시겠습니까??")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        //Intent intent=new Intent(getApplication(),Login.class);
                        //startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

            }
        })
                .create()
                .show();
    }
}
