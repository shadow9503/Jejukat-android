package com.androidlec.jejukat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_MyScheduleList extends RecyclerView.Adapter<Adapter_MyScheduleList.mViewHolder> {

    private Context context;
    private ArrayList<Bean_Search> mData; //
    ArrayList<Bean_Search> list; // 조회수

    public Adapter_MyScheduleList(ArrayList<Bean_Search> mData, Context context) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_myschedulelist, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, final int position) {
        String title = mData.get(position).getTitle();
        String uploader_picture = mData.get(position).getUploader_picture();
        String uploader_nickname = mData.get(position).getUploader_nickname();
        String imageUrl = mData.get(position).getThumnail_place();
        final String view = mData.get(position).getView();//
        String validation = mData.get(position).getValidation();

        holder.myschedule_image_wv.getSettings().setLoadWithOverviewMode(true);
        holder.myschedule_image_wv.getSettings().setUseWideViewPort(true);
        holder.myschedule_image_wv.loadUrl("http://" + imageUrl);
        holder.myschedule_title_tv.setText(title);

        //CIV url(String)로 가져오기
        Glide.with(holder.itemView.getContext())
                .load("http://" + uploader_picture)
                .placeholder(R.drawable.icon_user_null)
                .error(R.drawable.icon_user_null)
                .into(holder.myschedule_uploader_picture_civ);

        holder.myschedule_uploader_nickname_tv.setText(uploader_nickname);

        // 권한 없을떄 검토 중으로 변경///일정도 똑같이 되게 함
        if(validation.equals("0")){
            holder.myscheduleAdapter_show_view_tv.setText("검토 중");
            holder.myscheduleAdapter_show_view_tv.setTextColor(Color.RED); //빨간색
            holder.auth_validation_myschedule_iv.setVisibility(View.INVISIBLE);
            holder.invalidation_myschedule_iv.setVisibility(View.VISIBLE);
        }else{
            holder.myscheduleAdapter_show_view_tv.setText(String.valueOf(view));
            holder.auth_validation_myschedule_iv.setVisibility(View.VISIBLE);
            holder.invalidation_myschedule_iv.setVisibility(View.INVISIBLE);
        }

        holder.myschedule_image_wv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        //최근 조회한 게시물
        holder.layout_myschedule.setOnClickListener(new View.OnClickListener() {
            String seq_post = mData.get(position).getSeq_post();
            String select_title = mData.get(position).getTitle();
            String select_cat1 = mData.get(position).getCat1();
            String select_cat2 = mData.get(position).getCat2();
            String select_address = mData.get(position).getAddress();
            String select_image = mData.get(position).getThumnail_place();

            @Override
            public void onClick(View view) { // 클릭했을떄 조회수, 최근조회게시물 반영
                Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show();

                SelectPlacesViewsUpdate(seq_post); //조회수+1
                STATICDATA.SEQ_POST=Integer.parseInt(seq_post);
                Log.v("TAG", String.valueOf(seq_post));

                Intent intent = new Intent(view.getContext(), Activity_Detail_Schedule.class);
                view.getContext().startActivity(intent);

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

    public static class mViewHolder extends RecyclerView.ViewHolder {
        TextView myschedule_title_tv, myscheduleAdapter_show_view_tv, myschedule_uploader_nickname_tv;
        ImageView auth_validation_myschedule_iv, invalidation_myschedule_iv;
        WebView myschedule_image_wv;
        CardView cardView_myschedule;
        LinearLayout layout_myschedule;
        CircleImageView myschedule_uploader_picture_civ; //picture추가

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            myschedule_title_tv = itemView.findViewById(R.id.tv_myscheduleAdapter_show_title);
            myscheduleAdapter_show_view_tv = itemView.findViewById(R.id.tv_myscheduleAdapter_show_view);
            myschedule_uploader_nickname_tv = itemView.findViewById(R.id.tv_myscheduleAdapter_show_uploader_nickname);
            myschedule_image_wv = itemView.findViewById(R.id.wv_myscheduleAdapter_show_image);
            myscheduleAdapter_show_view_tv = itemView.findViewById(R.id.tv_myscheduleAdapter_show_view);
            auth_validation_myschedule_iv = itemView.findViewById(R.id.iv_auth_validation_myschedule);
            invalidation_myschedule_iv = itemView.findViewById(R.id.iv_invalidation_myschedule);
            cardView_myschedule = itemView.findViewById(R.id.cardView_myschedule);
            layout_myschedule = itemView.findViewById(R.id.Ll_myschedule_ListItem_card);
            myschedule_uploader_picture_civ = itemView.findViewById(R.id.civ_myscheduleAdapter_uploader_picture);
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

}//------------
