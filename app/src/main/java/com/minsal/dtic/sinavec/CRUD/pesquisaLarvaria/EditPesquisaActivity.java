package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditPesquisaActivity extends AppCompatActivity {
    ImageView imActualizar,imElimnar;
    TextInputEditText titAnopheles12,titAnopheles34,titCulicino12,titCulicino34;
    TextInputEditText titPupa, titCucharonada, titLargo,titAncho;

    TextView tvIndiceEdit,tvCriaderoEdit;
    PlPesquisaLarvaria pesEdit;
    private DaoSession daoSession;
    private PlPesquisaLarvariaDao pesDao;
    private Object id_semana,id_pesquisa;
    long idCaserio;
    private SharedPreferences pref;
    private int estado_sync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pesquisa);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        titAnopheles12  = (TextInputEditText)findViewById(R.id.titAnopheles12Edit);
        titAnopheles34  = (TextInputEditText)findViewById(R.id.titAnopheles34Edit);
        titCulicino12   = (TextInputEditText)findViewById(R.id.titCulicino12Edit);
        titCulicino34   = (TextInputEditText)findViewById(R.id.titCulicino34Edit);
        titPupa         = (TextInputEditText)findViewById(R.id.titPupaEdit);
        titCucharonada  = (TextInputEditText)findViewById(R.id.titCucharonadaEdit);
        titLargo        = (TextInputEditText)findViewById(R.id.titLargoEdit);
        titAncho        = (TextInputEditText)findViewById(R.id.titAnchoEdit);
        imActualizar    = (ImageView)findViewById(R.id.imActualizarPesquisa);
        tvCriaderoEdit  = (TextView)findViewById(R.id.tvCriaderoEdit);
        tvIndiceEdit    = (TextView)findViewById(R.id.tvIndiceEdit);
        imElimnar       = (ImageView)findViewById(R.id.imEliminarPesquisa);

        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        pesDao          = daoSession.getPlPesquisaLarvariaDao();
        //id_pesquisa     =bundle.get("id_pesquisa");
        id_semana       =bundle.get("id_semana");
       // Toast.makeText(getApplicationContext(),String.valueOf(id_pesquisa),Toast.LENGTH_LONG).show();
        try {
            id_pesquisa = bundle.get("id_pesquisa");
            pesEdit = pesDao.loadByRowId((long)(id_pesquisa));
            if (pesEdit.getEstado_sync()==0){
                imActualizar.setVisibility(View.INVISIBLE);
                imElimnar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Este registro ya fue sincronizado, solo puede editarse en la web",Toast.LENGTH_LONG).show();
            }
            int pupa       = pesEdit.getPupa();
            float ancho    = pesEdit.getAncho();
            float largo    = pesEdit.getLargo();
            estado_sync    = pesEdit.getEstado_sync();
            String fecha   = pesEdit.getFechaHoraReg();
            int anopheles12= pesEdit.getAnophelesUno();
            int anopheles34= pesEdit.getAnophelesDos();
            int culicino12 = pesEdit.getCulicinosUno();
            int culicino34 = pesEdit.getCulicinosDos();
            int cucharonda = pesEdit.getNumeroCucharonada();
            String criadero = pesEdit.getCtlPlCriadero().getNombre();
            float indice   =pesEdit.getIndiceLarvario();
            idCaserio       =pesEdit.getCtlPlCriadero().getIdCaserio();
            tvCriaderoEdit.setText(String.format("Pesquisa realizada en %s  la fecha: %s", criadero,fecha));
            titAnopheles12.setText(String .valueOf(anopheles12));
            titAnopheles34.setText(String.valueOf(anopheles34));
            titCucharonada.setText(String.valueOf(cucharonda));
            titCulicino12.setText(String.valueOf(culicino12));
            titCulicino34.setText(String.valueOf(culicino34));
            titLargo.setText(String .valueOf(largo));
            titAncho.setText(String.valueOf(ancho));
            titPupa.setText(String.valueOf(pupa));
            tvIndiceEdit.setText(String.valueOf(indice));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error al obtener los datos"+e.getMessage(),Toast.LENGTH_LONG).show();

        }
        imElimnar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();

            }
        });
        imActualizar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    boolean validate = validateData();
                    if (validate){
                        long idSibasi  = pref.getLong("idSibasiUser",0);
                        long idTablet  = pref.getLong("idTablet",0);
                        long idUsuario = pref.getLong("idUser",0);
                        String anopheles12 = titAnopheles12.getText().toString().trim();
                        String anopheles34 = titAnopheles34.getText().toString().trim();
                        String culicino12  = titCulicino12.getText().toString().trim();
                        String culicino34  = titCulicino34.getText().toString().trim();
                        String pupa        = titPupa.getText().toString().trim();
                        String cucharonada = titCucharonada.getText().toString().trim();
                        String largo       = titLargo.getText().toString().trim();
                        String ancho       = titAncho.getText().toString().trim();
                        String indice      = tvIndiceEdit.getText().toString().trim();
                        updatePesquisa(Integer.parseInt(anopheles12),Integer.parseInt(anopheles34),Integer.parseInt(culicino12),
                                Integer.parseInt(culicino34),Integer.parseInt(pupa),Integer.parseInt(cucharonada),Float.parseFloat(largo),
                                Float.parseFloat(ancho),estado_sync,idCaserio,idSibasi,idTablet, (int) idUsuario,Float.parseFloat(indice));
                        Intent intent = new Intent(getApplicationContext(), DetalleSemanaActivity.class);
                        intent.putExtra("id_semana2",String.valueOf(id_semana));
                        intent.putExtra("bandera_delete","bandera_delete");
                        startActivity(intent);
                        finish();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        titAnopheles34.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Toast.makeText(getActivity(),"antes",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Toast.makeText(getActivity(),"durante",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String anopheles = titAnopheles34.getText().toString().trim();
                String cucharonada = titCucharonada.getText().toString().trim();
                if(!TextUtils.isEmpty(anopheles) && !TextUtils.isEmpty(cucharonada)){
                    float indice=  calcularIndice(Float.parseFloat(anopheles),Float.parseFloat(cucharonada));
                    tvIndiceEdit.setText(String.valueOf(indice));
                }else{
                    tvIndiceEdit.setText(String.valueOf(0));
                }
            }
        });
        titCucharonada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String anopheles = titAnopheles34.getText().toString().trim();
                String cucharonada = titCucharonada.getText().toString().trim();
                if(!TextUtils.isEmpty(anopheles) && !TextUtils.isEmpty(cucharonada)){
                    float indice= calcularIndice(Float.parseFloat(anopheles),Float.parseFloat(cucharonada));
                    tvIndiceEdit.setText(String.valueOf(indice));
                }else{
                    tvIndiceEdit.setText(String.valueOf(0));
                }

            }
        });

    }

    public void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditPesquisaActivity.this);
        builder.setMessage(Html.fromHtml("<font color='#FF0000'><b>¿Seguro que desea eliminar la Pesquisa Larvaria?</b></font>"))
                .setNegativeButton(Html.fromHtml("Cancelar"), null)
                .setPositiveButton(Html.fromHtml("Sí, Eliminar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pesDao.deleteByKey((long) id_pesquisa);
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DetalleSemanaActivity.class);
        intent.putExtra("id_semana2",String.valueOf(id_semana));
        intent.putExtra("bandera_delete","bandera_delete");
        startActivity(intent);
        finish();
    }
    private void updatePesquisa(int anopheles12, int anopheles34,
                                int culicino12, int culicino34, int pupa, int cucharonada,
                                float largo, float ancho,int estado_sync,long idCaserio
            ,long idSibasi,long idTablet,int idUsuario,float indice) throws ParseException {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String prueba = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentTime);
        String fecha = dateFormat.format(currentTime);
        Date fec = dateFormat.parse(fecha);

        PlPesquisaLarvariaDao pesDao = daoSession.getPlPesquisaLarvariaDao();
        pesEdit.setAnophelesUno(anopheles12);
        pesEdit.setAnophelesDos(anopheles34);
        pesEdit.setCulicinosUno(culicino12);
        pesEdit.setCulicinosDos(culicino34);
        pesEdit.setAncho(ancho);
        pesEdit.setLargo(largo);
        pesEdit.setNumeroCucharonada(cucharonada);
        pesEdit.setPupa(pupa);
        pesEdit.setIndiceLarvario(indice);
        pesEdit.setFechaHoraMod(fecha);// se debe quitar not null
        pesEdit.setIdCaserio(idCaserio);
        pesEdit.setIdSibasi(idSibasi);
        pesEdit.setIdTablet(idTablet);
        pesEdit.setIdUsuarioMod(idUsuario);
       if (estado_sync==1){pesEdit.setEstado_sync(1);}else{pesEdit.setEstado_sync(2);}
        pesDao.update(pesEdit);
    }
    public float calcularIndice(float anopheles34,float cucharonada){
        float indice;
        if (cucharonada>0){
            indice = anopheles34 / cucharonada;
        }else{indice = 0;}

        return indice;
    }
    public boolean validateData(){
        boolean validate= false;
        if (TextUtils.isEmpty(titAnopheles12.getText().toString().trim())){
            titAnopheles12.setError("Cantidad de Anopheles estadío I o II requerida");
            titAnopheles12.requestFocus();

        }else if (TextUtils.isEmpty(titAnopheles34.getText().toString().trim())){
            titAnopheles34.setError("Cantidad de Anopheles estadío III o IV requerida");
            titAnopheles34.requestFocus();

        }else if (TextUtils.isEmpty(titCulicino12.getText().toString().trim())){
            titCulicino12.setError("Cantidad de Culicino estadío I o II requerida");
            titCulicino12.requestFocus();

        }else if (TextUtils.isEmpty(titCulicino34.getText().toString().trim())){
            titCulicino34.setError("Cantidad de Culicinos estadío III o IV requerida");
            titCulicino34.requestFocus();
        }else if (TextUtils.isEmpty(titPupa.getText().toString().trim())) {
            titPupa.setError("Cantidad de pupas requerida");
            titPupa.requestFocus();
        }else if (TextUtils.isEmpty(titCucharonada.getText().toString().trim())){
            titCucharonada.setError("Cantidad de cucharonadas requerida");
            titCucharonada.requestFocus();
        }else if (TextUtils.isEmpty(titLargo.getText().toString().trim())){
            titLargo.setError("Largo de criadero requerido");
            titLargo.requestFocus();
        }else if (TextUtils.isEmpty(titAncho.getText().toString().trim())){
            titAncho.setError("Ancho de criadero requerido");
            titAncho.requestFocus();
        }else{
            validate= true;
        }
        return validate;
    }

}
