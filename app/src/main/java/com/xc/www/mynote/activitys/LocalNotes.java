package com.xc.www.mynote.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.xc.www.mynote.R;
import com.xc.www.mynote.adapters.LocalNoteListAdapter;
import com.xc.www.mynote.db.NoteDb;
import com.xc.www.mynote.model.Note;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
public class LocalNotes extends Fragment {

    private Context context;
    private ListView listView;
    private List<Note> list;
    private LocalNoteListAdapter adapter=null;
    private NoteDb noteDb;

    public static final int REQUEST_CODE_ADD_NOTE=1;
    public static final int REQUEST_CODE_READ_NOTE=2;
    private Button addnote;
    private View.OnClickListener ButtonClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(context,EditNote.class);
            i.putExtra("opentype",2);
            startActivityForResult(i,REQUEST_CODE_ADD_NOTE);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.localnotes,container,false);
        listView= (ListView) view.findViewById(R.id.localnote_lv);
        addnote= (Button) view.findViewById(R.id.addnote);
        addnote.setOnClickListener(ButtonClick);
        context=getActivity().getApplicationContext();
        noteDb=NoteDb.getInstance(context);
        list=noteDb.queryNoteList();
        adapter=new LocalNoteListAdapter(context,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note= adapter.getItem(position);
                Intent i=new Intent(context,EditNote.class);
                Bundle bundle=new Bundle();
                bundle.putInt("noteId",note.getId());
                bundle.putInt("opentype",1);
                i.putExtras(bundle);
//                i.putExtra("noteParcel",note);
                startActivityForResult(i,REQUEST_CODE_READ_NOTE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_ADD_NOTE:
                /*if (resultCode== Activity.RESULT_OK){
                adapter.add((Note) data.getParcelableExtra("backnote"));
                }
                adapter.notifyDataSetChanged();
                break;*/
            case REQUEST_CODE_READ_NOTE:
                if (resultCode== Activity.RESULT_OK){
//                    adapter.updatenote(data.getExtras().getInt("position"),(Note) data.getParcelableExtra("backnote"));
                    adapter.setNotelist(list=noteDb.queryNoteList());
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        noteDb.closedb();
        super.onDestroy();
    }
}
