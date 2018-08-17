package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "FOS_USER_USER".
 */
@Entity(active = true)
public class FosUserUser {

    @Id
    private Long id;
    private String username;
    private String password;
    private Integer tipoEmpleado;
    private String firstname;
    private String lastname;
    private String salt;
    private long idSibasi;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient FosUserUserDao myDao;

    @ToOne(joinProperty = "idSibasi")
    private CtlEstablecimiento ctlEstablecimiento;

    @Generated
    private transient Long ctlEstablecimiento__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public FosUserUser() {
    }

    public FosUserUser(Long id) {
        this.id = id;
    }

    @Generated
    public FosUserUser(Long id, String username, String password, Integer tipoEmpleado, String firstname, String lastname, String salt, long idSibasi) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipoEmpleado = tipoEmpleado;
        this.firstname = firstname;
        this.lastname = lastname;
        this.salt = salt;
        this.idSibasi = idSibasi;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFosUserUserDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(Integer tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
