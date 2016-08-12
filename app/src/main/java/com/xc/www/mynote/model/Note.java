package com.xc.www.mynote.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
public class Note implements Parcelable{
    private int id;
    private String name;
    private String content;
    private String date;
    private List<String> photopath=new ArrayList<String>();
    private List<String> videopath=new ArrayList<String>();

    public Note() {
    }

    public void addPhotopath(String newphotopath) {
        photopath.add(newphotopath);
    }

    public void addVideopath(String newvideopath) {
        videopath.add(newvideopath);
    }

    public List<String> getPhotopath() {
        return photopath;
    }

    public List<String> getVideopath() {
        return videopath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(content);
        dest.writeStringList(photopath);
        dest.writeStringList(videopath);
        /*String photopathlist[]= (String[]) photopath.toArray();
        String videopathlist[]= (String[]) videopath.toArray();
        dest.writeStringArray(photopathlist);
        dest.writeStringArray(videopathlist);*/
    }

    public static final Parcelable.Creator<Note> CREATOR= new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[0];
        }
    };

    //这一段方法可以放到createFromParcel()里面，但要如果有List<String>()最好这么写，因为那是个静态变量
    public Note(Parcel parcel) {
        id=parcel.readInt();
        name=parcel.readString();
        content=parcel.readString();
        photopath=new ArrayList<String>();
        parcel.readStringList(photopath);
        videopath=new ArrayList<String>();
        parcel.readStringList(videopath);
    }
}
