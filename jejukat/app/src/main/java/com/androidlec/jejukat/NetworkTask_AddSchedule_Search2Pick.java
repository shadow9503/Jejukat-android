package com.androidlec.jejukat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NetworkTask_AddSchedule_Search2Pick extends AsyncTask<Integer,String,Object>{
    Context context;
    String mAddr;
    CustomProgressDialog_AddSchedule progressDialog;
    ArrayList<Bean_Search2Pick> places;

    public NetworkTask_AddSchedule_Search2Pick(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.places = new ArrayList<Bean_Search2Pick>();
    }

    // ProgressDialog 설정 --------------------------------------------
    // 데이터를 받고있을때
    @Override
    protected void onPreExecute() {
        progressDialog = new CustomProgressDialog_AddSchedule( context );
        progressDialog.setCancelable( false );
        progressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
//        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.show();

        // 로딩 다이어로그 딜레이
        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            int count = 0;
            public void run() {
                progressDialog.dismiss();
            }
        }, 500);
    }

    // 데이터가 바뀌엇을때
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

    }

    // 데이터가 완료되었을때
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
    // ProgressDialog 설정끝 --------------------------------------------

    @Override
    protected Object doInBackground(Integer... integers) {
//        Log.v( "Current", "DoIn()" );
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                Log.v( "Current", "HTTP_OK()" );
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null)
                        break;
                    stringBuffer.append(strline + "\n");
                }
                parser(stringBuffer.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) { bufferedReader.close(); }
                if (inputStreamReader != null) { inputStreamReader.close(); }
                if (inputStream != null) { inputStream.close(); }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return places;
    }

    /*//////////////////////////////////
              장소정보 parser
    *///////////////////////////////////
    protected void parser(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("place_info"));
            places.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject placeObject = (JSONObject) jsonArray.get(i);
                String seq_post = placeObject.getString("seq_post");
                String title = placeObject.getString( "title" );
                String cat1 = placeObject.getString("cat1");
                String cat2 = placeObject.getString("cat2");
                String address = placeObject.getString("address");

                places.add(new Bean_Search2Pick(seq_post, title, cat1, cat2, address));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

