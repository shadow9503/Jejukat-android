package com.androidlec.jejukat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class Adapter_AddSchedule_GridView extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private LayoutInflater inflater = null;

    public Adapter_AddSchedule_GridView(Context mContext, int layout) {
        this.mContext = mContext;
        this.layout = layout;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (null != STATICDATA2.URIS ? STATICDATA2.URIS.size() : 0);

    }

    @Override
    public Object getItem(int i) {
        return STATICDATA2.URIS.get( i );
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
        ImageView imageView = view.findViewById( R.id.iv_addchedule_ImageGridViewAdapter_image );
        imageView.setImageURI( STATICDATA2.URIS.get( i ) );

        return view;
    }

    public void clearItem() {
        STATICDATA2.URIS.clear();
    }
}
