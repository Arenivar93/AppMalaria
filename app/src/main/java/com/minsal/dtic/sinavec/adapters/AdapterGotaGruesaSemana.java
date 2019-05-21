package com.minsal.dtic.sinavec.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.PlGotaGruesa;
import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterGotaGruesaSemana extends BaseAdapter {
    Context context;
    List<PlGotaGruesa> lista;

    public AdapterGotaGruesaSemana(Context context, List<PlGotaGruesa> lista) {
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
            tv7 = (TextView) v.findViewById(R.id.tvgg7);//sin resultado
            String municipio ="Extranjero";
            String canton ="Extranjero";
            String caserio ="Extrajero";
            String nombre=  lista.get(position).getPrimerNombre();
            String apellido=  lista.get(position).getPrimerApellido();
            String fechaToma=  lista.get(position).getFechaToma();
            String edad= String.valueOf(lista.get(position).getEdad());
            if (lista.get(position).getIdCaserio()!=1){ //si caserio trae id 1 es porqu es extranjer
                municipio = lista.get(position).getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre();
                canton = lista.get(position).getCtlCaserio().getCtlCanton().getNombre();
                caserio = lista.get(position).getCtlCaserio().getNombre();
            }
            int estadoSync = lista.get(position).getEstado_sync();
            if(estadoSync==0){
                tv7.setText("Sync");
            }else if(estadoSync==3){
                tv7.setText("Error");
            }else{
                tv7.setText("Pendiente");
            }



            tv1.setText(String.format("%s %s", nombre, apellido));
            tv2.setText(edad);
            tv3.setText(fechaToma);
            tv4.setText(municipio);
            tv5.setText(canton);
            tv6.setText(caserio);

        }catch (Exception e ){
            e.printStackTrace();
            Toast.makeText(context,"Error al obetener los datos",Toast.LENGTH_SHORT).show();
        }


        return v;
    }





}