package com.minsal.dtic.sinavec;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.minsal.dtic.sinavec.CRUD.capturaAnopheles.CapturaAnopheles;
import com.minsal.dtic.sinavec.CRUD.capturaAnopheles.DetalleCapturaSemanaActivity;
import com.minsal.dtic.sinavec.DataBaseTile.SQLiteMapCache;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.tools.GoogleMapOfflineTileProvider;
import com.minsal.dtic.sinavec.utilidades.Utilidades;
import com.minsal.dtic.sinavec.utilidades.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class VerMapsOffline extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowLongClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    Utilidades u;
    SharedPreferences prefs;
    private CharSequence itemTitle;
    private String[] tagTitles;
    private CharSequence activityTitle;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private HashMap<Circle, LatLng> areas;
    private SQLiteMapCache mapDatabase;
    //TextView textview_zoom_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        areas = new HashMap<>();
        mapDatabase = new SQLiteMapCache(this);
        DaoSession daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        u = new Utilidades(daoSession);
        //textview_zoom_actual = (TextView)findViewById(R.id.textview_zoom_actual);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        inicializarcontroles();
        mapDatabase = new SQLiteMapCache(getApplicationContext());
        Validator validator = new Validator();

        boolean countTiles = Validator.hasSaveMap(getApplication());
        if (!countTiles){
            goDowloadMap();
        }

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
        mMap.setMaxZoomPreference(15);
        mMap.setMinZoomPreference(1);
        moverCamaraDepartamento();
        mMap.getUiSettings().setMapToolbarEnabled(false);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS + 1);
        } else {
            mMap.setMyLocationEnabled(true);
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
        ponerPuntosSync();



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

    private void ponerPuntosSync() {
        if (mapDatabase == null) {
            mapDatabase = new SQLiteMapCache(this);
        }

        ArrayList<LatLng> syncPoint = mapDatabase.getSyncPoints();
        Log.i("ahuachapan", String.valueOf(syncPoint));
        Iterator i = syncPoint.iterator();
        while (i.hasNext()) {

            LatLng center = (LatLng) i.next();
            areas.put(mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(7000)
                    .clickable(true)
                    .strokeColor(Color.argb(94, 255, 0, 0))
                    .fillColor(Color.argb(94, 255, 0, 0))), center);
        }
    }
    public void  goDowloadMap(){
            AlertDialog.Builder builder = new AlertDialog.Builder(VerMapsOffline.this);
            builder.setMessage(Html.fromHtml("<font color='#FF0000'><b>Primero debe descargar el mapa!!</b></font>"))
                    .setNegativeButton(Html.fromHtml("Cancelar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    })
                    .setPositiveButton(Html.fromHtml("Descargar Ahora"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MapOfflineActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setCancelable(false);
            //.create().show();
            AlertDialog a = builder.create();
            a.show();
            Button btnPositivo = a.getButton(DialogInterface.BUTTON_POSITIVE);
            btnPositivo.setTextColor(Color.RED);
            Button btnNegativo = a.getButton(DialogInterface.BUTTON_NEGATIVE);
            btnNegativo.setTextColor(Color.GREEN);

    }



}
