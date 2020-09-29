package com.androidlec.jejukat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Activity_Detail_Place_Frag_Cmt extends Fragment {

    private View view;

    private ArrayList<Bean_Comment> comments;  //BEAN
    private Adapter_Place_Cmt comment_adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    static String centIP = STATICDATA.CENTIP;
    static int seq_post = STATICDATA.SEQ_POST;
    static String seq_user = STATICDATA.SEQ_USER;

    //댓글리스트
    private String urlAddrSelectComments;

    //댓글등록
    private String urlAddrAddcomment;
    EditText comment_fragcomment_detailsPost_et;
    Button btn_add_comment;

    //댓글삭제
    private String urlAddrDeleteComment;


    //상태저장해야함
    public static Activity_Detail_Place_Frag_Cmt newInstance() {
        // 프레그먼트 교체할때 필요함
        Activity_Detail_Place_Frag_Cmt fragComment = new Activity_Detail_Place_Frag_Cmt();
        return fragComment; // 어댑터랑 연결위해
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // xml연동
        view = inflater.inflate(R.layout.tablayout_fragment_comment_detailspost, container, false);

        //댓글 등록연결
        comment_fragcomment_detailsPost_et = view.findViewById(R.id.et_detailspost_fragcomment_input_comment);
        btn_add_comment = view.findViewById(R.id.btn_detailspost_fragcomment_insert_comment);

        if (seq_user == null){
            comment_fragcomment_detailsPost_et.setHint("로그인이 필요한 서비스 입니다.");
            comment_fragcomment_detailsPost_et.setFocusableInTouchMode(false);
            btn_add_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "로그인이 필요한 서비스 입니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btn_add_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InsertComment(); // 댓글등록

                    //리스트 다시불러오기
                    ConnectGetDetailsPostComments();
                    ConnectRecyclerView();
                }
            });
        }

        return view;
    }


    //Method
    //댓글목록 가져오기
    public void ConnectGetDetailsPostComments(){
        urlAddrSelectComments = "http://" + centIP + ":8080/jeju/DetailsPost_comments_select.jsp?";
        urlAddrSelectComments = urlAddrSelectComments + "seq_post=" + STATICDATA.SEQ_POST; // seq_post로 받아옴

        try{
            Log.v("url comments; ",urlAddrAddcomment);
            NetworkTask_Select_Comment commentNetworkTask
                    = new NetworkTask_Select_Comment( getActivity(), urlAddrSelectComments );
            Object obj = commentNetworkTask.execute( ).get( );
            comments = (ArrayList<Bean_Comment>) obj;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //댓글등록
    private void InsertComment(){
        int seq_post_comment = STATICDATA.SEQ_POST;
        String seq_user_comment = seq_user;
        String comment = comment_fragcomment_detailsPost_et.getText().toString().trim(); // 입력한 댓글내용 가져오기
        int validation = 0; // default=0 등록 / 1=삭제

        //댓글 null값 등록 방지
        if (comment.length()<5) {
            Toast.makeText(getActivity(), "5자 이상 작성해주세요.", Toast.LENGTH_SHORT).show();
        } else if (comment.length()>=200) {
            Toast.makeText(getActivity(), "200자 미만 작성해주세요.", Toast.LENGTH_SHORT).show();
        }else {
            //클릭할 떄 url지우로 재 구성하는걸로 가져옴
            urlAddrAddcomment="";
            urlAddrAddcomment = "http://" + centIP + ":8080/jeju/DetailsPost_comment_insert.jsp?"; //get방식으로 넘겨줌
            urlAddrAddcomment = urlAddrAddcomment + "&seq_post=" + seq_post_comment + "&seq_user="
                    + seq_user_comment + "&comment=" + comment + "&validation=" + validation;

            try {
                NetworkTask_Insert_Comment_Place detailsPost_networkTask_insert_comment
                        = new NetworkTask_Insert_Comment_Place(getActivity(), urlAddrAddcomment);
                detailsPost_networkTask_insert_comment.execute().get();

                Toast.makeText(getActivity(), urlAddrAddcomment + "입력되었습니다.", Toast.LENGTH_SHORT).show();
                comment_fragcomment_detailsPost_et.setText(""); // 댓글입력창 리셋
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //댓글삭제
    public void DeleteComment(){
        //클릭한 seq_cmt가져옴
        int seq_cmt = STATICDATA.SEQ_CMT;

        //클릭할 떄 url 지우고 재 구성하는걸로 가져옴
            urlAddrDeleteComment="";
            urlAddrDeleteComment = "http://" + centIP + ":8080/jeju/DetailsPost_comment_delete.jsp?";
            urlAddrDeleteComment = urlAddrDeleteComment + "&seq_cmt=" + seq_cmt ;

            try {
                NetworkTask_Upload detailsPost_networkTask_delete_comment
                        = new NetworkTask_Upload(getActivity(), urlAddrDeleteComment);
                detailsPost_networkTask_delete_comment.execute().get();
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    //리사이클러뷰 연결
    public void  ConnectRecyclerView(){
        recyclerView = view.findViewById(R.id.rv_detailspost_fragcomment_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        comment_adapter = new Adapter_Place_Cmt(comments); // 어댑터 가져와서 어레이리스트에 담아줌
        recyclerView.setAdapter(comment_adapter);
    }

    @Override
    public void onStart() {

        //댓글목록 불러와서
        ConnectGetDetailsPostComments(); //BEAN에 넣고 보여줌

        //리사이클러뷰
        ConnectRecyclerView();
        super.onStart();
    }
}//------------