package com.minsal.dtic.sinavec.CRUD.gotaGruesa.activityGotaGruesa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesa;
import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesaDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterGotaGruesaSemana;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DetalleSemanaGotaGruesa extends AppCompatActivity {
    private DaoSession daoSession;
    private ListView lvDtalleGotaSemana;
    private TextView tvDetallegotaSemama;
    int idSemana;
    Button btnNuevaGotaSemana;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_semana_gota_gruesa);
        Bundle bundle = getIntent().getExtras();
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        lvDtalleGotaSemana = (ListView)findViewById(R.id.lvDtalleGotaSemana);
        tvDetallegotaSemama = (TextView) findViewById(R.id.tvDetallegotaSemama);
        btnNuevaGotaSemana = (Button) findViewById(R.id.btnNuevaGotaSemana);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
         final String semana = bundle.getString("id_semana");
         idSemana = Integer.parseInt(semana);
        tvDetallegotaSemama.setText("Detalle de Gota Gruesa de la semana: "+semana);
        AdapterGotaGruesaSemana adapter = new AdapterGotaGruesaSemana(this,loadPesquisasDetalleSemana(Integer.parseInt(semana)));
        lvDtalleGotaSemana.setAdapter(adapter);
        lvDtalleGotaSemana.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long tipoEmpleado =  prefs.getLong("idTipoEmpleado",0);
                if (tipoEmpleado!= 3){
                    //btnNuevo.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Usuario no autorizado para editar gota gruesa",Toast.LENGTH_SHORT).show();
                }else {
                    long idRegisto = loadPesquisasDetalleSemana(idSemana).get(i).getId();
                    long idColvolClave = loadPesquisasDetalleSemana(idSemana).get(i).getIdClave();
                    Intent intent = new Intent(getApplicationContext(), GotaGruesaEditActivity.class);
                    intent.putExtra("idRegistro", idRegisto);
                    intent.putExtra("idSemana", semana);
                    intent.putExtra("idColvolClave", idColvolClave);
                    startActivity(intent);
                }
            }
        });


        btnNuevaGotaSemana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long tipoEmpleado =  prefs.getLong("idTipoEmpleado",0);
                if (tipoEmpleado!= 3){
                    //btnNuevo.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Usuario no autorizado para Ingresar gota gruesa",Toast.LENGTH_SHORT).show();
                }else {
                    Intent nueva = new Intent(getApplicationContext(), nuevaGotaGruesaActivity.class);
                    startActivity(nueva);
                    finish();
                }
            }
        });
    }
    public List<PlGotaGruesa> loadPesquisasDetalleSemana(int semana) {
        PlGotaGruesaDao gotadao = daoSession.getPlGotaGruesaDao();
        List<PlGotaGruesa> gota  = new ArrayList<PlGotaGruesa>();
        QueryBuilder<PlGotaGruesa> qb = gotadao.queryBuilder().where(PlGotaGruesaDao.Properties.IdSemanaEpidemiologica.eq(semana));
        gota = qb.list();
        return gota;
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(getApplicationContext(),ListGotaGruesaActivity.class);
        startActivity(i);
        finish();
    }
}
