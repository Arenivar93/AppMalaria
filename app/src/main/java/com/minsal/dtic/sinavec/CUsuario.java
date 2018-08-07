package com.minsal.dtic.sinavec;

/**
 * Created by administrador on 02-06-18.
 */

public class CUsuario {

    private Integer id;
    private String nombreUsuario;

    public CUsuario(){

    }

    public CUsuario(Integer id, String nombreUsuario){
        this.id=id;
        this.nombreUsuario=nombreUsuario;
    }

    @Override
    public String toString(){
        return nombreUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}

