package com.xc.www.mynote.model;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Media {
    private int noteid=1;
    public static String path="";


    public Media(String path) {
        this.path = path;
    }

    public Media(String path, int noteid) {
        this.path = path;
        this.noteid = noteid;
    }
}
