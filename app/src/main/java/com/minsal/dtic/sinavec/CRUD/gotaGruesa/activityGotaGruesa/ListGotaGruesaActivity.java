package com.minsal.dtic.sinavec.CRUD.gotaGruesa.activityGotaGruesa;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.CRUD.Colvol.fragmentColvol.verColvolMapDialogFragment;
import com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa.seleccionProcedenciaDialogFragment;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesa;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquin;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCustom;
import com.minsal.dtic.sinavec.adapters.AdapterGotaGruesa;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class ListGotaGruesaActivity extends AppCompatActivity implements seleccionProcedenciaDialogFragment.procedenciaDialogListener{
    Button btnNuevo;
    ArrayList<PlSeguimientoBotiquin> gotas;
    ListView listaGotas;
    List<PlSeguimientoBotiquin> gotaGruesa;
    Utilidades u;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gota_gruesa);
        btnNuevo      = (Button)findViewById(R.id.btnNuevaGota);
        listaGotas = (ListView)findViewById(R.id.lvGotaGruesa);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        daoSession =((MyMalaria)getApplicationContext()).getDaoSession();
        u.fragment = 0;
        final ArrayList<String> gotasList = listaAdapter();
        AdapterGotaGruesa adapter =new AdapterGotaGruesa(this,gotasList);
        listaGotas.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),nuevaGotaGruesaActivity.class);
                startActivity(i);
            }
        });
        listaGotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String fila = gotasList.get(position);
                String semana_g= fila.substring(5,7); //semana_g probablelemte trae un gion si es menor a 10
                String id_semana= semana_g.replace("-","");
                Intent intent = new Intent(getApplicationContext(),DetalleSemanaGotaGruesa.class);
                intent.putExtra("id_semana",id_semana);
                startActivity(intent);
                finish();
            }
        });
    }

    public ArrayList<String> listaAdapter(){
        Utilidades u = new Utilidades(daoSession);
        ArrayList<String> lista= u.loadGotaBySemana();
        return lista;
    }

    @Override
    public void onBackPressed() {
        Intent m =new Intent(getApplicationContext(),MainActivity.class);
        m.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(m);
        finish();

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}
