package com.androidlec.jejukat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class Activity_Detail_Schedule extends AppCompatActivity {

    ////전역변수////////////////

    //위젯
    TextView select_cat1_tv, select_cat2_tv, select_title_tv, select_view_tv, select_uploader_tv;
    ScrollView scrollview;

    //찜하기
    ImageView like_iv, unlike_iv,back;
    SQLiteHelper_MyLikeList detailsPost_sqLiteHelper_myLikeList; // SQLite 선언
    Integer like_seq_like, like_seq_post;
    String like_seq_user, like_title, like_cat1, like_cat2, like_image, like_nickname;
    ArrayList<Bean_Detail_Schedule_Likelist> likelists ;
    int seletedLike_seq_post; // 선택된 값 확인할라고

    //SQLite
    SQLiteDatabase DB;

//    private String centIP = "192.168.0.113"; /////////IP 여기만 변경하면됨/////
    private int seq_post = STATICDATA.SEQ_POST;  ///////////테스트 //////////// 앞에 검색리스트에서 받아와야함 .
    private String seq_user = STATICDATA.SEQ_USER; //   /////////테스트//////////////// 앞에 리스트 에서 넘겨받아야함
//    static int seq_post = ScheduleDetails_STATIC_DATA.SEQ_POST;
//    static String seq_user = ScheduleDetails_STATIC_DATA.SEQ_USER;


    //DB DetailsPost 불러오기
    private String urlAddrDetailsPost;
    private ArrayList<Bean_Detail_Schedule_Select> data = null;

    //이미지
    private ArrayList<String> imageList;
    private static final int DP = 6;
    ViewPager viewPagerImages; // 이미지

    //탭레이아웃
    private FragmentPagerAdapter fragmentPagerAdapter; //FragmentPager어댑터
    ViewPager viewPagerTabLayout; // 탭레이아웃 뷰페이져
    TextView context_frag_detailsPost_tv;


    //-----END 전역변수-----------------





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduledetails);

        // PLACEARRAY 는 직접적으로 adapter와 연결되는 arraylist
        STATICDATA.PLACEARRAY = new ArrayList<>();

        //찜하기 SQLite
        detailsPost_sqLiteHelper_myLikeList
                = new SQLiteHelper_MyLikeList(Activity_Detail_Schedule.this);

        ///연결
        select_cat1_tv = findViewById(R.id.tv_scheduledetailsPost_cat1);
        select_cat2_tv = findViewById(R.id.tv_scheduledetailsPost_cat2);
        select_title_tv = findViewById(R.id.tv_scheduledetailsPost_title);
        select_view_tv = findViewById(R.id.tv_scheduledetailsPost_view);
        select_uploader_tv = findViewById(R.id.tv_scheduledetailsPost_uploader);
        scrollview = findViewById(R.id.sv_scheduledetails);
        like_iv = findViewById(R.id.iv_scheduledetailsPost_like); //찜하기
        unlike_iv = findViewById(R.id.iv_scheduledetailsPost_unlike);
        back = findViewById(R.id.img_schedule_back);


        //오버스크롤
        OverScrollDecoratorHelper.setUpOverScroll(scrollview);

        //Method 상세정보 DB에서 받아와서 DTO에 넣기
        ConnectGetDetailsPostData();
        //Method 이미지 뷰페이져
        ViewPagerImages();



        //댓글에 필요한 정보 STATIC 선언/////////////////
//        ScheduleDetails_STATIC_DATA.CENTIP=centIP; // IP넘겨줌
        STATICDATA.SEQ_POST=seq_post; //seq_post넘겨줌
        STATICDATA.SEQ_USER=seq_user; //로그인한 값

        //TabLayout 뷰페이저
        viewPagerTabLayout = findViewById(R.id.scheduledetails_viewPager); //xml연결
        fragmentPagerAdapter = new Adapter_Detial_Schedule_ViewPager(getSupportFragmentManager()); // fragmentPager어댑터생성
        TabLayout tabLayout = findViewById(R.id.tab_scheduledetails_Layout); // 탭(지도/상세내용/댓글)
        viewPagerTabLayout.setAdapter(fragmentPagerAdapter); // 뷰페이저에 어댑터 연동
        tabLayout.setupWithViewPager(viewPagerTabLayout); // 탭레이아웃에 뷰페이저 연결
        //---------ent TabLayout 뷰페이저 ------------//



        // 찜목록에 있는지 확인
        CheckSeletedLike();
        Log.v("찜하기 :", "들어있는지 확인" + seletedLike_seq_post);

        ViewPagerImages();

        if( seletedLike_seq_post == 0 ) { // mylist에 없을떄
            unlike_iv.setVisibility(View.VISIBLE); // 빈하트
            like_iv.setVisibility(View.INVISIBLE);
        } else {
            unlike_iv.setVisibility(View.INVISIBLE); // 있을때 빨간하트
            like_iv.setVisibility(View.VISIBLE);
        }

//        리스너
        unlike_iv.setOnClickListener( onClickListener );
        like_iv.setOnClickListener( onClickListener );
        back.setOnClickListener(onClickListener);

    }//---------END onCreate----------

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //찜하기 누르면 값 받기
            like_seq_post = seq_post;
            like_seq_user = seq_user;
            like_cat1 = data.get(0).getCat1();
            like_cat2 = data.get(0).getCat2();
            like_nickname = STATICDATA.NICKNAME;
            like_title = data.get(0).getTitle();
            like_image = data.get(0).getImages().get(0);



            Log.v("찜하기", like_seq_post + "\n" + like_cat1 + "\n"+ like_cat2 + "\n"+ like_title + "\n"+ like_image);

            if(unlike_iv.getVisibility() == view.VISIBLE){
                unlike_iv.setVisibility(view.INVISIBLE);
                like_iv.setVisibility(view.VISIBLE);
            } else {
                unlike_iv.setVisibility(view.VISIBLE);
                like_iv.setVisibility(view.INVISIBLE);
            }

            switch (view.getId()){
                case R.id.iv_scheduledetailsPost_unlike:
                    try {
                        DB = detailsPost_sqLiteHelper_myLikeList.getWritableDatabase(); // DB 정의
//                        String insertQuery = "INSERT INTO mylikelist(seq_post, title, cat1, cat2, image) "
//                                + "VALUES ('" + like_seq_post + "', + '" + like_cat1 + "', + '" + like_cat2 + "', + '" + like_title + "', + '" + like_image + "');";
                        String insertQuery = "INSERT INTO mylikedlist(seq_user, seq_post, title, cat1, cat2, address, image) "
                                + "VALUES ('" + like_seq_user + "', + '" + like_seq_post + "', + '" + like_title + "', + '" + like_cat1 + "', + '" + like_cat2 + "', + '" + like_nickname + "', + '" + like_image + "');";
                        Log.v("query : ",insertQuery);
                        DB.execSQL(insertQuery);
                        detailsPost_sqLiteHelper_myLikeList.close();// DB 닫기
                        Log.v("찜하기 :", "SQLite 등록 완료");

                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.v("찜하기 :", "등록 오류");
                    }
                    break;
                case R.id.iv_scheduledetailsPost_like:
                    try {
                        DB = detailsPost_sqLiteHelper_myLikeList.getWritableDatabase(); // DB 정의
                        String deleteQuery = "DELETE FROM mylikedlist where seq_user = "+ seq_user +" AND seq_post = "+seq_post+";";
                        DB.execSQL(deleteQuery);
                        detailsPost_sqLiteHelper_myLikeList.close();// DB 닫기
                        Log.v("찜하기 :", "삭제 완료");

                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.v("찜하기 :", "삭제 오류");
                    }
                    break;
                case R.id.img_schedule_back:
                    onBackPressed();
                    break;


            }

        }
    };


    ///////////////////////////////////////////Method//////////////////////////////////////

    // 찜SQLite에 있는지 확인
    public void CheckSeletedLike(){

        try {
            DB = detailsPost_sqLiteHelper_myLikeList.getReadableDatabase();
            String query = "SELECT * FROM mylikedlist where seq_user = "+ seq_user +" AND seq_post = "+seq_post+";"; //조회

            // 이건 카운트로 조회함
            Cursor cursor = DB.rawQuery(query, null);
            seletedLike_seq_post = cursor.getCount(); //mylikelist에 북마크 들어가있는지 카운드로 확인

            cursor.close();
            detailsPost_sqLiteHelper_myLikeList.close();
            Toast.makeText(Activity_Detail_Schedule.this, "Select OK", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Activity_Detail_Schedule.this, "Select Error", Toast.LENGTH_SHORT).show();
        }
    }


    //뷰페이져이미지///////////////////////////////////////////////////
    public void ViewPagerImages() {
        //이미지링크 ArrayList에 저장
        imageList = new ArrayList(); //이미지 url를 저장하는 ArrayList<String>
        imageList = data.get(0).getImages();


        //ViewPager//
        viewPagerImages = findViewById(R.id.vp_scheduledetailspost_images);



        Log.v("TAG12", String.valueOf(imageList));


        //이미지뷰페이저 꾸민거
        viewPagerImages.setClipToPadding(false);
        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPagerImages.setPadding(margin, 0, margin, 0);
        viewPagerImages.setPageMargin(margin / 2);

        //어댑터 연결
        viewPagerImages.setAdapter(new Adapter_Detail_Schedule_Img(this, imageList));
    }//End 뷰페이져이미지


    //상세정보 요청 DTO에넣음////////////////////////////////////////////
    //jsp읽고 DTO에넣고 텍스트필드 채우기
    private void ConnectGetDetailsPostData() {
        urlAddrDetailsPost = "http://" + STATICDATA.CENTIP + ":8080/jejukat/DetailsPost_query_select.jsp?";
        urlAddrDetailsPost = urlAddrDetailsPost + "seq_post=" + seq_post; // seq_post로 받아옴

        try{
            NetworkTask_DetailSchedule_Select selectNetworkTask = new NetworkTask_DetailSchedule_Select( Activity_Detail_Schedule.this, urlAddrDetailsPost );
            Object obj = selectNetworkTask.execute( ).get( );
            data = (ArrayList<Bean_Detail_Schedule_Select>) obj;

            //fragment STATIC으로 넘겨주기
            STATICDATA.CONTEXT=data.get(0).getContext();

            //주소 ""값일때
            if (data.get(0).getBasicinfo().get(0).trim().equals("")){
                STATICDATA.ADDRESS = "제주특별자치도";
            } else {
                STATICDATA.ADDRESS = data.get(0).getBasicinfo().get(0).trim();
            }
//            Log.v("Current", "BasicInfo:" + data.get(0).getBasicinfo().get(0).trim()+"으로뜸");

            STATICDATA.TEL=data.get(0).getBasicinfo().get(1).trim();

            selectPickPlaces();
            setAllTextView(); //텍스트필드 채우기
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //텍스트필드 채우기
    private void setAllTextView() {
        select_cat1_tv.setText( data.get( 0 ).getCat1());
        select_cat2_tv.setText( data.get( 0 ).getCat2());
        select_title_tv.setText( data.get( 0 ).getTitle() );
        select_view_tv.setText( data.get( 0 ).getView());
        select_uploader_tv.setText( data.get( 0 ).getUploader() );

    }//END 상세정보 DTO ////////////////////////////////////


    private void selectPickPlaces() {
        urlAddrDetailsPost = "http://" + STATICDATA.CENTIP + ":8080/jejukat/AddSchedule_select_place.jsp?";
//        Log.v( "Current", String.valueOf( data.get( 0 ).getBasicinfo() ) );
        ArrayList<String> seq_arr = data.get( 0 ).getBasicinfo();
        try{
//            Log.v( "URL", urlAddrDetailsPost );
            NetworkTask_DetailSchedule_SelectPlaces scheduleDetails_networkTask_selectPickPlaces =
                    new NetworkTask_DetailSchedule_SelectPlaces( Activity_Detail_Schedule.this, urlAddrDetailsPost, seq_arr);
            Object obj = scheduleDetails_networkTask_selectPickPlaces.execute( ).get( );
            STATICDATA.PLACEARRAY  = (ArrayList<Bean_Detail_Schedule_PickPlacesList>) obj;

            setAllTextView(); //텍스트필드 채우기
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    //===========End Method===============================
}//----------