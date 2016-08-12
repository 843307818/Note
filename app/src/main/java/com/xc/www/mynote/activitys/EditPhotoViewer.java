package com.xc.www.mynote.activitys;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Administrator on 2016/7/11.
 */
public class EditPhotoViewer extends AppCompatActivity {
    private ImageView imageView;
    public static final String EXTRA_PHOTOPATH="path";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView=new ImageView(this);
        setContentView(imageView);
        String photopath=getIntent().getStringExtra(EXTRA_PHOTOPATH);
        if (photopath!=null){
            imageView.setImageURI(Uri.fromFile(new File(photopath)));
        }else finish();
    }
}
