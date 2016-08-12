package com.xc.www.mynote.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xc.www.mynote.R;

/**
 * Created by Administrator on 2016/4/9.
 */
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_fragment, container,false);
        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(getArguments().getString("param"));
        return view;
    }
}
