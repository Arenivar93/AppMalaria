package com.minsal.dtic.sinavec;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividad;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCaptura;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class CapturaAnopheles extends AppCompatActivity {
    Spinner spMunicipio, spCanton, spCaserio,sptipo, spActividad;
    List<CtlMunicipio> municipios;
    ArrayList<String> listaMunicipio=new ArrayList<String>();

    private DaoSession daoSession;
    int depto = MainActivity.depto;
    List<PlTipoActividad> actividad;
    List<PlTipoCaptura> tipoCaptura;
    ArrayList<String> listaActividad;
    ArrayList<String> listaTipoCaptura;


    ArrayList<String> listaCanton=new ArrayList<String>();
    ArrayList<String> listaCaserios=new ArrayList<String>();
    List<CtlCanton> cantones;
    List<CtlCaserio> caserios;
    private Utilidades u;
    ArrayAdapter adapter, adapter2,adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoSession=((MyMalaria)getApplicationContext()).getDaoSession();
        setContentView(R.layout.activity_captura);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        spMunicipio = (Spinner)findViewById(R.id.idMunicipioCap);
        spCanton    = (Spinner)findViewById(R.id.idCantonCap);
        spCaserio   = (Spinner)findViewById(R.id.idCaserioCap);
        spActividad = (Spinner)findViewById(R.id.spActividad);
        sptipo      = (Spinner)findViewById(R.id.spCaptura);

        u=new Utilidades(daoSession);
        loadSpinerMun();
        loadSpinerCaptura();
        listaCanton.add("Seleccione");
        listaCaserios.add("Seleccione");
        adapter2=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaCanton);
        adapter2.notifyDataSetChanged();
        spCanton.setAdapter(adapter2);
         adapter3=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaCaserios);
        spCaserio.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();
        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    cantones=u.loadSpinerCanton(municipios.get(position-1).getId());
                    listaCanton.clear();
                    listaCanton=u.obetenerListaCantones(cantones);
                    adapter2=new ArrayAdapter(parent.getContext(),android.R.layout.simple_list_item_1,listaCanton);
                    spCanton.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    spCanton.setSelection(0);
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
                    caserios=u.loadSpinerCaserio(cantones.get(position-1).getId());
                    listaCaserios.clear();
                    listaCaserios=u.obetenerListaCaserios(caserios);
                    adapter3=new ArrayAdapter
                            (parent.getContext(),android.R.layout.simple_list_item_1,listaCaserios);
                    spCaserio.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
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
    }


    private void loadSpinerMun() {
        Utilidades u = new Utilidades(daoSession);
        municipios=u.loadspinnerMunicipio(depto);
        listaMunicipio=u.obtenerListaMunicipio(municipios);
        ArrayAdapter adapter=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaMunicipio);
        spMunicipio.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadSpinerCaptura(){
        actividad=u.loadspinnerActividad();
        listaActividad=u.obtenerListaActividad(actividad);
        ArrayAdapter adapter=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaActividad);
        spActividad.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
