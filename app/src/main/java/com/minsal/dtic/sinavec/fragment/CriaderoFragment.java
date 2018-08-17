package com.minsal.dtic.sinavec.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
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

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvolDao;
import com.minsal.dtic.sinavec.MyMalaria;
import com.minsal.dtic.sinavec.R;

import HelperDB.DbHelpers;

public class CriaderoFragment extends Fragment {
    View vista;
    DaoSession daoSession;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_criadero, container, false);

        final TableLayout tableLayout=(TableLayout) vista.findViewById(R.id.table);

        //DbHelpers dataHelper=new DbHelpers(getContext());
        //daoSession = ((MyMalaria) getApplication()).getDaoSession();
        daoSession=((MyMalaria)getActivity().getApplication()).getDaoSession();

        TableRow rowHeader = new TableRow(getContext());
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"ID","NOMBRE","OPCIONES"};
        for(String c:headerText) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        //SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        daoSession.getDatabase().beginTransaction();

        PlColvolDao colvolDao = daoSession.getPlColvolDao();

        try
        {
            String selectQuery = "SELECT * FROM pl_colvol";
            Cursor cursor = daoSession.getDatabase().rawQuery(selectQuery,null);
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
                    button.setText("Map");
                    button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(),"Asignara Coordenadas id:"+id,Toast.LENGTH_LONG).show();
                        }
                    });
                    row.addView(button);
                    tableLayout.addView(row);
                }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        finally{
            daoSession.getDatabase().endTransaction();
            // End the transaction.
            //daoSession.getDatabase().close();
            // Close database
        }
        return vista;
    }
}
