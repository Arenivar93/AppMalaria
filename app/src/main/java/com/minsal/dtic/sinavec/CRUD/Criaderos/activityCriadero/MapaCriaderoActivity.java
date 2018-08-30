package com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
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
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.BuscarCriaderoSinActivity;
import com.minsal.dtic.sinavec.R;

public class MapaCriaderoActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private FloatingActionButton fab;
    private EditText nomCriadero,nomCaserio,latitudCriadero,longitudCriadero;
    int idMunicipio=0,idCanton=0,idCaserio=0;
    private String nombreCriadero;
    private Button guardar,cancelar;
    private ToggleButton tipoBusqueda;
    private ImageView txtBusqueda;
    int MY_PERMISSION_LOCATION = 10;
    private Location currentLocation;
    private LocationManager locationManager;
    private Marker marker;
    private CameraPosition cameraZoom;
    private CameraPosition camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_criadero);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fab = (FloatingActionButton) findViewById(R.id.fabCriadero2);
        nomCriadero=(EditText)findViewById(R.id.nombreCriadero);
        nomCaserio=(EditText)findViewById(R.id.nombreCaserio);
        guardar=(Button)findViewById(R.id.guardarGeoCaserio);
        cancelar=(Button)findViewById(R.id.cancelarGeoCaserio);
        tipoBusqueda=(ToggleButton)findViewById(R.id.tipoBusqueda);
        txtBusqueda=(ImageView) findViewById(R.id.imgBusqueda);
        latitudCriadero=(EditText) findViewById(R.id.criaderoLatitud);
        longitudCriadero=(EditText) findViewById(R.id.criaderoLongitud);
        txtBusqueda.setImageResource(R.drawable.mano44);


        Bundle geolocalizarDatos=this.getIntent().getExtras();
        if(geolocalizarDatos!=null){
            idMunicipio=geolocalizarDatos.getInt("idMunicipio");
            idCanton=geolocalizarDatos.getInt("idCanton");
            idCaserio=geolocalizarDatos.getInt("idCaserio");
            nombreCriadero=geolocalizarDatos.getString("criadero");
            nomCriadero.setText(nombreCriadero);
            nomCaserio.setText("Prueba");
        }else{

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habilitarPosicionInicial();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent geolocalizarCriadero=new Intent(MapaCriaderoActivity.this, BuscarCriaderoSinActivity.class);
                //geolocalizarCriadero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle miBundle=new Bundle();
                miBundle.putString("criadero", nombreCriadero);
                miBundle.putInt("idMuni",idMunicipio);
                miBundle.putInt("idCtn",idCanton);
                miBundle.putInt("idCas",idCaserio);
                miBundle.putInt("cancelar",0);
                geolocalizarCriadero.putExtras(miBundle);
                startActivity(geolocalizarCriadero);
                finish();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent geolocalizarCriadero=new Intent(MapaCriaderoActivity.this, BuscarCriaderoSinActivity.class);
                //geolocalizarCriadero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle miBundle=new Bundle();
                miBundle.putString("criadero", nombreCriadero);
                miBundle.putInt("idMuni",idMunicipio);
                miBundle.putInt("idCtn",idCanton);
                miBundle.putInt("idCas",idCaserio);
                miBundle.putInt("cancelar",1);
                geolocalizarCriadero.putExtras(miBundle);
                startActivity(geolocalizarCriadero);
                finish();
            }
        });
        tipoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipoBusqueda.isChecked()){
                    txtBusqueda.setImageResource(R.drawable.migps2);
                }else{
                    txtBusqueda.setImageResource(R.drawable.mano44);
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

        cameraZoom=new CameraPosition.Builder()
                .target(new LatLng(13.713998,-89.724181))
                .zoom(13)
                .bearing(0)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));

        setUpMap();

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(),"Click en el mapa",Toast.LENGTH_LONG).show();

                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker nuevo"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });*/
    }
    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
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
        if (marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
        } else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
            zoomToLocation(location);
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

    @Override
    public void onLocationChanged(Location location) {
        createOrUpdateMarkerByLocation(location);
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
}
