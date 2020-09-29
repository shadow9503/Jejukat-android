package com.androidlec.jejukat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Place_Cmt extends RecyclerView.Adapter<Adapter_Place_Cmt.CustomViewHolder> {

    //Field
    private ArrayList<Bean_Comment> comments;

//    //댓글삭제
//    private String urlAddrDeleteComment;
//    static String centIP = STATIC_DATA.CENTIP;
//    static int seq_post = STATIC_DATA.SEQ_POST;
//    static int seq_user = STATIC_DATA.SEQ_USER;

    int seq_cmt;

    //Constructor
    public Adapter_Place_Cmt(ArrayList<Bean_Comment> arrayList) {
        this.comments = arrayList;
    }


    @NonNull
    @Override  //리스트뷰 처음 생성될 떄 생명주기 뜻함
    public Adapter_Place_Cmt.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_detailpost_cmt, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override  //실제 리스트뷰 추가될 때
    public void onBindViewHolder(@NonNull final Adapter_Place_Cmt.CustomViewHolder holder, int position) {

        //이미지뷰 url(String)로 가져오기
        Glide.with(holder.itemView.getContext())
                .load("http://" + comments.get(position).getPicture())
                .placeholder(R.drawable.icon_user_null)
                .error(R.drawable.icon_user_null)
                .into(holder.civ_comment_profile);


        holder.tv_comment_nickname.setText(comments.get(position).getNickname());
        holder.tv_comment_insertdate.setText(comments.get(position).getDate());
        holder.tv_comment_contents.setText(comments.get(position).getComment());

        //댓글작성자만 삭제 가능
        if(comments.get(position).getSeq_user().equals(STATICDATA.SEQ_USER)){
            holder.tv_comment_delete.setVisibility(View.VISIBLE);
        }else{
            holder.tv_comment_delete.setVisibility(View.GONE);
        }
        //삭제버튼 클릭 후 seq_cmt 가져옴
        holder.tv_comment_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSeqCmt(holder.getAdapterPosition()); // 클릭한 댓글 seq_cmt가져오기
                Activity_Detail_Place_Frag_Cmt detailsPost_fragmentComment = new Activity_Detail_Place_Frag_Cmt();
                detailsPost_fragmentComment.DeleteComment();//DB에서 validation = 1(삭제)로 변경
            }

            //seq_cmt
            private void getSeqCmt(int position) {
                try {
                    seq_cmt = comments.get(position).getSeq_cmt();
                    STATICDATA.SEQ_CMT = seq_cmt;
                    Log.v("Current", "코멘트번호는?" + seq_cmt);
                    comments.remove(position); // 어레이리스트에서 삭제
                    notifyItemRemoved(position); //새로고침

                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != comments ? comments.size() : 0);
    }


    //사용할것 선언
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView civ_comment_profile;
        protected TextView tv_comment_nickname;
        protected TextView tv_comment_insertdate;
        protected TextView tv_comment_contents;
        protected TextView tv_comment_delete;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.civ_comment_profile = itemView.findViewById(R.id.civ_detailspost_fragcomment_profile);
            this.tv_comment_nickname = itemView.findViewById(R.id.tv_detailspost_fragcomment_nickname);
            this.tv_comment_insertdate = itemView.findViewById(R.id.tv_detailspost_fragcomment_insertdate);
            this.tv_comment_contents = itemView.findViewById(R.id.tv_detailspost_fragcomment_contents);
            this.tv_comment_delete = itemView.findViewById(R.id.tv_detailspost_fragcomment_delete_comment);

        }
    }





}///---------
