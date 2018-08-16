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
 * DAO for table "CTL_MUNICIPIO".
*/
public class CtlMunicipioDao extends AbstractDao<CtlMunicipio, Long> {

    public static final String TABLENAME = "CTL_MUNICIPIO";

    /**
     * Properties of entity CtlMunicipio.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Nombre = new Property(1, String.class, "nombre", false, "NOMBRE");
        public final static Property IdDeptoApoyo = new Property(2, Long.class, "idDeptoApoyo", false, "ID_DEPTO_APOYO");
        public final static Property IdDepartamento = new Property(3, long.class, "idDepartamento", false, "ID_DEPARTAMENTO");
    }

    private DaoSession daoSession;


    public CtlMunicipioDao(DaoConfig config) {
        super(config);
    }
    
    public CtlMunicipioDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CTL_MUNICIPIO\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NOMBRE\" TEXT," + // 1: nombre
                "\"ID_DEPTO_APOYO\" INTEGER," + // 2: idDeptoApoyo
                "\"ID_DEPARTAMENTO\" INTEGER NOT NULL );"); // 3: idDepartamento
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CTL_MUNICIPIO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CtlMunicipio entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(2, nombre);
        }
 
        Long idDeptoApoyo = entity.getIdDeptoApoyo();
        if (idDeptoApoyo != null) {
            stmt.bindLong(3, idDeptoApoyo);
        }
        stmt.bindLong(4, entity.getIdDepartamento());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CtlMunicipio entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(2, nombre);
        }
 
        Long idDeptoApoyo = entity.getIdDeptoApoyo();
        if (idDeptoApoyo != null) {
            stmt.bindLong(3, idDeptoApoyo);
        }
        stmt.bindLong(4, entity.getIdDepartamento());
    }

    @Override
    protected final void attachEntity(CtlMunicipio entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CtlMunicipio readEntity(Cursor cursor, int offset) {
        CtlMunicipio entity = new CtlMunicipio( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nombre
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // idDeptoApoyo
            cursor.getLong(offset + 3) // idDepartamento
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CtlMunicipio entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNombre(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIdDeptoApoyo(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setIdDepartamento(cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CtlMunicipio entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CtlMunicipio entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CtlMunicipio entity) {
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getCtlDepartamentoDao().getAllColumns());
            builder.append(" FROM CTL_MUNICIPIO T");
            builder.append(" LEFT JOIN CTL_DEPARTAMENTO T0 ON T.\"ID_DEPARTAMENTO\"=T0.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected CtlMunicipio loadCurrentDeep(Cursor cursor, boolean lock) {
        CtlMunicipio entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        CtlDepartamento ctlDepartamento = loadCurrentOther(daoSession.getCtlDepartamentoDao(), cursor, offset);
         if(ctlDepartamento != null) {
            entity.setCtlDepartamento(ctlDepartamento);
        }

        return entity;    
    }

    public CtlMunicipio loadDeep(Long key) {
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
    public List<CtlMunicipio> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<CtlMunicipio> list = new ArrayList<CtlMunicipio>(count);
        
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
    
    protected List<CtlMunicipio> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<CtlMunicipio> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
