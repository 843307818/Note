package com.xc.www.mynote.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.xc.www.mynote.R;
import com.xc.www.mynote.adapters.DrawerMenuAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView menulist;

    private FragmentTabHost fragmentTabHost;
    private Class<?> fragmentClasses[]={LocalNotes.class,CloudNotes.class,Article.class,Setting.class};
    private int imageIds[] = { R.drawable.tab_news_selector, R.drawable.tab_found_selector,
            R.drawable.tab_read_selector, R.drawable.tab_self_selector };
    private String tabLabels[] = { "Local", "Cloud", "Article", "Setting" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar_drawerlayout();
        tabhost();

    }

    //
    private void tabhost() {
        fragmentTabHost= (FragmentTabHost) findViewById(R.id.id_f_tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),R.id.id_layout_fl_tab_content);
        for (int i=0;i<tabLabels.length;i++){
            TabHost.TabSpec tabSpec=fragmentTabHost.newTabSpec(tabLabels[i]+i);
            tabSpec.setIndicator(getTabLabelView(i));
            fragmentTabHost.addTab(tabSpec, fragmentClasses[i],null);
        }

    }
    //设置并获取tabhost标签textview
    public View getTabLabelView(int i){
        TextView textView= (TextView) View.inflate(this,
                R.layout.layout_indicator_view, null);
        //  TextView textView= (TextView) linearLayout.findViewById(R.id.id_tv_indicator);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
                .getDrawable(imageIds[i], this.getTheme()), null, null);
        textView.setText(tabLabels[i]);
        return textView;
    }


    private void toolbar_drawerlayout() {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon);
        toolbar.setTitle("Note");
        toolbar.setSubtitle("NoteList");
        setSupportActionBar(toolbar);  // setSupportActionBar 设定，Toolbar即能取代原本的 actionbar
        //setNavigationIcon需要放在 setSupportActionBar之后才会生效
        /*toolbar.inflateMenu(R.menu.main_menu);     //这种方式加载菜单不能设置setSupportActionBar(toolbar);否则无法加载菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit:
                        break;
                    case R.id.delete:
                        break;
                    case R.id.media:
                        break;
                    default:
                        break;
                }
                return true;  //
            }
        });*/
        toolbar.setPopupTheme(R.style.PopupMenu);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle=new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        //初始化drawerlayout里的菜单列表数据
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("image",R.drawable.find);
        map1.put("title","Search");
        list.add(map1);
        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("image",R.drawable.picture2);
        map2.put("title","Photos");
        list.add(map2);
        Map<String,Object> map3=new HashMap<String,Object>();
        map3.put("image",R.drawable.videos);
        map3.put("title","Videos");
        list.add(map3);
        menulist= (ListView) findViewById(R.id.drawermenu);
        DrawerMenuAdapter drawerMenuAdapter=new DrawerMenuAdapter(MainActivity.this,list);
        menulist.setAdapter(drawerMenuAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //菜单（也是toolbar里的菜单）的点击处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                break;
            case R.id.delete:
                break;
            case R.id.media:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
