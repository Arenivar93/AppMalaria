package com.minsal.dtic.sinavec.Sincronizar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.OkHttpClient.Builder;

public class SubirDatos extends AppCompatActivity {
    ImageView subirDatos;
    private DaoSession daoSession;
    TextView tvja,tvPesquisa;
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
        tvPesquisa = (TextView) findViewById(R.id.tvPesquisa);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        username = pref.getString("userRem", "");
        password = pref.getString("passRem", "");

        subirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/entomologicas";
                checkinServer();
                //String json ="hola";
               // send();

            }
        });
    }

    public JSONArray getInsertCapturas() throws JSONException {

        JSONArray joTotal = new JSONArray();

        List<PlCapturaAnopheles> capturas;
        PlCapturaAnophelesDao capDao = daoSession.getPlCapturaAnophelesDao();
        capturas = capDao.queryBuilder().where(PlCapturaAnophelesDao.Properties.Estado_sync.eq(1)).list();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (PlCapturaAnopheles p : capturas) {
            JSONObject jo = new JSONObject();
            String date = dateFormat.format(p.getFechaHoraReg());
            jo.put("id",p.getId());
            jo.put("fecha", date);
            jo.put("idTablet",p.getIdTablet());
            jo.put("idEstado", p.getIdEstado());
            jo.put("idSibasi" , p.getIdSibasi());
            jo.put("idCaserio", p.getIdCaserio());
            jo.put("idUsuario",p.getIdUsuarioReg());
            jo.put("propietario", p.getPropietario());
            jo.put("casaPositiva",  p.getCasaPositiva());
            jo.put("idTipoCaptura",  p.getIdTipoCaptura());
            jo.put("totalAnopheles",p.getTotalAnopheles());
            jo.put("tiempoColecta", p.getTiempoColecta());
            jo.put("totalMosquitos" ,p.getTotalMosquitos());
            jo.put("idTipoActividad",p.getIdTipoActividad());
            jo.put("idSemana" ,p.getIdSemanaEpidemiologica());
            jo.put("casaInspeccionada",p.getCasaInspeccionada());
            jo.put("componente", p.getComponenteInspeccionado());
            joTotal.put(jo);
        }
        return joTotal;
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

    public JSONArray getInsertPesquisas() throws JSONException {

        JSONArray joTotal = new JSONArray();
        List<PlPesquisaLarvaria> pesquisas;
        PlPesquisaLarvariaDao pesDao = daoSession.getPlPesquisaLarvariaDao();
        pesquisas = pesDao.queryBuilder().where(PlPesquisaLarvariaDao.Properties.Estado_sync.eq(1)).list();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (PlPesquisaLarvaria p : pesquisas) {
            JSONObject joPesquisas = new JSONObject();
            String date = dateFormat.format(p.getFecha());
            joPesquisas.put("id", p.getId());
            joPesquisas.put("idTablet", p.getIdTablet());
            joPesquisas.put("idSibasi", p.getIdSibasi());
            joPesquisas.put("idSemana", p.getIdSemanaEpidemiologica());
            joPesquisas.put("idCriadero", p.getIdCriadero());
            joPesquisas.put("idCaserio", p.getIdCaserio());
            joPesquisas.put("idUsuario", p.getIdUsuarioReg());
            joPesquisas.put("idEstado", p.getIdEstado());
            joPesquisas.put("fecha", p.getFechaHoraReg());
            joPesquisas.put("indice", p.getIndiceLarvario());
            joPesquisas.put("anopheles34", p.getAnophelesDos());
            joPesquisas.put("anopheles12", p.getAnophelesUno());
            joPesquisas.put("culicino12", p.getCulicinosUno());
            joPesquisas.put("culicino34", p.getCulicinosDos());
            joPesquisas.put("pupa", p.getPupa());
            joPesquisas.put("cucharonadas", p.getNumeroCucharonada());
            joPesquisas.put("ancho", p.getAncho());
            joPesquisas.put("largo", p.getLargo());
            joTotal.put(joPesquisas);
        }
        return joTotal;
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
                                send(token);
                                sendDataPesquisa(token);
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
        String tkn = token;
        try {
            JSONArray ja = getInsertCapturas();
            
            String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/entomologicas";
            RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url, ja,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (response != null) {

                                    tvja.setText(String.valueOf(response.toString()));
                                    Log.i("aaaa", String.valueOf(response.toString()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("***errorCaptura", error.getMessage());
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
            rq.getCache().clear();
            rq.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDataUpdatePesquisa(final String token) {
        String tkn = token;
        try {
            JSONArray ja = new JSONArray();
            ja.put(getInsertPesquisas());
            String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/pesquisas";
            RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.PUT, url, ja,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (response != null) {

                                    tvja.setText(String.valueOf(response.toString()));
                                    Log.i("aaaa", String.valueOf(response.toString()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("***errorPesquisa", error.getMessage());
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


    /**
     * se inicia prubas conj libreira oktthp para subir datos
     **/


    OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    private void send(String tkn) throws JSONException {
        JSONArray json = getInsertCapturas();
        int cont = json.length();

        String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/entomologicas";
        RequestBody body = RequestBody.create(JSON, json.toString());
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer " + tkn)
                .build();

        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("***okhttp error**", String.valueOf(e));
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String res = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("***okhttr succ**", res);
                                tvja.setText(res);
                            }
                        });
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void sendDataPesquisa(final String token) throws JSONException {
        JSONArray json = getInsertPesquisas();
        int cont = json.length();

        String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/pesquisas";
        RequestBody body = RequestBody.create(JSON, json.toString());
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer " + token)
                .build();

        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("***pesuiza error**", String.valueOf(e));
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String res2 = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("***pesquiza succ**", res2);
                                tvPesquisa.setText(res2);
                            }
                        });
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
