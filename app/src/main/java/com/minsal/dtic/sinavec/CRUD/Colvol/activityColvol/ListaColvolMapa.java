package com.minsal.dtic.sinavec.CRUD.Colvol.activityColvol;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.minsal.dtic.sinavec.CRUD.Colvol.fragmentColvol.verColvolMapDialogFragment;
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.verCriaderoMapDialogFragment;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MapOfflineActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.VerMapsOffline;
import com.minsal.dtic.sinavec.tools.GoogleMapOfflineTileProvider;
import com.minsal.dtic.sinavec.utilidades.Utilidades;
import com.minsal.dtic.sinavec.utilidades.Validator;

import java.util.ArrayList;
import java.util.List;

public class ListaColvolMapa extends AppCompatActivity implements OnMapReadyCallback,LocationListener,verColvolMapDialogFragment.colvolDialogListener {
    private GoogleMap mMap;
    private DaoSession daoSession;
    private List<CtlPlCriadero> criaderosMap;
    Utilidades u;
    private SharedPreferences prefs;
    private CameraPosition cameraZoom;
    ArrayList<LatLng> locations = new ArrayList();
    ArrayList<String> nombres = new ArrayList();
    private List<CtlMunicipio> municipios;
    private ArrayList<String> listaMunicipio;
    private Spinner spMunicipioColvol;
    int depto = MainActivity.depto;
    TextView tvCountColvol;
    Button btnBuscarColvol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_colvol_mapa);

        //Me permite regresar  a la actividad anterior
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Utilidades.fragment=2;

        daoSession =((MyMalaria)getApplicationContext()).getDaoSession();
        prefs      = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        spMunicipioColvol  =(Spinner)findViewById(R.id.spMunicipioColvol);

        tvCountColvol = (TextView)findViewById(R.id.tvCountCriadero);
        btnBuscarColvol = (Button)findViewById(R.id.btnBuscarCriaderoPes);
        u=new Utilidades(daoSession);
        boolean countTiles = Validator.hasSaveMap(getApplication());
        if (!countTiles){
            goDowloadMap();
        }
        loadSpinerMun();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapListCriadero);

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        moverCamaraDepartamento();
        //solo preparamos el mapa luego mostraremos los puntos al presionar el botin buscar
        // se muestra el mapa que esta en la base de datos
        if (!Validator.isNetDisponible(this)){
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE); // tiene que se type none para que funcione el de la BD
            mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new GoogleMapOfflineTileProvider(this)).zIndex(-100)).clearTileCache();

        }else{
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                PlColvol col = (PlColvol) marker.getTag();
                verColvolMapDialogFragment dialog = new verColvolMapDialogFragment();
                Bundle datos=new Bundle();
                datos.putLong("id",col.getId());
                dialog.setArguments(datos);
                dialog.show(getFragmentManager(), "dialog");
                return false;
            }
        });
        btnBuscarColvol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mMap.clear();
                long idMunicipio = getIdMunicipioPes();
                colvolMap((int)idMunicipio);

            }
        });


    }

    public void colvolMap(int municipio){
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new GoogleMapOfflineTileProvider(this)).zIndex(-100)).clearTileCache();
        Utilidades u = new Utilidades(daoSession);
        List<PlColvol> colvols = u.loadColvolMap(municipio);
        if (colvols.size()>0){
            for (PlColvol c: colvols){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(c.getLatitud()), Double.parseDouble(c.getLongitud())))
                        .title(c.getNombre()).snippet("sdf")).setTag(c);
            }
            tvCountColvol.setText("Total de Colvol encontrados:"+String.valueOf(colvols.size()));
        }else{
            tvCountColvol.setText("No se encontraron Colvol");
        }

    }

    private void moverCamaraDepartamento(){
        String elUser = prefs.getString("user", "");
        int idDepto=u.deptoUser(elUser);
        List<Double> coordenadasDepto=u.getCoordenadasDepartamento(idDepto);
        cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(coordenadasDepto.get(0),coordenadasDepto.get(1)))
                .zoom(10)
                .bearing(5)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    private void loadSpinerMun() {
        Utilidades u   = new Utilidades(daoSession);
        municipios     = u.loadspinnerMunicipio(depto);
        listaMunicipio = u.getMunicipioTodos(municipios);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listaMunicipio);
        spMunicipioColvol.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public long getIdMunicipioPes() {
        int listIdMunicipio = spMunicipioColvol.getSelectedItemPosition();
        int idMunicipio = 0;
        if (listIdMunicipio != 0) {
            idMunicipio = (int) (long) municipios.get(listIdMunicipio - 1).getId();
        }
        return idMunicipio;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
    public void  goDowloadMap(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaColvolMapa.this);
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
