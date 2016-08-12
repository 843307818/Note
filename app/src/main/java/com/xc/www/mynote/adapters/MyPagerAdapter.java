package com.xc.www.mynote.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xc.www.mynote.activitys.MyFragment;

import java.util.ArrayList;
import java.util.List;

/**viewpager的标签适配器
 * Created by Administrator on 2016/7/9.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private String[] TITLES=null;
    List<MyFragment> fragments = new ArrayList<MyFragment>();
    public MyPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        TITLES=titles;
        for(String title:TITLES){
            MyFragment fragment = new MyFragment();
            Bundle args =new Bundle();
            args.putString("param", title);
            fragment.setArguments(args);
            fragments.add(fragment);
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
    @Override
    public int getCount() {
        return TITLES.length;
    }

    //当viewpager滑动时会提取相应的fragment
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
