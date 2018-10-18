package com.minsal.dtic.sinavec.EntityDAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RESULTADO_GOTA".
*/
public class ResultadoGotaDao extends AbstractDao<ResultadoGota, Long> {

    public static final String TABLENAME = "RESULTADO_GOTA";

    /**
     * Properties of entity ResultadoGota.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Resultado = new Property(1, String.class, "resultado", false, "RESULTADO");
        public final static Property Codigo = new Property(2, String.class, "codigo", false, "CODIGO");
    }


    public ResultadoGotaDao(DaoConfig config) {
        super(config);
    }
    
    public ResultadoGotaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RESULTADO_GOTA\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"RESULTADO\" TEXT," + // 1: resultado
                "\"CODIGO\" TEXT);"); // 2: codigo
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RESULTADO_GOTA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ResultadoGota entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String resultado = entity.getResultado();
        if (resultado != null) {
            stmt.bindString(2, resultado);
        }
 
        String codigo = entity.getCodigo();
        if (codigo != null) {
            stmt.bindString(3, codigo);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ResultadoGota entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String resultado = entity.getResultado();
        if (resultado != null) {
            stmt.bindString(2, resultado);
        }
 
        String codigo = entity.getCodigo();
        if (codigo != null) {
            stmt.bindString(3, codigo);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ResultadoGota readEntity(Cursor cursor, int offset) {
        ResultadoGota entity = new ResultadoGota( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // resultado
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // codigo
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ResultadoGota entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setResultado(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCodigo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ResultadoGota entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ResultadoGota entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ResultadoGota entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
