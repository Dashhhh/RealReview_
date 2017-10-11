package com.teamnova.ej.realreview.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main_Test extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {


    public static String ID;
    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_CAMERA = 2;
    public static boolean UPLOAD_FLAG = false;

    private Uri mImageCaptureUri;


    /**
     * filePath Upload Thread Var
     */
    Handler mHandler = new Handler(Looper.getMainLooper());
    String login_url = "http://222.122.203.55/realreview/signup/profileimagepath.php?";
    String strAnd = "&", strId = "id=", strPw = "pw=", strNick = "nick=",
            strPath = "profile_image_path=", urlParse;
    String UrParseImage = "";
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON = "realreview";
    private static final String TAG_NICK = "nick";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    String tempNick, tempNick2;


    /**
     * Server Thread Var
     */
    int serverResponseCode = 0;
    ProgressBar dialog = null;
    /**********  File Path *************/
    final String uploadFilePath = "storage/emulated/0/Pictures/";//경로를 모르겠으면, 갤러리 어플리케이션 가서 메뉴->상세 정보
    final String uploadFileName = "Instagram/IMG_20170715_043200_310.jpg"; //전송하고자하는 파일 이름
    TextView messageText;
    Button uploadButton;
    String realPath = "";
    String upLoadServerUri = "http://222.122.203.55/realreview/signup/uploadtoserver.php";  // Set Image File at The SERVER
    /**
     * init VAR
     */

    LinearLayout nearbyLinear, searchLinear, meLinear, meLinearSecond, meProfileStateLayout, meMLinearMyFeed;
    ListView meMyFeedListView;
    FrameLayout content;
    ScrollView map_container, meScrollView;
    BottomNavigationView navigation;
    private GoogleMap mMap;
    TextView searchText, meProfileId, meProfileNick, meFollowerText, meReviewText, imageUploadText;
    ImageView meProfileImage, followerCntImage, reviewCntImage, imageUploadCnt;
    private String imagePath;
    Button nearbyShopAdd;


    //    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__test);


        init();
        defineBottomNavi();
        listener();



    }   // onCreate

    private void defineBottomNavi() {

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_nearby:
                        nearbyLinear.setVisibility(View.VISIBLE);
                        searchLinear.setVisibility(View.GONE);
                        meLinear.setVisibility(View.GONE);
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.mapView);
                        mapFragment.getMapAsync(Main_Test.this);
                        return true;
                    case R.id.navigation_search:
                        nearbyLinear.setVisibility(View.GONE);
                        searchLinear.setVisibility(View.VISIBLE);
                        meLinear.setVisibility(View.GONE);
                        SupportMapFragment searchMapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.mapView);
                        searchMapFragment.getMapAsync(Main_Test.this);

                        return true;
                    case R.id.navigation_me:
                        searchLinear.setVisibility(View.GONE);
                        nearbyLinear.setVisibility(View.GONE);
                        meLinear.setVisibility(View.VISIBLE);


                        return true;
                }
                return false;
            }

        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_nearby);

    }

    private void init() {

        searchLinear = (LinearLayout) findViewById(R.id.searchLinear);
        nearbyLinear = (LinearLayout) findViewById(R.id.nearbyLinear);
        meLinear = (LinearLayout) findViewById(R.id.meLinear);
        content = (FrameLayout) findViewById(R.id.content);
        map_container = (ScrollView) findViewById(R.id.map_container);    // onCreate Init
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        meProfileId = (TextView) findViewById(R.id.meProfileId);
        meProfileNick = (TextView) findViewById(R.id.meProfileNick);
        meFollowerText = (TextView) findViewById(R.id.meFollowerText);
        meReviewText = (TextView) findViewById(R.id.meReviewText);
        imageUploadText = (TextView) findViewById(R.id.imageUploadText);
        meProfileImage = (ImageView) findViewById(R.id.meProfileImage);
        followerCntImage = (ImageView) findViewById(R.id.followerCntImage);
        reviewCntImage = (ImageView) findViewById(R.id.reviewCntImage);
        imageUploadCnt = (ImageView) findViewById(R.id.imageUploadCnt);
        meMyFeedListView = (ListView) findViewById(R.id.meMyFeedListView);
        nearbyShopAdd = (Button) findViewById(R.id.nearbyShopAdd);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.meProfileImage: {    // Bottom - Me

                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(this)
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
                break;

            }

            case R.id.nearbyShopAdd: {

                Intent intent = new Intent(Main_Test.this, ShopAdd1.class);
                startActivity(intent);
                break;

            }

        }

    }


    @Override
    protected void onResume() {
        super.onResume();


        if (UPLOAD_FLAG) {

            new Thread(new Runnable() {
                public void run() {
                    uploadFile(imagePath);
                    Log.e("UploadFile Start -> UrParseImage:", imagePath);
                }
            }).start();

            try {
                filePathThread fpt = new filePathThread();
                fpt.start();
                fpt.join();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            UPLOAD_FLAG = false;
        }


    }

    private void listener() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        String imagePath = pref.getSharedData("isLogged_profileImagePath");

        if (imagePath.isEmpty()) {
            Glide.with(Main_Test.this)
                    .load("http://222.122.203.55/realreview/signup/profiledefault/homeme_default.jpg")
                    .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                    .into(meProfileImage);
        } else {
            Glide.with(Main_Test.this)
                    .load(imagePath)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                    .into(meProfileImage);
        }

        meProfileImage.setOnClickListener(this);
        nearbyShopAdd.setOnClickListener(this);

    }


    @Override
    public void onMapReady(GoogleMap map) {

            mMap = map;
            mMap = map;
//        // check if we have got the googleMap already
//        if (mMap == null) {
//            mMap = ((MapTouchWrapper) getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap();
//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            mMap.getUiSettings().setZoomControlsEnabled(true);
//            map_container = (ScrollView) findViewById(R.id.map_container); //parent scrollview in xml, give your scrollview id value
//
//            ((MapTouchWrapper) getSupportFragmentManager().findFragmentById(R.id.mapView))
//                    .setListener(new MapTouchWrapper.OnTouchListener() {
//                        @Override
//                        public void onTouch() {
//                            map_container.requestDisallowInterceptTouchEvent(true);
//                        }
//                    });
//        }


        LatLng officeFirst = new LatLng(37.48408310967865, 126.97256952524185);
        LatLng officeSecond = new LatLng(37.484108650390326, 126.97206526994705);
        LatLng officeThird = new LatLng(37.48404479859481, 126.97373896837234);

        mMap.addMarker(new MarkerOptions().position(officeThird).title("~TEAM NOVA 3, 4사무실~"));
        mMap.addMarker(new MarkerOptions().position(officeFirst).title("~TEAM NOVA 1사무실~"));
        mMap.addMarker(new MarkerOptions().position(officeSecond).title("~TEAM NOVA 2사무실~"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(officeThird));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

    }

    /**
     * CAMERA
     */


    /**
     * 카메라에서 이미지 가져오기
     */

    private void doTakePhotoAction() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "DCIM/AT" + String.valueOf(System.currentTimeMillis())
                + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                mImageCaptureUri);
        intent.putExtra("return-data", true);
//        startActivityForResult(intent, PICK_FROM_CAMERA);
        startActivityForResult(Intent.createChooser(intent, "실행 할 카메라 선택"), PICK_FROM_CAMERA);

    }


//    /*
//     * 참고 해볼곳
//     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
//     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
//     * http://www.damonkohler.com/2009/02/android-recipes.html
//     * http://www.firstclown.us/tag/android/
//     */

    /**
     * 앨범에서 이미지 가져오기
     */

    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                mImageCaptureUri = data.getData();
//                realPath = getRealPathFromURI(mImageCaptureUri);

            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

//                intent.putExtra("outputX", 1000);
//                intent.putExtra("outputY", 1000);
//                intent.putExtra("aspectX", 1);
//                intent.putExtra("aspectY", 1);
//                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

//                    realPath = getPath(mImageCaptureUri);
//                    UrParseImage = realPath;
//                    String fileName = new File(realPath).getName();
//                    realPath = fileName;

                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }

            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();
                Toast.makeText(this, "CROP FROM CAMERA!", Toast.LENGTH_SHORT).show();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    Glide.with(Main_Test.this)
                            .load(mImageCaptureUri)
                            .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                            .into(meProfileImage);

                } else {
                    Dialog_Default dial = new Dialog_Default(this);
                    dial.call("Image Warning", "문제가 발생 했습니다.");
                }
                /**
                 *
                 * Recent Camera Image File PATH
                 */
                String fileName = null;
                File[] listFiles = (new File(Environment.getExternalStorageDirectory() + "/dcim/camera/").listFiles());

                if (listFiles[0].getName().endsWith(".jpg") || listFiles[0].getName().endsWith(".bmp"))
                    fileName = listFiles[0].getName();

                File file = new File(Environment.getExternalStorageDirectory() + "/dcim/" + fileName);

//                Log.e("CAMERA RECENT FILE :", String.valueOf(file));
//                Log.e("CAMERA RECENT FILE(String) :", fileName);
                Log.e("mImageCaptureUri Value --->>> :", String.valueOf(mImageCaptureUri));


                imagePath = getPath(mImageCaptureUri);
                Log.e("Image Path:", imagePath);

//                        uploadFile("/storage/emulated/0/dcim/Camera/7495c41036542d76bfe7aef7d9746641.jpg");
                UPLOAD_FLAG = true;

                // 임시 파일 삭제
//                File f = new File(mImageCaptureUri.getPath());
//
//                if (f.exists()) {
//                    f.delete();
//                }

                break;
            }

        }
    }
//
//    private class fixProfileImage extends AsyncTask<String, Integer, Long> {
//
//
//        public fixProfileImage() {
//            super();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected Long doInBackground(String... sourceFileUri) {
////            String fileName = String.valueOf(sourceFileUri);
////
////            HttpURLConnection conn = null;
////            DataOutputStream dos = null;
////            String lineEnd = "\r\n";
////            String twoHyphens = "--";
////            String boundary = "*****";
////            int bytesRead, bytesAvailable, bufferSize;
////            byte[] buffer;
////            int maxBufferSize = 1 * 1024 * 1024;
////            File sourceFile = new File(String.valueOf(sourceFileUri));
////
////            if (!sourceFile.isFile()) {
////
////                Log.e("uploadFile", "Source File not exist :" + upLoadServerUri);
////                return Long.valueOf(0);
////
////            } else {
////                try {
////
////                    // open a URL connection to the Servlet
////                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
////                    URL url = new URL(upLoadServerUri);
////
////                    // Open a HTTP  connection to  the URL
////                    conn = (HttpURLConnection) url.openConnection();
////                    Log.e("SERVER URL :", String.valueOf(url));
////                    conn.setDoInput(true); // Allow Inputs
////                    conn.setDoOutput(true); // Allow Outputs
////                    conn.setUseCaches(false); // Don't use a Cached Copy
////                    conn.setRequestMethod("POST");
////                    conn.setRequestProperty("Connection", "Keep-Alive");
////                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
////                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
////                    conn.setRequestProperty("uploaded_file", fileName);
////
////                    dos = new DataOutputStream(conn.getOutputStream());
////
////                    dos.writeBytes(twoHyphens + boundary + lineEnd);
////                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
////                            + fileName + "\"" + lineEnd);
////
////                    dos.writeBytes(lineEnd);
////
////                    // create a buffer of  maximum size
////                    bytesAvailable = fileInputStream.available();
////
////                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
////                    buffer = new byte[bufferSize];
////
////                    // read file and write it into form...
////                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
////                    while (bytesRead > 0) {
////
////                        dos.write(buffer, 0, bufferSize);
////                        bytesAvailable = fileInputStream.available();
////                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
////                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
////                    }
////
////                    // send multipart form data necesssary after file data...
////                    dos.writeBytes(lineEnd);
////                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
////
////                    // Responses from the server (code and message)
////                    serverResponseCode = conn.getResponseCode();
////                    String serverResponseMessage = conn.getResponseMessage();
////
////                    Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
////
////                    if (serverResponseCode == 200) {
////
////                        runOnUiThread(new Runnable() {
////                            public void run() {
////
////                                String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
////                                        + upLoadServerUri;
////
////                                messageText.setText(msg);
////                                Toast.makeText(Main_Test.this, "File Upload Complete.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        });
////                    }
////
////                    //close the streams //
////                    fileInputStream.close();
////                    dos.flush();
////                    dos.close();
////
////                } catch (MalformedURLException ex) {
////
////                    ex.printStackTrace();
////
////                    runOnUiThread(new Runnable() {
////                        public void run() {
////                            messageText.setText("MalformedURLException Exception : check script url.");
////                            Toast.makeText(Main_Test.this, "MalformedURLException",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    });
////
////                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
////                } catch (Exception e) {
////
////                    e.printStackTrace();
////
////                    runOnUiThread(new Runnable() {
////                        public void run() {
////                            messageText.setText("Got Exception : see logcat ");
////                            Toast.makeText(Main_Test.this, "Got Exception : see logcat ",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    });
////                    Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
////                }
////                return Long.valueOf(serverResponseCode);
////
////            } // End else block
//////            return null;
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//
//        }
//
//        @Override
//        protected void onPostExecute(Long aLong) {
//            super.onPostExecute(aLong);
//            Dialog_Default dial = new Dialog_Default(getApplicationContext());
//            dial.call("Profile Image", "수정이 완료 되었습니다.");
//
//        }
//
//        @Override
//        protected void onCancelled(Long aLong) {
//            super.onCancelled(aLong);
//            Dialog_Default dial = new Dialog_Default(getApplicationContext());
//            dial.call("Profile Image", "프로필 이미지 수정에 실패했습니다.");
//
//        }
//    }


    public String getPath(Uri uri) {
        // uri가 null일경우 null반환
        if (uri == null) {
            return null;
        }
        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // URI경로를 반환한다.
        return uri.getPath();
    }   // getPath();


    public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

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
                Log.e("SERVER URL :", String.valueOf(url));
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

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    Log.e("SERVER RESPONSE CODE", String.valueOf(serverResponseCode));

//                    runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
//                                    + upLoadServerUri;
//
//                            messageText.setText(msg);
//                            Toast.makeText(Main_Test.this, "File Upload Complete.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
            }
            return serverResponseCode;

        } // End else block
    }   // uploadFile

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class filePathThread extends Thread {

        public filePathThread() throws UnsupportedEncodingException {

            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
            String time = sdfNow.format(new Date(System.currentTimeMillis()));
            Log.e("TIME", "TIME:" + time);

            SharedPreferenceUtil pref = new SharedPreferenceUtil(Main_Test.this);
            strId += pref.getSharedData("isLogged_id");

            String aa = getPath(mImageCaptureUri);
            String fileName = new File(aa).getName();
            Log.e("AA -> Image GET NAME(mImageCaptureUri)", aa);
            String strPathEncode = fileName;
            Log.e("filePathThread", "FILE PATH THREAD - fileName :" + fileName);
            String tempPath = URLEncoder.encode(strPathEncode, "UTF-8");
            strPath += tempPath;

            urlParse = login_url + strId + strAnd + strPath;

        }

        public void run() {
            super.run();
            try {
                URL phpUrl = new URL(urlParse);
                UrParseImage = String.valueOf(phpUrl);
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
//                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                Log.e("phpURL", "phpURL:" + phpUrl);
                Log.e("urlParse", "urlParse:" + urlParse);
                conn.connect();
                int responseStatusCode = conn.getResponseCode();
                Log.e(TAG, "response code - " + responseStatusCode);
                conn.disconnect();
                strAnd = "&";
                strId = "id=";
                strPw = "pw=";
                strNick = "nick=";
                strPath = "profile_image_path=";

            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferenceUtil pref = new SharedPreferenceUtil(getApplicationContext());

        }
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(getString(R.string.app_name))
                .setMessage("App을 종료 하시겠습니까??")
                .setPositiveButton("그만할래요", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        SharedPreferenceUtil pref = new SharedPreferenceUtil(Main_Test.this);
                        finish();
                    }
                }).setNegativeButton("계속하기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

            }
        })
                .create()
                .show();
    }
}
