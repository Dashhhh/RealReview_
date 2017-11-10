package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ej on 2017-11-04.
 */

public class AsyncSeeAllAnswerRequest extends AsyncTask<Void, Integer, JSONObject> {

    private ProgressWheel dialog;
    private String params = "";
    String TestVAR;
    private Context mContext;
    private String iShopID, iIdx, iNick;
    private String userLocationCheckResult;
    String parseUrlParameter = "http://222.122.203.55/realreview/answer/seeAllAnswer.php?";
    private JSONObject jsonObject;


    public AsyncSeeAllAnswerRequest(String iShopID, String iIdx, String iNick, Context mContext) {
        this.iShopID = iShopID;
        this.iIdx = iIdx;
        this.iNick = iNick;
        this.mContext = mContext;
        dialog = new ProgressWheel(mContext);
        
    }

    @Override
    protected void onPreExecute() {

        dialog.setInstantProgress(0.64f);
        dialog.setBarColor(Color.BLUE);
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO Auto-generated method stub

        StringBuilder jsonhtml = new StringBuilder();
        StringBuffer postDataBuilder = new StringBuffer();
        JSONArray jsonArray;


        try {
            // URL설정, 접속
            String parameterCheck = "shopid=" + iShopID + "&" + "idx=" + iIdx + "&" + "nick=" + iNick;
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
            jsonObject = new JSONObject(result);

            http.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    @Override
    protected void onPostExecute(JSONObject result) {


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