package com.minsal.dtic.sinavec.CRUD.seguimientoBotiquin;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SeguimientoBotiquinActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {
    private SharedPreferences prefs;
    Utilidades u;
    private DaoSession daoSession;
    private CameraPosition cameraZoom;
    private GoogleMap mMap;
    ImageView ivBuscar;
    RadioButton rdbColvol, rdbSmo;
    Spinner spMunicipio;
    private List<CtlMunicipio> municipios;
    private ArrayList<String> listaMunicipio;
    int depto = MainActivity.depto;
    TextView tvCountBotiquin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_botiquin);
        daoSession = ((MyMalaria)getApplicationContext()).getDaoSession();
        prefs      = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        u          = new Utilidades(daoSession);
        rdbColvol  = (RadioButton)findViewById(R.id.rdbColvol);
        rdbSmo     = (RadioButton)findViewById(R.id.rdbSmo);
        ivBuscar   = (ImageView)findViewById(R.id.ivBuscarBotiquin);
        spMunicipio=(Spinner)findViewById(R.id.spMunicipioBotiquin);
        tvCountBotiquin    = (TextView)findViewById(R.id.tvCountBotiquin);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        loadSpinerMun();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ivBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                validateSpinnerRadio();
                long idMunicipio = getIdMunicipioPes();
                if (rdbSmo.isChecked()){
                    establecimientosMap((int) idMunicipio);
                }else{
                    colvolMap((int) idMunicipio);

                }

            }
        });
    }//fin metodo oncreate

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moverCamaraDepartamento();


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
    private void loadSpinerMun() {
        Utilidades u   = new Utilidades(daoSession);
        municipios     = u.loadspinnerMunicipio(depto);
        listaMunicipio = u.getMunicipioTodos(municipios);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listaMunicipio);
        spMunicipio.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void validateSpinnerRadio(){
        if (!rdbSmo.isChecked() && !rdbColvol.isChecked()){
            Toast.makeText(getApplicationContext(),"Seleccione el tipo de botiquin",Toast.LENGTH_LONG).show();
            }
    }
    public long getIdMunicipioPes() {
        int listIdMunicipio = spMunicipio.getSelectedItemPosition();
        int idMunicipio = 0;
        if (listIdMunicipio != 0) {
            idMunicipio = (int) (long) municipios.get(listIdMunicipio - 1).getId();
        }
        return idMunicipio;
    }

    public void establecimientosMap(int municipio) {
        Utilidades u = new Utilidades(daoSession);
        List<CtlEstablecimiento> establecimientos = u.loadEstablecimientoMap(municipio,depto);
        if (establecimientos.size() > 0) {
            for (CtlEstablecimiento e : establecimientos) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(e.getLatitud()), Double.parseDouble(e.getLongitud())))
                        .title(e.getNombre())).setTag(e);
            }
            tvCountBotiquin.setText(String.format("Total de establecimientos encontrados: %s", String.valueOf(establecimientos.size())));
        } else {
            tvCountBotiquin.setText("No se encontraron criaderos registrados con coordenadas");
        }
    }
    public void colvolMap(int municipio) {
        Utilidades u = new Utilidades(daoSession);
        List<PlColvol> colvols = u.loadColvolMap(municipio);
        if (colvols.size() > 0) {
            for (PlColvol e : colvols) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(e.getLatitud()), Double.parseDouble(e.getLongitud())))
                        .title(e.getNombre())).setTag(e);
            }
            tvCountBotiquin.setText(String.format("Total de ColVol encontrados: %s", String.valueOf(colvols.size())));
        } else {
            tvCountBotiquin.setText("No se encontraron colvol registrados con coordenadas");
        }
    }
}
