package com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;

/**
 * Dialogo usado para el ingreso de los datos de un criadero
 */
public class nuevoCriaderoDialogFragment extends DialogFragment {


    private criaderoDialogListener mListener;
    private Spinner spTipoCriadero;
    ArrayAdapter<String> adapterTipoCriadero;
    ArrayList<String> tipoCriadero=new ArrayList<String>();
    int tipoCriaderoVal=0;
    TextInputLayout errorNombre,errorDescripcion,errorAncho,errorLargo;
    TextInputEditText nombre,ancho,largo,descripcion;

    public interface criaderoDialogListener {
        void onDialogPositiveClick(DialogFragment dialog,String nombre,String descripcion,int tipo,float ancho,float largo);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_agregar_criadero, null);

        spTipoCriadero = (Spinner)v.findViewById(R.id.spTipoCriadero);
        errorNombre=(TextInputLayout)v.findViewById(R.id.textoNombre);
        errorDescripcion=(TextInputLayout)v.findViewById(R.id.textoDescripcion);
        errorAncho=(TextInputLayout)v.findViewById(R.id.textoAncho);
        errorLargo=(TextInputLayout)v.findViewById(R.id.textoLargo);


        nombre=(TextInputEditText)v.findViewById(R.id.nombreCriadero);
        ancho=((TextInputEditText)v.findViewById(R.id.anchoCriadero));
        largo=((TextInputEditText)v.findViewById(R.id.largoCriadero));
        descripcion=((TextInputEditText)v.findViewById(R.id.descripcionCriadero));

        tipoCriadero.add("Seleccione");
        tipoCriadero.add("Permanente");
        tipoCriadero.add("Temporal");
        adapterTipoCriadero=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,tipoCriadero);
        spTipoCriadero.setAdapter(adapterTipoCriadero);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Complete los siguientes campos:");
        builder.setPositiveButton("Guardar",null);
        builder.setNegativeButton("Cancelar",null);

        final AlertDialog ventana=builder.create();

        ventana.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnCrear=ventana.getButton(AlertDialog.BUTTON_POSITIVE);
                //btnCrear.setBackground(getActivity().getDrawable(R.mipmap.ic_save));
                btnCrear.setTextColor(getResources().getColor(R.color.colorButtonSiguiente));
                btnCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        errorNombre.setError("");
                        errorDescripcion.setError("");
                        errorAncho.setError("");
                        errorLargo.setError("");
                        TextView errorText=(TextView)spTipoCriadero.getSelectedView();
                        int error=0;

                        if(nombre.getText().toString().isEmpty()){
                            errorNombre.setError("Nombre requerido");
                            error=1;
                        }
                        if(descripcion.getText().toString().isEmpty()){
                            errorDescripcion.setError("Descripción requerida");
                            error=1;
                        }
                        if(spTipoCriadero.getSelectedItemPosition()==0){
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);
                            errorText.setText("Seleccione");
                            error=1;
                        }
                        if(ancho.getText().toString().isEmpty() || ancho.getText().toString().equals(".") || ancho.getText().toString().equals(".0") || ancho.getText().toString().equals("0") || ancho.getText().toString().equals("0.")){
                            errorAncho.setError("Ancho requerido");
                            error=1;
                        }
                        if(largo.getText().toString().isEmpty() || largo.getText().toString().equals(".") || largo.getText().toString().equals(".0") || largo.getText().toString().equals("0") || largo.getText().toString().equals("0.")){
                            errorLargo.setError("Largo requerido");
                            error=1;
                        }
                        if(error==0){
                            String nombreVal = nombre.getText().toString();
                            String descripcionVal = descripcion.getText().toString();
                            tipoCriaderoVal = spTipoCriadero.getSelectedItemPosition();
                            float anchoVal=Float.parseFloat(ancho.getText().toString());
                            float largoVal=Float.parseFloat(largo.getText().toString());
                           mListener.onDialogPositiveClick(nuevoCriaderoDialogFragment.this,nombreVal,descripcionVal,tipoCriaderoVal,anchoVal,largoVal);
                            ventana.dismiss();
                        }
                    }
                });
                Button btnCancelar=ventana.getButton(AlertDialog.BUTTON_NEGATIVE);
                btnCancelar.setTextColor(Color.RED);
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Operación Cancelada",Toast.LENGTH_LONG).show();
                        ventana.dismiss();
                    }
                });
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
            // Instantiate the criaderoDialogListener so we can send events to the host
            mListener = (criaderoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement criaderoDialogListener");
        }
    }

    @Override
    public void onResume() {
        /*ViewGroup.LayoutParams params=getDialog().getWindow().getAttributes();
        params.width= WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);*/
        super.onResume();
    }
}
