package com.xc.www.mynote.activitys;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.xc.www.mynote.R;
import com.xc.www.mynote.adapters.MyPagerAdapter;

/**
 * Created by Administrator on 2016/7/8.
 */
public class CloudNotes extends Fragment {

    private PagerSlidingTabStrip tabs;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private final String[] CLOUDTITLES={ "Java", "Andorid", "IOS", "HTML5"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cloudnotes,container,false);
        tabs= (PagerSlidingTabStrip) view.findViewById(R.id.pagertabs);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        myPagerAdapter=new MyPagerAdapter(getActivity().getSupportFragmentManager(),CLOUDTITLES);
        viewPager.setAdapter(myPagerAdapter);
        tabs.setViewPager(viewPager);
        setTabValue();
        return view;
    }
    public void setTabValue(){
        tabs.setSelectTextColor(getResources().getColor(R.color.tab_text_color_selected));
    }
}
