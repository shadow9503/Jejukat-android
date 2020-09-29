package com.androidlec.jejukat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class Activity_ViewedList extends AppCompatActivity {

    final static String TAG = "Activity_LatestList";

    ImageView back;
    SQLiteHelper_ViewedtList sqLiteHelper_viewedtList;
    ArrayList<Bean_Search> data_viewed = null;
    Adapter_ViewedList adapter_viewedList = null;
    RecyclerView rv_viewedList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewedlist);

        back = findViewById(R.id.img_viewed_back);
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
        //최근조회한게시물/////////////////
        data_viewed = new ArrayList<Bean_Search>();
        sqLiteHelper_viewedtList = new SQLiteHelper_ViewedtList(Activity_ViewedList.this); //MemberInfo 인스턴스 생성

        SQLiteDatabase DB;

        try {
            DB = sqLiteHelper_viewedtList.getReadableDatabase();

            String query = "SELECT * FROM viewedlist ORDER BY date desc;"; //최근 순 조회

            Cursor cursor = DB.rawQuery(query, null);

            while (cursor.moveToNext()) {
                String seq_viewed = cursor.getString(0);
                String seq_post = cursor.getString(1);
                String title = cursor.getString(2);
                String cat1 = cursor.getString(3);
                String cat2 = cursor.getString(4);
                String address = cursor.getString(5);
                String image = cursor.getString(6);
                String latestViewedDate = cursor.getString(7);

                Bean_Search dto = new Bean_Search(seq_viewed, seq_post, title, cat1, cat2, address, image, latestViewedDate);
                data_viewed.add(dto);

            }

            cursor.close();
            sqLiteHelper_viewedtList.close();
            Toast.makeText(Activity_ViewedList.this, "Select ViewedList OK", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Activity_ViewedList.this, "Select ViewedList Error", Toast.LENGTH_SHORT).show();
        }

        rv_viewedList = findViewById(R.id.rv_viewedList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_viewedList.setLayoutManager(layoutManager);
        adapter_viewedList = new Adapter_ViewedList(data_viewed, Activity_ViewedList.this);
        rv_viewedList.setAdapter(adapter_viewedList);
        super.onStart();
    }
}
