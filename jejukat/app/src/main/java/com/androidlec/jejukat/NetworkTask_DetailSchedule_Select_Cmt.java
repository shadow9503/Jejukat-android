package com.androidlec.jejukat;

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

public class NetworkTask_DetailSchedule_Select_Cmt extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;

    //내부적으로필요
    ArrayList<Bean_Detail_Place_Cmt> comments;

    public NetworkTask_DetailSchedule_Select_Cmt(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;

        this.comments = new ArrayList<Bean_Detail_Place_Cmt>();
    }


    /////////////// Start ProgressDialog ///////////////
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
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
//                Log.v( "Current", "HTTP_OK()" );
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
        return comments;  // 어레이리스트 넘겨주기 !!
    }



    //////////JSON에 맞게 가져올수있게 쓰기/////////////////
    protected void Parser(String str) {
//        Log.v( "Current", "Parser()" );
        try {
            Log.v( "Current", str );
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("scheduledetailscomments_info"));// JSONArray 로 불러오고
            comments.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
                JSONObject comments_Object = (JSONObject) jsonArray.get(i);
                int seq_cmt = comments_Object.getInt("seq_cmt");
                int seq_post = comments_Object.getInt("seq_post");
                String seq_user = comments_Object.getString("seq_user");
                String nickname = comments_Object.getString("nickname");
                String picture = comments_Object.getString( "picture" );
                String comment = comments_Object.getString("comment");
                String date = comments_Object.getString("date");
                int validation = comments_Object.getInt( "validation" );

                comments.add(new Bean_Detail_Place_Cmt(seq_cmt, seq_post, seq_user, nickname,  picture,  comment,  date, validation));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
