package com.androidlec.jejukat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Activity_AddSchedule_FragmentPlaces extends Fragment {

    // constant
//    final int GET_SEQ_POST = 100;

    // 커스텀 마커를위한 뷰 선언
    private View view;
    public static Activity_AddSchedule_FragmentPlaces addSchedule_fragmentPlaces;
    private Activity_AddSchedule addScheduleActivity;


    private Bean_picklist place;  // BEAN
    private ArrayList<Bean_picklist> place_stack; // adapter에 사용되는 장소목록 arraylist
    private Adapter_AddSchedule_Frag places_adapter;

    private RecyclerView recyclerView;
    private ItemTouchHelper helper;
    private LinearLayoutManager linearLayoutManager;


    private String urlAddr;

    //상태저장해야함
    public static Activity_AddSchedule_FragmentPlaces newInstance() {
        // 프레그먼트 교체할때 필요함
        Activity_AddSchedule_FragmentPlaces fragComment = new Activity_AddSchedule_FragmentPlaces();
        return fragComment; // 어댑터랑 연결위해
    }

    @Override
    public void onResume() {
        super.onResume();
        ConnectRecyclerView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // xml연동
        view = inflater.inflate(R.layout.tablayout_fragment_map_places_addschedule, container, false);
        addSchedule_fragmentPlaces = Activity_AddSchedule_FragmentPlaces.this;
        // 장소 목록 리스트가 누적됨
        STATICDATA2.PLACEARRAY = new ArrayList<>();

        // 장소 목록 추가 버튼
        Button btn_add_place = view.findViewById(R.id.btn_AddSchedule_add_place);
        btn_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), Activity_Search2Pick.class );
                startActivityForResult(intent, STATICDATA2.GET_SEQ_POST);
            }
        });

        // 장소 목록 편집 버튼
//        final ToggleButton btn_edit_place = view.findViewById( R.id.btn_AddSchedule_edit_place );
//
//        btn_edit_place.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            if(STATIC_DATA.PLACEARRAY != null) {
//                if (btn_edit_place.isChecked() == true) {
//                    places_adapter.isEditable( true );
//                    dataSetChanged();
//                } else {
//                    places_adapter.isEditable( false );
//                    dataSetChanged();
//                }
//            }
//            }
//        } );

        // 리사이클러뷰
        ConnectRecyclerView();

        return view;
    }

    public void dataSetChanged() {
        places_adapter.notifyDataSetChanged();
        addScheduleActivity.setMultipleMarker();
    }

    //리사이클러뷰 연결
    public void  ConnectRecyclerView(){
        recyclerView = view.findViewById(R.id.rv_addschedule_show_places);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        places_adapter = new Adapter_AddSchedule_Frag(STATICDATA2.PLACEARRAY, addScheduleActivity);
        recyclerView.setAdapter(places_adapter);

        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(places_adapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.addOnItemTouchListener( new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                if(e.getAction() == MotionEvent.ACTION_UP) {
//                    Toast.makeText( addScheduleActivity, "UP", Toast.LENGTH_SHORT ).show();
//                    dataSetChanged();
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        } );
    }


    /*//////////////////////////////////////////////////////////////////
            SearchPlaceActivity 에서 클릭한 장소의 seq를 전달 받는다.
    *///////////////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == STATICDATA2.GET_SEQ_POST) {
            if (resultCode == RESULT_OK) {

                String seq_post = data.getStringExtra( "seq_post" );
                urlAddr = "";
                urlAddr = "http://" + STATICDATA2.CENTIP + ":8080/jejukat/AddSchedule_select_place.jsp?";
                urlAddr += "seq_post=" + seq_post;
                try {
                    NetworkTask_AddSchedule_PickPlaces pickPlaces_networkTask
                            = new NetworkTask_AddSchedule_PickPlaces(getActivity(), urlAddr);
                    place = (Bean_picklist) pickPlaces_networkTask.execute().get();
                    STATICDATA2.PLACEARRAY.add( place );

                    addScheduleActivity = (Activity_AddSchedule) getActivity(); // AddScheduleActivity의 메소드 호출
                    addScheduleActivity.setMultipleMarker();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }




}//------------