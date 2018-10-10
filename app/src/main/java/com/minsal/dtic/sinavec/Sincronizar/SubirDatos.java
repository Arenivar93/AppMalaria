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
import com.minsal.dtic.sinavec.EntityDAO.CtlTablet;
import com.minsal.dtic.sinavec.EntityDAO.CtlTabletDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoMaster;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.AdapterCapturas;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SubirDatos extends AppCompatActivity {
    private DaoSession daoSession;
    private SharedPreferences pref;
    ImageView subirDatos, bajarDatos;
    TextView tvCapturas,tvPesquisa,tvCriaderos, tvCriaderosUpdate,tvSeguimientos;
    String username, password;
    long idTablet;
    long idSibasi;
    int idUltimoReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_datos);
        daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        subirDatos = (ImageView) findViewById(R.id.subirDatos);
        bajarDatos = (ImageView) findViewById(R.id.imBajar);
        tvCapturas = (TextView) findViewById(R.id.tvCapturas);
        tvPesquisa = (TextView) findViewById(R.id.tvPesquisa);
        tvCriaderos = (TextView) findViewById(R.id.tvCriaderos);
        tvSeguimientos = (TextView) findViewById(R.id.tvSeguimientos);
        tvCriaderosUpdate = (TextView) findViewById(R.id.tvCriaderosUpdate);

        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        username = pref.getString("userRem", "");
        password = pref.getString("passRem", "");
        idSibasi  = pref.getLong("idSibasiUser",0);
        idTablet  = pref.getLong("idTablet",0);

        subirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float carga = calculoBateria(getApplicationContext());
                    if (carga < 0.10) { //50% solo tiene 10 para probarla
                        Toast.makeText(getApplicationContext(), "El estado de carga es menor al 50%, Por favor conecte el dispositivo", Toast.LENGTH_LONG).show();
                    } else {
                        checkinServer("subir");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        bajarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float carga =calculoBateria(getApplicationContext());
                    if (carga<0.10){
                        Toast.makeText(getApplicationContext(),"El estado de carga es menor al 50%, Por favor conecte el dispositivo",Toast.LENGTH_LONG).show();
                    }else {
                        checkinServerBajar();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *Metodo para obtener los datos que se subiran al servidor
     */
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
    public JSONArray getUpdateCriaderos() throws JSONException {
        JSONArray jaUpdate = new JSONArray();
        List<CtlPlCriadero> criaderos;
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
            joCriaderoUpdate.put("idTablet",idTablet);
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
    public JSONArray getInsetCriaderos() throws JSONException {
        JSONArray jacriaderos = new JSONArray();
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

    /***
     * Metodos para actualizar los registros locales una ves se hayan subido
     */
    public void updateLocalCapturas(JSONObject joCapturas) {
        try {
            JSONArray jaCapturasIserted = joCapturas.getJSONArray("ids");
            for (int i = 0; i < jaCapturasIserted.length(); i++) {
                String id = String.valueOf(jaCapturasIserted.get(i));
                PlCapturaAnophelesDao capDao = daoSession.getPlCapturaAnophelesDao();
                PlCapturaAnopheles capUpdate = capDao.loadByRowId(Long.parseLong(id));
                capUpdate.setEstado_sync(0);
                capDao.update(capUpdate);            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        pesUpdate.setEstado_sync(0);
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
                //Database db=daoSession.getDatabase();
                for (int i = 0; i < jaIdLocal.length(); i++) {
                    String idGenerado = String.valueOf(jaIdGenerado.get(i));
                    String id = String.valueOf(jaIdLocal.get(i));
                    String sql = "UPDATE CTL_PL_CRIADERO SET ID ="+idGenerado+", ESTADO_SYNC = 0    WHERE ID ="+id+"";
                    db.execSQL(sql);
                }
                db.close();            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),String.valueOf(e.getMessage()),Toast.LENGTH_LONG).show();
        }
    }

    public void updateCriaderosUpdate(JSONObject jsonObject){
        try {
            JSONArray jaCriaderosUpdate = jsonObject.getJSONArray("ids");
            int total = jaCriaderosUpdate.length();
            if (total>0){
                for (int i = 0; i < jaCriaderosUpdate.length(); i++) {
                    String id = String.valueOf(jaCriaderosUpdate.get(i));
                    CtlPlCriaderoDao uptCriaDao = daoSession.getCtlPlCriaderoDao();
                    CtlPlCriadero criaUpt = uptCriaDao.loadByRowId(Long.parseLong(id));
                    criaUpt.setEstado_sync(0);
                    uptCriaDao.update(criaUpt);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *peticion de token al servidor,si la respuesta es corresta iniciara los metodos que suben los datos
     */
    public void checkinServer(final String accion) throws JSONException {
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
                tvCapturas.setText(String.format("Capturas lista para sincronizar: %d", countCapturas));
                String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/api/login_check";
                RequestQueue cola = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                 String token1 = jsonObject.getString("token");
                                    if (accion.equals("subir")){
                                        sendCapturas(token1);
                                        sendDataPesquisa(token1);
                                        sendCriaderosUpdate(token1);
                                        sendCriaderos(token1);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "autorizado para bajar", Toast.LENGTH_LONG).show();
                                    }

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
                        parameters.put("_username", username);
                        parameters.put("_password", password);
                        return parameters;
                    }

                };
                cola.add(stringRequest);
            }else{
             Toast.makeText(getApplicationContext(),"No hay registros pendiente de sincronizacion",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void checkinServerBajar() throws JSONException {
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
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String tokenSubir = jsonObject.getString("token");
                                    bajarBitacora(tokenSubir);
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                        parameters.put("_username", username);
                        parameters.put("_password", password);
                        return parameters;
                    }
                };
                cola.add(stringRequest);

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
                    tvCapturas.setText("Error:"+String.valueOf(e));
                }
                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                       final String respuestaCapturas = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray respuesta = new JSONArray(respuestaCapturas);
                                    JSONObject jores = respuesta.getJSONObject(1);
                                    updateLocalCapturas(jores);
                                    tvCapturas.setText("Capturas Anopheles ingresadas con éxito");


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
    private void sendDataPesquisa(String token) throws JSONException {
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
                        final String respuestaPesquiza = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray respuesta = null;
                                try {
                                    respuesta = new JSONArray(respuestaPesquiza);
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
                    tvCriaderos.setText("Error:"+String.valueOf(e));
                }
                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String resCriaderos = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray respuesta = new JSONArray(resCriaderos);
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
                        final String respuestaUpdateCri = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray respuesta = null;
                                try {
                                    respuesta = new JSONArray(respuestaUpdateCri);
                                    JSONObject jores = respuesta.getJSONObject(1);
                                    updateCriaderosUpdate(jores);
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
    private void bajarBitacora(String tkn){
        idUltimoReg = ultimoRegistroBajado();
        String url = "http://10.168.10.80/proyecto_sinave_jwt/web/app_dev.php/" +
                "api/bitacora?accion=cambiosTablet&idTablet="+idTablet+"&idSibasi="+idSibasi+"&idUltimoReg="+idUltimoReg;
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + tkn)
                .build();
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("****",e.getMessage());
                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String respuestaBajar = response.body().string();
                        SubirDatos.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    bajarDatos.setEnabled(false);
                                    JSONObject  jsTotal = new JSONObject(respuestaBajar);
                                   saveCambiosBitacora(jsTotal);
                                } catch (Exception e) {
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
    public int ultimoRegistroBajado(){
        int registro=0;

            List<CtlTablet> ids = null;
            CtlTabletDao tbDao = daoSession.getCtlTabletDao();
            QueryBuilder<CtlTablet> qb = tbDao.queryBuilder();
            qb.where(CtlTabletDao.Properties.Id.isNotNull()).orderDesc().limit(1);
            ids = qb.list();
            for (CtlTablet f : ids) {
                if (f.getUltimoRegBajado()!= null){
                    registro = f.getUltimoRegBajado();
                }
        }
        return  registro;
    }

    public void saveCambiosBitacora(JSONObject jsTotal) {
        try {
            JSONArray jaInsertCriadero = jsTotal.getJSONArray("insertCriadero");
            JSONArray jaUpdateCriadero = jsTotal.getJSONArray("updateCriadero");
            if(jaInsertCriadero.length()>0){
                for (int i = 0; i < jaInsertCriadero.length(); i++) {
                    JSONObject fila = jaInsertCriadero.getJSONObject(i);
                    int regBajado = fila.getInt("id");
                    JSONObject sentencia = fila.getJSONObject("sentencia");
                    long id = sentencia.getLong("idGenerado");
                    long idCaserio = sentencia.getLong("idCaserio");
                    int tipo = sentencia.getInt("idTipoCriadero");
                    int estado = sentencia.getInt("idEstadoCriadero");
                    String nombre = sentencia.getString("nombre");
                    String descripcion = sentencia.getString("descripcion");
                    String latitud = sentencia.getString("latitud");
                    String longitud = sentencia.getString("longitud");
                    long idSibasi = sentencia.getLong("idSibasi");
                    long usuarioReg = sentencia.getLong("idUsuarioReg");
                    String fechaReg = sentencia.getString("fechaHoraReg");
                    int ancho = sentencia.getInt("anchoCriadero");
                    int largo = sentencia.getInt("longitudCriadero");
                    saveCriadero(id, idCaserio, tipo, usuarioReg,
                            0, nombre, descripcion, latitud, longitud, largo, ancho // el usuario mod es obligatorio en el metodo pero no lo toma en cuenta si es 0
                            , fechaReg, fechaReg, idSibasi, estado);
                    CtlTabletDao tabDao = daoSession.getCtlTabletDao();
                    CtlTablet tab = tabDao.loadByRowId(idTablet);
                    tab.setUltimoRegBajado(regBajado);
                    tabDao.update(tab);
                }
                tvCriaderos.setText(String.format("Se Descargaron %s nuevos", String.valueOf(jaInsertCriadero.length())));

            }else if(jaUpdateCriadero.length()>0){
                JSONArray sentencias;
                JSONObject total = jaUpdateCriadero.getJSONObject(0);
                int regBajado = total.getInt("id");
                 sentencias = total.getJSONArray("sentencia");
                for (int i = 0; i <sentencias.length() ; i++) {
                    JSONObject individual = sentencias.getJSONObject(i);
                    String fecha = individual.getString("fechaHoraMod");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = format.parse(fecha);
                    saveCriaderoUpdate(individual.getLong("id"),individual.getString("latitud"),
                            individual.getString("longitud"),individual.getLong("idUsuarioMod"),date);

                }
                CtlTabletDao tabDao = daoSession.getCtlTabletDao();
                CtlTablet tab = tabDao.loadByRowId(idTablet);
                tab.setUltimoRegBajado(regBajado);
                tabDao.update(tab);

                tvCriaderosUpdate.setText(String.format("Se Actualizaron %s Criaderos", String.valueOf(sentencias.length())));

            }else {
                Toast.makeText(getApplicationContext(),"No hay registros pendientes para descargar",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCriadero(long id, long idCaserio, int tipo, long usuarioreg, int usuarioMod,
                             String nombre, String descripcion, String latitud, String longitud, int log_cria,
                             int ancho, String fechaReg, String fechaMod, long idSibasi, int estado) {
        CtlPlCriaderoDao criaDao = daoSession.getCtlPlCriaderoDao();
        CtlPlCriadero cria = new CtlPlCriadero();
        cria.setId(id);
        cria.setIdCaserio(idCaserio);
        cria.setIdTipoCriadero(tipo);
        cria.setIdUsarioReg(usuarioreg);
        if (usuarioMod > 0) {
            cria.setIdUsuarioMod(usuarioMod);
        }
        cria.setNombre(nombre);
        cria.setDescripcion(descripcion);
        if (!latitud.equals("null") && !longitud.equals("null")) {
            cria.setLatitud(latitud);
            cria.setLongitud(longitud);
        }
        cria.setLongitudCriadero(log_cria);
        cria.setAnchoCriadero(ancho);
        cria.setIdSibasi(idSibasi);
        cria.setIdEstadoCriadero(estado);
        cria.setEstado_sync(0);
        criaDao.insert(cria);
    }
    public void saveCriaderoUpdate(long id, String latitud, String longitud,long usuarioMod,Date fechaMod){
        CtlPlCriadero criadero=daoSession.getCtlPlCriaderoDao().loadByRowId(id);
        criadero.setLongitud(longitud);
        criadero.setLatitud(latitud);
        criadero.setFechaHoraMod(fechaMod);
        criadero.setIdUsuarioMod(usuarioMod);

    }

}
