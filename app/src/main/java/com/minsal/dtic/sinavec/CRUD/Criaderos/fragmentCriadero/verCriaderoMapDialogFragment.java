package com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero;

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
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;

/**
 * Dialogo usado para el ingreso de los datos de un criadero
 */
public class verCriaderoMapDialogFragment extends DialogFragment {

    private criaderoDialogListener mListener;
    ArrayAdapter<String> adapterTipoCriadero;
    ArrayList<String> tipoCriadero=new ArrayList<String>();
    private int tipoCriaderoVal=0;
    private EditText nombre,ancho,largo,descripcion,tipo;
    private long idCriadero;
    private DaoSession daoSession;
    private CtlPlCriadero criadero;
    private TextView municipio,canton,caserio;

    public interface criaderoDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String nombre, String descripcion, int tipo, float ancho, float largo);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_ver_criaderomap, null);

        daoSession=((MyMalaria) getActivity().getApplication()).getDaoSession();

        nombre=(EditText)v.findViewById(R.id.nombreCriadero);
        ancho=((EditText)v.findViewById(R.id.anchoCriadero));
        largo=((EditText)v.findViewById(R.id.largoCriadero));
        descripcion=((EditText)v.findViewById(R.id.descripcionCriadero));
        tipo=((EditText)v.findViewById(R.id.tipoCriadero));
        municipio=(TextView)v.findViewById(R.id.idMunicipio);
        canton=(TextView)v.findViewById(R.id.idCanton);
        caserio=(TextView)v.findViewById(R.id.idCaserio);


        idCriadero=getArguments().getLong("id");
        criadero=daoSession.getCtlPlCriaderoDao().loadByRowId(idCriadero);
        nombre.setText(criadero.getNombre());
        ancho.setText(String.valueOf((int)criadero.getAnchoCriadero()));
        largo.setText(String.valueOf((int)criadero.getLongitudCriadero()));
        descripcion.setText(String.valueOf(criadero.getDescripcion()));
        if(criadero.getIdTipoCriadero()==1){
            tipo.setText("Permanente");
        }else{
            tipo.setText("Temporal");
        }
        municipio.setText(criadero.getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre());
        canton.setText(criadero.getCtlCaserio().getCtlCanton().getNombre());
        caserio.setText(criadero.getCtlCaserio().getNombre());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Información de Criadero:");
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
            mListener = (criaderoDialogListener) activity;
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
