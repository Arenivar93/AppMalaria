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
 * DAO for table "PROMOTOR_CLAVE".
*/
public class PromotorClaveDao extends AbstractDao<PromotorClave, Long> {

    public static final String TABLENAME = "PROMOTOR_CLAVE";

    /**
     * Properties of entity PromotorClave.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property IdClave = new Property(1, long.class, "idClave", false, "ID_CLAVE");
        public final static Property IdEmpleado = new Property(2, long.class, "idEmpleado", false, "ID_EMPLEADO");
    }

    private DaoSession daoSession;


    public PromotorClaveDao(DaoConfig config) {
        super(config);
    }
    
    public PromotorClaveDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROMOTOR_CLAVE\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ID_CLAVE\" INTEGER NOT NULL ," + // 1: idClave
                "\"ID_EMPLEADO\" INTEGER NOT NULL ,"+"FOREIGN KEY(\"ID_CLAVE\")" +
                " REFERENCES CLAVE(\"ID\") ON DELETE CASCADE,"+"FOREIGN KEY(\"ID_EMPLEADO\")" +
                " REFERENCES FOS_USER_USER(\"ID\") ON DELETE CASCADE );"); // 2: idEmpleado
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROMOTOR_CLAVE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PromotorClave entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIdClave());
        stmt.bindLong(3, entity.getIdEmpleado());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PromotorClave entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIdClave());
        stmt.bindLong(3, entity.getIdEmpleado());
    }

    @Override
    protected final void attachEntity(PromotorClave entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PromotorClave readEntity(Cursor cursor, int offset) {
        PromotorClave entity = new PromotorClave( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // idClave
            cursor.getLong(offset + 2) // idEmpleado
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PromotorClave entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdClave(cursor.getLong(offset + 1));
        entity.setIdEmpleado(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PromotorClave entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PromotorClave entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PromotorClave entity) {
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getClaveDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getFosUserUserDao().getAllColumns());
            builder.append(" FROM PROMOTOR_CLAVE T");
            builder.append(" LEFT JOIN CLAVE T0 ON T.\"ID_CLAVE\"=T0.\"ID\"");
            builder.append(" LEFT JOIN FOS_USER_USER T1 ON T.\"ID_EMPLEADO\"=T1.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected PromotorClave loadCurrentDeep(Cursor cursor, boolean lock) {
        PromotorClave entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Clave clave = loadCurrentOther(daoSession.getClaveDao(), cursor, offset);
         if(clave != null) {
            entity.setClave(clave);
        }
        offset += daoSession.getClaveDao().getAllColumns().length;

        FosUserUser fosUserUser = loadCurrentOther(daoSession.getFosUserUserDao(), cursor, offset);
         if(fosUserUser != null) {
            entity.setFosUserUser(fosUserUser);
        }

        return entity;    
    }

    public PromotorClave loadDeep(Long key) {
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
    public List<PromotorClave> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<PromotorClave> list = new ArrayList<PromotorClave>(count);
        
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
    
    protected List<PromotorClave> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<PromotorClave> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
