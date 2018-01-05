package com.teamnova.ej.realreview.activity;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.DetectionBasedTracker;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShopDetail_Checkin_OpenCV extends Activity implements CvCameraViewListener2, Camera.PictureCallback, View.OnClickListener {

    private static final String TAG = "OCVSample::Activity";
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    public static final int JAVA_DETECTOR = 0;
    public static final int NATIVE_DETECTOR = 1;


    private MenuItem mItemFace50;
    private MenuItem mItemFace40;
    private MenuItem mItemFace30;
    private MenuItem mItemFace20;
    private MenuItem mItemType;

    public Mat mRgba;
    public Mat mRgbaF;
    public Mat mGray;
    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    private DetectionBasedTracker mNativeDetector;

    private int mDetectorType = JAVA_DETECTOR;
    private String[] mDetectorName;

    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;

    private com.teamnova.ej.realreview.util_opencv.TakePicture mOpenCvCameraView;


    /**
     * 필터변수 4가지 (아래 버튼은 View Bind 변수이다.)
     *
     * @var shopDetail_Opencv_VIEW_MODE_RGBA
     * @var shopDetail_Opencv_VIEW_MODE_GRAY
     * @var shopDetail_Opencv_VIEW_MODE_CANNY
     * @var shopDetail_Opencv_VIEW_MODE_BLUR
     * <p>
     * 영상에 필터를 실시간으로 입히는 버튼이다.
     * 필터를 입힌 영상이 제대로 저장이 되지 않아서 현재 .setVisibility(VIEW.Gone); 해둔 상태이다.
     * @var shopDetail_Opencv_flip
     * 카메라 전면/후면 전환버튼이다.
     * @var shopDetail_Opencv_takePicture
     * 카메라 촬영버튼이다. 이미지가 저장되고 필터를 입힐 수 있는 activity로 전환된다.
     * Activity Stack 관리를 위해 본 버튼을 누르면 finish()가 실행 된다.
     */
    com.beardedhen.androidbootstrap.BootstrapButton
            shopDetail_Opencv_VIEW_MODE_RGBA,
            shopDetail_Opencv_VIEW_MODE_GRAY,
            shopDetail_Opencv_VIEW_MODE_CANNY,
            shopDetail_Opencv_VIEW_MODE_BLUR,
            shopDetail_Opencv_VIEW_MODE_LAPLACIAN,
            shopDetail_Opencv_flip,
            shopDetail_Opencv_takePicture;

    private static final int VIEW_MODE_RGBA = 0;
    private static final int VIEW_MODE_GRAY = 1;
    private static final int VIEW_MODE_CANNY = 2;
    private static final int VIEW_MODE_GAUSSIANBLUR = 3;
    private static final int VIEW_MODE_LAPLACIAN = 4;


    private int mViewMode;
    private Mat mIntermediateMat;


    private MenuItem mItemPreviewRGBA;
    private MenuItem mItemPreviewGray;
    private MenuItem mItemPreviewCanny;
    private MenuItem mItemPreviewFeatures;

    /**
     * @var FLIP_FLAG - Camera
     * 전면/후면 전환을 위한 기준 변수
     * onCameraFrame()에서 Core.flip()을 통해 이용된다.
     * shopDetail_Opencv_flip.button을 누르게 되면 true || false가 전환 된다.
     * 기본 값은 false 이다. (후면 카메라 = false)
     * <p>
     * FLIP_FLAG = true
     * - 위 조건 일때에는 카메라가 전면으로 바뀐다
     * <p>
     * FLIP_FLAG = false
     * - 위 조건 일때에는 카메라가 후변으로 바뀐다 (기본 값)
     */
    boolean FLIP_FLAG = false;


    /**
     * @var FILEPATH_FACEDETECT_IMAGE - 얼굴인식 촬영 후 이미지 경로를 저장하는 변수
     * ex) emulate/storage/0/realreview/filenama.jpg
     *
     * @var FILEPATH_FACEDETECT_IMAGE - 얼굴인식 촬영 후 이미지 파일명을 저장하는 변수
     *
     */
    public static String FILEPATH_FACEDETECT_IMAGE = "nothing";
    public static String FILENAME_FACEDETECT_IMAGE = "";

    /**
     * @var countFace : onCameraFrame()를 통해 현재 카메라에 담겨져 있는 얼굴의 갯수를 돌려받는다.
     * 실시간으로 (JavaCameraView에 Frame이 들어갈 때마다) 갱신된다.
     */
    int countFace;


    private String mPictureFileName;


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after(!) OpenCV initialization
//                    System.loadLibrary("detection_based_tracker");
                    System.loadLibrary("native-lib");

                    try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt_tree);
                        File cascadeDir = getDir("raw", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);


                        Log.i(TAG, "cascadeDir : " + cascadeDir);
                        Log.i(TAG, "mCascadeFile :  " + mCascadeFile);


                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        while ((bytesRead = is.read(buffer)) != -1) {

//                            Log.e(TAG, "buffer check : "+ Arrays.toString(buffer));
                            os.write(buffer, 0, bytesRead);
                        }

                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());


                        mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

                        cascadeDir.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }

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


    public ShopDetail_Checkin_OpenCV() {
        mDetectorName = new String[2];
        mDetectorName[JAVA_DETECTOR] = "Java";
        mDetectorName[NATIVE_DETECTOR] = "Native (tracking)";

        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);

        /**
         * 아래 코드는 Activity에 나오는 ActionBar 및 상태바까지 모두 없애는 코드이다.
         * OpenCV는 화면 방향에 따라 얼굴인식이 안될 수 있기 때문에(안되기 때문에) 상태바만 나오게 만들고 주석 처리한다.
         * 상태바만 나오게 하는 코드는 Manifest -> Activity Theme   를 통해 설정했다.
         */
/*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_shop_detail__checkin__open_cv);


        /**
         *  SD CARD 하위에 realreview 폴더를 생성하기 위해 미리 dirPath에 생성 할 폴더명을 추가
         */
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/realreview/";
        final File file = new File(dirPath);

        // 일치하는 폴더가 없으면 생성
        if (!file.exists())
            file.mkdirs();

        Log.d("FILECHECK", "onCreate, File dir Check :" + file);
        mOpenCvCameraView = findViewById(R.id.fd_activity_surface_view);
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        shopDetail_Opencv_flip = findViewById(R.id.shopDetail_Opencv_flip);
        shopDetail_Opencv_flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * onCameraFrame()에서 한번만 실행 된 후 false로 변경 된다.
                 * "true" 일때에만 Camera가 전환된다.
                 */
                if (FLIP_FLAG) {
                    FLIP_FLAG = false;
                } else {
                    FLIP_FLAG = true;
                }
            }
        });

        shopDetail_Opencv_takePicture = findViewById(R.id.shopDetail_Opencv_takePicture);
        shopDetail_Opencv_takePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (countFace != 0) {
                    final MaterialDialog builder;
                    builder = new MaterialDialog.Builder(ShopDetail_Checkin_OpenCV.this)
                            .title("Save Image")
                            .content("Processing..")
                            .autoDismiss(true)
                            .progressIndeterminateStyle(true)
                            .show();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                    String currentDateandTime = sdf.format(new Date());

                    /**
                     * Filepath 참고사항
                     *  - "/storage/emulated/..." 로 시작되는 주소는 "/sdcard/..."  와 동일하다.
                     */
                    String fileName = Environment.getExternalStorageDirectory().getPath() + "/dcim/camera/reviewer_" + currentDateandTime + ".jpg";
                    /*
                    String fileName2 = Environment.getDataDirectory().getPath() +
                            "/realreview/reviewer_" + currentDateandTime + ".jpg";
                    String fileName3 = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/realreview/reviewer_" + currentDateandTime + ".jpg";
                    Log.d("FILECHECK", "onClick! File Check :" + fileName2);
                    Log.d("FILECHECK", "onClick! File Check :" + fileName3);
                    */


//                    mOpenCvCameraView.takePictures(fileName);

                    /**
                     * mRgba MAT -> Mat to Bitmap
                     * Bitmap -> File
                     * File uri!
                     *
                     */


                    Bitmap bmp = null;
                    try {
                        //Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_RGB2BGRA);
                        bmp = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(mRgba, bmp);
                    } catch (CvException e) {
                        Log.d("Exception", e.getMessage());
                    }


//                    saveBitmaptoJpeg(bmp, "realreview", "reviewer_" + currentDateandTime + ".jpg");
                    saveBitmaptoJpeg(bmp, "realreview", "reviewer_" + currentDateandTime);


                    Log.d("FILECHECK", "onClick! File Check :" + fileName);

                    final String fFileName = fileName;

                    File file = new File(fileName);

                    Log.d("FILECHECK", "onClick! file.getAbsoluteFile()  :" + file.getAbsoluteFile());
                    Log.d("FILECHECK", "onClick! file.getAbsolutePath()  :" + file.getAbsolutePath());
                    Log.d("FILECHECK", "onClick! file.getPath()  :" + file.getPath());

                    /**
                     * finish()를 바로 호출 할 경우 이미지 처리가 이뤄지지 않는다.(저장되지 않는다)
                     * PostDelayed()를 통해 이미지 처리하는 시간을 확보한다.
                     */
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 3000);

/*
                    mOpenCvCameraView.buildDrawingCache();
                    Bitmap captureView = mOpenCvCameraView.getDrawingCache();
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/capture.jpeg");
                        captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Captured!", Toast.LENGTH_LONG).show();
*/

                } else {
                    Toast.makeText(ShopDetail_Checkin_OpenCV.this, "최소 하나의 얼굴은 나와야 합니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        shopDetail_Opencv_VIEW_MODE_RGBA = findViewById(R.id.shopDetail_Opencv_VIEW_MODE_RGBA);
        shopDetail_Opencv_VIEW_MODE_GRAY = findViewById(R.id.shopDetail_Opencv_VIEW_MODE_GRAY);
        shopDetail_Opencv_VIEW_MODE_CANNY = findViewById(R.id.shopDetail_Opencv_VIEW_MODE_CANNY);
        shopDetail_Opencv_VIEW_MODE_BLUR = findViewById(R.id.shopDetail_Opencv_VIEW_MODE_BLUR);
        shopDetail_Opencv_VIEW_MODE_LAPLACIAN = findViewById(R.id.shopDetail_Opencv_VIEW_MODE_LAPLACIAN);


        /**
         *
         @var - shopDetail_Opencv_VIEW_MODE_RGBA
         @var - shopDetail_Opencv_VIEW_MODE_GRAY
         @var - shopDetail_Opencv_VIEW_MODE_CANNY
         @var - shopDetail_Opencv_VIEW_MODE_BLUR
         @var - shopDetail_Opencv_VIEW_MODE_LAPLACIAN

         위 4가지 변수는 화면에 올라오는 카메라 영상에 필터를 씌우는 버튼(view)이다.
         올라와 있는 영상에 필터는 씌워지지만 저장 할 때에는 필터가 씌워지지 않은 원본으로 저장 되어지는 문제가 있다.

         문제점)
         onCameraFrame() -> JNI Native 코드를 통해 Camera로 들어오는 이미지를 Frame 단위로 분석하고 얼굴을 찾는다.
         이 과정 중간에 onCameraFrame() 내 에서 아래와 같은 시도를 하였다
         : Mat 변수에 이미지 정보를 byte 배열로 담아서 Bitmat에 옮겨보기 -> 시간부족으로 못해봄
         : getDrawingCache()를 통해 View Capture를 해봤지만 Camera 영상이 Surface View를 상속 받아서 View Capture는 안됨 (검은 화면만 올라옴)
         - 관련자료 : http://sozu.tistory.com/tag/SurfaceView
         */

        shopDetail_Opencv_VIEW_MODE_RGBA.setOnClickListener(this);
        shopDetail_Opencv_VIEW_MODE_GRAY.setOnClickListener(this);
        shopDetail_Opencv_VIEW_MODE_CANNY.setOnClickListener(this);
        shopDetail_Opencv_VIEW_MODE_BLUR.setOnClickListener(this);
        shopDetail_Opencv_VIEW_MODE_LAPLACIAN.setOnClickListener(this);


    }   // end onCreate();


    /**
     * Image SDCard Save (input Bitmap -> saved file JPEG)
     * Writer intruder(Kwangseob Kim)
     *
     * @param bitmap : input bitmap file
     * @param folder : input folder name
     * @param name   : output file name
     */

    public static void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name) {
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String folder_name = "/" + folder + "/";
        String file_name = name + ".jpg";
        String string_path = ex_storage + folder_name;
        String result_path = string_path + file_name;

        File file_path;
        try {
            file_path = new File(string_path);
            if (!file_path.isDirectory()) {
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path + file_name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            FILEPATH_FACEDETECT_IMAGE = result_path;
            FILENAME_FACEDETECT_IMAGE = file_name;
            Log.d(TAG, "saveBitmaptoJpeg, Filepath Check : " + FILEPATH_FACEDETECT_IMAGE);
        } catch (FileNotFoundException exception) {
            Log.e("FileNotFoundException", exception.getMessage());
        } catch (IOException exception) {
            Log.e("IOException", exception.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }


    /**
     * @param width  -  the width of the frames that will be delivered
     * @param height - the height of the frames that will be delivered
     * @var mOpenCvCameraView.setCvCameraViewListener(this)에 해당하는 Method
     */
    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat(height, width, CvType.CV_8UC1);
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mRgbaF = new Mat(height, width, CvType.CV_8UC4);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC4);


    }

    /**
     * @var mOpenCvCameraView.setCvCameraViewListener(this)에 해당하는 Method
     */
    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
        mIntermediateMat.release();

    }


    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            Log.i(TAG, "called onCreateOptionsMenu");
            mItemPreviewRGBA = menu.add("Preview RGBA");
            mItemPreviewGray = menu.add("Preview GRAY");
            mItemPreviewCanny = menu.add("Canny");
            mItemPreviewFeatures = menu.add("Find features");
            return true;
        }
        */


    /**
     * @var mOpenCvCameraView.setCvCameraViewListener(this)에 해당하는 Method
     */
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        final int viewMode = mViewMode;

        switch (viewMode) {
            case VIEW_MODE_RGBA: {
                // input frame has RBGA format
                mRgba = inputFrame.rgba();
                break;

            }
            case VIEW_MODE_GRAY: {
                // input frame has gray scale format
                Imgproc.cvtColor(inputFrame.gray(), mRgba, Imgproc.COLOR_GRAY2BGR, 4);
                break;

            }
            case VIEW_MODE_CANNY: {
                // input frame has gray scale format
                mRgba = inputFrame.rgba();
                Imgproc.Canny(inputFrame.gray(), mIntermediateMat, 80, 100);
                Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4);
                break;
            }
            case VIEW_MODE_GAUSSIANBLUR: {
                // 참고 사항)
                // 아래 주석 코드는 Object Detect 기능이다. 이미지의 경계점을 찾아서 표시해주는데 이 경계점 관련 Algorithm은 Harris Corner Algorithm으로 추정된다.
                // 1970년대에 나온 개념으로 사물 인식 오차율이 약 30 ~ 25% 선이다. 현재는 1% 수준.
                //                mRgba = inputFrame.rgba();
                //                mGray = inputFrame.gray();
                //                mNativeDetector.findFeatures(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr());

                Log.d("BlurCheck", String.valueOf(mRgba.size()));
                org.opencv.core.Size blurSize = new Size(3,3);
                Imgproc.GaussianBlur(inputFrame.rgba(), mRgba, blurSize, 11, 11);
                Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4);
                break;
            }
            case VIEW_MODE_LAPLACIAN: {
                Imgproc.Laplacian(inputFrame.rgba(), mRgba, 10);
                Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4);
                break;
            }
        }

        if (FLIP_FLAG) {
            /**
             * 버튼 (변수명 : shopDetail_Opencv_flip) 을 누를때마다 카메라의 앞 뒤가 바뀐다.
             */
            Core.flip(mRgba, mRgbaF, 1);
        }

        if (mAbsoluteFaceSize == 0) {
            int height = mGray.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
            mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
        }

        MatOfRect faces = new MatOfRect();
        if (mDetectorType == JAVA_DETECTOR) {
            if (mJavaDetector != null)
                mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
            Log.e(TAG, "mDetectorType == JAVA_DETECTOR");
            Log.e(TAG, "FACE RETURN :" + mNativeDetector.countFace());


        } else if (mDetectorType == NATIVE_DETECTOR) {
            if (mNativeDetector != null)
                mNativeDetector.detect(mGray, faces);
            Log.e(TAG, "mDetectorType == NATIVE_DETECTOR");

        } else {
            Log.e(TAG, "Detection method is not selected!");
        }

        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++)
            Imgproc.rectangle(mRgba, facesArray[i].tl(), facesArray[i].br(), FACE_RECT_COLOR, 1);
        countFace = facesArray.length;
        Log.e(TAG, "countFace : " + countFace);



//        Imgcodecs.imread()

        return mRgba;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemFace50 = menu.add("Face size 50%");
        mItemFace40 = menu.add("Face size 40%");
        mItemFace30 = menu.add("Face size 30%");
        mItemFace20 = menu.add("Face size 20%");
        mItemType = menu.add(mDetectorName[mDetectorType]);


        List<String> effects = mOpenCvCameraView.getEffectList();
        for (int i = 0; i < effects.size(); i++) {
            Log.d("EFFECTCHECK", effects.get(i));
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemFace50)
            setMinFaceSize(0.5f);
        else if (item == mItemFace40)
            setMinFaceSize(0.4f);
        else if (item == mItemFace30)
            setMinFaceSize(0.3f);
        else if (item == mItemFace20)
            setMinFaceSize(0.2f);
        else if (item == mItemType) {
            int tmpDetectorType = (mDetectorType + 1) % mDetectorName.length;
            item.setTitle(mDetectorName[tmpDetectorType]);
            setDetectorType(tmpDetectorType);
        }
        return true;
    }

    private void setMinFaceSize(float faceSize) {
//        mRelativeFaceSize = faceSize;
        mRelativeFaceSize = 0.2f;
        mAbsoluteFaceSize = 0;
    }

    private void setDetectorType(int type) {
        if (mDetectorType != type) {
            mDetectorType = type;

            if (type == NATIVE_DETECTOR) {
                Log.i(TAG, "Detection Based Tracker enabled");
                mNativeDetector.start();
            } else {
                Log.i(TAG, "Cascade detector enabled");
                mNativeDetector.stop();
            }
        }
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {

        // Write the image in a file (in jpeg format)
        try {
            FileOutputStream fos = new FileOutputStream(mPictureFileName);

            fos.write(bytes);
            fos.close();

        } catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
        }
    }

    public void takePictures(final String fileName) {
        Log.i(TAG, "Taking picture");
        this.mPictureFileName = fileName;
        // Postview and jpeg are sent in the same buffers if the queue is not empty when performing a capture.
        // Clear up buffers to avoid mCamera.takePictures to be stuck because of a memory issue

//        mOpenCvCameraView.takePictures(null, null, this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.shopDetail_Opencv_VIEW_MODE_RGBA: {
                mViewMode = VIEW_MODE_RGBA;
                break;
            }

            case R.id.shopDetail_Opencv_VIEW_MODE_GRAY: {
                mViewMode = VIEW_MODE_GRAY;
                break;
            }

            case R.id.shopDetail_Opencv_VIEW_MODE_CANNY: {
                mViewMode = VIEW_MODE_CANNY;
                break;
            }

            case R.id.shopDetail_Opencv_VIEW_MODE_BLUR: {
                mViewMode = VIEW_MODE_GAUSSIANBLUR;
                break;
            }
            case R.id.shopDetail_Opencv_VIEW_MODE_LAPLACIAN: {
                mViewMode = VIEW_MODE_LAPLACIAN;
                break;
            }
        }
    }


}