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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_LikeList extends RecyclerView.Adapter<Adapter_LikeList.mViewHolder> {

    private Context context;
    private ArrayList<Bean_Like> mData;
    ArrayList<Bean_Search> list; //조회수


    public Adapter_LikeList(ArrayList<Bean_Like> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }


    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_like_list, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, final int position) {
        String title = mData.get(position).getTitle();
        String cat1 = mData.get(position).getCat1();
        String cat2 = mData.get(position).getCat2();
        String address = mData.get(position).getAddress();
        String imageUrl = mData.get(position).getImage();

        holder.like_image_wv.getSettings().setLoadWithOverviewMode(true);
        holder.like_image_wv.getSettings().setUseWideViewPort(true);
        holder.like_image_wv.loadUrl("http://" + imageUrl);
        holder.like_title_tv.setText(title);
        holder.like_cat1_tv.setText(cat1);
        holder.like_cat2_tv.setText(cat2);
        holder.like_district_tv.setText(address);

        holder.like_image_wv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        //레이아웃 클릭시 조회수 + 최근조회게시물 넣기
        holder.like_layout.setOnClickListener(new View.OnClickListener() {
            String seq_post = mData.get(position).getSeq_post();
            String select_title = mData.get(position).getTitle();
            String select_cat1 = mData.get(position).getCat1();
            String select_cat2 = mData.get(position).getCat2();
            String select_address = mData.get(position).getAddress();
            String select_image = mData.get(position).getImage();


            @Override
            public void onClick(View view) {
                Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show();

                SelectPlacesViewsUpdate(seq_post); //조회수+1

                STATICDATA.SEQ_POST=Integer.parseInt(seq_post);
                Log.v("TAG", String.valueOf(seq_post));
                Intent place = new Intent(view.getContext(), Activity_Detail_Place.class);
                Intent schedule = new Intent(view.getContext(), Activity_Detail_Schedule.class);

                if (select_cat1.equals("일정")) {
                    view.getContext().startActivity(schedule);
                }else{
                    view.getContext().startActivity(place);
                }

                //SQLite 최근 조회한 게시물//////////////////////////////////주소 다시 넣음!!/////
                SQLiteDatabase DB;
                SQLiteHelper_ViewedtList sqLiteHelper_viewedtList = new SQLiteHelper_ViewedtList(context);

                int checkViewedPost_seq_post=0;
                //최근조회했는지 확인 하고 없으면(count = 0) insert 실행, 조회한 적있으면(count != 0) update datetime
                try {
                    DB = sqLiteHelper_viewedtList.getReadableDatabase(); // DB 정의
                    String viewedSelectQuery = "SELECT * FROM viewedlist where seq_post = "+seq_post+";";

                    Cursor cursor = DB.rawQuery(viewedSelectQuery, null);
                    checkViewedPost_seq_post = cursor.getCount(); //myliklist에 북마크 들어가있는지 카운드로 확인
                    Log.v("최근게시물 조회여부: " , String.valueOf(checkViewedPost_seq_post));

                    sqLiteHelper_viewedtList.close();// DB 닫기
                    Log.v("최근게시물 :", "SQLite 조회 완료");

                }catch (Exception e) {
                    e.printStackTrace();
                    Log.v("최근게시물 :", "조회 오류 ");
                }

                if(checkViewedPost_seq_post == 0){ // 조회안했으면 등록
                    //LatestPostInsert(seq_post);
                    try {
                        DB = sqLiteHelper_viewedtList.getWritableDatabase(); // DB 정의
                        String viewedInsertQuery = "INSERT INTO viewedlist(seq_post, title, cat1, cat2, address, image, date) "
                                + "VALUES ('" + seq_post + "', + '" + select_title + "', + '" + select_cat1 + "', + '" + select_cat2 + "', + '" + select_address + "', + '" + select_image + "', datetime());";
                        DB.execSQL(viewedInsertQuery);
                        sqLiteHelper_viewedtList.close();// DB 닫기
                        Log.v("최근게시물 :", "SQLite 등록 완료");

                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.v("최근게시물 :", "등록 오류");
                    }


                } else { // count 있으면 insertdate 수정

                    try {
                        DB = sqLiteHelper_viewedtList.getWritableDatabase(); // DB 정의
                        String viewedUpdateQuery = "UPDATE viewedlist SET date = datetime() WHERE seq_post = "+seq_post+";";
                        DB.execSQL(viewedUpdateQuery);
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

        TextView like_title_tv, like_cat1_tv, like_cat2_tv, like_district_tv;
        WebView like_image_wv;
        CardView like_cardView;
        LinearLayout like_layout;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            like_title_tv = itemView.findViewById(R.id.tv_likeAdapter_show_title);
            like_cat1_tv = itemView.findViewById(R.id.tv_likeAdapter_show_cat1);
            like_cat2_tv = itemView.findViewById(R.id.tv_likeAdapter_show_cat2);
            like_title_tv = itemView.findViewById(R.id.tv_likeAdapter_show_title);
            like_district_tv = itemView.findViewById(R.id.tv_likeAdapter_show_district);
            like_image_wv = itemView.findViewById(R.id.wv_likeAdapter_show_image);
            like_cardView = itemView.findViewById(R.id.cardView_like);
            like_layout = itemView.findViewById(R.id.Ll_like_ListItem_card);
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
