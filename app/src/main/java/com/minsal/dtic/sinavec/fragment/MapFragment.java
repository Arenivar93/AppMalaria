package com.minsal.dtic.sinavec.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback{

    private View viewrootView;
    private MapView mapView;
    private GoogleMap gmap;

    public MapFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewrootView=inflater.inflate(R.layout.fragment_map, container, false);
        return viewrootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView=viewrootView.findViewById(R.id.mapFragment);

        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        this.verificarGPS();

    }

    @Override
    public void onResume() {
        super.onResume();
        this.verificarGPS();

    }

    public void verificarGPS(){
        try {
            int gpsSignal= Settings.Secure.getInt(getActivity().getContentResolver(),Settings.Secure.LOCATION_MODE);
            if (gpsSignal==0){
                //El gps no esta activado
                Toast.makeText(getContext(),"Por favor active el GPS",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap=googleMap;
        LatLng place=new LatLng(13.71466708231014, -89.20727916061878);
        gmap.addMarker(new MarkerOptions().position(place).title("Casa ues").draggable(true));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,15));
        gmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        gmap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.hideInfoWindow();
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(getContext(),"Drag Click on: \n"+
                        "Lat:" + marker.getPosition().latitude + "\n" +
                        "Lon:" + marker.getPosition().longitude, Toast.LENGTH_SHORT).show();

                marker.showInfoWindow();
            }
        });

    }


}
