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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.minsal.dtic.sinavec.EntityDAO.CtlPais;
import com.minsal.dtic.sinavec.EntityDAO.CtlPaisDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlProcedencia;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.EntityDAO.PlColvolDao;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import HelperDB.DbHelpers;

public class SettingActivity extends AppCompatActivity {
    Button btnSetting;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnSetting = (Button) findViewById(R.id.btnConfigurar);
        daoSession = ((MyMalaria) getApplication()).getDaoSession();
        MetodosGlobales metodosGlobales = new MetodosGlobales(daoSession);
        getIMEINumber();

        //final boolean check = objBaseDeDatos.checkDataBase();
        //este evento debe ocurrir solo cuando se instala la aplicacioj por primera vez o por si se borra la base
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://10.168.10.80/tablets/catalogos.php?imei=" + getIMEINumber();
                RequestQueue cola = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray ja = new JSONArray(response);
                                    for (int i = 0; i <ja.length() ; i++) {
                                        JSONObject row = ja.getJSONObject(i);
                                        guardarPrueba(row.getLong("id"), row.getString("nombre"),row.getInt("activo"));

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //  btnSetting.setText("Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), String.valueOf(error), Toast.LENGTH_LONG).show();
                    }
                });

// Add the request to the RequestQueue.
                cola.add(stringRequest);


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

    public void saveUserSerer(long id, String firstname,String username, String lastname,
                                 String password, String salt, int tipoEmpleado, int idSibasi) {
        //daoSession = ((MyMalaria) getApplication()).getDaoSession();
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
        Toast.makeText(getApplicationContext(),"Usuarios descargados con exito",Toast.LENGTH_LONG);
        finish();

    }
    public void guardarPrueba(long id, String nombre, int activo){
        CtlPaisDao ctlPaisDao = daoSession.getCtlPaisDao();
        CtlPais pais = new CtlPais();
        pais.setId(id);
        pais.setNombre(nombre);
        pais.setActivo(activo);
        ctlPaisDao.insert(pais);
        Toast.makeText(getApplicationContext(),"Pais descargados con exito",Toast.LENGTH_LONG);
        finish();

    }


}



