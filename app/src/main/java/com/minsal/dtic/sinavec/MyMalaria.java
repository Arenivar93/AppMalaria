package com.minsal.dtic.sinavec;

import android.app.Application;
import android.database.Cursor;
import android.os.SystemClock;
import android.util.Log;

import com.minsal.dtic.sinavec.EntityDAO.DaoMaster;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.File;

import HelperDB.DbHelpers;

//esta clase la usamos para que al cargar tarde dos segundos en el splash
public class MyMalaria extends Application{

    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;
    public String eluser;


    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"malaria");
        Database db=helper.getWritableDb();
        db.execSQL("PRAGMA foreign_keys=ON;");
        daoSession = new DaoMaster(db).newSession();

        SystemClock.sleep(2000);
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }




}
