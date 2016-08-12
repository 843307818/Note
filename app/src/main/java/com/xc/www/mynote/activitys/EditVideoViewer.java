package com.xc.www.mynote.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Administrator on 2016/7/11.
 */
public class EditVideoViewer extends AppCompatActivity {
    private VideoView videoView;
    public static final String VIDEOPATH="path";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoView=new VideoView(this);
        setContentView(videoView);
        videoView.setMediaController(new MediaController(this));
        String path=getIntent().getStringExtra(VIDEOPATH);
        if (path!=null){
            videoView.setVideoPath(path);
        }else finish();
    }
}
