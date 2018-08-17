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
 * DAO for table "PL_PESQUISA_LARVARIA".
*/
public class PlPesquisaLarvariaDao extends AbstractDao<PlPesquisaLarvaria, Long> {

    public static final String TABLENAME = "PL_PESQUISA_LARVARIA";

    /**
     * Properties of entity PlPesquisaLarvaria.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property IdSemanaEpidemiologica = new Property(1, int.class, "idSemanaEpidemiologica", false, "ID_SEMANA_EPIDEMIOLOGICA");
        public final static Property IdUsuarioMod = new Property(2, int.class, "idUsuarioMod", false, "ID_USUARIO_MOD");
        public final static Property IdEstado = new Property(3, int.class, "idEstado", false, "ID_ESTADO");
        public final static Property FechaHoraReg = new Property(4, java.util.Date.class, "fechaHoraReg", false, "FECHA_HORA_REG");
        public final static Property FechaHoraMod = new Property(5, java.util.Date.class, "fechaHoraMod", false, "FECHA_HORA_MOD");
        public final static Property IndiceLarvario = new Property(6, float.class, "indiceLarvario", false, "INDICE_LARVARIO");
        public final static Property AnophelesDos = new Property(7, int.class, "anophelesDos", false, "ANOPHELES_DOS");
        public final static Property AnophelesUno = new Property(8, int.class, "anophelesUno", false, "ANOPHELES_UNO");
        public final static Property CulicinosUno = new Property(9, int.class, "culicinosUno", false, "CULICINOS_UNO");
        public final static Property CulicinosDos = new Property(10, int.class, "culicinosDos", false, "CULICINOS_DOS");
        public final static Property Pupa = new Property(11, int.class, "pupa", false, "PUPA");
        public final static Property NumeroCucharonada = new Property(12, int.class, "numeroCucharonada", false, "NUMERO_CUCHARONADA");
        public final static Property Ancho = new Property(13, float.class, "ancho", false, "ANCHO");
        public final static Property Largo = new Property(14, float.class, "largo", false, "LARGO");
        public final static Property Fecha = new Property(15, java.util.Date.class, "fecha", false, "FECHA");
        public final static Property IdTablet = new Property(16, long.class, "idTablet", false, "ID_TABLET");
        public final static Property IdSibasi = new Property(17, long.class, "idSibasi", false, "ID_SIBASI");
        public final static Property IdCriadero = new Property(18, long.class, "idCriadero", false, "ID_CRIADERO");
        public final static Property IdCaserio = new Property(19, long.class, "idCaserio", false, "ID_CASERIO");
        public final static Property IdUsuarioReg = new Property(20, long.class, "idUsuarioReg", false, "ID_USUARIO_REG");
    }

    private DaoSession daoSession;


    public PlPesquisaLarvariaDao(DaoConfig config) {
        super(config);
    }
    
    public PlPesquisaLarvariaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PL_PESQUISA_LARVARIA\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ID_SEMANA_EPIDEMIOLOGICA\" INTEGER NOT NULL ," + // 1: idSemanaEpidemiologica
                "\"ID_USUARIO_MOD\" INTEGER NOT NULL ," + // 2: idUsuarioMod
                "\"ID_ESTADO\" INTEGER NOT NULL ," + // 3: idEstado
                "\"FECHA_HORA_REG\" INTEGER NOT NULL ," + // 4: fechaHoraReg
                "\"FECHA_HORA_MOD\" INTEGER NOT NULL ," + // 5: fechaHoraMod
                "\"INDICE_LARVARIO\" REAL NOT NULL ," + // 6: indiceLarvario
                "\"ANOPHELES_DOS\" INTEGER NOT NULL ," + // 7: anophelesDos
                "\"ANOPHELES_UNO\" INTEGER NOT NULL ," + // 8: anophelesUno
                "\"CULICINOS_UNO\" INTEGER NOT NULL ," + // 9: culicinosUno
                "\"CULICINOS_DOS\" INTEGER NOT NULL ," + // 10: culicinosDos
                "\"PUPA\" INTEGER NOT NULL ," + // 11: pupa
                "\"NUMERO_CUCHARONADA\" INTEGER NOT NULL ," + // 12: numeroCucharonada
                "\"ANCHO\" REAL NOT NULL ," + // 13: ancho
                "\"LARGO\" REAL NOT NULL ," + // 14: largo
                "\"FECHA\" INTEGER NOT NULL ," + // 15: fecha
                "\"ID_TABLET\" INTEGER NOT NULL ," + // 16: idTablet
                "\"ID_SIBASI\" INTEGER NOT NULL ," + // 17: idSibasi
                "\"ID_CRIADERO\" INTEGER NOT NULL ," + // 18: idCriadero
                "\"ID_CASERIO\" INTEGER NOT NULL ," + // 19: idCaserio
                "\"ID_USUARIO_REG\" INTEGER NOT NULL ,"+"FOREIGN KEY(\"ID_TABLET\")" +
                " REFERENCES CTL_TABLET(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_SIBASI\")" +
                " REFERENCES CTL_ESTABLECIMIENTO(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_CRIADERO\")" +
                " REFERENCES CTL_PL_CRIADERO(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_CASERIO\")" +
                " REFERENCES CTL_CASERIO(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_USUARIO_REG\")" +
                " REFERENCES FOS_USER_USER(\"ID\") ON DELETE CASCADE );"); // 20: idUsuarioReg
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PL_PESQUISA_LARVARIA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PlPesquisaLarvaria entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIdSemanaEpidemiologica());
        stmt.bindLong(3, entity.getIdUsuarioMod());
        stmt.bindLong(4, entity.getIdEstado());
        stmt.bindLong(5, entity.getFechaHoraReg().getTime());
        stmt.bindLong(6, entity.getFechaHoraMod().getTime());
        stmt.bindDouble(7, entity.getIndiceLarvario());
        stmt.bindLong(8, entity.getAnophelesDos());
        stmt.bindLong(9, entity.getAnophelesUno());
        stmt.bindLong(10, entity.getCulicinosUno());
        stmt.bindLong(11, entity.getCulicinosDos());
        stmt.bindLong(12, entity.getPupa());
        stmt.bindLong(13, entity.getNumeroCucharonada());
        stmt.bindDouble(14, entity.getAncho());
        stmt.bindDouble(15, entity.getLargo());
        stmt.bindLong(16, entity.getFecha().getTime());
        stmt.bindLong(17, entity.getIdTablet());
        stmt.bindLong(18, entity.getIdSibasi());
        stmt.bindLong(19, entity.getIdCriadero());
        stmt.bindLong(20, entity.getIdCaserio());
        stmt.bindLong(21, entity.getIdUsuarioReg());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PlPesquisaLarvaria entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIdSemanaEpidemiologica());
        stmt.bindLong(3, entity.getIdUsuarioMod());
        stmt.bindLong(4, entity.getIdEstado());
        stmt.bindLong(5, entity.getFechaHoraReg().getTime());
        stmt.bindLong(6, entity.getFechaHoraMod().getTime());
        stmt.bindDouble(7, entity.getIndiceLarvario());
        stmt.bindLong(8, entity.getAnophelesDos());
        stmt.bindLong(9, entity.getAnophelesUno());
        stmt.bindLong(10, entity.getCulicinosUno());
        stmt.bindLong(11, entity.getCulicinosDos());
        stmt.bindLong(12, entity.getPupa());
        stmt.bindLong(13, entity.getNumeroCucharonada());
        stmt.bindDouble(14, entity.getAncho());
        stmt.bindDouble(15, entity.getLargo());
        stmt.bindLong(16, entity.getFecha().getTime());
        stmt.bindLong(17, entity.getIdTablet());
        stmt.bindLong(18, entity.getIdSibasi());
        stmt.bindLong(19, entity.getIdCriadero());
        stmt.bindLong(20, entity.getIdCaserio());
        stmt.bindLong(21, entity.getIdUsuarioReg());
    }

    @Override
    protected final void attachEntity(PlPesquisaLarvaria entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PlPesquisaLarvaria readEntity(Cursor cursor, int offset) {
        PlPesquisaLarvaria entity = new PlPesquisaLarvaria( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // idSemanaEpidemiologica
            cursor.getInt(offset + 2), // idUsuarioMod
            cursor.getInt(offset + 3), // idEstado
            new java.util.Date(cursor.getLong(offset + 4)), // fechaHoraReg
            new java.util.Date(cursor.getLong(offset + 5)), // fechaHoraMod
            cursor.getFloat(offset + 6), // indiceLarvario
            cursor.getInt(offset + 7), // anophelesDos
            cursor.getInt(offset + 8), // anophelesUno
            cursor.getInt(offset + 9), // culicinosUno
            cursor.getInt(offset + 10), // culicinosDos
            cursor.getInt(offset + 11), // pupa
            cursor.getInt(offset + 12), // numeroCucharonada
            cursor.getFloat(offset + 13), // ancho
            cursor.getFloat(offset + 14), // largo
            new java.util.Date(cursor.getLong(offset + 15)), // fecha
            cursor.getLong(offset + 16), // idTablet
            cursor.getLong(offset + 17), // idSibasi
            cursor.getLong(offset + 18), // idCriadero
            cursor.getLong(offset + 19), // idCaserio
            cursor.getLong(offset + 20) // idUsuarioReg
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PlPesquisaLarvaria entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdSemanaEpidemiologica(cursor.getInt(offset + 1));
        entity.setIdUsuarioMod(cursor.getInt(offset + 2));
        entity.setIdEstado(cursor.getInt(offset + 3));
        entity.setFechaHoraReg(new java.util.Date(cursor.getLong(offset + 4)));
        entity.setFechaHoraMod(new java.util.Date(cursor.getLong(offset + 5)));
        entity.setIndiceLarvario(cursor.getFloat(offset + 6));
        entity.setAnophelesDos(cursor.getInt(offset + 7));
        entity.setAnophelesUno(cursor.getInt(offset + 8));
        entity.setCulicinosUno(cursor.getInt(offset + 9));
        entity.setCulicinosDos(cursor.getInt(offset + 10));
        entity.setPupa(cursor.getInt(offset + 11));
        entity.setNumeroCucharonada(cursor.getInt(offset + 12));
        entity.setAncho(cursor.getFloat(offset + 13));
        entity.setLargo(cursor.getFloat(offset + 14));
        entity.setFecha(new java.util.Date(cursor.getLong(offset + 15)));
        entity.setIdTablet(cursor.getLong(offset + 16));
        entity.setIdSibasi(cursor.getLong(offset + 17));
        entity.setIdCriadero(cursor.getLong(offset + 18));
        entity.setIdCaserio(cursor.getLong(offset + 19));
        entity.setIdUsuarioReg(cursor.getLong(offset + 20));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PlPesquisaLarvaria entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PlPesquisaLarvaria entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PlPesquisaLarvaria entity) {
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getCtlTabletDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getCtlEstablecimientoDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getCtlPlCriaderoDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getCtlCaserioDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T4", daoSession.getFosUserUserDao().getAllColumns());
            builder.append(" FROM PL_PESQUISA_LARVARIA T");
            builder.append(" LEFT JOIN CTL_TABLET T0 ON T.\"ID_TABLET\"=T0.\"ID\"");
            builder.append(" LEFT JOIN CTL_ESTABLECIMIENTO T1 ON T.\"ID_SIBASI\"=T1.\"ID\"");
            builder.append(" LEFT JOIN CTL_PL_CRIADERO T2 ON T.\"ID_CRIADERO\"=T2.\"ID\"");
            builder.append(" LEFT JOIN CTL_CASERIO T3 ON T.\"ID_CASERIO\"=T3.\"ID\"");
            builder.append(" LEFT JOIN FOS_USER_USER T4 ON T.\"ID_USUARIO_REG\"=T4.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected PlPesquisaLarvaria loadCurrentDeep(Cursor cursor, boolean lock) {
        PlPesquisaLarvaria entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

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

        CtlPlCriadero ctlPlCriadero = loadCurrentOther(daoSession.getCtlPlCriaderoDao(), cursor, offset);
         if(ctlPlCriadero != null) {
            entity.setCtlPlCriadero(ctlPlCriadero);
        }
        offset += daoSession.getCtlPlCriaderoDao().getAllColumns().length;

        CtlCaserio ctlCaserio = loadCurrentOther(daoSession.getCtlCaserioDao(), cursor, offset);
         if(ctlCaserio != null) {
            entity.setCtlCaserio(ctlCaserio);
        }
        offset += daoSession.getCtlCaserioDao().getAllColumns().length;

        FosUserUser fosUserUser = loadCurrentOther(daoSession.getFosUserUserDao(), cursor, offset);
         if(fosUserUser != null) {
            entity.setFosUserUser(fosUserUser);
        }

        return entity;    
    }

    public PlPesquisaLarvaria loadDeep(Long key) {
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
    public List<PlPesquisaLarvaria> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<PlPesquisaLarvaria> list = new ArrayList<PlPesquisaLarvaria>(count);
        
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
    
    protected List<PlPesquisaLarvaria> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<PlPesquisaLarvaria> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}