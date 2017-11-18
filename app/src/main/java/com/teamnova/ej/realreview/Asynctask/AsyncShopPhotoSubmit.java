package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ej on 2017-10-08.
 */

public class AsyncShopPhotoSubmit extends AsyncTask<Void, Integer, Void> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    Context mContext;
    String TestVAR;
    private String reviewData;
    ProgressWheel shopDetailSpinner;

    public AsyncShopPhotoSubmit(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {

         shopDetailSpinner = new ProgressWheel(mContext);
        



    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub

        try {

            String sURL = "http://222.122.203.55/realreview/shopimage/photoImageUpload.php";
            SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);

            String review0 = pref.getSharedData("HTTP_REVIEW_ID");
            String review2 = pref.getSharedData("HTTP_REVIEW_USER");
            reviewData = sURL + "?" + "reviewid=" + review0 + "&user=" + review2;
            Log.d("ShopPhoto_Image", "String review0 :" + review0);
            Log.d("ShopPhoto_Image", "String review2 :" + review2);



            StringBuffer postDataBuilder = new StringBuffer();

            String URL0 = String.valueOf(pref.getSharedData("ShopPhoto_Image_1"));
            String URL1 = String.valueOf(pref.getSharedData("ShopPhoto_Image_2"));
            String URL2 = String.valueOf(pref.getSharedData("ShopPhoto_Image_3"));
            String URL3 = String.valueOf(pref.getSharedData("ShopPhoto_Image_4"));
            String URL4 = String.valueOf(pref.getSharedData("ShopPhoto_Image_5"));
            Log.d("ShopPhoto_Image", "String URL0 :" + URL0);
            Log.d("ShopPhoto_Image", "String URL1 :" + URL1);
            Log.d("ShopPhoto_Image", "String URL2 :" + URL2);
            Log.d("ShopPhoto_Image", "String URL3 :" + URL3);
            Log.d("ShopPhoto_Image", "String URL4 :" + URL4);


            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            URL connectUrl = new URL(sURL);
            Log.d("ShopPhoto_Image", "URL :" + String.valueOf(connectUrl));


            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();

            // open connection
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            ArrayList<String> imagePath = new ArrayList<>();
            ArrayList<String> imagePathList = new ArrayList<>();
            imagePathList.add(String.valueOf(pref.getSharedData("ShopPhoto_Image_1")));
            imagePathList.add(String.valueOf(pref.getSharedData("ShopPhoto_Image_2")));
            imagePathList.add(String.valueOf(pref.getSharedData("ShopPhoto_Image_3")));
            imagePathList.add(String.valueOf(pref.getSharedData("ShopPhoto_Image_4")));
            imagePathList.add(String.valueOf(pref.getSharedData("ShopPhoto_Image_5")));

            String delimiter = "--" + boundary + "\r\n";
            Log.d("ShopPhoto_Image", "imagePath size :" + imagePath.size());

            // sendID();



            Log.d("ShopPhoto_Image", "OutputStream URL :" + reviewData);

            postDataBuilder.append("\r\n");
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("reviewid",review0));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("user",review2));
            for (int i = 0; i < imagePathList.size(); i++) {
                if (imagePathList.get(i).equals("")) {
                    break;
                } else {
                    imagePath.add(i, imagePathList.get(i));
                    File sourceFile = new File(imagePathList.get(i));
                    String fileName = new File(imagePathList.get(i)).getName();
                    Log.d("ShopPhoto_Image", "File sourceFile " + i + "번:" + sourceFile);
                    Log.d("ShopPhoto_Image", "String fileName" + i + "번:" + fileName);
                    Log.d("ShopPhoto_Image", "imagePath add" + i + "번:" + imagePathList.get(i));
//                conn.setRequestProperty("uploaded_file0", fileName0);
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("uploaded_file" + i, fileName));
                    postDataBuilder.append("\r\n");

                }
            }

            dos.writeUTF(postDataBuilder.toString());

            String postCheck = String.valueOf(postDataBuilder);
            Log.d("POSTDATABUILDER",postCheck);

            /**
             * REVIEW ID (SHOP ID)
             */
            dos.writeShort(0x0d0a);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("reviewid");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(review0);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            /**
             * REVIEW (USER ID)
             */
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("user");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(review2);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);




            for (int i = 0; i < imagePath.size(); i++) {
                // write data
                FileInputStream mFileInputStream = new FileInputStream(imagePath.get(i));

                Log.d("ShopPhoto_Image", "FOR COUNT :" + i);
                dos = new DataOutputStream(conn.getOutputStream());
                File sourceFile = new File(imagePath.get(i));
                String fileName = new File(imagePath.get(i)).getName();
                if (!sourceFile.isFile()) {
                    Log.d("ShopPhoto_Image", "sourceFile is not exist!!!! :" + imagePath.get(i));
                }
                Log.d("ShopPhoto_Image", "mFileInputStream  is " + mFileInputStream);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file" + i + "\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes("Content-Type: application/octet-stream");
                dos.writeBytes(lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 8 * 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                Log.d("ShopPhoto_Image", "image byte is " + bytesRead);

                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }
                dos.writeBytes(lineEnd);
                mFileInputStream.close();
            }

            Log.e("ShopPhoto_Image", "File is written");

            dos.writeBytes("\r\n--" + boundary + "--\r\n");
            dos.flush(); // finish upload...
            dos.close();
            int responseCode = conn.getResponseCode();
            Log.e("ShopPhoto_Image", "response code - " + responseCode);
            conn.disconnect();

        } catch (Exception e) {
            eraseSharedPref(mContext);
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
        eraseSharedPref(mContext);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        } catch (Exception e) {
        }

    }


    public void eraseSharedPref(Context mContext) {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        pref.setSharedData("ShopPhoto_Image_1", "");
        pref.setSharedData("ShopPhoto_Image_2", "");
        pref.setSharedData("ShopPhoto_Image_3", "");
        pref.setSharedData("ShopPhoto_Image_4", "");
        pref.setSharedData("ShopPhoto_Image_5", "");
        pref.setSharedData("HTTP_REVIEW_ID","");
        pref.setSharedData("HTTP_REVIEW_REVIEW","");
        pref.setSharedData("HTTP_REVIEW_USER","");
        pref.setSharedData("HTTP_REVIEW_RATING","");

    }

    /**
     * Map 형식으로 Key와 Value를 셋팅한다.
     *
     * @param key   : 서버에서 사용할 변수명
     * @param value : 변수명에 해당하는 실제 값
     * @return
     */
    public static String setValue(String key, String value) {
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
    public static String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key
                + "\";filename=\"" + fileName + "\"\r\n";
    }
}
