package com.androidlec.jejukat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Activity_Notification_Selected extends AppCompatActivity {

    //전역변수
    TextView notification_title_tv, notification_date_tv, notification_context_tv;
    //ScrollView scrollview;
    ImageView back, notification_picture_iv;


    private String centIP = STATICDATA.CENTIP; // 이걸로변경하면됨

    //DB notification 불러오기
    private String urlAddrNotification;
    private ArrayList<Bean_Notification> data = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_notification);

        ///연결
        notification_title_tv = findViewById(R.id.tv_selected_notification_title);
        notification_date_tv = findViewById(R.id.tv_selected_notification_date);
        notification_picture_iv = findViewById(R.id.iv_selected_notification_picture);
        notification_context_tv = findViewById(R.id.tv_selected_notification_context);

        back = findViewById(R.id.iv_notification_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }//---------END onCreate----------

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        //선택한 공지사항 DTO에넣음////////////////////////////////////////////
        //jsp읽고 DTO에넣고 텍스트필드 채우기
        urlAddrNotification = "http://" + centIP + ":8080/jeju/jejukat_selected_notification.jsp?";
        String sql_seqNoti = "seq_notification=" + STATICDATA.SEQ_NOTIFICATION; // seq_post로 받아옴
        Log.v("link",urlAddrNotification + sql_seqNoti);

        try{
            NetworkTask_Notification select_notification = new NetworkTask_Notification( Activity_Notification_Selected.this, urlAddrNotification + sql_seqNoti);
            Object obj = select_notification.execute( ).get( );
            data = (ArrayList<Bean_Notification>) obj;

            //가져온거 넣기!
            notification_title_tv.setText( data.get( 0 ).getTitle());
            notification_date_tv.setText( data.get( 0 ).getDate().substring(2,10));
            notification_context_tv.setText( data.get( 0 ).getContext());

            //이미지 url넣기
            Glide.with(this).load("http://" + data.get(0).getPicture()).into(notification_picture_iv);
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onStart();
    }

}