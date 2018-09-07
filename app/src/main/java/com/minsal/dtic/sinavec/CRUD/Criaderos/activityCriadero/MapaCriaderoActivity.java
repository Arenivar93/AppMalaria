package com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.verCriaderoDialogFragment;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.List;

public class MapaCriaderoActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener, verCriaderoDialogFragment.criaderoDialogListener {

    private GoogleMap mMap;
    private FloatingActionButton fab;
    private EditText nomMunicipio,nomCanton,nomCaserio,latitudCriadero,longitudCriadero;
    int idMunicipio=0,idCanton=0,idCaserio=0;
    private String nombreCriadero;
    private int coordenada=0;
    private double latitud;
    private double longitud;
    private ImageView guardar,cancelar,verEdit;
    private ToggleButton tipoBusqueda;
    private ImageView txtBusqueda;
    int MY_PERMISSION_LOCATION = 10;
    private Location currentLocation;
    private LocationManager locationManager;
    private Marker markerGps;
    private Marker markerManual;
    private CameraPosition cameraZoom;
    private CameraPosition camera;
    private SharedPreferences prefs;
    private DaoSession daoSession;
    Utilidades utilidades;
    private CtlPlCriadero criadero;
    private CtlPlCriaderoDao criaderoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_criadero);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCoordenadaCriadero);
        fab = (FloatingActionButton) findViewById(R.id.fabCriadero2);
        nomMunicipio=(EditText)findViewById(R.id.nombreMunicipio);
        nomCanton=(EditText)findViewById(R.id.nombreCanton);
        nomCaserio=(EditText)findViewById(R.id.nombreCaserio);
        guardar=(ImageView) findViewById(R.id.guardarGeoCaserio);
        cancelar=(ImageView) findViewById(R.id.cancelarGeoCaserio);
        verEdit=(ImageView) findViewById(R.id.verGeoCaserio);
        tipoBusqueda=(ToggleButton)findViewById(R.id.tipoBusqueda);
        txtBusqueda=(ImageView) findViewById(R.id.imgBusqueda);
        latitudCriadero=(EditText) findViewById(R.id.criaderoLatitud);
        longitudCriadero=(EditText) findViewById(R.id.criaderoLongitud);
        txtBusqueda.setImageResource(R.drawable.mano44);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        daoSession = ((MyMalaria) getApplication()).getDaoSession();
        utilidades=new Utilidades(daoSession);
        criaderoDao=daoSession.getCtlPlCriaderoDao();


        Bundle geolocalizarDatos=this.getIntent().getExtras();
        if(geolocalizarDatos!=null){
            idMunicipio=geolocalizarDatos.getInt("idMunicipio");
            idCanton=geolocalizarDatos.getInt("idCanton");
            idCaserio=geolocalizarDatos.getInt("idCaserio");
            nombreCriadero=geolocalizarDatos.getString("criadero");
            coordenada=geolocalizarDatos.getInt("coordenada");
            criadero=daoSession.getCtlPlCriaderoDao().loadByRowId(geolocalizarDatos.getLong("id"));
            nomMunicipio.setText(criadero.getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre());
            nomCanton.setText(criadero.getCtlCaserio().getCtlCanton().getNombre());
            nomCaserio.setText(criadero.getCtlCaserio().getNombre());

            if(criadero.getEstado_sync()==0 || criadero.getEstado_sync()==2){
                verEdit.setImageResource(R.mipmap.ic_ver);
            }
            if(coordenada==1){
                latitud=geolocalizarDatos.getDouble("latitud");
                longitud=geolocalizarDatos.getDouble("longitud");
                setTitle("Editar Coordenada Criadero: "+nombreCriadero);
            }else{
                setTitle("Georeferenciar criadero: "+nombreCriadero);
            }
        }else{
            Intent geolocalizarCriadero=new Intent(MapaCriaderoActivity.this, BuscarCriaderoActivity.class);
            Bundle miBundle=new Bundle();
            miBundle.putInt("bandera",3);
            geolocalizarCriadero.putExtras(miBundle);
            startActivity(geolocalizarCriadero);
            finish();
        }

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
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!latitudCriadero.getText().toString().isEmpty() && !longitudCriadero.getText().toString().isEmpty()){

                    criadero.setLatitud(latitudCriadero.getText().toString());
                    criadero.setLongitud(longitudCriadero.getText().toString());
                    int estado_sync=criadero.getEstado_sync();
                    if(estado_sync==0 || estado_sync==2){
                        criadero.setEstado_sync(2);
                    }
                    criaderoDao.update(criadero);
                    Intent geolocalizarCriadero=new Intent(MapaCriaderoActivity.this, BuscarCriaderoActivity.class);
                    Bundle miBundle=new Bundle();
                    miBundle.putString("criadero", nombreCriadero);
                    miBundle.putInt("idMuni",idMunicipio);
                    miBundle.putInt("idCtn",idCanton);
                    miBundle.putInt("idCas",idCaserio);
                    miBundle.putInt("bandera",0);
                    geolocalizarCriadero.putExtras(miBundle);
                    startActivity(geolocalizarCriadero);
                    finish();

                }else if(latitudCriadero.getText().toString().isEmpty() && longitudCriadero.getText().toString().isEmpty() && tipoBusqueda.isChecked()) {
                    if(!isGPSEnabled()){
                        Toast.makeText(getApplicationContext(),"Active el GPS para obtener las coordenadas",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Espere un momento, GPS calcula coordenadas",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Latitud y Longitud vacios, debe de " +
                            "geolocalizar el criadero",Toast.LENGTH_LONG).show();
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent geolocalizarCriadero=new Intent(MapaCriaderoActivity.this, BuscarCriaderoActivity.class);
                Bundle miBundle=new Bundle();
                miBundle.putString("criadero", nombreCriadero);
                miBundle.putInt("idMuni",idMunicipio);
                miBundle.putInt("idCtn",idCanton);
                miBundle.putInt("idCas",idCaserio);
                miBundle.putInt("bandera",1);
                geolocalizarCriadero.putExtras(miBundle);
                startActivity(geolocalizarCriadero);
                finish();
            }
        });

        tipoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipoBusqueda.isChecked()){
                    txtBusqueda.setImageResource(R.drawable.ic_gpsmap);
                    habilitarPosicionInicial();
                    if(markerManual!=null){
                        //markerManual.remove();
                        markerManual.setVisible(false);
                    }
                    latitudCriadero.setText(null);
                    longitudCriadero.setText(null);
                }else{
                    txtBusqueda.setImageResource(R.drawable.mano44);
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

        verEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(criadero.getEstado_sync()==1){
                    Intent update=new Intent(MapaCriaderoActivity.this,ActualizarCriaderoActivity.class);
                    Bundle miBundle=new Bundle();
                    miBundle.putLong("id",criadero.getId());
                    update.putExtras(miBundle);
                    startActivity(update);
                    finish();
                }else{
                    verCriaderoDialogFragment dialog = new verCriaderoDialogFragment();
                    Bundle datos=new Bundle();
                    datos.putLong("id",criadero.getId());
                    dialog.setArguments(datos);
                    dialog.show(getFragmentManager(), "dialog");
                }
            }
        });
        mapFragment.getMapAsync(this);
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
        if (ActivityCompat.checkSelfPermission(MapaCriaderoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapaCriaderoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) MapaCriaderoActivity.this.getSystemService(Context.LOCATION_SERVICE);
        mMap.setMyLocationEnabled(true);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    private void marshmallowGPSPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(MapaCriaderoActivity.this,
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
            if (ActivityCompat.checkSelfPermission(MapaCriaderoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapaCriaderoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //Teoria****
            //LocationManager---> These services allow applications to obtain periodic updates of the device's geographical location, or to fire an application-specified Intent when the device enters the proximity of a given geographical location.
            //Location-->A data class representing a geographic location. A location can consist of a latitude, longitude, timestamp, and other information such as bearing, altitude and velocity.
            //All locations generated by the LocationManager are guaranteed to have a valid latitude, longitude, and timestamp (both UTC time and elapsed real-time since boot), all other parameters are optional.
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
        new AlertDialog.Builder(MapaCriaderoActivity.this)
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

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}