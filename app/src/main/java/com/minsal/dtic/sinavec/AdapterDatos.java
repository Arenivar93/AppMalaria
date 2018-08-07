package com.minsal.dtic.sinavec;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minsal.dtic.sinavec.clases.Utilidades;

import java.util.ArrayList;

/**
 * Created by administrador on 07-23-18.
 */

public class AdapterDatos
        extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos>
        implements View.OnClickListener{

    ArrayList<PersonajeVo> listDatos;
    private View.OnClickListener listener;

    public AdapterDatos(ArrayList<PersonajeVo> listDatos) {
        this.listDatos = listDatos;
    }

        //Enlazara el presente adaptador con el xml item_list
    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout=0;
        if(Utilidades.visualizacion==Utilidades.LIST){
            layout= R.layout.item_list;

        }else{
            layout=R.layout.item_grid_list;
        }

        View view= LayoutInflater.from(parent.getContext())
                .inflate(layout,null,false);
        //permite que se escuche el evento de seleccion
        view.setOnClickListener(this);

        return new ViewHolderDatos(view);
    }

        //comunicara el adaptador en tre la clase ViewHolderDatos
    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        holder.idNombre.setText(listDatos.get(position).getNombre());
        if(Utilidades.visualizacion==Utilidades.LIST){
            holder.idInfo.setText(listDatos.get(position).getInfo());
        }
        holder.foto.setImageResource(listDatos.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView idNombre,idInfo;
        ImageView foto;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            //hace la referencia al contenido que se cargara
            idNombre=(TextView) itemView.findViewById(R.id.idNombre);
            if(Utilidades.visualizacion==Utilidades.LIST){
                idInfo=(TextView)itemView.findViewById(R.id.idInfo);
            }
            foto=(ImageView) itemView.findViewById(R.id.foto);
        }

        /*public void asignarDatos(String datos) {
            dato.setText(datos);
        }*/
    }
}
