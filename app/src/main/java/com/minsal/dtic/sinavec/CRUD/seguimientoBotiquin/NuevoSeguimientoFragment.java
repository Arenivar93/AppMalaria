package com.minsal.dtic.sinavec.CRUD.seguimientoBotiquin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria.NuevaPesquisaFragment;
import com.minsal.dtic.sinavec.R;

/**
 * Created by desarrollo on 09-21-18.
 */

public class NuevoSeguimientoFragment  extends android.app.DialogFragment {
    private OnFragmentInteractionListener mListener;

    static NuevoSeguimientoFragment newInstance(long id) {
        NuevoSeguimientoFragment f = new NuevoSeguimientoFragment();
        return f;
    }
    public interface OnFragmentInteractionListener {
        void OnDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_fragment_botiquin, null);
        RadioButton rdbSupervisado = v.findViewById(R.id.rdbSupervisado);
        RadioButton rdbVisitado = v.findViewById(R.id.rdbVisitado);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Seguimiento Botiquin: ")
                .setPositiveButton("Guardar",null)
                .setNegativeButton("Cancelar",null)
                .setCancelable(false);

        final AlertDialog dialog = builder.create();
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


}
