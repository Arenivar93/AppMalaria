package com.minsal.dtic.sinavec;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlSemanaEpi;
import com.minsal.dtic.sinavec.EntityDAO.CtlSemanaEpiDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlTablet;
import com.minsal.dtic.sinavec.EntityDAO.CtlTabletDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividad;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCaptura;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CapturaAnopheles extends AppCompatActivity {
    Spinner spMunicipio, spCanton, spCaserio, sptipo, spActividad;
    List<CtlMunicipio> municipios;
    ArrayList<String> listaMunicipio = new ArrayList<String>();
    private DaoSession daoSession;
    int depto = MainActivity.depto;
    List<PlTipoActividad> actividades;
    ArrayList<String> listaActividad;
    ArrayList<String> listaTipoCaptura;
    ArrayList<String> listaCanton = new ArrayList<String>();
    ArrayList<String> listaCaserios = new ArrayList<String>();
    List<CtlCanton> cantones;
    List<CtlCaserio> caserios;
    List<PlTipoCaptura> capturas;
    private Utilidades u;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    EditText edtFecha, edtPropietario, edtAnopheles, edtTiempo, edtZancudo, edtComponente;
    ImageView imGuardar;
    final Calendar myCalendar = Calendar.getInstance();
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_captura);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        spMunicipio = (Spinner) findViewById(R.id.idMunicipioCap);
        spCanton = (Spinner) findViewById(R.id.idCantonCap);
        spCaserio = (Spinner) findViewById(R.id.idCaserioCap);
        spActividad = (Spinner) findViewById(R.id.spActividad);
        sptipo = (Spinner) findViewById(R.id.spCaptura);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtAnopheles = (EditText) findViewById(R.id.edtAnopheles);
        edtZancudo = (EditText) findViewById(R.id.edtZancudo);
        edtTiempo = (EditText) findViewById(R.id.edtTiempo);
        edtComponente = (EditText) findViewById(R.id.edtComponentes);
        edtPropietario = (EditText) findViewById(R.id.edtPropietario);
        imGuardar = (ImageView) findViewById(R.id.imGuardar);
        u = new Utilidades(daoSession);
        long ids =5;
        CtlSemanaEpiDao semDao = daoSession.getCtlSemanaEpiDao();
        CtlSemanaEpi sem = semDao.load(ids);






        loadSpinerMun();
        loadSpinerActividad();
        loadSpinerCaptura();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                actualizaFecha();
            }
        };

        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        listaCanton.add("Seleccione");
        listaCaserios.add("Seleccione");
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCanton);
        adapter2.notifyDataSetChanged();
        spCanton.setAdapter(adapter2);
        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCaserios);
        spCaserio.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();
        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    cantones = u.loadSpinerCanton(municipios.get(position - 1).getId());
                    listaCanton.clear();
                    listaCanton = u.obetenerListaCantones(cantones);
                    adapter2 = new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_list_item_1, listaCanton);
                    spCanton.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    spCanton.setSelection(0);
                } else {
                    listaCanton.clear();
                    listaCanton.add("Seleccione");
                    adapter2.notifyDataSetChanged();
                    spCanton.setSelection(0);
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    caserios = u.loadSpinerCaserio(cantones.get(position - 1).getId());
                    listaCaserios.clear();
                    listaCaserios = u.obetenerListaCaserios(caserios);
                    adapter3 = new ArrayAdapter<>
                            (parent.getContext(), android.R.layout.simple_list_item_1, listaCaserios);
                    spCaserio.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                } else {
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapter3.notifyDataSetChanged();
                    spCaserio.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean dataValida = validaData();
                try {
                    if (dataValida) {
                        long idSibasi = getIdSibasiUser();
                        long idTablet = getIdTablet();
                        long idUsuario = getIdUser();
                        long idCaserio = idCaserioCap();
                        long idTipoCaptura = idTipoCaptura();
                        long idTipoActividad = idTipoActividad();
                        String propietario = edtPropietario.getText().toString().trim();
                        int total = Integer.parseInt(edtZancudo.getText().toString().trim());
                        int anopheles = Integer.parseInt(edtAnopheles.getText().toString().trim());
                        int componentes = Integer.parseInt(edtComponente.getText().toString().trim());
                        int tiempo = Integer.parseInt(edtTiempo.getText().toString().trim());
                        saveCaptura(propietario, total, anopheles, componentes, idUsuario, idTipoActividad, idTipoCaptura,
                                tiempo,idCaserio,idTablet,idSibasi);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }//fin metodo create


    private void loadSpinerMun() {
        Utilidades u = new Utilidades(daoSession);
        municipios = u.loadspinnerMunicipio(depto);
        listaMunicipio = u.obtenerListaMunicipio(municipios);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listaMunicipio);
        spMunicipio.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadSpinerActividad() {
        actividades = u.loadspinnerActividad();
        listaActividad = u.obtenerListaActividad(actividades);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listaActividad);
        spActividad.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void loadSpinerCaptura() {
        capturas = u.loadspinnerCaptura();
        listaTipoCaptura = u.obtenerListaCaptura(capturas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listaTipoCaptura);
        sptipo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void actualizaFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        edtFecha.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean validaData() {
        boolean validate = false;
        if (TextUtils.isEmpty(edtPropietario.getText().toString().trim())) {
            edtPropietario.requestFocus();
            edtPropietario.setError("Campo requerido");
        } else if (TextUtils.isEmpty(edtZancudo.getText().toString().trim())) {
            edtZancudo.requestFocus();
            edtZancudo.setError("Campo requerido");
        } else if (TextUtils.isEmpty(edtAnopheles.getText().toString().trim())) {
            edtAnopheles.requestFocus();
            edtAnopheles.setError("Campo requerido");
        } else if (TextUtils.isEmpty(edtTiempo.getText().toString().trim())) {
            edtTiempo.requestFocus();
            edtTiempo.setError("Campo requerido");
        } else {
            validate = true;
        }
        return validate;
    }

    public long idCaserioCap() {
        int listIdMunicipio = spCaserio.getSelectedItemPosition();
        int idMunicipio = 0;
        if (listIdMunicipio != 0) {
            idMunicipio = (int) (long) caserios.get(listIdMunicipio - 1).getId();
        }
        return idMunicipio;
    }

    public long idTipoCaptura() {
        int listIdCaptura = sptipo.getSelectedItemPosition();
        int idTipoCaptura = 0;
        if (listIdCaptura != 0) {
            idTipoCaptura = (int) (long) capturas.get(listIdCaptura - 1).getId();
        }
        return idTipoCaptura;
    }

    public long idTipoActividad() {
        int listIdActividad = sptipo.getSelectedItemPosition();
        int idTipoActividad = 0;
        if (listIdActividad != 0) {
            idTipoActividad = (int) (long) actividades.get(listIdActividad - 1).getId();
        }
        return idTipoActividad;
    }

    public void saveCaptura(String propietario, int total, int anopheles,
                            int componentes, long usuarioReg, long idActividad, long idTipoCaptura,
                            int tiempo,long idcaserio,long idTablet,long idSibasi) throws ParseException {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = dateFormat.format(currentTime);
        Date fec = dateFormat.parse(fecha);

        PlCapturaAnophelesDao capDao = daoSession.getPlCapturaAnophelesDao();
        PlCapturaAnopheles cap = new PlCapturaAnopheles();
        cap.setIdTipoActividad(idActividad);
        cap.setIdTipoCaptura(idTipoCaptura);
        cap.setTiempoColecta(tiempo);
        cap.setTotalMosquitos(total);
        cap.setTotalAnopheles(anopheles);
        cap.setFechaHoraReg(fec);
        cap.setFecha(fec);
        cap.setIdCaserio(idcaserio);
        cap.setIdTablet(idTablet);
        cap.setIdSibasi(idSibasi);
        cap.setIdSemanaEpidemiologica(2);
        cap.setIdUsuarioReg(usuarioReg);
        cap.setPropietario(propietario);
        cap.setIdEstado(1);
        cap.setCasaPositiva(0);
        cap.setIdUsuarioMod(usuarioReg);
        if (componentes > 0) {
            cap.setComponenteInspeccionado(componentes);
        }
        capDao.insert(cap);
    }

//obteniendo el usuario en sesion
    public  long getIdUser() {
        String username = pref.getString("user", "");
        long id = 0;
        if (!username.equals("")) {
            List<FosUserUser> ids = null;
            FosUserUserDao userDao = daoSession.getFosUserUserDao();
            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
            qb.where(FosUserUserDao.Properties.Username.eq(username));
            ids = qb.list();
            for (FosUserUser f : ids) {
                id = f.getId();
            }
        }
        return id;
    }
    //obteniendo el sibasi del usuario
    public long getIdSibasiUser() {
        String username = pref.getString("user", "");
        long idSibasi = 0;
        if (!username.equals("")) {
            List<FosUserUser> ids = null;
            FosUserUserDao userDao = daoSession.getFosUserUserDao();
            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
            qb.where(FosUserUserDao.Properties.Username.eq(username));
            ids = qb.list();
            for (FosUserUser f : ids) {
                idSibasi = f.getIdSibasi();
            }
        }
        return idSibasi;
    }
    //obteniendo el id de la tablet
    public long getIdTablet() {
        String imei =getIMEINumber();
        long idtablet = 0;
        if (!imei.equals("")) {
            List<CtlTablet> ids = null;
            CtlTabletDao tabDao = daoSession.getCtlTabletDao();
            QueryBuilder<CtlTablet> qb = tabDao.queryBuilder();
            qb.where(CtlTabletDao.Properties.Serie.eq(imei)); //el imei se esta guardando en el campo serie
            ids = qb.list();
            for (CtlTablet t : ids) {
                idtablet = t.getId();
            }
        }
        return idtablet;
    }
    //para el id necesito el imei
    public  String getIMEINumber() {
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
    public int getSemana(Date date1){
        int semana = 0;
        if (date1 !=null){
            List<CtlSemanaEpi> ids = null;
            CtlSemanaEpiDao semDao = daoSession.getCtlSemanaEpiDao();
            QueryBuilder<CtlSemanaEpi> qb = semDao.queryBuilder();
            qb.where(CtlSemanaEpiDao.Properties.FechaFin.between(CtlSemanaEpiDao.Properties.FechaInicio,CtlSemanaEpiDao.Properties.FechaFin));
            ids = qb.list();
            for (CtlSemanaEpi f : ids) {
                semana = f.getSemana();
            }
        }

        return semana;
    }

}




