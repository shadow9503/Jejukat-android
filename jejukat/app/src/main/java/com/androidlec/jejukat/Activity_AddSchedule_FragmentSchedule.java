package com.androidlec.jejukat;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

import com.gun0912.tedpermission.PermissionListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

public class Activity_AddSchedule_FragmentSchedule extends Fragment {

    private View view;
    private EditText input_context_ed;   // 일정 내용
    private CustomGridView input_images_gv;   // 이미지 그리드뷰
    private Adapter_AddSchedule_GridView adapter;

    // 버튼
    private Button clearImage_btn, loadImage_btn;

    // constant
    final int PICTURE_REQUEST_CODE = 100;

    //상태저장해야함
    public static Activity_AddSchedule_FragmentSchedule newInstance(){
        // 프레그먼트 교체할때 필요함
        Activity_AddSchedule_FragmentSchedule fragContext = new Activity_AddSchedule_FragmentSchedule();
        return fragContext; // 어댑터랑 연결하려고
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        STATICDATA2.URIS = new ArrayList<>();  // 이미지 Uri 를 담는 Static ArrayList
        STATICDATA2.FILENAMES = new ArrayList<>();  // FTP업로드 되는 이미지 이름이 담긴 Static ArrayList

        // xml연동
        view = inflater.inflate(R.layout.tablayout_fragment_schedule_addschedule, container, false);

        setWidgetId(); // 위젯 등록
        setListener(); // 리스너 등록
        input_images_gv.setExpanded(true);

        return view;

    }

    // 위젯 등록
    private void setWidgetId() {
        input_context_ed = view.findViewById(R.id.ed_addschedule_input_context);
        input_images_gv = view.findViewById(R.id.gv_addschedule_input_images);
        clearImage_btn = view.findViewById(R.id.btn_addschedule_clear_image);
        loadImage_btn = view.findViewById(R.id.btn_addschedule_load_image);
    }

    // 리스너 등록
    private void setListener() {
        loadImage_btn.setOnClickListener(onClickListener);
        clearImage_btn.setOnClickListener(onClickListener);
    }

    /*/////////////////////////////////////////////////////////////
              이미지 불러오기 버튼 / 이미지 초기화 버튼 리스너
    *//////////////////////////////////////////////////////////////

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_addschedule_load_image:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    //사진을 여러개 선택할수 있도록 한다
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);
                    break;
                case R.id.btn_addschedule_clear_image:
                    if(STATICDATA2.URIS.size() > 0) {
                        adapter.clearItem();
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }

        }
    };

    /*//////////////////////////////////
    //          접근 권한 확인
    *///////////////////////////////////

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText( getActivity(), "", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getActivity(), "권한이 거부됨",Toast.LENGTH_SHORT).show();
        }
    };

    /*///////////////////////////////////////////////////////////////
    //          갤러리에서 선택한 이미지들을 받아오는 메소드
    *////////////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // 1개의 ClipData 는 Uri에 바로 담아서 add
                Uri urione = data.getData();
                // ClipData 를 가져온다.
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    // 갤러리에서 가져온 이미지 Uri 담기
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        if (i < clipData.getItemCount()) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            STATICDATA2.URIS.add(uri);
                        }
                    }
                } else if (urione != null) {
                    STATICDATA2.URIS.add(urione);
                }
            }
        }
        // 그리드 뷰 생성
        if (STATICDATA2.URIS != null) {
            adapter = new Adapter_AddSchedule_GridView( getActivity(), R.layout.schedule_gridview_item) ;
            input_images_gv.setAdapter( adapter );
        }
    }



    /*////////////////////////////////////////
    //      FTP서버로 일정 이미지 업로드     ------------- AddScheduleAcitivity 에서 호출함.
    */////////////////////////////////////////

    public void uploadScheduleImage() {
//        Log.v("JSON", String.valueOf(StaticDataApplication.getInstance().getURIS().size()));
        ConnectFTP addSchedule_connectFTP = new ConnectFTP(getActivity(), STATICDATA2.CENTIP, "tj", "1234", 21, STATICDATA2.URIS );
        addSchedule_connectFTP.execute();

        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                if(STATICDATA2.FTPUPLOAD == 1) {
                    Log.v("FTP UPLOAD",Integer.toString(STATICDATA2.FTPUPLOAD));
                    createScheduleData();
                } else {
                    Log.v("FTP UPLOAD",Integer.toString(STATICDATA2.FTPUPLOAD));
                    mHandler.postDelayed(this, 1500);
                }
            }
        }, 1500);
    }

    /*////////////////////////////////////////////////////
    //      DB업로드 할 데이터들 모으고 STATIC에 넣기     ------------- uploadScheduleImage() 에서 호출함.
    */////////////////////////////////////////////////////

    private void createScheduleData() {
        // 액티비티의 메소드 사용하기위해 가져옴
        Activity_AddSchedule addScheduleActivity = (Activity_AddSchedule) Activity_AddSchedule.addScheduleActivity;

        // STATICDATA 에서 FTP업로드때 저장한 이미지 이름들을 가져옴.
        ArrayList<String> imgArray = STATICDATA.FILENAMES;
        Log.v("process",imgArray.toString());

        // 문자열 배열에 담기
        String[] image = new String[imgArray.size()];
        for(int i=0; i<imgArray.size(); i++ ) {
//            Log.v("ImageValue", image[i]);
            image[i] = imgArray.get(i) + ".jpg";
        }

        // 장소 이미지
        JSONArray placeimg = new JSONArray(Arrays.asList(image));

        // 장소 이미지 스트링 저장
        STATICDATA2.IMAGES = placeimg.toString();

        // 일정 타이틀 저장
        STATICDATA2.TITLE = addScheduleActivity.getScheduleContext();   // AddScheduleActivity 에서 title 텍스트 메소드로 가져옴.

        // 일정 내용 저장
        STATICDATA2.CONTEXT = input_context_ed.getText().toString();

        connectInsertData();
        Intent intent = new Intent(addScheduleActivity,Activity_Main.class);
        startActivity(intent);
        // 장소 시퀀스 배열은 insert TASK 내부에서 만듬.
    }

    /*//////////////////////////////////////////////////////////
    //      일정 데이터 Task전달
    *///////////////////////////////////////////////////////////

    private void connectInsertData() {
//        Log.v( "Current", "connectInsertData()" );
        int validation = 0; // default=0 등록 / 1=삭제
        String urlAddr="";
        urlAddr = "http://" + STATICDATA2.CENTIP + ":8080/jejukat/AddSchedule_insert_schedule.jsp";
        Log.v("url",urlAddr);
        try {
            NetworkTask_Upload_Schedule addSchedule_insert_networkTask
                    = new NetworkTask_Upload_Schedule(getActivity(), urlAddr, STATICDATA2.PLACEARRAY);
            addSchedule_insert_networkTask.execute().get();

            Toast.makeText(getActivity(), "일정이 업로드 되었습니다.", Toast.LENGTH_SHORT ).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}//-------------END
