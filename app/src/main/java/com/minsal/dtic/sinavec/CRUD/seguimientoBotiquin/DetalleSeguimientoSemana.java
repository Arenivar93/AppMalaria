package com.minsal.dtic.sinavec.CRUD.seguimientoBotiquin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquin;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.adapters.AdapterSeguimiento;
import com.minsal.dtic.sinavec.adapters.AdapterSemanaPesquisa;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetalleSeguimientoSemana extends AppCompatActivity {
    private DaoSession daoSession;
    Utilidades u;
    private List<PlSeguimientoBotiquin> seguimientoSemana;
    int id_semana;
    ListView lvSeguimiento;
    TextView tvDetalleSemana;
    Button nuevoSeguimiento;
    AdapterSeguimiento adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_seguimiento_semana);
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lvSeguimiento    = (ListView)findViewById(R.id.lvSeguimientoSemana);
        tvDetalleSemana  = (TextView)findViewById(R.id.tvDetalleSemana);
        nuevoSeguimiento = (Button)findViewById(R.id.btnNuevoSeguimientoDetalle);
        Bundle bundle     = getIntent().getExtras();
        if (bundle!=null){
            if(bundle.get("bandera_delete")!=null){
                id_semana = Integer.parseInt(bundle.getString("id_semana2"));
            }else {
                id_semana = Integer.parseInt(bundle.getString("id_semana"));
            }
        }
        tvDetalleSemana.setText(String.format("Detalle de la semana: %s", String.valueOf(id_semana)));
        final ArrayList<String> lista = listaAdapter();
        AdapterSeguimiento adapter =new AdapterSeguimiento(this,lista);
        lvSeguimiento.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvSeguimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String fila = lista.get(position);
               // Toast.makeText(getApplicationContext(),fila,Toast.LENGTH_LONG).show();
                String[] parts= fila.split("/");
                String id= parts[0];
                String nombre= parts[3];
                Intent i = new Intent(getApplicationContext(),EditSeguimiento.class);
                i.putExtra("id_seguimiento",id);
                i.putExtra("id_semana",id_semana);
                i.putExtra("nombre",nombre);
                startActivity(i);
              //  Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
            }
        });
        nuevoSeguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SeguimientoBotiquinActivity.class);
                startActivity(i);

            }
        });
    }//fin onCreate
    public ArrayList<String> listaAdapter(){
        Utilidades u = new Utilidades(daoSession);
        ArrayList<String> lista= u.loadSeguimientosDetalleCombinado(id_semana);
        return lista;
    }
    @Override
    public void onBackPressed() {
        Intent list =new Intent(getApplicationContext(),ListSeguimientoBotiquin.class);
        startActivity(list);
        finish();
    }


}
