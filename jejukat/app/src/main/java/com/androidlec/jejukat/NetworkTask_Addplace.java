package com.androidlec.jejukat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask_Addplace extends AsyncTask<Integer, String, Void> {

    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    JSONObject placeData;

    //Constructor 를 만들어놔야 new 로 해서 쓸 수 있음
    public NetworkTask_Addplace(Context context, String mAddr, JSONObject placeData) {
        this.context = context;
        this.mAddr = mAddr;
        this.placeData = placeData;

    }

    /////////////// Start ProgressDialog ///////////////

    @Override
    protected void onPreExecute() { //실행전
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Insert...");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) { //끝났을떄
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /////////////// End ProgressDialog /////////////////



    //////////////////////백그라운드//////////////////////
    @Override
    protected Void doInBackground(Integer... integers) {
//        Log.v( "Test", "doIn(insert)" );
        try {
            URL url = new URL(mAddr); //mAddr을가지고나감
//            URL url = new URL("http://192.168.219.100:8080/jejukat/jejukat_place_insert.jsp" ); //mAddr을가지고나감
            HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();

            String cat1= placeData.getString("cat1");
            String cat2= placeData.getString("cat2");
            String title = placeData.getString("title");
            String context = placeData.getString("context");
            String images = placeData.getString("images");
            String basicinfo = placeData.getString("basicinfo");

            httpURLConn.setRequestProperty("uploader", STATICDATA.SEQ_USER);
            httpURLConn.setRequestProperty("cat1", cat1);
            httpURLConn.setRequestProperty("cat2", cat2);
            httpURLConn.setRequestProperty("title", title);
            httpURLConn.setRequestProperty("context", context);
            httpURLConn.setRequestProperty("images", images);
            httpURLConn.setRequestProperty("basicinfo", basicinfo);

            httpURLConn.setRequestMethod("POST");
            httpURLConn.setRequestProperty("Cache-Control", "no-cache");
            httpURLConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            httpURLConn.setRequestProperty("Accept", "x-www-form-urlencoded");
            httpURLConn.setDoOutput(true);
            httpURLConn.setConnectTimeout(5000);
//            Log.v("Object", context);
//            Log.v("Object", images);
//            Log.v("Object", basicinfo);

            // 파라미터 구성
            StringBuffer buffer = new StringBuffer();
            buffer.append("uploader").append("=").append(STATICDATA.SEQ_USER).append("&");                 // php 변수에 값 대입
            buffer.append("title").append("=").append(title).append("&");                 // php 변수에 값 대입
            buffer.append("context").append("=").append(context).append("&");   // php 변수 앞에 '$' 붙이지 않는다
            buffer.append("cat1").append("=").append(cat1).append("&");   // php 변수 앞에 '$' 붙이지 않는다
            buffer.append("cat2").append("=").append(cat2).append("&");   // php 변수 앞에 '$' 붙이지 않는다
            buffer.append("images").append("=").append(images).append("&");           // 변수 구분은 '&' 사용
            buffer.append("basicinfo").append("=").append(basicinfo);

            Log.v("Object", buffer.toString());

            // 서버로 값 전송
//            OutputStreamWriter os = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
//            PrintWriter writer = new PrintWriter(os);
//            writer.write(buffer.toString());
//            writer.flush();
            OutputStream os = httpURLConn.getOutputStream();
            os.write(buffer.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            if (httpURLConn.getResponseCode() == HttpURLConnection.HTTP_OK){
                Log.v( "Test", "HTTP_OK" );
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
