package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class DetalleSemanaActivity extends AppCompatActivity {
    private DaoSession daoSession;
    Utilidades u;
    private List<PlPesquisaLarvaria> pesquisasSemana;
    int id_semana;
    ListView lvPesquisasSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_semana);
        daoSession      =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
        lvPesquisasSemana = (ListView)findViewById(R.id.lvPesquisasSemana);
        Bundle bundle = getIntent().getExtras();
        id_semana = Integer.parseInt(bundle.getString("id_semana"));
        final ArrayList<String> capturasSemana = listaAdapter();
        AdapterCapturas adapter =new AdapterCapturas(this,capturasSemana);
        lvPesquisasSemana.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Toast.makeText(getApplicationContext(),String.valueOf(id_semana),Toast.LENGTH_LONG).show();

    }

    public ArrayList<String> listaAdapter(){
        Utilidades u = new Utilidades(daoSession);
        pesquisasSemana = u.loadPesquisasDetalleSemana(id_semana);
        ArrayList<String> lista = new ArrayList<String>();
        List<PlPesquisaLarvaria> objList = pesquisasSemana;
        for (PlPesquisaLarvaria p: objList){
            lista.add(p.getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre()
                    +"-"+p.getCtlCaserio().getCtlCanton().getNombre()
                    +"-"+p.getCtlCaserio().getNombre()
                    +"-"+p.getIdSemanaEpidemiologica()
                    +"-"+p.getCtlPlCriadero().getNombre()
                    +"-"+p.getAnophelesUno()
                    +"-"+p.getAnophelesDos()
            ) ;
        }
        return lista;
    }


}
