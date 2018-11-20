package com.minsal.dtic.sinavec;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.minsal.dtic.sinavec.DataBaseTile.SQLiteMapCache;
import com.minsal.dtic.sinavec.DataBaseTile.TileCache;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MapOfflineActivity  extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCircleClickListener  {
    private HashMap<Circle, LatLng> areas;
    private SharedPreferences prefs;

    private GoogleMap mMap;
    Utilidades u;
    private SQLiteMapCache mapDatabase;
    List<LatLng> coordenadaPoligono;
    ImageView imSaveMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_offline);
        prefs= getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        DaoSession daoSession =((MyMalaria)getApplicationContext()).getDaoSession();
        u=new Utilidades(daoSession);
        imSaveMap = (ImageView)findViewById(R.id.imSaveMap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);

        mapFragment.getMapAsync(this);
        areas = new HashMap<>();
        mapDatabase = new SQLiteMapCache(this);


    }
    public void save(View v) {
        TileCache downloader = new TileCache(this, 10000, 15, 20);
        mapDatabase.deleteAllTile(); //Borra los datos de la tabla de Tile
        downloader.execute(areas.values().toArray());

    }

    @Override
    public void onCircleClick(Circle circle) {
      //  mapDatabase.deleteSyncPoint(circle.getCenter());
        //areas.remove(circle);
        //circle.remove();
        Toast.makeText(getApplicationContext(),"clicadasd",Toast.LENGTH_LONG).show();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moverCamaraDepartamento();
        ponerPuntosSync();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //Toast.makeText(getApplicationContext(),"clic",Toast.LENGTH_LONG).show();
              preview(latLng);
                //vamos a recoger los puntos que el usuario toco en la pantalla
              //  coordenadaPoligono.add(latLng);

            }
        });
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                LatLng cir = circle.getCenter();
                Toast.makeText(getApplicationContext(),String.valueOf(cir),Toast.LENGTH_LONG).show();

            }
        });
        addPolin();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    private void moverCamaraDepartamento(){
        String elUser = prefs.getString("user", "");
        int idDepto=u.deptoUser(elUser);
        List<Double> coordenadasDepto=u.getCoordenadasDepartamento(idDepto);
        CameraPosition cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(coordenadasDepto.get(0),coordenadasDepto.get(1)))
                .zoom(10)
                .bearing(5)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));
    }
    /**
     * Añade un circulo en el mapa con un radio de 500 metros color rojo traslucido, y ademas guarda
     * en la base de datos el centro como punto de sincronización y lo añade a las áreas a descargar.
     * @param center centro del circulo
     */
    private void preview(LatLng center) {
        int radius = 10000;
        if (center != null) {
            areas.put(mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(radius)
                    .clickable(true)
                    .strokeColor(Color.argb(94, 255, 0, 0))
                    .fillColor(Color.argb(94, 255, 0, 0))), center);
            mapDatabase.saveSyncPoint(center);
        }
    }
    /** METODDOS **/

    /**
     * Pone los puntos de soncronización que se encuentran guardados en la Base de Datos.
     */
    private void ponerPuntosSync() {
        if (mapDatabase == null) {
            mapDatabase = new SQLiteMapCache(this);
        }

        ArrayList<LatLng> syncPoint = mapDatabase.getSyncPoints();
        Iterator i = syncPoint.iterator();
        while (i.hasNext()) {

            LatLng center = (LatLng) i.next();
            areas.put(mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(500)
                    .clickable(true)
                    .strokeColor(Color.argb(94, 255, 0, 0))
                    .fillColor(Color.argb(94, 255, 0, 0))), center);
        }
    }
    public void addPolin(){
        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(13.743664, -90.131320))
                .add(new LatLng(13.833092, -90.112246))
                .add(new LatLng(14.061549, -89.834857))
                .add(new LatLng(14.034928, -89.735845))
                .add(new LatLng(13.976264, -89.698708))
                .add(new LatLng(13.902913, -89.733020))  // North of the previous point, but at the same longitude
                .add(new LatLng(13.888216, -89.785212))  // Same latitude, and 30km to the west
                .add(new LatLng(13.836221, -89.774186))  // Same longitude, and 16km to the south
                .add(new LatLng(13.664136, -89.837223))  // Same longitude, and 16km to the south
                .add(new LatLng(13.703732, -89.919153))  // Same longitude, and 16km to the south
                .add(new LatLng(13.665015, -89.956200))  // Same longitude, and 16km to the south
                .add(new LatLng(13.743664, -90.131320)); // Closes the polyline.

// Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptions);
    }
}
