package com.xc.www.mynote.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.xc.www.mynote.R;
import com.xc.www.mynote.adapters.MedialistAdapter;
import com.xc.www.mynote.db.NoteDb;
import com.xc.www.mynote.model.Note;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class EditNote extends Activity implements View.OnClickListener{
    private EditText title;
    private EditText content;
    private ListView listView;
    private Button save;
    private Button cancel;
    private Button takephoto;
    private Button recordvideo;

    private int opentype;
    private Intent intent;
    private NoteDb noteDb;
    private MedialistAdapter adapter=null;
    private String mediatemporarypath=null;
    private Note note;
    private AdapterView.OnItemClickListener listclick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;
            String path=adapter.getItem(position);
            if (path.endsWith(".png")){
                intent=new Intent(EditNote.this,EditPhotoViewer.class);
                intent.putExtra(EditPhotoViewer.EXTRA_PHOTOPATH,path);
                startActivity(intent);
            }else if (path.endsWith(".mp4")){
                intent=new Intent(EditNote.this,EditVideoViewer.class);
                intent.putExtra(EditVideoViewer.VIDEOPATH,path);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnote);
        title= (EditText) findViewById(R.id.title_et);
        content= (EditText) findViewById(R.id.content_et);
        listView= (ListView) findViewById(R.id.media_lv);
        adapter=new MedialistAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listclick);

        note=new Note();
        intent=getIntent();
        Bundle bundle=new Bundle();
        bundle=intent.getExtras();
        opentype=bundle.getInt("opentype",0);
        if (opentype==1){
            noteDb=NoteDb.getInstance(getApplicationContext());
            note=noteDb.queryNote(bundle.getInt("noteId"));
//            note =intent.getParcelableExtra("noteParcel");
            title.setText(note.getName());
            content.setText(note.getContent());
            ArrayList<String> i1= (ArrayList<String>) note.getPhotopath();
            adapter.addAll(i1);//执行此句后note的photopath为2
            List<String> i2=note.getPhotopath();
            adapter.addAll((ArrayList<String>) note.getVideopath());
            adapter.notifyDataSetChanged();
        }

        save= (Button) findViewById(R.id.save);
        cancel= (Button) findViewById(R.id.cancel);
        takephoto= (Button) findViewById(R.id.takephoto);
        recordvideo= (Button) findViewById(R.id.recordvideo);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        takephoto.setOnClickListener(this);
        recordvideo.setOnClickListener(this);

    }

    private Intent mediaIntent;
    private File file;
    public static final int REQUEST_CODE_GET_PHOTO=1;
    public static final int REQUEST_CODE_GET_VIDEO=2;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                save();
//                intent.putExtra("backnote", note);
//                intent.putExtra("position",intent.getExtras().getInt("position"));
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.takephoto:
                mediaIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file=new File(getMediaDir(),System.currentTimeMillis()+".png");
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mediatemporarypath=file.getAbsolutePath();
                mediaIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(mediaIntent,REQUEST_CODE_GET_PHOTO);
                break;
            case R.id.recordvideo:
                mediaIntent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                file=new File(getMediaDir(),System.currentTimeMillis()+".mp4");
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mediatemporarypath=file.getAbsolutePath();
                Log.e("1", "onClick: " );
                mediaIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(mediaIntent,REQUEST_CODE_GET_VIDEO);
                break;
            default:
                break;
        }
    }

    public void save(){
        note.setName(title.getText().toString());
        note.setContent(content.getText().toString());
        note.setDate(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()));
        //数据库更新
        noteDb=NoteDb.getInstance(getApplicationContext());
        if (opentype==2){
            noteDb.insertnote(note);
        }else if (opentype==1){
            noteDb.updatenote(note);
        }
    }

    public File getMediaDir(){
        File file=new File(Environment.getExternalStorageDirectory(),"NoteMedia");
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case REQUEST_CODE_GET_PHOTO:
                if(resultCode==RESULT_OK){
                    note.addPhotopath(mediatemporarypath);
                    adapter.add(mediatemporarypath);
                    adapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_CODE_GET_VIDEO:
                if(resultCode==RESULT_OK){
                    note.addVideopath(mediatemporarypath);
                    adapter.add(mediatemporarypath);
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
//        noteDb.closedb();
        super.onDestroy();
    }
}
