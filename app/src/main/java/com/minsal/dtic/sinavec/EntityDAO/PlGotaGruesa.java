package com.minsal.dtic.sinavec.EntityDAO;

import org.greenrobot.greendao.annotation.*;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "PL_GOTA_GRUESA".
 */
@Entity(active = true)
public class PlGotaGruesa {

    @Id(autoincrement = true)
    private Long id;
    private int idE6;
    private int idSemanaEpidemiologica;
    private int idSexo;
    private int idEstado;
    private int idUsuarioMod;
    private int busquedaActiva;

    @NotNull
    private String primerNombre;
    private int edad;

    @NotNull
    private String direccion;

    @NotNull
    private String fechaFiebre;

    @NotNull
    private String fechaToma;

    @NotNull
    private String fechaHoraReg;
    private String fechaHoraMod;
    private String fecha;
    private int esPc;
    private int tipoProcedencia;
    private int anio;

    @NotNull
    private String idVectores;
    private int extranjero;
    private int sospecha;
    private int idLabLectura;
    private String detalleLectura;
    private int estado_sync;
    private int unidadEdad;
    private String responsable;
    private String segundoNombre;

    @NotNull
    private String primerApellido;
    private String segundoApellido;
    private String numeroDocIdePaciente;
    private String idDocIdePaciente;
    private String fechaNacimiento;
    private long idTablet;
    private long idSibasi;
    private long idCaserio;
    private long idUsuarioReg;
    private long idClave;
    private long idPais;
    private long idResultado;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient PlGotaGruesaDao myDao;

    @ToOne(joinProperty = "idPais")
    private CtlPais ctlPais;

    @Generated
    private transient Long ctlPais__resolvedKey;

    @ToOne(joinProperty = "idTablet")
    private CtlTablet ctlTablet;

    @Generated
    private transient Long ctlTablet__resolvedKey;

    @ToOne(joinProperty = "idSibasi")
    private CtlEstablecimiento ctlEstablecimiento;

    @Generated
    private transient Long ctlEstablecimiento__resolvedKey;

    @ToOne(joinProperty = "idCaserio")
    private CtlCaserio ctlCaserio;

    @Generated
    private transient Long ctlCaserio__resolvedKey;

    @ToOne(joinProperty = "idUsuarioReg")
    private FosUserUser fosUserUser;

    @Generated
    private transient Long fosUserUser__resolvedKey;

    @ToOne(joinProperty = "idClave")
    private Clave clave;

    @Generated
    private transient Long clave__resolvedKey;

    @ToOne(joinProperty = "idResultado")
    private ResultadoGota resultadoGota;

    @Generated
    private transient Long resultadoGota__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public PlGotaGruesa() {
    }

    public PlGotaGruesa(Long id) {
        this.id = id;
    }

    @Generated
    public PlGotaGruesa(Long id, int idE6, int idSemanaEpidemiologica, int idSexo, int idEstado, int idUsuarioMod, int busquedaActiva, String primerNombre, int edad, String direccion, String fechaFiebre, String fechaToma, String fechaHoraReg, String fechaHoraMod, String fecha, int esPc, int tipoProcedencia, int anio, String idVectores, int extranjero, int sospecha, int idLabLectura, String detalleLectura, int estado_sync, int unidadEdad, String responsable, String segundoNombre, String primerApellido, String segundoApellido, String numeroDocIdePaciente, String idDocIdePaciente, String fechaNacimiento, long idTablet, long idSibasi, long idCaserio, long idUsuarioReg, long idClave, long idPais, long idResultado) {
        this.id = id;
        this.idE6 = idE6;
        this.idSemanaEpidemiologica = idSemanaEpidemiologica;
        this.idSexo = idSexo;
        this.idEstado = idEstado;
        this.idUsuarioMod = idUsuarioMod;
        this.busquedaActiva = busquedaActiva;
        this.primerNombre = primerNombre;
        this.edad = edad;
        this.direccion = direccion;
        this.fechaFiebre = fechaFiebre;
        this.fechaToma = fechaToma;
        this.fechaHoraReg = fechaHoraReg;
        this.fechaHoraMod = fechaHoraMod;
        this.fecha = fecha;
        this.esPc = esPc;
        this.tipoProcedencia = tipoProcedencia;
        this.anio = anio;
        this.idVectores = idVectores;
        this.extranjero = extranjero;
        this.sospecha = sospecha;
        this.idLabLectura = idLabLectura;
        this.detalleLectura = detalleLectura;
        this.estado_sync = estado_sync;
        this.unidadEdad = unidadEdad;
        this.responsable = responsable;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.numeroDocIdePaciente = numeroDocIdePaciente;
        this.idDocIdePaciente = idDocIdePaciente;
        this.fechaNacimiento = fechaNacimiento;
        this.idTablet = idTablet;
        this.idSibasi = idSibasi;
        this.idCaserio = idCaserio;
        this.idUsuarioReg = idUsuarioReg;
        this.idClave = idClave;
        this.idPais = idPais;
        this.idResultado = idResultado;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlGotaGruesaDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdE6() {
        return idE6;
    }

    public void setIdE6(int idE6) {
        this.idE6 = idE6;
    }

    public int getIdSemanaEpidemiologica() {
        return idSemanaEpidemiologica;
    }

    public void setIdSemanaEpidemiologica(int idSemanaEpidemiologica) {
        this.idSemanaEpidemiologica = idSemanaEpidemiologica;
    }

    public int getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdUsuarioMod() {
        return idUsuarioMod;
    }

    public void setIdUsuarioMod(int idUsuarioMod) {
        this.idUsuarioMod = idUsuarioMod;
    }

    public int getBusquedaActiva() {
        return busquedaActiva;
    }

    public void setBusquedaActiva(int busquedaActiva) {
        this.busquedaActiva = busquedaActiva;
    }

    @NotNull
    public String getPrimerNombre() {
        return primerNombre;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPrimerNombre(@NotNull String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @NotNull
    public String getDireccion() {
        return direccion;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDireccion(@NotNull String direccion) {
        this.direccion = direccion;
    }

    @NotNull
    public String getFechaFiebre() {
        return fechaFiebre;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFechaFiebre(@NotNull String fechaFiebre) {
        this.fechaFiebre = fechaFiebre;
    }

    @NotNull
    public String getFechaToma() {
        return fechaToma;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFechaToma(@NotNull String fechaToma) {
        this.fechaToma = fechaToma;
    }

    @NotNull
    public String getFechaHoraReg() {
        return fechaHoraReg;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFechaHoraReg(@NotNull String fechaHoraReg) {
        this.fechaHoraReg = fechaHoraReg;
    }

    public String getFechaHoraMod() {
        return fechaHoraMod;
    }

    public void setFechaHoraMod(String fechaHoraMod) {
        this.fechaHoraMod = fechaHoraMod;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEsPc() {
        return esPc;
    }

    public void setEsPc(int esPc) {
        this.esPc = esPc;
    }

    public int getTipoProcedencia() {
        return tipoProcedencia;
    }

    public void setTipoProcedencia(int tipoProcedencia) {
        this.tipoProcedencia = tipoProcedencia;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @NotNull
    public String getIdVectores() {
        return idVectores;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setIdVectores(@NotNull String idVectores) {
        this.idVectores = idVectores;
    }

    public int getExtranjero() {
        return extranjero;
    }

    public void setExtranjero(int extranjero) {
        this.extranjero = extranjero;
    }

    public int getSospecha() {
        return sospecha;
    }

    public void setSospecha(int sospecha) {
        this.sospecha = sospecha;
    }

    public int getIdLabLectura() {
        return idLabLectura;
    }

    public void setIdLabLectura(int idLabLectura) {
        this.idLabLectura = idLabLectura;
    }

    public String getDetalleLectura() {
        return detalleLectura;
    }

    public void setDetalleLectura(String detalleLectura) {
        this.detalleLectura = detalleLectura;
    }

    public int getEstado_sync() {
        return estado_sync;
    }

    public void setEstado_sync(int estado_sync) {
        this.estado_sync = estado_sync;
    }

    public int getUnidadEdad() {
        return unidadEdad;
    }

    public void setUnidadEdad(int unidadEdad) {
        this.unidadEdad = unidadEdad;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    @NotNull
    public String getPrimerApellido() {
        return primerApellido;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPrimerApellido(@NotNull String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNumeroDocIdePaciente() {
        return numeroDocIdePaciente;
    }

    public void setNumeroDocIdePaciente(String numeroDocIdePaciente) {
        this.numeroDocIdePaciente = numeroDocIdePaciente;
    }

    public String getIdDocIdePaciente() {
        return idDocIdePaciente;
    }

    public void setIdDocIdePaciente(String idDocIdePaciente) {
        this.idDocIdePaciente = idDocIdePaciente;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public long getIdTablet() {
        return idTablet;
    }

    public void setIdTablet(long idTablet) {
        this.idTablet = idTablet;
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

    public long getIdUsuarioReg() {
        return idUsuarioReg;
    }

    public void setIdUsuarioReg(long idUsuarioReg) {
        this.idUsuarioReg = idUsuarioReg;
    }

    public long getIdClave() {
        return idClave;
    }

    public void setIdClave(long idClave) {
        this.idClave = idClave;
    }

    public long getIdPais() {
        return idPais;
    }

    public void setIdPais(long idPais) {
        this.idPais = idPais;
    }

    public long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(long idResultado) {
        this.idResultado = idResultado;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public CtlPais getCtlPais() {
        long __key = this.idPais;
        if (ctlPais__resolvedKey == null || !ctlPais__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CtlPaisDao targetDao = daoSession.getCtlPaisDao();
            CtlPais ctlPaisNew = targetDao.load(__key);
            synchronized (this) {
                ctlPais = ctlPaisNew;
            	ctlPais__resolvedKey = __key;
            }
        }
        return ctlPais;
    }

    @Generated
    public void setCtlPais(CtlPais ctlPais) {
        if (ctlPais == null) {
            throw new DaoException("To-one property 'idPais' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ctlPais = ctlPais;
            idPais = ctlPais.getId();
            ctlPais__resolvedKey = idPais;
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
    public ResultadoGota getResultadoGota() {
        long __key = this.idResultado;
        if (resultadoGota__resolvedKey == null || !resultadoGota__resolvedKey.equals(__key)) {
            __throwIfDetached();
            ResultadoGotaDao targetDao = daoSession.getResultadoGotaDao();
            ResultadoGota resultadoGotaNew = targetDao.load(__key);
            synchronized (this) {
                resultadoGota = resultadoGotaNew;
            	resultadoGota__resolvedKey = __key;
            }
        }
        return resultadoGota;
    }

    @Generated
    public void setResultadoGota(ResultadoGota resultadoGota) {
        if (resultadoGota == null) {
            throw new DaoException("To-one property 'idResultado' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.resultadoGota = resultadoGota;
            idResultado = resultadoGota.getId();
            resultadoGota__resolvedKey = idResultado;
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
