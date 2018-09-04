package com.minsal.dtic.sinavec;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListCapturaActivity extends AppCompatActivity {
    Button btnNuevaCaptura;
    TableLayout tbListCaptura;
    ArrayList<PlCapturaAnopheles> capturas;
    ListView lvCaptura;
    String[] listaArray;

    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_captura);
        btnNuevaCaptura = (Button)findViewById(R.id.btnNuevaCaptura);
        lvCaptura = (ListView)findViewById(R.id.lvCaptura);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        ArrayList<String> capturasSemana = getCapturas();

        AdapterCapturas adapter =new AdapterCapturas(this,capturasSemana);
        lvCaptura.setAdapter(adapter);
        /*AlertDialog.Builder builder = new AlertDialog.Builder(ListCapturaActivity.this);
        builder.setMessage("prueba alert")
                .setNegativeButton("Retry", null)
                .create().show();*/

        btnNuevaCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CapturaAnopheles.class);
                i.putExtra("accion","Nueva");
                startActivity(i);

            }
        });

        lvCaptura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent nuevo = new Intent(getApplicationContext(), CapturaAnopheles.class);
                nuevo.putExtra("accion","edit");
                nuevo.putExtra("position",position);
                startActivity(nuevo);
            }
        });
    }
    public ArrayList<String> getCapturas(){
        ArrayList<String> caps = new ArrayList<String>();
        String sqlQUERY = "SELECT cas.nombre,can.nombre, mun.nombre, cap.propietario, cap.total_mosquitos,cap.total_anopheles," +
                "cap.id_semana_epidemiologica,cap.id \n" +
                "FROM PL_CAPTURA_ANOPHELES cap\n" +
                "INNER JOIN CTL_CASERIO cas on(cas.id=cap.ID_CASERIO)\n" +
                "INNER JOIN CTL_CANTON can on(can.id=cas.ID_CANTON)\n" +
                "INNER JOIN CTL_MUNICIPIO mun on(mun.id=can.ID_MUNICIPIO) ORDER BY cap.id DESC";
        Cursor c = daoSession.getDatabase().rawQuery(sqlQUERY, null);
        if (c.moveToFirst()) {
            do {
                caps.add(c.getString(2)+"-"+c.getString(1)+"-"+c.getString(0)+"-"+c.getString(3)+"-"+c.getString(5)+"-"+c.getString(6)+"-"+c.getString(7));

            } while (c.moveToNext());
        }
        c.close();
        return caps;
    }
}
