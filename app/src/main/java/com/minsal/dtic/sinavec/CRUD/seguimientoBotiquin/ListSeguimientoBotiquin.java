package com.minsal.dtic.sinavec.CRUD.seguimientoBotiquin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquin;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class ListSeguimientoBotiquin extends AppCompatActivity {
    Button btnNuevo;
    ArrayList<PlSeguimientoBotiquin> seguimientos;
    ListView lvSeguimiento;
    List<PlSeguimientoBotiquin> seguimientoBotiquin;
    Utilidades u;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_seguimiento_botiquin);
        btnNuevo      = (Button)findViewById(R.id.btnNuevaSeg);
        lvSeguimiento = (ListView)findViewById(R.id.lvSeguimientoBotiquin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        daoSession =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
        final ArrayList<String> seguimientoList = listaAdapter();
        AdapterCapturas adapter =new AdapterCapturas(this,seguimientoList);
        lvSeguimiento.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SeguimientoBotiquinActivity.class);
                startActivity(i);

            }
        });
        lvSeguimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String fila = seguimientoList.get(position);
                String semana_g= fila.substring(5,7); //semana_g probablelemte trae un gion si es menor a 10
                String id_semana= semana_g.replace("-","");
                //Toast.makeText(getApplicationContext(),id_semana,Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),DetalleSeguimientoSemana.class);
                i.putExtra("id_semana",id_semana);
                startActivity(i);
            }
        });
    }
    public ArrayList<String> listaAdapter(){
        Utilidades u = new Utilidades(daoSession);
        ArrayList<String> lista= u.loadSeguimientosBySem();
        return lista;
    }

    @Override
    public void onBackPressed() {
        Intent m =new Intent(getApplicationContext(),MainActivity.class);
        m.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(m);
        finish();

    }
}
