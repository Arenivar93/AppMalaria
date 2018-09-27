package com.minsal.dtic.sinavec.CRUD.seguimientoBotiquin;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquin;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquinDao;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SeguimientoBotiquinActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener,NuevoSeguimientoFragment.OnFragmentInteractionListener {
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
    int bandera;
    long idEstablecimiento, idColvol;
    TextView tvCountBotiquin;
    String clave;
    long idSibasi,idTablet,idUsuario;
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
        idSibasi  = prefs.getLong("idSibasiUser",0);
        idTablet  = prefs.getLong("idTablet",0);
        idUsuario = prefs.getLong("idUser",0);
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
               boolean validate= validateSpinnerRadio();
               if (validate){
                   long idMunicipio = getIdMunicipioPes();
                   if (rdbSmo.isChecked()){
                       establecimientosMap((int) idMunicipio);
                       bandera = 2;
                   }else{
                       colvolMap((int) idMunicipio);
                       bandera=1;
                   }

               }

            }
        });
    }//fin metodo oncreate

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moverCamaraDepartamento();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                setObjectMarker(bandera, marker);
                return false;
            }
        });
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
    public boolean validateSpinnerRadio(){
        boolean validate= true;
        if (!rdbSmo.isChecked() && !rdbColvol.isChecked()){
            Toast.makeText(getApplicationContext(),"Seleccione el tipo de botiquin",Toast.LENGTH_LONG).show();
            validate = false;
            }
            return validate;
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
//la bandera que recibe colvol= col , establecimiento = est para buscar dependiendo de eso el id clave
    @Override
    public void OnDialogPositiveClick(DialogFragment dialog, int muestras, int personas, String accion, int riesgo,String bandera, long id) {
        Date currentTime = Calendar.getInstance().getTime();
       // Toast.makeText(getApplicationContext(),bandera,Toast.LENGTH_LONG).show();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(currentTime);
        int idClave = getIdClave(id,bandera);
        try {
                int semanaActual = getSemana();
                Date fec = dateFormat.parse(fecha);
                PlSeguimientoBotiquinDao segDao = daoSession.getPlSeguimientoBotiquinDao();
                PlSeguimientoBotiquin seg = new PlSeguimientoBotiquin();
                seg.setIdClave(idClave);
                seg.setNumeroMuestra(muestras);
                seg.setNumeroPersonaDivulgo(personas);
                seg.setFecha(fec);
                seg.setFechaHoraReg(fecha);
                seg.setFechaRegistro(fec);
                seg.setIdSibasi(idSibasi);
                seg.setIdUsuarioReg(idUsuario);
                seg.setIdTablet(idTablet);
                seg.setEstado_sync(1);
                seg.setIdEstadoFormulario(2);
                seg.setIdSemanaEpidemiologica(semanaActual);
                if (accion.equals("visitar")){
                    seg.setVisitado(1);
                }else{
                    seg.setSupervisado(1);
                }
                if (riesgo==1){
                    seg.setEnRiesgo(1);
                }else{
                    seg.setEnRiesgo(0);
                }
                segDao.insert(seg);

            customToadSuccess(getApplicationContext(),"Seguimiento de Botiquin registrado con éxito");

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
    /** este metodo nos servira para saber si vamos a castear un colvol o establecimiento en el mapa
     *la bandera recibira el paramtro si es colvol o si establecimiento
     * 1= colvol 2= establecimiento
     */
    public void setObjectMarker(int bandera,Marker marker){
        if (bandera==2){
            CtlEstablecimiento est = (CtlEstablecimiento) marker.getTag();
            idEstablecimiento = est.getId();
            NuevoSeguimientoFragment dialog = NuevoSeguimientoFragment.newInstance(est.getId(),est.getNombre(),"est");
            dialog.show(getFragmentManager(),"dialog");
        }else{
            PlColvol col = (PlColvol) marker.getTag();
            idColvol = col.getId();
            NuevoSeguimientoFragment dialog = NuevoSeguimientoFragment.newInstance(col.getId(),col.getNombre(),"col");
            dialog.show(getFragmentManager(),"dialog");
        }
    }
    public int getIdClave(long id, String bandera){
        int idClave = 0;
        if (bandera.equals("est")){
            String sqlQUERY = "SELECT ID_CLAVE FROM ESTABLECIMIENTO_CLAVE WHERE ID_ESTABLECIMIENTO='" + id + "'";
            Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY, null);
            if (cursor.moveToFirst()) {
                idClave = cursor.getInt(0);
            }
        }else{
            String sqlQUERY = "SELECT ID_CLAVE FROM COLVOL_CALVE WHERE ID_COLVOL='" + id + "'";
            Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY, null);
            if (cursor.moveToFirst()) {
                idClave = cursor.getInt(0);
            }
        }
        return  idClave;
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
        Intent i = new Intent(getApplicationContext(),ListSeguimientoBotiquin.class);
        startActivity(i);
        finish();
    }
}
