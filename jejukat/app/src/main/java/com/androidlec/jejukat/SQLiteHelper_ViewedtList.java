package com.androidlec.jejukat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper_ViewedtList extends SQLiteOpenHelper {

    //Constructor

    public SQLiteHelper_ViewedtList(Context context) {
        super(context, "ViewedList.db", null, 1); ////내부 데이터베이스생성
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // 젤 처음 구동할때 한번만 onCreate 작동함. //address추가
        String query = "CREATE TABLE viewedlist(seq_viewed INTEGER PRIMARY KEY AUTOINCREMENT, seq_post TEXT, title TEXT, cat1 TEXT, cat2 TEXT, address TEXT, image TEXT, date DATETIME);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS viewedlist;";
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);// 드랍하고 다시 실행
    }
}
