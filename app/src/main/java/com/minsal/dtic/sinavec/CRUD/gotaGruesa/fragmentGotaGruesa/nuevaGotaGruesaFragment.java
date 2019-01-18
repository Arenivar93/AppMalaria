package com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.ColvolCalve;
import com.minsal.dtic.sinavec.EntityDAO.ColvolCalveDao;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Dialogo usado para el ingreso de los datos de gota gruesa
 */
public class nuevaGotaGruesaFragment extends DialogFragment {

    private procedenciaDialogListener mListener;
    private DaoSession daoSession;
    private CardView colvolCard;
    private Spinner spEdad,spSexo, spLaboratorio,spDepartamento,
            spMunicipio,spCanton,spCaserio,spPais;

    ArrayAdapter<String> adapterEdad;
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
    Button btnGuardar, btnCancelar;
    String fechaTomaTxt,fechaFiebreTxt;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<CtlEstablecimiento> laboratorios;;



    public interface procedenciaDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_nueva_gota_gruesa, null);
        salir=0;

        daoSession=((MyMalaria) getActivity().getApplication()).getDaoSession();
        utilidades=new Utilidades(daoSession);
        //declaracion de objetos del layout
        fechaToma=(EditText)v.findViewById(R.id.tvGgFechaToma);
        fechaFiebre= (EditText)v.findViewById(R.id.tvGgfechaInicioFiebre);
        tvGgDescripcion= (EditText)v.findViewById(R.id.tvGgDescripcion);
        colvolCard=(CardView) v.findViewById(R.id.cardColvol);
        spEdad = (Spinner)v.findViewById(R.id.spEdad);
        spSexo = (Spinner)v.findViewById(R.id.spGgSexo);
        fechaNac = (TextInputEditText)v.findViewById(R.id.titGgFechaNac);
        spLaboratorio = (Spinner)v.findViewById(R.id.spGgLabLee);
        spDepartamento = (Spinner)v.findViewById(R.id.spGgDepartamento);
        spMunicipio = (Spinner)v.findViewById(R.id.spGgMunicipio);
        spCanton = (Spinner)v.findViewById(R.id.spGgCanton);
        spCaserio = (Spinner)v.findViewById(R.id.spGgCaserio);
        spPais=(Spinner)v.findViewById(R.id.spGgPais);
        linearLocal=(LinearLayout)v.findViewById(R.id.linearLocal);
        linearExtranjero=(LinearLayout)v.findViewById(R.id.linearExtranjero);
        linearLocal=(LinearLayout)v.findViewById(R.id.linearLocal);
        swExtrajero = (Switch)v.findViewById(R.id.swGgExtranjero);
        fechaToma.setFocusable(false);
        fechaToma.setClickable(true);
        fechaFiebre.setFocusable(false);
        fechaFiebre.setClickable(true);
        fechaNac.setFocusable(false);
        fechaNac.setClickable(true);
        titGgNombre = (TextInputEditText)v.findViewById(R.id.titGgNombre);
        titGgSegundoNombre = (TextInputEditText)v.findViewById(R.id.titGgSegundoNombre);
        titGgPrimerApellido = (TextInputEditText)v.findViewById(R.id.titGgPrimerApellido);
        titGgSegundoApellido = (TextInputEditText)v.findViewById(R.id.titGgSegundoApellido);
        titGgEdad = (TextInputEditText)v.findViewById(R.id.titGgEdad);
        tvGge6 = (EditText)v.findViewById(R.id.tvGge6);
        btnGuardar = (Button)v.findViewById(R.id.btnGgGuardar);
        btnCancelar = (Button)v.findViewById(R.id.btnGgCancelar);
        spGgTipoDoc =(Spinner)v.findViewById(R.id.spGgTipoDoc);
        titGgNumeroDoc =(TextInputEditText)v.findViewById(R.id.titGgNumeroDoc);
        titGgResponsable =(TextInputEditText)v.findViewById(R.id.titGgResponsable);


        idSibasi=getArguments().getLong("idSibasi");
        idTablet=getArguments().getLong("idTablet");
        idUsuario=getArguments().getLong("idUsuario");
        idClave =getArguments().getLong("idClave");
        nombreColvol =getArguments().getString("nombreColvol");
        laboratorios=utilidades.obtenerLaboratorios(idSibasi);
        listaLaboratorios=utilidades.obtenerListaEstablecimientoClave(laboratorios);
        ArrayAdapter adapterTipoDoc = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item, MetodosGlobales.getTipoDocumento());
        spGgTipoDoc.setAdapter(adapterTipoDoc);
        spGgTipoDoc.setSelection(7);

        adapterLaboratorio=new ArrayAdapter
                (getActivity(),android.R.layout.simple_list_item_1,listaLaboratorios);
        adapterLaboratorio.notifyDataSetChanged();
        spLaboratorio.setAdapter(adapterLaboratorio);

        adapterEdad=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,MetodosGlobales.getTipoEdad());
        spEdad.setAdapter(adapterEdad);

        adapterSexo=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,MetodosGlobales.getSexo());
        spSexo.setAdapter(adapterSexo);

        //lleno el spiner de pais
        paises=utilidades.loadspinnerPais();
        listaPaises=utilidades.obtenerListaPais(paises);
        adapterPaises=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaPaises);
        spPais.setAdapter(adapterPaises);

        //lleno el espiner de departamento
        departamentos=utilidades.loadspinnerDepartamento();
        listaDepartamentos=utilidades.obtenerListaDepartamento(departamentos);
        adapterDepartamento=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaDepartamentos);
        spDepartamento.setAdapter(adapterDepartamento);

        //inicialiso el spiner de municipio
        listaMunicipios.add("Seleccione");
        adapterMunicipio=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaMunicipios);
        spMunicipio.setAdapter(adapterMunicipio);

        //inicialiso el spiner de canton
        listaCantones.add("Seleccione");
        adapterCanton=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaCantones);
        spCanton.setAdapter(adapterCanton);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean valida = validateData();
                if (valida){
                    saveData();
                }else{
                    Toast.makeText(getActivity(),"Existen errores en el formulario",Toast.LENGTH_SHORT).show();
                }

            }
        });


        //inicialiso el spiner de municipio
        listaCaserios.add("Seleccione");
        adapterCaserio=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaCaserios);
        spCaserio.setAdapter(adapterCaserio);
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


        //definire los eventos para los cambios en mis spinner
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
                    spMunicipio.setSelection(0);
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
                    spCanton.setSelection(0);
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
                    spCaserio.setSelection(0);
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

        linearExtranjero.setVisibility(LinearLayout.GONE);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Gota Gruesa tomada por: "+nombreColvol);

        final AlertDialog ventana=builder.create();
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            private static final int INTERVALO = 2000; //2 segundos para salir
            private long tiempoPrimerClick;
            @Override
            public void onClick(View view) {
                if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
                    ventana.dismiss();
                }else {
                    Toast.makeText(getActivity(), "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
                }
                tiempoPrimerClick = System.currentTimeMillis();

            }
        });



        fechaToma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c=Calendar.getInstance();
                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                anio=c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        fechaTomaTxt = sdf.format(c.getTime());
                        fechaToma.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });
        fechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c2=Calendar.getInstance();
                dia=c2.get(Calendar.DAY_OF_MONTH);
                mes=c2.get(Calendar.MONTH);
                anio=c2.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String edadConcatenada = calcularEdad(year,monthOfYear+1,dayOfMonth);
                        String parts[] =edadConcatenada.split("/");
                        String anios = parts[0];
                        String meses = parts[1];
                        String dias = parts[2];
                        if (!anios.equals("0")){
                            titGgEdad.setText(anios);
                            spEdad.setSelection(0);
                        }else if(!meses.equals("0")){
                            titGgEdad.setText(meses);
                            spEdad.setSelection(1);
                        }else{
                            titGgEdad.setText(dias);
                            spEdad.setSelection(2);
                        }


                        fechaNac.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },anio,mes,dia);
                datePickerDialog.show();


            }
        });

        fechaNac.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                fechaNac.setText("");
                titGgEdad.setText("");
                return false;
            }
        });

        fechaFiebre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c3=Calendar.getInstance();
                dia=c3.get(Calendar.DAY_OF_MONTH);
                mes=c3.get(Calendar.MONTH);
                anio=c3.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        fechaFiebre.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        fechaFiebreTxt = sdf.format(c3.getTime());
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });


        return ventana;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // La actividad desde donde se levantara la modal, debe de implementar la intefaz junto
        // con sus metodos.
        try {
            // Instantiate the colvolDialogListener so we can send events to the host
            mListener = (procedenciaDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement procedenciaDialogListener");
        }
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(android.content.DialogInterface dialog, int keyCode,android.view.KeyEvent event) {

                if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK))
                {
                    btnCancelar.setFocusable(true);
                    btnCancelar.setFocusableInTouchMode(true);
                    btnCancelar.requestFocus();

                    return true; // pretend we've processed it
                }
                else
                    return false; // pass on to be processed as normal
            }
        });


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
            Toast.makeText(getActivity(),"Se guardo con exito",Toast.LENGTH_SHORT).show();
            getDialog().dismiss();


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(),"Error al guardar",Toast.LENGTH_SHORT).show();
        }

    }
    private long claveColvol(long idColvol){
        long idClave=0;
        ColvolCalveDao claveDao= daoSession.getColvolCalveDao();
        List<ColvolCalve> colvol = claveDao.queryBuilder().where(ColvolCalveDao.Properties.IdColvol.eq(idColvol)).list();
        idClave = colvol.get(0).getId();
        return idClave;
    }

    public static String calcularEdad(int yearOfBirth, int monthOfBirth, int dayOfBirth) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            LocalDate birthdate = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
            Period p = Period.between(birthdate, today);
            String anios = String.valueOf(p.getYears());
            String meses = String.valueOf(p.getMonths());
            String dias = String.valueOf(p.getDays());
            return anios+"/"+meses+"/"+dias;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            Calendar c2 = new GregorianCalendar(yearOfBirth, monthOfBirth, dayOfBirth);
            Calendar c1 = new GregorianCalendar(year, month, day);
            long end = c2.getTimeInMillis();
            long start = c1.getTimeInMillis();
            long milliseconds = TimeUnit.MILLISECONDS.toMillis(Math.abs(end - start));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(milliseconds);
            int mYear = c.get(Calendar.YEAR) - 1970;
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH) - 1;
            return mYear+"/"+mMonth+"/"+mDay;
        }
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
                Toast.makeText(getActivity(),"La fecha de fiebre no puede ser mayor a la feha de toma",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  valida;
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
                Toast.makeText(getActivity(),"La fecha de nacimiento no puede ser mayor a este dia",Toast.LENGTH_SHORT).show();
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

}
