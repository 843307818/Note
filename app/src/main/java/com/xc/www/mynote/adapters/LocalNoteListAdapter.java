package com.xc.www.mynote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xc.www.mynote.R;
import com.xc.www.mynote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class LocalNoteListAdapter extends BaseAdapter {

    private List<Note> notelist=new ArrayList<Note>();
    private Context context;

    public LocalNoteListAdapter(Context context,List<Note> notelist) {
        this.context=context;
        this.notelist.addAll(notelist);
    }

    public void add(Note note){
        notelist.add(note);
    }

    public void setNotelist(List<Note> notelist) {
        this.notelist = notelist;
    }

    public void updatenote(int position, Note note){
        notelist.get(position).setName(note.getName());
        notelist.get(position).setDate(note.getDate());
    }
    @Override
    public int getCount() {
        return notelist.size();
    }

    @Override
    public Note getItem(int position) {
        return notelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notelist.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note=notelist.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.note_list_cell,null);
            viewHolder=new ViewHolder();
            viewHolder.title= (TextView) view.findViewById(R.id.note_title_tv);
            viewHolder.date= (TextView) view.findViewById(R.id.date_tv);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(note.getName());
        viewHolder.date.setText(note.getDate());
        return view;
    }


    class ViewHolder{
        private TextView title;
        private TextView date;
    }
}
