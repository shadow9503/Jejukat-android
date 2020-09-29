package com.androidlec.jejukat;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class CustomProgressDialog_AddSchedule extends Dialog {
//    TextView tv_message;
    public CustomProgressDialog_AddSchedule(Context context) {
        super(context);

        requestWindowFeature( Window.FEATURE_NO_TITLE); // 지저분한(?) 다이얼 로그 제목을 날림
        setContentView(R.layout.progress_dialog_addschedule); // 다이얼로그에 박을 레이아웃

//        tv_message = findViewById(R.id.tv_progress_message);
    }

    // 다이어로그 메시지 설정
//    public void setDialogMessage(String message) {
//        tv_message.setText(message);
//    }
}
