package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.minsal.dtic.sinavec.R;


public class NuevaPesquisaFragment extends android.app.DialogFragment {
    TextInputEditText titAnopheles12,titAnopheles34,titCulicino12,titCulicino34;
    TextInputEditText titPupa, titCucharonada, titLargo,titAncho;

    private OnFragmentInteractionListener mListener;
    public interface OnFragmentInteractionListener {
        void OnDialogPositiveClick(DialogFragment dialog, int anopheles12, int anopheles34,
                                   int culicino12, int culicino34, int pupa, int cucharonada, int largo, int ancho);
        void onDialogNegativeClick(DialogFragment dialog);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // return super.onCreateDialog(savedInstanceState);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_nueva_pesquisa, null);
        titAnopheles12  = (TextInputEditText)v.findViewById(R.id.titAnopheles12);
        titAnopheles34  = (TextInputEditText)v.findViewById(R.id.titAnopheles34);
        titCulicino12   = (TextInputEditText)v.findViewById(R.id.titCulicino12);
        titCulicino34   = (TextInputEditText)v.findViewById(R.id.titCulicino34);
        titPupa         = (TextInputEditText)v.findViewById(R.id.titPupas);
        titCucharonada  = (TextInputEditText)v.findViewById(R.id.titCucharonada);
        titLargo        = (TextInputEditText)v.findViewById(R.id.titLargo);
        titAncho        = (TextInputEditText)v.findViewById(R.id.titAncho);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Complete los siguientes campos:");
        builder.setPositiveButton("Guardar",null);
        builder.setNegativeButton("Cancelar",null);
        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnCrear=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       boolean dataValida= validateData();
                       if (dataValida){
                           Toast.makeText(getActivity(),"vas a guardae",Toast.LENGTH_SHORT).show();
                           mListener.OnDialogPositiveClick(NuevaPesquisaFragment.this,2,3,4,5,6,7,7,7);
                           dialog.dismiss();
                       }

                    }
                });

            }
        });

        return dialog;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;

        }catch (Exception e){
            throw new ClassCastException(context.toString()
                    + " must implement criaderoDialogListener");
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





    public boolean validateData(){
        boolean validate= false;
        if (TextUtils.isEmpty(titAnopheles12.getText().toString().trim())){
            titAnopheles12.setError("Cantidad de Anopheles estadío I o II requerida");
            titAnopheles12.requestFocus();

        }else if (TextUtils.isEmpty(titAnopheles34.getText().toString().trim())){
            titAnopheles34.setError("Cantidad de Anopheles estadío III o IV requerida");
            titAnopheles34.requestFocus();

        }else if (TextUtils.isEmpty(titCulicino12.getText().toString().trim())){
            titCulicino12.setError("Cantidad de Anopheles estadío vI o II requerida");
            titCulicino12.requestFocus();

        }else if (TextUtils.isEmpty(titCulicino34.getText().toString().trim())){
            titCulicino34.setError("Cantidad de Culicinos estadío III o IV requerida");
            titCulicino34.requestFocus();
        }else if (TextUtils.isEmpty(titPupa.getText().toString().trim())) {
            titPupa.setError("Cantidad de pupas requerida");
            titPupa.requestFocus();
        }else if (TextUtils.isEmpty(titCucharonada.getText().toString().trim())){
            titCucharonada.setError("Cantidad de cucharonadas requerida");
            titCucharonada.requestFocus();
        }else if (TextUtils.isEmpty(titLargo.getText().toString().trim())){
            titLargo.setError("Largo de criadero requerido");
            titLargo.requestFocus();
        }else if (TextUtils.isEmpty(titAncho.getText().toString().trim())){
            titAncho.setError("Ancho de criadero requerido");
            titAncho.requestFocus();
        }else{
            validate= true;
        }
        return validate;
    }
}
