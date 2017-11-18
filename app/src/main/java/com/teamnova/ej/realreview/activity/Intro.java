package com.teamnova.ej.realreview.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Intro extends AppCompatActivity implements LocationListener {
    Handler mhandler;
    Animation anime;


    //위치정보 객체
    LocationManager lm = null;
    //위치정보 장치 이름
    String provider = null;

    com.wang.avi.AVLoadingIndicatorView introProgress;

    public static String MYLOCATION;
    void startAnim(){
        introProgress = findViewById(R.id.introProgress);
        introProgress.show();
        // or avi.smoothToShow();
    }


    void stopAnim(){
        introProgress.hide();
        // or avi.smoothToHide();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        startAnim();






        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);

        /**위치정보 객체를 생성한다.*/
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /** 현재 사용가능한 위치 정보 장치 검색*/
        //위치정보 하드웨어 목록
        Criteria c = new Criteria();
        //최적의 하드웨어 이름을 리턴받는다.
//        provider = lm.getBestProvider(c, true);
        Log.d("LOCATION_PROVIDER_CHECK","provider :"+provider);

        // 최적의 값이 없거나, 해당 장치가 사용가능한 상태가 아니라면,
        //모든 장치 리스트에서 사용가능한 항목 얻기
        if (provider == null || !lm.isProviderEnabled(provider)) {
            // 모든 장치 목록
            List<String> list = lm.getAllProviders();

            for (int i = 0; i < list.size(); i++) {
                //장치 이름 하나 얻기
                String temp = list.get(i);
                Log.d("LOCATION_PROVIDER_CHECK","Device Name :"+temp);
                //사용 가능 여부 검사
                if (lm.isProviderEnabled(temp)) {
                    provider = temp;
                    break;
                }
            }
        }// (end if)위치정보 검색 끝

        /**마지막으로  조회했던 위치 얻기*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(provider);

        if (location == null) {
            Toast.makeText(this, "사용가능한 위치 정보 제공자가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            //최종 위치에서 부터 이어서 GPS 시작...
            onLocationChanged(location);

        }


        takePlaceTypeMap();

        anime = AnimationUtils.loadAnimation(this, R.anim.rise_up);
        anime.setFillAfter(true);

        mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopAnim();

                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                        Log.d("TEDPermission","onPermissionGranted");

                        Intent i = new Intent(Intro.this, Signin.class);
                        startActivity(i);
                        finish();

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Log.d("TEDPermission","onPermissionDenied");


                    }


                };

                TedPermission.with(Intro.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .check();

            }
        }, 800);


    }

    @Override
    protected void onResume() {
        super.onResume();

        //위치정보 객체에 이벤트 연결
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else{

            lm.requestLocationUpdates(provider, 2000, 1, this);
        }



    }

    @Override
    protected void onStop() {
        super.onStop();

        lm.removeUpdates(this);

    }

    /**
     * 위치가 변했을 경우 호출된다.
     */
    @Override
    public void onLocationChanged(Location location) {
        // 위도, 경도
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        String checkAddress = getAddress(lat,lng);

        Main_Test.MY_POSITION_LAT = lat;
        Main_Test.MY_POSITION_LNG = lng;

        Log.d("Intro - onLocationChange", "lat :"+lat);
        Log.d("Intro - onLocationChange", "lng :"+lng);
        Log.d("Intro - onLocationChange", "checkAddress :"+checkAddress);



    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    /**
     * 위도와 경도 기반으로 주소를 리턴하는 메서드
     */
    public String getAddress(double lat, double lng) {
        String address = null;

        //위치정보를 활용하기 위한 구글 API 객체
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //주소 목록을 담기 위한 HashMap
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list == null) {
            Log.e("getAddress", "주소 데이터 얻기 실패");
            return null;
        }

        if (list.size() > 0) {
            Address addr = list.get(0);
            address = addr.getCountryName() + " "
                    + addr.getPostalCode() + " "
                    + addr.getLocality() + " "
                    + addr.getThoroughfare() + " "
                    + addr.getFeatureName();
        }

        return address;


    }

    private void takePlaceTypeMap() {

        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        Log.e("Share Check", "PlaceType Mapping");
        pref.setSharedData("0", "기타");
        pref.setSharedData("1", "회계");
        pref.setSharedData("2", "공항");
        pref.setSharedData("3", "유원지");
        pref.setSharedData("4", "수족관");
        pref.setSharedData("5", "아트 갤러리");
        pref.setSharedData("6", "Atm");
        pref.setSharedData("7", "제과점");
        pref.setSharedData("8", "은행");
        pref.setSharedData("9", "바");
        pref.setSharedData("10", "뷰티 살롱");
        pref.setSharedData("11", "자전거 상점");
        pref.setSharedData("12", "서점");
        pref.setSharedData("13", "볼링장");
        pref.setSharedData("14", "버스 정류장");
        pref.setSharedData("15", "카페");
        pref.setSharedData("16", "캠프장");
        pref.setSharedData("17", "자동차 딜러");
        pref.setSharedData("18", "자동차 렌탈");
        pref.setSharedData("19", "자동차 수리");
        pref.setSharedData("20", "세차장");
        pref.setSharedData("21", "카지노");
        pref.setSharedData("22", "묘지");
        pref.setSharedData("23", "교회");
        pref.setSharedData("24", "시청");
        pref.setSharedData("25", "의류 매장");
        pref.setSharedData("26", "편의점");
        pref.setSharedData("27", "법원");
        pref.setSharedData("28", "치과 의사");
        pref.setSharedData("30", "의사");
        pref.setSharedData("29", "백화점");
        pref.setSharedData("31", "전기");
        pref.setSharedData("32", "전자 상점");
        pref.setSharedData("33", "대사관");
        pref.setSharedData("34", "설립");
        pref.setSharedData("35", "금융");
        pref.setSharedData("36", "소방서");
        pref.setSharedData("37", "꽃집");
        pref.setSharedData("38", "음식");
        pref.setSharedData("39", "장례식장");
        pref.setSharedData("40", "가구점");
        pref.setSharedData("41", "주유소");
        pref.setSharedData("42", "일반 계약자");
        pref.setSharedData("43", "식료품점");
        pref.setSharedData("44", "체육관");
        pref.setSharedData("45", "모발 관리");
        pref.setSharedData("46", "하드웨어 매장");
        pref.setSharedData("47", "건강");
        pref.setSharedData("48", "힌두 사원");
        pref.setSharedData("49", "가정 용품점");
        pref.setSharedData("50", "병원");
        pref.setSharedData("51", "보험 기관");
        pref.setSharedData("52", "보석상");
        pref.setSharedData("53", "세탁");
        pref.setSharedData("54", "변호사");
        pref.setSharedData("55", "도서관");
        pref.setSharedData("56", "주류 판매점");
        pref.setSharedData("57", "지방 정부 청사");
        pref.setSharedData("58", "자물쇠");
        pref.setSharedData("59", "숙박");
        pref.setSharedData("60", "식사 제공");
        pref.setSharedData("61", "식사 테이크 아웃");
        pref.setSharedData("62", "모스크");
        pref.setSharedData("63", "영화 대여");
        pref.setSharedData("64", "영화 극장");
        pref.setSharedData("65", "이사 회사");
        pref.setSharedData("66", "박물관");
        pref.setSharedData("67", "나이트 클럽");
        pref.setSharedData("68", "화가");
        pref.setSharedData("69", "공원");
        pref.setSharedData("70", "주차");
        pref.setSharedData("71", "애완 동물 가게");
        pref.setSharedData("72", "약국");
        pref.setSharedData("73", "물리 치료사");
        pref.setSharedData("74", "예배당의 장소");
        pref.setSharedData("75", "배관공");
        pref.setSharedData("76", "경찰");
        pref.setSharedData("77", "우체국");
        pref.setSharedData("78", "부동산 중개인");
        pref.setSharedData("79", "레스토랑");
        pref.setSharedData("80", "루핑 계약자");
        pref.setSharedData("81", "RV공원");
        pref.setSharedData("82", "학교");
        pref.setSharedData("83", "신발 가게");
        pref.setSharedData("84", "쇼핑몰");
        pref.setSharedData("85", "스파");
        pref.setSharedData("86", "경기장");
        pref.setSharedData("87", "보관");
        pref.setSharedData("88", "상점");
        pref.setSharedData("89", "지하철역");
        pref.setSharedData("90", "회당");
        pref.setSharedData("91", "택시 승차장");
        pref.setSharedData("92", "기차역");
        pref.setSharedData("93", "여행사");
        pref.setSharedData("94", "대학");
        pref.setSharedData("95", "수의 진료");
        pref.setSharedData("96", "동물원");
        pref.setSharedData("1001", "행정 구역 수준 1");
        pref.setSharedData("1002", "행정 구역 수준 2");
        pref.setSharedData("1003", "행정 구역 수준 3");
        pref.setSharedData("1004", "구어짐 면적");
        pref.setSharedData("1005", "국가");
        pref.setSharedData("1006", "층");
        pref.setSharedData("1007", "지오 코드");
        pref.setSharedData("1008", "교차점");
        pref.setSharedData("1009", "지역성");
        pref.setSharedData("1010", "자연 지형지 물");
        pref.setSharedData("1011", "이웃");
        pref.setSharedData("1012", "정치");
        pref.setSharedData("1013", "관심 지점");
        pref.setSharedData("1014", "포스트 박스");
        pref.setSharedData("1015", "우편 번호");
        pref.setSharedData("1016", "우편 번호 접두사");
        pref.setSharedData("1017", "우편 타운");
        pref.setSharedData("1018", "전제");
        pref.setSharedData("1019", "방");
        pref.setSharedData("1020", "루트");
        pref.setSharedData("1021", "거리 주소");
        pref.setSharedData("1022", "하부 구조");
        pref.setSharedData("1023", "하위 수준 1");
        pref.setSharedData("1024", "하위 수준 2");
        pref.setSharedData("1025", "하위 수준 3");
        pref.setSharedData("1026", "하위 수준 4");
        pref.setSharedData("1027", "하위 수준 5");
        pref.setSharedData("1028", "Subpremise");
        pref.setSharedData("1029", "합성 지오 코드");
        pref.setSharedData("1030", "대중 교통 역");

    }



}