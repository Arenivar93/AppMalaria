package com.minsal.dtic.sinavec.CRUD.Colvol.fragmentColvol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;

/**
 * Dialogo usado para el ingreso de los datos de un criadero
 */
public class verColvolMapDialogFragment extends DialogFragment {

    private colvolDialogListener mListener;
    ArrayAdapter<String> adapterTipoCriadero;
    ArrayList<String> tipoCriadero=new ArrayList<String>();
    private int tipoCriaderoVal=0;
    private EditText nombre,telefono,habilitado,clave,circuito;
    private long idColvol;
    private DaoSession daoSession;
    private PlColvol colvol;
    private TextView municipio,canton,caserio;

    public interface colvolDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_ver_colvolmap, null);

        daoSession=((MyMalaria) getActivity().getApplication()).getDaoSession();

        nombre=(EditText)v.findViewById(R.id.nombreColvol);
        habilitado=((EditText)v.findViewById(R.id.habilitado));
        clave=((EditText)v.findViewById(R.id.clave));
        municipio=(TextView)v.findViewById(R.id.idMunicipio);
        canton=(TextView)v.findViewById(R.id.idCanton);
        caserio=(TextView)v.findViewById(R.id.idCaserio);


        idColvol=getArguments().getLong("id");
        colvol=daoSession.getPlColvolDao().loadByRowId(idColvol);
        nombre.setText(colvol.getNombre());
        if(colvol.getEstado()==1){
            habilitado.setText("SI");
        }else{
            habilitado.setText("NO");
        }

        clave.setText(colvol.getClave());
        municipio.setText(colvol.getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre());
        canton.setText(colvol.getCtlCaserio().getCtlCanton().getNombre());
        caserio.setText(colvol.getCtlCaserio().getNombre());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Información de Colvol:");
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
        return ventana;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // La actividad desde donde se levantara la modal, debe de implementar la intefaz junto
        // con sus metodos.
        try {
            // Instantiate the colvolDialogListener so we can send events to the host
            mListener = (colvolDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement colvolDialogListener");
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
