package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_SHOPID;
import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_TEXT;
import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_USERID;
import static com.teamnova.ej.realreview.activity.ShopDetail_Question_Submit.QUESTION_USERNICK;

/**
 * Created by ej on 2017-11-04.
 */

public class AsyncAnswerSubmit extends AsyncTask<Void, Integer, Void> {

    private String params = "";
    String TestVAR;
    private Context mContext;
    private String iShopID, iUserID, answer, iIdx, iNick;
    private String userLocationCheckResult;
    String parseUrlParameter = "http://222.122.203.55/realreview/answer/answerUpload.php?";
    private MaterialDialog builder;


    public AsyncAnswerSubmit(String iShopID, String iUserID, String answer, String iIdx,String iNick, Context mContext) {
        this.iShopID = iShopID;
        this.iUserID = iUserID;
        this.answer = answer;
        this.iIdx = iIdx;
        this.iNick = iNick;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {


        builder = new MaterialDialog.Builder(mContext)
                .title("Connecting")
                .content("loading..")
                .progressIndeterminateStyle(true)
                .show();

    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub

        StringBuilder jsonhtml = new StringBuilder();
        StringBuffer postDataBuilder = new StringBuffer();
        JSONArray jsonArray;


        try {
            // URL설정, 접속
            String parameterCheck = "shopid=" + iShopID + "&" + "userid=" + iUserID + "&" + "answer=" + answer + "&" + "idx=" + iIdx + "&" + "nick=" + iNick;
            URL url = new URL(parseUrlParameter);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            Log.d("AsyncAnswerSubmit", "parseUrlParameter : " + parameterCheck);
            Log.d("AsyncAnswerSubmit", "url : " + url);

            // 전송모드 설정(일반적인 POST방식)
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            // content-type 설정
            http.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            // 전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("shopid").append("=").append(iShopID).append("&");
            buffer.append("userid").append("=").append(iUserID).append("&");
            buffer.append("answer").append("=").append(answer).append("&");
            buffer.append("nick").append("=").append(iNick).append("&");
            buffer.append("question_idx").append("=").append(iIdx);

            // 서버로 전송
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();
            
            // 전송 결과값 받기
            InputStreamReader inputStream = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputStream);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = bufferReader.readLine()) != null) {
                builder.append(str + "\n");
            }
            String result = builder.toString();
            Log.d("AsyncAnswerSubmit", "전송결과 : " + result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        builder.dismiss();
    }

    /**
     * Map 형식으로 Key와 Value를 셋팅한다.
     *
     * @param key   : 서버에서 사용할 변수명
     * @param value : 변수명에 해당하는 실제 값
     * @return
     */
    private static String setValue(String key, String value) {
        return "Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n"
                + value + "\r\n";
    }

    /**
     * 업로드할 파일에 대한 메타 데이터를 설정한다.
     *
     * @param key      : 서버에서 사용할 파일 변수명
     * @param fileName : 서버에서 저장될 파일명
     * @return
     */
    private static String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key
                + "\";filename=\"" + fileName + "\"\r\n";
    }
}