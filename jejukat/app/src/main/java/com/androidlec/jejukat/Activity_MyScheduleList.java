package com.androidlec.jejukat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class Activity_MyScheduleList extends AppCompatActivity {

    final static String TAG = "Activity_MyScheduleList";

    ImageView back;
    String urlAddr;
    ArrayList<Bean_Search> alMySchedules;  //// Bean_Search 에서 validation field + constructor 추가함
    Adapter_MyScheduleList adapter_myScheduleList = null;
    RecyclerView rv_myschedule = null;
    String centIP = STATICDATA.CENTIP; ////////변경해주세요 //////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myschedulelist);

        back = findViewById(R.id.img_myschedule_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        //ㄴ ㅐ 일정 networktask mySQL연결 //////////////////////////
        alMySchedules = new ArrayList<Bean_Search>();
        urlAddr = "http://119.207.169.213:8080/jeju/jejukat_myschedulelist.jsp?uploader=" + STATICDATA.SEQ_USER; //테스트

        Log.v("내일정 : ", " user" + STATICDATA.SEQ_USER);

        try {
            alMySchedules = new ArrayList<Bean_Search>();
            NetworkTask_Schedule_Select NetworkTask = new NetworkTask_Schedule_Select(Activity_MyScheduleList.this, urlAddr); //
            alMySchedules = (ArrayList<Bean_Search>) NetworkTask.execute().get();

            rv_myschedule = findViewById(R.id.rv_myschedule);// Recyclerview연결
            LinearLayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            rv_myschedule.setLayoutManager(layoutManager);
            adapter_myScheduleList = new Adapter_MyScheduleList(alMySchedules, Activity_MyScheduleList.this);
            rv_myschedule.setAdapter(adapter_myScheduleList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }
}
