package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MapOfflineActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.VerMapsOffline;
import com.minsal.dtic.sinavec.tools.GoogleMapOfflineTileProvider;
import com.minsal.dtic.sinavec.utilidades.Utilidades;
import com.minsal.dtic.sinavec.utilidades.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class pesquisaLarvaria extends AppCompatActivity implements OnMapReadyCallback,LocationListener
                                                                    ,NuevaPesquisaFragment.OnFragmentInteractionListener{

    private GoogleMap mMap;
    private DaoSession daoSession;
    Utilidades u;
    private CameraPosition cameraZoom;
    private List<CtlMunicipio> municipios;
    private ArrayList<String> listaMunicipio;
    private Spinner spMunicipioPesquisa;
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
        setContentView(R.layout.activity_pesquisa_larvaria);
        daoSession          =((MyMalaria)getApplicationContext()).getDaoSession();
        prefs               = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        spMunicipioPesquisa =(Spinner)findViewById(R.id.spMunicipioPesquisa);
        tvCountCriadero     = (TextView)findViewById(R.id.tvCountCriadero);
        btnBuscarCriaderoPes= (Button)findViewById(R.id.btnBuscarCriaderoPes);
        idSibasi  = prefs.getLong("idSibasiUser",0);
        idTablet  = prefs.getLong("idTablet",0);
        idUsuario = prefs.getLong("idUser",0);
        u=new Utilidades(daoSession);
        u.fragment = 0;
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        boolean countTiles = Validator.hasSaveMap(getApplication());
        if (!countTiles){
            goDowloadMap();
        }

        loadSpinerMun();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        btnBuscarCriaderoPes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mMap.clear();
                mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new GoogleMapOfflineTileProvider(getApplicationContext())).zIndex(-100)).clearTileCache();
                long idMunicipio = getIdMunicipioPes();
                criaderosMap((int)idMunicipio);

            }
        });

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
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new GoogleMapOfflineTileProvider(this)).zIndex(-100)).clearTileCache();
        moverCamaraDepartamento();
        mMap.setMinZoomPreference(1);
        mMap.setMaxZoomPreference(15);
        //solo preparamos el mapa luego mostraremos los puntos al presionar el botin buscar

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CtlPlCriadero cria = (CtlPlCriadero) marker.getTag();
                idCriadero=cria.getId();
                idCaserio=cria.getCtlCaserio().getId();
                NuevaPesquisaFragment dialog = new NuevaPesquisaFragment().newInstance(cria.getId(),cria.getNombre());
                dialog.show(getFragmentManager(),"dialog");
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
            tvCountCriadero.setText(String.format("Total de criaderos enontrados: %s", String.valueOf(criaderos.size())));
        }else{
            tvCountCriadero.setText("No se encontraron criaderos registrados con coordenadas");
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
        mMap.setMaxZoomPreference(15);
        mMap.setMinZoomPreference(1);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void OnDialogPositiveClick(DialogFragment dialog, int anopheles12, int anopheles34,
                                      int culicino12, int culicino34, int pupa, int cucharonada,
                                      float largo, float ancho) {
       Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String prueba = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentTime);
        String fecha = dateFormat.format(currentTime);
        float indice;
        if (cucharonada>0 && anopheles34>0){
            indice = anopheles34/cucharonada;
        }else {indice=0;}

        try {
                Date fec = dateFormat.parse(fecha);
                int semanaActual = getSemana();
                PlPesquisaLarvariaDao pesDao = daoSession.getPlPesquisaLarvariaDao();
                PlPesquisaLarvaria pes = new PlPesquisaLarvaria();
                pes.setAnophelesUno(anopheles12);
                pes.setAnophelesDos(anopheles34);
                pes.setCulicinosUno(culicino12);
                pes.setCulicinosDos(culicino34);
                pes.setAncho(ancho);
                pes.setLargo(largo);
                pes.setNumeroCucharonada(cucharonada);
                pes.setPupa(pupa);
                pes.setIdCriadero(idCriadero);
                pes.setFechaHoraReg(fecha);
                pes.setIdEstado(1);
                pes.setFechaHoraMod(fecha);// se debe quitar not null
                pes.setFecha(currentTime);
                pes.setIdSemanaEpidemiologica(semanaActual);
                pes.setIdUsuarioReg(idUsuario);
                pes.setIdCaserio(idCaserio);//el criadero esta amarradp a un caserio navegar a el
                pes.setIdSibasi(idSibasi);
                pes.setIdTablet(idTablet);
                pes.setIndiceLarvario(indice);
                pes.setEstado_sync(1);
                pesDao.insert(pes);




            customToadSuccess(getApplicationContext(),"Pesquisa Larvaria se guardo con éxito");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

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
        Intent list =new Intent(getApplicationContext(),ListPesquisaActivity.class);
        startActivity(list);
        finish();
    }
    public void  goDowloadMap(){
        AlertDialog.Builder builder = new AlertDialog.Builder(pesquisaLarvaria.this);
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
