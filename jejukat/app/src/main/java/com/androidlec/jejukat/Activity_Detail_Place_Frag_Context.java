package com.androidlec.jejukat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Activity_Detail_Place_Frag_Context extends Fragment {

    private View view;


    //상태저장해야함
    public static Activity_Detail_Place_Frag_Context newInstance(){
        // 프레그먼트 교체할때 필요함
        Activity_Detail_Place_Frag_Context fragContext = new Activity_Detail_Place_Frag_Context();
        return fragContext; // 어댑터랑 연결하려고
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // xml연동
        view = inflater.inflate(R.layout.tablayout_fragment_context_detailspost, container, false);
        TextView context = view.findViewById(R.id.tv_detailsPost_frag_context);
        context.setText(STATICDATA.CONTEXT);
        return view;


    }




}//-------------
