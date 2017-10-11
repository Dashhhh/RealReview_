package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

public class Signup3 extends AppCompatActivity implements View.OnClickListener {

    WebView webView;
    TextView result;
    Handler handler;
    Button sign3Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        result = (TextView) findViewById(R.id.result);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

        sign3Next = (Button) findViewById(R.id.sign3Next);
        sign3Next.setOnClickListener(this);

    }

    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView);
        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        // webview urlParse load
        webView.loadUrl("http://codeman77.ivyro.net/getAddress.php");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign3Next : {


                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                String addressCheck = pref.getSharedData("SIGNIN_ADDRESS");
                if (addressCheck.isEmpty()){
                    Dialog_Default dial = new Dialog_Default(this);
                    dial.call("WARNING","주소를 입력해 주세요");
                }
                else{
                    Intent intent = new Intent(Signup3.this, Signup4.class);
                    startActivity(intent);
                    break;
                }



            }
        }
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    result.setText(String.format("%s %s %s", arg1, arg2, arg3));
                    String strResult = result.getText().toString();
                    SharedPreferenceUtil pref = new SharedPreferenceUtil(Signup3.this);
                    pref.setSharedData("SIGNIN_ADDRESS", strResult);
                    Log.e("ADDRESS", "ADDRESS :"+strResult);
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }
}
