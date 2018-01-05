package com.teamnova.ej.realreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.Asynctask.AsyncCheckinUpload_Text;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;

import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.teamnova.ej.realreview.activity.Intro.HOST_ADDRESS;
import static com.teamnova.ej.realreview.activity.ShopDetail_Checkin_OpenCV.FILENAME_FACEDETECT_IMAGE;
import static com.teamnova.ej.realreview.activity.ShopDetail_Checkin_OpenCV.FILEPATH_FACEDETECT_IMAGE;


/**
 * @author devil1032@gmail.com, Dashhh
 * @Create 2017/12/25
 * @Description : Check in 기능을 사용하기 위한 Activity이다.
 * : 주 목적은 다음과 같다
 * - 서버에 "Check in" 글 등록
 * - Opencv Camera를 이용한 인물 사진 획득 및 저장 (저장은 서버까지)
 * - Preview Camera는(변수명 : mOpenCvCameraView) 단순히 "이 곳을 누르면 카메라가 켜진다"는 암시일뿐이다. Touch 할 경우 ShopDetail_Checkin_OpenCV.activity로 이동 된다.
 * - ShopDetail_Checkin_OpenCV.activity로 이동 후에 얼굴 이미지를 획득 -> 글 등록의 순서로 기능이 진행 된다.
 * - 등록 된 글은 ShopDetail_Main.activity 상단에 노출 되도록 구현했다.("최근 다녀감"이라는 TEXT로 표시)
 */

public class ShopDetail_Checkin_Submit extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG_OPENCV = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean mIsJavaCamera = true;
    private MenuItem mItemSwitchCamera = null;
    private ImageView shopDetail_Checkin_Opencv_faceImage;
    com.beardedhen.androidbootstrap.BootstrapButton shopDetail_Checkin_Opencv_submit;
    com.beardedhen.androidbootstrap.BootstrapEditText shopDetail_Checkin_Opencv_review;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG_OPENCV, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
    public static String JSONOBJECT_CHECKIN_IMAGEURL;


    public ShopDetail_Checkin_Submit() {
        Log.i(TAG_OPENCV, "Instantiated new " + this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__checkin__submit);
        Log.i(TAG_OPENCV, "called onCreate");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        init();
        listener();

    }

    private void init() {


        FILEPATH_FACEDETECT_IMAGE = "nothing";

        Log.d("OpenCV_Check", "init-Enter");
        Log.d("OpenCV_Check", "FilePath Check Filepath : " + FILEPATH_FACEDETECT_IMAGE);
        shopDetail_Checkin_Opencv_faceImage = findViewById(R.id.shopDetail_Checkin_Opencv_faceImage);

        mOpenCvCameraView = findViewById(R.id.tutorial1_activity_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        shopDetail_Checkin_Opencv_submit = findViewById(R.id.shopDetail_Checkin_Opencv_submit);
        shopDetail_Checkin_Opencv_review = findViewById(R.id.shopDetail_Checkin_Opencv_review);

    }

    public static String RESULT_CHECKIN_UPLOAD_RETURN_FILEIMAGENAME = "";

    private void listener() {

        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopDetail_Checkin_Submit.this, ShopDetail_Checkin_OpenCV.class);
                startActivity(intent);
            }
        });


        shopDetail_Checkin_Opencv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int conn;
                //                    conn = new AsyncCheckinUpload_Image(ShopDetail_Checkin_Submit.this, FILEPATH_FACEDETECT_IMAGE).execute().get(10000, TimeUnit.MILLISECONDS);


                new Thread(new Runnable() {
                    public void run() {
                        RESULT_CHECKIN_UPLOAD_RETURN_FILEIMAGENAME = uploadFile(FILEPATH_FACEDETECT_IMAGE);

                        Log.e("uploadFile", "UploadFile Start -> UrParseImage:" + FILEPATH_FACEDETECT_IMAGE);
                        Log.e("uploadFile", "UploadFile Start -> UrParseImage:" + RESULT_CHECKIN_UPLOAD_RETURN_FILEIMAGENAME);
                    }
                }).start();

//                    if(conn == HTTP_OK){
//                        JSONObject textUpload = new JSONObject(String.valueOf(conn));
//                        JSONArray textUpload_toArray =  textUpload.getJSONArray("checkin_result");
//
//                        textUpload = new AsyncCheckinUpload_Text(ShopDetail_Checkin_Submit.this, shopDetail_Checkin_Opencv_review.getText().toString(), );
//                    }

//                    Log.d("OpenCV_Check", "AsyncCheckinUpload_Image, Response code" + conn);


                JSONObject asyncResult_afterCheckinDataSubmit;
                try {

                    String userDescriptionText = shopDetail_Checkin_Opencv_review.getText().toString();
                    String.valueOf(userDescriptionText);


                    if (!userDescriptionText.equals("null")) {
                        asyncResult_afterCheckinDataSubmit = new AsyncCheckinUpload_Text(
                                ShopDetail_Checkin_Submit.this,
                                userDescriptionText,
                                HOST_ADDRESS + "checkin/upload/" + FILENAME_FACEDETECT_IMAGE )
                                .execute().get(10000, TimeUnit.MILLISECONDS);
                        finish();

                    } else {
                        Dialog_Default dial = new Dialog_Default(ShopDetail_Checkin_Submit.this);
                        dial.callMaterialDefault("WARNING", "내용을 입력해주세요!");

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FILEPATH_FACEDETECT_IMAGE.equals("nothing"))
            shopDetail_Checkin_Opencv_faceImage.setVisibility(View.GONE);
        else {
            Log.d("OpenCV_Check", "Glide-Enter");
            Log.d("OpenCV_Check", "FilePath Check Filepath : " + FILEPATH_FACEDETECT_IMAGE);

            shopDetail_Checkin_Opencv_faceImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(FILEPATH_FACEDETECT_IMAGE).thumbnail(0.8f).into(shopDetail_Checkin_Opencv_faceImage);
        }

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG_OPENCV, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d(TAG_OPENCV, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {


        //TODO : Core.rorate()  :  Camera 방향 시계방향으로 90도 돌리기


    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        return inputFrame.rgba();
    }


    public String uploadFile(String sourceFileUri) {

        String upLoadServerUri = HOST_ADDRESS + "checkin/checkin_upload_image.php";  // Set Image File at The SERVER

        String fileName = sourceFileUri;
        Log.e("uploadFile", "fileUri : " + fileName);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 32 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        String result = "";
        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist - Uri:" + upLoadServerUri);
            Log.e("uploadFile", "Source File not exist - FileName :" + fileName);

            return "notFile";

        } else {
            int serverResponseCode = 0;

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


                // 전송 결과값 받기
                InputStreamReader inputStream = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader bufferReader = new BufferedReader(inputStream);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = bufferReader.readLine()) != null) {
                    builder.append(str + "\n");
                }
                result = builder.toString();
                Log.d("uploadFile", "전송결과 : " + result);
            } catch (MalformedURLException ex) {

                ex.printStackTrace();
                Log.e("uploadFile", "Upload file to server, " + "error: " + ex.getMessage(), ex);

            } catch (Exception e) {
                Log.e("uploadFile", "Upload file to server Exception, " + "Exception : " + e.getMessage(), e);
            }
            return result;

        } // End else block
    }   // uploadFile


}
