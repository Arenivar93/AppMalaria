package com.minsal.dtic.sinavec.Sincronizar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.BatteryManager;
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
import com.minsal.dtic.sinavec.EntityDAO.DaoMaster;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import org.greenrobot.greendao.database.Database;
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
    TextView tvja,tvPesquisa,tvCriaderos;
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
        tvCriaderos = (TextView) findViewById(R.id.tvCriaderos);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        username = pref.getString("userRem", "");
        password = pref.getString("passRem", "");

        subirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/entomologicas";
                    float carga =calculoBateria(getApplicationContext());
                    if (carga<0.50){
                        Toast.makeText(getApplicationContext(),"El estado de carga es menor al 50%, Por favor conecte el dispositivo",Toast.LENGTH_LONG).show();
                    }else {
                        checkinServer();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

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
            JSONArray jaCapturasIserted = joCapturas.getJSONArray("ids");
            for (int i = 0; i < jaCapturasIserted.length(); i++) {
              //  Log.i("***ids******", String.valueOf(jaCapturasIserted.get(i)));
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

    public JSONArray getUpdateCriaderos() throws JSONException {
        JSONArray jaUpdate = new JSONArray();
        List<CtlPlCriadero> criaderos = new ArrayList<CtlPlCriadero>();
        CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();
        criaderos = criaderoDao.queryBuilder().where(CtlPlCriaderoDao.Properties.Estado_sync.eq(2)).list();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (CtlPlCriadero c : criaderos) {
            String date = dateFormat.format(c.getFechaHoraMod());
            JSONObject joCriaderoUpdate = new JSONObject();
            joCriaderoUpdate.put("id",c.getId());
            joCriaderoUpdate.put("longitud",c.getLongitud());
            joCriaderoUpdate.put("latitud",c.getLatitud());
            joCriaderoUpdate.put("idUsuarioMod",c.getIdUsuarioMod());
            joCriaderoUpdate.put("fechaHoraMod",date);
            jaUpdate.put(joCriaderoUpdate);
            }
        return jaUpdate;
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

    /**
     * subir los nuevos criaderos
     */
    public JSONArray getInsetCriaderos() throws JSONException {
        JSONArray jacriaderos = new JSONArray();
        JSONObject joCriaderos = new JSONObject();
        List<CtlPlCriadero> criaderos = new ArrayList<CtlPlCriadero>();
        CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();
        criaderos = criaderoDao.queryBuilder().where(CtlPlCriaderoDao.Properties.Estado_sync.eq(1)).list();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (CtlPlCriadero c : criaderos) {
            String date = dateFormat.format(c.getFechaHoraReg());
            JSONObject joCriaderoUpdate = new JSONObject();
            joCriaderoUpdate.put("id",c.getId());
            joCriaderoUpdate.put("idTipoCriadero",c.getIdTipoCriadero());
            joCriaderoUpdate.put("idEstadoCriadero",c.getIdEstadoCriadero());
            joCriaderoUpdate.put("nombre",c.getNombre());
            joCriaderoUpdate.put("descripcion",c.getDescripcion());
            joCriaderoUpdate.put("longitud",c.getLongitud());
            joCriaderoUpdate.put("latitud",c.getLatitud());
            joCriaderoUpdate.put("idSibasi",c.getIdSibasi());
            joCriaderoUpdate.put("idCaserio",c.getIdCaserio());
            joCriaderoUpdate.put("longitudCriadero",c.getLongitudCriadero());
            joCriaderoUpdate.put("anchoCriadero",c.getAnchoCriadero());
            joCriaderoUpdate.put("idCaserio",c.getIdCaserio());
            joCriaderoUpdate.put("idUsuarioReg",c.getIdUsarioReg());
            joCriaderoUpdate.put("fechaHoraReg",date);
            joCriaderoUpdate.put("idTablet",c.getIdTablet());
            jacriaderos.put(joCriaderoUpdate);
        }
        return jacriaderos;
    }

    public void updateLocalPesquisas(JSONObject joPesquisas) {
            try {
                JSONArray jaPesquisasIserted = joPesquisas.getJSONArray("ids");
                int total = jaPesquisasIserted.length();
                if (total>0){
                    for (int i = 0; i < jaPesquisasIserted.length(); i++) {
                        String id = String.valueOf(jaPesquisasIserted.get(i));
                        PlPesquisaLarvariaDao pesDao = daoSession.getPlPesquisaLarvariaDao();
                        PlPesquisaLarvaria pesUpdate = pesDao.loadByRowId(Long.parseLong(id));

                        //pesUpdate.setEstado_sync(0);
                        pesDao.update(pesUpdate);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    /**
     * aqui actualizaremos el id del criadero local por el generado en el servidor
     */
    public void updateLocalcriaderos(JSONObject joCriaderos) {
        try {

            JSONArray jaIdGenerado = joCriaderos.getJSONArray("idGenerado");
            JSONArray jaIdLocal = joCriaderos.getJSONArray("ids");
            int total = jaIdLocal.length();
            if (total>0){
                DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"malaria");
                Database db=helper.getWritableDb();
                for (int i = 0; i < jaIdLocal.length(); i++) {
                    String idGenerado = String.valueOf(jaIdGenerado.get(i));
                    String id = String.valueOf(jaIdLocal.get(i));
                    String sql = "UPDATE CTL_PL_CRIADERO SET ID ="+idGenerado+", ESTADO_SYNC = 0    WHERE ID ="+id+"";
                    db.execSQL(sql);
                }
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),String.valueOf(e.getMessage()),Toast.LENGTH_LONG).show();
        }
    }

    public void checkinServer() throws JSONException {
        boolean red = MetodosGlobales.compruebaConexion(getApplicationContext());
        if (!red) {
            Toast.makeText(getApplicationContext(), "Lo sentimos no tiene conexion a Internet", Toast.LENGTH_SHORT).show();

        } else {
            //antes de hacer una peticion vamos a comprobar que hay registros para enviar
            int countCapturas = getInsertCapturas().length();
            int countPesquisas = getInsertPesquisas().length();
            int countCriaderosUpdate = getUpdateCriaderos().length();
            int countCridero = getInsetCriaderos().length();
            if (countCapturas>0 || countPesquisas>0 || countCriaderosUpdate>0 || countCridero>0){
                tvPesquisa.setText(String.format("Pesquisas lista para sincronizar: %d", countPesquisas));
                tvja.setText(String.format("Capturas lista para sincronizar: %d", countCapturas));
                String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/login_check";
                RequestQueue cola = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    token = jsonObject.getString("token");
                                    sendCapturas(token);
                                    sendDataPesquisa(token);
                                    sendCriaderosUpdate(token);
                                    sendCriaderos(token);
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
            }else{
             Toast.makeText(getApplicationContext(),"No hay registros pendiente de sincronizacion",Toast.LENGTH_LONG).show();
            }
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
    private void sendCapturas(String tkn) throws JSONException {
        JSONArray json = getInsertCapturas();
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
                   tvja.setText("Error:"+String.valueOf(e));
                }
                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String res = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray respuesta = new JSONArray(res);
                                    JSONObject jores = respuesta.getJSONObject(1);
                                    updateLocalCapturas(jores);
                                    tvja.setText("Capturas Anopheles ingresadas con éxito");

                                   // Log.i("***okhttr succ**", String.valueOf(jores.getJSONArray("ids")));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                   tvPesquisa.setText(String.valueOf(e));
                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String res2 = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray respuesta = null;
                                try {
                                    respuesta = new JSONArray(res2);
                                    JSONObject jores = respuesta.getJSONObject(1);
                                    updateLocalPesquisas(jores);
                                    tvPesquisa.setText("Pesquisas Ingresadas con Exito");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void sendCriaderos(String tkn) throws JSONException {
        JSONArray json = getInsetCriaderos();
        String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/criaderos";
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
                    tvja.setText("Error:"+String.valueOf(e));
                }
                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String res3 = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray respuesta = new JSONArray(res3);
                                    JSONObject jores = respuesta.getJSONObject(1);
                                    updateLocalcriaderos(jores);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void sendCriaderosUpdate(String token) throws JSONException {
        JSONArray json = getUpdateCriaderos();
        int cont = json.length();
        String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/criaderos";
        RequestBody body = RequestBody.create(JSON, json.toString());
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .put(body)
                .header("Authorization", "Bearer " + token)
                .build();
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    tvPesquisa.setText(String.valueOf(e));
                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String res2 = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray respuesta = null;
                                try {
                                    respuesta = new JSONArray(res2);
                                    JSONObject jores = respuesta.getJSONObject(1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public float calculoBateria(Context context){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float)scale;
        return batteryPct;

    }

}
