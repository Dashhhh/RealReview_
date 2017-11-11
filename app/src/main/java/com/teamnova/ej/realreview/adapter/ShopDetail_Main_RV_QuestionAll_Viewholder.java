package com.teamnova.ej.realreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.R;

/**
 * Created by ej on 2017-11-04.
 */

public class ShopDetail_Main_RV_QuestionAll_Viewholder extends RecyclerView.ViewHolder {

    LinearLayout questionAllRootLinear, questionAllButtonRoot;
    LinearLayout questionAllTextLayout, questionAllProfileLayout;
    ImageView questionAllUserImage;
    com.beardedhen.androidbootstrap.BootstrapLabel questionAllCertify;
    TextView questionAllUserNick;
    TextView questionAllUserFollower;
    TextView questionAllReviewCount;
    TextView questionAllImageCount;
    TextView questionAllText;
    com.beardedhen.androidbootstrap.AwesomeTextView questionAllRegdate;
    com.beardedhen.androidbootstrap.BootstrapButton questionAllStar, questionAllAnswer, questionAllReplyBtn;

    public ShopDetail_Main_RV_QuestionAll_Viewholder(View itemView) {
        super(itemView);
        TypefaceProvider.registerDefaultIconSets();

        questionAllRootLinear = itemView.findViewById(R.id.questionAllRootLinear);
        questionAllTextLayout = itemView.findViewById(R.id.questionAllTextLayout);
        questionAllProfileLayout = itemView.findViewById(R.id.questionAllProfileLayout);
        questionAllUserImage = itemView.findViewById(R.id.questionAllUserImage);
        questionAllUserNick = itemView.findViewById(R.id.questionAllUserNick);
        questionAllUserFollower = itemView.findViewById(R.id.questionAllUserFollower);
        questionAllReviewCount = itemView.findViewById(R.id.questionAllReviewCount);
        questionAllImageCount = itemView.findViewById(R.id.questionAllImageCount);
        questionAllText = itemView.findViewById(R.id.questionAllText);
        questionAllRegdate = itemView.findViewById(R.id.questionAllRegdate);
        questionAllStar = itemView.findViewById(R.id.questionAllStar);
        questionAllAnswer = itemView.findViewById(R.id.questionAllAnswer);
        questionAllButtonRoot = itemView.findViewById(R.id.questionAllButtonRoot);
        questionAllReplyBtn = itemView.findViewById(R.id.questionAllReplyBtn);



/*

        questionAllStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RecyclerTestClick","Enter Click Listener");

            }
        });


        questionAllAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RecyclerTestClick","Enter Click Listener");
            }
        });
*/

    }




}
