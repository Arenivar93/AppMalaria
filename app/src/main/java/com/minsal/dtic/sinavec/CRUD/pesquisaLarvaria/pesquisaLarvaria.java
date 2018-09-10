package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.minsal.dtic.sinavec.CRUD.capturaAnopheles.CapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class pesquisaLarvaria extends AppCompatActivity implements OnMapReadyCallback,LocationListener
                                                                    ,NuevaPesquisaFragment.OnFragmentInteractionListener{

    private GoogleMap mMap;
    private DaoSession daoSession;
    private List<CtlPlCriadero> criaderosMap;
    Utilidades u;
    private SharedPreferences prefs;
    private CameraPosition cameraZoom;
    ArrayList<LatLng> locations = new ArrayList();
    ArrayList<String> nombres   = new ArrayList();
    private List<CtlMunicipio> municipios;
    private ArrayList<String> listaMunicipio;
    private Spinner spMunicipioPesquisa;
    int depto = MainActivity.depto;
    TextView tvCountCriadero;
    Button btnBuscarCriaderoPes;
    long idCriadero;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_larvaria);
        daoSession          =((MyMalaria)getApplicationContext()).getDaoSession();
        prefs               = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        spMunicipioPesquisa =(Spinner)findViewById(R.id.spMunicipioPesquisa);
        tvCountCriadero     = (TextView)findViewById(R.id.tvCountCriadero);
        btnBuscarCriaderoPes= (Button)findViewById(R.id.btnBuscarCriaderoPes);
        u=new Utilidades(daoSession);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        loadSpinerMun();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

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
        moverCamaraDepartamento();
        //solo preparamos el mapa luego mostraremos los puntos al presionar el botin buscar

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CtlPlCriadero cria = (CtlPlCriadero) marker.getTag();
                idCriadero=cria.getId();
                NuevaPesquisaFragment dialog = new NuevaPesquisaFragment().newInsrance(cria.getId(),cria.getNombre());
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
            tvCountCriadero.setText("Total de criaderos enontrados: "+String.valueOf(criaderos.size()));
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
    public void OnDialogPositiveClick(DialogFragment dialog, int anopheles12, int anopheles34,
                                      int culicino12, int culicino34, int pupa, int cucharonada,
                                      int largo, int ancho) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = dateFormat.format(currentTime);
        long idUsuario = getIdUser();
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
            pes.setIdCriadero(idCriadero);
            pes.setFechaHoraReg(fec);
            pes.setIdEstado(1);
            pes.setFechaHoraMod(fec);// se debe quitar not null
            pes.setFecha(fec);// se debe quitar not null
            pes.setIdSemanaEpidemiologica(semanaActual);
            pes.setIdUsuarioReg(idUsuario);
            pes.setIdCaserio(2458);//el criadero esta amarradp a un caserio navegar a el
            pes.setIdSibasi(8);
            pes.setIdTablet(2);
            pes.setEstado_sync(0);
            pesDao.insert(pes);
            Toast.makeText(getApplicationContext(),"se guardo",Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }/*
        String string = String.valueOf(anopheles12)+"-"+String.valueOf(anopheles34)+"-"+String.valueOf(culicino12)
                +"-"+String.valueOf(culicino34)+"-"+String.valueOf(pupa)+"-"+String.valueOf(cucharonada)+"-"+String.valueOf(largo);
        Toast.makeText(getApplicationContext(), string,Toast.LENGTH_LONG).show();*/

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
    public long getIdUser() {
        String username = pref.getString("user", "");
        long id = 0;
        if (!username.equals("")) {
            List<FosUserUser> ids = null;
            FosUserUserDao userDao = daoSession.getFosUserUserDao();
            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
            qb.where(FosUserUserDao.Properties.Username.eq(username));
            ids = qb.list();
            for (FosUserUser f : ids) {
                id = f.getId();
            }
        }
        return id;
    }

}
