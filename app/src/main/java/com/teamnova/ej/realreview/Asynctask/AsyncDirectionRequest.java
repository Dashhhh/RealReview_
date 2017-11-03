package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ej on 2017-10-26.
 */

public class AsyncDirectionRequest extends AsyncTask<Void, Integer, JSONObject> {

    Context mContext;
    String directionOriginLat = "";
    String directionOriginLng = "";
    String directionDestination = "";
    String directionDestinationLat = "";
    String directionDestinationLng = "";

    public AsyncDirectionRequest() {
    }
    public AsyncDirectionRequest(String directionOriginLat,String directionOriginLng, String directionDestinationLat,String directionDestinationLng, Context mContext) {
        this.mContext = mContext;
        this.directionOriginLat = directionOriginLat;
        this.directionOriginLng = directionOriginLng;
        this.directionDestinationLat = directionDestinationLat;
        this.directionDestinationLng = directionDestinationLng;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressWheel dial = new ProgressWheel(mContext);
        dial.setInstantProgress(0.64f);
        dial.setBarColor(Color.BLUE);
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
//        String directionURL = "https://maps.googleapis.com/maps/api/directions/json?origin="+ directionOriginLat +","+directionOriginLng+"&destination="+directionDestinationLat+","+directionDestinationLng+"&region=kr&mode=transit&key=AIzaSyCH9Jr6Ag9BwAmV5qw3BcHTnJ3qgqHmWtk";
        String directionURL = "https://maps.googleapis.com/maps/api/directions/json?origin="+ directionOriginLat +","+directionOriginLng+"&destination=37.4905915,126.9613552"+"&language=ko&region=kr&mode=transit&key=AIzaSyCH9Jr6Ag9BwAmV5qw3BcHTnJ3qgqHmWtk";
        JSONObject jsonObject = new JSONObject();
        String result = "";
        String okUrl = "";



//        OkHttpClient client = new OkHttpClient();
//        String run(String okUrl) throws IOException {
//            Request req = new Request.Builder()
//                    .url(okUrl)
//                    .build();
//            Response response = null;
//            try {
//                response = client.newCall(req).execute();
//                return response.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }

        /**
         * CONDITION - below if
         * HTTP POST Request, TODO : Parameter Check
         */

        try {
            // URL설정, 접속



//            URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=place_id:ChIJ685WIFYViEgRHlHvBbiD5nE&destination=place_id:ChIJA01I-8YVhkgRGJb0fW4UX7Y&key=AIzaSyCH9Jr6Ag9BwAmV5qw3BcHTnJ3qgqHmWtk");
            URL url = new URL(directionURL);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            Log.d("DIRECTION", "url :" + url);

            // 전송모드 설정(일반적인 POST방식)
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            // content-type 설정
            http.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            // 전송 결과값 받기

//            http.connect();

            InputStreamReader inputStream = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputStream);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = bufferReader.readLine()) != null) {
                builder.append(str + "\n");
            }
            result = builder.toString();
            Log.d("DIRECTION", "전송결과 : " + result);
            jsonObject = new JSONObject(result);

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
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
