package com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.nuevoCriaderoDialogFragment;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgregarCriaderoActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener,nuevoCriaderoDialogFragment.criaderoDialogListener{
    Spinner spMunicipio, spCanton,spCaserio;
    private GoogleMap mMap;
    private FloatingActionButton fab;
    private EditText nomMunicipio,nomCanton,nomCaserio,latitudCriadero,longitudCriadero;
    int idMunicipio=0,idCanton=0,idCaserio=0;
    private String nombreCriadero;
    private int coordenada=0;
    private double latitud;
    private double longitud;
    private ImageView guardar,cancelar;
    private ToggleButton tipoBusqueda;
    private ImageView imgBusqueda;
    int MY_PERMISSION_LOCATION = 10;
    private Location currentLocation;
    private LocationManager locationManager;
    private Marker markerGps;
    private Marker markerManual;
    private CameraPosition cameraZoom;
    private CameraPosition camera;
    private SharedPreferences prefs;
    ArrayList<String> listaMunicipio=new ArrayList<String>();
    List<CtlMunicipio> municipios;
    ArrayList<String> listaCanton=new ArrayList<String>();
    List<CtlCanton> cantones;
    ArrayList<String> listaCaserios=new ArrayList<String>();
    List<CtlCaserio> caserios;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    private DaoSession daoSession;
    Utilidades utilidades;
    private CtlPlCriadero criadero;
    private CtlPlCriaderoDao criaderoDao;
    private AppCompatButton buttonSiguiente;
    String elUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_criadero);
        //Me permite regresar  a la actividad anterior
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAgregarCriadero);
        fab = (FloatingActionButton) findViewById(R.id.fabCriadero2);
        tipoBusqueda=(ToggleButton)findViewById(R.id.tipoBusqueda);
        imgBusqueda =(ImageView) findViewById(R.id.imgBusqueda);
        latitudCriadero=(EditText) findViewById(R.id.criaderoLatitud);
        longitudCriadero=(EditText) findViewById(R.id.criaderoLongitud);
        imgBusqueda.setImageResource(R.drawable.mano44);
        buttonSiguiente=(AppCompatButton)findViewById(R.id.idSiguiente);

        spMunicipio = (Spinner)findViewById(R.id.spMun);
        spCanton = (Spinner)findViewById(R.id.spCan);
        spCaserio = (Spinner)findViewById(R.id.spCas);

        daoSession = ((MyMalaria) getApplication()).getDaoSession();
        criaderoDao=daoSession.getCtlPlCriaderoDao();
        utilidades=new Utilidades(daoSession);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        elUser = prefs.getString("user", "");
        int idDepto=utilidades.deptoUser(elUser);


        municipios=utilidades.loadspinnerMunicipio(idDepto);
        listaMunicipio=utilidades.obtenerListaMunicipio(municipios);
        listaCanton.add("Seleccione");
        listaCaserios.add("Seleccione");

        adapter=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaMunicipio);
        adapter.notifyDataSetChanged();
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMunicipio.setAdapter(adapter);
        adapter2=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaCanton);
        adapter2.notifyDataSetChanged();
        spCanton.setAdapter(adapter2);
        adapter3=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaCaserios);
        spCaserio.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coordenada==1){
                    zoomToLocationCoordenadas(latitud,longitud);
                }else{
                    moverCamaraDepartamento();
                }
            }
        });
        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView errorText=(TextView)spCaserio.getSelectedView();
                if(!latitudCriadero.getText().toString().isEmpty()
                        && !longitudCriadero.getText().toString().isEmpty() && spCaserio.getSelectedItemPosition()!=0){

                    nuevoCriaderoDialogFragment dialog = new nuevoCriaderoDialogFragment();
                    dialog.show(getFragmentManager(), "dialog");

                }else if(latitudCriadero.getText().toString().isEmpty() && longitudCriadero.getText().toString().isEmpty() && tipoBusqueda.isChecked()) {
                    if(!isGPSEnabled()){
                        Toast.makeText(getApplicationContext(),"Active el GPS para obtener las coordenadas",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Espere un momento, GPS calcula coordenadas",Toast.LENGTH_LONG).show();
                    }
                }else if(spCaserio.getSelectedItemPosition()==0){
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("Seleccione");
                }else{
                    Toast.makeText(getApplicationContext(),"Latitud y Longitud vacios, debe de " +
                            "geolocalizar el criadero",Toast.LENGTH_LONG).show();
                }

            }
        });
       tipoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipoBusqueda.isChecked()){
                    imgBusqueda.setImageResource(R.drawable.ic_gpsmap);
                    habilitarPosicionInicial();
                    if(markerManual!=null){
                        //markerManual.remove();
                        markerManual.setVisible(false);
                    }
                    latitudCriadero.setText(null);
                    longitudCriadero.setText(null);
                }else{
                    imgBusqueda.setImageResource(R.drawable.mano44);
                    if(markerGps!=null){
                        //markerGps.remove();
                        markerGps.setVisible(false);
                    }
                    latitudCriadero.setText(null);
                    longitudCriadero.setText(null);
                    if(coordenada==1){
                        zoomToLocationCoordenadas(latitud,longitud);
                    }else{
                        moverCamaraDepartamento();
                    }

                }
            }
        });
        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    cantones=utilidades.loadSpinerCanton(municipios.get(position-1).getId());
                    listaCanton.clear();
                    listaCanton=utilidades.obetenerListaCantones(cantones);
                    adapter2=new ArrayAdapter
                            (parent.getContext(),android.R.layout.simple_list_item_1,listaCanton);
                    spCanton.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
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
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    caserios=utilidades.loadSpinerCaserio(cantones.get(position-1).getId());
                    listaCaserios.clear();
                    listaCaserios=utilidades.obetenerListaCaserios(caserios);
                    adapter3=new ArrayAdapter
                            (parent.getContext(),android.R.layout.simple_list_item_1,listaCaserios);
                    spCaserio.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                }else{
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(coordenada!=1){
            moverCamaraDepartamento();
        }else{
            markerManual = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitud, longitud))
                    .title("Latitud: "+latitud+" Longitud: "+longitud).draggable(true));
            zoomToLocationCoordenadas(latitud,longitud);
            latitudCriadero.setText(null);
            longitudCriadero.setText(null);
            latitudCriadero.setText(String.valueOf(latitud));
            longitudCriadero.setText(String.valueOf(longitud));
        }
        setUpMap();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(tipoBusqueda.isChecked()){
                    Toast.makeText(getApplicationContext(),"La Busqueda GPS esta activada." +
                            " No puede agregar el marcador",Toast.LENGTH_LONG).show();
               }else{
                    if(markerGps!=null){
                        //markerGps.remove();
                        markerGps.setVisible(false);
                    }
                    if (markerManual == null) {
                        markerManual = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latLng.latitude, latLng.longitude))
                                .title("Latitud: "+latLng.latitude+" Longitud: "+latLng.longitude).draggable(true));
                    } else {
                        markerManual.setPosition(new LatLng(latLng.latitude,latLng.longitude));
                        markerManual.setTitle("Latitud: "+latLng.latitude+" Longitud: "+latLng.longitude);
                        markerManual.setVisible(true);
                        //zoomToLocationManual(latLng);
                    }
                   latitudCriadero.setText(null);
                    longitudCriadero.setText(null);
                    latitudCriadero.setText(String.valueOf(latLng.latitude));
                    longitudCriadero.setText(String.valueOf(latLng.longitude));

                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if(tipoBusqueda.isChecked()){
                    Toast.makeText(getApplicationContext(),"No puede actualizar la coordena GPS",Toast.LENGTH_LONG).show();
                }else{
                    latitudCriadero.setText(null);
                    longitudCriadero.setText(null);
                    latitudCriadero.setText(String.valueOf(marker.getPosition().latitude));
                    longitudCriadero.setText(String.valueOf(marker.getPosition().longitude));
                }
            }
        });
    }

    private void moverCamaraDepartamento() {
        String elUser = prefs.getString("user", "");
        int idDepto=utilidades.deptoUser(elUser);
        List<Double> coordenadasDepto=utilidades.getCoordenadasDepartamento(idDepto);
        cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(coordenadasDepto.get(0),coordenadasDepto.get(1)))
                .zoom(13)
                .bearing(0)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));
    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (Build.VERSION.SDK_INT >= 23) {
            marshmallowGPSPremissionCheck();

        } else {
            enableMyLocation();

        }
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(AgregarCriaderoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AgregarCriaderoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) AgregarCriaderoActivity.this.getSystemService(Context.LOCATION_SERVICE);
        mMap.setMyLocationEnabled(true);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    private void marshmallowGPSPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(AgregarCriaderoActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_LOCATION);
        } else {
            enableMyLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        }else {
            Toast.makeText(getApplicationContext(),"Permiso denegado!!!",Toast.LENGTH_LONG).show();
        }
    }

    public void habilitarPosicionInicial(){
        if (!this.isGPSEnabled()) {
            showInfoAlert();
        } else {
            if (ActivityCompat.checkSelfPermission(AgregarCriaderoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AgregarCriaderoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            currentLocation = location;
            if (currentLocation != null) {
                createOrUpdateMarkerByLocation(location);
                zoomToLocation(location);
            }

        }

    }

    private boolean isGPSEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void showInfoAlert() {
        new AlertDialog.Builder(AgregarCriaderoActivity.this)
                .setTitle("Gps Signal")
                .setMessage("Desea activar el gps?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void createOrUpdateMarkerByLocation(Location location) {
        if (markerGps == null) {
            markerGps = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
        } else {

            markerGps.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
            zoomToLocation(location);
            markerGps.setVisible(true);
        }
    }
    private void zoomToLocation(Location location){
        cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(),location.getLongitude()))
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));
    }
    private void zoomToLocationManual(LatLng latLng){
        cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(latLng.latitude,latLng.longitude))
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));
    }
    private void zoomToLocationCoordenadas(double latitud,double longitud){
        cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(latitud,longitud))
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));
    }

    @Override
    public void onLocationChanged(Location location) {
        //Verifico el tipo de busqueda
        if(tipoBusqueda.isChecked()){
            createOrUpdateMarkerByLocation(location);
            latitudCriadero.setText(null);
            longitudCriadero.setText(null);
            latitudCriadero.setText(String.valueOf(location.getLatitude()));
            longitudCriadero.setText(String.valueOf(location.getLongitude()));
        }else{
            if(markerGps!=null){
                //markerGps.remove();
                markerGps.setVisible(false);

            }
        }

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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo) {

        try {
            Date fecha=new Date();
            String sqlQUERY = "SELECT MAX(id) FROM CTL_PL_CRIADERO";
            Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY, null);
            int idCriaderoMax = 0;
            if (cursor.moveToFirst()) {
                idCriaderoMax = cursor.getInt(0);
            }
            long idUser=utilidades.getIdUser(elUser);
            FosUserUser usuario=daoSession.getFosUserUserDao().loadByRowId(idUser);
            CtlCaserio caserio=daoSession.getCtlCaserioDao().loadByRowId(caserios.get(spCaserio.getSelectedItemPosition()-1).getId());

            CtlPlCriadero criadero=new CtlPlCriadero();
            criadero.setId((long)(idCriaderoMax+1));
            criadero.setIdTipoCriadero(tipo);
            criadero.setIdEstadoCriadero(1);
            criadero.setNombre(nombre);
            criadero.setDescripcion(descripcion);
            criadero.setLatitud(latitudCriadero.getText().toString());
            criadero.setLongitud(longitudCriadero.getText().toString());
            criadero.setLongitudCriadero(largo);
            criadero.setAnchoCriadero(ancho);
            criadero.setFechaHoraMod(fecha);
            criadero.setFechaHoraReg(fecha);
            criadero.setIdUsarioReg(idUser);
            criadero.setFosUserUser(usuario);
            criadero.setCtlEstablecimiento(usuario.getCtlEstablecimiento());
            criadero.setCtlCaserio(caserio);
            criadero.setEstado_sync(1);
            daoSession.getCtlPlCriaderoDao().insert(criadero);

            Intent listadoCriaderos=new Intent(this, BuscarCriaderoActivity.class);
            startActivity(listadoCriaderos);
            Toast.makeText(this,"El criadero fue registrado con exito!!!",Toast.LENGTH_LONG).show();
            finish();

            Intent guardarCriadero=new Intent(this, BuscarCriaderoActivity.class);
            Bundle miBundle=new Bundle();
            miBundle.putString("criadero", criadero.getNombre());
            miBundle.putInt("idMuni",(int)(long)criadero.getCtlCaserio().getCtlCanton().getCtlMunicipio().getId());
            miBundle.putInt("idCtn",(int)(long)criadero.getCtlCaserio().getCtlCanton().getId());
            miBundle.putInt("idCas",(int)(long)criadero.getCtlCaserio().getId());
            miBundle.putInt("bandera",5);
            guardarCriadero.putExtras(miBundle);
            startActivity(guardarCriadero);
            finish();
        }catch (Exception e){
            Toast.makeText(this,"Error, comuniquese con el administrador" +
                    "del Sistema. Error:"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this,"Cancelo la operación",Toast.LENGTH_LONG).show();
    }
}
