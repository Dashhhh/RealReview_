package com.teamnova.ej.realreview.Asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ej on 2017-10-08.
 */

public class AsyncReviewSubmit extends AsyncTask<Void, Integer, Void> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    Context mContext;
    private ProgressDialog dialog;
    String TestVAR;

    public AsyncReviewSubmit(ProgressDialog dialog, Context mContext) {
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
            SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);


            String URL0 = String.valueOf(pref.getSharedData("REVIEW_IMAGE_1"));
            String URL1 = String.valueOf(pref.getSharedData("REVIEW_IMAGE_2"));
            String URL2 = String.valueOf(pref.getSharedData("REVIEW_IMAGE_3"));
            String URL3 = String.valueOf(pref.getSharedData("REVIEW_IMAGE_4"));
            String URL4 = String.valueOf(pref.getSharedData("REVIEW_IMAGE_5"));
            Log.d("REVIEW_Image", "String URL0 :" + URL0);
            Log.d("REVIEW_Image", "String URL1 :" + URL1);
            Log.d("REVIEW_Image", "String URL2 :" + URL2);
            Log.d("REVIEW_Image", "String URL3 :" + URL3);
            Log.d("REVIEW_Image", "String URL4 :" + URL4);

            ArrayList<String> imagePath = new ArrayList<>();
            if (!URL0.isEmpty()) {
                imagePath.add(0, URL0);
                Log.d("REVIEW_Image", "imagePath add0 :" + imagePath.get(0));
            }
            if (!URL1.isEmpty()) {
                imagePath.add(1, URL1);
                Log.d("REVIEW_Image", "imagePath add1 :" + imagePath.get(1));
            }
            if (!URL2.isEmpty()) {
                imagePath.add(2, URL2);
                Log.d("REVIEW_Image", "imagePath add2 :" + imagePath.get(2));
            }
            if (!URL3.isEmpty()) {
                imagePath.add(3, URL3);
                Log.d("REVIEW_Image", "imagePath add3 :" + imagePath.get(3));
            }
            if (!URL4.isEmpty()) {
                imagePath.add(4, URL4);
                Log.d("REVIEW_Image", "imagePath add4 :" + imagePath.get(4));
            }

            Log.d("REVIEW_Image", "imagePath size :" + imagePath.size());


            // sendID();
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String sURL = "http://222.122.203.55/realreview/shopimage/reviewImageUpload.php";
            URL connectUrl = new URL(sURL);
            Log.d("REVIEW_Image", "URL :" + String.valueOf(connectUrl));
            File sourceFile0 = new File(imagePath.get(0));
            File sourceFile1 = new File(imagePath.get(1));
            File sourceFile2 = new File(imagePath.get(2));
            File sourceFile3 = new File(imagePath.get(3));
            File sourceFile4 = new File(imagePath.get(4));
            String fileName0 = new File(imagePath.get(0)).getName();
            String fileName1 = new File(imagePath.get(1)).getName();
            String fileName2 = new File(imagePath.get(2)).getName();
            String fileName3 = new File(imagePath.get(3)).getName();
            String fileName4 = new File(imagePath.get(4)).getName();
            Log.d("REVIEW_Image", "File sourceFile0 :" + sourceFile0);
            Log.d("REVIEW_Image", "File sourceFile1 :" + sourceFile1);
            Log.d("REVIEW_Image", "File sourceFile2 :" + sourceFile2);
            Log.d("REVIEW_Image", "File sourceFile3 :" + sourceFile3);
            Log.d("REVIEW_Image", "File sourceFile4 :" + sourceFile4);
            Log.d("REVIEW_Image", "String fileName0 :" + fileName0);
            Log.d("REVIEW_Image", "String fileName1 :" + fileName1);
            Log.d("REVIEW_Image", "String fileName2 :" + fileName2);
            Log.d("REVIEW_Image", "String fileName3 :" + fileName3);
            Log.d("REVIEW_Image", "String fileName4 :" + fileName4);

            // open connection
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file0", fileName0);
            conn.setRequestProperty("uploaded_file1", fileName1);
            conn.setRequestProperty("uploaded_file2", fileName2);
            conn.setRequestProperty("uploaded_file3", fileName3);
            conn.setRequestProperty("uploaded_file4", fileName4);


            conn.connect();
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            FileInputStream mFileInputStream = new FileInputStream(imagePath.get(0));

            OutputStream os = conn.getOutputStream();
            String review0 = pref.getSharedData("HTTP_REVIEW_ID");
            String review1 = pref.getSharedData("HTTP_REVIEW_REVIEW");
            String review2 = pref.getSharedData("HTTP_REVIEW_USER");
            String review3 = pref.getSharedData("HTTP_REVIEW_RATING");

            String reviewData = sURL + "?" + "reviewID=" + review0 + "&review=" + review1 + "&user=" + review2 + "&rating=" + review3;
            os.write(reviewData.getBytes("UTF-8"));
            Log.d("REVIEW_Image", "OutputStream URL :" + reviewData);


            for (int i = 0; i < imagePath.size(); i++) {
                // write data
                dos = new DataOutputStream(conn.getOutputStream());
                File sourceFile = new File(imagePath.get(i));
                String fileName = new File(imagePath.get(i)).getName();
                if (!sourceFile.isFile()) {
                    Log.d("REVIEW_Image", "sourceFile is not exist!!!! :" + imagePath.get(i));
                }
                mFileInputStream = new FileInputStream(sourceFile);
                Log.d("REVIEW_Image", "mFileInputStream  is " + mFileInputStream);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile" + i + "\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 8 * 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);

                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                Log.d("REVIEW_Image", "image byte is " + bytesRead);

                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }

            }

            // close streams
            dos.writeBytes(lineEnd);
            Log.e("REVIEW_Image", "File is written");
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            mFileInputStream.close();
            dos.flush(); // finish upload...
            os.flush();
            os.close();
            /*
            // get response
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("REVIEW_Image", "result = " + s);
            // mEdityEntry.setText(s);
            */
            dos.close();
            int responseCode = conn.getResponseCode();
            Log.e("REVIEW_Image", "response code - " + responseCode);

            pref.setSharedData("REVIEW_IMAGE_1", "");
            pref.setSharedData("REVIEW_IMAGE_2", "");
            pref.setSharedData("REVIEW_IMAGE_3", "");
            pref.setSharedData("REVIEW_IMAGE_4", "");
            pref.setSharedData("REVIEW_IMAGE_5", "");

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            dialog.dismiss();

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        } catch (Exception e) {
        }

    }

}
