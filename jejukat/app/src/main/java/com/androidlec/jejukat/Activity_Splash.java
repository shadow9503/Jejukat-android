package com.androidlec.jejukat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Activity_Splash extends AppCompatActivity {

    private final int delay = 3000;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        String email = sharedPreferences.getString("email","");
        String method = sharedPreferences.getString("method","");

        String url = "http://119.207.169.213:8080//jejukat/jejukat_query_login.jsp?email=" + email + "&nickname=&method=" + method + "&picture=";
        Log.v("splash", url);

        if(email.equals("")==false&&method.equals("")==false) {
            STATICDATA.LOGIN_STATUS=1;
            NetworkTask_Select_Userdata networktask = new NetworkTask_Select_Userdata(Activity_Splash.this, url);
            networktask.execute();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Activity_Splash.this, Activity_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        },delay);

    }


}