package com.minsal.dtic.sinavec.CRUD.gotaGruesa.activityGotaGruesa;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolygonOptions;
import com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa.nuevaGotaGruesaFragment;
import com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa.seleccionProcedenciaDialogFragment;
import com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria.NuevaPesquisaFragment;
import com.minsal.dtic.sinavec.EntityDAO.ColvolCalve;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

import com.minsal.dtic.sinavec.EntityDAO.EstablecimientoClave;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class nuevaGotaGruesaActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener,nuevaGotaGruesaFragment.procedenciaDialogListener {

    private GoogleMap mMap;
    private DaoSession daoSession;
    Utilidades u;
    private CameraPosition cameraZoom;
    ArrayList<String> listaMunicipio=new ArrayList<String>();
    List<CtlMunicipio> municipios;
    ArrayList<String> listaCanton=new ArrayList<String>();
    List<CtlCanton> cantones;
    ArrayList<String> listaCaserios=new ArrayList<String>();
    List<CtlCaserio> caserios;
    ArrayList<String> listaLaboratorios=new ArrayList<String>();
    List<CtlEstablecimiento> laboratorios;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    List<Marker> misMarkers;
    Marker marker;

    Utilidades utilidades;

    private Spinner spMunicipio, spCanton, spCaserio;
    int depto = MainActivity.depto;
    TextView tvCountCriadero;
    Button btnBuscarCriaderoPes;
    long idCriadero, idCaserio;
    long idSibasi ;
    long idTablet ;
    long idUsuario;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_gota_gruesa);
        //Me permite regresar  a la actividad anterior
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        daoSession          =((MyMalaria)getApplicationContext()).getDaoSession();
        utilidades=new Utilidades(daoSession);

        prefs               = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        spMunicipio =(Spinner)findViewById(R.id.spMunicipioGota);
        spCanton =(Spinner)findViewById(R.id.spCantonGota);
        spCaserio =(Spinner)findViewById(R.id.spCaserioGota);
        tvCountCriadero     = (TextView)findViewById(R.id.tvCountCriadero);
        btnBuscarCriaderoPes= (Button)findViewById(R.id.btnBuscarCriaderoPes);
        idSibasi  = prefs.getLong("idSibasiUser",0);
        idTablet  = prefs.getLong("idTablet",0);
        idUsuario = prefs.getLong("idUser",0);
        u=new Utilidades(daoSession);
        u.fragment = 0;


       // Toast.makeText(getApplicationContext(),""+depto,Toast.LENGTH_LONG).show();

        //loadSpinerMun();
        listaCanton.add("Seleccione");
        listaCaserios.add("Seleccione");
        municipios=utilidades.loadspinnerMunicipio(depto);
        listaMunicipio=utilidades.obtenerListaMunicipio(municipios);
        laboratorios=utilidades.obtenerLaboratorios(idSibasi);
        listaLaboratorios=utilidades.obtenerListaEstablecimientoClave(laboratorios);

        adapter=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaMunicipio);
        adapter.notifyDataSetChanged();
        spMunicipio.setAdapter(adapter);
        adapter2=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaCanton);
        adapter2.notifyDataSetChanged();
        spCanton.setAdapter(adapter2);
        adapter3=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaCaserios);
        spCaserio.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();

        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    cantones=utilidades.loadSpinerCanton(municipios.get(i-1).getId());
                    listaCanton.clear();
                    listaCanton=utilidades.obetenerListaCantones(cantones);
                    adapter2=new ArrayAdapter
                            (adapterView.getContext(),android.R.layout.simple_list_item_1,listaCanton);
                    spCanton.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    spCanton.setSelection(0);
                }else{
                    listaCanton.clear();
                    listaCanton.add("Seleccione");
                    adapter2.notifyDataSetChanged();
                    spCanton.setSelection(0);
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    caserios=utilidades.loadSpinerCaserio(cantones.get(i-1).getId());
                    listaCaserios.clear();
                    listaCaserios=utilidades.obetenerListaCaserios(caserios);
                    adapter3=new ArrayAdapter
                            (adapterView.getContext(),android.R.layout.simple_list_item_1,listaCaserios);
                    spCaserio.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                }else{
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnBuscarCriaderoPes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                long idMunicipio = getIdMunicipioGota();
                long idCanton = getIdCantonGota();
                long idCaserio = getIdCaserioGota();
                if(idMunicipio!=0){
                    colvolMap((int)idMunicipio,(int)idCanton,(int)idCaserio);
                }else{
                    Toast.makeText(getApplicationContext(),"Seleccione un Municipio",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        moverCamaraDepartamento();
        //solo preparamos el mapa luego mostraremos los puntos al presionar el botin buscar
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ColvolCalve colvolClave=(ColvolCalve)marker.getTag();
               marker.showInfoWindow();
                nuevaGotaGruesaFragment dialog = new nuevaGotaGruesaFragment();
                Bundle datos=new Bundle();
                datos.putStringArrayList("listaEstClave",listaLaboratorios);
                datos.putLong("idSibasi",idSibasi);
                datos.putLong("idTablet",idTablet);
                datos.putLong("idUsuario",idUsuario);
                datos.putLong("idClave",colvolClave.getIdClave());
                datos.putString("nombreColvol",colvolClave.getPlColvol().getNombre());
                datos.putLong("idCaserioColvol",colvolClave.getPlColvol().getIdCaserio());
                dialog.setArguments(datos);
                dialog.show(getFragmentManager(), "dialog");
                return false;
            }

        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (marker!=null){
                    for(Marker marker : misMarkers) {
                        if(Math.abs(marker.getPosition().latitude - latLng.latitude) < 0.005 && Math.abs(marker.getPosition().longitude - latLng.longitude) < 0.005) {
                            marker.showInfoWindow();
                            //Toast.makeText(nuevaGotaGruesaActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show(); //do some stuff
                            break;
                        }
                    }

                }

            }
        });


    }

    public void colvolMap(int municipio,int canton,int caserio){
        Utilidades u = new Utilidades(daoSession);
        List<CtlPlCriadero> criaderos = u.loadCriaderosMap(municipio);

        List<ColvolCalve> clavesColvol = u.loadClavesColvolMap(municipio,canton,caserio);


        if (clavesColvol.size()>0){
            misMarkers=new ArrayList<Marker>();
            for (ColvolCalve c: clavesColvol){
                String id=String.valueOf(c.getId());
                marker=mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(c.getPlColvol().getLatitud()), Double.parseDouble(c.getPlColvol().getLongitud())))
                        .title(c.getPlColvol().getNombre()+"--"+c.getClave().getClave())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gota_sangre)));
                marker.setTag(c);
                marker.showInfoWindow();
                misMarkers.add(marker);
            }
            tvCountCriadero.setText(String.format("Total de colvol encontrados: %s", String.valueOf(clavesColvol.size())));
        }else{
            tvCountCriadero.setText("Sin colvol");
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
        spMunicipio.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public long getIdMunicipioGota() {
        int listIdMunicipio = spMunicipio.getSelectedItemPosition();
        int idMunicipio = 0;
        if (listIdMunicipio != 0) {
            idMunicipio = (int) (long) municipios.get(listIdMunicipio - 1).getId();
        }
        return idMunicipio;
    }
    public long getIdCantonGota() {
        int lisIdCaserio = spCanton.getSelectedItemPosition();
        int idCanton = 0;
        if (lisIdCaserio != 0) {
            idCanton = (int) (long) cantones.get(lisIdCaserio - 1).getId();
        }
        return idCanton;
    }
    public long getIdLaboratorioGota() {
        int lisIdCaserio = spCanton.getSelectedItemPosition();
        int idCanton = 0;
        if (lisIdCaserio != 0) {
            idCanton = (int) (long) cantones.get(lisIdCaserio - 1).getId();
        }
        return idCanton;
    }
    public long getIdCaserioGota() {
        int listIdCaserio = spCaserio.getSelectedItemPosition();
        int idCaserio = 0;
        if (listIdCaserio != 0) {
            idCaserio = (int) (long) caserios.get(listIdCaserio - 1).getId();
        }
        return idCaserio;
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
    public int getSemana() {
        int semana = 0;
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            String sqlQUERY = "SELECT semana FROM ctl_semana_epi where '" + strDate + "' BETWEEN fecha_inicio " +
                    "and fecha_fin";
            Cursor c = daoSession.getDatabase().rawQuery(sqlQUERY, null);
            if (c.moveToFirst()) {
                do {
                    semana = c.getInt(0);

                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return semana;
    }

    public void customToadSuccess(Context context, String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toad_exito,
                (ViewGroup) findViewById(R.id.custom_toast_container_exito));
        TextView text = (TextView) layout.findViewById(R.id.tvToasExito);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    @Override
    public void onBackPressed() {
        Intent list =new Intent(getApplicationContext(),ListGotaGruesaActivity.class);
        startActivity(list);
        finish();
    }

}
