package com.minsal.dtic.sinavec.CRUD.gotaGruesa.activityGotaGruesa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa.nuevaGotaGruesaFragment;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamento;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPais;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesa;
import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesaDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GotaGruesaEditActivity extends AppCompatActivity {
    private nuevaGotaGruesaFragment.procedenciaDialogListener mListener;
    private DaoSession daoSession;
    private CardView colvolCard;
    private Spinner spEdad,spSexo, spLaboratorio,spDepartamento,
            spMunicipio,spCanton,spCaserio,spPais;
    ArrayList<String> tipoEdad=new ArrayList<String>();
    ArrayAdapter<String> adapterEdad;
    ArrayList<String> sexo=new ArrayList<String>();
    ArrayAdapter<String> adapterSexo;
    ArrayList<String> listaLaboratorios=new ArrayList<String>();
    ArrayAdapter<String> adapterLaboratorio;
    private List<CtlPais> paises;
    private List<CtlDepartamento> departamentos;
    private List<CtlMunicipio> municipios;
    private List<CtlCanton> cantones;
    private List<CtlCaserio> caserios;
    private ArrayList<String> listaPaises=new ArrayList<>();
    private ArrayList<String> listaDepartamentos=new ArrayList<>();
    private ArrayList<String> listaMunicipios=new ArrayList<>();
    private ArrayList<String> listaCantones=new ArrayList<>();
    private ArrayList<String> listaCaserios=new ArrayList<>();
    ArrayAdapter<String> adapterPaises;
    ArrayAdapter<String> adapterDepartamento;
    ArrayAdapter<String> adapterMunicipio;
    ArrayAdapter<String> adapterCanton;
    ArrayAdapter<String> adapterCaserio;
    private EditText fechaToma,fechaFiebre,tvGgDescripcion,
            tvGge6;
    TextInputEditText fechaNac,titGgNombre,titGgSegundoNombre,titGgPrimerApellido,
            titGgSegundoApellido,titGgEdad,titGgNumeroDoc,titGgResponsable;
    private int dia,mes,anio;
    private long idSibasi;
    private long idTablet;
    private long idUsuario,idClave;
    int salir=0;
    private Spinner spGgTipoDoc;
    String nombreColvol;

    Utilidades utilidades;
    private LinearLayout linearLocal,linearExtranjero;
    Switch swExtrajero;
    ImageView btnGuardar, btnCancelar;
    String fechaTomaTxt,fechaFiebreTxt;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<CtlEstablecimiento> laboratorios;
    private SharedPreferences prefs;
    PlGotaGruesa gota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gota_gruesa_edit);
        Bundle bundle = getIntent().getExtras();
        prefs               = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

       long idRegistro = bundle.getLong("idRegistro");
        daoSession=((MyMalaria)getApplication()).getDaoSession();
        utilidades=new Utilidades(daoSession);
        //declaracion de objetos del layout
        fechaToma=(EditText)findViewById(R.id.tvGgFechaTomaEdit);
        fechaFiebre= (EditText)findViewById(R.id.tvGgfechaInicioFiebreEdit);
        tvGgDescripcion= (EditText)findViewById(R.id.tvGgDescripcionEdit);
        colvolCard=(CardView) findViewById(R.id.cardColvolEdit);
        spEdad = (Spinner)findViewById(R.id.spEdadEdit);
        spSexo = (Spinner)findViewById(R.id.spGgSexoEdit);
        fechaNac = (TextInputEditText)findViewById(R.id.titGgFechaNacEdit);
        spLaboratorio = (Spinner)findViewById(R.id.spGgLabLeeEdit);
        spDepartamento = (Spinner)findViewById(R.id.spGgDepartamentoEdit);
        spMunicipio = (Spinner)findViewById(R.id.spGgMunicipioEdit);
        spCanton = (Spinner)findViewById(R.id.spGgCantonEdit);
        spCaserio = (Spinner)findViewById(R.id.spGgCaserioEdit);
        spPais=(Spinner)findViewById(R.id.spGgPaisEdit);
        linearExtranjero=(LinearLayout)findViewById(R.id.linearExtranjeroEdit);
        linearLocal=(LinearLayout)findViewById(R.id.linearLocalEdit);
        swExtrajero = (Switch)findViewById(R.id.swGgExtranjeroEdit);
        fechaToma.setFocusable(false);
        fechaToma.setClickable(true);
        fechaFiebre.setFocusable(false);
        fechaFiebre.setClickable(true);
        fechaNac.setFocusable(false);
        fechaNac.setClickable(true);
        titGgNombre = findViewById(R.id.titGgNombreEdit);
        titGgSegundoNombre = (TextInputEditText)findViewById(R.id.titGgSegundoNombreEdit);
        titGgPrimerApellido = (TextInputEditText)findViewById(R.id.titGgPrimerApellidoEdit);
        titGgSegundoApellido = (TextInputEditText)findViewById(R.id.titGgSegundoApellidoEdit);
        titGgEdad = (TextInputEditText)findViewById(R.id.titGgEdadEdit);
        tvGge6 = (EditText)findViewById(R.id.tvGge6Edit);
        btnGuardar = (ImageView)findViewById(R.id.btnGgGuardarEdit);
        btnCancelar = (ImageView)findViewById(R.id.btnGgCancelarEdit);
        spGgTipoDoc =(Spinner)findViewById(R.id.spGgTipoDocEdit);
        titGgNumeroDoc =(TextInputEditText)findViewById(R.id.titGgNumeroDocEdit);
        titGgResponsable =(TextInputEditText)findViewById(R.id.titGgResponsableEdit);
        idSibasi  = prefs.getLong("idSibasiUser",0);
        idTablet  = prefs.getLong("idTablet",0);
        idUsuario = prefs.getLong("idUser",0);
        listaMunicipios.add("Seleccione");
        adapterMunicipio=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listaMunicipios);
        spMunicipio.setAdapter(adapterMunicipio);
        listaCantones.add("Seleccione");
        adapterCanton = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCantones);
        adapterCanton.notifyDataSetChanged();
        spCanton.setAdapter(adapterCanton);
        listaCaserios.add("Seleccione");
        adapterCaserio=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listaCaserios);
        spCaserio.setAdapter(adapterCaserio);
        laboratorios=utilidades.obtenerLaboratorios(idSibasi);
        listaLaboratorios=utilidades.obtenerListaEstablecimientoClave(laboratorios);
        adapterLaboratorio=new ArrayAdapter
                (getApplicationContext(),android.R.layout.simple_list_item_1,listaLaboratorios);
        adapterLaboratorio.notifyDataSetChanged();
        spLaboratorio.setAdapter(adapterLaboratorio);
        adapterEdad=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, MetodosGlobales.getTipoEdad());
        spEdad.setAdapter(adapterEdad);
        ArrayAdapter adapterTipoDoc = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, MetodosGlobales.getTipoDocumento());
        spGgTipoDoc.setAdapter(adapterTipoDoc);
        spGgTipoDoc.setSelection(7);
        //llena el spiner departamento
        departamentos=utilidades.loadspinnerDepartamento();
        listaDepartamentos=utilidades.obtenerListaDepartamento(departamentos);
        adapterDepartamento=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listaDepartamentos);
        spDepartamento.setAdapter(adapterDepartamento);
        adapterSexo=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,MetodosGlobales.getSexo());
        spSexo.setAdapter(adapterSexo);
        paises=utilidades.loadspinnerPais();
        listaPaises=utilidades.obtenerListaPais(paises);
        adapterPaises=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listaPaises);
        spPais.setAdapter(adapterPaises);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valida = validateData();
                if (valida){
                    saveData();
                }else{
                    Toast.makeText(getApplicationContext(),"Existen errores en el formulario",Toast.LENGTH_SHORT).show();
                }

            }
        });
        swExtrajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swExtrajero.isChecked()){
                    linearExtranjero.setVisibility(View.VISIBLE);
                    linearLocal.setVisibility(View.GONE);
                    spDepartamento.setSelection(0);
                }else{
                    linearLocal.setVisibility(View.VISIBLE);
                    linearExtranjero.setVisibility(View.GONE);
                    spPais.setSelection(0);

                }
            }
        });
        spDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    municipios=utilidades.loadspinnerMunicipio((int)(long)departamentos.get(i-1).getId());
                    listaMunicipios.clear();
                    listaMunicipios=utilidades.obtenerListaMunicipio(municipios);
                    adapterMunicipio=new ArrayAdapter<>(adapterView.getContext(),android.R.layout.simple_list_item_1,listaMunicipios);
                    spMunicipio.setAdapter(adapterMunicipio);
                    adapterMunicipio.notifyDataSetChanged();
                    if (gota.getIdPais()==68){
                        long idMunicipio = gota.getCtlCaserio().getCtlCanton().getIdMunicipio();
                        for (int p = 0; p <municipios.size() ; p++) {
                            if(municipios.get(p).getId()== idMunicipio){
                                Log.i("******",String.valueOf(p));
                                spMunicipio.setSelection(p+1);
                            }
                        }
                    }
                }else{
                    listaMunicipios.clear();
                    listaMunicipios.add("Seleccione");
                    adapterMunicipio.notifyDataSetChanged();
                    listaCantones.clear();
                    listaCantones.add("Selecciones");
                    adapterCanton.notifyDataSetChanged();
                    listaCaserios.clear();
                    listaCaserios.add("Selecciones");
                    adapterCaserio.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    cantones=utilidades.loadSpinerCanton(municipios.get(i-1).getId());
                    listaCantones.clear();
                    listaCantones=utilidades.obetenerListaCantones(cantones);
                    adapterCanton=new ArrayAdapter<>(adapterView.getContext(),android.R.layout.simple_list_item_1,listaCantones);
                    spCanton.setAdapter(adapterCanton);
                    adapterCanton.notifyDataSetChanged();
                    if (gota.getIdPais()==68){
                        long idCanton = gota.getCtlCaserio().getCtlCanton().getId();
                        for (int p = 0; p <cantones.size() ; p++) {
                            if(cantones.get(p).getId()== idCanton){
                                Log.i("******",String.valueOf(p));
                                spCanton.setSelection(p+1);
                            }
                        }
                    }
                }else{
                    listaCantones.clear();
                    listaCantones.add("Seleccione");
                    adapterCanton.notifyDataSetChanged();
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapterCaserio.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    caserios=utilidades.loadSpinerCaserio(cantones.get(i-1).getId());
                    listaCaserios.clear();
                    listaCaserios=utilidades.obetenerListaCaserios(caserios);
                    adapterCaserio=new ArrayAdapter<>(adapterView.getContext(),android.R.layout.simple_list_item_1,listaCaserios);
                    spCaserio.setAdapter(adapterCaserio);
                    adapterCaserio.notifyDataSetChanged();
                    if (gota.getIdPais()==68){
                        long idCaserio = gota.getIdCaserio();
                        for (int j = 0; j <caserios.size() ; j++) {
                            if (idCaserio== caserios.get(j).getId());{
                                spCaserio.setSelection(j+1);
                            }

                        }
                    }

                }else{
                    listaCaserios.clear();
                    listaCaserios.add("Seleccione");
                    adapterCaserio.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        try {
             gota = daoSession.getPlGotaGruesaDao().loadByRowId(idRegistro);
            titGgNombre.setText(gota.getPrimerNombre());
            titGgPrimerApellido.setText(gota.getPrimerApellido());
            titGgEdad.setText(String.valueOf(gota.getEdad()));
            int sexo = gota.getIdSexo();
            //fecha de fiebre
            String fechaFiebreEdit =gota.getFechaFiebre();
            String fechaEditex= formatDate(fechaFiebreEdit,"yyyy-MM-dd","dd-MM-yyyy");
            fechaFiebre.setText(fechaEditex);
            //fecha de toma
            String fechaTomaEdit =gota.getFechaToma();


            tvGge6.setText(String.valueOf(gota.getIdE6()));
            long idPais = gota.getIdPais();
            tvGgDescripcion.setText(gota.getDireccion());
            if (gota.getSegundoNombre()!= null){
                titGgSegundoNombre.setText(gota.getSegundoNombre());
            }
            if (gota.getSegundoApellido()!= null){
                titGgSegundoApellido.setText(gota.getSegundoApellido());
            }
            if (gota.getFechaNacimiento() != null){
                fechaNac.setText(gota.getFechaNacimiento());
            }
            if (gota.getNumeroDocIdePaciente() != null){
                titGgNumeroDoc.setText(gota.getNumeroDocIdePaciente());
            }
            if (gota.getResponsable() != null){
                titGgResponsable.setText(gota.getResponsable());
            }
            long idLab = gota.getIdLabLectura();
            for (int i = 0; i <laboratorios.size() ; i++) {
                if (idLab == laboratorios.get(i).getId()){
                    spLaboratorio.setSelection(i+1);
                }
            }
            if(idPais != 68){
                //es extranjero
                setSpPais(idPais);
                swExtrajero.setChecked(true);
               linearExtranjero.setVisibility(View.VISIBLE);
               linearLocal.setVisibility(View.GONE);
            }else{
                //llenar los spines de depto
                long idDepto = gota.getCtlCaserio().getCtlCanton().getCtlMunicipio().getIdDepartamento();
                for (int i = 0; i <departamentos.size() ; i++) {
                    if (departamentos.get(i).getId() == idDepto) {spDepartamento.setSelection(i + 1);}
                }

            }
            if (sexo ==1){
                spSexo.setSelection(1);
            }else{spSexo.setSelection(2);}
            int tipoDocumento = Integer.parseInt(gota.getIdDocIdePaciente());
            setTipoDocumento(tipoDocumento);
            int idLaboratorio = gota.getIdLabLectura();
            setSpLaboratorio(idLaboratorio);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error al obtner los datos "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    public void setTipoDocumento(int idDoc){
        ArrayList<String> tipodoc = MetodosGlobales.getTipoDocumento();
        for (int i = 0; i <tipodoc.size() ; i++) {
            if (i== idDoc){
                spGgTipoDoc.setSelection(i);
            }
        }
    }
    public void setSpLaboratorio(int idLab){
        List<CtlEstablecimiento> labs = laboratorios;
        for (int i = 0; i <labs.size() ; i++) {
            if (labs.get(i).getId()== idLab){
                spLaboratorio.setSelection(i+1);
            }
        }
    }
    public void setSpPais(long idPais){
        List<CtlPais> listaPais = paises;
        for (int i = 0; i <listaPais.size() ; i++) {
            if (listaPais.get(i).getId()== idPais){
                spPais.setSelection(i+1);
            }
        }
    }
    private boolean validateData(){
        boolean valida = false;
        if (titGgNombre.getText().toString().trim().equals("")){
            titGgNombre.setError("Nombre Requerido");
            titGgNombre.requestFocus();

        }else if (titGgPrimerApellido.getText().toString().trim().equals("")){
            titGgPrimerApellido.setError("Apellido requerido");
            titGgPrimerApellido.requestFocus();
        }else if(titGgEdad.getText().toString().trim().equals("")){
            titGgEdad.setError("Edad requerido");
            titGgEdad.requestFocus();
        }else if(Integer.parseInt(titGgEdad.getText().toString().trim()) <18 && titGgResponsable.getText().toString().trim().equals("")){
            titGgResponsable.setError("Nombre de responsable requerido");
            titGgResponsable.requestFocus();

        }else if(spSexo.getSelectedItemPosition()==0){
            TextView errorText=(TextView)spSexo.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.BLUE);
            errorText.setText("Seleccione el sexo");
            errorText.requestFocus();
        }else if(spGgTipoDoc.getSelectedItemPosition()!=0 && spGgTipoDoc.getSelectedItemPosition()!=7 && titGgNumeroDoc.getText().toString().trim().equals("")){
            titGgNumeroDoc.setError("Ingrese un numero de documento");
            titGgNumeroDoc.requestFocus();
        }else if(fechaToma.getText().toString().trim().equals("")){
            fechaToma.setError("Fecha de toma requerida");
            fechaToma.setFocusable(true);
            fechaToma.requestFocus();
        }else if(fechaFiebre.getText().toString().trim().equals("")){
            fechaFiebre.setError("Fecha de inicio de fiebre requerida");
            fechaFiebre.requestFocus();
        }else if(tvGge6.getText().toString().trim().equals("")){
            tvGge6.setError("Correlativo requerido");
            tvGge6.requestFocus();
        }else if(tvGgDescripcion.getText().toString().trim().equals("")){
            tvGgDescripcion.setError("Descripción requerida");
            tvGgDescripcion.requestFocus();

        }else if(spLaboratorio.getSelectedItemPosition()==0){
            TextView errorText=(TextView)spLaboratorio.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.BLUE);
            errorText.setText("Seleccione el laboraorio donde se llevara la muestra");
        }else if(swExtrajero.isChecked() && spPais.getSelectedItemPosition()==0){
            TextView errorText=(TextView)spPais.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.BLUE);
            errorText.setText("Seleccione pais de procedencia");
        }else if (!swExtrajero.isChecked() && spCaserio.getSelectedItemPosition()==0){
            TextView errorText=(TextView)spCaserio.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.BLUE);
            errorText.setText("Seleccione un caserio");

        }else if(!validatefechaToma()){
            fechaToma.requestFocus();

        }else if(validateE6()>0){
            tvGge6.requestFocus();
            tvGge6.setError("E6 ya existe");
        }else if(!fechaNac.getText().toString().trim().equals("")){
            valida=validatefechaNacimiento();

        }else {
            //Toast.makeText(getActivity(),"Complete los campos indicados",Toast.LENGTH_SHORT).show();
            valida= true;

        }
        return valida;
    }
    public  boolean validatefechaNacimiento(){
        boolean valida = false;
        String fechaNacimiento = fechaNac.getText().toString().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDateNac = new Date();
        Date convertedDateHoy = new Date();
        Date fechaHoy = new Date();
        Date currentTime = Calendar.getInstance().getTime();
        String fecha = dateFormat.format(currentTime);
        try {
            convertedDateNac = dateFormat.parse(fechaNacimiento);
            convertedDateHoy = dateFormat.parse(fecha);
            if (convertedDateHoy.after(convertedDateNac)|| convertedDateNac.equals(convertedDateHoy)){
                valida = true;
            }else{
                Toast.makeText(getApplicationContext(),"La fecha de nacimiento no puede ser mayor a este dia",Toast.LENGTH_SHORT).show();
                valida=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  valida;
    }
    public int getSemana() {
        int semana = 0;
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            String sqlQUERY = "SELECT semana FROM ctl_semana_epi where '" + strDate + "' BETWEEN fecha_inicio " +
                    "and fecha_fin";
            Cursor c = daoSession.getDatabase().rawQuery(sqlQUERY, null);
            if (c.moveToFirst()) {
                do {
                    semana = c.getInt(0);

                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return semana;
    }
    public int validateE6(){
        String e6 = tvGge6.getText().toString().trim();
        String clave = String.valueOf(idClave);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateAnio = new SimpleDateFormat("yyyy");
        String anio = dateAnio.format(currentTime);
        PlGotaGruesaDao gotaDao = daoSession.getPlGotaGruesaDao();
        List<PlGotaGruesa> gota = gotaDao.queryBuilder().where(PlGotaGruesaDao.Properties.Anio.eq(anio))
                .where(PlGotaGruesaDao.Properties.IdE6.eq(e6)).where(PlGotaGruesaDao.Properties.IdClave.eq(clave)).list();
        return gota.size();
    }
    private int getIdLabLee(){
        int idLaboratorio=0;
        int selected = spLaboratorio.getSelectedItemPosition();
        if (selected != 0 && selected != -1) {
            idLaboratorio = (int) (long) laboratorios.get(selected - 1).getId();
        }
        return idLaboratorio;
    }
    public  boolean validatefechaToma(){
        boolean valida = false;
        String fechaTomada = fechaToma.getText().toString().trim();
        String fechaInicioFiebre = fechaFiebre.getText().toString().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDateToma = new Date();
        Date convertedFechaFiebre = new Date();
        try {
            convertedDateToma = dateFormat.parse(fechaTomada);
            convertedFechaFiebre = dateFormat.parse(fechaInicioFiebre);
            if (convertedFechaFiebre.before(convertedDateToma)|| convertedDateToma.equals(convertedFechaFiebre)){
                valida = true;
            }else{
                Toast.makeText(getApplicationContext(),"La fecha de fiebre no puede ser mayor a la feha de toma",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  valida;
    }
    private void saveData(){
        try{
            PlGotaGruesaDao gotaGruesaDao = daoSession.getPlGotaGruesaDao();
            PlGotaGruesa gota = new PlGotaGruesa();
            int semanaActual = getSemana();
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateAnio = new SimpleDateFormat("yyyy");
            String fecha = dateFormat.format(currentTime);
            String anio = dateAnio.format(currentTime);
            String primerNombre = titGgNombre.getText().toString().trim();
            String primerApellido = titGgPrimerApellido.getText().toString().trim();
            String edad = titGgEdad.getText().toString().trim();
            int idSexo = spSexo.getSelectedItemPosition();
            long idPais =0;
            long idCaserio=0;
            long idClaveColvol = idClave;
            if (swExtrajero.isChecked()){
                idPais= paises.get(spPais.getSelectedItemPosition()-1).getId();
                gota.setExtranjero(1);
                gota.setIdCaserio(1);
            }else{
                idPais = 68;
                idCaserio= caserios.get(spCaserio.getSelectedItemPosition()-1).getId();
                gota.setExtranjero(0);
            }
            String e6 = tvGge6.getText().toString().trim();
            int labLleera = getIdLabLee();
            gota.setIdLabLectura(labLleera);

            gota.setPrimerNombre(primerNombre);
            gota.setPrimerApellido(primerApellido);
            gota.setEdad(Integer.parseInt(edad));
            gota.setIdClave(idClaveColvol);
            gota.setIdSexo(idSexo);
            gota.setIdPais(idPais);
            gota.setIdResultado(3); // por el momento siempre sera sin resultado ya que solo son de colvol
            if (idCaserio>0){
                gota.setIdCaserio(idCaserio);
            }
            if (!titGgSegundoApellido.getText().toString().trim().equals("")){
                gota.setSegundoApellido(titGgSegundoApellido.getText().toString().trim());
            }
            if (!titGgSegundoNombre.getText().toString().trim().equals("")){
                gota.setSegundoNombre(titGgSegundoNombre.getText().toString().trim());
            }
            gota.setEstado_sync(0);
            gota.setFechaFiebre(fechaFiebreTxt);
            gota.setFechaToma(fechaTomaTxt);
            gota.setDireccion(tvGgDescripcion.getText().toString().trim());
            gota.setFechaHoraReg(fecha);
            gota.setEsPc(1);
            gota.setTipoProcedencia(1);
            gota.setIdSibasi(idSibasi);
            gota.setIdUsuarioReg(idSibasi);
            gota.setIdTablet(idTablet);
            gota.setIdE6(Integer.parseInt(e6));
            gota.setAnio(Integer.parseInt(anio));
            if (!titGgNumeroDoc.getText().toString().trim().equals("")){
                if (spGgTipoDoc.getSelectedItemPosition()!=0 || spGgTipoDoc.getSelectedItemPosition()!=7){
                    gota.setIdDocIdePaciente(String.valueOf(spGgTipoDoc.getSelectedItemPosition()));
                    gota.setNumeroDocIdePaciente(titGgNumeroDoc.getText().toString().trim());
                }else{gota.setIdDocIdePaciente(String.valueOf(spGgTipoDoc.getSelectedItemPosition()));}
            }
            if (!fechaNac.getText().toString().trim().equals("")){
                gota.setFechaNacimiento(fechaNac.getText().toString().trim());
            }
            if (!titGgResponsable.getText().toString().trim().equals("")){
                gota.setResponsable(titGgResponsable.getText().toString().trim());
            }
            gota.setIdSemanaEpidemiologica(semanaActual);
            gota.setIdVectores("N/A");
            gotaGruesaDao.save(gota);
            Toast.makeText(getApplicationContext(),"Se guardo con exito",Toast.LENGTH_SHORT).show();



        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error al guardar",Toast.LENGTH_SHORT).show();
        }

    }
    public static String formatDate (String date, String initDateFormat, String endDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
    }
}
