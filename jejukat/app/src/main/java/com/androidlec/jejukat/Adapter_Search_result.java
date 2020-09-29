package com.androidlec.jejukat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Search_result extends RecyclerView.Adapter<Adapter_Search_result.mViewHolder> {

    private Context context;
    private ArrayList<Bean_Search> mData;
    ArrayList<Bean_Search> list;



    public Adapter_Search_result(ArrayList<Bean_Search> mData, Context context) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_search_results, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, final int position) {
        String title = mData.get(position).getTitle();
        String placeView = mData.get(position).getView();
        String address = mData.get(position).getAddress();
        String imageUrl = mData.get(position).getThumnail_place();
        final String view = mData.get(position).getView();//

        holder.show_image_wv.getSettings().setLoadWithOverviewMode(true);
        holder.show_image_wv.getSettings().setUseWideViewPort(true);
        holder.show_image_wv.loadUrl("http://" + imageUrl);
        holder.show_title_tv.setText(title);
        holder.show_view_tv.setText(String.valueOf(placeView));
        holder.tv_placeAdapter_show_view.setText(String.valueOf(view));
        if(mData.get(position).getCat1().equals("일정")){
            holder.show_district_tv.setVisibility(View.GONE);
            holder.img_holder.setVisibility(View.GONE);
        }else{
            holder.show_district_tv.setText(address);
        }

        holder.show_image_wv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
//////여기서부터 통째로 집어넣으면 조회수 + 최근 게시물 등록 SQLite 됨.//////////////////////////////////////////////
        //여기에 최근 조회한 게시물 // 메소드로 안바꿈
        holder.layout.setOnClickListener(new View.OnClickListener() {
            String seq_post = mData.get(position).getSeq_post();
            String select_title = mData.get(position).getTitle();
            String select_cat1 = mData.get(position).getCat1();
            String select_cat2 = mData.get(position).getCat2();
            String select_image = mData.get(position).getThumnail_place();


            @Override
            public void onClick(View view) {
                Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show();

                SelectPlacesViewsUpdate(seq_post); //조회수+1
                Log.v("TAG", String.valueOf(seq_post));
                Intent places = new Intent(view.getContext(),Activity_Detail_Place.class);
                Intent schedule = new Intent(view.getContext(),Activity_Detail_Schedule.class);

                if(STATICDATA.CAT1.equals("일정")){
                    STATICDATA.SEQ_POST=Integer.parseInt(seq_post);
                    view.getContext().startActivity(schedule);
                }else{
                    STATICDATA.SEQ_POST=Integer.parseInt(seq_post);
                    view.getContext().startActivity(places);
                }

                //SQLite 최근 조회한 게시물
                SQLiteDatabase DB;
                SQLiteHelper_ViewedtList sqLiteHelper_viewedtList = new SQLiteHelper_ViewedtList(context);

                int seletedlatest_seq_post=0;
                //최근조회했는지 확인 하고 없으면(count = 0) insert 실행, 조회한 적있으면(count != 0) update datetime
                try {
                    DB = sqLiteHelper_viewedtList.getReadableDatabase(); // DB 정의
                    String latestSelectQuery = "SELECT * FROM viewedlist where seq_post = "+seq_post+";";

                    Cursor cursor = DB.rawQuery(latestSelectQuery, null);
                    seletedlatest_seq_post = cursor.getCount(); //myliklist에 북마크 들어가있는지 카운드로 확인
                    Log.v("최근게시물 : " , String.valueOf(seletedlatest_seq_post));

                    sqLiteHelper_viewedtList.close();// DB 닫기
                    Log.v("최근게시물 :", "SQLite 조회 완료");

                }catch (Exception e) {
                    e.printStackTrace();
                    Log.v("최근게시물 :", "조회 오류 ");
                }

                if(seletedlatest_seq_post == 0){ // 조회안했으면 등록
                    //LatestPostInsert(seq_post);
                    try {
                        DB = sqLiteHelper_viewedtList.getWritableDatabase(); // DB 정의
                        String latestInsertQuery = "INSERT INTO viewedlist(seq_post, title, cat1, cat2, image, date) "
                                + "VALUES ('" + seq_post + "', + '" + select_title + "', + '" + select_cat1 + "', + '" + select_cat2 + "', + '" + select_image + "', datetime());";
                        DB.execSQL(latestInsertQuery);
                        sqLiteHelper_viewedtList.close();// DB 닫기
                        Log.v("최근게시물 :", "SQLite 등록 완료");

                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.v("최근게시물 :", "등록 오류");
                    }


                } else { // count 있으면 insertdate 수정

                    try {
                        DB = sqLiteHelper_viewedtList.getWritableDatabase(); // DB 정의
                        String latestUpdateQuery = "UPDATE viewedlist SET date = datetime() WHERE seq_post = "+seq_post+";";
                        DB.execSQL(latestUpdateQuery);
                        sqLiteHelper_viewedtList.close();// DB 닫기
                        Log.v("최근게시물 :", "최근시간 수정 완료");

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("최근게시물 :", "최근시간 수정 오류");
                    }


                }
            }
        });
////////END 조회수 + 최근게시물 ////////////////////////////////////////////////////////////////////

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        TextView show_title_tv, show_view_tv, show_district_tv, tv_placeAdapter_show_view;
        WebView show_image_wv;
        CardView cardView;
        LinearLayout layout;
        ImageView img_holder;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            show_title_tv = itemView.findViewById(R.id.tv_placeAdapter_show_title);
            show_view_tv = itemView.findViewById(R.id.tv_placeAdapter_show_view);
            show_district_tv = itemView.findViewById(R.id.tv_placeAdapter_show_district);
            show_image_wv = itemView.findViewById(R.id.wv_placeAdapter_show_image);
            tv_placeAdapter_show_view = itemView.findViewById(R.id.tv_placeAdapter_show_view);//
            cardView = itemView.findViewById(R.id.cardView);
            layout = itemView.findViewById(R.id.Ll_places_ListItem_card);
            img_holder = itemView.findViewById(R.id.img_search_holder);
        }
    }

    //조회수올리기
    private void SelectPlacesViewsUpdate(String seq_post) {
        String urladdr = "http://119.207.169.213:8080/jejukat/jejukat_query_view.jsp?seq_post=" + seq_post;
        try{
            list = new ArrayList<Bean_Search>();
            NetworkTask_Upload networkTask = new NetworkTask_Upload(context,urladdr);
            list = (ArrayList<Bean_Search>) networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
