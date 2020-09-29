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


public class NetworkTask_Notification extends AsyncTask<Integer,String,Object>{
    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    ArrayList<Bean_Notification> notifications;

    public NetworkTask_Notification(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.notifications = new ArrayList<Bean_Notification>();
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
        return notifications;
    }

    /////////////////////// Notification Parser /////////////////////////////////////// jsp만들어야함//////////////
    protected void parser(String str) {
        Log.v( "Current", "Notification Parser()" );
        try {
            Log.v( "Current", "try()" );
            JSONObject jsonObject = new JSONObject(str);

            Log.v( "Current", str );
            JSONArray jsonArray = new JSONArray(jsonObject.getString("notification_info"));
            Log.v( "Current", "arr" );
            notifications.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {

                JSONObject notiObject = (JSONObject) jsonArray.get(i);
                int seq_notification = notiObject.getInt("seq_notification");
                String title = notiObject.getString( "title" );
                String context = notiObject.getString("context");
                String picture = notiObject.getString("picture");
                String date = notiObject.getString("date");
                notifications.add(new Bean_Notification(seq_notification, title, context, picture, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

