package com.minsal.dtic.sinavec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Dialogo usado para el ingreso de los datos de un nuevo estudiante.
 * Se puede agregar un estudiante con nombre, apellido paterno y apellido materno.
 */
public class AddStudentDialogFragment extends DialogFragment {

    private NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, Student student);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_agregar_alumno, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle("Selecciona un estudiante");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               /* String name = ((EditText) v.findViewById(R.id.dialog_student_name))
                        .getText().toString().toUpperCase();
                String firstLastname = ((EditText) v.findViewById(R.id.dialog_first_lastname))
                        .getText().toString().toUpperCase();
                String secondLastname = ((EditText) v.findViewById(R.id.dialog_second_lastname))
                        .getText().toString().toUpperCase();
                Student student = new Student(name, firstLastname, secondLastname);
               // mListener.onDialogPositiveClick(AddStudentDialogFragment.this , student);*/
                Toast.makeText(getActivity().getApplicationContext(),"Agregara",Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity().getApplicationContext(),"Agregara",Toast.LENGTH_LONG).show();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
