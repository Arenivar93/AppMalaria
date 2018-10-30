package com.minsal.dtic.sinavec.CRUD.seguimientoBotiquin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria.NuevaPesquisaFragment;
import com.minsal.dtic.sinavec.R;

/**
 * Created by desarrollo on 09-21-18.
 */

public class NuevoSeguimientoFragment  extends android.app.DialogFragment {
    private OnFragmentInteractionListener mListener;
    String nombre,bandera,clave;
    long mNum;

    TextInputEditText titMuestras,titPersonas ;
    RadioButton rdbSupervisado,rdbVisitado;
    RadioGroup accion;


    static NuevoSeguimientoFragment newInstance(long id, String nombre,String bandera,String clave) {
        NuevoSeguimientoFragment f = new NuevoSeguimientoFragment();
        Bundle args = new Bundle();
        args.putLong("num", id);
        args.putString("nombre", nombre);
        args.putString("bandera",bandera);
        args.putString("clave",clave);
        f.setArguments(args);
        return f;
    }
    public interface OnFragmentInteractionListener {
        void OnDialogPositiveClick(DialogFragment dialog,int muestras, int personas, String accion, int riesgo,String bandera,long id);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mNum = (int) getArguments().getLong("num");
        nombre = getArguments().getString("nombre");
        bandera= getArguments().getString("bandera");
        clave = getArguments().getString("clave");
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v               = inflater.inflate(R.layout.dialog_fragment_botiquin, null);
        rdbSupervisado = v.findViewById(R.id.rdbSupervisado);
        rdbVisitado    = v.findViewById(R.id.rdbVisitado);
        accion         = v.findViewById(R.id.rdgAccion);
        final Switch      swRiesgo       = v.findViewById(R.id.swRiesgoSocial);
       titMuestras = v.findViewById(R.id.titMuestras);
       titPersonas =  v.findViewById(R.id.titPersonas);
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Seguimiento Botiquin:"+nombre+ " Clave: "+clave)
                .setPositiveButton("Guardar",null)
                .setNegativeButton("Cancelar",null)
                .setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnCrear=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnCrear.setTextColor(Color.GREEN);
                btnCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean validate = validateData();
                        if (validate){
                            String muestras = titMuestras.getText().toString().trim();
                            String personas = titPersonas.getText().toString().trim();
                            String accion = rdbSupervisado.isChecked()? "supervisar" : "visitar"; // si es supervision sera 1 sino sera 2
                            int riesgo = swRiesgo.isChecked()? 1 : 0;// si es switch esta marcado el riesgo sera uno sino sera 0
                            mListener.OnDialogPositiveClick(NuevoSeguimientoFragment.this,Integer.parseInt(muestras),Integer.parseInt(personas),accion,riesgo,bandera,mNum);
                            dialog.dismiss();
                        }

                    }
                });
                Button btnCancelar = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                btnCancelar.setTextColor(Color.RED);
            }
        });
        return dialog;
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;

        }catch (Exception e){
            throw new ClassCastException(context.toString()
                    + " must implement botiquinDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public  boolean  validateData(){
        boolean validate = false;
        if (TextUtils.isEmpty(titMuestras.getText().toString().trim())){
            titMuestras.setError("Numero de muestras requeridas");
            titMuestras.requestFocus();
        }else if (TextUtils.isEmpty(titPersonas.getText().toString().trim())) {
            titPersonas.setError("Numero de personas a las que se le divulgó requerido");
            titPersonas.requestFocus();
        }else  if(!rdbSupervisado.isChecked() && !rdbVisitado.isChecked()){
            rdbVisitado.setError("seleccione uno");
            rdbSupervisado.setError("seleccione uno");
        }else{
            validate = true;
        }
        return validate;
    }


}
