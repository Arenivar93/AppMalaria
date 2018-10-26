package com.minsal.dtic.sinavec.CRUD.gotaGruesa.fragmentGotaGruesa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.CRUD.gotaGruesa.activityGotaGruesa.nuevaGotaGruesaActivity;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;

/**
 * Dialogo usado para el ingreso de los datos de gota gruesa
 */
public class nuevaGotaGruesaFragment extends DialogFragment {

    private procedenciaDialogListener mListener;
    private DaoSession daoSession;
    private CardView colvolCard;
    private Spinner spEdad,spSexo;
    ArrayList<String> tipoEdad=new ArrayList<String>();
    ArrayAdapter<String> adapterEdad;
    ArrayList<String> sexo=new ArrayList<String>();
    ArrayAdapter<String> adapterSexo;

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
        //declaracion de objetos del layout
        colvolCard=(CardView) v.findViewById(R.id.cardColvol);
        spEdad = (Spinner)v.findViewById(R.id.spEdad);
        spSexo = (Spinner)v.findViewById(R.id.spSexo);

        tipoEdad.add("Años");
        tipoEdad.add("Meses");
        tipoEdad.add("Dias");
        adapterEdad=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,tipoEdad);
        spEdad.setAdapter(adapterEdad);
        sexo.add("Masculino");
        sexo.add("Femenino");
        adapterSexo=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,sexo);
        spSexo.setAdapter(adapterSexo);


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


        /*colvolCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),nuevaGotaGruesaActivity.class);
                startActivity(intent);
                //Toast.makeText(getActivity().getApplicationContext(),"Regresaste",Toast.LENGTH_SHORT).show();
            }
        });*/
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
