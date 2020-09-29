package com.androidlec.jejukat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Activity_Search extends AppCompatActivity {


    ArrayList<Bean_Search> data_cat2, data_result;
    Adapter_Search_result adapter_result;
    Adapter_search_cat2 adapter_cat2;

    Toolbar toolbar;
    ImageView menu,search,write,notification,profile;
    TextView nickname,email;
    EditText et_search;
    DrawerLayout nav_draw_left;
    NavigationView nav_view_left;
    View header;
    Button cat1_1,cat1_2,cat1_3,cat1_4,cat1_5,orderName,orderView,orderLatest;

    LinearLayout showsort,sortmenu,listcat1;

    RecyclerView rv_cat2, rv_search;

    String [] cat1 ={"일정","관광","맛집","숙소","카페"};

    String [] cat2_1 = {"전체"};
    String [] cat2_2 = {"전체","자연","문화관광","레저체험","테마관광지","섬속의섬","도보","제주4.3"};
    String [] cat2_3 = {"전체","향토","한식","양식","일식","중식"};
    String [] cat2_4 = {"전체","안전민박","관광호텔","전통가족호텔","호스텔","휴양펜션","콘도","일반숙박","농어촌민박","유스호스텔"};
    String [] cat2_5 = {"전체"};

    Intent login, newplace, viewed, bookmark, myplaces, myschedules, newschedule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar_main);
        menu = findViewById(R.id.img_toolbar_menu);
        search = findViewById(R.id.img_toolbar_search);
        write = findViewById(R.id.img_toolbar_new);
        notification = findViewById(R.id.img_toolbar_bell);
        nav_draw_left = findViewById(R.id.drawer_main_left);
        nav_view_left = findViewById(R.id.nav_view_left);
        et_search = findViewById(R.id.et_main_search);
        showsort = findViewById(R.id.layout_show_sort);
        sortmenu = findViewById(R.id.layout_sort_menu);
        listcat1 = findViewById(R.id.layout_cat1);
        rv_search = findViewById(R.id.rv_search);
        rv_cat2 = findViewById(R.id.rv_cat2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv_cat2.setLayoutManager(layoutManager);

        cat1_1 = findViewById(R.id.btn_cat1_1);
        cat1_2 = findViewById(R.id.btn_cat1_2);
        cat1_3 = findViewById(R.id.btn_cat1_3);
        cat1_4 = findViewById(R.id.btn_cat1_4);
        cat1_5 = findViewById(R.id.btn_cat1_5);

        orderName = findViewById(R.id.btn_order_name);
        orderLatest = findViewById(R.id.btn_order_latest);
        orderView = findViewById(R.id.btn_order_view);

        header = nav_view_left.getHeaderView(0);
        nickname = header.findViewById(R.id.tv_navHeader_nickname);
        email = header.findViewById(R.id.tv_navHeader_email);
        profile= header.findViewById(R.id.img_navHeader);

        login = new Intent(this, Activity_Login.class);
        newplace = new Intent(this,Activity_Addplace.class);
        newschedule = new Intent(this,Activity_AddSchedule.class);
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
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN)&&(i==KeyEvent.KEYCODE_ENTER)){
                    STATICDATA.SEARCHTAG=et_search.getText().toString();
                    search_datas();
                    return true;
                }
                return false;
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////

        showsort.setOnClickListener(click);
        cat1_1.setOnClickListener(click);
        cat1_2.setOnClickListener(click);
        cat1_3.setOnClickListener(click);
        cat1_4.setOnClickListener(click);
        cat1_5.setOnClickListener(click);
        orderView.setOnClickListener(click);
        orderLatest.setOnClickListener(click);
        orderName.setOnClickListener(click);

        search_datas();

    }

    public void drawer_setMenu (){
        switch (STATICDATA.LOGIN_STATUS){
            case 0:
                nav_view_left.getMenu().clear();
                nav_view_left.inflateMenu(R.menu.nav_item_main_null);
                nickname.setText(STATICDATA.NICKNAME);
                email.setText(STATICDATA.EMAIL);
                Glide.with(this).load("http://"+STATICDATA.PROFILEPIC)
                        .placeholder(R.drawable.icon_user_null)
                        .error(R.drawable.icon_user_null)
                        .into(profile);
                break;
            case 1:
                nav_view_left.getMenu().clear();
                nav_view_left.inflateMenu(R.menu.nav_item_main);
                nickname.setText(STATICDATA.NICKNAME);
                email.setText(STATICDATA.EMAIL);
                Glide.with(this).load("http://"+STATICDATA.PROFILEPIC)
                        .placeholder(R.drawable.icon_user_null)
                        .error(R.drawable.icon_user_null)
                        .into(profile);
                break;
        }
    }


    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            data_cat2 =  new ArrayList<Bean_Search>();

            switch (view.getId()){
                case R.id.img_toolbar_menu:
                    nav_draw_left.openDrawer(GravityCompat.START);
                    break;
                case R.id.img_toolbar_search:
                    if (et_search.getVisibility()==View.GONE){
                        et_search.setVisibility(View.VISIBLE);
                    }else{
                        et_search.setVisibility(View.GONE);
                    }
                    break;
                case R.id.layout_show_sort:
                    if (sortmenu.getVisibility()==View.GONE){
                        sortmenu.setVisibility(View.VISIBLE);
                    }else{
                        sortmenu.setVisibility(View.GONE);
                    }
                    break;
                case R.id.img_toolbar_new:
                    if (STATICDATA.LOGIN_STATUS==0){
                        Toast.makeText(Activity_Search.this, "로그인후 이용해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        String [] format = {"일정","장소"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Search.this);
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


                case R.id.btn_cat1_1:
                    STATICDATA.CAT1=cat1[0];
                    STATICDATA.CAT2="";
                    search_datas();
                    rv_cat2.setVisibility(View.GONE);
                    break;
                case R.id.btn_cat1_2:
                    data_cat2.clear();
                    STATICDATA.CAT1=cat1[1];
                    STATICDATA.CAT2="";
                    search_datas();
                    for (int i = 0; i<cat2_2.length; i++){
                        data_cat2.add(new Bean_Search(cat2_2[i]));
                    }
                    adapter_cat2 = new Adapter_search_cat2(data_cat2,Activity_Search.this);
                    rv_cat2.setAdapter(adapter_cat2);
                    rv_cat2.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_cat1_3:
                    data_cat2.clear();
                    STATICDATA.CAT1=cat1[2];
                    STATICDATA.CAT2="";
                    search_datas();
                    for (int i = 0; i<cat2_3.length; i++){
                        Log.v("array",cat2_3[i]);
                        data_cat2.add(new Bean_Search(cat2_3[i]));
                    }
                    Log.v("data",data_cat2.get(1).getCat2());
                    adapter_cat2 = new Adapter_search_cat2(data_cat2,Activity_Search.this);
                    rv_cat2.setAdapter(adapter_cat2);
                    rv_cat2.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_cat1_4:
                    data_cat2.clear();
                    STATICDATA.CAT1=cat1[3];
                    STATICDATA.CAT2="";
                    search_datas();
                    for (int i = 0; i<cat2_4.length; i++){
                        data_cat2.add(new Bean_Search(cat2_4[i]));
                    }
                    adapter_cat2 = new Adapter_search_cat2(data_cat2,Activity_Search.this);
                    rv_cat2.setAdapter(adapter_cat2);
                    rv_cat2.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_cat1_5:
                    STATICDATA.CAT1=cat1[4];
                    STATICDATA.CAT2="";
                    search_datas();
                    rv_cat2.setVisibility(View.GONE);
                    break;
                case R.id.btn_order_name:
                    if(STATICDATA.ORDER.equals("title")){
                        STATICDATA.ORDER="title desc";
                    }else{
                        STATICDATA.ORDER="title";
                    }
                    search_datas();
                    break;
                case R.id.btn_order_view:
                    if(STATICDATA.ORDER.equals("view desc")){
                        STATICDATA.ORDER="view";
                    }else{
                        STATICDATA.ORDER="view desc";
                    }
                    search_datas();
                    break;
                case R.id.btn_order_latest:
                    if(STATICDATA.ORDER.equals("seq_post")){
                        STATICDATA.ORDER="seq_post desc";
                    }else{
                        STATICDATA.ORDER="seq_post";
                    }
                    search_datas();
                    break;

            }
        }
    };

    protected void search_datas() {
        String urladdr = "http://119.207.169.213:8080/jejukat/jejukat_query_all.jsp?cat1="+STATICDATA.CAT1+"&cat2="+STATICDATA.CAT2+"&search="+STATICDATA.SEARCHTAG+"&order="+STATICDATA.ORDER;
        try{
            data_result = new ArrayList<Bean_Search>();
            NetworkTask_get_Result NetworkTask = new NetworkTask_get_Result(Activity_Search.this,urladdr);
            data_result = (ArrayList<Bean_Search>)NetworkTask.execute().get();
            ListUpdate(data_result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ListUpdate(ArrayList<Bean_Search> data){
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        rv_search.setLayoutManager(new LinearLayoutManager(this)) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter_result = new Adapter_Search_result(data, Activity_Search.this) ;
        rv_search.setAdapter(adapter_result) ;
    }

}
