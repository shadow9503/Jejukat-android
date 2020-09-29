package com.androidlec.jejukat;

import android.content.Context;
import android.net.Uri;

import com.nhn.android.naverlogin.OAuthLogin;

import java.util.ArrayList;

public class STATICDATA {

    //로그아웃:0, 로그인:1;
    public static int LOGIN_STATUS = 0;

    //카카오 1, 페북2, 네이버3, 구글4;
    public static int LOGIN_METHOD = 0;

    public static String EMAIL = "하단의 로그인 버튼을 눌러주세요";
    public static String NICKNAME = "비회원";
    public static String PROFILEPIC = "119.207.169.213:8080/ftp/user.png";
    public static String SEQ_USER = "";

    public static String SEARCHTAG = "";

    public static String TEMP_EMAIL = "";
    public static String TEMP_NICKNAME = "";
    public static String TEMP_PROFILEPIC = "";

    public static String TEMP_CAT1="";
    public static String TEMP_CAT2="";

    public static String CAT1="";
    public static String CAT2="";

    //order by title, order by view, order by date
    public static String ORDER="view desc";

    protected static final String NAVER_CLIENT_ID = "dndDyy63GmXga1fM2W8Y";
    protected static final String NAVER_CLIENT_SECRET = "dAQTNtpqTD";
    protected static final String NAVER_CLIENT_NAME = "네이버아이디로그인테스트";
    protected static OAuthLogin NAVER_LOGIN_INSTANCE;

    public static String CONTEXT;
    public static String ADDRESS;
    public static String TEL;
    public static String HOMEPAGE;


    public static String CENTIP = "119.207.169.213"; //댓글
    public static int SEQ_POST; //댓글
    public static int SEQ_CMT; //댓글

    public static ArrayList<Uri> URIS;
    public static ArrayList<String> FILENAMES;
    public static int FTPUPLOAD=0;

    public static ArrayList<Bean_Detail_Schedule_PickPlacesList> PLACEARRAY;


    public static String SEQ_NOTIFICATION;


}
