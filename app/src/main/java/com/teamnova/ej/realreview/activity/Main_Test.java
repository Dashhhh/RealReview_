package com.teamnova.ej.realreview.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.teamnova.ej.realreview.Asynctask.AsyncMainNearbyLatLngReceive;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.Main_Test_SearchMap;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main_Test extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, LocationListener {

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
    FusedLocationProviderClient mFusedLocationClient;
    private boolean mRequestingLocationUpdates;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;


    //    SupportMapFragment mapFragment;

    /**
     * Receive Location Data
     */


    //위치정보 객체
    LocationManager lm = null;
    //위치정보 장치 이름
    String provider = null;
    private MapFragment mMapFragment;
    private double myPosition_lat;
    private double myPosition_lng;
    private String resultNearRightLng;
    private String resultNearRightLat;
    private String resultFarLeftLat;
    private String resultFarLeftLng;

    public static String locationJson = "";
    public static double MY_POSITION_LAT;
    public static double MY_POSITION_LNG;

    public static StringBuilder HTTP_RECEIVE_SHOPDATA;

    public String replaceTest = "";
    ArrayList<String> fixShopDataList = new ArrayList<>();
    ArrayList<String> keyShopDataList = new ArrayList<>();
    ArrayList<String> valueShopDataList = new ArrayList<>();

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

                        mMapFragment = MapFragment.newInstance();
                        FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.searchLinear, mMapFragment);
                        fragmentTransaction.commit();
                        MapFragment searchFragment = (MapFragment) getFragmentManager()
                                .findFragmentById(R.id.searchMap);
                        Main_Test_SearchMap inst = new Main_Test_SearchMap(Main_Test.this);
                        searchFragment.getMapAsync(inst);

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
        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        pref.setSharedData("LOCATION_FLAG", "TRUE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__test);

        init();
        defineBottomNavi();
        listener();
        receiveLocationData();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });


        Log.d("Main_Test - onCreate", "MY_POSITION_LAT :" + MY_POSITION_LAT);
        Log.d("Main_Test - onCreate", "MY_POSITION_LNG :" + MY_POSITION_LNG);

        String url = "http://222.122.203.55/realreview/Nearby/latlng.php?";
        String urlMerge = url + "lat_start=" + resultNearRightLat + "&lat_end=" + resultFarLeftLat + "&lng_start=" + resultFarLeftLng + "&lng_end=" + resultNearRightLng;
        ProgressDialog progressDialog = new ProgressDialog(this);
        AsyncMainNearbyLatLngReceive upload = new AsyncMainNearbyLatLngReceive(urlMerge, progressDialog, this);
        upload.execute();


    }   // onCreate

    private void receiveLocationData() {

        /**위치정보 객체를 생성한다.*/
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /** 현재 사용가능한 위치 정보 장치 검색*/
        //위치정보 하드웨어 목록
        Criteria c = new Criteria();
        //최적의 하드웨어 이름을 리턴받는다.
        provider = lm.getBestProvider(c, true);

        // 최적의 값이 없거나, 해당 장치가 사용가능한 상태가 아니라면,
        //모든 장치 리스트에서 사용가능한 항목 얻기
        if (provider == null || !lm.isProviderEnabled(provider)) {
            // 모든 장치 목록
            List<String> list = lm.getAllProviders();

            for (int i = 0; i < list.size(); i++) {
                //장치 이름 하나 얻기
                String temp = list.get(i);

                //사용 가능 여부 검사
                if (lm.isProviderEnabled(temp)) {
                    provider = temp;
                    break;
                }
            }
        }// (end if)위치정보 검색 끝

        /**마지막으로  조회했던 위치 얻기*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(provider);

        if (location == null) {
            Toast.makeText(this, "사용가능한 위치 정보 제공자가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            //최종 위치에서 부터 이어서 GPS 시작...
            onLocationChanged(location);

        }
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
        Log.d("Main_Test", "ENTER - onResume");
        //위치정보 - Activity LifrCycle 관련 메서드는 무조건 상위 메서드 호출 필요
        /** 이 화면이 불릴 때, 일시정지 해제 처리*/

        //위치정보 객체에 이벤트 연결
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(provider, 5000, 1, this);


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

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }


    }


    @Override
    protected void onPause() {
        //Activity LifrCycle 관련 메서드는 무조건 상위 메서드 호출 필요
        super.onPause();

        //위치정보 객체에 이벤트 해제
        lm.removeUpdates(this);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
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


    /**
     * CAMERA
     */


//    /*
//     * 참고 해볼곳
//     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
//     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
//     * http://www.damonkohler.com/2009/02/android-recipes.html
//     * http://www.firstclown.us/tag/android/

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

    /**
     * 위치가 변했을 경우 호출된다.
     */
    @Override
    public void onLocationChanged(Location location) {
        // 위도, 경도

        myPosition_lat = location.getLatitude();
        myPosition_lng = location.getLongitude();


        // String이외의 데이터 형을 String으로 변환하는 메서드
        // String이외의 데이터 형을 String으로 변화하는 꼼수~!!

        Log.d("MYLOG", "ADDRESS CHECK :" + getAddress(myPosition_lat, myPosition_lng));

        String checkAddress = getAddress(myPosition_lat, myPosition_lng);
        String[] splitAddress = checkAddress.split(" ", 0);
        for (int a = 0; a < splitAddress.length; a++) {
            Log.d("MYLOG", "Address Split :" + splitAddress[a]);
        }

        MY_POSITION_LAT = location.getLatitude();
        MY_POSITION_LNG = location.getLongitude();


    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    /**
     * 위도와 경도 기반으로 주소를 리턴하는 메서드
     */
    public String getAddress(double lat, double lng) {
        String address = null;

        //위치정보를 활용하기 위한 구글 API 객체
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //주소 목록을 담기 위한 HashMap
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list == null) {
            Log.e("getAddress", "주소 데이터 얻기 실패");
            return null;
        }

        if (list.size() > 0) {
            Address addr = list.get(0);
            address = addr.getCountryName() + " "
                    + addr.getPostalCode() + " "
                    + addr.getLocality() + " "
                    + addr.getThoroughfare() + " "
                    + addr.getFeatureName();
        }

        return address;


    }

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

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(this);
        String locationFlag = preferenceUtil.getSharedData("LOCATION_FLAG");

        if (locationFlag.equals("TRUE")) {
            navigation.setSelectedItemId(R.id.navigation_nearby);
            preferenceUtil.setSharedData("LOCATION_FLAG", "FALSE");
        }


        mMap = map;
        mMap.setMyLocationEnabled(true);


        LatLng officeFirst = new LatLng(37.48408310967865, 126.97256952524185);

        String cameraPosition = String.valueOf(mMap.getCameraPosition());
        Log.d("onMapReady", "cameraPosition :" + cameraPosition);

        LatLng myPosition = new LatLng(MY_POSITION_LAT, MY_POSITION_LNG);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));


//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        String sFarLeft = String.valueOf(mMap.getProjection().getVisibleRegion().farLeft);
        String sNearRight = String.valueOf(mMap.getProjection().getVisibleRegion().nearRight);

        String[] splitFarLeft = sFarLeft.split("\\(", 0);
        String[] splitNearRight = sNearRight.split("\\(", 0);

        String[] split2FarLeft = splitFarLeft[1].split(",", 0);
        String[] split2NearRight = splitNearRight[1].split(",", 0);

        String[] split3FarLeft = split2FarLeft[1].split("\\)", 0);
        String[] split3NearRight = split2NearRight[1].split("\\)", 0);


        resultFarLeftLat = split2FarLeft[0];
        resultFarLeftLng = split3FarLeft[0];
        resultNearRightLat = split2NearRight[0];
        resultNearRightLng = split3NearRight[0];

        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2FarLeft " + split2FarLeft[0]);
        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2FarLeft " + split3FarLeft[0]);
        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2NearRight " + split2NearRight[0]);
        Log.d("SPLIT CHECK LOCATION FAR - NEAR", "split2NearRight " + split3NearRight[0]);
        Log.d("MYLOG", "FarLeft:" + sFarLeft);
        Log.d("MYLOG", "NearRight:" + sNearRight);


        String url = "http://222.122.203.55/realreview/Nearby/latlng.php?";
        String urlMerge = url + "lat_start=" + resultNearRightLat + "&lat_end=" + resultFarLeftLat + "&lng_start=" + resultFarLeftLng + "&lng_end=" + resultNearRightLng;
        ProgressDialog progressDialog = new ProgressDialog(this);
        StringBuilder conn = null;
        try {
            conn = new AsyncMainNearbyLatLngReceive(urlMerge, progressDialog, this).execute().get(1000, TimeUnit.MILLISECONDS);
            JSONObject castingJO = new JSONObject(String.valueOf(conn));
            Log.d("JSON_CHECK", "1 - castingJO :" + castingJO);
            JSONArray fixJSON = castingJO.getJSONArray("realreview");
            Log.d("JSON_CHECK", "2 - fixJSON :" + fixJSON);


            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < fixJSON.length(); i++) {
                JSONObject item = fixJSON.getJSONObject(i);
                fixShopDataList.add(String.valueOf(item));
                Log.d("JSON_CHECK", "3 - item :" + i + "번 :" + item);

                JSONObject item2 = new JSONObject(fixShopDataList.get(i));
                String sLat = item2.getString("latitude");
                String sLng = item2.getString("longtitude");
                double dLat = Double.parseDouble(sLat);
                double dLng = Double.parseDouble(sLng);
                LatLng latLng = new LatLng(dLat, dLng);



                markerOptions.position(latLng)
                        .title(item2.getString("shopName"))
                        .snippet("SHOP OPEN : " + item2.getString("shopOpen") + "\nSHOP CLOSE : " + item2.getString("shopClose"));

                mMap.addMarker(markerOptions);


                mMap.setOnInfoWindowClickListener(infoWindowClickListener);



//                mMap.addMarker(new MarkerOptions()
//                        .position(latLng)
//                        .title(item2.getString("shopName"))
//                        .snippet("SHOP OPEN : " + item2.getString("shopOpen") + "\nSHOP CLOSE : " + item2.getString("shopClose")));


            }

            Log.d("Main_Test, onMapReady", "connLength : " + fixJSON.length());


        } catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
            e.printStackTrace();

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Dialog_Default dial = new Dialog_Default(Main_Test.this);
                    dial.call("MARKER CLICK", "TODO : SHOP DETAIL ACTIVITY");


                    return false;
                }
            });

        }

    }

    //정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
//            Dialog_Default dial = new Dialog_Default(Main_Test.this);
//            dial.call("MARKER INFO WINDOW CLICK","TODO : MAKE ACTIVITY");


            Intent intent = new Intent(Main_Test.this, ShopDetail_Main.class);
            startActivity(intent);

        }
    };


}
