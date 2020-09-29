package com.androidlec.jejukat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Activity_Search2Pick extends AppCompatActivity {

    private TabLayout SearchTabLayout;   // 대분류 카테고리 탭 레이아웃
    private EditText input_search_et;   // 검색 EditText
    private TextView back_btn;

    private ArrayList<Bean_Search2Pick> places;
    private Adapter_AddSchedule_Search2Pick adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    private String urlAddr = "http://119.207.169.213:8080/jejukat/jejukat_search_place_all.jsp?cat1=&search=";
    private String cat1 = "";
    private String search = "";
    private String[] category1 = {"","관광","맛집","숙소","카페"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_searchplace );

        setWidgetId();    // ID 등록
        setListener();    // 리스너 등록
        SelectPlaces();

    }

    // ID 등록
    private void setWidgetId() {
        recyclerView = findViewById(R.id.rv_addschedule_search_place);   // 리사이클러 뷰
        SearchTabLayout = findViewById(R.id.tl_addschedule_searchresult);   // 대분류 카테고리 탭 레이아웃
        input_search_et = findViewById(R.id.et_addschedule_searchplace_input_search);   // 검색어 입력란
        back_btn = findViewById(R.id.tv_addschedule_searchplace_back);
    }

    // 리스너 등록
    private void setListener() {

        /*//////////////////////////////////
               대분류 탭 레이아웃 리스너
        *///////////////////////////////////

        SearchTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                cat1 = category1[pos];
                search = input_search_et.getText().toString();
                urlAddr = "";
                urlAddr = "http://"+ STATICDATA2.CENTIP +":8080/jejukat/jejukat_search_place_all.jsp?";
                urlAddr = urlAddr + "cat1=" + cat1 + "&search=" + search;
                SelectPlaces();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        /*//////////////////////////////////
               검색어 입력란 리스너
        *///////////////////////////////////

        input_search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                int actionId = i;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:  // ENTER
                        Toast.makeText(Activity_Search2Pick.this, "ENTER", Toast.LENGTH_SHORT).show();
                        search = input_search_et.getText().toString();
                        urlAddr = "";
                        urlAddr = "http://"+ STATICDATA2.CENTIP +":8080/jejukat/jejukat_search_place_all.jsp?";
                        urlAddr = urlAddr + "cat1=" + cat1 + "&search=" + search;
                        SelectPlaces();
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /*//////////////////////////////////
                   장소 검색
    *///////////////////////////////////

    private void SelectPlaces() {
        try{
            Log.v("Status", urlAddr);
            places = new ArrayList<Bean_Search2Pick>();
            NetworkTask_AddSchedule_Search2Pick addSchedule_searchPlace_networkTask = new NetworkTask_AddSchedule_Search2Pick(Activity_Search2Pick.this, urlAddr);
            places = (ArrayList<Bean_Search2Pick>) addSchedule_searchPlace_networkTask.execute().get();
//            adapter.notifyDataSetChanged();
            ConnectRecyclerView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*//////////////////////////////////
            RecyclerView 연결
    *///////////////////////////////////
    public void  ConnectRecyclerView(){

        linearLayoutManager = new LinearLayoutManager(Activity_Search2Pick.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_AddSchedule_Search2Pick(places, Activity_Search2Pick.this); // 어댑터 가져와서 어레이리스트에 담아줌
        recyclerView.setAdapter(adapter);
    }

    /*////////////////////////////////////////////////////////////////////
           장소검색 액티비티에서 seq_post들고 FragmentPlaces로 전달
    */////////////////////////////////////////////////////////////////////

    public void backToAddSchedule() {
        Intent intent = new Intent();
        intent.putExtra("seq_post", STATICDATA2.SEQ_POST );
        setResult(RESULT_OK, intent);
        finish();
    }

}//-------------END