package com.minsal.dtic.sinavec.CRUD.capturaAnopheles;import android.Manifest;import android.app.DatePickerDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.content.SharedPreferences;import android.content.pm.PackageManager;import android.database.Cursor;import android.graphics.Color;import android.os.Build;import android.support.v7.app.AlertDialog;import android.support.annotation.NonNull;import android.support.annotation.RequiresApi;import android.support.v4.app.ActivityCompat;import android.support.v7.app.ActionBar;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.telephony.TelephonyManager;import android.text.Html;import android.text.TextUtils;import android.view.Gravity;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.DatePicker;import android.widget.EditText;import android.widget.ImageView;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;import com.minsal.dtic.sinavec.EntityDAO.CtlTablet;import com.minsal.dtic.sinavec.EntityDAO.CtlTabletDao;import com.minsal.dtic.sinavec.EntityDAO.DaoSession;import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividad;import com.minsal.dtic.sinavec.EntityDAO.PlTipoCaptura;import com.minsal.dtic.sinavec.MainActivity;import com.minsal.dtic.sinavec.MyMalaria;import com.minsal.dtic.sinavec.R;import com.minsal.dtic.sinavec.utilidades.Utilidades;import org.greenrobot.greendao.query.QueryBuilder;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Calendar;import java.util.Date;import java.util.List;public class CapturaAnopheles extends AppCompatActivity {    Spinner spMunicipio, spCanton, spCaserio, spCaptura, spActividad;    private DaoSession daoSession;    private Utilidades u;    private SharedPreferences pref;    int depto = MainActivity.depto;    private PlCapturaAnophelesDao capDao;    final Calendar myCalendar = Calendar.getInstance();    static final int GET_IMEI = 100;    ArrayList<String> listaMunicipio = new ArrayList<String>();    ArrayList<String> listaActividad;    ArrayList<String> listaTipoCaptura;    ArrayList<String> listaCanton = new ArrayList<String>();    ArrayAdapter<String> adapter2,adapter3;    ArrayList<String> listaCaserios = new ArrayList<String>();    List<CtlMunicipio> municipios;    List<PlTipoActividad> actividades;    List<CtlCaserio> caserios;    List<CtlCanton> cantones;    List<PlTipoCaptura> capturas;    EditText edtFecha, edtPropietario, edtAnopheles, edtTiempo, edtZancudo, edtComponente;    ImageView imGuardar, imHome;    Bundle bundle;    PlCapturaAnopheles capEdit;    long idCanton,idCaserio,idActividad, idTipoCaptura;    Object id_captura;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        daoSession = ((MyMalaria) getApplicationContext()).getDaoSession();        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);        setContentView(R.layout.activity_captura);        ActionBar actionBar = getSupportActionBar();        actionBar.setDisplayHomeAsUpEnabled(true);        spMunicipio   = (Spinner) findViewById(R.id.idMunicipioCap);        spCanton      = (Spinner) findViewById(R.id.idCantonCap);        spCaserio     = (Spinner) findViewById(R.id.idCaserioCap);        spActividad   = (Spinner) findViewById(R.id.spActividad);        spCaptura     = (Spinner) findViewById(R.id.spCaptura);        edtFecha      = (EditText) findViewById(R.id.edtFecha);        edtAnopheles  = (EditText) findViewById(R.id.edtAnopheles);        edtZancudo    = (EditText) findViewById(R.id.edtZancudo);        edtTiempo     = (EditText) findViewById(R.id.edtTiempo);        edtComponente = (EditText) findViewById(R.id.edtComponentes);        edtPropietario= (EditText) findViewById(R.id.edtPropietario);        imGuardar     = (ImageView) findViewById(R.id.imGuardar);        imHome        = (ImageView) findViewById(R.id.imHome);        bundle        = getIntent().getExtras();        capDao        = daoSession.getPlCapturaAnophelesDao();        u             = new Utilidades(daoSession);        spMunicipio.setFocusable(true);        spMunicipio.setFocusableInTouchMode(true);        spMunicipio.requestFocus();        loadSpinerMun();        loadSpinerActividad();        loadSpinerCaptura();        String accion = bundle.getString("accion");        if (accion.equals("edit")) {            try {                int componente = 0;                imGuardar.setImageResource(R.drawable.actualizar);                imHome.setImageResource(R.drawable.borrar);                id_captura = bundle.get("id");                capEdit = capDao.loadByRowId((long) id_captura);                String propietario = capEdit.getPropietario();                int tiempo    = capEdit.getTiempoColecta();                int total     = capEdit.getTotalMosquitos();                int anopheles = capEdit.getTotalAnopheles();                idActividad   = capEdit.getIdTipoActividad();                idTipoCaptura = capEdit.getIdTipoCaptura();                if (capEdit.getComponenteInspeccionado() != null) {                    componente = capEdit.getComponenteInspeccionado();                }                 idCaserio = capEdit.getIdCaserio();                 idCanton = capEdit.getCtlCaserio().getCtlCanton().getId();                long idMunicipio = capEdit.getCtlCaserio().getCtlCanton().getCtlMunicipio().getId();                edtTiempo.setText(String.valueOf(tiempo));                edtComponente.setText(String.valueOf(componente));                edtPropietario.setText(propietario);                edtZancudo.setText(String.valueOf(total));                edtAnopheles.setText(String.valueOf(anopheles));                //seteando los valores a los spinner                for (int i = 0; i <municipios.size() ; i++) {                    if (municipios.get(i).getId() == idMunicipio) {spMunicipio.setSelection(i + 1);}                }                for (int j = 0; j <actividades.size() ; j++) {                    if(actividades.get(j).getId() == idActividad){spActividad.setSelection(j + 1);}                }                for (int k = 0; k <capturas.size(); k++) {                    if(capturas.get(k).getId() == idTipoCaptura){spCaptura.setSelection(k + 1);}                }            } catch (Exception e) {                customToadError(getApplicationContext(), "Hubo un erro al obtener los datos de captura");                e.printStackTrace();            }        }        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {            @Override            public void onDateSet(DatePicker datePicker, int year, int month, int day) {                myCalendar.set(Calendar.YEAR, year);                myCalendar.set(Calendar.MONTH, month);                myCalendar.set(Calendar.DAY_OF_MONTH, day);                actualizaFecha();            }        };        edtFecha.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                new DatePickerDialog(v.getContext(), date, myCalendar                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();            }        });        listaCanton.add("Seleccione");        listaCaserios.add("Seleccione");        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCanton);        adapter2.notifyDataSetChanged();        spCanton.setAdapter(adapter2);        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCaserios);        spCaserio.setAdapter(adapter3);        adapter3.notifyDataSetChanged();        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {            @Override            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                if (position != 0) {                    cantones = u.loadSpinerCanton(municipios.get(position - 1).getId());                    listaCanton.clear();                    listaCanton = u.obetenerListaCantones(cantones);                    adapter2 = new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_list_item_1, listaCanton);                    spCanton.setAdapter(adapter2);                    adapter2.notifyDataSetChanged();                    if (bundle.getString("accion").equals("edit")){                        for (int j = 0; j <cantones.size() ; j++) {                            if (cantones.get(j).getId()==idCanton){                                spCanton.setSelection(j + 1);                            }                        }                    }else{                        spCanton.setSelection(0);                    }                } else {                    listaCanton.clear();                    listaCanton.add("Seleccione");                    adapter2.notifyDataSetChanged();                    spCanton.setSelection(0);                    listaCaserios.clear();                    listaCaserios.add("Seleccione");                    adapter3.notifyDataSetChanged();                    spCaserio.setSelection(0);                }            }            @Override            public void onNothingSelected(AdapterView<?> parent) {            }        });        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {            @Override            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                if (position != 0) {                    caserios = u.loadSpinerCaserio(cantones.get(position - 1).getId());                    listaCaserios.clear();                    listaCaserios = u.obetenerListaCaserios(caserios);                    adapter3 = new ArrayAdapter<>                            (parent.getContext(), android.R.layout.simple_list_item_1, listaCaserios);                    spCaserio.setAdapter(adapter3);                    adapter3.notifyDataSetChanged();                    if (bundle.getString("accion").equals("edit")){                        for (int j = 0; j <caserios.size() ; j++) {                            if (caserios.get(j).getId()==idCaserio){                                spCaserio.setSelection(j + 1);                            }                        }                    }                } else {                    listaCaserios.clear();                    listaCaserios.add("Seleccione");                    adapter3.notifyDataSetChanged();                    spCaserio.setSelection(0);                }            }            @Override            public void onNothingSelected(AdapterView<?> parent) {            }        });        //boton de guardar o actualizar depende de donde se llego        imGuardar.setOnClickListener(new View.OnClickListener() {            @RequiresApi(api = Build.VERSION_CODES.O)            @Override            public void onClick(View view) {                boolean dataValida = validaData();                if (dataValida){                    long idSibasi  = getIdSibasiUser();                    long idTablet  = getIdTablet();                    long idUsuario = getIdUser();                    long idCaserio = idCaserioCap();                    long idTipoCaptura   = idTipoCaptura();                    long idTipoActividad = idTipoActividad();                    String propietario   = edtPropietario.getText().toString().trim();                    int total = Integer.parseInt(edtZancudo.getText().toString().trim());                    int anopheles = Integer.parseInt(edtAnopheles.getText().toString().trim());                    int componentes = Integer.parseInt(edtComponente.getText().toString().trim());                    int tiempo = Integer.parseInt(edtTiempo.getText().toString().trim());                    try {                        if(bundle.getString("accion").equals("edit")) {                            updateCaptura(propietario, total, anopheles, componentes, idUsuario, idTipoActividad, idTipoCaptura,                                    tiempo, idCaserio, idTablet, idSibasi);                            //cleanField();                            Intent i = new Intent(getApplicationContext(),ListCapturaActivity.class);                            startActivity(i);                            finish();                            customToadSuccess(getApplicationContext(), "Captura Anopheles actualizada con éxito");                        }else{                            saveCaptura(propietario, total, anopheles, componentes, idUsuario, idTipoActividad, idTipoCaptura,                                    tiempo, idCaserio, idTablet, idSibasi);                           // cleanField();                            customToadSuccess(getApplicationContext(), "Captura Anopheles creada con éxito");                        }                    }catch (Exception e) {                        e.printStackTrace();                        Toast.makeText(getApplicationContext(), "Hubo un error al ingresar la captura", Toast.LENGTH_SHORT).show();                    }                }            }        });        imHome.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                if (bundle.getString("accion").equals("edit")){                    confirm();                }else{                    Intent i = new Intent(getApplicationContext(), MainActivity.class);                    startActivity(i);                    finish();                }            }        });    }//fin metodo create    private void cleanField() {        edtTiempo.setText("");        edtAnopheles.setText("");        edtZancudo.setText("");        edtPropietario.setText("");        edtComponente.setText("");    }    private void loadSpinerMun() {        Utilidades u   = new Utilidades(daoSession);        municipios     = u.loadspinnerMunicipio(depto);        listaMunicipio = u.obtenerListaMunicipio(municipios);        ArrayAdapter<String> adapter = new ArrayAdapter<>                (this, android.R.layout.simple_list_item_1, listaMunicipio);        spMunicipio.setAdapter(adapter);        adapter.notifyDataSetChanged();    }    private void loadSpinerActividad() {        actividades    = u.loadspinnerActividad();        listaActividad = u.obtenerListaActividad(actividades);        ArrayAdapter<String> adapter = new ArrayAdapter<>                (this, android.R.layout.simple_list_item_1, listaActividad);        spActividad.setAdapter(adapter);        adapter.notifyDataSetChanged();    }    private void loadSpinerCaptura() {        capturas         = u.loadspinnerCaptura();        listaTipoCaptura = u.obtenerListaCaptura(capturas);        ArrayAdapter<String> adapter = new ArrayAdapter<>                (this, android.R.layout.simple_list_item_1, listaTipoCaptura);        spCaptura.setAdapter(adapter);        adapter.notifyDataSetChanged();    }    private void actualizaFecha() {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");        edtFecha.setText(sdf.format(myCalendar.getTime()));    }    public boolean validaData() {        boolean validate = false;        int listIdCaptura = spCaptura.getSelectedItemPosition();        int caserio = spCaserio.getSelectedItemPosition();        int actividad = spActividad.getSelectedItemPosition();        if (caserio == 0) {            spCaserio.setFocusableInTouchMode(true);            spCaserio.requestFocus();            TextView errorText=(TextView)spCaserio.getSelectedView();            errorText.setError("");            errorText.setTextColor(Color.BLUE);            errorText.setText("Seleccione un Caserío");        } else if (actividad == 0) {            spActividad.setFocusableInTouchMode(true);            spActividad.requestFocus();            TextView errorText=(TextView)spActividad.getSelectedView();            errorText.setError("");            errorText.setTextColor(Color.BLUE);            errorText.setText("Seleccione el tipo de actividad");        } else if (listIdCaptura == 0) {            spCaptura.setFocusableInTouchMode(true);            spCaptura.requestFocus();            TextView errorText=(TextView)spCaptura.getSelectedView();            errorText.setError("");            errorText.setTextColor(Color.BLUE);            errorText.setText("Seleccione el tipo de captura");        } else if (TextUtils.isEmpty(edtPropietario.getText().toString().trim())) {            edtPropietario.requestFocus();            edtPropietario.setError("Campo requerido");        } else if (TextUtils.isEmpty(edtZancudo.getText().toString().trim())) {            edtZancudo.requestFocus();            edtZancudo.setError("Campo requerido");        } else if (TextUtils.isEmpty(edtAnopheles.getText().toString().trim())) {            edtAnopheles.requestFocus();            edtAnopheles.setError("Campo requerido");        } else if (TextUtils.isEmpty(edtTiempo.getText().toString().trim())) {            edtTiempo.requestFocus();            edtTiempo.setError("Campo requerido");        }else if(TextUtils.isEmpty(edtComponente.getText().toString().trim())){            edtComponente.requestFocus();            edtComponente.setError("campo requerido");        } else {            validate = true;        }        return validate;    }    public long idCaserioCap() {        int listIdMunicipio = spCaserio.getSelectedItemPosition();        int idMunicipio = 0;        if (listIdMunicipio != 0) {            idMunicipio = (int) (long) caserios.get(listIdMunicipio - 1).getId();        }        return idMunicipio;    }    public long idTipoCaptura() {        int listIdCaptura = spCaptura.getSelectedItemPosition();        int idTipoCaptura = 0;        if (listIdCaptura != 0) {            idTipoCaptura = (int) (long) capturas.get(listIdCaptura - 1).getId();        }        return idTipoCaptura;    }    public long idTipoActividad() {        int listIdActividad = spActividad.getSelectedItemPosition();        int idTipoActividad = 0;        if (listIdActividad != 0) {            idTipoActividad = (int) (long) actividades.get(listIdActividad - 1).getId();        }        return idTipoActividad;    }    public void saveCaptura(String propietario, int total,       int anopheles,                            int componentes,    long usuarioReg, long idActividad,                            long idTipoCaptura, int tiempo,      long idcaserio,                            long idTablet,      long idSibasi) throws ParseException {        Date currentTime = Calendar.getInstance().getTime();        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        String fecha = dateFormat.format(currentTime);        Date fec = dateFormat.parse(fecha);        int semanaActual = getSemana();        PlCapturaAnopheles cap = new PlCapturaAnopheles();        cap.setIdTipoActividad(idActividad);        cap.setIdTipoCaptura(idTipoCaptura);        cap.setTotalMosquitos(total);        cap.setTotalAnopheles(anopheles);        cap.setFechaHoraReg(fec);        cap.setFecha(fec);        cap.setIdCaserio(idcaserio);        cap.setIdTablet(idTablet);        cap.setIdSibasi(idSibasi);        cap.setTiempoColecta(tiempo);        cap.setIdSemanaEpidemiologica(semanaActual);        cap.setIdUsuarioReg(usuarioReg);        cap.setPropietario(propietario);        cap.setIdEstado(1);        if (anopheles > 0) {            cap.setCasaPositiva(1);        } else {            cap.setCasaPositiva(0);        }        cap.setIdUsuarioMod(usuarioReg);        if (componentes > 0) {            cap.setComponenteInspeccionado(componentes);        }        capDao.insert(cap);    }    //obteniendo el usuario en sesion    public long getIdUser() {        String username = pref.getString("user", "");        long id = 0;        if (!username.equals("")) {            List<FosUserUser> ids = null;            FosUserUserDao userDao = daoSession.getFosUserUserDao();            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();            qb.where(FosUserUserDao.Properties.Username.eq(username));            ids = qb.list();            for (FosUserUser f : ids) {                id = f.getId();            }        }        return id;    }    public long getIdSibasiUser() {        String username = pref.getString("user", "");        long idSibasi = 0;        if (!username.equals("")) {            List<FosUserUser> ids = null;            FosUserUserDao userDao = daoSession.getFosUserUserDao();            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();            qb.where(FosUserUserDao.Properties.Username.eq(username));            ids = qb.list();            for (FosUserUser f : ids) {                idSibasi = f.getIdSibasi();            }        }        return idSibasi;    }    //obteniendo el id de la tablet    @RequiresApi(api = Build.VERSION_CODES.O)    public long getIdTablet() {        String imei = getIMEINumber();        long idtablet = 0;        if (!imei.equals("")) {            List<CtlTablet> ids = null;            CtlTabletDao tabDao = daoSession.getCtlTabletDao();            QueryBuilder<CtlTablet> qb = tabDao.queryBuilder();            qb.where(CtlTabletDao.Properties.Serie.eq(imei)); //el imei se esta guardando en el campo serie            ids = qb.list();            for (CtlTablet t : ids) {                idtablet = t.getId();            }        }        return idtablet;    }    //para el id necesito el imei    @RequiresApi(api = Build.VERSION_CODES.O)    public String getIMEINumber() {        String myAndroidDeviceId = "";        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);        //comprobar version de android usando        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, GET_IMEI);            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)                ;            myAndroidDeviceId = mTelephony.getDeviceId();            return myAndroidDeviceId;        } else {            myAndroidDeviceId = mTelephony.getImei();        }        return myAndroidDeviceId;    }    public int getSemana() {        int semana = 0;        try {            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");            Date now = new Date();            String strDate = sdfDate.format(now);            String sqlQUERY = "SELECT semana FROM ctl_semana_epi where '" + strDate + "' BETWEEN fecha_inicio " +                    "and fecha_fin";            Cursor c = daoSession.getDatabase().rawQuery(sqlQUERY, null);            if (c.moveToFirst()) {                do {                    semana = c.getInt(0);                } while (c.moveToNext());            }            c.close();        } catch (Exception e) {            e.printStackTrace();        }        return semana;    }    private boolean checkPermission(String permission) {        int result = this.checkCallingOrSelfPermission(permission);        return result == PackageManager.PERMISSION_GRANTED;    }    public void olderVersion() {        if (checkPermission(Manifest.permission.READ_PHONE_STATE)) {        } else {            Toast.makeText(getApplicationContext(), "Sin permiso para leer IMEI", Toast.LENGTH_SHORT).show();        }    }    @Override    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {        switch (requestCode) {            case GET_IMEI:                String permission = permissions[0];                int result = grantResults[0];                if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {                    //comprobar si el user acepto el permiso}                    if (result == PackageManager.PERMISSION_GRANTED) {//acepto                    } else {//no dio el permiso                        Toast.makeText(getApplicationContext(), "No acepto el permiso para leer IMEI", Toast.LENGTH_SHORT).show();                    }                }                break;            default:                super.onRequestPermissionsResult(requestCode, permissions, grantResults);                break;        }    }    public void customToadError(Context context, String message) {        LayoutInflater inflater = getLayoutInflater();        View layout = inflater.inflate(R.layout.toad_error,                (ViewGroup) findViewById(R.id.custom_toast_container));        TextView text = (TextView) layout.findViewById(R.id.tvToasError);        text.setText(message);        Toast toast = new Toast(context);        toast.setGravity(Gravity.BOTTOM, 0, 0);        toast.setDuration(Toast.LENGTH_SHORT);        toast.setView(layout);        toast.show();    }    public void customToadSuccess(Context context, String message) {        LayoutInflater inflater = getLayoutInflater();        View layout = inflater.inflate(R.layout.toad_exito,                (ViewGroup) findViewById(R.id.custom_toast_container_exito));        TextView text = (TextView) layout.findViewById(R.id.tvToasExito);        text.setText(message);        Toast toast = new Toast(context);        toast.setGravity(Gravity.BOTTOM, 0, 0);        toast.setDuration(Toast.LENGTH_SHORT);        toast.setView(layout);        toast.show();    }    public void updateCaptura(String propietario, int total,        int anopheles,                              int componentes,    long usuarioReg, long idActividad,                              long idTipoCaptura, int tiempo,      long idcaserio,                              long idTablet,      long idSibasi) throws ParseException {        Date currentTime = Calendar.getInstance().getTime();        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        String fecha = dateFormat.format(currentTime);        Date fec = dateFormat.parse(fecha);        int semanaActual = getSemana();        capEdit.setIdTipoActividad(idActividad);        capEdit.setIdTipoCaptura(idTipoCaptura);        capEdit.setTotalMosquitos(total);        capEdit.setTotalAnopheles(anopheles);        capEdit.setFechaHoraMod(fec);        capEdit.setIdCaserio(idcaserio);        capEdit.setTiempoColecta(tiempo);        capEdit.setIdUsuarioMod(usuarioReg);        capEdit.setPropietario(propietario);        capEdit.setIdEstado(1);        if (anopheles > 0) {            capEdit.setCasaPositiva(1);        } else {            capEdit.setCasaPositiva(0);        }        capEdit.setIdUsuarioMod(usuarioReg);        if (componentes > 0) {            capEdit.setComponenteInspeccionado(componentes);        }        capDao.update(capEdit);    }    public void confirm() {        AlertDialog.Builder builder = new AlertDialog.Builder(CapturaAnopheles.this);        builder.setMessage(Html.fromHtml("<font color='#FF0000'><b>¿Seguro que desea eliminar la captura anopheles?</b></font>"))                .setNegativeButton(Html.fromHtml("Cancelar"), null)                .setPositiveButton(Html.fromHtml("Sí, Eliminar"), new DialogInterface.OnClickListener() {                    @Override                    public void onClick(DialogInterface dialogInterface, int i) {                        capDao.deleteByKey((long) id_captura);                        Toast.makeText(getApplicationContext(), "La Captura se eliminó con éxito", Toast.LENGTH_SHORT).show();                        Intent intent = new Intent(getApplicationContext(), ListCapturaActivity.class);                        startActivity(intent);                        finish();                    }                })                .setCancelable(false);                //.create().show();        AlertDialog a = builder.create();        a.show();        Button btnPositivo = a.getButton(DialogInterface.BUTTON_POSITIVE);        btnPositivo.setTextColor(Color.RED);        Button btnNegativo = a.getButton(DialogInterface.BUTTON_NEGATIVE);        btnNegativo.setTextColor(Color.GREEN);    }    @Override    public void onBackPressed() {       Intent list =new Intent(getApplicationContext(),ListCapturaActivity.class);       startActivity(list);       finish();    }}