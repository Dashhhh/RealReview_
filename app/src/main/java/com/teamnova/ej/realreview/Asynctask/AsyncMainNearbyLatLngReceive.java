package com.teamnova.ej.realreview.Asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.teamnova.ej.realreview.activity.Main_Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by ej on 2017-10-12.
 */

public class AsyncMainNearbyLatLngReceive  extends AsyncTask<Void, Integer, StringBuilder> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    Context mContext;
    private ProgressDialog dialog;
    String TestVAR;

    public AsyncMainNearbyLatLngReceive(String urlString, ProgressDialog dialog, Context mContext) {
        this.urlString = urlString;
        this.dialog = dialog;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {

        dialog.setMessage("SHOP DATA Check");
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }
    @Override
    protected StringBuilder doInBackground(Void... params) {
        // TODO Auto-generated method stub

            StringBuilder jsonHtml = new StringBuilder();
            JSONArray jsonArray;
            try {
                URL phpUrl = new URL(urlString);
                Log.d("AsyncMainLatLngReceive","URL:"+urlString);

                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
                conn.setUseCaches(false);
                int responseStatusCode = conn.getResponseCode();
                Log.e(TAG, "response code - " + responseStatusCode);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    while (true) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        jsonHtml.append(line + "\n");
                    }
                    br.close();
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            jsonHtml.toString().trim();
            Log.e(TAG, "jsonHtml - " + jsonHtml);
            try {
                int i = 0;
                JSONObject jObject = new JSONObject(String.valueOf(jsonHtml));
                jsonArray = jObject.getJSONArray("realreview");
                JSONObject item = jsonArray.getJSONObject(i);
                int length = jsonArray.length();

                Log.d("AsyncMainLatLngReceive","JSONObject jOcject:"+jObject);
                Log.d("AsyncMainLatLngReceive","JSONArray jsonArray :"+jsonArray);
                Log.d("AsyncMainLatLngReceive","JSONObject item:"+item);

                Log.d("AsyncMainLatLngReceive","JSONObject jOcject Length:"+jObject.length());
                Log.d("AsyncMainLatLngReceive","JSONArray jsonArray Length :"+jsonArray.length());
                Log.d("AsyncMainLatLngReceive","JSONArray jsonArray Length :"+item.length());


                String getId                    = item.getString("id");
                String getAddress                   = item.getString("address");
                String getLat                   = item.getString("latitude");
                String getLng                       = item.getString("longtitude");
                String getviewportSouthWestLat = item.getString("viewportSouthWestLat");
                String getViewportSouthWestLng              = item.getString("viewportSouthWestLng");
                String getViewportNorthEastLat                         = item.getString("viewportNorthEastLat");
                String getViewportNorthEastLng              = item.getString("viewportNorthEastLng");
                String getShopName                   = item.getString("shopName");
                String getShopOpen                 = item.getString("shopOpen");
                String getShopClose                = item.getString("shopClose");
                String getShopTheme1             = item.getString("shopTheme1");
                String getShopTheme2               = item.getString("shopTheme2");
                String getShopTheme3                  = item.getString("shopTheme3");
                String getShopTheme4                  = item.getString("shopTheme4");
                String getShopTheme5                  = item.getString("shopTheme5");
                String getCallNumber                  = item.getString("callNumber");
                String getIndexShopAdd                   = item.getString("indexShopAdd");
                String getPermanentlyClosed                   = item.getString("permanentlyClosed");
                String getPriceLevel                   = item.getString("priceLevel");
                Main_Test.HTTP_RECEIVE_SHOPDATA = jsonHtml;

                return jsonHtml;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
    }
    @Override
    protected void onPostExecute(StringBuilder result) {
        try {
            dialog.dismiss();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
