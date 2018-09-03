package com.minsal.dtic.sinavec.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;
import java.util.List;
public class AdapterCapturas extends BaseAdapter {
    Context context;
    ArrayList<String> capturas;

    public AdapterCapturas(Context context, ArrayList<String> capturas) {
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

        TextView tv1, tv2, tv3,tv4,tv5,tv6;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.list_custom_capturas, viewGroup, false);

        tv1 = (TextView) v.findViewById(R.id.tvCap1);
        tv2 = (TextView) v.findViewById(R.id.tvCap2);
        tv3 = (TextView) v.findViewById(R.id.tvCap3);
        tv4 = (TextView) v.findViewById(R.id.tvCap4);
        tv5 = (TextView) v.findViewById(R.id.tvCap5);
        tv6 = (TextView) v.findViewById(R.id.tvCap6);
        String str = capturas.get(position);
        String[] tvs = str.split("-");
        tv1.setText(tvs[0]);
        tv2.setText(tvs[1]);
        tv3.setText(tvs[2]);
        tv4.setText(tvs[3]);
        tv5.setText(tvs[4]);
        tv6.setText(tvs[5]);
        return v;
    }
}