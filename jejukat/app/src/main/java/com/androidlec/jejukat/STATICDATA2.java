package com.androidlec.jejukat;


import android.net.Uri;

import java.util.ArrayList;

public class STATICDATA2 {
    public static String CENTIP = "119.207.169.213";
//    public static String CENTIP = "182.230.84.233";

    //  일정 내용들 담기위한 변수
    ////////////////////////////////////
    public static String TITLE;
    public static String CONTEXT;
    public static String IMAGES;
    ////////////////////////////////////

    public static ArrayList<Bean_picklist> PLACEARRAY;          // 장소목록 리스트 adapter에 연결됨.
    public static String SEQ_POST; // 검색 액티비티에서 선택한 장소의 SEQ
    public static int GET_SEQ_POST = 100; // seq_post intent로 받아올때 REQUEST_CODE 와 비교하는 값

    // 갤러리 & FTP
    public static ArrayList<Uri> URIS;          // 갤러리에서 선택한 이미지들
    public static ArrayList<String> FILENAMES;          // 업로드된 이미지 이름들
    public static int FTPUPLOAD = 0;          // FTP업로드 체크 변수

}
