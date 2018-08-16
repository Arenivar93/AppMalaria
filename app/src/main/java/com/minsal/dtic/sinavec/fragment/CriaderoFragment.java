package com.minsal.dtic.sinavec.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.MainActivity;
import com.minsal.dtic.sinavec.MapsActivity;
import com.minsal.dtic.sinavec.R;

import HelperDB.DbHelpers;

public class CriaderoFragment extends Fragment {
    View vista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_criadero, container, false);

        final TableLayout tableLayout=(TableLayout) vista.findViewById(R.id.table);

        DbHelpers dataHelper=new DbHelpers(getContext());

        TableRow rowHeader = new TableRow(getContext());
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        //String[] headerText={"ID","NOMBRE","OPCIONES"};
        /*for(String c:headerText) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }*/
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try
        {
            String selectQuery = "SELECT * FROM ctl_pl_criadero";
            Drawable d = getResources().getDrawable(R.drawable.ic_mapa);
            Cursor cursor = db.rawQuery(selectQuery,null);
                while (cursor.moveToNext()) {
                    // Read columns data
                    final int id= cursor.getInt(cursor.getColumnIndex("id"));
                    String nombre= cursor.getString(cursor.getColumnIndex("nombre"));

                    //Toast.makeText(getContext(),cursor.getCount()+"-"+id+"-"+nombre+"-"+telefono,Toast.LENGTH_LONG).show();

                    // dara rows
                    TableRow row = new TableRow(getContext());
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={id+"",nombre};

                    for(String text:colText) {
                        TextView tv = new TextView(getContext());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    // Creation  button
                    final Button button = new Button(getContext());
                    //button.setText("Map");
                    button.setBackgroundResource(R.drawable.ic_mapa);
                    button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(),"Asignara Coordenadas id:"+String.valueOf(id),Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getContext(), MapsActivity.class);
                            i.putExtra("id",String.valueOf(id));
                            startActivity(i);
                        }
                    });
                    row.addView(button);
                    tableLayout.addView(row);
                }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        finally{
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        return vista;
    }
}
