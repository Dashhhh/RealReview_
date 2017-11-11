package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.bumptech.glide.Glide;
import com.teamnova.ej.realreview.Asynctask.AsyncSeeAllAnswerRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.ShopDetail_Answer_Viewing_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Answer_Viewing_Divider;
import com.teamnova.ej.realreview.adapter.ShopDetail_Answer_Viewing_Set;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Adapter;
import com.teamnova.ej.realreview.adapter.ShopDetail_Main_RV_QuestionAll_Set;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ShopDetail_Answer_Viewing extends AppCompatActivity {


    ImageView questionViewUserImage;
    TextView questionViewUserFollower, questionViewReviewCount, questionViewImageCount, questionViewText, questionViewRegdate, questionViewUserNick;
    android.support.v7.widget.RecyclerView questionViewRV;
    String iNick, iShopId, iQuestionIdx, iQuestion;
    ArrayList<ShopDetail_Answer_Viewing_Set> data = new ArrayList<>();
    LinearLayout questionViewRoot;
    private String iRegdate;
    private String iFollowerCnt;
    private String iReviewCnt;
    private String iImageCnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();

        setContentView(R.layout.activity_shop_detail__answer_viewing);

        init();
        listener();
        initializingData();
        adaptingData();

    }

    private void init() {
        questionViewUserImage = findViewById(R.id.questionViewUserImage);
        questionViewUserNick = findViewById(R.id.questionViewUserNick);
        questionViewUserFollower = findViewById(R.id.questionViewUserFollower);
        questionViewReviewCount = findViewById(R.id.questionViewReviewCount);
        questionViewImageCount = findViewById(R.id.questionViewImageCount);
        questionViewText = findViewById(R.id.questionViewText);
        questionViewRV = findViewById(R.id.questionViewRV);
        questionViewRegdate = findViewById(R.id.questionViewRegdate);
        questionViewRoot = findViewById(R.id.questionViewRoot);

    }

    private void listener() {

    }

    private void initializingData() {

        Bundle bundle = getIntent().getExtras();
        iNick = bundle.getString("nick");
        iShopId = bundle.getString("shopid");
        iQuestionIdx = bundle.getString("questionIdx");
        iQuestion = bundle.getString("question");
        iRegdate = bundle.getString("regdate");
        iFollowerCnt = bundle.getString("followerCnt");
        iReviewCnt = bundle.getString("reviewCnt");
        iImageCnt = bundle.getString("imageCnt");
        Log.d("SeeAll", "iNick :" + iNick);
        Log.d("SeeAll", "iShopId :" + iShopId);
        Log.d("SeeAll", "iQuestionIdx :" + iQuestionIdx);
        Log.d("SeeAll", "iQuestion :" + iQuestion);
        Log.d("SeeAll", "iRegdate :" + iRegdate);
        Log.d("SeeAll", "iFollowerCnt :" + iFollowerCnt);
        Log.d("SeeAll", "iReviewCnt :" + iReviewCnt);
        Log.d("SeeAll", "iImageCnt :" + iImageCnt);


        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        Glide.with(this).load(pref.getSharedData("isLogged_profileImagePath")).thumbnail(0.5f).into(questionViewUserImage);
        questionViewRegdate.setText(iRegdate);
        questionViewUserNick.setText(iNick);
        questionViewText.setText(iQuestion);


    }

    private void adaptingData() {

        data.clear();
        ShopDetail_Answer_Viewing_Adapter adapter = new ShopDetail_Answer_Viewing_Adapter(this, data);
        StaggeredGridLayoutManager themeLayoutSet = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        questionViewRV.setHasFixedSize(true);
        questionViewRV.setLayoutManager(themeLayoutSet);
/*
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        questionViewRV.addItemDecoration(dividerItemDecoration);
        questionViewRV.addItemDecoration(new ShopDetail_Answer_Viewing_Divider(2));
        questionViewRV.addItemDecoration(dividerItemDecoration);
*/

        questionViewRV.setAdapter(adapter);

        JSONObject conn = new JSONObject();
        try {

            /**
             * conn Parsing
             */
            conn = new AsyncSeeAllAnswerRequest(iShopId, iQuestionIdx, iNick, this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("SeeAll", "conn :" + conn);

            JSONArray jsonArray = conn.getJSONArray("seeallresult");
            Log.d("SeeAll", "jsonArray :" + jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                String parse1 = jsonArray.getString(i);
                Log.d("SeeAll", "parse1 :" + parse1);

                JSONObject jsonObject = new JSONObject(parse1);
                Log.d("SeeAll", "FOR - jsonObject :" + jsonObject);

                String type = jsonObject.getString("type");
                String idx = jsonObject.getString("idx");
                String question_idx = jsonObject.getString("question_idx");
                String nick = jsonObject.getString("nick");
                String nick_imagepath = jsonObject.getString("nick_imagepath");
                String regdate = jsonObject.getString("regdate");
                String shop_id = jsonObject.getString("shop_id");
                String user_id = jsonObject.getString("user_id");
                String replycount = jsonObject.getString("replycount");
                String answer = jsonObject.getString("answer");

                ShopDetail_Answer_Viewing_Set dataSet = new ShopDetail_Answer_Viewing_Set(type, idx, nick_imagepath, regdate, question_idx, nick, shop_id, user_id, answer, replycount);
                data.add(dataSet);


            }

            adapter.notifyDataSetChanged();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
