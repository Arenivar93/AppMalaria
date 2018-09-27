package com.minsal.dtic.sinavec.CRUD.seguimientoBotiquin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria.DetalleSemanaActivity;
import com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria.EditPesquisaActivity;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquin;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquinDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;

public class EditSeguimiento extends AppCompatActivity {
    Object id_seguimiento;
    Object id_semana;
    private DaoSession daoSession;
    private SharedPreferences pref;
    PlSeguimientoBotiquinDao segDao;
    PlSeguimientoBotiquin segEdit;
    TextView tvNombre;
    TextInputEditText titMuestras,titPersonas;
    RadioButton rdSupervisado, rdVisitado;
    Switch swRiesgo;
    ImageView imActualizar, imEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_seguimiento);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        titMuestras = (TextInputEditText)findViewById(R.id.titMuestrasEdit);
        titPersonas = (TextInputEditText)findViewById(R.id.titPersonasEdit);
        rdSupervisado = (RadioButton)findViewById(R.id.rdbSupervisadoEdit);
        rdVisitado    = (RadioButton)findViewById(R.id.rdbVisitadoEdit);
        imActualizar  = (ImageView)findViewById(R.id.imActualizarSeguimiento);
        imEliminar    = (ImageView)findViewById(R.id.imEliminarSeguimiento);
        swRiesgo      = (Switch)findViewById(R.id.swRiesgoSocialEdit);
        tvNombre      = (TextView)findViewById(R.id.tvNombreBotiquinEdi);


        Bundle bundle = getIntent().getExtras();
        id_seguimiento = bundle.get("id_seguimiento");
        id_semana = bundle.get("id_semana");
        daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        segDao = daoSession.getPlSeguimientoBotiquinDao();
        try {
            segEdit = segDao.loadByRowId(Long.parseLong((String) id_seguimiento));
            String botiquin = "Boqituin: "+bundle.getString("nombre")+"    Clave:"+segEdit.getClave().getClave();
            tvNombre.setText(botiquin);
            int supervisado = segEdit.getSupervisado();
            int muestras = segEdit.getNumeroMuestra();
            int riesgo = segEdit.getEnRiesgo();
            int personas = segEdit.getNumeroPersonaDivulgo();

            titMuestras.setText(String.valueOf(muestras));
            titPersonas.setText(String.valueOf(personas));
            if (supervisado == 1) {
                rdSupervisado.setChecked(true);
            } else {
                rdVisitado.setChecked(true);
            }
            if (riesgo==1){
                swRiesgo.setChecked(true);
            }
            imEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirm();
                }
            });
            imActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String muestras = titMuestras.getText().toString().trim();
                    String personas = titPersonas.getText().toString().trim();
                    String accion = rdSupervisado.isChecked()? "supervisar" : "visitar"; // si es supervision sera 1 sino sera 2
                    int riesgo = swRiesgo.isChecked()? 1 : 0;// si es switch esta marcado el riesgo sera uno sino sera 0
                    updateSeguimiento(accion,riesgo,Integer.parseInt(muestras),Integer.parseInt(personas));
                    Intent intent = new Intent(getApplicationContext(), DetalleSeguimientoSemana.class);
                    intent.putExtra("id_semana2",String.valueOf(id_semana));
                    intent.putExtra("bandera_delete","bandera_delete");
                    startActivity(intent);
                    finish();

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }//fin oncreate
    public void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditSeguimiento.this);
        builder.setMessage(Html.fromHtml("<font color='#FF0000'><b>¿Seguro que desea eliminar el seguimienot a botiquin?</b></font>"))
                .setNegativeButton(Html.fromHtml("Cancelar"), null)
                .setPositiveButton(Html.fromHtml("Sí, Eliminar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        segDao.deleteByKey(Long.parseLong((String) id_seguimiento));
                        Toast.makeText(getApplicationContext(), "La Captura se eliminó con éxito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DetalleSeguimientoSemana.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DetalleSeguimientoSemana.class);
        intent.putExtra("id_semana2",String.valueOf(id_semana));
        intent.putExtra("bandera_delete","bandera_delete");
        startActivity(intent);
        finish();
    }

    public void updateSeguimiento(String accion,int riesgo,int muestras,int personas){
        PlSeguimientoBotiquinDao segDao = daoSession.getPlSeguimientoBotiquinDao();
        if (accion.equals("visitar")){
            segEdit.setVisitado(1);
        }else{
            segEdit.setSupervisado(1);
        }
        if (riesgo==1){
            segEdit.setEnRiesgo(1);
        }else{
            segEdit.setEnRiesgo(0);
        }
        segEdit.setNumeroMuestra(muestras);
        segEdit.setNumeroPersonaDivulgo(personas);//falta agregar usuario y fecha modifico
        segDao.update(segEdit);

    }
}
