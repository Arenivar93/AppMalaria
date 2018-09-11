package com.minsal.dtic.sinavec.CRUD.Colvol.fragmentColvol;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minsal.dtic.sinavec.CRUD.Colvol.activityColvol.BuscarColvolActivity;
import com.minsal.dtic.sinavec.CRUD.Colvol.activityColvol.ListaColvolMapa;
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.MenuCriaderoFragment;
import com.minsal.dtic.sinavec.R;

public class MenuColvolFragment extends Fragment {
    CardView cvAsignar, cvVer;

    private MenuColvolFragment.OnFragmentInteractionListener mListener;

    public MenuColvolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_menu_colvol, container, false);
        cvAsignar= (CardView) v.findViewById(R.id.cvAsignarCoordenada);
        cvVer = (CardView) v.findViewById(R.id.cvCriaderoCoordenada);

        cvAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),BuscarColvolActivity.class);
                startActivity(i);

            }
        });

        cvVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ListaColvolMapa.class);
                startActivity(i);
            }
        });

        return v;
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
        if (context instanceof MenuCriaderoFragment.OnFragmentInteractionListener) {
            mListener = (MenuColvolFragment.OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
