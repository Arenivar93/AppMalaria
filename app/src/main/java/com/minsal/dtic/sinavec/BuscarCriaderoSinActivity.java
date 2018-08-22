package com.minsal.dtic.sinavec;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

import HelperDB.DbHelpers;

public class BuscarCriaderoSinActivity extends AppCompatActivity {
    Spinner spMunicipio, spCanton,spCaserio;
    private SQLiteDatabase conDb;
    private DbHelpers helper;
    DaoSession daoSession;
    CtlMunicipioDao daoMunicipio;
    CtlCantonDao daoCanton;
    CtlCaserioDao daoCaserio;

    ArrayList<String> listaMunicipio=new ArrayList<String>();
    List<CtlMunicipio> municipios=new ArrayList<CtlMunicipio>();
    ArrayList<String> listaCanton=new ArrayList<String>();
    List<CtlCanton> cantones=new ArrayList<CtlCanton>();
    ArrayList<String> listaCaserios;
    List<CtlCaserio> caserios=new ArrayList<CtlCaserio>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burcar_criadero);
        spMunicipio = (Spinner)findViewById(R.id.spMun);
        spCanton = (Spinner)findViewById(R.id.spCan);
        spCaserio = (Spinner)findViewById(R.id.spCas);

        listaMunicipio.add("Seleccione");
        listaCanton.add("Seleccione");

        //Cargo session de la bd
        daoSession=((MyMalaria)getApplicationContext()).getDaoSession();

        loadSpinerMun();

        ArrayAdapter<CharSequence> adapter=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaMunicipio);
        spMunicipio.setAdapter(adapter);

        final ArrayAdapter<CharSequence> adapter2=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaCanton);
        spCanton.setAdapter(adapter2);


        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    //String nombre=municipios.get(position-1).getNombre();
                    //Toast.makeText(parent.getContext(),nombre,Toast.LENGTH_LONG).show();
                    loadSpinerCanton(municipios.get(position-1).getId());
                    adapter2.notifyDataSetChanged();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void loadSpinerCanton(Long idCtn) {
        Toast.makeText(this,idCtn+"",Toast.LENGTH_LONG).show();
        daoCanton=daoSession.getCtlCantonDao();
        cantones=new ArrayList<CtlCanton>();
        cantones=daoCanton.queryBuilder().where(CtlCantonDao.Properties.IdMunicipio.eq(idCtn))
                .orderAsc(CtlCantonDao.Properties.Nombre).list();
        obetenerListaCantones();
    }

    private void obetenerListaCantones() {
        listaCanton=new ArrayList<String>();
        Toast.makeText(this,"Llena lista Cantones",Toast.LENGTH_LONG).show();

        for (int i=0;i<cantones.size();i++){
            listaCanton.add(cantones.get(i).getNombre());
        }
    }

    private void loadSpinerMun(){
        CtlMunicipio municipio=null;
        daoMunicipio=daoSession.getCtlMunicipioDao();
        municipios=new ArrayList<CtlMunicipio>();

        //lista=daoMunicipio.loadAll();
        municipios=daoMunicipio.queryBuilder().where(CtlMunicipioDao.Properties.IdDepartamento.eq(3))
                .orderAsc(CtlMunicipioDao.Properties.Nombre).list();
        obetenerLista();
    }

    private void obetenerLista() {
        listaMunicipio=new ArrayList<String>();
        for (int i=0;i<municipios.size();i++){
            listaMunicipio.add(municipios.get(i).getNombre());
        }
    }
}
