package com.teamnova.ej.realreview.Asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.teamnova.ej.realreview.activity.Main.LOCATION_FAR_LEFT_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_FAR_LEFT_LNG;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_NEAR_RIGHT_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_NEAR_RIGHT_LNG;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_USER_LAT;
import static com.teamnova.ej.realreview.activity.Main.LOCATION_USER_LNG;

/**
 * Created by ej on 2017-10-08.
 */

public class AsyncReviewSubmit extends AsyncTask<Void, Integer, Void> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private String urlString;
    private String params = "";
    Context mContext;
    String TestVAR;
    private String reviewData;
    private String userLocationCheckResult;
    private String localityCheckResult;
    private MaterialDialog builder;

    public AsyncReviewSubmit(Context mContext) {
        this.mContext = mContext;
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

        try {

            /**
             * Nearby & Locality
             */


            if (
                    LOCATION_USER_LAT > LOCATION_NEAR_RIGHT_LAT &&
                            LOCATION_USER_LAT < LOCATION_FAR_LEFT_LAT &&
                            LOCATION_USER_LNG > LOCATION_FAR_LEFT_LNG &&
                            LOCATION_USER_LNG < LOCATION_NEAR_RIGHT_LNG
                    ) {
                userLocationCheckResult = "1";    // TRUE
                Log.d("ASYNC_TIP", "Nearby True");
            } else {
                userLocationCheckResult = "0";    // FALSE
                Log.d("ASYNC_TIP", "Nearby False");
            }

            Log.d("ASYNC_TIP", "LOCATION_USER_LAT :" + LOCATION_USER_LAT + ">" + LOCATION_NEAR_RIGHT_LAT);
            Log.d("ASYNC_TIP", "LOCATION_USER_LAT :" + LOCATION_USER_LAT + "<" + LOCATION_FAR_LEFT_LAT);
            Log.d("ASYNC_TIP", "LOCATION_USER_LNG :" + LOCATION_USER_LNG + ">" + LOCATION_FAR_LEFT_LNG);
            Log.d("ASYNC_TIP", "LOCATION_USER_LNG :" + LOCATION_USER_LNG + "<" + LOCATION_NEAR_RIGHT_LNG);

            SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);

            String tag = pref.getSharedData("TAG");
            Log.d("ASYNC_TIP", "Locality, tag :" + tag);

            double shopLat = Double.parseDouble(pref.getSharedData("LAT" + tag));
            Log.d("ASYNC_TIP", "Locality, shopLat " + shopLat);

            double shopLng = Double.parseDouble(pref.getSharedData("LNG" + tag));
            Log.d("ASYNC_TIP", "Locality, shopLng " + shopLng);

            double lat = Double.parseDouble(pref.getSharedData("isLogged_lat"));
            Log.d("ASYNC_TIP", "Locality, lat " + lat);

            double lng = Double.parseDouble(pref.getSharedData("isLogged_lng"));
            Log.d("ASYNC_TIP", "Locality, lng " + lng);

            double SW_Lat = Double.parseDouble(pref.getSharedData("isLogged_SW_Lat"));
            Log.d("ASYNC_TIP", "Locality, SW_Lat " + SW_Lat);

            double SW_Lng = Double.parseDouble(pref.getSharedData("isLogged_SW_Lng"));
            Log.d("ASYNC_TIP", "Locality, SW_Lng " + SW_Lng);

            double NE_Lat = Double.parseDouble(pref.getSharedData("isLogged_NE_Lat"));
            Log.d("ASYNC_TIP", "Locality, NE_Lat " + NE_Lat);

            double NE_Lng = Double.parseDouble(pref.getSharedData("isLogged_NE_Lng"));
            Log.d("ASYNC_TIP", "Locality, NE_Lng " + NE_Lng);


//        if (    lat > SW_Lng &&
//                lat < NE_Lng &&
//                lng > NE_Lat &&
//                lng < SW_Lat )
            if (shopLat > SW_Lat &&
                    shopLat < NE_Lat &&
                    shopLng > SW_Lng &&
                    shopLng < NE_Lng) {
                localityCheckResult = "1";    // TRUE
                Log.d("ASYNC_TIP", "localityCheckResult True");
            } else {
                localityCheckResult = "0";    // FALSE
                Log.d("ASYNC_TIP", "localityCheckResult False");
            }

            Log.d("ASYNC_TIP", "shopLat  :" + shopLat + ">" + SW_Lat);
            Log.d("ASYNC_TIP", "shopLat  :" + shopLat + "<" + NE_Lat);
            Log.d("ASYNC_TIP", "shopLng  :" + shopLng + ">" + SW_Lng);
            Log.d("ASYNC_TIP", "shopLng  :" + shopLng + "<" + NE_Lng);


            String sURL = "http://222.122.203.55/realreview/shopimage/reviewImageUpload.php";

            String review0 = pref.getSharedData("HTTP_REVIEW_ID");
            String review1 = pref.getSharedData("HTTP_REVIEW_REVIEW");
            String review2 = pref.getSharedData("HTTP_REVIEW_USER");
            String review3 = pref.getSharedData("HTTP_REVIEW_RATING");
            String review4 = pref.getSharedData("HTTP_REVIEW_NICK");
//            reviewData = sURL + "?" + "reviewid=" + review0 + "&review=" + review1 + "&user=" + review2 + "&rating=" + review3 + "&nick=" + review4 ;
            //            Log.d("REVIEW_Image", "OutputStream URL :" + reviewData);

            Log.d("REVIEW_Image", "String review0 :" + review0);
            Log.d("REVIEW_Image", "String review1 :" + review1);
            Log.d("REVIEW_Image", "String review2 :" + review2);
            Log.d("REVIEW_Image", "String review3 :" + review3);
            Log.d("REVIEW_Image", "String review4 :" + review4);


            StringBuffer postDataBuilder = new StringBuffer();

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


            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            URL connectUrl = new URL(sURL);
            Log.d("REVIEW_Image", "URL :" + String.valueOf(connectUrl));


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
            imagePathList.add(String.valueOf(pref.getSharedData("REVIEW_IMAGE_1")));
            imagePathList.add(String.valueOf(pref.getSharedData("REVIEW_IMAGE_2")));
            imagePathList.add(String.valueOf(pref.getSharedData("REVIEW_IMAGE_3")));
            imagePathList.add(String.valueOf(pref.getSharedData("REVIEW_IMAGE_4")));
            imagePathList.add(String.valueOf(pref.getSharedData("REVIEW_IMAGE_5")));

            String delimiter = "--" + boundary + "\r\n";
            Log.d("REVIEW_Image", "imagePath size :" + imagePath.size());

            // sendID();


            postDataBuilder.append("\r\n");
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("reviewid", review0));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("review", review1));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("user", review2));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("nick", review4));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("rating", review3));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("locality", localityCheckResult));
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue("nearby", userLocationCheckResult));
            for (int i = 0; i < imagePathList.size(); i++) {
                if (imagePathList.get(i).equals("")) {
                    break;
                } else {
                    imagePath.add(i, imagePathList.get(i));
                    File sourceFile = new File(imagePathList.get(i));
                    String fileName = new File(imagePathList.get(i)).getName();
                    Log.d("REVIEW_Image", "File sourceFile " + i + "번:" + sourceFile);
                    Log.d("REVIEW_Image", "String fileName" + i + "번:" + fileName);
                    Log.d("REVIEW_Image", "imagePath add" + i + "번:" + imagePathList.get(i));
//                conn.setRequestProperty("uploaded_file0", fileName0);
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("uploaded_file" + i, fileName));
                    postDataBuilder.append("\r\n");

                }
            }

            dos.writeUTF(postDataBuilder.toString());

            String postCheck = String.valueOf(postDataBuilder);
            Log.d("REVIEW_Image", "postCheck :" + postCheck);

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
             * REVIEW (REVIEW TEXT)
             */
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("review");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(review1);
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
            /**
             * REVIEW (USER ID)
             */
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("nick");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(review4);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);

            /**
             * REVIEW (RATING)
             */
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("rating");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(review3);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);

            /**
             * REVIEW (Locality)
             */
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("locality");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(localityCheckResult);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);

            /**
             * REVIEW (Nearby)
             */

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=");
            dos.writeBytes("nearby");
            dos.writeBytes("\"");
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);
            dos.writeBytes(userLocationCheckResult);
            dos.writeShort(0x0d0a);
            dos.writeShort(0x0d0a);


            for (int i = 0; i < imagePath.size(); i++) {
                // write data
                FileInputStream mFileInputStream = new FileInputStream(imagePath.get(i));

                Log.d("REVIEW_Image", "FOR COUNT :" + i);
                dos = new DataOutputStream(conn.getOutputStream());
                File sourceFile = new File(imagePath.get(i));
                String fileName = new File(imagePath.get(i)).getName();
                if (!sourceFile.isFile()) {
                    Log.d("REVIEW_Image", "sourceFile is not exist!!!! :" + imagePath.get(i));
                }
                Log.d("REVIEW_Image", "mFileInputStream  is " + mFileInputStream);

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

                Log.d("REVIEW_Image", "image byte is " + bytesRead);

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

            Log.e("REVIEW_Image", "File is written");
//            mFileInputStream.close();
            // close streams
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            dos.writeBytes("\r\n--" + boundary + "--\r\n");
            dos.flush(); // finish upload...
            // get response
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            Log.e("REVIEW_Image", "b = " + b);
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("REVIEW_Image", "s = " + s);


            // mEdityEntry.setText(s);
            dos.close();
            int responseCode = conn.getResponseCode();
            Log.e("REVIEW_Image", "response code - " + responseCode);
            conn.disconnect();

        } catch (Exception e) {
            eraseSharedPref(mContext);
            Log.d("REVIEW_Image", "exception :" + e.getMessage());
            // TODO: handle exception
        }

        eraseSharedPref(mContext);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        builder.dismiss();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    public void eraseSharedPref(Context mContext) {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(mContext);
        pref.setSharedData("REVIEW_IMAGE_1", "");
        pref.setSharedData("REVIEW_IMAGE_2", "");
        pref.setSharedData("REVIEW_IMAGE_3", "");
        pref.setSharedData("REVIEW_IMAGE_4", "");
        pref.setSharedData("REVIEW_IMAGE_5", "");
        pref.setSharedData("HTTP_REVIEW_ID", "");
        pref.setSharedData("HTTP_REVIEW_REVIEW", "");
        pref.setSharedData("HTTP_REVIEW_USER", "");
        pref.setSharedData("HTTP_REVIEW_RATING", "");
        pref.setSharedData("HTTP_REVIEW_NICK", "");

    }

    /**
     * Map 형식으로 Key와 Value를 셋팅한다.
     *
     * @param key   : 서버에서 사용할 변수명
     * @param value : 변수명에 해당하는 실제 값
     * @return
     */
    public String setValue(String key, String value) {
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
    public String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key
                + "\";filename=\"" + fileName + "\"\r\n";
    }
}
