package com.androidlec.jejukat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Activity_NotificationList extends AppCompatActivity {

    final static String TAG = "Activity_Notification";

    ImageView back;
    String urlAddr;
    ArrayList<Bean_Notification> alNotification;  //// Bean_Search 에서 validation field + constructor 추가함
    Adapter_Notification adapter_notification = null;
    RecyclerView rv_notification = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        back = findViewById(R.id.img_notification_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();//
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        //공지사항
        alNotification = new ArrayList<Bean_Notification>();
        urlAddr = "http://119.207.169.213:8080/jeju/jejukat_notification_list.jsp"; //이걸로변경!
        Log.v(TAG, " select ");

        try {
            alNotification = new ArrayList<Bean_Notification>();
            NetworkTask_Notification NetworkTask = new NetworkTask_Notification(Activity_NotificationList.this, urlAddr); //
            alNotification = (ArrayList<Bean_Notification>) NetworkTask.execute().get();

            rv_notification = findViewById(R.id.rv_notification);// Recyclerview연결
            LinearLayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            rv_notification.setLayoutManager(layoutManager);
            adapter_notification = new Adapter_Notification(alNotification, Activity_NotificationList.this);
            rv_notification.setAdapter(adapter_notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }


}
