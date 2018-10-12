package com.minsal.dtic.sinavec.CRUD.capturaAnopheles;
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
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.utilidades.Utilidades;
import java.util.ArrayList;
import java.util.List;

public class ListCapturaActivity extends AppCompatActivity {
    Button btnNuevaCaptura;
    ArrayList<PlCapturaAnopheles> capturas;
    ListView lvCaptura;
    List<PlCapturaAnopheles> capturasAnopheles;
    Utilidades u;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_captura);
        btnNuevaCaptura     = (Button)findViewById(R.id.btnNuevaCaptura);
        lvCaptura           = (ListView)findViewById(R.id.lvCaptura);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){actionBar.setDisplayHomeAsUpEnabled(true);}
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
        final ArrayList<String> capturasSemana = listaAdapterBySem();
        AdapterCapturas adapter =new AdapterCapturas(this,capturasSemana);
        lvCaptura.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnNuevaCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CapturaAnopheles.class);
                i.putExtra("accion","nueva");
                startActivity(i);
                 //finish();
            }
        });

        lvCaptura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String fila =  capturasSemana.get(position);
                String semana_g= fila.substring(5,7); //semana_g probablelemte trae un gion si es menor a 10
                String id_semana= semana_g.replace("-","");
                Toast.makeText(getApplicationContext(),id_semana,Toast.LENGTH_LONG).show();
               Intent nuevo = new Intent(getApplicationContext(), DetalleCapturaSemanaActivity.class);
                nuevo.putExtra("accion","edit");
                nuevo.putExtra("id_semana",id_semana);
                startActivity(nuevo);
                finish();
            }
        });
    }
    public ArrayList<String> listaAdapterBySem(){
        Utilidades u = new Utilidades(daoSession);
        ArrayList<String> lista= u.loadListcapturasBySem();
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
