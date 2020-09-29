package com.androidlec.jejukat;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask_Upload extends AsyncTask<Integer,String,Object> {
    Context context;
    String mAddr;
    ArrayList<Bean_Search> places;

    public NetworkTask_Upload(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.places = new ArrayList<Bean_Search>();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v( "Current", "DoIn()" );

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.v( "Current", "HTTP_OK()" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }


}
