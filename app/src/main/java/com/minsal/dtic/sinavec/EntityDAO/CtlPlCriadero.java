package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CTL_PL_CRIADERO".
 */
@Entity(active = true)
public class CtlPlCriadero implements Serializable{

    @Id
    private Long id;
    private Integer idTipoCriadero;
    private Integer idEstadoCriadero;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;
    private String latitud;
    private String longitud;
    private float longitudCriadero;
    private float anchoCriadero;
    private java.util.Date fechaHoraReg;
    private java.util.Date fechaHoraMod;
    private long idUsarioReg;
    private long idSibasi;
    private long idCaserio;
    private long idUsuarioMod;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient CtlPlCriaderoDao myDao;

    @ToOne(joinProperty = "idSibasi")
    private CtlEstablecimiento ctlEstablecimiento;

    @Generated
    private transient Long ctlEstablecimiento__resolvedKey;

    @ToOne(joinProperty = "idCaserio")
    private CtlCaserio ctlCaserio;

    @Generated
    private transient Long ctlCaserio__resolvedKey;

    @ToOne(joinProperty = "idUsuarioMod")
    private FosUserUser fosUserUser;

    @Generated
    private transient Long fosUserUser__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public CtlPlCriadero() {
    }

    public CtlPlCriadero(Long id) {
        this.id = id;
    }

    @Generated
    public CtlPlCriadero(Long id, Integer idTipoCriadero, Integer idEstadoCriadero, String nombre, String descripcion, String latitud, String longitud, float longitudCriadero, float anchoCriadero, java.util.Date fechaHoraReg, java.util.Date fechaHoraMod, long idUsarioReg, long idSibasi, long idCaserio, long idUsuarioMod) {
        this.id = id;
        this.idTipoCriadero = idTipoCriadero;
        this.idEstadoCriadero = idEstadoCriadero;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.longitudCriadero = longitudCriadero;
        this.anchoCriadero = anchoCriadero;
        this.fechaHoraReg = fechaHoraReg;
        this.fechaHoraMod = fechaHoraMod;
        this.idUsarioReg = idUsarioReg;
        this.idSibasi = idSibasi;
        this.idCaserio = idCaserio;
        this.idUsuarioMod = idUsuarioMod;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCtlPlCriaderoDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdTipoCriadero() {
        return idTipoCriadero;
    }

    public void setIdTipoCriadero(Integer idTipoCriadero) {
        this.idTipoCriadero = idTipoCriadero;
    }

    public Integer getIdEstadoCriadero() {
        return idEstadoCriadero;
    }

    public void setIdEstadoCriadero(Integer idEstadoCriadero) {
        this.idEstadoCriadero = idEstadoCriadero;
    }

    @NotNull
    public String getNombre() {
        return nombre;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setNombre(@NotNull String nombre) {
        this.nombre = nombre;
    }

    @NotNull
    public String getDescripcion() {
        return descripcion;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescripcion(@NotNull String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public float getLongitudCriadero() {
        return longitudCriadero;
    }

    public void setLongitudCriadero(float longitudCriadero) {
        this.longitudCriadero = longitudCriadero;
    }

    public float getAnchoCriadero() {
        return anchoCriadero;
    }

    public void setAnchoCriadero(float anchoCriadero) {
        this.anchoCriadero = anchoCriadero;
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

    public long getIdUsarioReg() {
        return idUsarioReg;
    }

    public void setIdUsarioReg(long idUsarioReg) {
        this.idUsarioReg = idUsarioReg;
    }

    public long getIdSibasi() {
        return idSibasi;
    }

    public void setIdSibasi(long idSibasi) {
        this.idSibasi = idSibasi;
    }

    public long getIdCaserio() {
        return idCaserio;
    }

    public void setIdCaserio(long idCaserio) {
        this.idCaserio = idCaserio;
    }

    public long getIdUsuarioMod() {
        return idUsuarioMod;
    }

    public void setIdUsuarioMod(long idUsuarioMod) {
        this.idUsuarioMod = idUsuarioMod;
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
    public FosUserUser getFosUserUser() {
        long __key = this.idUsuarioMod;
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
            throw new DaoException("To-one property 'idUsuarioMod' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.fosUserUser = fosUserUser;
            idUsuarioMod = fosUserUser.getId();
            fosUserUser__resolvedKey = idUsuarioMod;
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
