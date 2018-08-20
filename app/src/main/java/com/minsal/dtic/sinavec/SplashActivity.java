package com.minsal.dtic.sinavec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import HelperDB.DbHelpers;
import Utils.Util;

public class SplashActivity extends AppCompatActivity {
    private DbHelpers objBaseDeDatos;
    SQLiteDatabase sql;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objBaseDeDatos = new DbHelpers(getApplicationContext());
        sql = objBaseDeDatos.getReadableDatabase();
        sql.execSQL("");
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        boolean check = objBaseDeDatos.checkDataBase();
        if(check){
            if(!TextUtils.isEmpty(Util.getUserPrefs(prefs))
                    && !TextUtils.isEmpty(Util.getPassPrefs(prefs))){
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }else {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        }else{
            Intent i = new Intent(getApplicationContext(),SettingActivity.class);
            startActivity(i);
        }
        //noq queremos que esta activity se guarde en el historial
        finish();


    }


}
