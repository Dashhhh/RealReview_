package com.teamnova.ej.realreview.util;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ej on 2017-09-16.
 */

public class Dialog_Default {
    Context mContext;

    public Dialog_Default(Context mContext) {
        this.mContext = mContext;
    }

//    public void call (String title, String text){
//
//        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        };
//        new AlertDialog.Builder(mContext).setTitle(title).
//                setMessage(text).
//                setNegativeButton("확인", cancelListener).show();
//
//    }


    public void call(String title, String text) {

        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText(title)
                .setContentText(text)
                .setConfirmText("확인")
                .show();
        pDialog.show();
    }





    /**
     * onBackPressed -> EXIT (COPY & PASTE)
     */

    public void end(String title, String text) {
//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("REAL REVIEW")
//                .setContentText("정말로 App을 종료 하시겠습니까?!")
//                .setCancelText("취소")
//                .setConfirmText("종료")
//                .showCancelButton(true)
//                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        finish();
//                    }
//                })
//                .show();
    }


}
