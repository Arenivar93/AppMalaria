package com.minsal.dtic.sinavec.utilidades;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

import java.io.File;

public class MetodosGlobales{
    private static String DB_PATH="/data/data/com.minsal.dtic.sinavec/databases/";
    private static  String DB_NAME="malaria";

    private DaoSession daoSession;
    public String eluser;

    public MetodosGlobales(DaoSession daoSession) {
        this.daoSession = daoSession;
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

    public boolean validateLogin(String elUser,String elPass){
        this.eluser = elUser;
        boolean existe = false;
        String sqlQUERY="select username from fos_user_user where username='"+elUser+"'";

        Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY,null);
        boolean exixte =false;

        if (cursor.moveToFirst())
        {
            existe=true;
        }else{
            existe= false;
        }
        //daoSession.getDatabase().close();
        Log.i("usuario", String.valueOf(existe));
        return existe;
    }
}