package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ej on 2017-11-10.
 */

public class AsyncReviewGoodDown extends AsyncTask<Void, Integer, Void> {


    private String params = "";
    String TestVAR;
    private Context mContext;
    private String iShopID, iUserID, iIdx, iNick;
    private String userLocationCheckResult;
    String parseUrlParameter = "http://222.122.203.55/realreview/reviewScore/reviewGoodDown.php?";
        private MaterialDialog builder;


    public AsyncReviewGoodDown(String iShopID, String iUserID, String iNick, String iIdx, Context mContext) {
        this.mContext = mContext;
        this.iShopID = iShopID;
        this.iUserID = iUserID;
        this.iNick = iNick;
        this.iIdx = iIdx;
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
            String parameterCheck = "id_shop=" + iShopID + "\n" + "id_user=" + iUserID + "\n" + "idx_question=" + iIdx + "\n" + "nick=" + iNick;
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
            buffer.append("id_shop").append("=").append(iShopID).append("&");
            buffer.append("id_user").append("=").append(iUserID).append("&");
            buffer.append("nick").append("=").append(iNick).append("&");
            buffer.append("idx_review").append("=").append(iIdx);

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