package com.androidlec.jejukat;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Activity_Detail_Place_Frag_Map extends Fragment implements OnMapReadyCallback {

    View view;

    GoogleMap gMap;
    MapView mapView;
    Geocoder geocoder;
    double lat;
    double lon;




    //상태저장해야함
    public static Activity_Detail_Place_Frag_Map newInstance(){
        // 프레그먼트 교체할때 필요함
        Activity_Detail_Place_Frag_Map fragMap = new Activity_Detail_Place_Frag_Map();
        return fragMap; // 어댑터랑 연결하려고
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // xml연동
        view = inflater.inflate(R.layout.tablayout_fragment_map_detailspost, container, false);

        //연결
        TextView address_basicinfo_frag_map_detailsPost_tv = view.findViewById(R.id.tv_detailsPost_frag_map_basicinfo_address);
        TextView tel_basicinfo_frag_map_detailsPost_tv = view.findViewById(R.id.tv_detailsPost_frag_map_basicinfo_tel);
        TextView homepage_basicinfo_frag_map_detailsPost_tv = view.findViewById(R.id.tv_detailsPost_frag_map_basicinfo_homepage);

        //setText
        address_basicinfo_frag_map_detailsPost_tv.setText(STATICDATA.ADDRESS);
        tel_basicinfo_frag_map_detailsPost_tv.setText(STATICDATA.TEL);
        homepage_basicinfo_frag_map_detailsPost_tv.setText(STATICDATA.HOMEPAGE);

        return view;
    }

    //구글지도
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        List<Address> list = null; // 위도 경도 저장
        String place = STATICDATA.ADDRESS;

        LatLng latLng = null;

        geocoder = new Geocoder( getActivity() );

        try {
            list = geocoder.getFromLocationName
                    (place, // 지역 이름
                            1); // 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();

            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
            if (list.size() == 0) {
                Log.v("Status: ", "해당되는 주소 정보는 없습니다" );
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
                latLng = new LatLng(lat, lon);

                Marker marker = gMap.addMarker( new MarkerOptions()
                        .position( latLng )
                        .title(place)
                        .snippet( "설명" )
                );
                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng,17) );
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map_detailpost);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }
}//--------
