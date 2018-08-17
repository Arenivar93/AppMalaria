package com.minsal.dtic.sinavec.EntityDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 1): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        CtlProcedenciaDao.createTable(db, ifNotExists);
        ClaveDao.createTable(db, ifNotExists);
        CtlPaisDao.createTable(db, ifNotExists);
        CtlDepartamentoDao.createTable(db, ifNotExists);
        CtlMunicipioDao.createTable(db, ifNotExists);
        CtlCantonDao.createTable(db, ifNotExists);
        CtlCaserioDao.createTable(db, ifNotExists);
        PlTipoCapturaDao.createTable(db, ifNotExists);
        PlTipoActividadDao.createTable(db, ifNotExists);
        CtlInstitucionDao.createTable(db, ifNotExists);
        CtlTipoEstablecimientoDao.createTable(db, ifNotExists);
        CtlEstablecimientoDao.createTable(db, ifNotExists);
        FosUserUserDao.createTable(db, ifNotExists);
        PlColvolDao.createTable(db, ifNotExists);
        ColvolCalveDao.createTable(db, ifNotExists);
        EstablecimientoClaveDao.createTable(db, ifNotExists);
        PromotorClaveDao.createTable(db, ifNotExists);
        CtlSemanaEpiDao.createTable(db, ifNotExists);
        CtlTabletDao.createTable(db, ifNotExists);
        BitacoraDao.createTable(db, ifNotExists);
        CtlPlCriaderoDao.createTable(db, ifNotExists);
        CtlPlCriaderoTmpDao.createTable(db, ifNotExists);
        PlCapturaAnophelesDao.createTable(db, ifNotExists);
        PlSeguimientoBotiquinDao.createTable(db, ifNotExists);
        PlPesquisaLarvariaDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        CtlProcedenciaDao.dropTable(db, ifExists);
        ClaveDao.dropTable(db, ifExists);
        CtlPaisDao.dropTable(db, ifExists);
        CtlDepartamentoDao.dropTable(db, ifExists);
        CtlMunicipioDao.dropTable(db, ifExists);
        CtlCantonDao.dropTable(db, ifExists);
        CtlCaserioDao.dropTable(db, ifExists);
        PlTipoCapturaDao.dropTable(db, ifExists);
        PlTipoActividadDao.dropTable(db, ifExists);
        CtlInstitucionDao.dropTable(db, ifExists);
        CtlTipoEstablecimientoDao.dropTable(db, ifExists);
        CtlEstablecimientoDao.dropTable(db, ifExists);
        FosUserUserDao.dropTable(db, ifExists);
        PlColvolDao.dropTable(db, ifExists);
        ColvolCalveDao.dropTable(db, ifExists);
        EstablecimientoClaveDao.dropTable(db, ifExists);
        PromotorClaveDao.dropTable(db, ifExists);
        CtlSemanaEpiDao.dropTable(db, ifExists);
        CtlTabletDao.dropTable(db, ifExists);
        BitacoraDao.dropTable(db, ifExists);
        CtlPlCriaderoDao.dropTable(db, ifExists);
        CtlPlCriaderoTmpDao.dropTable(db, ifExists);
        PlCapturaAnophelesDao.dropTable(db, ifExists);
        PlSeguimientoBotiquinDao.dropTable(db, ifExists);
        PlPesquisaLarvariaDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CtlProcedenciaDao.class);
        registerDaoClass(ClaveDao.class);
        registerDaoClass(CtlPaisDao.class);
        registerDaoClass(CtlDepartamentoDao.class);
        registerDaoClass(CtlMunicipioDao.class);
        registerDaoClass(CtlCantonDao.class);
        registerDaoClass(CtlCaserioDao.class);
        registerDaoClass(PlTipoCapturaDao.class);
        registerDaoClass(PlTipoActividadDao.class);
        registerDaoClass(CtlInstitucionDao.class);
        registerDaoClass(CtlTipoEstablecimientoDao.class);
        registerDaoClass(CtlEstablecimientoDao.class);
        registerDaoClass(FosUserUserDao.class);
        registerDaoClass(PlColvolDao.class);
        registerDaoClass(ColvolCalveDao.class);
        registerDaoClass(EstablecimientoClaveDao.class);
        registerDaoClass(PromotorClaveDao.class);
        registerDaoClass(CtlSemanaEpiDao.class);
        registerDaoClass(CtlTabletDao.class);
        registerDaoClass(BitacoraDao.class);
        registerDaoClass(CtlPlCriaderoDao.class);
        registerDaoClass(CtlPlCriaderoTmpDao.class);
        registerDaoClass(PlCapturaAnophelesDao.class);
        registerDaoClass(PlSeguimientoBotiquinDao.class);
        registerDaoClass(PlPesquisaLarvariaDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
