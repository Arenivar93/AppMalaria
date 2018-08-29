package com.minsal.dtic.sinavec;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.minsal.dtic.sinavec.EntityDAO.Clave;
import com.minsal.dtic.sinavec.EntityDAO.ClaveDao;
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
import com.minsal.dtic.sinavec.EntityDAO.CtlTablet;
import com.minsal.dtic.sinavec.EntityDAO.CtlTabletDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlTipoEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlTipoEstablecimientoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividad;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividadDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCaptura;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCapturaDao;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class SettingActivity extends AppCompatActivity {
    Button btnSetting;
    private DaoSession daoSession;
    ProgressBar pbSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pbSetting = (ProgressBar) findViewById(R.id.pbSetting);
        btnSetting = (Button) findViewById(R.id.btnConfigurar);
        daoSession = ((MyMalaria) getApplication()).getDaoSession();

        //este evento debe ocurrir solo cuando se instala la aplicacioj por primera vez o por si se borra la base
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usarVolley();

            }
        });
    }


    private String getIMEINumber() {
        String IMEINumber = "";
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IMEINumber = telephonyMgr.getImei();
            } else {
                IMEINumber = telephonyMgr.getDeviceId();
            }
        }
        return IMEINumber;
    }

    public void saveFosUserUser(long id, String firstname, String username, String lastname,
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

    public void saveTablet(long id, long sibasi, String codigo, String serie) {
        CtlTabletDao tabletDao = daoSession.getCtlTabletDao();
        CtlTablet tablet = new CtlTablet();
        tablet.setId(id);
        tablet.setCodigo(codigo);
        tablet.setIdSibasi(sibasi);
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

    public void saveEstablecimiento(Long id, String nombre, String lat, String lon, long idMpo, int idPadre, int idTipo) {
        CtlEstablecimientoDao estDao = daoSession.getCtlEstablecimientoDao();
        CtlEstablecimiento est = new CtlEstablecimiento();
        est.setId(id);
        est.setNombre(nombre);
        est.setLatitud(lat);
        est.setLongitud(lon);
        if (idMpo != 0) {
            est.setIdMunicipio(idMpo);
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
        cria.setIdUsuarioMod(usuarioMod);
        cria.setNombre(nombre);
        cria.setDescripcion(descripcion);
        cria.setLatitud(latitud);
        cria.setLongitud(longitud);
        cria.setLongitudCriadero(log_cria);
        cria.setAnchoCriadero(ancho);
        cria.setIdSibasi(idSibasi);
        cria.setIdEstadoCriadero(estado);
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


    public void usarVolley() {
        boolean red = MetodosGlobales.compruebaConexion(getApplicationContext());
        if (!red) {
            Toast.makeText(getApplicationContext(), "Lo sentimos no tiene conexion a Internet", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Solicitando Datos al Servidor, espere...", Toast.LENGTH_SHORT).show();
            String imei = getIMEINumber();
            String url = "http://10.168.10.80/tablets/catalogos.php?imei=" + imei;
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
                                Log.i("eror", "no descarga datos");

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("eror", String.valueOf(error));

                }
            });

            cola.add(stringRequest);

        }

    }


    private class saveDowloadedCat extends AsyncTask<JSONObject, Integer, Boolean> {
        JSONObject jsTotal;
        int num = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Iniciando descarga de datos", Toast.LENGTH_SHORT).show();
            pbSetting.setMax(5630);
            pbSetting.setProgress(0);


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

                for (int i = 0; i < jaPaises.length(); i++) {
                    JSONObject joPais = jaPaises.getJSONObject(i);
                    saveCoutry(joPais.getLong("id"), joPais.getString("nombre"), joPais.getInt("activo"));
                    num++;
                    publishProgress(num);

                }
                for (int j = 0; j < jaProcedencia.length(); j++) {
                    JSONObject joProcedencia = jaProcedencia.getJSONObject(j);
                    saveProcedencia(joProcedencia.getLong("id"), joProcedencia.getString("nombre"));
                    num++;
                    publishProgress(num);
                }
                for (int k = 0; k < jaClave.length(); k++) {
                    JSONObject joClave = jaClave.getJSONObject(k);
                    saveClave(joClave.getLong("id"), joClave.getInt("id_departamento"), joClave.getInt("id_municipio"),
                            joClave.getInt("correlativo"), joClave.getString("clave"), joClave.getLong("id_procedencia"));
                    num++;
                    publishProgress(num);
                }
                for (int l = 0; l < jaDepartamento.length(); l++) {
                    JSONObject joDepto = jaDepartamento.getJSONObject(l);
                    saveDepartamento(joDepto.getLong("id"), joDepto.getString("nombre"), joDepto.getLong("id_pais"));
                    num++;
                    publishProgress(num);
                }
                for (int m = 0; m < jaMunicipio.length(); m++) {
                    JSONObject joMpo = jaMunicipio.getJSONObject(m);
                    int id_depto_apoyo;
                    if (!joMpo.isNull("id_depto_apoyo")) {
                        id_depto_apoyo = joMpo.getInt("id_depto_apoyo");
                    } else {
                        id_depto_apoyo = 0;
                    }
                    saveMunicipio(joMpo.getLong("id"), joMpo.getString("nombre"), joMpo.getLong("id_departamento"), id_depto_apoyo);
                    num++;
                    publishProgress(num);
                }
                for (int n = 0; n < jaCanton.length(); n++) {
                    JSONObject joCanton = jaCanton.getJSONObject(n);
                    saveCanton(joCanton.getLong("id"), joCanton.getString("nombre"), joCanton.getLong("id_municipio"));
                    num++;
                    publishProgress(num);
                }
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
                    num++;
                    publishProgress(num);
                }
                for (int q = 0; q < jaInstitucion.length(); q++) {
                    JSONObject joIns = jaInstitucion.getJSONObject(q);
                    saveInstitucion(joIns.getLong("id"), joIns.getString("nombre"));
                    num++;
                    publishProgress(num);
                }
                for (int r = 0; r < jaTipo.length(); r++) {
                    JSONObject joTipo = jaTipo.getJSONObject(r);
                    saveTipoestablecimiento(joTipo.getLong("id"), joTipo.getString("nombre"), joTipo.getLong("id_institucion"));
                    num++;
                    publishProgress(num);
                }
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
                            joEst.getInt("id_tipo_establecimiento"));
                    num++;
                    publishProgress(num);
                }


                for (int p = 0; p < jaTablet.length(); p++) {
                    JSONObject joTablet = jaTablet.getJSONObject(p);
                    saveTablet(joTablet.getLong("id"), joTablet.getLong("id_sibasi"), joTablet.getString("codigo"), joTablet.getString("imei"));
                    num++;
                    publishProgress(num);
                }
                for (int q = 0; q < jaUser.length(); q++) {
                    JSONObject joUser = jaUser.getJSONObject(q);
                    saveFosUserUser(joUser.getLong("id"), joUser.getString("firstname"), joUser.getString("username"),
                            joUser.getString("lastname"), joUser.getString("password"), joUser.getString("salt"),
                            joUser.getInt("id_tipo_empleado"), joUser.getInt("id_sibasi"));
                    num++;
                    publishProgress(num);
                }
                for (int y = 0; y < jaCriadero.length(); y++) {
                    JSONObject joCria = jaCriadero.getJSONObject(y);
                    //int sibasi = joCria.getInt("id_sibasi");
                    saveCriadero(joCria.getLong("id"), joCria.getLong("id_caserio"), joCria.getInt("id_tipo_criadero"),
                            joCria.getLong("id_usuario_reg"), joCria.getInt("id_usuario_mod"),
                            joCria.getString("nombre"), joCria.getString("descripcion"),
                            joCria.getString("latitud"), joCria.getString("longitud"), joCria.getInt("longitud_criadero"),
                            joCria.getInt("ancho_criadero"), joCria.getString("fecha_hora_reg"), joCria.getString("fecha_hora_mod"),
                            joCria.getLong("id_sibasi"),joCria.getInt("id_estado_criadero"));
                    num++;
                    publishProgress(num);

                }
                for (int z = 0; z <jaActividad.length() ; z++) {
                    JSONObject joCap = jaActividad.getJSONObject(z);
                    saveTipoActivida(joCap.getLong("id"),joCap.getString("nombre"));
                }
                for (int x = 0; x <jaTipoCaptura.length() ; x++) {
                    JSONObject joTipo = jaTipoCaptura.getJSONObject(x);
                    saveTipoCaptura(joTipo.getLong("id"),joTipo.getString("nombre"));
                }

            } catch (Exception e) {
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
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getBaseContext(), "La Tarea ha sido cancelada por el usuario", Toast.LENGTH_SHORT).show();
        }


    }


}



