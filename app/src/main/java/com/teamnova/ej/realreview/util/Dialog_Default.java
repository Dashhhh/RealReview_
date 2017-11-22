package com.teamnova.ej.realreview.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;
import com.teamnova.ej.realreview.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ej on 2017-09-16.
 */

public class Dialog_Default {
    Context mContext;

    public Dialog_Default(Context mContext) {
        this.mContext = mContext;
    }

    public void tempCall (String title, String text){

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        new AlertDialog.Builder(mContext).setTitle(title).
                setMessage(text).
                setNegativeButton("확인", cancelListener).show();

    }


    public void call(String title, String text) {

//        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE);
//        pDialog.setTitleText(title)
//                .setContentText(text)
//                .setConfirmText("확인")
//                .show();
//        pDialog.show();

        SweetAlertDialog s = new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE);
        s.setTitleText(title);
        s.setContentText(text);
        s.setConfirmText("확인");
        s.show();


    }

    public void callMaterialDefault(String title, String text) {

        new MaterialDialog.Builder(mContext)
                .title(title)
                .content(text)
                .positiveText("확인")
                .show();

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
