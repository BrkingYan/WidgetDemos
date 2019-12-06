package com.example.yy.widgetdemos.SaveDataDemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    /*private static final String CREATE_T_BOOK = "create table book("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            +  "pages integer, "
            + "name text)";

    private static final String CREATE_T_CATEGORY = "create table category("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";*/

    public MyDatabaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(mContext,"Create DB succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("drop table if exists book");
        db.execSQL("drop table if exists category");
        onCreate(db);*/
    }
}
