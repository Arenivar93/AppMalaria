package com.minsal.dtic.sinavec;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
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

        daoSession=((MyMalaria)getApplicationContext()).getDaoSession();
        utilidades=new Utilidades(daoSession);

        municipios=utilidades.loadspinnerMunicipio(3);
        listaMunicipio=utilidades.obtenerListaMunicipio(municipios);

        //loadSpinerMun();

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


        encabezadoTabla(getApplicationContext());
        buscarCriaderos(getApplicationContext());

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
                    Toast.makeText(parent.getContext(),cantones.get(position-1).getNombre()+"",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),"Buscara Criadero",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void buscarCriaderos(Context context) {
        Toast.makeText(this,"Llena tabla",Toast.LENGTH_LONG).show();
        try
        {
            //daoSession.getDatabase().beginTransaction();
            String selectQuery = "SELECT id,nombre FROM ctl_pl_criadero";
            Cursor cursor = daoSession.getDatabase().rawQuery(selectQuery,null);
            //Toast.makeText(this,cursor.getCount()+"",Toast.LENGTH_LONG).show();
            while (cursor.moveToNext()) {
                // Read columns data
                final int id= cursor.getInt(0);
                String nombre= cursor.getString(1);
                //String municipio= cursor.getString(cursor.getColumnIndex("nombre"));
               // String canton= cursor.getString(cursor.getColumnIndex("nombre"));
               // String caserio= cursor.getString(cursor.getColumnIndex("nombre"));

                // dara rows
                TableRow row = new TableRow(context);
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                String[] colText={nombre,nombre,nombre,nombre};
                for(String text:colText) {
                    TextView tv = new TextView(context);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setTextSize(16);
                    tv.setPadding(85, 5, 85, 5);
                    tv.setText(text);
                    tv.setTextColor(Color.BLACK);
                    row.addView(tv);
                }
                // Creation  button
                final Button button = new Button(context);
                button.setText("Map");
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Asignara Coordenadas id:"+id,Toast.LENGTH_LONG).show();
                    }
                });
                row.addView(button);
                tablaCriaderos.addView(row);
            }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        /*finally{
            daoSession.getDatabase().endTransaction();
            // End the transaction.
            //daoSession.getDatabase().close();
            // Close database
        }*/
    }

    private void encabezadoTabla(Context context){
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"NOMBRE","MUNICIPIO","CANTON","CASERIO","OPCIONES"};
        for(String c:headerText) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(85, 5, 85, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tablaCriaderos.addView(rowHeader);
    }
}
