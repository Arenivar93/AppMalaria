package com.minsal.dtic.sinavec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.Sincronizar.SettingActivity;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import Utils.Util;

public class SplashActivity extends AppCompatActivity {
    private DaoSession daoSession;
    private MetodosGlobales metodosGlobales;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoSession = ((MyMalaria) getApplication()).getDaoSession();
        metodosGlobales = new MetodosGlobales(daoSession);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        int tabletCOnfiguracion = metodosGlobales.consultaTabelt();
        if (tabletCOnfiguracion == 1) { // si existe un registro en la tabla tablet es porque ya se configuro
            if (!TextUtils.isEmpty(Util.getUserPrefs(prefs)) // si el usuario marco la casi la casila de recordar en login
                    && !TextUtils.isEmpty(Util.getPassPrefs(prefs))) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            } else {//sino marco la casilla cada vez que ingrese de loguearse
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        } else {//si no exixte un registro es porque debe configurar la tablet
            Intent i = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(i);
        }
        //noq queremos que esta activity se guarde en el historial
        finish();


    }


}
