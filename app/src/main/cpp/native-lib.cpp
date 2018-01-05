//
// Created by ej on 2017-12-27.
//

#include <jni.h>
#include <opencv2/opencv.hpp>

//#include <opencv2/core/core.hpp>

//#include <opencv2/imgproc/imgproc.hpp>
//#include <opencv2/objdetect.hpp>
#include <android/log.h>

#include <string>
#include <vector>


#define LOG_TAG "FaceDetection/DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))


using namespace cv;
using namespace std;

inline void vector_Rect_to_Mat(vector<Rect> &v_rect, Mat &mat) {
    mat = Mat(v_rect, true);
}

class CascadeDetectorAdapter : public DetectionBasedTracker::IDetector {
public:
    CascadeDetectorAdapter(cv::Ptr<cv::CascadeClassifier> detector) :
            IDetector(),
            Detector(detector) {

        LOGD("CascadeDetectorAdapter::Detect::Detect");
        CV_Assert(detector);
    }

    void detect(const cv::Mat &Image, std::vector<cv::Rect> &objects) {
        LOGD("CascadeDetectorAdapter::Detect: begin");
        LOGD("CascadeDetectorAdapter::Detect: scaleFactor=%.2f, minNeighbours=%d, minObjSize=(%dx%d), maxObjSize=(%dx%d)",
             scaleFactor, minNeighbours, minObjSize.width, minObjSize.height, maxObjSize.width,
             maxObjSize.height);
        Detector->detectMultiScale(Image, objects, scaleFactor, minNeighbours, 0, minObjSize,
                                   maxObjSize);
        LOGD("CascadeDetectorAdapter::Detect: end");
    }

    virtual ~CascadeDetectorAdapter() {
        LOGD("CascadeDetectorAdapter::Detect::~Detect");
    }

private:
    CascadeDetectorAdapter();

    cv::Ptr<cv::CascadeClassifier> Detector;
};

struct DetectorAgregator {
    cv::Ptr<CascadeDetectorAdapter> mainDetector;
    cv::Ptr<CascadeDetectorAdapter> trackingDetector;

    cv::Ptr<DetectionBasedTracker> tracker;

    DetectorAgregator(cv::Ptr<CascadeDetectorAdapter> &_mainDetector,
                      cv::Ptr<CascadeDetectorAdapter> &_trackingDetector) :
            mainDetector(_mainDetector),
            trackingDetector(_trackingDetector) {
        CV_Assert(_mainDetector);
        CV_Assert(_trackingDetector);

        DetectionBasedTracker::Parameters DetectorParams;
        tracker = makePtr<DetectionBasedTracker>(mainDetector, trackingDetector, DetectorParams);
    }
};

extern "C" {
JNIEXPORT jstring

JNICALL Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


JNIEXPORT jlong JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeCreateObject(JNIEnv *env,
                                                                              jclass type,
                                                                              jstring cascadeName_,
                                                                              jint minFaceSize) {
    const char *cascadeName = env->GetStringUTFChars(cascadeName_, 0);

    // TODO



//    string baseDir("/storage/emulated/0/");
    string baseDir("");
    baseDir.append(cascadeName);
    const char *pathDir = baseDir.c_str();

    jlong ret = 0;
    ret = (jlong) new CascadeClassifier(pathDir);
    if (((CascadeClassifier *) ret)->empty()) {
        __android_log_print(ANDROID_LOG_DEBUG, "native-lib :: ",
                            "CascadeClassifier로 로딩 실패 cascadeName : %s", cascadeName);
    } else
        __android_log_print(ANDROID_LOG_DEBUG, "native-lib :: ",
                            "CascadeClassifier로 로딩 성공 %s", cascadeName);


    env->ReleaseStringUTFChars(cascadeName_, cascadeName);
    return ret;
}

JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDestroyObject(JNIEnv *env,
                                                                               jclass type,
                                                                               jlong thiz) {

    // TODO

}
JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStart(JNIEnv *env, jclass type,
                                                                       jlong thiz) {

    // TODO

}
JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStop(JNIEnv *env, jclass type,
                                                                      jlong thiz) {

    // TODO

}
JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeSetFaceSize(JNIEnv *env,
                                                                             jclass type,
                                                                             jlong thiz,
                                                                             jint size) {

    // TODO

}
JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDetect(JNIEnv *env, jclass type,
                                                                        jlong thiz,
                                                                        jlong inputImage,
                                                                        jlong faces) {

    // TODO

}


float resize(Mat img_src, Mat &img_resize, int resize_width) {

    float scale = resize_width / (float) img_src.cols;
    if (img_src.cols > resize_width) {
        int new_height = cvRound(img_src.rows * scale);
        resize(img_src, img_resize, Size(resize_width, new_height));
    } else {
        img_resize = img_src;
    }
    return scale;
}


JNIEXPORT jlong JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeCreateObjectOpencv
        (JNIEnv *jenv, jclass type, jstring jFileName, jint faceSize) {
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeCreateObject enter");
    const char *jnamestr = jenv->GetStringUTFChars(jFileName, NULL);
    string stdFileName(jnamestr);
    jlong result = 0;

    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeCreateObject");

    try {
        cv::Ptr<CascadeDetectorAdapter> mainDetector = makePtr<CascadeDetectorAdapter>(
                makePtr<CascadeClassifier>(stdFileName));
        cv::Ptr<CascadeDetectorAdapter> trackingDetector = makePtr<CascadeDetectorAdapter>(
                makePtr<CascadeClassifier>(stdFileName));
        result = (jlong) new DetectorAgregator(mainDetector, trackingDetector);
        if (faceSize > 0) {
            mainDetector->setMinObjectSize(Size(faceSize, faceSize));
            //trackingDetector->setMinObjectSize(Size(faceSize, faceSize));
        }
    }
    catch (cv::Exception &e) {
        LOGD("nativeCreateObject caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if (!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...) {
        LOGD("nativeCreateObject caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je,
                       "Unknown exception in JNI code of DetectionBasedTracker.nativeCreateObject()");
        return 0;
    }

    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeCreateObject exit");
    return result;
}

JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDestroyObjectOpencv
        (JNIEnv *jenv, jclass type, jlong thiz) {
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDestroyObject");

    try {
        if (thiz != 0) {
            ((DetectorAgregator *) thiz)->tracker->stop();
            delete (DetectorAgregator *) thiz;
        }
    }
    catch (cv::Exception &e) {
        LOGD("nativeestroyObject caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if (!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...) {
        LOGD("nativeDestroyObject caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je,
                       "Unknown exception in JNI code of DetectionBasedTracker.nativeDestroyObject()");
    }
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDestroyObject exit");
}

JNIEXPORT void JNICALL Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStartOpencv
        (JNIEnv *jenv, jclass type, jlong thiz) {
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStart");

    try {
        ((DetectorAgregator *) thiz)->tracker->run();
    }
    catch (cv::Exception &e) {
        LOGD("nativeStart caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if (!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...) {
        LOGD("nativeStart caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeStart()");
    }
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStart exit");
}

JNIEXPORT void JNICALL Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStopOpencv
        (JNIEnv *jenv, jclass type, jlong thiz) {
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStop");

    try {
        ((DetectorAgregator *) thiz)->tracker->stop();
    }
    catch (cv::Exception &e) {
        LOGD("nativeStop caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if (!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...) {
        LOGD("nativeStop caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeStop()");
    }
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeStop exit");
}

JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeSetFaceSizeOpencv
        (JNIEnv *jenv, jclass type, jlong thiz, jint faceSize) {
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeSetFaceSize -- BEGIN");

    try {
        if (faceSize > 0) {
            ((DetectorAgregator *) thiz)->mainDetector->setMinObjectSize(Size(faceSize, faceSize));
//((DetectorAgregator*)thiz)->trackingDetector->setMinObjectSize(Size(faceSize, faceSize));
        }
    }
    catch (cv::Exception &e) {
        LOGD("nativeStop caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if (!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...) {
        LOGD("nativeSetFaceSize caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je,
                       "Unknown exception in JNI code of DetectionBasedTracker.nativeSetFaceSize()");
    }
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeSetFaceSize -- END");
}


JNIEXPORT void JNICALL Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDetectOpencv
        (JNIEnv *jenv, jclass type, jlong thiz, jlong imageGray, jlong faces) {
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDetect");

    try {
        vector<Rect> RectFaces;
        ((DetectorAgregator *) thiz)->tracker->process(*((Mat *) imageGray));
        ((DetectorAgregator *) thiz)->tracker->getObjects(RectFaces);
        *((Mat *) faces) = Mat(RectFaces, true);
    }
    catch (cv::Exception &e) {
        LOGD("nativeCreateObject caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if (!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...) {
        LOGD("nativeDetect caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code DetectionBasedTracker.nativeDetect()");
    }
    LOGD("Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_nativeDetect END");
}

extern "C" {
JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_FindFeatures(JNIEnv *, jobject,
                                                                        jlong addrGray,
                                                                        jlong addrRgba);

JNIEXPORT void JNICALL
Java_com_teamnova_ej_realreview_util_DetectionBasedTracker_FindFeatures(JNIEnv *, jobject,
                                                                        jlong addrGray,
                                                                        jlong addrRgba) {
    Mat &mGr = *(Mat *) addrGray;
    Mat &mRgb = *(Mat *) addrRgba;
    vector<KeyPoint> v;

    Ptr<FeatureDetector> detector = FastFeatureDetector::create(50);
    detector->detect(mGr, v);
    for (unsigned int i = 0; i < v.size(); i++) {
        const KeyPoint &kp = v[i];
        circle(mRgb, Point(kp.pt.x, kp.pt.y), 10, Scalar(255, 0, 0, 255));
    }
}


/*
 *@brief rotate image by multiple of 90 degrees
 *
 *@param source : input image
 *@param dst : output image
 *@param angle : factor of 90, even it is not factor of 90, the angle
 * will be mapped to the range of [-360, 360].
 * {angle = 90n; n = {-4, -3, -2, -1, 0, 1, 2, 3, 4} }
 * if angle bigger than 360 or smaller than -360, the angle will
 * be map to -360 ~ 360.
 * mapping rule is : angle = ((angle / 90) % 4) * 90;
 *
 * ex : 89 will map to 0, 98 to 90, 179 to 90, 270 to 3, 360 to 0.
 *
 */

void rotate_90n(cv::Mat const &src, cv::Mat &dst, int angle) {
    CV_Assert(angle % 90 == 0 && angle <= 360 && angle >= -360);
    if (angle == 270 || angle == -90) {
        // Rotate clockwise 270 degrees
        cv::transpose(src, dst);
        cv::flip(dst, dst, 0);
    } else if (angle == 180 || angle == -180) {
        // Rotate clockwise 180 degrees
        cv::flip(src, dst, -1);
    } else if (angle == 90 || angle == -270) {
        // Rotate clockwise 90 degrees
        cv::transpose(src, dst);
        cv::flip(dst, dst, 1);
    } else if (angle == 360 || angle == 0 || angle == -360) {
        if (src.data != dst.data) {
            src.copyTo(dst);
        }
    }
}

}


}

