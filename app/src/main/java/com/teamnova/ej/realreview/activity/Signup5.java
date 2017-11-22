package com.teamnova.ej.realreview.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.Dialog_Default;
import com.teamnova.ej.realreview.util.LogCheck;
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

public class Signup5 extends AppCompatActivity implements View.OnClickListener {

    /**
     * Camera VAR
     */
    public static String ID;
    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_CAMERA = 2;
    public static int FILEPATH_COUNT = 2;
    private Uri mImageCaptureUri;
    Button sign5AddImage;
    ImageView sign5Camera;


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
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    /**********  File Path *************/
    final String uploadFilePath = "storage/emulated/0/Pictures/";//경로를 모르겠으면, 갤러리 어플리케이션 가서 메뉴->상세 정보
    final String uploadFileName = "Instagram/IMG_20170715_043200_310.jpg"; //전송하고자하는 파일 이름
    TextView messageText;
    Button uploadButton;
    String realPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup5);
        init();
        listener();


    }

    private void logCheck() {

        LogCheck isLogged = new LogCheck();
        boolean bIsLogged = isLogged.doLogCheck(this);
        if (!bIsLogged) {
            Dialog_Default dial = new Dialog_Default(this);
            dial.tempCall("WARNING", "정상적인 접근이 아닙니다. 다시 로그인 해 주세요");
            finish();
        }

    }

    private void init() {

        sign5Camera = (ImageView) findViewById(R.id.sign5Camera);
        sign5AddImage = (Button) findViewById(R.id.sign5AddImage);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        messageText = (TextView) findViewById(R.id.messageText);
        messageText.setText("Uploading file path :- '/mnt/sdcard/" + uploadFileName + "'");

    }

    private void listener() {

        sign5AddImage.setOnClickListener(this);
//        sign5Camera.setBackground(new ShapeDrawable(new OvalShape()));
//        sign5Camera.setClipToOutline(true);

        /************* Php script path ****************/
        upLoadServerUri = "http://222.122.203.55/realreview/signup/uploadtoserver.php";// Server Ip Address

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(Signup5.this, "", "Uploading file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                messageText.setText("uploading started.....");
                            }
                        });

//                        uploadFile(uploadFilePath + "" + uploadFileName);
                        uploadFile(UrParseImage);
                        Log.e("UploadFile Start -> UrParseImage:",UrParseImage);
                    }
                }).start();
                try {
                    Thread.sleep(1000);
                    filePathThread fpt = new filePathThread();
                    fpt.start();
                    fpt.join();
                } catch (UnsupportedEncodingException | InterruptedException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(Signup5.this, Main_Test.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }); //  Upload Thread END
    }


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
                FILEPATH_COUNT += 2;
                Toast.makeText(this, "PICK_FROM_ALBUM" + FILEPATH_COUNT, Toast.LENGTH_SHORT).show();
//                realPath = getRealPathFromURI(mImageCaptureUri);

            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Toast.makeText(this, "PICK_FROM_CAMERA!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 1000);
                intent.putExtra("outputY", 1000);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                if (FILEPATH_COUNT != 2) {
                    Log.e("REA::::::::::BEFORE",realPath);
                    realPath = getPath(mImageCaptureUri);
                    UrParseImage = realPath;
                    Log.e("REA::::::::::AFTER",realPath);
                    String fileName = new File(realPath).getName();
                    Log.e("mImageCaptureUri", "getRealPathFromURI > FILE NAME " + fileName);
                    realPath = fileName;
                    FILEPATH_COUNT = 2;
                } else {
                }
                Log.e("mImageCaptureUri", "mImageCaptureUri(content://):" + mImageCaptureUri);
                Log.e("mImageCaptureUri", "getRealPathFromURI:(file://)" + realPath);
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
//                    sign5Camera.setBackground(new ShapeDrawable(new OvalShape()));
                    Glide.with(Signup5.this)
                            .load(mImageCaptureUri)
                            .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                            .into(sign5Camera);

//                    sign5Camera.setClipToOutline(true);
//                    sign5Camera.setImageBitmap(photo);
//                    String image = BitMapToString(photo);
//                    editor.putString("IMAGE_TEMP", image);
//                    editor.commit();
//                    sign5Camera.setImageBitmap(photo);
//                    Intro.iv.setImageBitmap(photo);
                } else {
                    Dialog_Default dial = new Dialog_Default(this);
                    dial.tempCall("Image Warning", "문제가 발생 했습니다.");
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

                Log.e("CAMERA RECENT FILE :", String.valueOf(file));
                Log.e("CAMERA RECENT FILE(String) :", fileName);

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                Log.e("mImageCapture Path:", String.valueOf(f));

                if (f.exists()) {
                    f.delete();
                }


                break;
            }

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.sign5AddImage: {
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


        }

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

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};

        CursorLoader cursorLoader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }   //  getREalPathFromURI();

    /**
     * Image Upload THREAD()
     */

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

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :" + upLoadServerUri);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :" + upLoadServerUri);
                }
            });

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

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    + upLoadServerUri;

                            messageText.setText(msg);
                            Toast.makeText(Signup5.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(Signup5.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(Signup5.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }   // uploadFile


    public class filePathThread extends Thread {

        public filePathThread() throws UnsupportedEncodingException {

            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
            String time = sdfNow.format(new Date(System.currentTimeMillis()));
            Log.e("TIME", "TIME:" + time);

            SharedPreferenceUtil pref = new SharedPreferenceUtil(Signup5.this);
            strId += pref.getSharedData("SIGNIN_ID");
            String strPathEncode = realPath;
            Log.e("filePathThread","FILE PATH THREAD - RealPath :"+realPath);
            String tempPath = URLEncoder.encode(strPathEncode, "UTF-8");
            strPath += tempPath;
            Log.e("filePathThread","STR - FILE PATH THREAD - strPath :"+strPath);

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
            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferenceUtil pref = new SharedPreferenceUtil(getApplicationContext());
            pref.setSharedData("SIGNIN_ID","");

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

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
                .setMessage("정말 App을 종료 하시겠습니까??")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        //Intent intent=new Intent(getApplication(),Login.class);
                        //startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

            }
        })
                .create()
                .show();
    }



}