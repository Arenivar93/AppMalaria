package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

    private OnFragmentInteractionListener mListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // return super.onCreateDialog(savedInstanceState);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_nueva_pesquisa, null);
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
                        Toast.makeText(getActivity(),"vas a guardae",Toast.LENGTH_SHORT).show();
                       // mListener.OnDialogPositiveClick(NuevaPesquisaFragment.this,"dialog");
                        dialog.dismiss();
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
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }



    public interface OnFragmentInteractionListener {
        void OnDialogPositiveClick(android.app.DialogFragment dialog,String string);
        void onDialogNegativeClick(DialogFragment dialog);
    }
}
