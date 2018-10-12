package com.minsal.dtic.sinavec.CRUD.capturaAnopheles;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.adapters.AdapterCustom;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetalleCapturaSemanaActivity extends AppCompatActivity {
    private DaoSession daoSession;
    Button btnNuevaCaptura;
    Bundle bundle;
    ArrayList<PlCapturaAnopheles> capturas;
    ListView lvCaptura;
    List<PlCapturaAnopheles> capturasAnopheles;
    Utilidades u;
    TextView tvDetalle;
    int semana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_captura_semana);
        btnNuevaCaptura     = (Button)findViewById(R.id.btnNuevaCaptura);
        lvCaptura           = (ListView)findViewById(R.id.lvCapturaSemana);
        tvDetalle = (TextView)findViewById(R.id.tvDetalleSemanaCaptura);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){actionBar.setDisplayHomeAsUpEnabled(true);}
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
        bundle = getIntent().getExtras();
        if (bundle!=null){
            if(bundle.get("bandera_delete")!=null){
                semana = Integer.parseInt(bundle.getString("id_semana2"));
            }else {
                semana = Integer.parseInt(bundle.getString("id_semana"));
            }
        }
        tvDetalle.setText(String.format("Detalle de capturas de la semana: %d", semana));
        final ArrayList<String> capturasSemana = listaAdapter();
        AdapterCustom adapter =new AdapterCustom(this,capturasSemana);
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
                long id =  capturasAnopheles.get(position).getId();
                Intent nuevo = new Intent(getApplicationContext(), CapturaAnopheles.class);
                nuevo.putExtra("accion","edit");
                nuevo.putExtra("id_semana",semana);
                nuevo.putExtra("id",id);
                startActivity(nuevo);
                //finish();
            }
        });
    }
    public ArrayList<String> listaAdapter(){
        ArrayList<String> lista = new ArrayList<String>();
        if (semana > 0){
            Utilidades u = new Utilidades(daoSession);
            capturasAnopheles = u.loadListcapturas(semana);
            List<PlCapturaAnopheles> objList = capturasAnopheles;
            for (PlCapturaAnopheles p: objList){
                DateFormat fecha = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
                String miFecha=fecha.format(p.getFechaHoraReg());
                lista.add(p.getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre()
                        +"-"+p.getCtlCaserio().getCtlCanton().getNombre()
                        +"-"+p.getCtlCaserio().getNombre()
                        +"-"+p.getPlTipoCaptura().getNombre()
                        +"-"+p.getPropietario()
                        +"-"+p.getTotalAnopheles()
                        +"-"+miFecha);
            }

        }else {
            Toast.makeText(getApplicationContext(),"Oops! hubo un error",Toast.LENGTH_LONG).show();
        }

        return lista;
    }
}

