package com.minsal.dtic.sinavec;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvolDao;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

import HelperDB.DbHelpers;

public class BuscarCriaderoSinActivity extends AppCompatActivity {
    Spinner spMunicipio, spCanton,spCaserio;
    ImageView buscar;
    TableLayout tablaCriaderos;
    private SQLiteDatabase conDb;
    private DbHelpers helper;
    DaoSession daoSession;
    CtlMunicipioDao daoMunicipio;
    CtlCantonDao daoCanton;
    CtlCaserioDao daoCaserio;
    CtlPlCriaderoDao ctlPlCriaderoDao;

    ArrayList<String> listaMunicipio=new ArrayList<String>();
    List<CtlMunicipio> municipios;
    ArrayList<String> listaCanton=new ArrayList<String>();
    List<CtlCanton> cantones;
    ArrayList<String> listaCaserios=new ArrayList<String>();
    List<CtlCaserio> caserios;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    Utilidades utilidades;
    List<CtlPlCriadero> criaderos;
    ProgressDialog progressDialog;
    List<TableRow> rowTablas;
    private ProgressDialog dialog;
    private ProgressDialog progressDialog2;
    Integer longitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burcar_criadero);
        spMunicipio = (Spinner)findViewById(R.id.spMun);
        spCanton = (Spinner)findViewById(R.id.spCan);
        spCaserio = (Spinner)findViewById(R.id.spCas);
        buscar= (ImageView)findViewById(R.id.buscarCriadero);
        tablaCriaderos = (TableLayout)findViewById(R.id.tableCriadero);
        listaCanton.add("Seleccione");
        listaCaserios.add("Seleccione");

        progressDialog2 = new ProgressDialog(BuscarCriaderoSinActivity.this);
        progressDialog2.setMessage("Cargando");


        daoSession=((MyMalaria)getApplicationContext()).getDaoSession();
        utilidades=new Utilidades(daoSession);

        municipios=utilidades.loadspinnerMunicipio(3);
        listaMunicipio=utilidades.obtenerListaMunicipio(municipios);

        adapter=new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,listaMunicipio);
        adapter.notifyDataSetChanged();
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMunicipio.setAdapter(adapter);
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
                    cantones=utilidades.loadSpinerCanton(municipios.get(position-1).getId());
                    listaCanton.clear();
                    listaCanton=utilidades.obetenerListaCantones(cantones);
                    adapter2=new ArrayAdapter
                            (parent.getContext(),android.R.layout.simple_list_item_1,listaCanton);
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
                    caserios=utilidades.loadSpinerCaserio(cantones.get(position-1).getId());
                    listaCaserios.clear();
                    listaCaserios=utilidades.obetenerListaCaserios(caserios);
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
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tablaCriaderos.removeAllViews();
                int idMuni=spMunicipio.getSelectedItemPosition();
                int idCtn=spCanton.getSelectedItemPosition();
                int idCas=spCaserio.getSelectedItemPosition();
                int idMunicipio=0;
                int idCanton=0;
                int idCaserio=0;

                if(idMuni!=0){
                    llenarTablaCriadero();
                    new MiTarea().execute();
                }else{
                    Toast.makeText(getApplicationContext(),"Seleccione un Municipio",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class MiTarea extends AsyncTask<Void, TableRow, Integer>{
        @Override
        protected void onPreExecute() {
            progressDialog2.show();
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            CtlPlCriadero criadero;
            for (int i=0;i<criaderos.size();i++){
                criadero=new CtlPlCriadero();
                criadero=criaderos.get(i);
                final int idCriadero=(int)(long) criadero.getId();
                // dara rows
                TableRow row = new TableRow(getApplicationContext());
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                String[] colText={i+1+"",criadero.getNombre(),criadero.getNombre(),criadero.getNombre()};
                TextView tv;
                int j=0;
                for(String text:colText) {
                    tv = new TextView(getApplicationContext());
                    if (j==0){
                        tv.setLayoutParams(new TableRow.LayoutParams(75,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setPadding(15, 5, 10, 5);
                    }else{
                        tv.setLayoutParams(new TableRow.LayoutParams(400,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setPadding(15, 5, 10, 5);
                    }
                    tv.setTextSize(16);
                    tv.setText(text);

                    tv.setTextColor(Color.BLACK);
                    row.addView(tv);
                    j++;
                }
                // Creation  button
                final Button button = new Button(getApplicationContext());
                button.setText("Map");
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Asignara Coordenadas id:"+idCriadero,Toast.LENGTH_LONG).show();
                    }
                });
                row.addView(button);
                publishProgress(row);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(TableRow... values) {
            tablaCriaderos.addView(values[0]);

        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog2.dismiss();
        }
    }

    private void llenarTablaCriadero(){
        int idMuni=spMunicipio.getSelectedItemPosition();
        int idCtn=spCanton.getSelectedItemPosition();
        int idCas=spCaserio.getSelectedItemPosition();
        int idMunicipio=0;
        int idCanton=0;
        int idCaserio=0;
        criaderos=new ArrayList<CtlPlCriadero>();
            if(idCtn!=0 && idCas==0){
                //Ejecutara busqueda de municipio y canton
                idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
                idCanton=(int) (long) cantones.get(idCtn-1).getId();
                criaderos=utilidades.obtenerCaseriosByIds(daoSession,idMunicipio,idCanton,idCaserio);

            }else if(idCtn!=0 && idCas!=0){
                //Ejecutara busqueda de muni ctn y cass
                idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
                idCanton=(int) (long) cantones.get(idCtn-1).getId();
                idCaserio=(int) (long) caserios.get(idCas-1).getId();
                criaderos=utilidades.obtenerCaseriosByIds(daoSession,idMunicipio,idCanton,idCaserio);
            }else{
                //ejecutara busqueda solo de municipio
                idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
                criaderos=utilidades.obtenerCaseriosByIds(daoSession,idMunicipio,idCanton,idCaserio);
            }
    }

    private void encabezadoTabla(Context context){
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"N°","NOMBRE","CANTON","CASERIO","OPCIONES"};
        for(String c:headerText) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            //tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15);
            tv.setPadding(15, 5, 10, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tablaCriaderos.addView(rowHeader);
    }
}
