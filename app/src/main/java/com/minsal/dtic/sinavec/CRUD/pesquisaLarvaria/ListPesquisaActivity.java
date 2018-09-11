package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

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
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class ListPesquisaActivity extends AppCompatActivity {
    Button btnNueva;
    ArrayList<PlPesquisaLarvaria> capturas;
    ListView lvPesquisa;
    List<PlPesquisaLarvaria> pesquisaLarvaria;
    Utilidades u;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pesquisa);
        btnNueva = (Button)findViewById(R.id.btnNuevaPesquisa);
        lvPesquisa = (ListView)findViewById(R.id.lvPesquisa);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
      final ArrayList<String> pesquisaPrueba2 = listaAdapter();
        AdapterCapturas adapter =new AdapterCapturas(this,pesquisaPrueba2);
        lvPesquisa.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),pesquisaLarvaria.class);
                startActivity(i);finish();
            }
        });

        lvPesquisa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int id_semana = pesquisaPrueba2.indexOf(position);
                Toast.makeText(getApplicationContext(),String.valueOf(id_semana),Toast.LENGTH_LONG).show();

            }
        });
    }
    public ArrayList<String> listaAdapter(){
        Utilidades u = new Utilidades(daoSession);
        ArrayList<String> lista= u.loadListPesquisaBySem();
        return lista;
    }
}
