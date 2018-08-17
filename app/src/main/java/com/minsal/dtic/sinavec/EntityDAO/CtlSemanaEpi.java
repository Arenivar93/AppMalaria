package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CTL_SEMANA_EPI".
 */
@Entity
public class CtlSemanaEpi {

    @Id
    private Long id;
    private Integer anio;
    private Integer semana;
    private java.util.Date fechaInicio;
    private java.util.Date fechaFin;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public CtlSemanaEpi() {
    }

    public CtlSemanaEpi(Long id) {
        this.id = id;
    }

    @Generated
    public CtlSemanaEpi(Long id, Integer anio, Integer semana, java.util.Date fechaInicio, java.util.Date fechaFin) {
        this.id = id;
        this.anio = anio;
        this.semana = semana;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public java.util.Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(java.util.Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public java.util.Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(java.util.Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
