package com.androidlec.jejukat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask_DetailSchedule_Select extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;

    //내부적으로필요
    ProgressDialog progressDialog;
    ArrayList<Bean_Detail_Schedule_Select> detailsPosts;

    public NetworkTask_DetailSchedule_Select(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;

        this.detailsPosts = new ArrayList<Bean_Detail_Schedule_Select>();
    }

    public NetworkTask_DetailSchedule_Select(Context context) {
        this.context = context;
    }

    /////////////// Start ProgressDialog ///////////////
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get........");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /////////////// End ProgressDialog /////////////////

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;


        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);

//            Log.v("TAG", mAddr);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                Log.v("Current", "HTTP_OK()");
                inputStream = httpURLConnection.getInputStream(); // 한줄씩 가져오는걸
                inputStreamReader = new InputStreamReader(inputStream); // inputStreamReader 가 모아서 포장하고
                bufferedReader = new BufferedReader(inputStreamReader); // 버퍼드리더가 메모에 넣음

                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                //끝나면 JSON분해하기 파싱 메소드
                Parser(stringBuffer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return detailsPosts;  // 어레이리스트 넘겨주기 !!
    }


    //////////JSON에 맞게 가져올수있게 쓰기/////////////////
    protected void Parser(String str) {
//        Log.v("Current", "Parser()");

        try {
//            Log.v("Current", str);
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("scheduledetailsPost_info"));// JSONArray 로 불러오고


            detailsPosts.clear();


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject scheduledetailsPost_Object = (JSONObject) jsonArray.get(i);
                int seq_post = scheduledetailsPost_Object.getInt("seq_post");
                String nickname = scheduledetailsPost_Object.getString("uploader");
                String title = scheduledetailsPost_Object.getString("title");
                String cat1 = scheduledetailsPost_Object.getString("cat1");
                String cat2 = scheduledetailsPost_Object.getString("cat2");
                String context = scheduledetailsPost_Object.getString("context");
                String view = scheduledetailsPost_Object.getString("view");
                String date = scheduledetailsPost_Object.getString("date");





                //이미지어레이
                ArrayList<String> images = new ArrayList<String>(); // 이미지 어레이 만들어서 넣음
                JSONArray jsonArray_images = scheduledetailsPost_Object.getJSONArray("images");
                for (int j = 0; j < jsonArray_images.length(); j++) {
                    String image = jsonArray_images.getString(j);
//                    Log.v( "JSON", "imageName: " + image );
                    images.add(image);
                }

                //basicinfo 어레이
                ArrayList<String> basicinfos = new ArrayList<String>(); // 이미지 어레이 만들어서 넣음
                JSONArray jsonArray_basicinfo = scheduledetailsPost_Object.getJSONArray("basicinfo");
                for (int k = 0; k < jsonArray_basicinfo.length(); k++) {
                    String basicinfo = jsonArray_basicinfo.getString(k);
//                    Log.v( "JSON", "seq: " + basicinfo );
                    basicinfos.add(basicinfo);
                }

                detailsPosts.add(new Bean_Detail_Schedule_Select(seq_post, nickname, title, cat1, cat2, context, images, basicinfos, view, date));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}