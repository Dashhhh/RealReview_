package com.teamnova.ej.realreview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.adapter.MainSearch_Placetype_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Main_Search_Placetype extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private MainSearch_Placetype_Adapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__search__placetype);

        editSearch = findViewById(R.id.editSearch);
        listView = findViewById(R.id.listView);

        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new MainSearch_Placetype_Adapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);
        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });

    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList() {
        list.add("전체");
        list.add("레스토랑");
        list.add("제과점");
        list.add("은행");
        list.add("바");
        list.add("뷰티살롱");
        list.add("서점");
        list.add("버스정류장");
        list.add("카페");
        list.add("의류매장");
        list.add("편의점");
        list.add("백화점");
        list.add("전자상점");
        list.add("꽃집");
        list.add("식료품점");
        list.add("병원");
        list.add("도서관");
        list.add("숙박");
        list.add("영화극장");
        list.add("나이트클럽");
        list.add("공원");
        list.add("주차");
        list.add("약국");
        list.add("경찰");
        list.add("우체국");
        list.add("부동산중개인");
        list.add("신발가게");
        list.add("쇼핑몰");
        list.add("지하철역");
        list.add("회계");
        list.add("공항");
        list.add("유원지");
        list.add("수족관");
        list.add("아트갤러리");
        list.add("Atm");
        list.add("자전거상점");
        list.add("볼링장");
        list.add("캠프장");
        list.add("음식");
        list.add("자동차딜러");
        list.add("자동차렌탈");
        list.add("자동차수리");
        list.add("세차장");
        list.add("카지노");
        list.add("교회");
        list.add("시청");
        list.add("법원");
        list.add("치과의사");
        list.add("의사");
        list.add("전기");
        list.add("대사관");
        list.add("금융");
        list.add("소방서");
        list.add("장례식장");
        list.add("가구점");
        list.add("주유소");
        list.add("체육관");
        list.add("모발관리");
        list.add("하드웨어매장");
        list.add("건강");
        list.add("힌두사원");
        list.add("가정용품점");
        list.add("보험기관");
        list.add("보석상");
        list.add("세탁");
        list.add("변호사");
        list.add("주류판매점");
        list.add("지방정부청사");
        list.add("영화대여");
        list.add("박물관");
        list.add("화가");
        list.add("애완동물가게");
        list.add("물리치료사");
        list.add("RV공원");
        list.add("학교");
        list.add("스파");
        list.add("경기장");
        list.add("택시승차장");
        list.add("기차역");
        list.add("여행사");
        list.add("대학");
        list.add("수의진료");
        list.add("동물원");
        list.add("교차점");
        list.add("자연지형지물");
        list.add("이웃");
        list.add("정치");
        list.add("대중교통역");


    }
}