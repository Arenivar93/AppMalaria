package com.minsal.dtic.sinavec.Sincronizar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubirDatos extends AppCompatActivity {
    ImageView subirDatos;
    private DaoSession daoSession;
    TextView tvja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_datos);
        daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        subirDatos = (ImageView)findViewById(R.id.subirDatos);
        tvja = (TextView) findViewById(R.id.tvja);



        subirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long startTime = System.currentTimeMillis();
                try {
                    JSONObject jo = new JSONObject();
                    jo.put("pl_capturas_insert",getInsertCapturas());
                    jo.put("criaderos_update",getUpdateCriaderos());
                    jo.put("pl_pesquisas_insert",getInsertPesquisas());
                    final int cantidad_registro= getInsertCapturas().length()+getInsertPesquisas().length()+getUpdateCriaderos().length();
                    String url = "http://10.168.10.80/tablets/subirData.php";
                    RequestQueue rq  = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jo,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject joRespuesta = response;
                                       // updateLocalCapturas(joRespuesta); // aqui hay que cambiar ya que se envia la respuesta completa mejor enviar el array especico
                                      //  updateLocalPesquisas(joRespuesta);
                                        Log.i("*cantidad de registros*",String.valueOf(cantidad_registro));
                                          tvja.setText(joRespuesta.getString("capturas"));
                                            Log.i("***criaderos****",joRespuesta.getString("criaderos_update"));
                                            Log.i("***pesquisas****",joRespuesta.getString("pesquisas"));


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    rq.add(postRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long endTime = System.currentTimeMillis() - startTime;
                Toast.makeText(getApplicationContext(),"duraccion"+endTime,Toast.LENGTH_LONG).show();


            }
        });
    }

    public JSONObject getInsertCapturas() throws JSONException {
        JSONObject joCapturas = new JSONObject();
        String insert;
        List<PlCapturaAnopheles> capturas;

        PlCapturaAnophelesDao capDao = daoSession.getPlCapturaAnophelesDao();
        capturas = capDao.queryBuilder().where(PlCapturaAnophelesDao.Properties.Estado_sync.eq(1)).list();
        SimpleDateFormat dateFormat;
        dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for (PlCapturaAnopheles p: capturas){
            String date = dateFormat.format(p.getFechaHoraReg());
            insert = "INSERT INTO prueba.PL_CAPTURA_ANOPHELES(id_captura, id_sibasi, id_tablet, id_semana_epidemiologica," +
                        "id_tipo_actividad, id_caserio, id_usuario_reg,id_estado, id_tipo_captura, total_mosquitos, total_anopheles," +
                        "casa_positiva, casa_inspeccionada, componente_inspeccionado, tiempo_colecta, fecha_hora_reg, propietario,fecha " +
                        ")VALUES ("+p.getId()+","+p.getIdSibasi()+","+p.getIdTablet()+","+p.getIdSemanaEpidemiologica()+","+p.getIdTipoActividad()+","+p.getIdCaserio()+","+p.getIdUsuarioReg()+"," +
                        ""+p.getIdEstado()+","+p.getIdTipoCaptura()+","+p.getTotalMosquitos()+"" +
                        ","+p.getTotalAnopheles()+","+p.getCasaPositiva()+","+p.getCasaInspeccionada()+","+p.getComponenteInspeccionado()+","+p.getTiempoColecta()+",'"+date+"','"+p.getPropietario()+"','"+date+"');";

                  joCapturas.put(String.valueOf(p.getId()),insert);
        }
        return joCapturas;
    }

    public void updateLocalCapturas(JSONObject joCapturas){
        try {
            JSONArray jaCapturasIserted = joCapturas.getJSONArray("capturas");
            for (int i = 0; i <jaCapturasIserted.length() ; i++) {
                String id = String.valueOf(jaCapturasIserted.get(i));
                PlCapturaAnophelesDao capDao= daoSession.getPlCapturaAnophelesDao();
                PlCapturaAnopheles capUpdate = capDao.loadByRowId(Long.parseLong(id));
                capUpdate.setEstado_sync(0);
                capDao.update(capUpdate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject getUpdateCriaderos() throws JSONException {
        JSONObject joCriaderos = new JSONObject();
        String insert = "";
        List<CtlPlCriadero> criaderos = new ArrayList<CtlPlCriadero>();
        CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();
        criaderos = criaderoDao.queryBuilder().where(CtlPlCriaderoDao.Properties.Estado_sync.eq(2)).list();
        SimpleDateFormat dateFormat;
        dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for (CtlPlCriadero c: criaderos){
            String date = dateFormat.format(c.getFechaHoraMod());
            insert = "UPDATE ctl_pl_criadero SET latitud='"+c.getLatitud()+"',longitud='"+c.getLongitud()+"' WHERE id= '"+c.getId()+"'" ;
            joCriaderos.put(String.valueOf(c.getId()),insert);
        }
        return joCriaderos;
    }

    public JSONObject getInsertPesquisas() throws JSONException {
        JSONObject joPesquisas = new JSONObject();
        String insert = "";
        List<PlPesquisaLarvaria> pesquisas;
        PlPesquisaLarvariaDao pesDao = daoSession.getPlPesquisaLarvariaDao();
        pesquisas = pesDao.queryBuilder().where(PlPesquisaLarvariaDao.Properties.Estado_sync.eq(1)).list();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (PlPesquisaLarvaria p : pesquisas) {
            String date = dateFormat.format(p.getFecha());
            insert = "INSERT INTO pl_pesquisa_larvaria(id_pesquisa, id_tablet, id_sibasi, id_semana_epidemiologica," +
                    "id_criadero, id_caserio, id_usuario_reg, id_estado,fecha_hora_reg, indice_larvario, anopheles_dos," +
                    "anopheles_uno, culicinos_uno, culicinos_dos, pupa, numero_cucharonada,ancho, largo, fecha)" +
                    "VALUES ('" + p.getId() + "', '" + p.getIdTablet() + "'" +
                    ",'" + p.getIdSibasi() + "', '" + p.getIdSemanaEpidemiologica() + "', '" + p.getIdCriadero() + "', '" + p.getIdCaserio() + "', " +
                    "'" + p.getIdUsuarioReg() + "','" + p.getIdEstado() + "', '" + p.getFechaHoraReg() + "', '" + p.getIndiceLarvario() + "'," +
                    "'" + p.getAnophelesDos() + "', '" + p.getAnophelesUno() + "', '" + p.getCulicinosUno() + "', '" + p.getCulicinosDos() + "', '" + p.getPupa() + "', " +
                    "'" + p.getNumeroCucharonada() + "', '" + p.getAncho() + "', '" + p.getLargo() + "','" + date + "');";
            joPesquisas.put(String.valueOf(p.getId()), insert);
        }
        return joPesquisas;
    }
    public void updateLocalPesquisas(JSONObject joPesquisas){
        try {
            JSONArray jaPesquisasIserted = joPesquisas.getJSONArray("pesquisas");
            for (int i = 0; i <jaPesquisasIserted.length() ; i++) {
                String id = String.valueOf(jaPesquisasIserted.get(i));
                PlPesquisaLarvariaDao pesDao= daoSession.getPlPesquisaLarvariaDao();
                PlPesquisaLarvaria pesUpdate = pesDao.loadByRowId(Long.parseLong(id));
                pesUpdate.setEstado_sync(0);
                pesDao.update(pesUpdate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
