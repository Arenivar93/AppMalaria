package com.minsal.dtic.sinavec.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.minsal.dtic.sinavec.R;

public class CriaderoFragment extends Fragment {
    View vista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_criadero, container, false);

        final TableLayout table=(TableLayout) vista.findViewById(R.id.table);
        for (int i = 0; i < 5; i++) {
            // Creation row
            final TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView text = new TextView(getContext());

            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation  button
            final Button button = new Button(getContext());
            button.setText("Delete");
            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TableRow parent = (TableRow) v.getParent();
                    table.removeView(parent);
                }
            });

            tableRow.addView(text);
            tableRow.addView(button);

            table.addView(tableRow);
        }
        return vista;


    }
}
