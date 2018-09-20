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

public class AdapterSemanaPesquisa extends BaseAdapter {
    Context context;
   // ArrayList<String> capturas;
    DaoSession daoSession;
    ArrayList<String> listaCapturas;
    ArrayList<String> capturas;
    PlCapturaAnophelesDao capDao;

    public AdapterSemanaPesquisa(Context context, ArrayList<String> capturas) {
        this.context = context;
        this.capturas = capturas;

    }


    @Override
    public int getCount() {
        return capturas.size();
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

        TextView tv1, tv2, tv3,tv4,tv5,tv6,tv7;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.list_custom_capturas, viewGroup, false);

        tv1 = (TextView) v.findViewById(R.id.tvCap1);
        tv2 = (TextView) v.findViewById(R.id.tvCap2);
        tv3 = (TextView) v.findViewById(R.id.tvCap3);
        tv4 = (TextView) v.findViewById(R.id.tvCap4);
        tv5 = (TextView) v.findViewById(R.id.tvCap5);
        tv6 = (TextView) v.findViewById(R.id.tvCap6);
        tv7= (TextView) v.findViewById(R.id.tvCapt7);
        String str = capturas.get(position);
        String[] tvs = str.split("-");
        tv1.setText(tvs[0]);
        tv2.setText(tvs[1]);
        tv3.setText(tvs[2]);
        tv4.setText(tvs[3]);
        tv4.setGravity(Gravity.CENTER_HORIZONTAL);
        tv5.setText(tvs[4]);
        tv5.setGravity(Gravity.CENTER_HORIZONTAL);
        tv6.setText(tvs[5]);
        tv6.setGravity(Gravity.CENTER_HORIZONTAL);
        tv7.setText(tvs[6]);
        return v;
    }

    public ArrayList<String> getData() {
        return capturas;
    }



}