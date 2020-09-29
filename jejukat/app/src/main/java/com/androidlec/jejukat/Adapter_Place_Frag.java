package com.androidlec.jejukat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Adapter_Place_Frag extends FragmentPagerAdapter {


    public Adapter_Place_Frag(@NonNull FragmentManager fm) {
        super(fm);
    }


    // 프래그먼트 교를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) { //여기서 불러들임

        switch (position) {   // fragment 위치에 따라서 어떤 뷰를 띄워줄 것인지 구성함
            case 0: //지도
                return Activity_Detail_Place_Frag_Map.newInstance(); //  화면띄워줌
            case 1: //상세내용
                return Activity_Detail_Place_Frag_Context.newInstance();
            case 2:
                return Activity_Detail_Place_Frag_Cmt.newInstance();
            default:
                return null;
        }
    }

    //getItem갯수 명시 해줘야함
    @Override
    public int getCount() {
        return 3; // 3개 뷰페이저
    }

    // 상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "지도";
            case 1:
                return "상세내용";
            case 2:
                return "댓글";
            default:
                return null;
        }
    }





}//-------------------
