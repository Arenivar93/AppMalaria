package com.minsal.dtic.sinavec.Sincronizar;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.IOUtils;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SubirDatos extends AppCompatActivity {
    ImageView subirDatos;
    private DaoSession daoSession;
    TextView tvja;
    private SharedPreferences pref;
    String username, password;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_datos);
        daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        subirDatos = (ImageView) findViewById(R.id.subirDatos);
        tvja = (TextView) findViewById(R.id.tvja);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        username = pref.getString("userRem", "");
        password = pref.getString("passRem", "");

        subirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkinServer();

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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (PlCapturaAnopheles p : capturas) {
            String date = dateFormat.format(p.getFechaHoraReg());
            insert = "INSERT INTO prueba.PL_CAPTURA_ANOPHELES(id_captura, id_sibasi, id_tablet, id_semana_epidemiologica," +
                    "id_tipo_actividad, id_caserio, id_usuario_reg,id_estado, id_tipo_captura, total_mosquitos, total_anopheles," +
                    "casa_positiva, casa_inspeccionada, componente_inspeccionado, tiempo_colecta, fecha_hora_reg, propietario,fecha " +
                    ")VALUES (" + p.getId() + "," + p.getIdSibasi() + "," + p.getIdTablet() + "," + p.getIdSemanaEpidemiologica() + "," + p.getIdTipoActividad() + "," + p.getIdCaserio() + "," + p.getIdUsuarioReg() + "," +
                    "" + p.getIdEstado() + "," + p.getIdTipoCaptura() + "," + p.getTotalMosquitos() + "" +
                    "," + p.getTotalAnopheles() + "," + p.getCasaPositiva() + "," + p.getCasaInspeccionada() + "," + p.getComponenteInspeccionado() + "," + p.getTiempoColecta() + ",'" + date + "','" + p.getPropietario() + "','" + date + "');";

            joCapturas.put(String.valueOf(p.getId()), insert);
        }
        return joCapturas;
    }

    public void updateLocalCapturas(JSONObject joCapturas) {
        try {
            JSONArray jaCapturasIserted = joCapturas.getJSONArray("capturas");
            for (int i = 0; i < jaCapturasIserted.length(); i++) {
                String id = String.valueOf(jaCapturasIserted.get(i));
                PlCapturaAnophelesDao capDao = daoSession.getPlCapturaAnophelesDao();
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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (CtlPlCriadero c : criaderos) {
            String date = dateFormat.format(c.getFechaHoraMod());
            insert = "UPDATE ctl_pl_criadero SET latitud='" + c.getLatitud() + "',longitud='" + c.getLongitud() + "' WHERE id= '" + c.getId() + "'";
            joCriaderos.put(String.valueOf(c.getId()), insert);
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

    public void updateLocalPesquisas(JSONObject joPesquisas) {
        try {
            JSONArray jaPesquisasIserted = joPesquisas.getJSONArray("pesquisas");
            for (int i = 0; i < jaPesquisasIserted.length(); i++) {
                String id = String.valueOf(jaPesquisasIserted.get(i));
                PlPesquisaLarvariaDao pesDao = daoSession.getPlPesquisaLarvariaDao();
                PlPesquisaLarvaria pesUpdate = pesDao.loadByRowId(Long.parseLong(id));
                pesUpdate.setEstado_sync(0);
                pesDao.update(pesUpdate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkinServer() {
        boolean red = MetodosGlobales.compruebaConexion(getApplicationContext());
        if (!red) {
            Toast.makeText(getApplicationContext(), "Lo sentimos no tiene conexion a Internet", Toast.LENGTH_SHORT).show();

        } else {
            String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/login_check";
            RequestQueue cola = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                token = jsonObject.getString("token");
                                sendData(token);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error!!Por favor contacta al administrador" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error!Por favor contacta al administrador" + String.valueOf(error), Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("Content-Type", "application/json");
                    parameters.put("_username", "mvalladares");
                    parameters.put("_password", "mvalladares");
                    return parameters;
                }

            };
            cola.add(stringRequest);
        }
    }

    private void sendData(final String token) {
        long startTime = System.currentTimeMillis();
        String tkn = token;
        try {
            JSONArray ja = new JSONArray();

            JSONObject jo = new JSONObject();
            jo.put("pl_capturas_insert", getInsertCapturas());
            jo.put("criaderos_update",   getUpdateCriaderos());
            jo.put("pl_pesquisas_insert",getInsertPesquisas());
            ja.put(jo);
            final int cantidad_registro = getInsertCapturas().length() + getInsertPesquisas().length() + getUpdateCriaderos().length();
            String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/entomologicas";
            RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url, ja,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (response != null) {

                                    tvja.setText(response.toString());
                                    Log.i("**reponse**",response.toString());

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                Log.i("***error",error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", "Bearer " + token);
                    return params;
                }
            };
            postRequest.setShouldCache(false);
            //rq.getCache().clear();
            rq.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
