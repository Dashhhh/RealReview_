package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.activity.ShopDetail_Tip_Submit;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.teamnova.ej.realreview.activity.Main.LOCATION_FAR_LEFT_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_FAR_LEFT_LNG;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_NEAR_RIGHT_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_NEAR_RIGHT_LNG;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_USER_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_USER_LNG;

/**
 * Created by ej on 2017-10-26.
 */

public class AsyncTipSubmit extends AsyncTask<Void, Integer, Void> {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    private ProgressWheel dialog;
    String TestVAR;
    private Context mContext;
    private String userLocationCheckResult;
    private String localityCheckResult;

    public AsyncTipSubmit(String urlString, ProgressWheel dialog, Context mContext) {
        this.urlString = urlString;
        this.dialog = dialog;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {

        dialog.setInstantProgress(0.64f);
        dialog.setBarColor(Color.BLUE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub

        /**
         * CONDITION - below if
         * USER LOCATION, VIEW PORT 내에 들어오는 조건
         */

        if (    LOCATION_USER_LAT > LOCATION_NEAR_RIGHT_LAT &&
                LOCATION_USER_LAT < LOCATION_FAR_LEFT_LAT   &&
                LOCATION_USER_LNG > LOCATION_FAR_LEFT_LNG    &&
                LOCATION_USER_LNG < LOCATION_NEAR_RIGHT_LNG  )
        {
            userLocationCheckResult = "1";    // TRUE
            Log.d("ASYNC_TIP", "Nearby True" );
        }

        else {
            userLocationCheckResult = "0";    // FALSE
            Log.d("ASYNC_TIP", "Nearby False" );
        }

        Log.d("ASYNC_TIP", "LOCATION_USER_LAT :" + LOCATION_USER_LAT  +">"+  LOCATION_NEAR_RIGHT_LAT );
        Log.d("ASYNC_TIP", "LOCATION_USER_LAT :" + LOCATION_USER_LAT  +"<"+  LOCATION_FAR_LEFT_LAT   );
        Log.d("ASYNC_TIP", "LOCATION_USER_LNG :" + LOCATION_USER_LNG  +">"+  LOCATION_FAR_LEFT_LNG    );
        Log.d("ASYNC_TIP", "LOCATION_USER_LNG :" + LOCATION_USER_LNG  +"<"+  LOCATION_NEAR_RIGHT_LNG  );

        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);

        String tag = pref.getSharedData("TAG");
        double shopLat = Double.parseDouble(pref.getSharedData("LAT" + tag));
        double shopLng = Double.parseDouble(pref.getSharedData("LNG" + tag));
        double lat = Double.parseDouble(pref.getSharedData("isLogged_lat"));
        double lng = Double.parseDouble(pref.getSharedData("isLogged_lng"));
        double SW_Lat = Double.parseDouble(pref.getSharedData("isLogged_SW_Lat"));
        double SW_Lng = Double.parseDouble(pref.getSharedData("isLogged_SW_Lng"));
        double NE_Lat = Double.parseDouble(pref.getSharedData("isLogged_NE_Lat"));
        double NE_Lng = Double.parseDouble(pref.getSharedData("isLogged_NE_Lng"));

        Log.d("ASYNC_TIP", "Locality, shopLat " + shopLat  );
        Log.d("ASYNC_TIP", "Locality, shopLng " + shopLng  );
        Log.d("ASYNC_TIP", "Locality, SW_Lat " + SW_Lat  );
        Log.d("ASYNC_TIP", "Locality, SW_Lng " + SW_Lng  );
        Log.d("ASYNC_TIP", "Locality, NE_Lat " + NE_Lat  );
        Log.d("ASYNC_TIP", "Locality, NE_Lng " + NE_Lng  );


//        if (    lat > SW_Lng &&
//                lat < NE_Lng &&
//                lng > NE_Lat &&
//                lng < SW_Lat )
        if (    shopLat > SW_Lat &&
                shopLat < NE_Lat &&
                shopLng > SW_Lng &&
                shopLng < NE_Lng   )
        {
            localityCheckResult = "1";    // TRUE
            Log.d("ASYNC_TIP", "localityCheckResult True" );
        }

        else {
            localityCheckResult = "0";    // FALSE
            Log.d("ASYNC_TIP", "localityCheckResult False" );
        }

        Log.d("ASYNC_TIP", "lat  :" + lat   +">"+ SW_Lat);
        Log.d("ASYNC_TIP", "lat  :" + lat   +"<"+ NE_Lat);
        Log.d("ASYNC_TIP", "lng  :" + lng   +">"+ SW_Lng);
        Log.d("ASYNC_TIP", "lng  :" + lng   +"<"+ NE_Lng);

        StringBuilder jsonhtml = new StringBuilder();
        StringBuffer postDataBuilder = new StringBuffer();
        JSONArray jsonArray;


        try {
            // URL설정, 접속
            URL url = new URL(urlString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            // 전송모드 설정(일반적인 POST방식)
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            // content-type 설정
            http.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            // 전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("shopid").append("=").append(ShopDetail_Tip_Submit.TIP_SHOPID).append("&");
            buffer.append("userid").append("=").append(ShopDetail_Tip_Submit.TIP_USERID).append("&");
            buffer.append("tip").append("=").append(ShopDetail_Tip_Submit.TIP_TEXT).append("&");
            buffer.append("nearby").append("=").append(userLocationCheckResult).append("&");
            buffer.append("locality").append("=").append(localityCheckResult).append("&");
            buffer.append("nick").append("=").append(ShopDetail_Tip_Submit.TIP_USERNICK);
            Log.d("ASYNC_TIP", "userLocationCheckResult : "+userLocationCheckResult   );

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
            while((str = bufferReader.readLine()) != null){
                builder.append(str + "\n");
            }
            String result = builder.toString();
            Log.d("ASYNC_TIP", "전송결과 : " + result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;

    }

    @Override
    protected void onPostExecute(Void result) {


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
