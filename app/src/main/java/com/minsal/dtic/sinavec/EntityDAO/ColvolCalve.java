package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "COLVOL_CALVE".
 */
@Entity(active = true)
public class ColvolCalve implements Serializable {

    @Id
    private Long id;
    private long idClave;
    private long idColvol;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient ColvolCalveDao myDao;

    @ToOne(joinProperty = "idClave")
    private Clave clave;

    @Generated
    private transient Long clave__resolvedKey;

    @ToOne(joinProperty = "idColvol")
    private PlColvol plColvol;

    @Generated
    private transient Long plColvol__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public ColvolCalve() {
    }

    public ColvolCalve(Long id) {
        this.id = id;
    }

    @Generated
    public ColvolCalve(Long id, long idClave, long idColvol) {
        this.id = id;
        this.idClave = idClave;
        this.idColvol = idColvol;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getColvolCalveDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdClave() {
        return idClave;
    }

    public void setIdClave(long idClave) {
        this.idClave = idClave;
    }

    public long getIdColvol() {
        return idColvol;
    }

    public void setIdColvol(long idColvol) {
        this.idColvol = idColvol;
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
    public PlColvol getPlColvol() {
        long __key = this.idColvol;
        if (plColvol__resolvedKey == null || !plColvol__resolvedKey.equals(__key)) {
            __throwIfDetached();
            PlColvolDao targetDao = daoSession.getPlColvolDao();
            PlColvol plColvolNew = targetDao.load(__key);
            synchronized (this) {
                plColvol = plColvolNew;
            	plColvol__resolvedKey = __key;
            }
        }
        return plColvol;
    }

    @Generated
    public void setPlColvol(PlColvol plColvol) {
        if (plColvol == null) {
            throw new DaoException("To-one property 'idColvol' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.plColvol = plColvol;
            idColvol = plColvol.getId();
            plColvol__resolvedKey = idColvol;
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
