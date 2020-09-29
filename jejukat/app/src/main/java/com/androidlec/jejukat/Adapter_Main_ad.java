package com.androidlec.jejukat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class Adapter_Main_ad extends PagerAdapter {

    //필드
    private Context mContext;
    private ArrayList<Bean_Main> imageList;

    //생성자
    public Adapter_Main_ad(Context mContext, ArrayList<Bean_Main> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @NonNull
    @Override // 데이터 리스트에서 인자로 넘어온 position에 해당하는 아이템 항목에 대한 페이지를 생성.
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpage_main_ad, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.img_vp_ad);
        imageView.setImageResource(imageList.get(position%imageList.size()).getImage());
        container.addView(v);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link =imageList.get(position%imageList.size()).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(link));
                view.getContext().startActivity(intent);
            }
        });
        return v;
    }

    @Override // Adapter가 관리하는 데이터 리스트의 총 갯수
    public int getCount() {
        return Integer.MAX_VALUE;
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
