package com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamento;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlPais;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
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
    private EditText fecha;
    private TextInputLayout textFecha;
    private int dia,mes,anio;
    private long idSibasi;
    private long idTablet;
    private long idUsuario;
    Utilidades utilidades;
    private LinearLayout linearLocal,linearExtranjero;


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
        fecha=(EditText)v.findViewById(R.id.fecha);
        textFecha= (TextInputLayout)v.findViewById(R.id.textFecha);
        colvolCard=(CardView) v.findViewById(R.id.cardColvol);
        spEdad = (Spinner)v.findViewById(R.id.spEdad);
        spSexo = (Spinner)v.findViewById(R.id.spSexo);
        spLaboratorio = (Spinner)v.findViewById(R.id.laboratorio);
        spDepartamento = (Spinner)v.findViewById(R.id.departamento);
        spMunicipio = (Spinner)v.findViewById(R.id.municipio);
        spCanton = (Spinner)v.findViewById(R.id.canton);
        spCaserio = (Spinner)v.findViewById(R.id.caserio);
        spPais=(Spinner)v.findViewById(R.id.pais);
        linearLocal=(LinearLayout)v.findViewById(R.id.linearLocal);
        linearExtranjero=(LinearLayout)v.findViewById(R.id.linearExtranjero);

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

        //inicialiso el spiner de municipio
        listaCaserios.add("Seleccione");
        adapterCaserio=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaCaserios);
        spCaserio.setAdapter(adapterCaserio);

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
        builder.setTitle("Complete los siguientes información:");
        builder.setNegativeButton("Cerrar",null);

        final AlertDialog ventana=builder.create();

        ventana.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button btnCancelar=ventana.getButton(AlertDialog.BUTTON_NEGATIVE);
                btnCancelar.setTextColor(Color.RED);
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ventana.dismiss();
                    }
                });
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c=Calendar.getInstance();
                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                anio=c.get(Calendar.YEAR);

                int dia2=dia;
                int mes2=mes;
                int anio2=anio;

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        //textFecha.setError("");
                        //textFecha.setError("Error fecha");
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
        super.onResume();
    }
}
