package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.adapters.AdapterSemanaPesquisa;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetalleSemanaActivity extends AppCompatActivity {
    private DaoSession daoSession;
    Utilidades u;
    private List<PlPesquisaLarvaria> pesquisasSemana;
    int id_semana;
    ListView lvPesquisasSemana;
    TextView tvDetalleSemana;
    Button nuevapesquisa;
    AdapterSemanaPesquisa adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_semana);
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvPesquisasSemana = (ListView)findViewById(R.id.lvPesquisasSemana);
        tvDetalleSemana   = (TextView)findViewById(R.id.tvDetalleSemana);
        nuevapesquisa     = (Button)findViewById(R.id.nuevaPesquisa);
        Bundle bundle     = getIntent().getExtras();
        if (bundle!=null){
            if(bundle.get("bandera_delete")!=null){
                id_semana = Integer.parseInt(bundle.getString("id_semana2"));
            }else {
                id_semana = Integer.parseInt(bundle.getString("id_semana"));
            }
        }

        tvDetalleSemana.setText(String.format("Detalle de la semana: %s", String.valueOf(id_semana)));
        final ArrayList<String> capturasSemana = listaAdapter();
         adapter =new AdapterSemanaPesquisa(this,capturasSemana);
        lvPesquisasSemana.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        nuevapesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),pesquisaLarvaria.class);
                startActivity(i);
                finish();
            }
        });
        lvPesquisasSemana.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                long id = pesquisasSemana.get(position).getId();
                Intent i = new Intent(getApplicationContext(),EditPesquisaActivity.class);
                i.putExtra("id_pesquisa",id);
                i.putExtra("id_semana",id_semana);
                i.putExtra("action","edit");
                startActivity(i);
                finish();
            }
        });
        lvPesquisasSemana.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                reloadAllData();
                return true;
            }
        });




    }// fin del metodo onCreate
    private void reloadAllData(){
        adapter.getData().clear();
        adapter.getData().addAll(listaAdapter());
        adapter.notifyDataSetChanged();
    }

    public ArrayList<String> listaAdapter(){
        Utilidades u = new Utilidades(daoSession);
        pesquisasSemana = u.loadPesquisasDetalleSemana(id_semana);
        ArrayList<String> lista = new ArrayList<String>();
        List<PlPesquisaLarvaria> objList = pesquisasSemana;
        for (PlPesquisaLarvaria p: objList){
            DateFormat fecha = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
            String miFecha=fecha.format(p.getFecha());
            lista.add(p.getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre()
                    +"-"+p.getCtlCaserio().getCtlCanton().getNombre()
                    +"-"+p.getCtlPlCriadero().getNombre()
                    +"-"+p.getAnophelesUno()
                    +"-"+p.getAnophelesDos()
                    +"-"+p.getNumeroCucharonada()
                    +"-"+miFecha
            ) ;
        }
        return lista;
    }
    @Override
    public void onBackPressed() {
        Intent list =new Intent(getApplicationContext(),ListPesquisaActivity.class);
        startActivity(list);
        finish();
    }
    public void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetalleSemanaActivity.this);
        builder.setMessage(Html.fromHtml("<font color='#FF0000'><b>¿Seguro que desea eliminar la Pesquisa Larvaria?</b></font>"))
                .setNegativeButton(Html.fromHtml("Cancelar"), null)
                .setPositiveButton(Html.fromHtml("Sí, Eliminar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getApplicationContext(), "La Captura se eliminó con éxito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DetalleSemanaActivity.class);
                        intent.putExtra("id_semana2", String.valueOf(id_semana));
                        intent.putExtra("bandera_delete", "bandera_delete");
                        startActivity(intent);
                        finish();
                    }
                })
                .setCancelable(false);
        //.create().show();
        AlertDialog a = builder.create();
        a.show();
        Button btnPositivo = a.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPositivo.setTextColor(Color.RED);
        Button btnNegativo = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnNegativo.setTextColor(Color.GREEN);
    }

}
