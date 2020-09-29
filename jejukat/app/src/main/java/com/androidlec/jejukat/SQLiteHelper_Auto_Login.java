package com.androidlec.jejukat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper_Auto_Login extends SQLiteOpenHelper {

    //Constructor

    public SQLiteHelper_Auto_Login(Context context) {
        super(context, "Login.db", null, 1); ////내부 데이터베이스생성
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // 젤 처음 구동할때 한번만 onCreate 작동함.
        String query = "CREATE TABLE latestlist(seq INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, method TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS latestlist;";
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);// 드랍하고 다시 실행
    }
}
