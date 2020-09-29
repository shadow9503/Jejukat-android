package com.androidlec.jejukat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class Activity_LikeList extends AppCompatActivity {

    final static String TAG = "Activity_BookmarkList";

    ImageView back;
    SQLiteHelper_MyLikeList sqLiteHelper_myLikeList;
    ArrayList<Bean_Like> data_like = null;
    Adapter_LikeList adapter_likeList = null;
    RecyclerView rv_likeList = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likelist);
        /////////////////////////////////////////

        back = findViewById(R.id.img_mylike_back);
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
        //찜한게시//////////////////////////
        data_like = new ArrayList<Bean_Like>();
        sqLiteHelper_myLikeList = new SQLiteHelper_MyLikeList(Activity_LikeList.this); // 인스턴스 생성

        Log.v("찜하기 : ", " user" + STATICDATA.SEQ_USER);

        SQLiteDatabase DB;

        try {
            DB = sqLiteHelper_myLikeList.getReadableDatabase();

            String query = "SELECT * FROM mylikedlist WHERE seq_user = " + STATICDATA.SEQ_USER + " ORDER BY seq_like desc;"; //최근 등록순 조회

            Cursor cursor = DB.rawQuery(query, null);

            while (cursor.moveToNext()) {
                String seq_like = cursor.getString(0);
                String seq_user = cursor.getString(1);
                String seq_post = cursor.getString(2);
                String title = cursor.getString(3);
                String cat1 = cursor.getString(4);
                String cat2 = cursor.getString(5);
                String address = cursor.getString(6);
                String image = cursor.getString(7);

                Bean_Like dto = new Bean_Like(seq_like, seq_user, seq_post, title, cat1, cat2, address, image); ////
                data_like.add(dto);

            }

            cursor.close();
            sqLiteHelper_myLikeList.close();
            Toast.makeText(Activity_LikeList.this, "Select LikeList OK", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Activity_LikeList.this, "Select LikeList Error", Toast.LENGTH_SHORT).show();
        }

        // Recyclerview연결
        rv_likeList = findViewById(R.id.rv_likeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_likeList.setLayoutManager(layoutManager);
        adapter_likeList = new Adapter_LikeList(data_like, Activity_LikeList.this);
        rv_likeList.setAdapter(adapter_likeList);
        super.onStart();
    }
}
