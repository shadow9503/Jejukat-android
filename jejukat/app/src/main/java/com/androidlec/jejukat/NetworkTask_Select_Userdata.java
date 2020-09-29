package com.androidlec.jejukat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

public class NetworkTask_Select_Userdata extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;

    //내부적으로필요
    ProgressDialog progressDialog;


    public NetworkTask_Select_Userdata(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
    }


    /////////////// Start ProgressDialog ///////////////
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog( context );
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
        super.onProgressUpdate( values );
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

        try{
            URL url = new URL( mAddr );
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout( 5000 );

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.v( "Current", "HTTP_OK()" );
                inputStream = httpURLConnection.getInputStream(); // 한줄씩 가져오는걸
                inputStreamReader = new InputStreamReader( inputStream ); // inputStreamReader 가 모아서 포장하고
                bufferedReader = new BufferedReader( inputStreamReader ); // 버퍼드리더가 메모에 넣음

                while(true) {
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append( strline + "\n" );
                }

                //끝나면 JSON분해하기 파싱 메소드
                Parser(stringBuffer.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;  // 어레이리스트 넘겨주기 !!
    }


    //////////JSON에 맞게 가져올수있게 쓰기/////////////////
    protected void Parser(String str) {
        Log.v( "Current", str );
        try {
            Log.v( "Current", "try()" );
            JSONObject jsonObject = new JSONObject(str);

            STATICDATA.SEQ_USER = jsonObject.getString("seq_user");
            STATICDATA.NICKNAME = jsonObject.getString("nickname");
            STATICDATA.EMAIL = jsonObject.getString( "email" );
            STATICDATA.PROFILEPIC = jsonObject.getString("picture");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
