package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.teamnova.ej.realreview.Asynctask.AsyncMyFeedRequest;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Question_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Question_Set;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Adapter;
import com.teamnova.ej.realreview.adapter.MainMe_MyFeed_Review_Set;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main_Me_Question extends AppCompatActivity {
    public ArrayList<MainMe_MyFeed_Question_Set> questionArrayData = new ArrayList<>();
    private String myFeedURL = "http://222.122.203.55/realreview/myFeed/myFeed.php";
    android.support.v7.widget.RecyclerView mainMeQuestionRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main__me__question);
        init();
        myFeedAdapting();

    }

    private void init() {

        mainMeQuestionRV = findViewById(R.id.mainMeQuestionRV);
    }

    private void myFeedAdapting() {

        JSONObject conn;
        try {
            conn = new AsyncMyFeedRequest(myFeedURL, this).execute().get(10000, TimeUnit.MILLISECONDS);
            Log.d("ASYNCMyFeed", "conn : " + conn);

            /**
             *  questionDefault Parsing
             */
            questionArrayData.clear();

            MainMe_MyFeed_Question_Adapter questionAdapter = new MainMe_MyFeed_Question_Adapter(this, questionArrayData);
            LinearLayoutManager mainMeMyQuestionLayoutManager = new LinearLayoutManager(this);
            mainMeQuestionRV.setLayoutManager(mainMeMyQuestionLayoutManager);
            mainMeQuestionRV.setHasFixedSize(false);
            mainMeQuestionRV.setAdapter(questionAdapter);

            JSONArray myFeedQuestionParsing = conn.getJSONArray("questionDefault");

            for (int i = 0; i < myFeedQuestionParsing.length(); i++) {

                String question_Idx;
                String question_Id_shop;
                String question_Id_user;
                String question_Nick;
                String question_ReviewIdx;
                String type;
                String idx;
                String nick;
                String regdate;
                String shop_id;
                String user_id;
                String question;
                String answerCount;
                String metooCount;
                String shopQuestionCount;
                String address;
                String shopName;
                String callNumber;
                double rating;

                String answer_Idx;
                String answer_Id_shop;
                String answer_Id_user;
                String answer_Nick;
                String answer_QuestionIdx;


                String shopImagePath;

                JSONObject myFeedQuestionParsing2 = myFeedQuestionParsing.getJSONObject(i);
                type = myFeedQuestionParsing2.getString("type");
                idx = myFeedQuestionParsing2.getString("idx");
                nick = myFeedQuestionParsing2.getString("nick");
                regdate = myFeedQuestionParsing2.getString("regdate");
                shop_id = myFeedQuestionParsing2.getString("shop_id");
                user_id = myFeedQuestionParsing2.getString("user_id");
                question = myFeedQuestionParsing2.getString("question");
                answerCount = myFeedQuestionParsing2.getString("answerCount");
                metooCount = myFeedQuestionParsing2.getString("metooCount");
                shopQuestionCount = myFeedQuestionParsing2.getString("shopQuestionCount");
                address = myFeedQuestionParsing2.getString("address");
                shopName = myFeedQuestionParsing2.getString("shopName");
                callNumber = myFeedQuestionParsing2.getString("callNumber");
//                rating = myFeedQuestionParsing2.getDouble("rating");
                shopImagePath = myFeedQuestionParsing2.getString("shopImagePath");


                JSONArray myFeedReviewParsingQuestion = myFeedQuestionParsing2.getJSONArray("metoo_array");
                Log.d("ASYNCMyFeed_REVIEW_Question", "myFeedReviewParsingQuestion : " + myFeedReviewParsingQuestion);
                Log.d("ASYNCMyFeed_REVIEW_Question", "myFeedReviewParsingQuestion2 : " + myFeedQuestionParsing2);
                ArrayList<String> metooArray = new ArrayList<>();
                for (int j = 0; j < myFeedReviewParsingQuestion.length(); j++) {
                    JSONObject myFeedReviewParsingQuestion2 = myFeedReviewParsingQuestion.getJSONObject(j);
                    question_Idx = myFeedReviewParsingQuestion2.getString("idx");
                    question_Id_shop = myFeedReviewParsingQuestion2.getString("id_shop");
                    question_Id_user = myFeedReviewParsingQuestion2.getString("id_user");
                    question_Nick = myFeedReviewParsingQuestion2.getString("nick");
                    question_ReviewIdx = myFeedReviewParsingQuestion2.getString("idx_question");
                    metooArray.add(0, question_Nick);
                }
                JSONArray myFeedReviewParsingAnswer = myFeedQuestionParsing2.getJSONArray("answerUser");
                Log.d("ASYNCMyFeed_REVIEW_Question_Answer", "myFeedReviewParsingAnswer : " + myFeedReviewParsingAnswer);
                Log.d("ASYNCMyFeed_REVIEW_Question_Answer", "myFeedQuestionParsing2 : " + myFeedQuestionParsing2);
                ArrayList<String> answerArray = new ArrayList<>();
                for (int j = 0; j < myFeedReviewParsingAnswer.length(); j++) {
                    JSONObject myFeedReviewParsingQuestion2 = myFeedReviewParsingAnswer.getJSONObject(j);
                    Log.d("ASYNCMyFeed_REVIEW_Question_Answer", "myFeedReviewParsingQuestion2 : " + myFeedReviewParsingQuestion2);
                    answer_Idx = myFeedReviewParsingQuestion2.getString("idx");
                    answer_Id_shop = myFeedReviewParsingQuestion2.getString("shop_id");
                    answer_Id_user = myFeedReviewParsingQuestion2.getString("user_id");
                    answer_Nick = myFeedReviewParsingQuestion2.getString("nick");
                    answer_QuestionIdx = myFeedReviewParsingQuestion2.getString("question_idx");
                    answerArray.add(0, answer_Nick);
                }

                SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
                MainMe_MyFeed_Question_Set addData = new MainMe_MyFeed_Question_Set(
                        regdate,
                        answerCount,
                        metooCount,
                        shopImagePath,
                        metooArray,
                        address,
                        shopName,
                        callNumber,
                        shopQuestionCount,
                        shopImagePath,
                        answerArray
                );
                questionArrayData.add(0, addData);
            }

            questionAdapter.notifyDataSetChanged();

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
