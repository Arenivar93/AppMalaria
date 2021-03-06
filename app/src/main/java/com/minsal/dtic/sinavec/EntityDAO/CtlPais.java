package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CTL_PAIS".
 */
@Entity
public class CtlPais implements Serializable {

    @Id
    private Long id;
    private String nombre;
    private Integer activo;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public CtlPais() {
    }

    public CtlPais(Long id) {
        this.id = id;
    }

    @Generated
    public CtlPais(Long id, String nombre, Integer activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
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

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}