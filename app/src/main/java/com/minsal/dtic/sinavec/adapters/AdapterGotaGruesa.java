package com.minsal.dtic.sinavec.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;

public class AdapterGotaGruesa extends BaseAdapter {
    Context context;
    ArrayList<String> lista;

    public AdapterGotaGruesa(Context context, ArrayList<String> lista) {
        this.context = context;
        this.lista = lista;

    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        TextView tv1, tv2, tv3,tv4,tv5,tv6,tv7;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.list_gota_adapter, viewGroup, false);
        try {
            tv1 = (TextView) v.findViewById(R.id.tvgg1);//año
            tv2 = (TextView) v.findViewById(R.id.tvgg2);//semana
            tv3 = (TextView) v.findViewById(R.id.tvgg3);//total
            tv4 = (TextView) v.findViewById(R.id.tvgg4);//negativa
            tv5 = (TextView) v.findViewById(R.id.tvgg5);//positiva
            tv6 = (TextView) v.findViewById(R.id.tvgg6);//sin resultado
            String str = lista.get(position);
            String[] tvs = str.split("-");
            tv1.setText(tvs[0]);
            tv2.setText(tvs[1]);
            tv3.setText(tvs[2]);
            tv4.setText(tvs[4]);
            tv5.setText(tvs[5]);
            tv6.setText(tvs[3]);

        }catch (Exception e ){
            e.printStackTrace();
            Toast.makeText(context,"Error al obetener los datos",Toast.LENGTH_SHORT).show();
        }


        return v;
    }

    public ArrayList<String> getData() {
        return lista;
    }



}