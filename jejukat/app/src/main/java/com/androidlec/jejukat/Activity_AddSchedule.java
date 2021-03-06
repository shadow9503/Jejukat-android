package com.androidlec.jejukat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_AddSchedule extends AppCompatActivity implements OnMapReadyCallback {

    private EditText input_title_ed;
    private TextView confirm_btn;

    public static Activity addScheduleActivity;

    //탭레이아웃
    private FragmentPagerAdapter fragmentPagerAdapter; //FragmentPager어댑터
    ViewPager viewPagerTabLayout; // 탭레이아웃 뷰페이져

    /*//////////////////////////////////////////////
        구글 지도
    *///////////////////////////////////////////////
    private GoogleMap gMap;
    private Geocoder geocoder;
    private ArrayList<LatLng> LatLngList;
    private Polyline polyline = null;

    // 커스텀 마커가 담길 View (View에 담아 Bitmap으로 변환 시킬것임.
    private View customMarkerView;

    // 커스텀 마커와 마커 text
    private TextView tv_marker, tv_marker_text;

    // 구글 지도 Polyline DOT DASH 패턴 간격(GAP) 설정
    private static final int PATTERN_GAP_LENGTH_PX = 20;

    // Polyline 패턴 선언
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_GAP_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // 구글 지도 Polyline 점선 패턴
    private static final List<PatternItem> PATTERN_POLYLINE_DASH = Arrays.asList(GAP, DASH);

    // 기본 주소값
    private String default_place = "제주도";

    // 마커 전체 범위 계산을 위해 마커좌표를 담는 LatLngBounds (전체 마커가 화면에 전부 담기게끔 하기 위함)
    private LatLngBounds.Builder builder = null;

    /*//////////////////////////////////////////////
    *///////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_schedule );

        addScheduleActivity = Activity_AddSchedule.this;

        LatLngList = new ArrayList<>();

        setWidgetId(); // 위젯 ID 등록

        /*/////////////////////////////
        //      구글 지도 프래그먼트
        *//////////////////////////////

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_AddSchedule);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder( Activity_AddSchedule.this );

        /*/////////////////////////////
        //      TabLayout 뷰페이저
        *//////////////////////////////

        viewPagerTabLayout = findViewById(R.id.vp_addschedule); //xml연결
        fragmentPagerAdapter = new Adapter_AddSchedule_TabLayout_ViewPager(getSupportFragmentManager()); // fragmentPager어댑터생성

        TabLayout tabLayout = findViewById(R.id.tab_Layout); // 탭(지도/상세내용/댓글)
        viewPagerTabLayout.setAdapter(fragmentPagerAdapter); // 뷰페이저에 어댑터 연동
        tabLayout.setupWithViewPager(viewPagerTabLayout);

    }

    /*/////////////////////////////
          위젯 ID 등록
    *//////////////////////////////

    private void setWidgetId() {
        tv_marker_text = findViewById(R.id.tv_marker_text);
        tv_marker = findViewById(R.id.tv_marker);
        input_title_ed = findViewById(R.id.et_addschedule_title);
        confirm_btn = findViewById(R.id.tv_addschedule_confirm);
        confirm_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if( checkForm() == true ) {
                Activity_AddSchedule_FragmentSchedule fragmentSchedule = (Activity_AddSchedule_FragmentSchedule) getSupportFragmentManager().findFragmentById( R.id.vp_addschedule );
                fragmentSchedule.uploadScheduleImage();
//                    startActivity( new Intent( AddScheduleActivity.this, AddScheduleActivity.class ) );
//                Toast.makeText( AddScheduleActivity.this, "일정 업로드 suc", Toast.LENGTH_SHORT ).show();
//                }
//                Toast.makeText(AddPlace_Activity.this, checkWhat + "을(를) 입력해주세요.", Toast.LENGTH_SHORT).show();

            }
        } );
    }

    /*/////////////////////////////////////////////////////////////////
          커스텀 마커 사용을 위한 준비 View에 커스텀 마커 inflate
    *//////////////////////////////////////////////////////////////////

    private void setCustomMarkerView() {
        customMarkerView  = LayoutInflater.from(this).inflate(R.layout.schedule_gmap_marker, null);
        tv_marker = (TextView) customMarkerView.findViewById(R.id.tv_marker);
        tv_marker_text = (TextView) customMarkerView.findViewById( R.id.tv_marker_text );
    }

    /*//////////////////////////////////////////////////////////
                              구글 지도
    *///////////////////////////////////////////////////////////

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        // 초기 생성 맵
        gMap = googleMap;
        LatLng default_latlng = new LatLng( 33.4890113,126.49830229999999 );
        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(default_latlng,10) );
        gMap.getUiSettings().setZoomControlsEnabled( true );

        setCustomMarkerView();
    }

    /*////////////////////////////////
    //   ( 주소 -> 좌표 )  전환 메소드
    */////////////////////////////////

    private LatLng geoCoder(String place) {
//        Log.v( "Status", "geoCoder()" );
//        Log.v( "Status", place );
        List<Address> list = null;
        LatLng latLng = null;
        try {
            // 지오코딩 주소 -> 좌표
            list = geocoder.getFromLocationName
                    (place, 1 );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e( "test", "입출력 오류 - 서버에서 주소변환시 에러발생" );
        }
        if (list != null) {
            if (list.size() == 0) {
                Log.v( "Status: ", "해당되는 주소 정보는 없습니다" );
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get( 0 );
                double lat = addr.getLatitude();
                double lon = addr.getLongitude();
                latLng = new LatLng( lat, lon );
                LatLngList.add( latLng );
//                Log.v( "Status", latLng.toString() );
            }
        }
        return latLng;
    }

    /*//////////////////////////////////////////////////////////////////////
           FragmentPlaces 프래그먼트의 장소목록 주소들에 모두 마커 생성
    *///////////////////////////////////////////////////////////////////////

    public void setMultipleMarker() {

        gMap.clear();   // 마커 정리
        LatLngList.clear();   // 연결선 정리
        builder = new LatLngBounds.Builder();
        int size = STATICDATA2.PLACEARRAY.size();
//        Log.v( "Status", String.valueOf( size ) );
        for(int i=0; i<size; i++) {
            String addr = STATICDATA2.PLACEARRAY.get( i ).getPlaceAddress();
//            Log.v( "Status", addr );
            setMarker( gMap, addr, geoCoder( addr ), 15, i+1 );
        }
        setPolyLine();

    }

    /*////////////////////////////////
                 마커 생성
    */////////////////////////////////

    private void setMarker(GoogleMap gMap, String addr, LatLng latLng, int zoomLv, int number) {
//        Log.v( "Status", "setMarker()" );
        tv_marker_text.setText(String.valueOf(number));
        Marker marker = gMap.addMarker( new MarkerOptions()
                .position( latLng )
                .title(addr)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        createDrawableFromView( Activity_AddSchedule.this, customMarkerView ) ))
        );
        builder.include( marker.getPosition() ); // 생성된 마커들의 최소 최대 위도경도 값을 계산하기위해 include

        LatLngBounds bounds = builder.build(); // 마커들이 한번에 보이는 범위 값이 담김.

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.15); // offset from edges of the map 10% of screen

        if(number < 2) {
            gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng, zoomLv) );   // 화면 바로 전환 ( 마커 1개 일때 )
        } else {
            gMap.moveCamera( CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding) );   // 화면 바로 전환 (bounds값에 따라 범위가 정해짐)
        }

        // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);   // 부드럽게 화면 이동 (버벅임 있음)
        // gMap.animateCamera(cu);
    }

    /*////////////////////////////////
              마커 간 연결 선
    */////////////////////////////////

    private void setPolyLine() {

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll( LatLngList );
        polyline = gMap.addPolyline( polylineOptions );
        polyline.setWidth(7);
        polyline.setColor(Color.rgb(0, 162, 212));
        polyline.setPattern(PATTERN_POLYLINE_DASH); // 패턴 설정
    }

    /*//////////////////////////////////////////////////////////////
               커스텀 마커 View를 Bitmap으로 전환시키는 메소드
    *///////////////////////////////////////////////////////////////

    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    //////////////////////////////////////////////////////////////////////////------Google Map Api

    // 일정 제목 Text값 STATIC에 저장하기위해 전달
    public String getScheduleContext() {
        return input_title_ed.getText().toString();
    }

}//----------END

