package com.androidlec.jejukat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class Activity_Main extends AppCompatActivity {

    int position = 0;

    ArrayList<Bean_Main> data_cat,data_ad;
    Adapter_Main_cat adapter_cat;

    Toolbar toolbar;
    ImageView menu,search,write,notification,profile;
    TextView nickname,email;
    RecyclerView rv_cat;
    ViewPager vp_ad;
    EditText searchtext;
    DrawerLayout nav_draw_left;
    NavigationView nav_view_left;
    ScrollView scrollView;
    View header;


    Intent login, result, newplace, viewed, bookmark, myplaces, myschedules, newschedule;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);
        menu = findViewById(R.id.img_toolbar_menu);
        search = findViewById(R.id.img_toolbar_search);
        write = findViewById(R.id.img_toolbar_new);
        notification = findViewById(R.id.img_toolbar_bell);
        nav_draw_left = findViewById(R.id.drawer_main_left);
        nav_view_left = findViewById(R.id.nav_view_left);
        scrollView = findViewById(R.id.scrollView_main);
        searchtext = findViewById(R.id.et_main_search);
        vp_ad = findViewById(R.id.vp_main_ad);
        rv_cat = findViewById(R.id.rv_main_cat);


        header = nav_view_left.getHeaderView(0);
        nickname =header.findViewById(R.id.tv_navHeader_nickname);
        email =header.findViewById(R.id.tv_navHeader_email);
        profile=header.findViewById(R.id.img_navHeader);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        login = new Intent(this, Activity_Login.class);
        newplace = new Intent(this,Activity_Addplace.class);
        newschedule = new Intent(this,Activity_AddSchedule.class);
        result = new Intent(this,Activity_Search.class);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        viewed = new Intent(this, Activity_ViewedList.class);
        bookmark = new Intent(this, Activity_LikeList.class);
        myplaces = new Intent(this, Activity_MyPlaceList.class);
        myschedules = new Intent(this, Activity_MyScheduleList.class);

        // 액션바
        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        menu.setOnClickListener(click);
        search.setOnClickListener(click);
        write.setOnClickListener(click);
        notification.setOnClickListener(click);
        ///////////////////////////////////////////////////////////////////////////////////////////



        //네비게이션 드로어
        drawer_setMenu(); //line187

        nav_view_left.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_item_login:
                        startActivity(login);
                        break;

                    case R.id.nav_item_logout:

                        //STATICDATA 초기화;
                        STATICDATA.LOGIN_STATUS=0;
                        STATICDATA.LOGIN_METHOD=0;
                        STATICDATA.NICKNAME="비회원";
                        STATICDATA.EMAIL="하단의 로그인 버튼을 눌러주세요";
                        STATICDATA.PROFILEPIC="";

                        //sharedPref 초기화;
                        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        //메뉴초기화
                        drawer_setMenu();
                        break;

                    case R.id.nav_item_viewed_null: //비회원
                        startActivity(viewed);
                        break;
                    case R.id.nav_item_viewed: //로그인했을때
                        startActivity(viewed);
                        break;
                    //찜목록
                    case R.id.nav_item_bookmark:
                        startActivity(bookmark);
                        break;
                    //내장소
                    case R.id.nav_item_places:
                        startActivity(myplaces);
                        break;
                    //내일정
                    case R.id.nav_item_schedules:
                        startActivity(myschedules);
                        break;
                }

                return false;
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////



        //텍스트뷰 엔터키
        searchtext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN)&&(i==KeyEvent.KEYCODE_ENTER)){
                    Toast.makeText(Activity_Main.this, "검색시작~", Toast.LENGTH_SHORT).show();
                    STATICDATA.SEARCHTAG = searchtext.getText().toString();
                    startActivity(result);
                    return true;
                }
                return false;
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////



        //스크롤뷰
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        ///////////////////////////////////////////////////////////////////////////////////////////




        //광고 슬라이드
        data_ad = new ArrayList<Bean_Main>();
        data_ad.add(new Bean_Main(R.drawable.ad_socar,"https://play.google.com/store/apps/details?id=socar.Socar&hl=ko"));
        data_ad.add(new Bean_Main(R.drawable.ad_jejuair, "https://www.jejuair.net/jejuair/kr/main.do"));
        data_ad.add(new Bean_Main(R.drawable.ad_yanolja,"https://play.google.com/store/apps/details?id=com.cultsotry.yanolja.nativeapp&hl=ko"));
        data_ad.add(new Bean_Main(R.drawable.ad_shotel,"https://www.shillahotels.com/membership/inquires/aboutShilla/memJejuHotel.do"));

        vp_ad.setAdapter(new Adapter_Main_ad(this, data_ad));





        //자동 넘기기
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                vp_ad.setCurrentItem(position++,true);
            }
        };

        Timer timer = new Timer();
        timer.schedule((new TimerTask() {
            @Override
            public void run() {
                try {
                    handler.post(update);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }),500,5000);
        ///////////////////////////////////////////////////////////////////////////////////////////




        //카테고리
        data_cat = new ArrayList<Bean_Main>();
        data_cat.add(new Bean_Main(R.drawable.cat_schedule,"#BB95CF","일정","놀기도 아까운 하루"));
        data_cat.add(new Bean_Main(R.drawable.cat_trip,"#50A73E","관광","남들은 모르는 나만의 모험"));
        data_cat.add(new Bean_Main(R.drawable.cat_food,"#FC7F65","맛집","한라산도 식후경"));
        data_cat.add(new Bean_Main(R.drawable.cat_hotel,"#72C0D6","숙소","목적과 예산에 맞는 숙소"));
        data_cat.add(new Bean_Main(R.drawable.cat_cafe,"#E89F4C","카페","커피 한잔의 여유"));

        rv_cat.setLayoutManager(new LinearLayoutManager(this));
        adapter_cat = new Adapter_Main_cat(data_cat);
        rv_cat.setAdapter(adapter_cat);



        ///////////////////////////////////////////////////////////////////////////////////////////
    }

    View.OnClickListener click = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.img_toolbar_menu:
                    nav_draw_left.openDrawer(GravityCompat.START);
                    break;
                case R.id.img_toolbar_search:
                    if (searchtext.getVisibility()==View.GONE){
                        searchtext.setVisibility(View.VISIBLE);
                    }else{
                        searchtext.setVisibility(View.GONE);
                    }
                    break;
                case R.id.img_toolbar_new:
                    if (STATICDATA.LOGIN_STATUS==0){
                        Toast.makeText(Activity_Main.this, "로그인후 이용해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        String [] format = {"일정","장소"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Main.this);
                        builder
                                .setTitle("게시글의 양식을 선택해주세요.")
                                .setItems(format, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i){
                                            case 0:
                                                startActivity(newschedule);
                                                break;
                                            case 1:
                                                startActivity(newplace);
                                                break;

                                        }

                                    }
                                })
                                .show();
                    }
                    break;

                case R.id.img_toolbar_bell:
                    Intent intentNotification = new Intent(Activity_Main.this, Activity_NotificationList.class);
                    startActivity(intentNotification);
                    break;
            }
        }
    };


    public void drawer_setMenu (){
        switch (STATICDATA.LOGIN_STATUS){
            case 0:
                nav_view_left.getMenu().clear();
                nav_view_left.inflateMenu(R.menu.nav_item_main_null);
                Glide.with(this).load("http://"+STATICDATA.PROFILEPIC)
                        .placeholder(R.drawable.icon_user_null)
                        .error(R.drawable.icon_user_null)
                        .into(profile);
                nickname.setText(STATICDATA.NICKNAME);
                email.setText(STATICDATA.EMAIL);
                break;
            case 1:
                nav_view_left.getMenu().clear();
                nav_view_left.inflateMenu(R.menu.nav_item_main);
                Glide.with(this).load("http://"+STATICDATA.PROFILEPIC)
                        .placeholder(R.drawable.icon_user_null)
                        .error(R.drawable.icon_user_null)
                        .into(profile);
                nickname.setText(STATICDATA.NICKNAME);
                email.setText(STATICDATA.EMAIL);
                break;
        }
    }

    @Override
    protected void onStart() {
        drawer_setMenu();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
