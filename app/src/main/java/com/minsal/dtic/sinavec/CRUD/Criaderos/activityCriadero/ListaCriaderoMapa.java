package com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.verCriaderoMapDialogFragment;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class ListaCriaderoMapa extends AppCompatActivity implements OnMapReadyCallback,LocationListener,verCriaderoMapDialogFragment.criaderoDialogListener {
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
    private Spinner spMunicipioPesquisa;
    int depto = MainActivity.depto;
    TextView tvCountCriadero;
    Button btnBuscarCriaderoPes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_criadero_mapa);
        //Me permite regresar  a la actividad anterior
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Utilidades.fragment=1;

        daoSession =((MyMalaria)getApplicationContext()).getDaoSession();
        prefs      = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        spMunicipioPesquisa  =(Spinner)findViewById(R.id.spMunicipioPesquisa);

        //spMunicipioPesquisa.getBackground().setColorFilter(getResources().getColor(R.color.colorButtonSiguiente), PorterDuff.Mode.SRC_ATOP);


        tvCountCriadero      = (TextView)findViewById(R.id.tvCountCriadero);
        btnBuscarCriaderoPes = (Button)findViewById(R.id.btnBuscarCriaderoPes);
        u=new Utilidades(daoSession);
        loadSpinerMun();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapListCriadero);

        mapFragment.getMapAsync(this);
        btnBuscarCriaderoPes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mMap.clear();
                long idMunicipio = getIdMunicipioPes();
                criaderosMap((int)idMunicipio);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        moverCamaraDepartamento();
        //solo preparamos el mapa luego mostraremos los puntos al presionar el botin buscar

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CtlPlCriadero cria = (CtlPlCriadero) marker.getTag();
                verCriaderoMapDialogFragment dialog = new verCriaderoMapDialogFragment();
                Bundle datos=new Bundle();
                datos.putLong("id",cria.getId());
                dialog.setArguments(datos);
                dialog.show(getFragmentManager(), "dialog");
                return false;
            }
        });


    }

    public void criaderosMap(int municipio){
        Utilidades u = new Utilidades(daoSession);
        List<CtlPlCriadero> criaderos = u.loadCriaderosMap(municipio);
        if (criaderos.size()>0){
            for (CtlPlCriadero c: criaderos){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(c.getLatitud()), Double.parseDouble(c.getLongitud())))
                        .title(c.getNombre())).setTag(c);
            }
            tvCountCriadero.setText("Total de criaderos enontrados:"+String.valueOf(criaderos.size()));
        }else{
            tvCountCriadero.setText("No se encontraron criaderos");
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
        spMunicipioPesquisa.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public long getIdMunicipioPes() {
        int listIdMunicipio = spMunicipioPesquisa.getSelectedItemPosition();
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
}
