package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

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
 * Created by ej on 2017-11-10.
 */

public class AsyncShopDetailWebsiteUpdate extends AsyncTask<Void, Integer, StringBuilder> {


    private final String userID;
    private final String nick;
    private String params = "";
    String TestVAR;
    private Context mContext;
    private String iShopID, shopURL, iIdx, iNick;
    private String userLocationCheckResult;
    String parseUrlParameter = "http://222.122.203.55/realreview/shopAdd/shopWebsiteUpdate.php";
    private MaterialDialog builder;
    private StringBuilder stringBuilder;


    public AsyncShopDetailWebsiteUpdate(String iShopID, String shopURL, String nick, String userID, Context mContext) {
        this.mContext = mContext;
        this.iShopID = iShopID;
        this.shopURL = shopURL;
        this.nick = nick;
        this.userID = userID;
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
    protected StringBuilder doInBackground(Void... params) {
        // TODO Auto-generated method stub

        StringBuilder jsonhtml = new StringBuilder();
        StringBuffer postDataBuilder = new StringBuffer();
        String result;
        JSONObject jsonObject = new JSONObject();
        try {
            // URL설정, 접속
            String parameterCheck = "\nid_shop=" + iShopID + "\n" + "shopid=" + shopURL;
            URL url = new URL(parseUrlParameter);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            Log.d("websiteUpdate", "parseUrlParameter : " + parameterCheck);
            Log.d("websiteUpdate", "url : " + url);

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
            buffer.append("userid").append("=").append(userID).append("&");
            buffer.append("nick").append("=").append(nick).append("&");
            buffer.append("url").append("=").append(shopURL);

            // 서버로 전송
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();
            Log.d("websiteUpdate", "http Response Code : " + http.getResponseCode());

            // 전송 결과값 받기
            InputStreamReader inputStream = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputStream);
            stringBuilder = new StringBuilder();
            String str;
            while ((str = bufferReader.readLine()) != null) {
                stringBuilder.append(str + "\n");
            }
            result = stringBuilder.toString();
            Log.d("websiteUpdate", "전송결과 : " + result);
            jsonObject = new JSONObject(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringBuilder;

    }

    @Override
    protected void onPostExecute(StringBuilder result) {
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