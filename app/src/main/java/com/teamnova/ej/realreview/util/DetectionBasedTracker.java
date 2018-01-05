package com.teamnova.ej.realreview.util;

/**
 * Created by ej on 2017-12-25.
 */

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

public class DetectionBasedTracker
{
    private long mNativeObj = 0;


    private static native long nativeCreateObject(String cascadeName, int minFaceSize);
    private static native void nativeDestroyObject(long thiz);
    private static native void nativeStart(long thiz);
    private static native void nativeStop(long thiz);
    private static native void nativeSetFaceSize(long thiz, int size);
    private static native void nativeDetect(long thiz, long inputImage, long faces);

    private static native long nativeCreateObjectOpencv(String cascadeName, int minFaceSize);
    private static native void nativeDestroyObjectOpencv(long thiz);
    private static native void nativeStartOpencv(long thiz);
    private static native void nativeStopOpencv(long thiz);
    private static native void nativeSetFaceSizeOpencv(long thiz, int size);
    private static native void nativeDetectOpencv(long thiz, long inputImage, long faces);

    private static native void FindFeatures(long addrGray, long addrRgba);


    public DetectionBasedTracker(String cascadeName, int minFaceSize) {
        mNativeObj = nativeCreateObject(cascadeName, minFaceSize);
    }

    public void start() {
        nativeStart(mNativeObj);
    }

    public void stop() {
        nativeStop(mNativeObj);
    }

    public void setMinFaceSize(int size) {
        nativeSetFaceSize(mNativeObj, size);
    }

    public void detect(Mat imageGray, MatOfRect faces) {
        nativeDetectOpencv(mNativeObj, imageGray.getNativeObjAddr(), faces.getNativeObjAddr());
    }

    public void release() {
        nativeDestroyObject(mNativeObj);
        mNativeObj = 0;
    }

    public long countFace() {
        return mNativeObj;
    }

    public void findFeatures (long addrGray, long addrRgba) {
        FindFeatures(addrGray, addrRgba);
    }

}