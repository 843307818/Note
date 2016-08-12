package com.xc.www.mynote.activitys;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xc.www.mynote.R;

/**
 * Created by Administrator on 2016/7/8.
 */
public class Article extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article,container,false);
        return view;
    }
}
