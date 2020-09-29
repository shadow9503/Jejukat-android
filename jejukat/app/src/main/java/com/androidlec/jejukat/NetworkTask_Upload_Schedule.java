package com.androidlec.jejukat;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class NetworkTask_Upload_Schedule extends AsyncTask<Integer, String, Void> {

    Context context;
    String mAddr;
    ArrayList<Bean_picklist> placeArray;
    String [] seq_post_arr;

    //Constructor 를 만들어놔야 new 로 해서 쓸 수 있음
    public NetworkTask_Upload_Schedule(Context context, String mAddr, ArrayList<Bean_picklist> placeArray) {
        this.context = context;
        this.mAddr = mAddr;
        this.placeArray = new ArrayList<Bean_picklist>(placeArray);

    }

    @Override
    protected void onPreExecute() { //실행전
        seq_post_arr = new String[placeArray.size()];
        for(int i=0; i<placeArray.size(); i++) {
            seq_post_arr[i] = placeArray.get(i).getPlaceSeq();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) { //끝났을떄
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Void doInBackground(Integer... integers) {
//        Log.v( "Test", "doIn(insert)" );
        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            JSONArray json_seq_arr = new JSONArray(Arrays.asList(seq_post_arr));
            String basicinfo = json_seq_arr.toString();

            //////////////////////
            //   전송 모드 설정
            //////////////////////

            httpURLConnection.setRequestMethod("POST"); // POST 방식
            httpURLConnection.setDoOutput(true); // 서버 쓰기 모드 지정
//            httpURLConnection.setDoInput(true); // 서버 읽기 모드 지정
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
            // 헤더 세팅
            // key-value tuple로 인코딩되는 기본 형식 영문은 percent encoded으로 인코딩됨
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
//            httpURLConnection.setRequestProperty("Accept", "x-www-form-urlencoded"); // 응답받는 형태

            // 파라미터 구성
            StringBuffer buffer = new StringBuffer();
            buffer.append("title").append("=").append(STATICDATA2.TITLE).append("&");
            buffer.append("context").append("=").append(STATICDATA2.CONTEXT).append("&");
            buffer.append("uploader").append("=").append(STATICDATA.SEQ_USER).append("&");
            buffer.append("images").append("=").append(STATICDATA2.IMAGES).append("&");
            buffer.append("basicinfo").append("=").append(basicinfo);

            // 서버로 값 전송
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(buffer.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                Log.v( "Test", "HTTP_OK" );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
