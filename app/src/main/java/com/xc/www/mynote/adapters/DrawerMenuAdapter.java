package com.xc.www.mynote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xc.www.mynote.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/7.
 */
public class DrawerMenuAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list=null;
    public DrawerMenuAdapter(Context context,List<Map<String,Object>> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            v= LayoutInflater.from(context).inflate(R.layout.drawermenu_list_cell,null);
            viewHolder.imageView=(ImageView)v.findViewById(R.id.menucell_iv);
            viewHolder.textView= (TextView) v.findViewById(R.id.menucell_tv);
            v.setTag(viewHolder);
        }else {
            v=convertView;
            viewHolder= (ViewHolder) v.getTag();
        }
        viewHolder.imageView.setImageResource((Integer)(list.get(position).get("image")));
        viewHolder.textView.setText((String) (list.get(position).get("title")));
        return v;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
