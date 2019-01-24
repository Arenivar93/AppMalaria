package com.minsal.dtic.sinavec.Sincronizar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.minsal.dtic.sinavec.EntityDAO.Clave;
import com.minsal.dtic.sinavec.EntityDAO.ClaveDao;
import com.minsal.dtic.sinavec.EntityDAO.ColvolCalve;
import com.minsal.dtic.sinavec.EntityDAO.ColvolCalveDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamento;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamentoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimientoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlInstitucion;
import com.minsal.dtic.sinavec.EntityDAO.CtlInstitucionDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPais;
import com.minsal.dtic.sinavec.EntityDAO.CtlPaisDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlProcedencia;
import com.minsal.dtic.sinavec.EntityDAO.CtlProcedenciaDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlSemanaEpi;
import com.minsal.dtic.sinavec.EntityDAO.CtlSemanaEpiDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlTablet;
import com.minsal.dtic.sinavec.EntityDAO.CtlTabletDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlTipoEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlTipoEstablecimientoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.EstablecimientoClave;
import com.minsal.dtic.sinavec.EntityDAO.EstablecimientoClaveDao;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.EntityDAO.PlColvolDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividad;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividadDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCaptura;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCapturaDao;
import com.minsal.dtic.sinavec.LoginActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SettingActivity extends AppCompatActivity {
    Button btnSetting;
    private DaoSession daoSession;
    private static SettingActivity instance;
    ProgressBar pbSetting;
    private SharedPreferences pref;
    String username, password;
    //public static final String imeiq= "356980052723205";
    public static final int GET_IMEI_CODE =100;
    private boolean imeiGranted = false;
    private static final String PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        pbSetting = (ProgressBar) findViewById(R.id.pbSetting);
        btnSetting = (Button) findViewById(R.id.btnConfigurar);
        daoSession = ((MyMalaria) getApplication()).getDaoSession();
        username = "tablet"; // no se pueden usar las sharedpreferences ya que es la primera vez que se utiliza la tabelt
        password = "tablet";
        instance = this;
        getImeiPermission();
        //este evento debe ocurrir solo cuando se instala la aplicacioj por primera vez o por si se borra la base
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    if (imeiGranted) {
                        checkinServer();
                    }else{
                        getImeiPermission();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getIMEINumber() {
        String myAndroidDeviceId = "";
        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //comprobar version de android usando
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, GET_IMEI_CODE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) ;
            myAndroidDeviceId = mTelephony.getDeviceId();
            return myAndroidDeviceId;
        }
        else {
            //myAndroidDeviceId = mTelephony.getImei();
            myAndroidDeviceId=  mTelephony.getDeviceId();

        }
        return myAndroidDeviceId;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case GET_IMEI_CODE:
                String permission = permissions[0];
                int result = grantResults[0];
                if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                    //comprobar si el user acepto el permiso}
                    if (result == PackageManager.PERMISSION_GRANTED) {//acepto


                    } else {//no dio el permiso
                        Toast.makeText(getApplicationContext(), "No acepto el permiso para leer IMEI", Toast.LENGTH_SHORT).show();

                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
    }
    }

    /*
    public  String getIMEINumber() {
        String IMEINumber = "";
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IMEINumber = telephonyMgr.getImei();
            } else {
                IMEINumber = telephonyMgr.getDeviceId();            }
        }
        return IMEINumber;
    }*/

    public  void saveFosUserUser(long id, String firstname, String username, String lastname,
                                String password, String salt, int tipoEmpleado, int idSibasi) {

        FosUserUserDao fosUserUser = daoSession.getFosUserUserDao();
        FosUserUser user = new FosUserUser();
        user.setId(id);
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setSalt(salt);
        user.setTipoEmpleado(tipoEmpleado);
        user.setIdSibasi(idSibasi);
        fosUserUser.insert(user);
    }

    public void saveCoutry(long id, String nombre, int activo) {
        CtlPaisDao ctlPaisDao = daoSession.getCtlPaisDao();
        CtlPais pais = new CtlPais();
        pais.setId(id);
        pais.setNombre(nombre);
        pais.setActivo(activo);
        ctlPaisDao.insert(pais);
    }

    public void saveProcedencia(long id, String nombre) {
        CtlProcedenciaDao ctlProcedenciaDao = daoSession.getCtlProcedenciaDao();
        CtlProcedencia procedencia = new CtlProcedencia();
        procedencia.setId(id);
        procedencia.setNombre(nombre);
        ctlProcedenciaDao.insert(procedencia);
    }

    public void saveClave(long id, int idDepto, int idMpo, int corr, String claveServer, long procedencia) {
        ClaveDao claveDao = daoSession.getClaveDao();
        Clave clave = new Clave();
        clave.setId(id);
        clave.setIdProcedencia(procedencia);
        clave.setIdDepartamento(idDepto);
        clave.setIdMunicipio(idMpo);
        clave.setClave(claveServer);//se llama clave server porque el objeto tambien se llama clave
        claveDao.insert(clave);
    }

    public void saveDepartamento(long id, String nombre, long idPais) {
        CtlDepartamentoDao ctlDepartamentoDao = daoSession.getCtlDepartamentoDao();
        CtlDepartamento depto = new CtlDepartamento();
        depto.setId(id);
        depto.setNombre(nombre);
        depto.setIdPais(idPais);
        ctlDepartamentoDao.insert(depto);
    }

    public void saveMunicipio(long id, String nombre, long idDepto, int deptoApoyo) {
        CtlMunicipioDao municipioDao = daoSession.getCtlMunicipioDao();
        CtlMunicipio municipio = new CtlMunicipio();
        municipio.setId(id);
        municipio.setNombre(nombre);
        municipio.setIdDepartamento(idDepto);
        municipio.setIdDeptoApoyo(deptoApoyo);
        municipioDao.insert(municipio);
    }

    public void saveCanton(long id, String nombre, long idMpo) {
        CtlCantonDao cantonDao = daoSession.getCtlCantonDao();
        CtlCanton canton = new CtlCanton();
        canton.setId(id);
        canton.setNombre(nombre);
        canton.setIdMunicipio(idMpo);
        cantonDao.insert(canton);
    }

    public void savecaserio(long id, String nombre, long idCanton, long idDeptoApoyo, int bandera) {
        CtlCaserioDao caserioDao = daoSession.getCtlCaserioDao();
        CtlCaserio caserio = new CtlCaserio();
        caserio.setId(id);
        caserio.setNombre(nombre);
        caserio.setIdCanton(idCanton);
        caserio.setBandera(bandera);
        caserio.setIdDeptoApoyo(idDeptoApoyo);
        caserioDao.insert(caserio);
    }

    public void saveTablet(long id, long sibasi, String codigo, String serie,int ultimoBajado) {
        CtlTabletDao tabletDao = daoSession.getCtlTabletDao();
        CtlTablet tablet = new CtlTablet();
        tablet.setId(id);
        tablet.setCodigo(codigo);
        tablet.setIdSibasi(sibasi);
        tablet.setUltimoRegBajado(ultimoBajado);
        tablet.setSerie(serie); //es el IMEI
        tabletDao.insert(tablet);
    }

    public void saveInstitucion(Long id, String nombre) {
        CtlInstitucionDao institucionDao = daoSession.getCtlInstitucionDao();
        CtlInstitucion institucion = new CtlInstitucion();
        institucion.setId(id);
        institucion.setNombre(nombre);
        institucionDao.insert(institucion);
    }

    public void saveTipoestablecimiento(Long id, String nombre, long tipoInstitucion) {
        CtlTipoEstablecimientoDao teDao = daoSession.getCtlTipoEstablecimientoDao();
        CtlTipoEstablecimiento te = new CtlTipoEstablecimiento();
        te.setId(id);
        te.setNombre(nombre);
        te.setIdInstitucion(tipoInstitucion);
        teDao.insert(te);
    }

    public void saveEstablecimiento(Long id, String nombre, String lat, String lon, long idMpo, int idPadre, int idTipo,int lab_clinico) {
        CtlEstablecimientoDao estDao = daoSession.getCtlEstablecimientoDao();
        CtlEstablecimiento est = new CtlEstablecimiento();
        est.setId(id);
        est.setNombre(nombre);
        est.setLabClinico(lab_clinico);
        if (idMpo != 0) {
            est.setIdMunicipio(idMpo);
        }
        if (!lat.equals("null")&& !
                lon.equals("null")){
            est.setLatitud(lat);
            est.setLongitud(lon);
        }
        est.setIdEstablecimientoPadre(idPadre);
        est.setIdTipoEstablecimiento(idTipo);
        estDao.insert(est);
    }

    public void saveMinsal() {
        CtlEstablecimientoDao estDao = daoSession.getCtlEstablecimientoDao();
        CtlEstablecimiento est = new CtlEstablecimiento();
        est.setId((long) 1038);
        est.setNombre("Secretaría de Estado SS Ministerio de Salud");
        est.setLatitud("13.69959484");
        est.setLongitud("-89.19812352");
        est.setIdMunicipio(4);
        est.setIdTipoEstablecimiento(8);
        estDao.insert(est);
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
        if (usuarioMod > 0){
            cria.setIdUsuarioMod(usuarioMod);
        }
        cria.setNombre(nombre);
        cria.setDescripcion(descripcion);
        if (!latitud.equals("null") && !longitud.equals("null")){
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
    public void saveTipoCaptura(Long id,String nombre){
        PlTipoCapturaDao capturaDao = daoSession.getPlTipoCapturaDao();
        PlTipoCaptura cap = new PlTipoCaptura();
        cap.setId(id);
        cap.setNombre(nombre);
        capturaDao.insert(cap);
    }
    public void saveTipoActivida(Long id,String nombre){
        PlTipoActividadDao actividadDao = daoSession.getPlTipoActividadDao();
        PlTipoActividad act = new PlTipoActividad();
        act.setId(id);
        act.setNombre(nombre);
        actividadDao.insert(act);
    }
    public void saveSemana(long id, int anio, String fecf, String feci, int semana) throws ParseException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date fechaFin = dateFormat.parse(fecf);
       // Date fechaIni = dateFormat.parse(feci);

        CtlSemanaEpiDao semanaDao = daoSession.getCtlSemanaEpiDao();
        CtlSemanaEpi sem = new CtlSemanaEpi();
        sem.setId(id);
        sem.setAnio(anio);
        sem.setFechaFin(fecf);
        sem.setFechaInicio(feci);
        sem.setSemana(semana);
        semanaDao.insert(sem);
    }

    public void saveColvol(long id,long idCaserio, String nombre,long idSibasi,String clave,
                           int estado, String latitud, String longitud){
        PlColvolDao colvolDao = daoSession.getPlColvolDao();
        PlColvol colvol = new PlColvol();
        colvol.setId(id);
        colvol.setIdCaserio(idCaserio);
        colvol.setNombre(nombre);
        if (!latitud.equals("null") && !longitud.equals("null") ){
            colvol.setLatitud(latitud);
            colvol.setLongitud(longitud);
        }
        colvol.setIdSibasi(idSibasi);
        colvol.setClave(clave);
        colvol.setEstado(estado);
        colvol.setEstado_sync(0);
        colvolDao.insert(colvol);
    }
    public void saveColvolClave(long id, long idClave,long idColvol){
        ColvolCalveDao claDao = daoSession.getColvolCalveDao();
        ColvolCalve cla = new ColvolCalve();
        cla.setId(id);
        cla.setIdClave(idClave);
        cla.setIdColvol(idColvol);
        claDao.insert(cla);
    }
    public void saveEstClave(long id, long idClave,long idEstablecimiento){
        EstablecimientoClaveDao claDao = daoSession.getEstablecimientoClaveDao();
        EstablecimientoClave cla = new EstablecimientoClave();
        cla.setId(id);
        cla.setIdClave(idClave);
        cla.setIdEstablecimiento(idEstablecimiento);
        claDao.insert(cla);
    }
    /**
     *peticion de token al servidor,si la respuesta es corresta iniciara los metodos que suben los datos
     */
    public void checkinServer() throws JSONException {
        boolean red = MetodosGlobales.compruebaConexion(getApplicationContext());
        if (!red) {
            Toast.makeText(getApplicationContext(), "Lo sentimos no tiene conexion a Internet", Toast.LENGTH_SHORT).show();

        } else {
            //antes de hacer una peticion vamos a comprobar que hay registros para enviar
            String url = "http://malaria-dev.salud.gob.sv/api/login_check";
                RequestQueue cola = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String token1 = jsonObject.getString("token");
                                    usarVolley(token1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Error1!!Por favor contacta al administrador" + e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error2!Por favor contacta al administrador" + String.valueOf(error), Toast.LENGTH_LONG).show();

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



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void usarVolley(final String tkn) {
        boolean red = MetodosGlobales.compruebaConexion(getApplicationContext());
        if (!red) {
            Toast.makeText(getApplicationContext(), "Lo sentimos no tiene conexion a Internet", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Solicitando Datos al Servidor, espere...", Toast.LENGTH_SHORT).show();
           String imei = getIMEINumber();
            String url = "http://malaria-dev.salud.gob.sv/api/catalogos?imei="+imei;
            RequestQueue cola = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                saveDowloadedCat e = new saveDowloadedCat();
                                e.execute(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"Error!!Por favor contacta al administrador"+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error!Por favor contacta al administrador"+String.valueOf(error),Toast.LENGTH_LONG).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                    headers.put("Authorization", "Bearer " + tkn);
                    return headers;
                }
            };
            cola.add(stringRequest);
        }
    }

    private class saveDowloadedCat extends AsyncTask<JSONObject, Integer, Boolean> {

        int num = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Iniciando descarga de datos", Toast.LENGTH_SHORT).show();
            pbSetting.setProgress(0);
            pbSetting.setMax(19);
            btnSetting.setEnabled(false);


        }

        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {

            try {
                JSONObject jsTotal       = jsonObjects[0];
                JSONArray jaPaises       = jsTotal.getJSONArray("paises");
                JSONArray jaProcedencia  = jsTotal.getJSONArray("procedencia");
                JSONArray jaClave        = jsTotal.getJSONArray("clave");
                JSONArray jaDepartamento = jsTotal.getJSONArray("departamento");
                JSONArray jaMunicipio    = jsTotal.getJSONArray("municipio");
                JSONArray jaCanton       = jsTotal.getJSONArray("canton");
                JSONArray jaCaserio      = jsTotal.getJSONArray("caserio");
                JSONArray jaTablet       = jsTotal.getJSONArray("tablet");
                JSONArray jaInstitucion  = jsTotal.getJSONArray("institucion");
                JSONArray jaTipo         = jsTotal.getJSONArray("tipoEstablecimiento");
                JSONArray jaEst          = jsTotal.getJSONArray("establecimiento");
                JSONArray jaUser         = jsTotal.getJSONArray("usuario");
                JSONArray jaCriadero     = jsTotal.getJSONArray("criadero");
                JSONArray jaTipoCaptura  = jsTotal.getJSONArray("tipoCaptura");
                JSONArray jaActividad    = jsTotal.getJSONArray("actividad");
                JSONArray jaSemana       = jsTotal.getJSONArray("semana");
                JSONArray jaColvol       = jsTotal.getJSONArray("colvol");
                JSONArray jaColvolClave  = jsTotal.getJSONArray("colvolClave");
                JSONArray jaEstClave     = jsTotal.getJSONArray("estClave");
               // JSONArray jsTotal22      = jsTotal.getJSONArray("total");
                for (int i = 0; i < jaPaises.length(); i++) {
                    JSONObject joPais = jaPaises.getJSONObject(i);
                    saveCoutry(joPais.getLong("id"), joPais.getString("nombre"), joPais.getInt("activo"));

                }
                num++;
                publishProgress(num);
                for (int j = 0; j < jaProcedencia.length(); j++) {
                    JSONObject joProcedencia = jaProcedencia.getJSONObject(j);
                    saveProcedencia(joProcedencia.getLong("id"), joProcedencia.getString("nombre"));

                }
                num++;
                publishProgress(num);
                for (int k = 0; k < jaClave.length(); k++) {
                    JSONObject joClave = jaClave.getJSONObject(k);
                    saveClave(joClave.getLong("id"), joClave.getInt("id_departamento"), joClave.getInt("id_municipio"),
                            joClave.getInt("correlativo"), joClave.getString("clave"), joClave.getLong("id_procedencia"));

                }
                num++;
                publishProgress(num);
                for (int l = 0; l < jaDepartamento.length(); l++) {
                    JSONObject joDepto = jaDepartamento.getJSONObject(l);
                    saveDepartamento(joDepto.getLong("id"), joDepto.getString("nombre"), joDepto.getLong("id_pais"));

                }
                num++;
                publishProgress(num);
                for (int m = 0; m < jaMunicipio.length(); m++) {
                    JSONObject joMpo = jaMunicipio.getJSONObject(m);
                    int id_depto_apoyo;
                    if (!joMpo.isNull("id_depto_apoyo")) {
                        id_depto_apoyo = joMpo.getInt("id_depto_apoyo");
                    } else {
                        id_depto_apoyo = 0;
                    }
                    saveMunicipio(joMpo.getLong("id"), joMpo.getString("nombre"), joMpo.getLong("id_departamento"), id_depto_apoyo);

                }
                num++;
                publishProgress(num);
                for (int n = 0; n < jaCanton.length(); n++) {
                    JSONObject joCanton = jaCanton.getJSONObject(n);
                    saveCanton(joCanton.getLong("id"), joCanton.getString("nombre"), joCanton.getLong("id_municipio"));

                }
                num++;
                publishProgress(num);
                for (int o = 0; o < jaCaserio.length(); o++) {
                    JSONObject joCaserio = jaCaserio.getJSONObject(o);
                    int id_depto_apoyo;
                    if (!joCaserio.isNull("id_depto_apoyo")) {
                        id_depto_apoyo = joCaserio.getInt("id_depto_apoyo");
                    } else {
                        id_depto_apoyo = 0;
                    }
                    int bandera;
                    if (!joCaserio.isNull("bandera")) {
                        bandera = joCaserio.getInt("bandera");
                    } else {
                        bandera = 0;
                    }
                    savecaserio(joCaserio.getLong("id"), joCaserio.getString("nombre"), joCaserio.getLong("id_canton"),
                            id_depto_apoyo, bandera);
                }
                num++;
                publishProgress(num);
                for (int q = 0; q < jaInstitucion.length(); q++) {
                    JSONObject joIns = jaInstitucion.getJSONObject(q);
                    saveInstitucion(joIns.getLong("id"), joIns.getString("nombre"));

                }
                num++;
                publishProgress(num);
                for (int r = 0; r < jaTipo.length(); r++) {
                    JSONObject joTipo = jaTipo.getJSONObject(r);
                    saveTipoestablecimiento(joTipo.getLong("id"), joTipo.getString("nombre"), joTipo.getLong("id_institucion"));

                }
                num++;
                publishProgress(num);
                saveMinsal();
                for (int s = 0; s < jaEst.length(); s++) {
                    JSONObject joEst = jaEst.getJSONObject(s);
                    long id_municipio;
                    if (!joEst.isNull("id_municipio")) {
                        id_municipio = joEst.getLong("id_municipio");
                    } else {
                        id_municipio = 0;
                    }
                    saveEstablecimiento(joEst.getLong("id"), joEst.getString("nombre"), joEst.getString("latitud"),
                            joEst.getString("longitud"), id_municipio, joEst.getInt("id_establecimiento_padre"),
                            joEst.getInt("id_tipo_establecimiento"),joEst.getInt("lab_clinico"));

                }
                num++;
                publishProgress(num);


                for (int p = 0; p < jaTablet.length(); p++) {
                    int ultimoBajado =0;

                    JSONObject joTablet = jaTablet.getJSONObject(p);
                    if (!joTablet.isNull("ultimo_bajado")){
                        ultimoBajado = joTablet.getInt("ultimo_bajado");
                        
                    }
                    saveTablet(joTablet.getLong("id"), joTablet.getLong("id_sibasi"),
                            joTablet.getString("codigo"), joTablet.getString("imei"),ultimoBajado);

                }
                num++;
                publishProgress(num);
                for (int q = 0; q < jaUser.length(); q++) {
                    JSONObject joUser = jaUser.getJSONObject(q);
                    saveFosUserUser(joUser.getLong("id"), joUser.getString("firstname"), joUser.getString("username"),
                            joUser.getString("lastname"), joUser.getString("password"), joUser.getString("salt"),
                            joUser.getInt("id_tipo_empleado"), joUser.getInt("id_sibasi"));

                }
                num++;
                publishProgress(num);
                for (int y = 0; y < jaCriadero.length(); y++) {
                    JSONObject joCria = jaCriadero.getJSONObject(y);
                    int idUsuarioMod;

                    if(!joCria.isNull("id_usuario_mod")){idUsuarioMod= joCria.getInt("id_usuario_mod");}else{idUsuarioMod=0;}
                    saveCriadero(joCria.getLong("id"), joCria.getLong("id_caserio"), joCria.getInt("id_tipo_criadero"),
                            joCria.getLong("id_usuario_reg"),idUsuarioMod,
                            joCria.getString("nombre"), joCria.getString("descripcion"),
                            joCria.getString("latitud"), joCria.getString("longitud"), joCria.getInt("longitud_criadero"),
                            joCria.getInt("ancho_criadero"), joCria.getString("fecha_hora_reg"), joCria.getString("fecha_hora_mod"),
                            joCria.getLong("id_sibasi"),joCria.getInt("id_estado_criadero"));


                }
                num++;
                publishProgress(num);
                for (int z = 0; z <jaActividad.length() ; z++) {
                    JSONObject joCap = jaActividad.getJSONObject(z);
                    saveTipoActivida(joCap.getLong("id"),joCap.getString("nombre"));

                }
                num++;
                publishProgress(num);
                for (int x = 0; x <jaTipoCaptura.length() ; x++) {
                    JSONObject joTipo = jaTipoCaptura.getJSONObject(x);
                    saveTipoCaptura(joTipo.getLong("id"),joTipo.getString("nombre"));

                }
                num++;
                publishProgress(num);
                for (int a = 0; a <jaSemana.length() ; a++) {
                    JSONObject joSem = jaSemana.getJSONObject(a);
                    saveSemana(joSem.getLong("id"),joSem.getInt("anio"),
                              joSem.getString("fecf"),joSem.getString("feci"),joSem.getInt("semana"));


                }
                num++;
                publishProgress(num);
                for (int b = 0; b <jaColvol.length() ; b++) {
                    JSONObject joColvol = jaColvol.getJSONObject(b);
                    saveColvol(joColvol.getLong("id"),joColvol.getLong("id_caserio"),joColvol.getString("nombre"),
                              joColvol.getLong("id_sibasi"),joColvol.getString("clave"),joColvol.getInt("estado")
                              ,joColvol.getString("latitud"),joColvol.getString("longitud"));

                }
                num++;
                publishProgress(num);
                for (int c = 0; c <jaColvolClave.length() ; c++) {
                    JSONObject joc = jaColvolClave.getJSONObject(c);
                    saveColvolClave(joc.getLong("id"),joc.getLong("id_clave"),joc.getLong("id_colvol"));
                }
                num++;
                publishProgress(num);
                for (int c = 0; c <jaEstClave.length() ; c++) {
                    JSONObject joc = jaEstClave.getJSONObject(c);
                    saveEstClave(joc.getLong("id"),joc.getLong("id_clave"),joc.getLong("id_establecimiento"));
                }
                num++;
                publishProgress(num);

            } catch (Exception e) {
                getApplicationContext().deleteDatabase("malaria");
                e.printStackTrace();
                return false;
            }
            return true;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Toast.makeText(getApplicationContext(),String.valueOf(values[0].intValue()), Toast.LENGTH_LONG).show();
            pbSetting.setProgress(values[0].intValue());

        }


        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(aVoid);
            if (resultado) {
                Toast.makeText(getBaseContext(), "Descarga de datos con exito", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(getBaseContext(), "Ha ocurrido un error, Reinicia la App e intenta de nuevo", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getBaseContext(), "La Tarea ha sido cancelada por el usuario", Toast.LENGTH_SHORT).show();
        }
    }
    public static SettingActivity getInstance() {

        return instance;
    }
    private void getImeiPermission() {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            imeiGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    GET_IMEI_CODE);
        }

    }



}



