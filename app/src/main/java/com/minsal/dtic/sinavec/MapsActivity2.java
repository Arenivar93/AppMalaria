package com.minsal.dtic.sinavec;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.tools.GoogleMapOfflineTileProvider;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowLongClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    Utilidades u;
    SharedPreferences prefs;
    private CharSequence itemTitle;
    private String[] tagTitles;
    private CharSequence activityTitle;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        DaoSession daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        u = new Utilidades(daoSession);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        inicializarcontroles();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        moverCamaraDepartamento();
        mMap.getUiSettings().setMapToolbarEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }else{
            mMap.setMyLocationEnabled(false);
        }
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new GoogleMapOfflineTileProvider(this)).zIndex(-100)).clearTileCache();
        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(),String.valueOf(latLng),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void moverCamaraDepartamento() {
        String elUser = prefs.getString("user", "");
        int idDepto = u.deptoUser(elUser);
        List<Double> coordenadasDepto = u.getCoordenadasDepartamento(idDepto);
        CameraPosition cameraZoom = new CameraPosition.Builder()
                .target(new LatLng(coordenadasDepto.get(0), coordenadasDepto.get(1)))
                .zoom(10)
                .bearing(5)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    public void inicializarcontroles() {
        itemTitle = activityTitle = getTitle();
    }



}
