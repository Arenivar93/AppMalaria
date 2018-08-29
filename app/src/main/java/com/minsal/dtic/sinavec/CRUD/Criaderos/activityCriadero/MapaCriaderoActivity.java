package com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.BuscarCriaderoSinActivity;
import com.minsal.dtic.sinavec.R;

public class MapaCriaderoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton fab;
    private EditText nomCriadero,nomCaserio,latitudCriadero,longitudCriadero;
    int idMunicipio=0,idCanton=0,idCaserio=0;
    private String nombreCriadero;
    private Button guardar,cancelar;
    private ToggleButton tipoBusqueda;
    private ImageView txtBusqueda;

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
                Toast.makeText(getApplicationContext(),"Verificara GPS",Toast.LENGTH_SHORT).show();
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(),"Click en el mapa",Toast.LENGTH_LONG).show();

                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker nuevo"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }
}
