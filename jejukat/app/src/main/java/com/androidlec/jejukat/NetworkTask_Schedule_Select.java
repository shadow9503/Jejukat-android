package com.androidlec.jejukat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NetworkTask_Schedule_Select extends AsyncTask<Integer,String,Object>{
    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    ArrayList<Bean_Search> places;

    public NetworkTask_Schedule_Select(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.places = new ArrayList<Bean_Search>();
        Log.v("url:", mAddr);
    }

    // ProgressDialog 설정 --------------------------------------------
    // 데이터를 받고있을때
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue()");
        progressDialog.setMessage("down.....");
        progressDialog.show();
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
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
    // ProgressDialog 설정끝 --------------------------------------------

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
            httpURLConnection.setConnectTimeout(5000);

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
        return places;
    }

    /////////////////////// 장소 정보 Parser ///////////////////////////////////////
    protected void parser(String str) {
        Log.v( "Current", "Parser()" );
        try {
            JSONObject jsonObject = new JSONObject(str);
            Log.v( "Current", str );
            JSONArray jsonArray = new JSONArray(jsonObject.getString("schedule_info"));
            places.clear();
            String thumnail_image = "";
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject placeObject = (JSONObject) jsonArray.get(i);
                String seq_post = placeObject.getString("seq_post");
                String uploader = placeObject.getString("uploader");
                String nickname = placeObject.getString("nickname");
                String picture = placeObject.getString("picture");
                String title = placeObject.getString( "title" );
                String cat1 = placeObject.getString("cat1");
                String cat2 = placeObject.getString("cat2");
                String view = placeObject.getString("view");
                String date = placeObject.getString("date");
                String validation = placeObject.getString("validation");

                //이미지
                JSONArray jsonArray_images = placeObject.getJSONArray( "images" );
                if(jsonArray_images.length() == 0) {
                    thumnail_image = "119.207.169.213:8080/ftp/jejukat_img_null.png";
                }else{
                    thumnail_image = jsonArray_images.getString( 0 );
                }

                //참고한 장소
                ArrayList<String> includedSeqPosts = new ArrayList<String>(); // 이미지 어레이 만들어서 넣음
                JSONArray jsonArray_includedSeqPost = placeObject.getJSONArray( "includedSeqPost" );
                String includedSeqPost="";
                if(includedSeqPosts.size() == 0) {
                    includedSeqPost= "추가한 장소가 없습니다.";
                } else {
                    for(int j=0; j<includedSeqPosts.size(); j++) {
                        includedSeqPost = jsonArray_includedSeqPost.getString( j );
                        includedSeqPosts.add( includedSeqPost );
                    }
                }
                places.add(new Bean_Search(seq_post, uploader, nickname, picture, title, cat1, cat2, view, date, thumnail_image, includedSeqPosts, validation));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

