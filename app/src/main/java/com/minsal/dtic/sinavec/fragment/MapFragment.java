package com.minsal.dtic.sinavec.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.CameraPosition;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener,LocationListener {

    private View viewrootView;
    private MapView mapView;
    private GoogleMap gmap;

    private FloatingActionButton fab;
    int MY_PERMISSION_LOCATION = 10;
    private Location currentLocation;
    private LocationManager locationManager;
    private Marker marker;
    private CameraPosition cameraZoom;

    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewrootView = inflater.inflate(R.layout.fragment_map, container, false);
        fab = (FloatingActionButton) viewrootView.findViewById(R.id.fab);

        fab.setOnClickListener(this);
        return viewrootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = viewrootView.findViewById(R.id.mapFragment);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //this.verificarGPS();

    }

    private boolean isGPSEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("Gps Signal")
                .setMessage("Desea activar el gps?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;
        locationManager=(LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);


        setUpMap();
        gmap.setMyLocationEnabled(false);
        //gmap.getUiSettings().setMyLocationButtonEnabled(false);
        /*Desabilita el boton que se crea en la
        interfaz*/
        /*Boton que se ejecuta al presionar el boton arriba cancelado*/
        /*gmap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(getContext(),"Mi posicion en mapa",Toast.LENGTH_LONG).show();
                return false;
            }
        });*/


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);



      }

    private void setUpMap() {
        gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (Build.VERSION.SDK_INT >= 23) {
            marshmallowGPSPremissionCheck();

        } else {
            enableMyLocation();

        }
    }

    private void marshmallowGPSPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_LOCATION);
        } else {
            enableMyLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        }else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }
    }
    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gmap.setMyLocationEnabled(true);
    }


    @Override
    public void onClick(View view) {
        if(!this.isGPSEnabled()){
            showInfoAlert();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(),"Changed ->"+location.getProvider(),Toast.LENGTH_LONG).show();
        //gmap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).draggable(true));

        if (marker == null){
            marker=gmap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).draggable(true));
        }else {
            marker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void zoomLocation(Location location){
        cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(),location.getLongitude()))
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();
        gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));

    }
}


/*
public class MapFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener{

    private View viewrootView;
    private MapView mapView;
    private GoogleMap gmap;

    private FloatingActionButton fab;

    public MapFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewrootView=inflater.inflate(R.layout.fragment_map, container, false);
        fab=(FloatingActionButton) viewrootView.findViewById(R.id.fab);

        fab.setOnClickListener(this);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        //this.verificarGPS();

    }

    public void verificarGPS(){
        try {
            int gpsSignal= Settings.Secure.getInt(getActivity().getContentResolver(),Settings.Secure.LOCATION_MODE);
            if (gpsSignal==0){
                //El gps no esta activado
                //Toast.makeText(getContext(),"Por favor active el GPS",Toast.LENGTH_LONG).show();
                showInfoAlert();
            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showInfoAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Gps Signal")
                .setMessage("Desea activar el gps?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",null)
                .show();
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


    @Override
    public void onClick(View view) {
        this.verificarGPS();
    }
}*/




