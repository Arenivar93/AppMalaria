package com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.AddStudentDialogFragment;
import com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero.AgregarCriaderoActivity;
import com.minsal.dtic.sinavec.CRUD.Criaderos.activityCriadero.MapaCriaderoActivity;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.Student;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class BuscarCriaderoActivity extends AppCompatActivity implements AddStudentDialogFragment.NoticeDialogListener{
    Spinner spMunicipio, spCanton,spCaserio;
    ImageView buscar;
    TableLayout tablaCriaderos;
    TextView result;
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
    private ProgressDialog progressDialog;
    private SharedPreferences prefs;
    Integer longitud;
    int bandera;
    int contador;
    int idMuni2=0;
    int idCtn2=0;
    int idCas2=0;
    int controladorSaltos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burcar_criadero);

        bandera=0;
        contador=0;
        controladorSaltos=0;
        daoSession=((MyMalaria)getApplicationContext()).getDaoSession();

        //Me permite regresar  a la actividad anterior
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Utilidades.fragment=1;
        utilidades=new Utilidades(daoSession);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String elUser = prefs.getString("user", "");
        int idDepto=utilidades.deptoUser(elUser);


        spMunicipio = (Spinner)findViewById(R.id.spMun);
        spCanton = (Spinner)findViewById(R.id.spCan);
        spCaserio = (Spinner)findViewById(R.id.spCas);
        buscar= (ImageView)findViewById(R.id.buscarCriadero);
        tablaCriaderos = (TableLayout)findViewById(R.id.tableCriadero);
        result = (TextView)findViewById(R.id.result);

        progressDialog = new ProgressDialog(BuscarCriaderoActivity.this);
        progressDialog.setMessage("Cargando");

        utilidades=new Utilidades(daoSession);

        listaCanton.add("Seleccione");
        listaCaserios.add("Seleccione");
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

        Bundle geolocalizarDatos=this.getIntent().getExtras();
        if(geolocalizarDatos!=null){
            int controlError=geolocalizarDatos.getInt("bandera");
            if(controlError!=3){
                bandera=1;
                contador=1;
                idMuni2=geolocalizarDatos.getInt("idMuni");
                idCtn2=geolocalizarDatos.getInt("idCtn");
                idCas2=geolocalizarDatos.getInt("idCas");
                for (int i=0;i<municipios.size();i++) {
                    if (municipios.get(i).getId() == idMuni2) {
                        spMunicipio.setSelection(i + 1);
                    }
                }
                if(controlError==0){
                    Toast.makeText(this,"EL Criadero fue Georeferenciado con exito",Toast.LENGTH_LONG).show();
                }
                if(controlError==1){
                    Toast.makeText(this,"Operación cancelada",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this,"Error al momento de cargar los datos del caserio seleccionado",Toast.LENGTH_LONG).show();
            }


        }


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
                    if(bandera==1 && contador==1){
                        if(idCtn2!=0){
                            for (int i=0;i<cantones.size();i++) {
                                if (cantones.get(i).getId() == idCtn2) {
                                    spCanton.setSelection(i + 1);
                                }
                            }
                        }else{
                            spCanton.setSelection(0);
                        }
                    }else{
                        spCanton.setSelection(0);
                    }
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
                if(bandera==1 && contador==1 && idCas2==0){
                    if(controladorSaltos==1){
                        realizarBusquedaCaserios();
                    }
                    controladorSaltos++;
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
                    if(bandera==1 && contador==1){
                        if(idCas2!=0){
                            for (int i=0;i<caserios.size();i++) {
                                if (caserios.get(i).getId() == idCas2) {
                                    spCaserio.setSelection(i + 1);
                                }
                            }
                         }else{
                            spCaserio.setSelection(0);
                        }
                    }else{
                        spCaserio.setSelection(0);
                    }
                }else{
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                }
                if(bandera==1 && contador==1){
                    if(controladorSaltos==1){
                        realizarBusquedaCaserios();
                    }
                    controladorSaltos++;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarBusquedaCaserios();

            }
        });
    }

    private void realizarBusquedaCaserios() {
        tablaCriaderos.removeAllViews();
        int idMuni=spMunicipio.getSelectedItemPosition();
        int idCtn=spCanton.getSelectedItemPosition();
        int idCas=spCaserio.getSelectedItemPosition();
        int idMunicipio=0;
        int idCanton=0;
        int idCaserio=0;

        if(idCtn!=0 && idCas==0){
            idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
            idCanton=(int) (long) cantones.get(idCtn-1).getId();

        }else if(idCtn!=0 && idCas!=0){
            idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
            idCanton=(int) (long) cantones.get(idCtn-1).getId();
            idCaserio=(int) (long) caserios.get(idCas-1).getId();
        }else if(idMuni!=0){
            idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
        }else{
            Toast.makeText(getApplicationContext(),"Seleccione un Municipio",Toast.LENGTH_LONG).show();
        }


        result.setText("");
        if(idMuni!=0){
            llenarTablaCriadero(idMuni,idCtn,idCas);
            if (criaderos.size()>0){
                new MiTarea().execute(idMunicipio,idCanton,idCaserio);
            }else{
                Toast.makeText(getApplicationContext(),"No se encontraron criaderos",Toast.LENGTH_LONG).show();
                result.setText("Sin Resultados");
                result.setTextColor(Color.RED);
            }
        }
    }




    private class MiTarea extends AsyncTask<Integer, TableRow, Integer>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... idSpiner) {
            final int idMunicipio=idSpiner[0];
            final int idCanton=idSpiner[1];
            final int idCaserio=idSpiner[2];

            for (int i=0;i<criaderos.size();i++){
                CtlPlCriadero criadero=new CtlPlCriadero();
                criadero=criaderos.get(i);
                final int idCriadero=(int)(long) criadero.getId();
                // dara rows
                TableRow row = new TableRow(getApplicationContext());
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                String[] colText={i+1+"",criadero.getNombre(),criadero.getCtlCaserio().getCtlCanton().getNombre(),criadero.getCtlCaserio().getNombre()};
                TextView tv;
                int j=0;
                for(String text:colText) {
                    tv = new TextView(getApplicationContext());
                    if (j==0){
                        tv.setLayoutParams(new TableRow.LayoutParams(85,
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
                final ImageButton button = new ImageButton(getApplicationContext());
                final CtlPlCriadero finalCriadero = criadero;
                button.setBackground(null);
                button.setPadding(60,10,0,10);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                if(criadero.getLongitud()==null || criadero.getLatitud()==null){
                    //El criadero no tiene coordenadas
                    button.setImageResource(R.mipmap.ic_edit_m);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent geolocalizarCriadero=new Intent(BuscarCriaderoActivity.this, MapaCriaderoActivity.class);

                            Bundle miBundle=new Bundle();
                            miBundle.putInt("idMunicipio",idMunicipio);
                            miBundle.putInt("idCanton",idCanton);
                            miBundle.putInt("idCaserio",idCaserio);
                            miBundle.putString("criadero", finalCriadero.getNombre());
                            miBundle.putLong("id",finalCriadero.getId());
                            miBundle.putInt("coordenada",0);
                            geolocalizarCriadero.putExtras(miBundle);
                            startActivity(geolocalizarCriadero);
                            finish();
                        }
                    });
                }else{
                    button.setImageResource(R.mipmap.ic_ver_map);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent geolocalizarCriadero=new Intent(BuscarCriaderoActivity.this, MapaCriaderoActivity.class);

                            Bundle miBundle=new Bundle();
                            miBundle.putInt("idMunicipio",idMunicipio);
                            miBundle.putInt("idCanton",idCanton);
                            miBundle.putInt("idCaserio",idCaserio);
                            miBundle.putString("criadero", finalCriadero.getNombre());
                            miBundle.putLong("id",finalCriadero.getId());
                            miBundle.putInt("coordenada",1);
                            miBundle.putDouble("latitud",Double.parseDouble(finalCriadero.getLatitud()));
                            miBundle.putDouble("longitud",Double.parseDouble(finalCriadero.getLongitud()));
                            geolocalizarCriadero.putExtras(miBundle);
                            startActivity(geolocalizarCriadero);
                            finish();
                        }
                    });
                }

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
            progressDialog.dismiss();
        }
    }

    private void llenarTablaCriadero(int idMuni,int idCtn,int idCas){
        int idMunicipio=0;
        int idCanton=0;
        int idCaserio=0;
        criaderos=new ArrayList<CtlPlCriadero>();
            if(idCtn!=0 && idCas==0){
                //Ejecutara busqueda de municipio y canton
                idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
                idCanton=(int) (long) cantones.get(idCtn-1).getId();
                criaderos=utilidades.obtenerCriaderosByIds(daoSession,idMunicipio,idCanton,idCaserio);

            }else if(idCtn!=0 && idCas!=0){
                //Ejecutara busqueda de muni ctn y cass
                idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
                idCanton=(int) (long) cantones.get(idCtn-1).getId();
                idCaserio=(int) (long) caserios.get(idCas-1).getId();
                criaderos=utilidades.obtenerCriaderosByIds(daoSession,idMunicipio,idCanton,idCaserio);
            }else{
                //ejecutara busqueda solo de municipio
                idMunicipio=(int)(long) municipios.get(idMuni-1).getId();
                criaderos=utilidades.obtenerCriaderosByIds(daoSession,idMunicipio,idCanton,idCaserio);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_agregar) {
            Intent agregarCriadero=new Intent(BuscarCriaderoActivity.this, AgregarCriaderoActivity.class);
            startActivity(agregarCriadero);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Llamado cuando se presinó el botón positivo en el dialogo de añadir estudiante.
     * @param dialog
     * @param student
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Student student) {
            Toast.makeText(this,"Si lo creo",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this,"No lo creo",Toast.LENGTH_LONG).show();
    }

}
