package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.R;

public class Main_Me_Bookmark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main__me__bookmark);
    }
}
