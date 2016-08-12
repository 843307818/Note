package com.xc.www.mynote.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xc.www.mynote.model.Note;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
public class NoteDb {
    private static final String DB_NAME="note.db";
    private static final int VERSION=1;
    private static NoteHelper noteHelper;
    private static SQLiteDatabase db;
    private static Context context;

    public void closedb(){
        db.close();
    }

    private static class SingletonHolder{
        static final NoteDb noteDb=new NoteDb();
    }

    public static NoteDb getInstance(Context context){
        NoteDb.context=context;
        noteHelper=new NoteHelper(context,DB_NAME,null,VERSION);
        db=noteHelper.getWritableDatabase();
        return SingletonHolder.noteDb;
    }

    private void Note(){}

    /*private NoteDb(Context context) {
        NoteHelper noteHelper=new NoteHelper(context,DB_NAME,null,VERSION);
        db=noteHelper.getWritableDatabase();
    }

    *//**单例模式
     * 获取NoteDb的实例
     *//*
    public synchronized static NoteDb getInstance(Context context){
        if (noteDb==null){
            noteDb=new NoteDb(context);
        }
        return noteDb;
    }*/

    public List<Note> queryNoteList(){
        List<Note> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from notes",null);//public void execSQL (String sql)所以不能用execSQL方式查询
        while (cursor.moveToNext()){
            Note note=new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex("id")));
            note.setName(cursor.getString(cursor.getColumnIndex("name")));
            note.setContent(cursor.getString(cursor.getColumnIndex("content")));
            note.setDate(cursor.getString(cursor.getColumnIndex("date")));
            list.add(note);
        }
        return list;
    }

    public Note queryNote(int id){
        Note note=new Note();
        Cursor cursor=db.rawQuery("select * from Notes where id=?",new String[]{id+""} );
        if (cursor!=null){
            while(cursor.moveToNext()) {
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setName(cursor.getString(cursor.getColumnIndex("name")));
                note.setContent(cursor.getString(cursor.getColumnIndex("content")));
                note.setDate(cursor.getString(cursor.getColumnIndex("date")));
            }
        }
        cursor=db.rawQuery("select * from Note_Media where note_id=?",new String[]{id+""});
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                if ( cursor.getString(cursor.getColumnIndex("mediatype")).equals("photo")) {
                    note.addPhotopath(cursor.getString(cursor.getColumnIndex("path")));
                } else
                    note.addVideopath(cursor.getString(cursor.getColumnIndex("path")));
            }
        }
        return note;
    }

    public void insertnote(Note note){
        //insert方法返回的是：the row ID of the newly inserted row, or -1 if an error occurred
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",note.getName());
        contentValues.put("content",note.getContent());
        contentValues.put("date",note.getDate());
        int rowId=(int)db.insert("Notes",null,contentValues);
//        db.execSQL("insert into Notes (name,content,date) values(?,?,?)",new String[]{note.getName(),note.getContent(),note.getDate()});
        if (note.getPhotopath().size()>0) {
            for (int i = 0; i < note.getPhotopath().size(); i++) {
                db.execSQL("insert into Note_Media (path,mediatype,note_id) values(?,?,?)",new String[]{note.getPhotopath().get(i), "photo", rowId+ ""});
            }
        }
        if (note.getVideopath().size()>0) {
            for (int i = 0; i < note.getVideopath().size(); i++) {
                db.execSQL("insert into Note_Media (path,mediatype,note_id) values(?,?,?)", new String[]{note.getVideopath().get(i), "video", rowId+ ""});
            }
        }
    }

    public void updatenote(Note note){
        /*ContentValues values = new ContentValues();
        values.put("name", note.getName());
        values.put("content",note.getContent());
        values.put("date",note.getDate());
        db.update("Notes", values, "id = ?", new String[]{ String.valueOf(note.getId())});*/
        db.execSQL("update Notes set name=?,content=?,date=? where id=?",new String[]{note.getName(),note.getContent(),note.getDate(),note.getId()+""} );
//        db.execSQL("delete from Note_Media where note_id=?",new String[]{String.valueOf(note.getId())});
        db.delete("Note_Media","note_id=?",new String[]{note.getId()+""}); //问题：有没有删除成功
        if (note.getPhotopath().size()>0) {
            for (int i = 0; i < note.getPhotopath().size(); i++) {
                if (new File(note.getPhotopath().get(i)).exists()) {
                    db.execSQL("insert into Note_Media (path,mediatype,note_id) values(?,?,?)", new String[]{note.getPhotopath().get(i), "photo", note.getId() + ""});
//                db.execSQL("insert or replace into Note_Media (path,mediatype,note_id) values（?,?,?）",new String[]{note.getPhotopath().get(i), "photo", note.getId() + ""});
//                db.execSQL("insert into Note_Media (path,mediatype,note_id) select (?,?,?) where not exists (select * from Note_Media where path=? )", new String[]{note.getPhotopath().get(i), "photo", note.getId() + "", note.getPhotopath().get(i)});
//                db.execSQL("update Note_Media set path=?,mediatype=?,note_id=?  where exists (select * from Note_Media where path=? )", new String[]{note.getPhotopath().get(i), "photo", note.getId() + "", note.getPhotopath().get(i)});
                }
            }
        }
        if (note.getVideopath().size()>0) {
            for (int i = 0; i < note.getVideopath().size(); i++) {
                if (new File(note.getVideopath().get(i)).exists()) {
                    db.execSQL("insert into Note_Media (path,mediatype,note_id) values(?,?,?)", new String[]{note.getVideopath().get(i), "video", note.getId() + ""});
//                db.execSQL("insert or replace into Note_Media (path,mediatype,note_id) values（?,?,?）",new String[]{note.getVideopath().get(i), "video", note.getId() + ""});
//                db.execSQL("insert into Note_Media (path,mediatype,note_id) select (?,?,?) where not exists (select * from Note_Media where path=? )", new String[]{note.getVideopath().get(i), "video", note.getId() + "", note.getVideopath().get(i)});
//                db.execSQL("update Note_Media (path,mediatype,note_id) set path=?,mediatype=?,note_id=?  where exists (select * from Note_Media where path=? )", new String[]{note.getVideopath().get(i), "video", note.getId() + "", note.getVideopath().get(i)});
                }
            }
        }
    }



}
