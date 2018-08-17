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
 * DAO for table "BITACORA".
*/
public class BitacoraDao extends AbstractDao<Bitacora, Long> {

    public static final String TABLENAME = "BITACORA";

    /**
     * Properties of entity Bitacora.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Operacion = new Property(1, String.class, "operacion", false, "OPERACION");
        public final static Property SentenciaSql = new Property(2, String.class, "sentenciaSql", false, "SENTENCIA_SQL");
        public final static Property NombreTabla = new Property(3, String.class, "nombreTabla", false, "NOMBRE_TABLA");
        public final static Property BanderaEstado = new Property(4, String.class, "banderaEstado", false, "BANDERA_ESTADO");
        public final static Property IdTablet = new Property(5, long.class, "idTablet", false, "ID_TABLET");
    }

    private DaoSession daoSession;


    public BitacoraDao(DaoConfig config) {
        super(config);
    }
    
    public BitacoraDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BITACORA\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"OPERACION\" TEXT," + // 1: operacion
                "\"SENTENCIA_SQL\" TEXT," + // 2: sentenciaSql
                "\"NOMBRE_TABLA\" TEXT," + // 3: nombreTabla
                "\"BANDERA_ESTADO\" TEXT," + // 4: banderaEstado
                "\"ID_TABLET\" INTEGER NOT NULL ,"+"FOREIGN KEY(\"ID_TABLET\")" +
                " REFERENCES CTL_TABLET(\"ID\") ON DELETE CASCADE );"); // 5: idTablet
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BITACORA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Bitacora entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String operacion = entity.getOperacion();
        if (operacion != null) {
            stmt.bindString(2, operacion);
        }
 
        String sentenciaSql = entity.getSentenciaSql();
        if (sentenciaSql != null) {
            stmt.bindString(3, sentenciaSql);
        }
 
        String nombreTabla = entity.getNombreTabla();
        if (nombreTabla != null) {
            stmt.bindString(4, nombreTabla);
        }
 
        String banderaEstado = entity.getBanderaEstado();
        if (banderaEstado != null) {
            stmt.bindString(5, banderaEstado);
        }
        stmt.bindLong(6, entity.getIdTablet());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Bitacora entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String operacion = entity.getOperacion();
        if (operacion != null) {
            stmt.bindString(2, operacion);
        }
 
        String sentenciaSql = entity.getSentenciaSql();
        if (sentenciaSql != null) {
            stmt.bindString(3, sentenciaSql);
        }
 
        String nombreTabla = entity.getNombreTabla();
        if (nombreTabla != null) {
            stmt.bindString(4, nombreTabla);
        }
 
        String banderaEstado = entity.getBanderaEstado();
        if (banderaEstado != null) {
            stmt.bindString(5, banderaEstado);
        }
        stmt.bindLong(6, entity.getIdTablet());
    }

    @Override
    protected final void attachEntity(Bitacora entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Bitacora readEntity(Cursor cursor, int offset) {
        Bitacora entity = new Bitacora( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // operacion
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sentenciaSql
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nombreTabla
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // banderaEstado
            cursor.getLong(offset + 5) // idTablet
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Bitacora entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOperacion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSentenciaSql(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNombreTabla(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBanderaEstado(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIdTablet(cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Bitacora entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Bitacora entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Bitacora entity) {
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
            builder.append(" FROM BITACORA T");
            builder.append(" LEFT JOIN CTL_TABLET T0 ON T.\"ID_TABLET\"=T0.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Bitacora loadCurrentDeep(Cursor cursor, boolean lock) {
        Bitacora entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        CtlTablet ctlTablet = loadCurrentOther(daoSession.getCtlTabletDao(), cursor, offset);
         if(ctlTablet != null) {
            entity.setCtlTablet(ctlTablet);
        }

        return entity;    
    }

    public Bitacora loadDeep(Long key) {
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
    public List<Bitacora> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Bitacora> list = new ArrayList<Bitacora>(count);
        
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
    
    protected List<Bitacora> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Bitacora> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}