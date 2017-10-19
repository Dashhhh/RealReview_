package com.teamnova.ej.realreview.Asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.teamnova.ej.realreview.activity.ShopAdd4;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by ej on 2017-10-08.
 */

public class AsyncShopAdd2HTTP extends AsyncTask<Void, Integer, Void> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    Context mContext;
    private ProgressDialog dialog;
    String TestVAR;

    public AsyncShopAdd2HTTP(String urlString, ProgressDialog dialog, Context mContext) {
        this.urlString = urlString;
        this.dialog = dialog;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {

        dialog.setMessage("Uploading");
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }
    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.e(TAG, "SHOP ADD response code - " + responseCode);

            Thread.sleep(1500);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


     /*
      * FILE UPLOAD

        try {
            // sendID();
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            FileInputStream mFileInputStream = new FileInputStream(fileName);
            urlString += "?id=";
            urlString += main.usr.getUserId();
            urlString += "&path=" + fileName;
            Log.d("Test", urlString);
            URL connectUrl = new URL("urlString");
                    Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // open connection
            HttpURLConnection conn = (HttpURLConnection) connectUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                    + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 8 * 1024 * 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush(); // finish upload...

            // get response
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("Test", "result = " + s);
            // mEdityEntry.setText(s);
            dos.close();

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
        */
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        try {
            dialog.dismiss();

            Intent intent = new Intent(mContext, ShopAdd4.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch(Exception e) {
        }

    }

}
