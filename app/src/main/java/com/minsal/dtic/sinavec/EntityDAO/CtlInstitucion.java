package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CTL_INSTITUCION".
 */
@Entity
public class CtlInstitucion implements Serializable {

    @Id
    private Long id;
    private String nombre;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public CtlInstitucion() {
    }

    public CtlInstitucion(Long id) {
        this.id = id;
    }

    @Generated
    public CtlInstitucion(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
