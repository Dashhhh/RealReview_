package com.teamnova.ej.realreview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.teamnova.ej.realreview.R;

public class _ServerImage extends AppCompatActivity implements View.OnClickListener {

    ImageView gridTest1, gridTest2, gridTest3, gridTest4, gridTest5, gridTest6, gridTest7, gridTest8, gridTest9, gridTest10, gridTest11, gridTest12, gridTest13, gridTest14, gridTest15;

    String u1 = "http://222.122.203.55/realreview/hard/test2.jpg";
    String u2 = "http://222.122.203.55/realreview/hard/test1.jpg";
    String u3 = "http://222.122.203.55/realreview/hard/test3.jpg";
    String u4 = "http://222.122.203.55/realreview/hard/test4.jpg";
    String u5 = "http://222.122.203.55/realreview/hard/test5.jpg";
    String u6 = "http://222.122.203.55/realreview/hard/test6.jpg";
    String u7 = "http://222.122.203.55/realreview/hard/test7.jpg";
    String u8 = "http://222.122.203.55/realreview/hard/test8.jpg";
    String u9 = "http://222.122.203.55/realreview/hard/test9.jpg";
    String u10 = "http://222.122.203.55/realreview/hard/test10.jpg";
    String u11 = "http://222.122.203.55/realreview/hard/test11.jpg";
    String u12 = "http://222.122.203.55/realreview/hard/test12.jpg";
    String u13 = "http://222.122.203.55/realreview/hard/test13.jpg";
    String u14 = "http://222.122.203.55/realreview/hard/test14.jpg";
    String u15 = "http://222.122.203.55/realreview/hard/test15.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___server_image);

        gridTest1 = findViewById(R.id.gridTest1);
        gridTest2 = findViewById(R.id.gridTest2);
        gridTest3 = findViewById(R.id.gridTest3);
        gridTest4 = findViewById(R.id.gridTest4);
        gridTest5 = findViewById(R.id.gridTest5);
        gridTest6 = findViewById(R.id.gridTest6);
        gridTest7 = findViewById(R.id.gridTest7);
        gridTest8 = findViewById(R.id.gridTest8);
        gridTest9 = findViewById(R.id.gridTest9);
        gridTest10 = findViewById(R.id.gridTest10);
        gridTest11 = findViewById(R.id.gridTest11);
        gridTest12 = findViewById(R.id.gridTest12);
        gridTest13 = findViewById(R.id.gridTest13);
        gridTest14 = findViewById(R.id.gridTest14);
        gridTest15 = findViewById(R.id.gridTest15);


        RequestOptions rOp = new RequestOptions();
        Glide.with(_ServerImage.this).load(u1).apply(rOp.circleCrop()).into(gridTest1);
        Glide.with(_ServerImage.this).load(u2).apply(rOp.circleCrop()).into(gridTest2);
        Glide.with(_ServerImage.this).load(u3).apply(rOp.circleCrop()).into(gridTest3);
        Glide.with(_ServerImage.this).load(u4).apply(rOp.circleCrop()).into(gridTest4);
        Glide.with(_ServerImage.this).load(u5).into(gridTest5);
        Glide.with(_ServerImage.this).load(u6).into(gridTest6);
        Glide.with(_ServerImage.this).load(u7).into(gridTest7);
        Glide.with(_ServerImage.this).load(u8).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest8);
        Glide.with(_ServerImage.this).load(u9).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest9);
        Glide.with(_ServerImage.this).load(u10).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest10);
        Glide.with(_ServerImage.this).load(u11).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest11);
        Glide.with(_ServerImage.this).load(u12).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest12);
        Glide.with(_ServerImage.this).load(u13).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest13);
        Glide.with(_ServerImage.this).load(u14).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest14);
        Glide.with(_ServerImage.this).load(u15).apply(rOp.placeholder(R.drawable.placeholder_loading)).into(gridTest15);

        gridTest1.setOnClickListener(this);
        gridTest2.setOnClickListener(this);
        gridTest3.setOnClickListener(this);
        gridTest4.setOnClickListener(this);
        gridTest5.setOnClickListener(this);
        gridTest6.setOnClickListener(this);
        gridTest7.setOnClickListener(this);
        gridTest8.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}
