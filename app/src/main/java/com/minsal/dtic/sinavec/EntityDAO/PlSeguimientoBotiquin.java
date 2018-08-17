package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "PL_SEGUIMIENTO_BOTIQUIN".
 */
@Entity(active = true)
public class PlSeguimientoBotiquin {

    @Id(autoincrement = true)
    private Long id;
    private int idEstadoFormulario;
    private int supervisado;
    private int visitado;
    private int enRiesgo;
    private int numeroPersonaDivulgo;
    private java.util.Date fechaHoraReg;
    private java.util.Date fechaHoraMod;

    @NotNull
    private java.util.Date fecha;
    private int idSemanaEpidemiologica;

    @NotNull
    private java.util.Date fechaRegistro;
    private int numeroMuestra;
    private int idUsuarioMod;
    private long idClave;
    private long idTablet;
    private long idUsuarioReg;
    private long idSibasi;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient PlSeguimientoBotiquinDao myDao;

    @ToOne(joinProperty = "idClave")
    private Clave clave;

    @Generated
    private transient Long clave__resolvedKey;

    @ToOne(joinProperty = "idTablet")
    private CtlTablet ctlTablet;

    @Generated
    private transient Long ctlTablet__resolvedKey;

    @ToOne(joinProperty = "idUsuarioReg")
    private FosUserUser fosUserUser;

    @Generated
    private transient Long fosUserUser__resolvedKey;

    @ToOne(joinProperty = "idSibasi")
    private CtlEstablecimiento ctlEstablecimiento;

    @Generated
    private transient Long ctlEstablecimiento__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public PlSeguimientoBotiquin() {
    }

    public PlSeguimientoBotiquin(Long id) {
        this.id = id;
    }

    @Generated
    public PlSeguimientoBotiquin(Long id, int idEstadoFormulario, int supervisado, int visitado, int enRiesgo, int numeroPersonaDivulgo, java.util.Date fechaHoraReg, java.util.Date fechaHoraMod, java.util.Date fecha, int idSemanaEpidemiologica, java.util.Date fechaRegistro, int numeroMuestra, int idUsuarioMod, long idClave, long idTablet, long idUsuarioReg, long idSibasi) {
        this.id = id;
        this.idEstadoFormulario = idEstadoFormulario;
        this.supervisado = supervisado;
        this.visitado = visitado;
        this.enRiesgo = enRiesgo;
        this.numeroPersonaDivulgo = numeroPersonaDivulgo;
        this.fechaHoraReg = fechaHoraReg;
        this.fechaHoraMod = fechaHoraMod;
        this.fecha = fecha;
        this.idSemanaEpidemiologica = idSemanaEpidemiologica;
        this.fechaRegistro = fechaRegistro;
        this.numeroMuestra = numeroMuestra;
        this.idUsuarioMod = idUsuarioMod;
        this.idClave = idClave;
        this.idTablet = idTablet;
        this.idUsuarioReg = idUsuarioReg;
        this.idSibasi = idSibasi;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlSeguimientoBotiquinDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdEstadoFormulario() {
        return idEstadoFormulario;
    }

    public void setIdEstadoFormulario(int idEstadoFormulario) {
        this.idEstadoFormulario = idEstadoFormulario;
    }

    public int getSupervisado() {
        return supervisado;
    }

    public void setSupervisado(int supervisado) {
        this.supervisado = supervisado;
    }

    public int getVisitado() {
        return visitado;
    }

    public void setVisitado(int visitado) {
        this.visitado = visitado;
    }

    public int getEnRiesgo() {
        return enRiesgo;
    }

    public void setEnRiesgo(int enRiesgo) {
        this.enRiesgo = enRiesgo;
    }

    public int getNumeroPersonaDivulgo() {
        return numeroPersonaDivulgo;
    }

    public void setNumeroPersonaDivulgo(int numeroPersonaDivulgo) {
        this.numeroPersonaDivulgo = numeroPersonaDivulgo;
    }

    public java.util.Date getFechaHoraReg() {
        return fechaHoraReg;
    }

    public void setFechaHoraReg(java.util.Date fechaHoraReg) {
        this.fechaHoraReg = fechaHoraReg;
    }

    public java.util.Date getFechaHoraMod() {
        return fechaHoraMod;
    }

    public void setFechaHoraMod(java.util.Date fechaHoraMod) {
        this.fechaHoraMod = fechaHoraMod;
    }

    @NotNull
    public java.util.Date getFecha() {
        return fecha;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFecha(@NotNull java.util.Date fecha) {
        this.fecha = fecha;
    }

    public int getIdSemanaEpidemiologica() {
        return idSemanaEpidemiologica;
    }

    public void setIdSemanaEpidemiologica(int idSemanaEpidemiologica) {
        this.idSemanaEpidemiologica = idSemanaEpidemiologica;
    }

    @NotNull
    public java.util.Date getFechaRegistro() {
        return fechaRegistro;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFechaRegistro(@NotNull java.util.Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getNumeroMuestra() {
        return numeroMuestra;
    }

    public void setNumeroMuestra(int numeroMuestra) {
        this.numeroMuestra = numeroMuestra;
    }

    public int getIdUsuarioMod() {
        return idUsuarioMod;
    }

    public void setIdUsuarioMod(int idUsuarioMod) {
        this.idUsuarioMod = idUsuarioMod;
    }

    public long getIdClave() {
        return idClave;
    }

    public void setIdClave(long idClave) {
        this.idClave = idClave;
    }

    public long getIdTablet() {
        return idTablet;
    }

    public void setIdTablet(long idTablet) {
        this.idTablet = idTablet;
    }

    public long getIdUsuarioReg() {
        return idUsuarioReg;
    }

    public void setIdUsuarioReg(long idUsuarioReg) {
        this.idUsuarioReg = idUsuarioReg;
    }

    public long getIdSibasi() {
        return idSibasi;
    }

    public void setIdSibasi(long idSibasi) {
        this.idSibasi = idSibasi;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Clave getClave() {
        long __key = this.idClave;
        if (clave__resolvedKey == null || !clave__resolvedKey.equals(__key)) {
            __throwIfDetached();
            ClaveDao targetDao = daoSession.getClaveDao();
            Clave claveNew = targetDao.load(__key);
            synchronized (this) {
                clave = claveNew;
            	clave__resolvedKey = __key;
            }
        }
        return clave;
    }

    @Generated
    public void setClave(Clave clave) {
        if (clave == null) {
            throw new DaoException("To-one property 'idClave' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.clave = clave;
            idClave = clave.getId();
            clave__resolvedKey = idClave;
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