package com.androidlec.jejukat;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class NetworkTask_AddSchedule_PickPlaces extends AsyncTask<Integer,String,Object>{

    Context context;
    String mAddr;
    Bean_picklist place;
    String seq_post_arr;

    public NetworkTask_AddSchedule_PickPlaces(Context context, String mAddr, String seq_post_arr) {
        this.context = context;
        this.mAddr = mAddr;
//        this.places = new ArrayList<Schedule>();
        this.seq_post_arr = seq_post_arr;
    }

    public NetworkTask_AddSchedule_PickPlaces(FragmentActivity activity, String urlAddr) {
        this.context = activity;
        this.mAddr = urlAddr;
//        this.places = new ArrayList<Schedule>();
    }

    @Override
    protected void onPreExecute() {
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

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v( "Current", "DoIn()" );
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.v( "Current", "HTTP_OK()" );
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
        return place;
    }

    ///////////////////////////
    //      장소 정보 Parser
    ///////////////////////////

    protected void parser(String str) {
        Log.v( "Current", "Parser()" );
        try {
            JSONObject jsonObject = new JSONObject(str);
            Log.v( "Current", str );
            JSONArray jsonArray = new JSONArray(jsonObject.getString("place_info"));

                JSONObject placeObject = (JSONObject) jsonArray.get(0);
                String placeSeq = placeObject.getString("seq_post");
                String placeName = placeObject.getString("placeName");
                String placeCat1 = placeObject.getString("placeCat1");
                String placeCat2 = placeObject.getString("placeCat2");
                String placeAddress = placeObject.getString("placeAddress");
                place = new Bean_picklist(placeSeq, placeName, placeCat1, placeCat2, placeAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

