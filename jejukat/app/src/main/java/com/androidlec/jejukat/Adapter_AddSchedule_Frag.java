package com.androidlec.jejukat;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter_AddSchedule_Frag extends RecyclerView.Adapter<Adapter_AddSchedule_Frag.CustomViewHolder>
implements ItemTouchHelperListener {

    //Field
    private ArrayList<Bean_picklist> places;
    private Activity_AddSchedule addScheduleActivity;

    //댓글삭제
    private String urlAddrDeleteComment;

    static String centIP = STATICDATA2.CENTIP;
//    static int seq_post = STATIC_DATA.SEQ_POST;
//    static int seq_user = STATIC_DATA.SEQ_USER;
    int seq_cmt;

    private TextView tv_place_title;
    private TextView tv_place_cat;
    private TextView tv_place_address;

    //Constructor
    public Adapter_AddSchedule_Frag(ArrayList<Bean_picklist> places, Activity_AddSchedule context) {
        this.places = places;
        this.addScheduleActivity = context;
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
        // EDITMODE 인지 STATIC 변수로 체크
//        switch (STATIC_DATA.EDITMODE) {
//            case 1:
//                holder.tv_remove_place.setVisibility( View.VISIBLE );
//                break;
//            case 0:
//                holder.tv_remove_place.setVisibility( View.INVISIBLE );
//                break;
//        }

        AdapterView.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }

        };

        holder.tv_remove_place.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove( position );
//                addScheduleActivity.setMultipleMarker();
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

    // 편집모드 설정 메소드
    public void isEditable(Boolean b) {
//        Log.v( "Adapter", "editable()" );

//        if( b == true) {
//            STATIC_DATA.EDITMODE = 1;
//        } else {
//            STATIC_DATA.EDITMODE = 0;
//        }
//        Log.v( "Adapter", String.valueOf(STATIC_DATA.EDITMODE));
    }

    @Override
    public boolean onItemMove(int form_position, int to_position) {
//        Log.v( "Adapter", "ItemMove");
//        AddSchedule_PickPlacesListDto placeItem = places.get(form_position);
//        places.remove(form_position);
//        places.add(to_position,placeItem);
//        notifyItemMoved(form_position, to_position);
//        addSchedule_fragmentPlaces.dataSetChanged();
        return true;
    }
    @Override
    public void onItemSwipe(int position) {
        remove( position );
        Activity_AddSchedule_FragmentPlaces.addSchedule_fragmentPlaces.dataSetChanged();
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
            this.tv_remove_place = itemView.findViewById( R.id.tv_scheduledetails_remove_place);
            this.tv_place_number = itemView.findViewById( R.id.tv_scheduledetails_show_number);


        }
    }

}///---------
