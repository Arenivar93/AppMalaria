package com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.BuscarCriaderoSinActivity;
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.MapCriaderoFragment;
import com.minsal.dtic.sinavec.R;

public class GeoreferenciarCriaderoActivity extends AppCompatActivity implements MapCriaderoFragment.OnFragmentInteractionListener{

    Button cancelar,guardar;
    String nombreCriadero="null";
    int idMunicipio=0,idCanton=0,idCaserio=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_georeferenciar_criadero);
        cancelar=(Button)findViewById(R.id.idRegresar);
        guardar=(Button)findViewById(R.id.idGuardar);
        Bundle geolocalizarDatos=this.getIntent().getExtras();
        if(geolocalizarDatos!=null){
            idMunicipio=geolocalizarDatos.getInt("idMunicipio");
            idCanton=geolocalizarDatos.getInt("idCanton");
            idCaserio=geolocalizarDatos.getInt("idCaserio");
            /*CtlPlCriadero objectoCriadero= (CtlPlCriadero) geolocalizarDatos.getSerializable("criadero");
            nombreCriadero=objectoCriadero.getNombre();*/
            nombreCriadero=geolocalizarDatos.getString("criadero");
        }else{
            //Si no viene el bundle redirigirlo a la vista anterior ya que nunca vendra el id pára actualizarlo
        }
        final String finalNombreCriadero = nombreCriadero;
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geolocalizarCriadero=new Intent(GeoreferenciarCriaderoActivity.this, BuscarCriaderoSinActivity.class);
                //geolocalizarCriadero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle miBundle=new Bundle();
                miBundle.putString("criadero", finalNombreCriadero);
                miBundle.putInt("idMuni",idMunicipio);
                miBundle.putInt("idCtn",idCanton);
                miBundle.putInt("idCas",idCaserio);
                miBundle.putInt("cancelar",1);
                geolocalizarCriadero.putExtras(miBundle);
                startActivity(geolocalizarCriadero);
                finish();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geolocalizarCriadero=new Intent(GeoreferenciarCriaderoActivity.this, BuscarCriaderoSinActivity.class);
                //geolocalizarCriadero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle miBundle=new Bundle();
                miBundle.putString("criadero", finalNombreCriadero);
                miBundle.putInt("idMuni",idMunicipio);
                miBundle.putInt("idCtn",idCanton);
                miBundle.putInt("idCas",idCaserio);
                miBundle.putInt("cancelar",0);
                geolocalizarCriadero.putExtras(miBundle);
                startActivity(geolocalizarCriadero);
                finish();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}