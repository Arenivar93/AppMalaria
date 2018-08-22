package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CTL_TABLET".
 */
@Entity(active = true)
public class CtlTablet {

    @Id
    private Long id;
    private String codigo;
    private String serie; //se usara como IMEI
    private long idSibasi;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient CtlTabletDao myDao;

    @ToOne(joinProperty = "idSibasi")
    private CtlEstablecimiento ctlEstablecimiento;

    @Generated
    private transient Long ctlEstablecimiento__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public CtlTablet() {
    }

    public CtlTablet(Long id) {
        this.id = id;
    }

    @Generated
    public CtlTablet(Long id, String codigo, String serie, long idSibasi) {
        this.id = id;
        this.codigo = codigo;
        this.serie = serie;
        this.idSibasi = idSibasi;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCtlTabletDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public long getIdSibasi() {
        return idSibasi;
    }

    public void setIdSibasi(long idSibasi) {
        this.idSibasi = idSibasi;
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
