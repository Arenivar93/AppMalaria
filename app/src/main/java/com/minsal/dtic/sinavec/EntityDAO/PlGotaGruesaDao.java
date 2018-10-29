

package com.minsal.dtic.sinavec.EntityDAO;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "PL_GOTA_GRUESA".
 */
public class PlGotaGruesaDao extends AbstractDao<PlGotaGruesa, Long> {

    public static final String TABLENAME = "PL_GOTA_GRUESA";

    /**
     * Properties of entity PlGotaGruesa.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property IdE6 = new Property(1, int.class, "idE6", false, "ID_E6");
        public final static Property IdSemanaEpidemiologica = new Property(2, int.class, "idSemanaEpidemiologica", false, "ID_SEMANA_EPIDEMIOLOGICA");
        public final static Property IdSexo = new Property(3, int.class, "idSexo", false, "ID_SEXO");
        public final static Property IdEstado = new Property(4, int.class, "idEstado", false, "ID_ESTADO");
        public final static Property IdUsuarioMod = new Property(5, int.class, "idUsuarioMod", false, "ID_USUARIO_MOD");
        public final static Property BusquedaActiva = new Property(6, int.class, "busquedaActiva", false, "BUSQUEDA_ACTIVA");
        public final static Property Nombre = new Property(7, String.class, "nombre", false, "NOMBRE");
        public final static Property Edad = new Property(8, int.class, "edad", false, "EDAD");
        public final static Property Direccion = new Property(9, String.class, "direccion", false, "DIRECCION");
        public final static Property FechaFiebre = new Property(10, String.class, "fechaFiebre", false, "FECHA_FIEBRE");
        public final static Property FechaToma = new Property(11, String.class, "fechaToma", false, "FECHA_TOMA");
        public final static Property FechaHoraReg = new Property(12, String.class, "fechaHoraReg", false, "FECHA_HORA_REG");
        public final static Property FechaHoraMod = new Property(13, String.class, "fechaHoraMod", false, "FECHA_HORA_MOD");
        public final static Property Fecha = new Property(14, String.class, "fecha", false, "FECHA");
        public final static Property EsPc = new Property(15, int.class, "esPc", false, "ES_PC");
        public final static Property TipoProcedencia = new Property(16, int.class, "tipoProcedencia", false, "TIPO_PROCEDENCIA");
        public final static Property Anio = new Property(17, int.class, "anio", false, "ANIO");
        public final static Property IdVectores = new Property(18, String.class, "idVectores", false, "ID_VECTORES");
        public final static Property Extranjero = new Property(19, int.class, "extranjero", false, "EXTRANJERO");
        public final static Property Sospecha = new Property(20, int.class, "sospecha", false, "SOSPECHA");
        public final static Property IdLabLectura = new Property(21, int.class, "idLabLectura", false, "ID_LAB_LECTURA");
        public final static Property DetalleLectura = new Property(22, String.class, "detalleLectura", false, "DETALLE_LECTURA");
        public final static Property Estado_sync = new Property(23, int.class, "estado_sync", false, "ESTADO_SYNC");
        public final static Property UnidadEdad = new Property(24, int.class, "unidadEdad", false, "UNIDAD_EDAD");
        public final static Property Responsable = new Property(25, String.class, "responsable", false, "RESPONSABLE");
        public final static Property IdTablet = new Property(26, long.class, "idTablet", false, "ID_TABLET");
        public final static Property IdSibasi = new Property(27, long.class, "idSibasi", false, "ID_SIBASI");
        public final static Property IdCaserio = new Property(28, long.class, "idCaserio", false, "ID_CASERIO");
        public final static Property IdUsuarioReg = new Property(29, long.class, "idUsuarioReg", false, "ID_USUARIO_REG");
        public final static Property IdClave = new Property(30, long.class, "idClave", false, "ID_CLAVE");
        public final static Property IdPais = new Property(31, long.class, "idPais", false, "ID_PAIS");
        public final static Property IdResultado = new Property(32, long.class, "idResultado", false, "ID_RESULTADO");
    }

    private DaoSession daoSession;


    public PlGotaGruesaDao(DaoConfig config) {
        super(config);
    }

    public PlGotaGruesaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PL_GOTA_GRUESA\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ID_E6\" INTEGER NOT NULL ," + // 1: idE6
                "\"ID_SEMANA_EPIDEMIOLOGICA\" INTEGER NOT NULL ," + // 2: idSemanaEpidemiologica
                "\"ID_SEXO\" INTEGER NOT NULL ," + // 3: idSexo
                "\"ID_ESTADO\" INTEGER NOT NULL ," + // 4: idEstado
                "\"ID_USUARIO_MOD\" INTEGER NOT NULL ," + // 5: idUsuarioMod
                "\"BUSQUEDA_ACTIVA\" INTEGER NOT NULL ," + // 6: busquedaActiva
                "\"NOMBRE\" TEXT NOT NULL ," + // 7: nombre
                "\"EDAD\" INTEGER NOT NULL ," + // 8: edad
                "\"DIRECCION\" TEXT NOT NULL ," + // 9: direccion
                "\"FECHA_FIEBRE\" TEXT NOT NULL ," + // 10: fechaFiebre
                "\"FECHA_TOMA\" TEXT NOT NULL ," + // 11: fechaToma
                "\"FECHA_HORA_REG\" TEXT NOT NULL ," + // 12: fechaHoraReg
                "\"FECHA_HORA_MOD\" TEXT NOT NULL ," + // 13: fechaHoraMod
                "\"FECHA\" TEXT NOT NULL ," + // 14: fecha
                "\"ES_PC\" INTEGER NOT NULL ," + // 15: esPc
                "\"TIPO_PROCEDENCIA\" INTEGER NOT NULL ," + // 16: tipoProcedencia
                "\"ANIO\" INTEGER NOT NULL ," + // 17: anio
                "\"ID_VECTORES\" TEXT NOT NULL ," + // 18: idVectores
                "\"EXTRANJERO\" INTEGER NOT NULL ," + // 19: extranjero
                "\"SOSPECHA\" INTEGER NOT NULL ," + // 20: sospecha
                "\"ID_LAB_LECTURA\" INTEGER NOT NULL ," + // 21: idLabLectura
                "\"DETALLE_LECTURA\" TEXT NOT NULL ," + // 22: detalleLectura
                "\"ESTADO_SYNC\" INTEGER NOT NULL ," + // 23: estado_sync
                "\"UNIDAD_EDAD\" INTEGER NOT NULL ," + // 24: unidadEdad
                "\"RESPONSABLE\" TEXT NOT NULL ," + // 25: responsable
                "\"ID_TABLET\" INTEGER NOT NULL ," + // 26: idTablet
                "\"ID_SIBASI\" INTEGER NOT NULL ," + // 27: idSibasi
                "\"ID_CASERIO\" INTEGER NOT NULL ," + // 28: idCaserio
                "\"ID_USUARIO_REG\" INTEGER NOT NULL ," + // 29: idUsuarioReg
                "\"ID_CLAVE\" INTEGER NOT NULL ," + // 30: idClave
                "\"ID_PAIS\" INTEGER NOT NULL ," + // 31: idPais
                "\"ID_RESULTADO\" INTEGER NOT NULL,"+"FOREIGN KEY(\"ID_SIBASI\")"+
                " REFERENCES CTL_ESTABLECIMIENTO(\"ID\") ON DELETE CASCADE, "+"FOREIGN KEY(\"ID_CASERIO\")"+
                " REFERENCES CTL_CASERIO(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_TABLET\")"+
                " REFERENCES CTL_TABLET(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_USUARIO_REG\")" +
                " REFERENCES FOS_USER_USER(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_RESULTADO\")"+
                " REFERENCES RESULTADO_GOTA(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_PAIS\")"+
                " REFERENCES CTL_PAIS(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_CLAVE\")"+
                " REFERENCES CLAVE(\"ID\") ON DELETE CASCADE );"); // 30: idResultado
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PL_GOTA_GRUESA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PlGotaGruesa entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIdE6());
        stmt.bindLong(3, entity.getIdSemanaEpidemiologica());
        stmt.bindLong(4, entity.getIdSexo());
        stmt.bindLong(5, entity.getIdEstado());
        stmt.bindLong(6, entity.getIdUsuarioMod());
        stmt.bindLong(7, entity.getBusquedaActiva());
        stmt.bindString(8, entity.getNombre());
        stmt.bindLong(9, entity.getEdad());
        stmt.bindString(10, entity.getDireccion());
        stmt.bindString(11, entity.getFechaFiebre());
        stmt.bindString(12, entity.getFechaToma());
        stmt.bindString(13, entity.getFechaHoraReg());
        stmt.bindString(14, entity.getFechaHoraMod());
        stmt.bindString(15, entity.getFecha());
        stmt.bindLong(16, entity.getEsPc());
        stmt.bindLong(17, entity.getTipoProcedencia());
        stmt.bindLong(18, entity.getAnio());
        stmt.bindString(19, entity.getIdVectores());
        stmt.bindLong(20, entity.getExtranjero());
        stmt.bindLong(21, entity.getSospecha());
        stmt.bindLong(22, entity.getIdLabLectura());
        stmt.bindString(23, entity.getDetalleLectura());
        stmt.bindLong(24, entity.getEstado_sync());
        stmt.bindLong(25, entity.getUnidadEdad());
        stmt.bindString(26, entity.getResponsable());
        stmt.bindLong(27, entity.getIdTablet());
        stmt.bindLong(28, entity.getIdSibasi());
        stmt.bindLong(29, entity.getIdCaserio());
        stmt.bindLong(30, entity.getIdUsuarioReg());
        stmt.bindLong(31, entity.getIdClave());
        stmt.bindLong(32, entity.getIdPais());
        stmt.bindLong(33, entity.getIdResultado());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PlGotaGruesa entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIdE6());
        stmt.bindLong(3, entity.getIdSemanaEpidemiologica());
        stmt.bindLong(4, entity.getIdSexo());
        stmt.bindLong(5, entity.getIdEstado());
        stmt.bindLong(6, entity.getIdUsuarioMod());
        stmt.bindLong(7, entity.getBusquedaActiva());
        stmt.bindString(8, entity.getNombre());
        stmt.bindLong(9, entity.getEdad());
        stmt.bindString(10, entity.getDireccion());
        stmt.bindString(11, entity.getFechaFiebre());
        stmt.bindString(12, entity.getFechaToma());
        stmt.bindString(13, entity.getFechaHoraReg());
        stmt.bindString(14, entity.getFechaHoraMod());
        stmt.bindString(15, entity.getFecha());
        stmt.bindLong(16, entity.getEsPc());
        stmt.bindLong(17, entity.getTipoProcedencia());
        stmt.bindLong(18, entity.getAnio());
        stmt.bindString(19, entity.getIdVectores());
        stmt.bindLong(20, entity.getExtranjero());
        stmt.bindLong(21, entity.getSospecha());
        stmt.bindLong(22, entity.getIdLabLectura());
        stmt.bindString(23, entity.getDetalleLectura());
        stmt.bindLong(24, entity.getEstado_sync());
        stmt.bindLong(25, entity.getUnidadEdad());
        stmt.bindString(26, entity.getResponsable());
        stmt.bindLong(27, entity.getIdTablet());
        stmt.bindLong(28, entity.getIdSibasi());
        stmt.bindLong(29, entity.getIdCaserio());
        stmt.bindLong(30, entity.getIdUsuarioReg());
        stmt.bindLong(31, entity.getIdClave());
        stmt.bindLong(32, entity.getIdPais());
        stmt.bindLong(33, entity.getIdResultado());
    }

    @Override
    protected final void attachEntity(PlGotaGruesa entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public PlGotaGruesa readEntity(Cursor cursor, int offset) {
        PlGotaGruesa entity = new PlGotaGruesa( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getInt(offset + 1), // idE6
                cursor.getInt(offset + 2), // idSemanaEpidemiologica
                cursor.getInt(offset + 3), // idSexo
                cursor.getInt(offset + 4), // idEstado
                cursor.getInt(offset + 5), // idUsuarioMod
                cursor.getInt(offset + 6), // busquedaActiva
                cursor.getString(offset + 7), // nombre
                cursor.getInt(offset + 8), // edad
                cursor.getString(offset + 9), // direccion
                cursor.getString(offset + 10), // fechaFiebre
                cursor.getString(offset + 11), // fechaToma
                cursor.getString(offset + 12), // fechaHoraReg
                cursor.getString(offset + 13), // fechaHoraMod
                cursor.getString(offset + 14), // fecha
                cursor.getInt(offset + 15), // esPc
                cursor.getInt(offset + 16), // tipoProcedencia
                cursor.getInt(offset + 17), // anio
                cursor.getString(offset + 18), // idVectores
                cursor.getInt(offset + 19), // extranjero
                cursor.getInt(offset + 20), // sospecha
                cursor.getInt(offset + 21), // idLabLectura
                cursor.getString(offset + 22), // detalleLectura
                cursor.getInt(offset + 23), // estado_sync
                cursor.getInt(offset + 24), // unidadEdad
                cursor.getString(offset + 25), // responsable
                cursor.getLong(offset + 26), // idTablet
                cursor.getLong(offset + 27), // idSibasi
                cursor.getLong(offset + 28), // idCaserio
                cursor.getLong(offset + 29), // idUsuarioReg
                cursor.getLong(offset + 30), // idClave
                cursor.getLong(offset + 31), // idPais
                cursor.getLong(offset + 32) // idResultado
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, PlGotaGruesa entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdE6(cursor.getInt(offset + 1));
        entity.setIdSemanaEpidemiologica(cursor.getInt(offset + 2));
        entity.setIdSexo(cursor.getInt(offset + 3));
        entity.setIdEstado(cursor.getInt(offset + 4));
        entity.setIdUsuarioMod(cursor.getInt(offset + 5));
        entity.setBusquedaActiva(cursor.getInt(offset + 6));
        entity.setNombre(cursor.getString(offset + 7));
        entity.setEdad(cursor.getInt(offset + 8));
        entity.setDireccion(cursor.getString(offset + 9));
        entity.setFechaFiebre(cursor.getString(offset + 10));
        entity.setFechaToma(cursor.getString(offset + 11));
        entity.setFechaHoraReg(cursor.getString(offset + 12));
        entity.setFechaHoraMod(cursor.getString(offset + 13));
        entity.setFecha(cursor.getString(offset + 14));
        entity.setEsPc(cursor.getInt(offset + 15));
        entity.setTipoProcedencia(cursor.getInt(offset + 16));
        entity.setAnio(cursor.getInt(offset + 17));
        entity.setIdVectores(cursor.getString(offset + 18));
        entity.setExtranjero(cursor.getInt(offset + 19));
        entity.setSospecha(cursor.getInt(offset + 20));
        entity.setIdLabLectura(cursor.getInt(offset + 21));
        entity.setDetalleLectura(cursor.getString(offset + 22));
        entity.setEstado_sync(cursor.getInt(offset + 23));
        entity.setUnidadEdad(cursor.getInt(offset + 24));
        entity.setResponsable(cursor.getString(offset + 25));
        entity.setIdTablet(cursor.getLong(offset + 26));
        entity.setIdSibasi(cursor.getLong(offset + 27));
        entity.setIdCaserio(cursor.getLong(offset + 28));
        entity.setIdUsuarioReg(cursor.getLong(offset + 29));
        entity.setIdClave(cursor.getLong(offset + 30));
        entity.setIdPais(cursor.getLong(offset + 31));
        entity.setIdResultado(cursor.getLong(offset + 32));
    }

    @Override
    protected final Long updateKeyAfterInsert(PlGotaGruesa entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(PlGotaGruesa entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PlGotaGruesa entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCtlPaisDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getCtlTabletDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getCtlEstablecimientoDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getCtlCaserioDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T4", daoSession.getFosUserUserDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T5", daoSession.getClaveDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T6", daoSession.getResultadoGotaDao().getAllColumns());
            builder.append(" FROM PL_GOTA_GRUESA T");
            builder.append(" LEFT JOIN CTL_PAIS T0 ON T.\"ID_PAIS\"=T0.\"ID\"");
            builder.append(" LEFT JOIN CTL_TABLET T1 ON T.\"ID_TABLET\"=T1.\"ID\"");
            builder.append(" LEFT JOIN CTL_ESTABLECIMIENTO T2 ON T.\"ID_SIBASI\"=T2.\"ID\"");
            builder.append(" LEFT JOIN CTL_CASERIO T3 ON T.\"ID_CASERIO\"=T3.\"ID\"");
            builder.append(" LEFT JOIN FOS_USER_USER T4 ON T.\"ID_USUARIO_REG\"=T4.\"ID\"");
            builder.append(" LEFT JOIN CLAVE T5 ON T.\"ID_CLAVE\"=T5.\"ID\"");
            builder.append(" LEFT JOIN RESULTADO_GOTA T6 ON T.\"ID_RESULTADO\"=T6.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected PlGotaGruesa loadCurrentDeep(Cursor cursor, boolean lock) {
        PlGotaGruesa entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        CtlPais ctlPais = loadCurrentOther(daoSession.getCtlPaisDao(), cursor, offset);
        if(ctlPais != null) {
            entity.setCtlPais(ctlPais);
        }
        offset += daoSession.getCtlPaisDao().getAllColumns().length;

        CtlTablet ctlTablet = loadCurrentOther(daoSession.getCtlTabletDao(), cursor, offset);
        if(ctlTablet != null) {
            entity.setCtlTablet(ctlTablet);
        }
        offset += daoSession.getCtlTabletDao().getAllColumns().length;

        CtlEstablecimiento ctlEstablecimiento = loadCurrentOther(daoSession.getCtlEstablecimientoDao(), cursor, offset);
        if(ctlEstablecimiento != null) {
            entity.setCtlEstablecimiento(ctlEstablecimiento);
        }
        offset += daoSession.getCtlEstablecimientoDao().getAllColumns().length;

        CtlCaserio ctlCaserio = loadCurrentOther(daoSession.getCtlCaserioDao(), cursor, offset);
        if(ctlCaserio != null) {
            entity.setCtlCaserio(ctlCaserio);
        }
        offset += daoSession.getCtlCaserioDao().getAllColumns().length;

        FosUserUser fosUserUser = loadCurrentOther(daoSession.getFosUserUserDao(), cursor, offset);
        if(fosUserUser != null) {
            entity.setFosUserUser(fosUserUser);
        }
        offset += daoSession.getFosUserUserDao().getAllColumns().length;

        Clave clave = loadCurrentOther(daoSession.getClaveDao(), cursor, offset);
        if(clave != null) {
            entity.setClave(clave);
        }
        offset += daoSession.getClaveDao().getAllColumns().length;

        ResultadoGota resultadoGota = loadCurrentOther(daoSession.getResultadoGotaDao(), cursor, offset);
        if(resultadoGota != null) {
            entity.setResultadoGota(resultadoGota);
        }

        return entity;
    }

    public PlGotaGruesa loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<PlGotaGruesa> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<PlGotaGruesa> list = new ArrayList<PlGotaGruesa>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    protected List<PlGotaGruesa> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<PlGotaGruesa> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

}
