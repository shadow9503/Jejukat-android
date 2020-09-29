package com.androidlec.jejukat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Place_Img extends PagerAdapter {

    //필드
    private Context mContext;
    private ArrayList<String> imageList;

    //생성자
    public Adapter_Place_Img(Context mContext, ArrayList<String> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @NonNull
    @Override // 데이터 리스트에서 인자로 넘어온 position에 해당하는 아이템 항목에 대한 페이지를 생성.
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpager_images_layout_detailspost,null);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_detailspost_images);

        if (imageList.size()==0){
            Glide.with(mContext).load("http://119.207.169.213:8080//ftp/jejukat_img_null.png").into(imageView);
        }else{
        Glide.with(mContext).load("http://" + imageList.get(position)).into(imageView);}



        container.addView(v);
        return v;
    }



    @Override // Adapter가 관리하는 데이터 리스트의 총 갯수
    public int getCount() {
        if(imageList.size()==0){
            return 1;
        }else{
            return imageList.size();
        }
    }

    @Override // Adapter가 관리하는 데이터 리스트에서 인자로 넘어온 position에 해당하는 데이터 항목을 생성된 페이지를 제거
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override //페이지가 특정 키와 연관되는지 체크
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }
}
