package com.minsal.dtic.sinavec.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.prueba;


public class MainFragment extends Fragment{

    private OnFragmentInteractionListener mListener;
    private CardView gotaCard,pesquisaCard,capturaCard,botiquinCard;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista=inflater.inflate(R.layout.fragment_main, container, false);

        gotaCard=(CardView) vista.findViewById(R.id.idGota);
        pesquisaCard=(CardView) vista.findViewById(R.id.idPesquisa);
        capturaCard=(CardView) vista.findViewById(R.id.idCaptura);
        botiquinCard=(CardView) vista.findViewById(R.id.idBotiquin);


        gotaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),prueba.class);
                startActivity(intent);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            }
        });
        pesquisaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),prueba.class);
                startActivity(intent);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            }
        });
        capturaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),prueba.class);
                startActivity(intent);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            }
        });
        botiquinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),prueba.class);
                startActivity(intent);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
/*
    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()){
            case R.id.idGota:
                intent=new Intent(getActivity(),prueba.class);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            break;
            case R.id.idPesquisa:
                intent=new Intent(getActivity(),prueba.class);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            break;
            case R.id.idCaptura:
                intent=new Intent(getActivity(),prueba.class);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            break;
            case R.id.idBotiquin:
                intent=new Intent(getActivity(),prueba.class);
                Toast.makeText(getContext(),"Redirige",Toast.LENGTH_SHORT).show();
            break;

        }
    }*/
}
