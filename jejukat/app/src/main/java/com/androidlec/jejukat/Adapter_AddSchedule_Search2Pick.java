package com.androidlec.jejukat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_AddSchedule_Search2Pick extends RecyclerView.Adapter<Adapter_AddSchedule_Search2Pick.ViewHolder> {

    private Context context;
    private ArrayList<Bean_Search2Pick> places = null;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView show_title_tv, show_cat_tv, show_address_tv;
//        WebView show_image_wv;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super( itemView );

            // 뷰 객체에 대한 참조. (hold strong reference)
            show_title_tv = itemView.findViewById( R.id.tv_addschedule_searchplace_show_title );
            show_cat_tv = itemView.findViewById( R.id.tv_addschedule_searchplace_show_cat );
            show_address_tv = itemView.findViewById( R.id.tv_addschedule_searchplace_show_address );
//            show_image_wv = itemView.findViewById( R.id.wv_addschedule_searchplace_show_image );
            linearLayout = itemView.findViewById( R.id.ll_addschedule_searchplace_item );


            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // TODO : use position

                        STATICDATA2.SEQ_POST = places.get( position ).getPlaceSeq();
                        Activity_Search2Pick searchPlaceActivity = (Activity_Search2Pick) context;
                        searchPlaceActivity.backToAddSchedule();
                    }
                }
            } );

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    Adapter_AddSchedule_Search2Pick(ArrayList<Bean_Search2Pick> list, Context context) {
        this.places = list;
        this.context = context;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public Adapter_AddSchedule_Search2Pick.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate( R.layout.activity_searchplace_listitem, parent, false );
        Adapter_AddSchedule_Search2Pick.ViewHolder vh = new Adapter_AddSchedule_Search2Pick.ViewHolder( view );
        //선언한 contextdp parent.getContext 값을 넣어준다 (호출한 Context)
        context = parent.getContext();

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = places.get( position ).getPlaceName();
        String cat = places.get( position ).getPlaceCat1() + " > " +places.get( position ).getPlaceCat2();
        String address = places.get( position ).getPlaceAddress();
//        String imageUrl = places.get( position ).getThumnail_place();

        // ----- 현재 버벅임과 에러때문에 주석처리

        // 원래 webview 이미지 꽉 채우는 방식 컨테이너 사이즈에따라 적용이 잘안됨.
//        holder.show_image_wv.getSettings().setLoadWithOverviewMode(true);
//        holder.show_image_wv.getSettings().setUseWideViewPort(true);
//        holder.show_image_wv.loadUrl( "http://" + imageUrl );

        // webview 컨테이너에 이미지가 꽉 참
//        holder.show_image_wv.setScrollBarDefaultDelayBeforeFade( 1 );
//        holder.show_image_wv.loadDataWithBaseURL(null,creHtmlBody("http://" + imageUrl), "text/html", "utf-8", null);

        holder.show_title_tv.setText( title );
        holder.show_cat_tv.setText( cat );
        holder.show_address_tv.setText( address );
    }


    /*////////////////////////////////////
        webview 이미지 꽉 채워주는 메소드
    */////////////////////////////////////

    public String creHtmlBody(String imagUrl){

        StringBuffer sb = new StringBuffer("<HTML>");
        sb.append("<HEAD>");
        sb.append("</HEAD>");
        sb.append("<BODY style='margin:0; padding:0; text-align:center;'>");    //중앙정렬
        sb.append("<img width='100%' height='100%' src=\"" + imagUrl+"\">"); //가득차게 나옴
        sb.append("</BODY>");
        sb.append("</HTML>");

        return sb.toString();

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {;
        return places.size();
    }

    // 상단 스크롤

//    public static class LinearLayoutManagerWithSmoothScroller extends LinearLayoutManager {
//
//        public LinearLayoutManagerWithSmoothScroller(Context context) {
//            super(context, VERTICAL, false);
//        }
//
//        public LinearLayoutManagerWithSmoothScroller(Context context, int orientation, boolean reverseLayout) {
//            super(context, orientation, reverseLayout);
//        }
//
//        @Override
//        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
//                                           int position) {
//            RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
//            smoothScroller.setTargetPosition(position);
//            startSmoothScroll(smoothScroller);
//        }
//
//        private class TopSnappedSmoothScroller extends LinearSmoothScroller {
//            public TopSnappedSmoothScroller(Context context) {
//                super(context);
//
//            }
//
//            @Override
//            public PointF computeScrollVectorForPosition(int targetPosition) {
//                return LinearLayoutManagerWithSmoothScroller.this
//                        .computeScrollVectorForPosition(targetPosition);
//            }
//
//            @Override
//            protected int getVerticalSnapPreference() {
//                return SNAP_TO_START;
//            }
//        }
//    }



}//------END

