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

public class Activity_MyPlaceList extends AppCompatActivity {

    final static String TAG = "Activity_MyPlaceList";

    ImageView back;
    String urlAddr;
    ArrayList<Bean_Search> alMyPlace;  //// Bean_Search 에서 validation field + constructor 추가함
    Adapter_MyPlaceList adapter_myPlaceList = null;
    RecyclerView rv_myplace = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplacelist);

        back = findViewById(R.id.img_myplace_back);
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
        //ㄴ ㅐ 장 소 networktask mySQL연결 //////////////////////////
        alMyPlace = new ArrayList<Bean_Search>();
        //urlAddr = "http://" + centIP + ":8080/jejukat/jejukat_myplacelist.jsp?uploader=" + STATICDATA.SEQ_USER;
        urlAddr = "http://119.207.169.213:8080/jeju/jejukat_myplacelist.jsp?uploader=" + STATICDATA.SEQ_USER; //테스트

        Log.v("내장소 : ", " user" + STATICDATA.SEQ_USER);

        try {
            alMyPlace = new ArrayList<Bean_Search>();
            NetworkTask_get_Result NetworkTask = new NetworkTask_get_Result(Activity_MyPlaceList.this, urlAddr); //
            alMyPlace = (ArrayList<Bean_Search>) NetworkTask.execute().get();

            rv_myplace = findViewById(R.id.rv_myplace);// Recyclerview연결
            LinearLayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            rv_myplace.setLayoutManager(layoutManager);
            adapter_myPlaceList = new Adapter_MyPlaceList(alMyPlace, Activity_MyPlaceList.this);
            rv_myplace.setAdapter(adapter_myPlaceList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }
}
