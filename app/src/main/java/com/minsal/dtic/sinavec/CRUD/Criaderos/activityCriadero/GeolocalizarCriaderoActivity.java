package com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.BuscarCriaderoSinActivity;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.R;

public class GeolocalizarCriaderoActivity extends AppCompatActivity {
    Button cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalizar_criadero);

        cancelar=(Button)findViewById(R.id.idRegresar);
        String nombreCriadero="";

        Bundle geolocalizarDatos=this.getIntent().getExtras();
        if(geolocalizarDatos!=null){
            int idMunicipio=geolocalizarDatos.getInt("idMunicipio");
            int idCanton=geolocalizarDatos.getInt("idCanton");
            int idCaserio=geolocalizarDatos.getInt("idCaserio");
            /*CtlPlCriadero objectoCriadero= (CtlPlCriadero) geolocalizarDatos.getSerializable("criadero");
            nombreCriadero=objectoCriadero.getNombre();*/
            nombreCriadero=geolocalizarDatos.getString("criadero");

        }
        Toast.makeText(this,"Nombre Criadero="+nombreCriadero,Toast.LENGTH_LONG).show();

        final String finalNombreCriadero = nombreCriadero;
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geolocalizarCriadero=new Intent(GeolocalizarCriaderoActivity.this, BuscarCriaderoSinActivity.class);
                //geolocalizarCriadero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle miBundle=new Bundle();
                miBundle.putString("criadero", finalNombreCriadero);
                geolocalizarCriadero.putExtras(miBundle);
                startActivity(geolocalizarCriadero);


                //finish();
            }
        });
    }


}
