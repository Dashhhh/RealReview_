package com.teamnova.ej.realreview.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

/**
 * Created by ej on 2017-09-26.
 */

public class UriPath {

    public UriPath(Context context) {

    }

//    public String getPath(Context context, Uri uri) {
//        // uri가 null일경우 null반환
//        if (uri == null) {
//            return null;
//        }
//        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        if (cursor != null) {
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
//        // URI경로를 반환한다.
//        return uri.getPath();
//    }   // getPath();


    private String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};

        CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }   //  getREalPathFromURI();


}
