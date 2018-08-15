package com.minsal.dtic.sinavec.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;

import com.minsal.dtic.sinavec.R;
import com.minsal.dtic.sinavec.adapters.SeccionesAdapter;
import com.minsal.dtic.sinavec.utilidades.Utilidades;


public class ContenedorFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    View vista;
    private AppBarLayout appBar;
    private TabLayout pestañas;
    private ViewPager viewPager;



    public ContenedorFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContenedorFragment newInstance(String param1, String param2) {
        ContenedorFragment fragment = new ContenedorFragment();
        Bundle args = new Bundle();
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
        vista= inflater.inflate(R.layout.fragment_contenedor, container, false);

        if (Utilidades.rotacion==0){
            View parent=(View)container.getParent();
        if (appBar==null){
            //Se usa parent ya que el appBar esta en un xml aparte no en la vista
            appBar=(AppBarLayout) parent.findViewById(R.id.appBar);
            pestañas=new TabLayout(getActivity());
            appBar.addView(pestañas);

            viewPager=(ViewPager) vista.findViewById(R.id.idViewPager);
            llenarViewPager(viewPager);

            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            });
            pestañas.setupWithViewPager(viewPager);
        }
        pestañas.setTabGravity(TabLayout.GRAVITY_FILL);
        }else{
            Utilidades.rotacion=1;
        }


        return vista;
    }

    private void llenarViewPager(ViewPager viewPager) {
        SeccionesAdapter addater=new SeccionesAdapter(getFragmentManager());
        addater.addFragment(new FebrilFragment(),"Febriles");
        addater.addFragment(new CapturaFragment(),"Entomologia");
        addater.addFragment(new CriaderoFragment(),"Criadero");
        addater.addFragment(new LenguajesFragment(),"Lenguajes");

        viewPager.setAdapter(addater);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Utilidades.rotacion==0){
            appBar.removeView(pestañas);
        }

    }
}
