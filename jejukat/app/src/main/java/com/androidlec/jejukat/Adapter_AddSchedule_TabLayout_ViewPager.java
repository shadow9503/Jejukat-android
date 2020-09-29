package com.androidlec.jejukat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Adapter_AddSchedule_TabLayout_ViewPager extends FragmentPagerAdapter {


    public Adapter_AddSchedule_TabLayout_ViewPager(@NonNull FragmentManager fm) {
        super(fm);
    }


    // 프래그먼트 교를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) { //여기서 불러들임

        switch (position) {   // fragment 위치에 따라서 어떤 뷰를 띄워줄 것인지 구성함
            case 0: //지도
                return Activity_AddSchedule_FragmentPlaces.newInstance(); //  화면띄워줌
            case 1: //상세내용
                return Activity_AddSchedule_FragmentSchedule.newInstance();
            default:
                return null;
        }
    }

    //getItem갯수 명시 해줘야함
    @Override
    public int getCount() {
        return 2; // 3개 뷰페이저
    }

    // 상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "방문 리스트";
            case 1:
                return "일정 상세";
            default:
                return null;
        }
    }






}//-------------------
