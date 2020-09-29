package com.androidlec.jejukat;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class Activity_Addplace extends AppCompatActivity implements OnMapReadyCallback{
    final int PICTURE_REQUEST_CODE = 100;

    GoogleMap gMap;
    ArrayList<Bean_Search> data_cat2;

    Adapter_addplace_cat2 adapter_cat2;
    Adapter_Addplace_imgs adapter_img;

    ImageView back;
    TextView submit;
    Button cat1,cat2,cat3,cat4,insert_img,clear_img;
    EditText searchplace,title,address,telno,homepage,context;
    ScrollView scrollView;
    RecyclerView rv_cat2;
    CustomGridView gridView;

    String [] cat2_1 = {"자연","문화관광","레저체험","테마관광지","섬속의섬","도보","제주4.3"};
    String [] cat2_2 = {"향토","한식","양식","일식","중식"};
    String [] cat2_3 = {"안전민박","관광호텔","전통가족호텔","호스텔","휴양펜션","콘도","일반숙박","농어촌민박","유스호스텔"};

    String check_input = "";
    String place = "제주도";
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplace);

        back = findViewById(R.id.img_addplace_back);
        submit = findViewById(R.id.tv_submit_addplace);
        cat1 = findViewById(R.id.btn_cat1_1_addplace);
        cat2 = findViewById(R.id.btn_cat1_2_addplace);
        cat3 = findViewById(R.id.btn_cat1_3_addplace);
        cat4 = findViewById(R.id.btn_cat1_4_addplace);
        insert_img = findViewById(R.id.btn_AddPlacActivity_load_image);
        clear_img = findViewById(R.id.btn_AddPlacActivity_clear_image);
        searchplace = findViewById(R.id.et_findaddress_addplace);
        title = findViewById(R.id.et_AddPlaceActivity_input_placename);
        address = findViewById(R.id.et_AddPlaceActivity_input_placeaddress);
        telno = findViewById(R.id.et_AddPlaceActivity_input_placetel);
        homepage = findViewById(R.id.et_AddPlaceActivity_input_placehomepage);
        context = findViewById(R.id.et_AddPlaceActivity_input_placecontext);

        scrollView = findViewById(R.id.sv_addplace);
        gridView = findViewById(R.id.gv_img_addplace);
        rv_cat2 = findViewById(R.id.rv_cat2_addplace);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv_cat2.setLayoutManager(layoutManager);

        back.setOnClickListener(click);
        submit.setOnClickListener(click);
        cat1.setOnClickListener(click);
        cat2.setOnClickListener(click);
        cat3.setOnClickListener(click);
        cat4.setOnClickListener(click);
        insert_img.setOnClickListener(click);
        clear_img.setOnClickListener(click);

        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        gridView.setExpanded(true);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_addplace);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder( Activity_Addplace.this );

    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            data_cat2 = new ArrayList<Bean_Search>();
            switch (view.getId()){

                case R.id.img_addplace_back:
                    onBackPressed();
                    break;

                case R.id.tv_submit_addplace:
                    if( checkForm() == true ) {
                        addPlace();
                        Intent intent = new Intent(Activity_Addplace.this,Activity_Main.class);
                        startActivity(intent);
                    }else{
                    Toast.makeText(Activity_Addplace.this, check_input + "을(를) 입력해주세요.", Toast.LENGTH_SHORT).show();}
                    break;


                case R.id.btn_cat1_1_addplace:
                    data_cat2.clear();
                    STATICDATA.TEMP_CAT1 = "관광";
                    STATICDATA.TEMP_CAT2 = "";
                    for (int i=0; i<cat2_1.length;i++){
                        data_cat2.add(new Bean_Search(cat2_1[i]));
                    }
                    adapter_cat2 = new Adapter_addplace_cat2(data_cat2,Activity_Addplace.this);
                    rv_cat2.setAdapter(adapter_cat2);
                    rv_cat2.setVisibility(View.VISIBLE);
                    Toast.makeText(Activity_Addplace.this, STATICDATA.TEMP_CAT1+"을(를) 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_cat1_2_addplace:
                    data_cat2.clear();
                    STATICDATA.TEMP_CAT1 = "맛집";
                    STATICDATA.TEMP_CAT2 = "";
                    for (int i=0; i<cat2_2.length;i++){
                        data_cat2.add(new Bean_Search(cat2_2[i]));
                    }
                    adapter_cat2 = new Adapter_addplace_cat2(data_cat2,Activity_Addplace.this);
                    rv_cat2.setAdapter(adapter_cat2);
                    rv_cat2.setVisibility(View.VISIBLE);
                    Toast.makeText(Activity_Addplace.this, STATICDATA.TEMP_CAT1+"을(를) 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_cat1_3_addplace:
                    data_cat2.clear();
                    STATICDATA.TEMP_CAT1 = "숙소";
                    STATICDATA.TEMP_CAT2 = "";
                    for (int i=0; i<cat2_3.length;i++){
                        data_cat2.add(new Bean_Search(cat2_3[i]));
                    }
                    adapter_cat2 = new Adapter_addplace_cat2(data_cat2,Activity_Addplace.this);
                    rv_cat2.setAdapter(adapter_cat2);
                    rv_cat2.setVisibility(View.VISIBLE);
                    Toast.makeText(Activity_Addplace.this, STATICDATA.TEMP_CAT1+"을(를) 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_cat1_4_addplace:
                    data_cat2.clear();
                    STATICDATA.TEMP_CAT1 = "카페";
                    STATICDATA.TEMP_CAT2 = "카페";
                    rv_cat2.setVisibility(View.GONE);
                    Toast.makeText(Activity_Addplace.this, STATICDATA.TEMP_CAT1+"을(를) 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.btn_AddPlacActivity_load_image:
                    STATICDATA.URIS=null;
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    //사진을 여러개 선택할수 있도록 한다
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);
                    break;

                case R.id.btn_AddPlacActivity_clear_image:
                    if (STATICDATA.URIS != null){
                        STATICDATA.URIS = null;
                        adapter_img.clear();
                        adapter_img.notifyDataSetChanged();
                    }
                    break;

            }
        }
    };
    private Boolean checkForm() {
        check_input = "";
        if( STATICDATA.TEMP_CAT1.equals("") ) {
            check_input = "대분류";
            return false;
        }
        else if( STATICDATA.TEMP_CAT2.equals("")) {
            check_input = "중분류";
            return false;
        }
        else if(title.getText().toString().equals("")) {
            check_input = "장소 명";
            return false;
        }
        else if(address.getText().toString().equals("")) {
            check_input = "주소";
            return false;
        }
        else if(context.getText().toString().equals("")) {
            check_input = "내용";
            return false;
        }
        return true;
    }


    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText( Activity_Addplace.this, "", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨",Toast.LENGTH_SHORT).show();
        }
    };




    /*///////////////////////////////////////////////////////////////
    //          갤러리에서 선택한 이미지들을 받아오는 메소드
    *////////////////////////////////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        STATICDATA.URIS = new ArrayList<Uri>();
        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // 1개의 ClipData 는 Uri에 바로 담아서 add
                Uri urione = data.getData();
                // ClipData 를 가져온다.
                ClipData clipData = data.getClipData();

                Log.v("uris",clipData.toString());


                if (clipData != null) {
                    // 갤러리에서 가져온 이미지 Uri 담기
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        if (i < clipData.getItemCount()) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            Log.v("uri",uri.toString());
                            STATICDATA.URIS.add(uri);
                        }
                    }
                } else if (urione != null) {
                    STATICDATA.URIS.add(urione);
                }
            }
        }
        // 그리드 뷰 생성
        if (STATICDATA.URIS != null) {
            adapter_img = new Adapter_Addplace_imgs( Activity_Addplace.this, R.layout.gridview_item, STATICDATA.URIS ) ;
            gridView.setAdapter( adapter_img );
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 초기 생성 맵
        gMap = googleMap;
        setMarker(gMap, geoCoder(place), 13);

        searchplace.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN)&&(i==KeyEvent.KEYCODE_ENTER)){
                    place = searchplace.getText().toString();
                    gMap.clear(); // 지도상의 마커 제거
                    setMarker(gMap, geoCoder(place), 17);
                    searchplace.setText("");
                    return true;
                }
                return false;
            }
        });


        // 구글맵 카메라 이동이 멈춘상태에서 기능
        gMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                currentlocation();
            }
        });

        // 구글맵 카메라 이동시 기능
        gMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                // 지도 스크롤 중 부모 스크롤뷰에 Touch Event를 뺏기지 않게 설정
                scrollView.requestDisallowInterceptTouchEvent(true);
                /* 이슈---------------------------------------
                    가로스크롤이 선행되면 문제 없으나
                    세로로 먼저 스크롤하면 부모 스크롤뷰가 움직임.
                 */
            }
        });
    }
     /*/////////////////////////////////////////////////////////////////////////////
    //      현재 맵 화면의 중심 좌표와 주소값을 가져오는 메소드 ( 좌표 -> 주소 )
    *//////////////////////////////////////////////////////////////////////////////

    private void currentlocation() {

        LatLng centerLatLng = gMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
        double lat = centerLatLng.latitude;
        double lon = centerLatLng.longitude;
        List<Address> addressList = null;
        try {
            // 리버스 지오코딩 좌표 -> 주소
            addressList =  geocoder.getFromLocation(lat, lon, 1); // 좌표1, 좌표2, 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressList != null) {
            if (addressList.size() == 0) {
                Log.v("Status: ", "해당되는 주소 정보는 없습니다" );
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = addressList.get(0);
                String locationName = addr.getLocality() + " " + addr.getThoroughfare(); // 지명
                String feature = addr.getFeatureName(); // 지번
//                Log.v("Location", addr.toString());
                // 마커 제거
                gMap.clear();
                // 뷰 중심에 새로운 마커 갱신
                Marker marker = gMap.addMarker( new MarkerOptions()
                                .position( centerLatLng )
                                .title( locationName )
//                        .snippet( feature )
                );
                if(locationName != null || locationName != "") { // 안먹힘^^
                    marker.showInfoWindow();
                }
                // 주소란 자동 입력
                address.setText(locationName + " " + feature);
            }
        }
    }

    /*////////////////////////////////
    //   주소 -> 좌표  전환 메소드
    */////////////////////////////////

    private LatLng geoCoder(String place) {
        List<Address> list = null;
        LatLng latLng = null;
        try {
            // 지오코딩 주소 -> 좌표
            list = geocoder.getFromLocationName
                    (place, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
            if (list.size() == 0) {
                Log.v("Status: ", "해당되는 주소 정보는 없습니다" );
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);
                double lat = addr.getLatitude();
                double lon = addr.getLongitude();
                latLng = new LatLng(lat, lon);
            }
        }
        return latLng;
    }

    /*////////////////////////////////
    //            마커 생성
    */////////////////////////////////

    private void setMarker(GoogleMap gMap, LatLng latLng, int zoomLv) {
        if(latLng==null){
            Toast.makeText(this, "정확한 주소나 장소명을 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }else{
            Marker marker = gMap.addMarker( new MarkerOptions()
                    .position( latLng )
                    .title(place)
                    .snippet( latLng.toString() )
            );
            gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng,zoomLv) );}
    }

    public void addPlace() {
        Log.v("JSON", STATICDATA.URIS.toString());
        ConnectFTP addPlaceConnectFTP = new ConnectFTP(Activity_Addplace.this, "119.207.169.213", "tj", "1234", 21, STATICDATA.URIS );
        addPlaceConnectFTP.execute();

        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                if(STATICDATA.FTPUPLOAD == 1) {
//                    Toast.makeText(AddPlace_Activity.this, "FTP 업로드 성공!", Toast.LENGTH_SHORT).show();
                    createPlaceData();
                } else {
//                    Toast.makeText(AddPlace_Activity.this, "FTP 업로드 중..", Toast.LENGTH_SHORT).show();
                    mHandler.postDelayed(this, 1500);
                }
            }
        }, 1500);
    }



    /*///////////////////////////////////////////
    //      장소 이미지, 상세정보 JSON 가공
    *////////////////////////////////////////////

    private void createPlaceData() {
//        Log.v("FILENAMES", "createPlaceData()");

        // JSON 담을 String 변수
        String images;
        String basicinfo;

        // EditText에서 데이터 가져오기
        String strtitle = title.getText().toString();
        String strcontext = context.getText().toString();
        String straddress = address.getText().toString();
        String strtel = "(82+) " + telno.getText().toString();
        String strhomepage = homepage.getText().toString();

        // STATICDATA 에서 FTP업로드때 저장한 이미지 이름들을 가져옴.
        ArrayList<String> imgArray = STATICDATA.FILENAMES;

        // 문자열 배열에 담기
        String[] image = new String[imgArray.size()];
        for(int i=0; i<imgArray.size(); i++ ) {
//            Log.v("ImageValue", image[i]);
            image[i] = imgArray.get(i) + ".jpg";
        }

        try {
            // 장소 이미지
            JSONArray placaimage = new JSONArray(Arrays.asList(image));

            // 장소 상세정보
            JSONObject placeinfo = new JSONObject();
            placeinfo.put("주소", straddress );
            placeinfo.put("연락처", strtel );
            placeinfo.put("홈페이지", strhomepage );

            // 장소 상세정보 배열로 담기
            JSONArray placeinfoarr = new JSONArray();
            placeinfoarr.put(placeinfo);

            // 장소 상세정보 다시 JSONObject에 담기
            JSONObject placebasicinfo = new JSONObject();
            placebasicinfo.put("basicinfo", placeinfoarr);

            // JSON to String
            images = placaimage.toString();
            basicinfo = placeinfo.toString();

//                Log.v("JSON", images);
//                Log.v("JSON", basicinfo);

            // 장소정보 모두 JSONObject로 가공하여 NetworkTask로 보내어 다시 String으로 전환하여 POST방식으로 전달
            JSONObject placeData = new JSONObject();
            placeData.put("cat1", STATICDATA.TEMP_CAT1);
            placeData.put("cat2", STATICDATA.TEMP_CAT2);
            placeData.put("title", strtitle);
            placeData.put("context", strcontext);
            placeData.put("images", images);
            placeData.put("basicinfo", basicinfo);


            Log.v("Json",placeData.toString());

            // FILENAMES 초기화
            STATICDATA.FILENAMES.clear();
            String urlAddr = "http://119.207.169.213:8080/jejukat/jejukat_place_insert.jsp?";  //아이피 받아서 넘기는 부분

            connectInsertData(urlAddr, placeData);

        }catch (Exception e) {
            Log.e("Status", "JSON Err");
            e.printStackTrace();
        }
    }



    /*//////////////////////////////////////////////////////////
    //      장소 데이터 AddPlace_InsNetworkTask 로 전달
    *///////////////////////////////////////////////////////////

    private void connectInsertData(String urlAddr, JSONObject placeData){
//        Log.v("URL", urlAddr);
        try {
            NetworkTask_Addplace addPlace_insNetworkTask = new NetworkTask_Addplace(Activity_Addplace.this, urlAddr, placeData); // 생성자를 만들거야 라고 정의해서쓰고 만들고오면 에러없어짐 /
            addPlace_insNetworkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
