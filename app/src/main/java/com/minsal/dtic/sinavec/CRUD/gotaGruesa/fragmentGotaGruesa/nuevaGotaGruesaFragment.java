package com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
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

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamento;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPais;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesa;
import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesaDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Dialogo usado para el ingreso de los datos de gota gruesa
 */
public class nuevaGotaGruesaFragment extends DialogFragment {

    private procedenciaDialogListener mListener;
    private DaoSession daoSession;
    private CardView colvolCard;
    private Spinner spEdad,spSexo, spLaboratorio,spDepartamento,spMunicipio,spCanton,spCaserio,spPais;
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
    TextInputEditText fechaNac,titGgNombre,titGgSegundoNombre,titGgPrimerApellido,titGgSegundoApellido,titGgEdad;
    private int dia,mes,anio;
    private long idSibasi;
    private long idTablet;
    private long idUsuario;
    Utilidades utilidades;
    private LinearLayout linearLocal,linearExtranjero;
    Switch swExtrajero;
    Button btnGuardar, btnCancelar;



    public interface procedenciaDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_nueva_gota_gruesa, null);

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



        //obtengo los datos que viene de mi clase
        listaLaboratorios=getArguments().getStringArrayList("listaEstClave");
        idSibasi=getArguments().getLong("idSibasi");
        idTablet=getArguments().getLong("idTablet");
        idUsuario=getArguments().getLong("idUsuario");

        adapterLaboratorio=new ArrayAdapter
                (getActivity(),android.R.layout.simple_list_item_1,listaLaboratorios);
        adapterLaboratorio.notifyDataSetChanged();
        spLaboratorio.setAdapter(adapterLaboratorio);

        //lleno el espiner de tipo edad
        tipoEdad.add("Años");
        tipoEdad.add("Meses");
        tipoEdad.add("Dias");
        adapterEdad=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,tipoEdad);
        spEdad.setAdapter(adapterEdad);

        //lleno el espiner de sexo
        sexo.add("Selecione");
        sexo.add("Masculino");
        sexo.add("Femenino");
        adapterSexo=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,sexo);
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

                if (!validateData()){

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
                  linearLocal.setVisibility(View.INVISIBLE);
              }else{
                  linearLocal.setVisibility(View.VISIBLE);
                  linearExtranjero.setVisibility(View.INVISIBLE);

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
        builder.setTitle("Ingrese la siguiente Informacion:");

        final AlertDialog ventana=builder.create();



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
                        fechaNac.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
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
        }else if(spSexo.getSelectedItemPosition()==0){
            TextView errorText=(TextView)spSexo.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.BLUE);
            errorText.setText("Seleccione el sexo");

        }else if(fechaToma.getText().toString().trim().equals("")){
            fechaToma.setError("Fecha de toma requerida");
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

        }else{
            valida= true;

        }
        return valida;
    }
    private void saveData(){
        String primerNombre = titGgNombre.getText().toString().trim();
        String primerApellido = titGgPrimerApellido.getText().toString().trim();
        String edad = titGgEdad.getText().toString().trim();
        int idSexo = spSexo.getSelectedItemPosition();
        long idPais =0;
        long idCaserio=0;
        if (swExtrajero.isChecked()){
            idPais= paises.get(spPais.getSelectedItemPosition()-1).getId();
        }else{
            idPais = 68;
            idCaserio= caserios.get(spCaserio.getSelectedItemPosition()-1).getId();
        }
        String e6 = tvGge6.getText().toString().trim();
        PlGotaGruesaDao gotaGruesaDao = daoSession.getPlGotaGruesaDao();
        PlGotaGruesa gota = new PlGotaGruesa();



    }
}
