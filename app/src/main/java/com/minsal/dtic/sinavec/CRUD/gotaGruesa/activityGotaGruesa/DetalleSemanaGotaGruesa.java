package com.minsal.dtic.sinavec.CRUD.gotaGruesa.activityGotaGruesa;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_semana_gota_gruesa);
        Bundle bundle = getIntent().getExtras();
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        lvDtalleGotaSemana = (ListView)findViewById(R.id.lvDtalleGotaSemana);
        tvDetallegotaSemama = (TextView) findViewById(R.id.tvDetallegotaSemama);
        btnNuevaGotaSemana = (Button) findViewById(R.id.btnNuevaGotaSemana);
         String semana = bundle.getString("id_semana");
         idSemana = Integer.parseInt(semana);
        tvDetallegotaSemama.setText("Detalle de Gota Gruesa de la semana: "+semana);
        AdapterGotaGruesaSemana adapter = new AdapterGotaGruesaSemana(this,loadPesquisasDetalleSemana(Integer.parseInt(semana)));
        lvDtalleGotaSemana.setAdapter(adapter);
        lvDtalleGotaSemana.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               long idRegisto = loadPesquisasDetalleSemana(idSemana).get(i).getId();
                Intent intent = new Intent(getApplicationContext(),GotaGruesaEditActivity.class);
                intent.putExtra("idRegistro",idRegisto);
                startActivity(intent);
            }
        });


        btnNuevaGotaSemana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nueva = new Intent(getApplicationContext(),nuevaGotaGruesaActivity.class);
                startActivity(nueva);
                finish();
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
}
