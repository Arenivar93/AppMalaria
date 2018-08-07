package com.example.administrador.sinavec.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrador.sinavec.CUsuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrador on 02-06-18.
 */

public class OpenHelperBd extends SQLiteOpenHelper {


    String sqlCreateTablePersona = "Create Table persona (id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "nombre TEXT, direccion TEXT)";

    String sqlCreateMascota="create table mascota (id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "id_persona INTEGER,nombre TEXT,"
            + " FOREIGN KEY(id_persona) REFERENCES persona(id))";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "malaria2.db";

    public OpenHelperBd(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Método ejecutado automáticamente si la base de datos no existe
            db.execSQL(sqlCreateTablePersona);
            db.execSQL(sqlCreateMascota);

            db.execSQL("INSERT INTO persona VALUES (1,'Jesus','Cojutepeque')");
            db.execSQL("INSERT INTO persona VALUES (2,'Vladimir','San Salvador')");

            db.execSQL("INSERT INTO mascota VALUES (1,1,'Escanor')");
            //db.execSQL("INSERT INTO mascota VALUES (2,4,'Lola')");
            db.execSQL("INSERT INTO mascota VALUES (2,2,'Lola2')");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists persona");
        db.execSQL("drop table if exists mascota");
    }

   /* @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }*/
    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }
}


