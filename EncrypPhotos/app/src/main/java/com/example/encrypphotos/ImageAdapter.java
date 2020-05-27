package com.example.encrypphotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

public class ImageAdapter extends BaseAdapter{

    private Context mContext;
    public File[] fileImages;
    public String[] filesPath;
    public String[] filesName;

    public ImageAdapter(Context mContext, File[] fileImages,String[] filesPath, String[] filesName) {
        this.mContext = mContext;
        this.fileImages = fileImages;
        this.filesPath = filesPath;
        this.filesName = filesName;
    }


    @Override
    public int getCount() {
        return filesName.length;
    }

    @Override
    public Object getItem(int position) {
        return fileImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(mContext);
        imageView.setImageURI(Uri.fromFile(fileImages[position]));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(340,350));
        return imageView;
    }

}
