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
 * DAO for table "FOS_USER_USER".
*/
public class FosUserUserDao extends AbstractDao<FosUserUser, Long> {

    public static final String TABLENAME = "FOS_USER_USER";

    /**
     * Properties of entity FosUserUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property TipoEmpleado = new Property(3, Integer.class, "tipoEmpleado", false, "TIPO_EMPLEADO");
        public final static Property Firstname = new Property(4, String.class, "firstname", false, "FIRSTNAME");
        public final static Property Lastname = new Property(5, String.class, "lastname", false, "LASTNAME");
        public final static Property Salt = new Property(6, String.class, "salt", false, "SALT");
        public final static Property IdSibasi = new Property(7, long.class, "idSibasi", false, "ID_SIBASI");
    }

    private DaoSession daoSession;


    public FosUserUserDao(DaoConfig config) {
        super(config);
    }
    
    public FosUserUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FOS_USER_USER\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USERNAME\" TEXT," + // 1: username
                "\"PASSWORD\" TEXT," + // 2: password
                "\"TIPO_EMPLEADO\" INTEGER," + // 3: tipoEmpleado
                "\"FIRSTNAME\" TEXT," + // 4: firstname
                "\"LASTNAME\" TEXT," + // 5: lastname
                "\"SALT\" TEXT," + // 6: salt
                "\"ID_SIBASI\" INTEGER NOT NULL,"+"FOREIGN KEY(\"ID_SIBASI\")" +
                " REFERENCES CTL_ESTABLECIMIENTO(\"ID\") ON DELETE CASCADE );"); // 7: idSibasi
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FOS_USER_USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FosUserUser entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        Integer tipoEmpleado = entity.getTipoEmpleado();
        if (tipoEmpleado != null) {
            stmt.bindLong(4, tipoEmpleado);
        }
 
        String firstname = entity.getFirstname();
        if (firstname != null) {
            stmt.bindString(5, firstname);
        }
 
        String lastname = entity.getLastname();
        if (lastname != null) {
            stmt.bindString(6, lastname);
        }
 
        String salt = entity.getSalt();
        if (salt != null) {
            stmt.bindString(7, salt);
        }
        stmt.bindLong(8, entity.getIdSibasi());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FosUserUser entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        Integer tipoEmpleado = entity.getTipoEmpleado();
        if (tipoEmpleado != null) {
            stmt.bindLong(4, tipoEmpleado);
        }
 
        String firstname = entity.getFirstname();
        if (firstname != null) {
            stmt.bindString(5, firstname);
        }
 
        String lastname = entity.getLastname();
        if (lastname != null) {
            stmt.bindString(6, lastname);
        }
 
        String salt = entity.getSalt();
        if (salt != null) {
            stmt.bindString(7, salt);
        }
        stmt.bindLong(8, entity.getIdSibasi());
    }

    @Override
    protected final void attachEntity(FosUserUser entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FosUserUser readEntity(Cursor cursor, int offset) {
        FosUserUser entity = new FosUserUser( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // tipoEmpleado
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // firstname
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // lastname
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // salt
            cursor.getLong(offset + 7) // idSibasi
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FosUserUser entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTipoEmpleado(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setFirstname(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLastname(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSalt(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIdSibasi(cursor.getLong(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FosUserUser entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FosUserUser entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FosUserUser entity) {
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
            builder.append(" FROM FOS_USER_USER T");
            builder.append(" LEFT JOIN CTL_ESTABLECIMIENTO T0 ON T.\"ID_SIBASI\"=T0.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected FosUserUser loadCurrentDeep(Cursor cursor, boolean lock) {
        FosUserUser entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        CtlEstablecimiento ctlEstablecimiento = loadCurrentOther(daoSession.getCtlEstablecimientoDao(), cursor, offset);
         if(ctlEstablecimiento != null) {
            entity.setCtlEstablecimiento(ctlEstablecimiento);
        }

        return entity;    
    }

    public FosUserUser loadDeep(Long key) {
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
    public List<FosUserUser> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<FosUserUser> list = new ArrayList<FosUserUser>(count);
        
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
    
    protected List<FosUserUser> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<FosUserUser> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
