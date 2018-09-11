package com.minsal.dtic.sinavec.Sincronizar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
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
                try {
                    JSONObject jo = new JSONObject();
                    JSONArray jaCapturas = getInsertCapturas();
                    jo.put("pl_capturas_insert",jaCapturas);
                    String url = "http://10.0.2.2/tablets/subidaData.php";
                    RequestQueue rq  = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jo,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject Obj = response;

                                        JSONArray ja = Obj.getJSONArray("anArray");
                                        for (int i = 0; i <Obj.length() ; i++) {

                                            tvja.setText(ja.getString(i));
                                        }
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public JSONArray getInsertCapturas() throws JSONException {
        JSONArray dataArray = new JSONArray();
        String insert = "";
        List<PlCapturaAnopheles> capturas = new ArrayList<PlCapturaAnopheles>();
        PlCapturaAnophelesDao capDao = daoSession.getPlCapturaAnophelesDao();
        capturas = capDao.queryBuilder().where(PlCapturaAnophelesDao.Properties.Estado_sync.eq(0)).list();
        SimpleDateFormat dateFormat;
        dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for (PlCapturaAnopheles p: capturas){
            String date = dateFormat.format(p.getFechaHoraReg());
            insert = "INSERT INTO pl_captura_anopheles(id_captura, id_sibasi, id_tablet, id_semana_epidemiologica," +
                        "id_tipo_actividad, id_caserio, id_usuario_reg,id_estado, id_tipo_captura, total_mosquitos, total_anopheles," +
                        "casa_positiva, casa_inspeccionada, componente_inspeccionado, tiempo_colecta, fecha_hora_reg, propietario " +
                        "VALUES ('"+p.getId()+"','"+p.getIdSibasi()+"','"+p.getIdTablet()+"','"+p.getIdSemanaEpidemiologica()+"','"+p.getIdTipoActividad()+"','"+p.getIdCaserio()+"','"+p.getIdUsuarioReg()+"'," +
                        "'"+p.getIdEstado()+"','"+p.getIdTipoCaptura()+"','"+p.getTotalMosquitos()+"'" +
                        ","+p.getTotalAnopheles()+",'"+p.getCasaPositiva()+"','"+p.getCasaInspeccionada()+"','"+p.getComponenteInspeccionado()+"','"+p.getTiempoColecta()+"','"+date+"','"+p.getPropietario()+"');";
                  dataArray.put(insert);
        }
        return dataArray;
    }

}
