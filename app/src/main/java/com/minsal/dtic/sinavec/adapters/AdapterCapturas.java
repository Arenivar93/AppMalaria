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

        TextView tv1, tv2, tv3;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.list_custom_capturas, viewGroup, false);

        tv1 = (TextView) v.findViewById(R.id.tvCap1);
        tv2 = (TextView) v.findViewById(R.id.tvCap2);
        tv3 = (TextView) v.findViewById(R.id.tvCap3);

        for (int i = 0; i <capturas.size() ; i++) {
            String s = capturas.get(i);
            tv1.setText(s);

            //tv1.setText(capturas.get(i));
        }


        return v;
    }
}
