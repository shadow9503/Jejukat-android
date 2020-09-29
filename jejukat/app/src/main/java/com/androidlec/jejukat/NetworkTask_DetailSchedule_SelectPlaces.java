package com.androidlec.jejukat;

import android.content.Context;
import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NetworkTask_DetailSchedule_SelectPlaces extends AsyncTask<Integer,String,Object>{

    Context context;
    String mAddr;
    String urlAddr;
    Bean_Detail_Schedule_PickPlacesList place;
    ArrayList<Bean_Detail_Schedule_PickPlacesList> places;
    ArrayList<String> seq_arr;


    public NetworkTask_DetailSchedule_SelectPlaces(FragmentActivity activity, String urlAddr, ArrayList<String> seq_arr) {
        this.context = activity;
        this.urlAddr = urlAddr;
        this.seq_arr = seq_arr;
        this.places = new ArrayList<>();
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
//        Log.v( "Current", "DoIn()" );
        for( int step=0; step<seq_arr.size(); step++) {

            StringBuffer stringBuffer = new StringBuffer();
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            mAddr = urlAddr;
            mAddr = mAddr + "seq_post=" + seq_arr.get( step );  // get방식 파라미터 설정
//            Log.v( "Current", mAddr );
//            Log.v( "Current", String.valueOf( seq_arr.size() ) );

            try {
                URL url = new URL(mAddr);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    Log.v( "Current", "HTTP_OK()" );
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

        }
        return places;
    }

    ///////////////////////////
    //      장소 정보 Parser
    ///////////////////////////

    protected void parser(String str) {
//        Log.v( "Current", "Parser()" );
        try {
            JSONObject jsonObject = new JSONObject(str);
//            Log.v( "Current", str );
            JSONArray jsonArray = new JSONArray(jsonObject.getString("place_info"));

                JSONObject placeObject = (JSONObject) jsonArray.get(0);
                String placeSeq = placeObject.getString("seq_post");
                String placeName = placeObject.getString("placeName");
                String placeCat1 = placeObject.getString("placeCat1");
                String placeCat2 = placeObject.getString("placeCat2");
                String placeAddress = placeObject.getString("placeAddress");

                places.add(new Bean_Detail_Schedule_PickPlacesList(placeSeq, placeName, placeCat1, placeCat2, placeAddress));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

