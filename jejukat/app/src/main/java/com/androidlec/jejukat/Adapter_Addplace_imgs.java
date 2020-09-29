package com.androidlec.jejukat;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class Adapter_Addplace_imgs extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ClipData clipData = null;
    private ArrayList<Uri> uris = null;
    private LayoutInflater inflater = null;
    private int cursor = 0;
    private int count = 0;

    public Adapter_Addplace_imgs(Context mContext, int layout, ArrayList<Uri> uris) {
        this.mContext = mContext;
        this.layout = layout;
        this.uris = uris;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uris.size();
    }

    @Override
    public Object getItem(int i) {
        return uris.get( i );
//        return clipData.getItemAt( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = inflater.inflate(this.layout, viewGroup, false);
        }
//        Log.v( "Current", "i: " + i + " count: " + count );
        ImageView imageView = view.findViewById( R.id.iv_ImageGridViewAdapter_image );
        imageView.setImageURI( uris.get( i ) );

        return view;
    }

    public void clear() {
        uris.clear();
    }
}
