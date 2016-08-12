package com.xc.www.mynote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/9.
 */
public class NoteHelper extends SQLiteOpenHelper {

    //   \"\"为空字符串
    private static final String CREATE_NOTES="create table Notes("+
            "id integer primary key autoincrement,"+
            "name text not null default \"\","+
            "content text not null default \"\","+
            "date text not null default \"\""+")";
    private static final String CREATE_NOTES_MEDIA="create table Note_Media("+
            "id integer primary key autoincrement,"+
            "path text not null default \"\","+
            "mediatype text not null default \"\","+
            "note_id integer not null default 0"+")";

    public NoteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTES);
        db.execSQL(CREATE_NOTES_MEDIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
