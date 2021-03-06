package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CTL_CASERIO".
 */
@Entity(active = true)
public class CtlCaserio {

    @Id
    private Long id;
    private String nombre;
    private Long idDeptoApoyo;
    private Integer bandera;
    private long idCanton;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient CtlCaserioDao myDao;

    @ToOne(joinProperty = "idCanton")
    private CtlCanton ctlCanton;

    @Generated
    private transient Long ctlCanton__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public CtlCaserio() {
    }

    public CtlCaserio(Long id) {
        this.id = id;
    }

    @Generated
    public CtlCaserio(Long id, String nombre, Long idDeptoApoyo, Integer bandera, long idCanton) {
        this.id = id;
        this.nombre = nombre;
        this.idDeptoApoyo = idDeptoApoyo;
        this.bandera = bandera;
        this.idCanton = idCanton;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCtlCaserioDao() : null;
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

    public Long getIdDeptoApoyo() {
        return idDeptoApoyo;
    }

    public void setIdDeptoApoyo(Long idDeptoApoyo) {
        this.idDeptoApoyo = idDeptoApoyo;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public long getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(long idCanton) {
        this.idCanton = idCanton;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public CtlCanton getCtlCanton() {
        long __key = this.idCanton;
        if (ctlCanton__resolvedKey == null || !ctlCanton__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CtlCantonDao targetDao = daoSession.getCtlCantonDao();
            CtlCanton ctlCantonNew = targetDao.load(__key);
            synchronized (this) {
                ctlCanton = ctlCantonNew;
            	ctlCanton__resolvedKey = __key;
            }
        }
        return ctlCanton;
    }

    @Generated
    public void setCtlCanton(CtlCanton ctlCanton) {
        if (ctlCanton == null) {
            throw new DaoException("To-one property 'idCanton' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ctlCanton = ctlCanton;
            idCanton = ctlCanton.getId();
            ctlCanton__resolvedKey = idCanton;
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
