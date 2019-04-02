package com.minsal.dtic.sinavec;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.minsal.dtic.sinavec.DataBaseTile.SQLiteMapCache;
import com.minsal.dtic.sinavec.DataBaseTile.TileCache;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.utilidades.Utilidades;
import com.minsal.dtic.sinavec.utilidades.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class MapOfflineActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCircleClickListener {
    private SharedPreferences prefs;

    private GoogleMap mMap;
    Utilidades u;
    private HashMap<Circle, LatLng> areas;
    private SQLiteMapCache mapDatabase;
    List<LatLng> coordenadaPoligono;
    TextView tvzoom_descarga;
    ImageView imSaveMap,imcancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_offline);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        DaoSession daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        u = new Utilidades(daoSession);
        imSaveMap = (ImageView) findViewById(R.id.imSaveMap);
        imcancelar = (ImageView) findViewById(R.id.cancel_button);
        tvzoom_descarga = (TextView) findViewById(R.id.tvzoom_descarga);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);

        mapFragment.getMapAsync(this);
        areas = new HashMap<>();
        mapDatabase = new SQLiteMapCache(getApplicationContext());
        boolean internet = Validator.isNetDisponible(getApplicationContext());
        if (!internet) {
            hasInternet();
        }
        imcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    public void save(View v) {
        TileCache downloader = new TileCache(this, 10000, 1, 13);
        mapDatabase.deleteAllTile(); //Borra los datos de la tabla de Tile
        downloader.execute(areas.values().toArray());

    }

    @Override
    public void onCircleClick(Circle circle) {

        //  Toast.makeText(getApplicationContext(),"clicadasd",Toast.LENGTH_LONG).show();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        moverCamaraDepartamento();
        int puntos = countPoint();
        if (puntos > 0) {
            ponerPuntosSync();
        } else {
            poblarPuntoDepto(MainActivity.depto);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //Toast.makeText(getApplicationContext(),"clic",Toast.LENGTH_LONG).show();
                preview(latLng);
            }
        });
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                mapDatabase.deleteSyncPoint(circle.getCenter());
                areas.remove(circle);
                circle.remove();
                LatLng cir = circle.getCenter();
                Toast.makeText(getApplicationContext(), String.valueOf(cir), Toast.LENGTH_LONG).show();
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                float zoom = mMap.getCameraPosition().zoom;
                tvzoom_descarga.setText(String.valueOf(zoom)+ "/15 ");
            }
        });

    }

    @Override
    public void onMapClick(LatLng latLng) {

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

    /**
     * Añade un circulo en el mapa con un radio de 500 metros color rojo traslucido, y ademas guarda
     * en la base de datos el centro como punto de sincronización y lo añade a las áreas a descargar.
     *
     * @param center centro del circulo
     */
    private void preview(LatLng center) {
        int radius = 7000;
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
                    .radius(7000)
                    .clickable(true)
                    .strokeColor(Color.argb(94, 255, 0, 0))
                    .fillColor(Color.argb(94, 255, 0, 0))), center);
        }
    }



    public int countPoint() {
        return mapDatabase.countPoint();
    }

    public void poblarPuntoDepto(int depto) {

        switch (depto) {
            case 1:
                List<LatLng> cooAhuachapan = getPoinstInitials.puntosAhuachapan();
                for (int i = 0; i < cooAhuachapan.size(); i++) {
                    preview(cooAhuachapan.get(i));

                }
                break;
            case 2:
                List<LatLng> cooSA = getPoinstInitials.puntosSantaAna();
                for (int i = 0; i < cooSA.size(); i++) {
                    preview(cooSA.get(i));

                }
                break;
            case 3:
                List<LatLng> cooSO = getPoinstInitials.puntosSonsonate();
                for (int i = 0; i < cooSO.size(); i++) {
                    preview(cooSO.get(i));
                }
                break;
            case 4:
                List<LatLng> cooCH = getPoinstInitials.puntosChalate();
                for (int i = 0; i < cooCH.size(); i++) {
                    preview(cooCH.get(i));
                }
                break;
            case 5:
                List<LatLng> cooLL = getPoinstInitials.puntosLibertad();
                for (int i = 0; i < cooLL.size(); i++) {
                    preview(cooLL.get(i));
                }
                break;
            case 6:
                List<LatLng> cooSS = getPoinstInitials.puntosSanSalvador();
                for (int i = 0; i < cooSS.size(); i++) {
                    preview(cooSS.get(i));
                }
                break;
            case 7:
                List<LatLng> cooCU = getPoinstInitials.puntosCuscatlan();
                for (int i = 0; i < cooCU.size(); i++) {
                    preview(cooCU.get(i));
                }
                break;
            case 8:
                List<LatLng> cooLP = getPoinstInitials.puntosLaPaz();
                for (int i = 0; i < cooLP.size(); i++) {
                    preview(cooLP.get(i));
                }
                break;
            case 9:
                List<LatLng> cooCA = getPoinstInitials.puntosCabanas();
                for (int i = 0; i < cooCA.size(); i++) {
                    preview(cooCA.get(i));
                }
                break;
            case 10:
                List<LatLng> cooSV = getPoinstInitials.puntosSanVicente();
                for (int i = 0; i < cooSV.size(); i++) {
                    preview(cooSV.get(i));
                }
                break;
            case 11:
                List<LatLng> cooUS = getPoinstInitials.puntosUsulutan();
                for (int i = 0; i < cooUS.size(); i++) {
                    preview(cooUS.get(i));
                }
                break;
            case 12:
                List<LatLng> cooSM = getPoinstInitials.puntosSanMiguel();
                for (int i = 0; i < cooSM.size(); i++) {
                    preview(cooSM.get(i));
                }
                break;
            case 13:
                List<LatLng> cooMO = getPoinstInitials.puntosMorazan();
                for (int i = 0; i < cooMO.size(); i++) {
                    preview(cooMO.get(i));
                }
                break;
            default:
                List<LatLng> cooLU = getPoinstInitials.puntosUnion();
                for (int i = 0; i < cooLU.size(); i++) {
                    preview(cooLU.get(i));
                }
        }
    }

    public void hasInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapOfflineActivity.this);
        builder.setMessage(Html.fromHtml("<font color='#FF0000'>" +
                "<b>No posee acceso a internet para descargar!</b></font>"))
                .setNegativeButton(Html.fromHtml("Cancelar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
