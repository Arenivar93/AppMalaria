package com.minsal.dtic.sinavec.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minsal.dtic.sinavec.AdapterDatos;
import com.minsal.dtic.sinavec.PersonajeVo;
import com.minsal.dtic.sinavec.R;

import java.util.ArrayList;

public class LenguajesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerPersonajes;
    ArrayList<PersonajeVo> listaPersonaje;


    public LenguajesFragment() {
        // Required empty public constructor
    }
    public static LenguajesFragment newInstance(String param1, String param2) {
        LenguajesFragment fragment = new LenguajesFragment();
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

        View vista=inflater.inflate(R.layout.fragment_lenguajes, container, false);
        listaPersonaje=new ArrayList<>();
        recyclerPersonajes=(RecyclerView) vista.findViewById(R.id.reciclerId);

        //ya no se usa this, yua que se esta en un fragment
        recyclerPersonajes.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        //Creo el adapter el cual se le pasa la lista de datos y este servira
        //para incrustarse en la recyclerView que se ha creado en el fragment
        //anterior mente inflado.
        AdapterDatos adapter=new AdapterDatos(listaPersonaje);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),
                        "Seleccion:"+listaPersonaje.get
                                (recyclerPersonajes.getChildAdapterPosition(v)).getNombre(),Toast.LENGTH_SHORT).show();
                //Intent siguiente=new Intent(MainActivity.this, prueba.class);
                //startActivity(siguiente);
            }
        });

        recyclerPersonajes.setAdapter(adapter);

       // construirRecycle(vista);

        return vista;
    }

    private void llenarLista() {
        listaPersonaje=new ArrayList<>();
        listaPersonaje.add(new PersonajeVo("Android","Android lenguaje de programacion para" +
                "dispositivos moviles",R.drawable.android));
        listaPersonaje.add(new PersonajeVo("JAVA","JAVA lenguaje de programacion para" +
                "desarrolladores",R.drawable.java));
        listaPersonaje.add(new PersonajeVo("PHP","Android lenguaje de programacion para" +
                "dispositivos moviles",R.drawable.php));
        listaPersonaje.add(new PersonajeVo("SYMFONY","JAVA lenguaje de programacion para" +
                "desarrolladores",R.drawable.symfony));
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



    /*public void construirRecycle() {
        Log.d("m1", "LLego");
        recyclerPersonajes=(RecyclerView) vista.findViewById(R.id.reciclerId);

        //Genera el tipo de recycler

        if(Utilidades.visualizacion==Utilidades.LIST){
            recyclerPersonajes.setLayoutManager(new LinearLayoutManager(getContext()));

        }else{
            recyclerPersonajes.setLayoutManager(new GridLayoutManager(getContext(),2));
        }

        Log.d("m2", "LLego2");

/*
        llenarLista();
        AdapterDatos adapter= new AdapterDatos(listaPersonaje);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),
                        "Seleccion:"+listaPersonaje.get
                                (recyclerPersonajes.getChildAdapterPosition(v)).getNombre(),Toast.LENGTH_SHORT).show();
               // Intent siguiente=new Intent(getActivity(), prueba.class);
                //startActivity(siguiente);
            }
        });
        recyclerPersonajes.setAdapter(adapter);*/
    //}*/


}
