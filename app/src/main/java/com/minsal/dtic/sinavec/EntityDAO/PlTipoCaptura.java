package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "PL_TIPO_CAPTURA".
 */
@Entity
public class PlTipoCaptura {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public PlTipoCaptura() {
    }

    public PlTipoCaptura(Long id) {
        this.id = id;
    }

    @Generated
    public PlTipoCaptura(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
