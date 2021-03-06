package com.minsal.dtic.sinavec.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;

public class AdapterSeguimiento extends BaseAdapter {
    Context context;
   // ArrayList<String> seguimientos;
    DaoSession daoSession;
    ArrayList<String> listaseguimientos;
    ArrayList<String> seguimientos;
    PlCapturaAnophelesDao capDao;

    public AdapterSeguimiento(Context context, ArrayList<String> seguimientos) {
        this.context = context;
        this.seguimientos = seguimientos;

    }


    @Override
    public int getCount() {
        return seguimientos.size();
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
        String pa ="";

        TextView tv1, tv2, tv3,tv4,tv5,tv6,tv7,tvId;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.list_custom_adapter, viewGroup, false);

        tv1 = (TextView) v.findViewById(R.id.tvAda1);
        tv2 = (TextView) v.findViewById(R.id.tvAda2);
        tv3 = (TextView) v.findViewById(R.id.tvAda3);
        tv4 = (TextView) v.findViewById(R.id.tvAda4);
        tv5 = (TextView) v.findViewById(R.id.tvAda5);
        tv6 = (TextView) v.findViewById(R.id.tvAda6);
        tv7= (TextView) v.findViewById(R.id.tvAda7);
        tvId= (TextView) v.findViewById(R.id.tvAdaId);
        String str = seguimientos.get(position);
        String[] tvs = str.split("/");
        tv1.setText(tvs[2]);
        tv2.setText(tvs[1]);
        tv3.setText(tvs[3]);
        tv4.setText(tvs[4]);
        tv4.setGravity(Gravity.CENTER);
        tv5.setText(tvs[5]);
        tv5.setGravity(Gravity.CENTER);
        tv6.setText(tvs[6]);
        tv6.setGravity(Gravity.CENTER);
        tv7.setText(tvs[7]);
        tvId.setText(tvs[0]);
        return v;
    }

    public ArrayList<String> getData() {
        return seguimientos;
    }



}