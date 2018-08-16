package com.minsal.dtic.sinavec;

import android.app.Application;
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
    private static String DB_PATH="/data/data/com.minsal.dtic.sinavec/databases/";
    private static  String DB_NAME="malaria";


    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"malaria");
        Database db=helper.getWritableDb();
        db.execSQL("PRAGMA foreign_keys=ON;");
        daoSession = new DaoMaster(db).newSession();

        boolean dbExist = checkDataBase();
        if (dbExist==true) {


        }else{

        }


        SystemClock.sleep(2000);
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    //este metodo verifica si existe la base de datos
    public boolean checkDataBase() {
        boolean existe= false;
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        existe=file.exists();
        //si esxiste retorna true
        return  existe;
    }
}
