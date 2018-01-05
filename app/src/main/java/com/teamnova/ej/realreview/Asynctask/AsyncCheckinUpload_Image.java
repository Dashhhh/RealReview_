package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.teamnova.ej.realreview.activity.ShopDetail_Checkin_Submit.JSONOBJECT_CHECKIN_IMAGEURL;

/**
 * Created by ej on 2017-12-30.
 */

public class AsyncCheckinUpload_Image extends AsyncTask<Void, Void, Integer> {

    private String upLoadServerUri = "http://222.122.203.55/realreview/checkin/checkin_upload_image.php";  // Set Image File at The SERVER
    private String sourceFileUri = "";
    Context context;
    private MaterialDialog builder;
    private String result = "";

    public AsyncCheckinUpload_Image() {
    }

    public AsyncCheckinUpload_Image(Context context, String sourceFileUri) {
        this.sourceFileUri = sourceFileUri;
        this.context = context;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        builder = new MaterialDialog.Builder(context)
                .title("Connecting")
                .content("loading..")
                .autoDismiss(true)
                .progressIndeterminateStyle(true)
                .show();

    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        builder.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        int serverResponseCode = 0;
        File sourceFile = new File(sourceFileUri);
        String fileName = sourceFileUri;
        Log.e("uploadFile", "fileUri : " + fileName);
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 8 * 1024 * 1024;

        Log.e("uploadFile", "sourceFile Uri:" + sourceFile);
        Log.e("uploadFile", "upLoadServerUri Uri:" + upLoadServerUri);
        Log.e("uploadFile", "fileName : " + fileName);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist - Uri:" + upLoadServerUri);
            Log.e("uploadFile", "Source File not exist - FileName :" + fileName);

            return 0;

        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                Log.e("uploadFile", "SERVER URL : " + String.valueOf(url));
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.e("uploadFile", "str check :" + bytesRead);

                }

                // send multipart form reviewArrayData necesssary after file reviewArrayData...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    Log.e("uploadFile", "SERVER RESPONSE CODE" + String.valueOf(serverResponseCode));

                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

                Log.e("uploadFile", "UPLOAD END");
                InputStreamReader inputStream = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader bufferReader = new BufferedReader(inputStream);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = bufferReader.readLine()) != null) {
                    builder.append(str + "\n");
                    Log.e("uploadFile", "str check :" + str);

                }
                result = builder.toString();
                JSONOBJECT_CHECKIN_IMAGEURL = result;
                Log.e("uploadFile", "JSONOBJECT_CHECKIN_IMAGEURL : " + JSONOBJECT_CHECKIN_IMAGEURL);


            } catch (MalformedURLException ex) {

                ex.printStackTrace();
                Log.e("uploadFile", "Upload file to server, " + "error: " + ex.getMessage(), ex);

            } catch (Exception e) {
                Log.e("uploadFile", "Upload file to server Exception, " + "Exception : " + e.getMessage(), e);
            }
            return serverResponseCode;

        } // End else block
    }
}