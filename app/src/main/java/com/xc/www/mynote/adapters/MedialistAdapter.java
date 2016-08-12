package com.xc.www.mynote.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xc.www.mynote.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MedialistAdapter extends BaseAdapter {

    private List<String> list=new ArrayList<String>();
    private Context context;

    public MedialistAdapter(Context context) {
        this.context = context;
    }

    public void addAll(ArrayList<String> list){
        this.list.addAll(list);
    }

    public void add(String path){
        list.add(path);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        /**
         * convertView避免重复去加载布局
         * ViewHolder避免重复调用View的findViewById()方法来获取控件的实例
         */
        if (convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.media_list_cell,null);
            viewHolder=new ViewHolder();
            viewHolder.ivIcon= (ImageView) view.findViewById(R.id.ivIcon);
            viewHolder.tvPath= (TextView) view.findViewById(R.id.tvPath);
            view.setTag(viewHolder); //将ViewHolder存储在View中
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.ivIcon.setAdjustViewBounds(true);// 自动缩放为宽高比
        viewHolder.ivIcon.setMaxHeight(80);// 图片的高度为80dp
        viewHolder.ivIcon.setPadding(10, 10, 10, 10); //内边距
        Bitmap bitmap;
        String path=list.get(position);
        File file=new File(path);
        if (file.exists()) {
            if (path.endsWith(".png")) {
                bitmap = BitmapFactory.decodeFile(list.get(position),getHeapOpts(file));
                bitmap=Bitmap.createScaledBitmap(bitmap,20,20,true);
                viewHolder.ivIcon.setImageBitmap(bitmap);
            } else if (path.endsWith(".mp4")) {
                bitmap = createVideoThumbnail(path, 20, 20);
                viewHolder.ivIcon.setImageBitmap(bitmap);
            }
            viewHolder.tvPath.setText(list.get(position));
        }

        return view;
    }
    class ViewHolder{
        ImageView ivIcon;
        TextView tvPath;
    }

    //获取视频缩略图
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    // 图片加载的类
    public static BitmapFactory.Options getHeapOpts(File file) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 数字越大读出的图片占用的heap必须越小，不然总是溢出
        if (file.length() < 20480) { // 0-20k
            opts.inSampleSize = 1;// 这里意为缩放的大小
        } else if (file.length() < 51200) { // 20-50k
            opts.inSampleSize = 2;
        } else if (file.length() < 307200) { // 50-300k
            opts.inSampleSize = 4;
        } else if (file.length() < 819200) { // 300-800k
            opts.inSampleSize = 6;
        } else if (file.length() < 1048576) { // 800-1024k
            opts.inSampleSize = 8;
        } else {
            opts.inSampleSize = 10;
        }
        return opts;
    }

}
