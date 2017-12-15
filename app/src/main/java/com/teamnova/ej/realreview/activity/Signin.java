package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.not.ForgotPassword;

public class Signin extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn, signupBtn, loginForFacebook, loginForNaver, loginForKakao, demoTest;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        init();
        listener();

    }

    private void listener() {
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        loginForFacebook.setOnClickListener(this);
        loginForNaver.setOnClickListener(this);
        loginForKakao.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        demoTest.setOnClickListener(this);
    }

    private void init() {
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        loginForFacebook = findViewById(R.id.loginForFacebook);
        loginForNaver = findViewById(R.id.loginForNaver);
        loginForKakao = findViewById(R.id.loginForKakao);
        forgotPassword = findViewById(R.id.forgotPassword);
        demoTest = findViewById(R.id.demoTest);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loginBtn: {
                Intent intent = new Intent(Signin.this, Login.class);
                startActivity(intent);
                break;
            }
            case R.id.signupBtn: {
                Intent intent = new Intent(Signin.this, Signup1_Check_ID_PW.class);
                startActivity(intent);
                break;
            }
            case R.id.loginForFacebook: {
                Uri uri = Uri.parse("http://www.facebook.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            }
            case R.id.loginForNaver: {
                Uri uri = Uri.parse("http://www.naver.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            }
            case R.id.loginForKakao: {
                Uri uri = Uri.parse("http://www.kakao.com/talk/ko");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            }
            case R.id.forgotPassword: {
                Intent intent = new Intent(Signin.this, ForgotPassword.class);
                startActivity(intent);
                break;

            }
            case R.id.demoTest: {
                Intent intent = new Intent(getApplicationContext(), _DemoTest.class);
                startActivity(intent);
                break;

            }

        }

    }
}

