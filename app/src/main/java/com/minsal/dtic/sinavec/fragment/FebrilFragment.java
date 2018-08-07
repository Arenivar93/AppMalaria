package com.minsal.dtic.sinavec.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minsal.dtic.sinavec.R;

public class FebrilFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getContext(),"mensage en fragment",Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_febril, container, false);
    }
}
