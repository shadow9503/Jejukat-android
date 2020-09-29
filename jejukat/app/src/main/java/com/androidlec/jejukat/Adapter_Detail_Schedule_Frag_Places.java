package com.androidlec.jejukat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter_Detail_Schedule_Frag_Places extends RecyclerView.Adapter<Adapter_Detail_Schedule_Frag_Places.CustomViewHolder>{

    //Field
    private ArrayList<Bean_Detail_Schedule_PickPlacesList> places;

    //댓글삭제
    private String urlAddrDeleteComment;

    //    static int seq_post = STATIC_DATA.SEQ_POST;
//    static int seq_user = STATIC_DATA.SEQ_USER;*
    int seq_cmt;


    //Constructor
    public Adapter_Detail_Schedule_Frag_Places(ArrayList<Bean_Detail_Schedule_PickPlacesList> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_pick_places_listitem, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
//        Log.v( "Adapter", "onBind()");
        holder.tv_place_title.setText(places.get(position).getPlaceName());
        holder.tv_place_cat.setText(places.get(position).getPlaceCat1() + " > " + places.get(position).getPlaceCat2());
        holder.tv_place_address.setText(places.get(position).getPlaceAddress());
        holder.tv_place_number.setText(String.valueOf(position+1));


        AdapterView.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }

        };

        holder.tv_place_title.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Activity_Detail_Place.class);
                STATICDATA.SEQ_POST=Integer.parseInt(places.get(position).getPlaceSeq());
                view.getContext().startActivity(intent);
            }
        } );

    }

    @Override
    public int getItemCount() {
//        Log.v( "Adapter", "ArrayListSize: " + String.valueOf( places.size() ) );
        return (null != places ? places.size() : 0);
    }

    // 아이템 삭제 메소드
    public void remove(int position) {
        try {
            places.remove(position); // position에 있는 값을 제거 해줘라
            notifyItemRemoved(position); // 리스트뷰 지운 다음에 새로고침 해주는 것
            notifyItemRangeChanged( position, places.size() );
//            addSchedule_fragmentPlaces.dataSetChanged();
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    //사용할것 선언
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_place_title;
        protected TextView tv_place_cat;
        protected TextView tv_place_address;
        protected TextView tv_remove_place;
        protected TextView tv_place_number;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tv_place_title = itemView.findViewById(R.id.tv_scheduledetails_show_title);
            this.tv_place_cat = itemView.findViewById(R.id.tv_scheduledetails_show_cat);
            this.tv_place_address = itemView.findViewById(R.id.tv_scheduledetails_show_address);
            this.tv_remove_place = itemView.findViewById( R.id.tv_scheduledetails_remove_place );
            this.tv_place_number = itemView.findViewById( R.id.tv_scheduledetails_show_number );


        }
    }

}///---------
