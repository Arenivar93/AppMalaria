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
 * DAO for table "CTL_ESTABLECIMIENTO".
*/
public class CtlEstablecimientoDao extends AbstractDao<CtlEstablecimiento, Long> {

    public static final String TABLENAME = "CTL_ESTABLECIMIENTO";

    /**
     * Properties of entity CtlEstablecimiento.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Nombre = new Property(1, String.class, "nombre", false, "NOMBRE");
        public final static Property Latitud = new Property(2, String.class, "latitud", false, "LATITUD");
        public final static Property Longitud = new Property(3, String.class, "longitud", false, "LONGITUD");
        public final static Property IdMunicipio = new Property(4, long.class, "idMunicipio", false, "ID_MUNICIPIO");
        public final static Property IdTipoEstablecimiento = new Property(5, long.class, "idTipoEstablecimiento", false, "ID_TIPO_ESTABLECIMIENTO");
        public final static Property IdEstablecimientoPadre = new Property(6, long.class, "idEstablecimientoPadre", false, "ID_ESTABLECIMIENTO_PADRE");
    }

    private DaoSession daoSession;


    public CtlEstablecimientoDao(DaoConfig config) {
        super(config);
    }
    
    public CtlEstablecimientoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CTL_ESTABLECIMIENTO\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NOMBRE\" TEXT," + // 1: nombre
                "\"LATITUD\" TEXT," + // 2: latitud
                "\"LONGITUD\" TEXT," + // 3: longitud
                "\"ID_MUNICIPIO\" INTEGER NOT NULL ," + // 4: idMunicipio
                "\"ID_TIPO_ESTABLECIMIENTO\" INTEGER NOT NULL ," + // 5: idTipoEstablecimiento
                "\"ID_ESTABLECIMIENTO_PADRE\" INTEGER NOT NULL,"+"FOREIGN KEY(\"ID_TIPO_ESTABLECIMIENTO\")" +
                " REFERENCES CTL_TIPO_ESTABLECIMIENTO(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_ESTABLECIMIENTO_PADRE\")" +
                " REFERENCES CTL_ESTABLECIMIENTO(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_MUNICIPIO\")" +
                " REFERENCES CTL_MUNICIPIO(\"ID\") ON DELETE CASCADE );"); // 6: idEstablecimientoPadre
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CTL_ESTABLECIMIENTO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CtlEstablecimiento entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(2, nombre);
        }
 
        String latitud = entity.getLatitud();
        if (latitud != null) {
            stmt.bindString(3, latitud);
        }
 
        String longitud = entity.getLongitud();
        if (longitud != null) {
            stmt.bindString(4, longitud);
        }
        stmt.bindLong(5, entity.getIdMunicipio());
        stmt.bindLong(6, entity.getIdTipoEstablecimiento());
        stmt.bindLong(7, entity.getIdEstablecimientoPadre());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CtlEstablecimiento entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(2, nombre);
        }
 
        String latitud = entity.getLatitud();
        if (latitud != null) {
            stmt.bindString(3, latitud);
        }
 
        String longitud = entity.getLongitud();
        if (longitud != null) {
            stmt.bindString(4, longitud);
        }
        stmt.bindLong(5, entity.getIdMunicipio());
        stmt.bindLong(6, entity.getIdTipoEstablecimiento());
        stmt.bindLong(7, entity.getIdEstablecimientoPadre());
    }

    @Override
    protected final void attachEntity(CtlEstablecimiento entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CtlEstablecimiento readEntity(Cursor cursor, int offset) {
        CtlEstablecimiento entity = new CtlEstablecimiento( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nombre
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // latitud
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // longitud
            cursor.getLong(offset + 4), // idMunicipio
            cursor.getLong(offset + 5), // idTipoEstablecimiento
            cursor.getLong(offset + 6) // idEstablecimientoPadre
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CtlEstablecimiento entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNombre(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLatitud(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLongitud(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIdMunicipio(cursor.getLong(offset + 4));
        entity.setIdTipoEstablecimiento(cursor.getLong(offset + 5));
        entity.setIdEstablecimientoPadre(cursor.getLong(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CtlEstablecimiento entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CtlEstablecimiento entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CtlEstablecimiento entity) {
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getCtlEstablecimientoDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getCtlMunicipioDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getCtlTipoEstablecimientoDao().getAllColumns());
            builder.append(" FROM CTL_ESTABLECIMIENTO T");
            builder.append(" LEFT JOIN CTL_ESTABLECIMIENTO T0 ON T.\"ID_ESTABLECIMIENTO_PADRE\"=T0.\"ID\"");
            builder.append(" LEFT JOIN CTL_MUNICIPIO T1 ON T.\"ID_MUNICIPIO\"=T1.\"ID\"");
            builder.append(" LEFT JOIN CTL_TIPO_ESTABLECIMIENTO T2 ON T.\"ID_TIPO_ESTABLECIMIENTO\"=T2.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected CtlEstablecimiento loadCurrentDeep(Cursor cursor, boolean lock) {
        CtlEstablecimiento entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        CtlEstablecimiento ctlEstablecimiento = loadCurrentOther(daoSession.getCtlEstablecimientoDao(), cursor, offset);
         if(ctlEstablecimiento != null) {
            entity.setCtlEstablecimiento(ctlEstablecimiento);
        }
        offset += daoSession.getCtlEstablecimientoDao().getAllColumns().length;

        CtlMunicipio ctlMunicipio = loadCurrentOther(daoSession.getCtlMunicipioDao(), cursor, offset);
         if(ctlMunicipio != null) {
            entity.setCtlMunicipio(ctlMunicipio);
        }
        offset += daoSession.getCtlMunicipioDao().getAllColumns().length;

        CtlTipoEstablecimiento ctlTipoEstablecimiento = loadCurrentOther(daoSession.getCtlTipoEstablecimientoDao(), cursor, offset);
         if(ctlTipoEstablecimiento != null) {
            entity.setCtlTipoEstablecimiento(ctlTipoEstablecimiento);
        }

        return entity;    
    }

    public CtlEstablecimiento loadDeep(Long key) {
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
    public List<CtlEstablecimiento> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<CtlEstablecimiento> list = new ArrayList<CtlEstablecimiento>(count);
        
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
    
    protected List<CtlEstablecimiento> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<CtlEstablecimiento> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}