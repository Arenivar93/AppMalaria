package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "PL_CAPTURA_ANOPHELES".
 */
@Entity(active = true)
public class PlCapturaAnopheles {

    @Id(autoincrement = true)
    private Long id;
    private int idEstado;
    private int totalMosquitos;
    private int totalAnopheles;
    private int casaPositiva;
    private Integer casaInspeccionada;
    private Integer componenteInspeccionado;
    private int tiempoColecta;
    private java.util.Date fechaHoraMod;
    private String fecha;
    private java.util.Date fechaHoraReg;
    private String propietario;
    private int idSemanaEpidemiologica;
    private long idUsuarioMod;
    private int estado_sync;
    private long idSibasi;
    private long idTablet;
    private long idCaserio;
    private long idUsuarioReg;
    private long idTipoCaptura;
    private long idTipoActividad;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient PlCapturaAnophelesDao myDao;

    @ToOne(joinProperty = "idSibasi")
    private CtlEstablecimiento ctlEstablecimiento;

    @Generated
    private transient Long ctlEstablecimiento__resolvedKey;

    @ToOne(joinProperty = "idCaserio")
    private CtlCaserio ctlCaserio;

    @Generated
    private transient Long ctlCaserio__resolvedKey;

    @ToOne(joinProperty = "idTablet")
    private CtlTablet ctlTablet;

    @Generated
    private transient Long ctlTablet__resolvedKey;

    @ToOne(joinProperty = "idUsuarioReg")
    private FosUserUser fosUserUser;

    @Generated
    private transient Long fosUserUser__resolvedKey;

    @ToOne(joinProperty = "idTipoCaptura")
    private PlTipoCaptura plTipoCaptura;

    @Generated
    private transient Long plTipoCaptura__resolvedKey;

    @ToOne(joinProperty = "idTipoActividad")
    private PlTipoActividad plTipoActividad;

    @Generated
    private transient Long plTipoActividad__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public PlCapturaAnopheles() {
    }

    public PlCapturaAnopheles(Long id) {
        this.id = id;
    }

    @Generated
    public PlCapturaAnopheles(Long id, int idEstado, int totalMosquitos, int totalAnopheles, int casaPositiva, Integer casaInspeccionada, Integer componenteInspeccionado, int tiempoColecta, java.util.Date fechaHoraMod, String fecha, java.util.Date fechaHoraReg, String propietario, int idSemanaEpidemiologica, long idUsuarioMod, int estado_sync, long idSibasi, long idTablet, long idCaserio, long idUsuarioReg, long idTipoCaptura, long idTipoActividad) {
        this.id = id;
        this.idEstado = idEstado;
        this.totalMosquitos = totalMosquitos;
        this.totalAnopheles = totalAnopheles;
        this.casaPositiva = casaPositiva;
        this.casaInspeccionada = casaInspeccionada;
        this.componenteInspeccionado = componenteInspeccionado;
        this.tiempoColecta = tiempoColecta;
        this.fechaHoraMod = fechaHoraMod;
        this.fecha = fecha;
        this.fechaHoraReg = fechaHoraReg;
        this.propietario = propietario;
        this.idSemanaEpidemiologica = idSemanaEpidemiologica;
        this.idUsuarioMod = idUsuarioMod;
        this.estado_sync = estado_sync;
        this.idSibasi = idSibasi;
        this.idTablet = idTablet;
        this.idCaserio = idCaserio;
        this.idUsuarioReg = idUsuarioReg;
        this.idTipoCaptura = idTipoCaptura;
        this.idTipoActividad = idTipoActividad;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlCapturaAnophelesDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getTotalMosquitos() {
        return totalMosquitos;
    }

    public void setTotalMosquitos(int totalMosquitos) {
        this.totalMosquitos = totalMosquitos;
    }

    public int getTotalAnopheles() {
        return totalAnopheles;
    }

    public void setTotalAnopheles(int totalAnopheles) {
        this.totalAnopheles = totalAnopheles;
    }

    public int getCasaPositiva() {
        return casaPositiva;
    }

    public void setCasaPositiva(int casaPositiva) {
        this.casaPositiva = casaPositiva;
    }

    public Integer getCasaInspeccionada() {
        return casaInspeccionada;
    }

    public void setCasaInspeccionada(Integer casaInspeccionada) {
        this.casaInspeccionada = casaInspeccionada;
    }

    public Integer getComponenteInspeccionado() {
        return componenteInspeccionado;
    }

    public void setComponenteInspeccionado(Integer componenteInspeccionado) {
        this.componenteInspeccionado = componenteInspeccionado;
    }

    public int getTiempoColecta() {
        return tiempoColecta;
    }

    public void setTiempoColecta(int tiempoColecta) {
        this.tiempoColecta = tiempoColecta;
    }

    public java.util.Date getFechaHoraMod() {
        return fechaHoraMod;
    }

    public void setFechaHoraMod(java.util.Date fechaHoraMod) {
        this.fechaHoraMod = fechaHoraMod;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public java.util.Date getFechaHoraReg() {
        return fechaHoraReg;
    }

    public void setFechaHoraReg(java.util.Date fechaHoraReg) {
        this.fechaHoraReg = fechaHoraReg;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public int getIdSemanaEpidemiologica() {
        return idSemanaEpidemiologica;
    }

    public void setIdSemanaEpidemiologica(int idSemanaEpidemiologica) {
        this.idSemanaEpidemiologica = idSemanaEpidemiologica;
    }

    public long getIdUsuarioMod() {
        return idUsuarioMod;
    }

    public void setIdUsuarioMod(long idUsuarioMod) {
        this.idUsuarioMod = idUsuarioMod;
    }

    public int getEstado_sync() {
        return estado_sync;
    }

    public void setEstado_sync(int estado_sync) {
        this.estado_sync = estado_sync;
    }

    public long getIdSibasi() {
        return idSibasi;
    }

    public void setIdSibasi(long idSibasi) {
        this.idSibasi = idSibasi;
    }

    public long getIdTablet() {
        return idTablet;
    }

    public void setIdTablet(long idTablet) {
        this.idTablet = idTablet;
    }

    public long getIdCaserio() {
        return idCaserio;
    }

    public void setIdCaserio(long idCaserio) {
        this.idCaserio = idCaserio;
    }

    public long getIdUsuarioReg() {
        return idUsuarioReg;
    }

    public void setIdUsuarioReg(long idUsuarioReg) {
        this.idUsuarioReg = idUsuarioReg;
    }

    public long getIdTipoCaptura() {
        return idTipoCaptura;
    }

    public void setIdTipoCaptura(long idTipoCaptura) {
        this.idTipoCaptura = idTipoCaptura;
    }

    public long getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(long idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public CtlEstablecimiento getCtlEstablecimiento() {
        long __key = this.idSibasi;
        if (ctlEstablecimiento__resolvedKey == null || !ctlEstablecimiento__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CtlEstablecimientoDao targetDao = daoSession.getCtlEstablecimientoDao();
            CtlEstablecimiento ctlEstablecimientoNew = targetDao.load(__key);
            synchronized (this) {
                ctlEstablecimiento = ctlEstablecimientoNew;
            	ctlEstablecimiento__resolvedKey = __key;
            }
        }
        return ctlEstablecimiento;
    }

    @Generated
    public void setCtlEstablecimiento(CtlEstablecimiento ctlEstablecimiento) {
        if (ctlEstablecimiento == null) {
            throw new DaoException("To-one property 'idSibasi' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ctlEstablecimiento = ctlEstablecimiento;
            idSibasi = ctlEstablecimiento.getId();
            ctlEstablecimiento__resolvedKey = idSibasi;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public CtlCaserio getCtlCaserio() {
        long __key = this.idCaserio;
        if (ctlCaserio__resolvedKey == null || !ctlCaserio__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CtlCaserioDao targetDao = daoSession.getCtlCaserioDao();
            CtlCaserio ctlCaserioNew = targetDao.load(__key);
            synchronized (this) {
                ctlCaserio = ctlCaserioNew;
            	ctlCaserio__resolvedKey = __key;
            }
        }
        return ctlCaserio;
    }

    @Generated
    public void setCtlCaserio(CtlCaserio ctlCaserio) {
        if (ctlCaserio == null) {
            throw new DaoException("To-one property 'idCaserio' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ctlCaserio = ctlCaserio;
            idCaserio = ctlCaserio.getId();
            ctlCaserio__resolvedKey = idCaserio;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public CtlTablet getCtlTablet() {
        long __key = this.idTablet;
        if (ctlTablet__resolvedKey == null || !ctlTablet__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CtlTabletDao targetDao = daoSession.getCtlTabletDao();
            CtlTablet ctlTabletNew = targetDao.load(__key);
            synchronized (this) {
                ctlTablet = ctlTabletNew;
            	ctlTablet__resolvedKey = __key;
            }
        }
        return ctlTablet;
    }

    @Generated
    public void setCtlTablet(CtlTablet ctlTablet) {
        if (ctlTablet == null) {
            throw new DaoException("To-one property 'idTablet' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ctlTablet = ctlTablet;
            idTablet = ctlTablet.getId();
            ctlTablet__resolvedKey = idTablet;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public FosUserUser getFosUserUser() {
        long __key = this.idUsuarioReg;
        if (fosUserUser__resolvedKey == null || !fosUserUser__resolvedKey.equals(__key)) {
            __throwIfDetached();
            FosUserUserDao targetDao = daoSession.getFosUserUserDao();
            FosUserUser fosUserUserNew = targetDao.load(__key);
            synchronized (this) {
                fosUserUser = fosUserUserNew;
            	fosUserUser__resolvedKey = __key;
            }
        }
        return fosUserUser;
    }

    @Generated
    public void setFosUserUser(FosUserUser fosUserUser) {
        if (fosUserUser == null) {
            throw new DaoException("To-one property 'idUsuarioReg' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.fosUserUser = fosUserUser;
            idUsuarioReg = fosUserUser.getId();
            fosUserUser__resolvedKey = idUsuarioReg;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public PlTipoCaptura getPlTipoCaptura() {
        long __key = this.idTipoCaptura;
        if (plTipoCaptura__resolvedKey == null || !plTipoCaptura__resolvedKey.equals(__key)) {
            __throwIfDetached();
            PlTipoCapturaDao targetDao = daoSession.getPlTipoCapturaDao();
            PlTipoCaptura plTipoCapturaNew = targetDao.load(__key);
            synchronized (this) {
                plTipoCaptura = plTipoCapturaNew;
            	plTipoCaptura__resolvedKey = __key;
            }
        }
        return plTipoCaptura;
    }

    @Generated
    public void setPlTipoCaptura(PlTipoCaptura plTipoCaptura) {
        if (plTipoCaptura == null) {
            throw new DaoException("To-one property 'idTipoCaptura' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.plTipoCaptura = plTipoCaptura;
            idTipoCaptura = plTipoCaptura.getId();
            plTipoCaptura__resolvedKey = idTipoCaptura;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public PlTipoActividad getPlTipoActividad() {
        long __key = this.idTipoActividad;
        if (plTipoActividad__resolvedKey == null || !plTipoActividad__resolvedKey.equals(__key)) {
            __throwIfDetached();
            PlTipoActividadDao targetDao = daoSession.getPlTipoActividadDao();
            PlTipoActividad plTipoActividadNew = targetDao.load(__key);
            synchronized (this) {
                plTipoActividad = plTipoActividadNew;
            	plTipoActividad__resolvedKey = __key;
            }
        }
        return plTipoActividad;
    }

    @Generated
    public void setPlTipoActividad(PlTipoActividad plTipoActividad) {
        if (plTipoActividad == null) {
            throw new DaoException("To-one property 'idTipoActividad' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.plTipoActividad = plTipoActividad;
            idTipoActividad = plTipoActividad.getId();
            plTipoActividad__resolvedKey = idTipoActividad;
        }
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
