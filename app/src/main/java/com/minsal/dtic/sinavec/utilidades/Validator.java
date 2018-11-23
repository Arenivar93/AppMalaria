package com.minsal.dtic.sinavec.utilidades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.minsal.dtic.sinavec.DataBaseTile.Esquema;
import com.minsal.dtic.sinavec.DataBaseTile.SQLiteMapCache;
import com.minsal.dtic.sinavec.DataBaseTile.TileCache;
import com.minsal.dtic.sinavec.MapOfflineActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Validator {


    /**
     * Verifica si el dispositivo posee google play service
     * @param c contexto
     * @return true si tiene google plays service, caso contrario false.
     */
    public static boolean hasPlayService(Context c) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(c);
        return code == ConnectionResult.SUCCESS ? true : false;
    }

    /**
     * Verifica si existe algun tipo de conexion a una red, ya sea WiFi o Datos.
     * @param c contexto
     * @return true si se encuentra disponible algun tipo de conexion, false en caso contrario.
     */
    public static boolean isNetDisponible(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }

    /**
     * Verifica si se tiene acceso a internet
     * @return retorna true si puede acceder a google, false en caso contrario
     */
    private static boolean executeCommand() {
        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue " + mExitValue);
            if (mExitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            System.out.println(" Exception:" + ignore);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return false;
    }

    /**
     * Si el positivo posee la capacidad de acceder a internet, se dira que puede utilizar el mapa
     * @param c contexto
     * @return true o false
     */
    public static boolean canUseMapOnline(Context c) {
        boolean hasConection  = isNetDisponible(c);

        final boolean[] hasAcces = {false};
        if(!hasConection )
            return false;

        AsyncTask a = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                if(TileCache.getBitmapFromURL(350,350,15) != null)
                    hasAcces[0] = true;
                return null;
            }
        };

        try {
            a.execute().get(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return hasAcces[0];
    }

    /**
     * Verifica si tiene por lo menos 100 cuadros de imagenes guardadas del mapa
     * @param c contexto
     * @return true o false
     */
    public static boolean hasSaveMap(Context c) {
        SQLiteMapCache db = new SQLiteMapCache(c);

        return db.countTiles() >= 100;
    }

    /**
     * Verifica si es necesario actualizar el mapa offline o no.
     * @param c Actividad en la que se quiere comprobar la validez del mapa
     */
    public static void updateMap( final Activity c){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        SQLiteMapCache db = new SQLiteMapCache(c);
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM "+ Esquema.Tile.TABLE_NAME + " WHERE "
                +"DATE("+ Esquema.Tile.COLUMN_NAME_DATE_SAVE + ") <= " + "DATE('"+sdf.format(d)+"')" , null);
        if(cursor.moveToFirst()){
            AlertDialog.Builder builder = new AlertDialog.Builder(c);

            builder.setMessage("Algún sector del mapa expiro o está a punto de expirar  " +
                    "¿Desea seguir utilizando el mapa? Las áreas vencidas no se mostrarán ")
                    .setTitle("Se necesita actualizar el mapa");
            // guarda el punto de la ficha actual
            builder.setPositiveButton("Actulizar Mapa", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent i = new Intent(c, MapOfflineActivity.class);
                    c.startActivity(i);
                    c.finish();
                }
            });

            builder.setNegativeButton("Seguir Usando igual", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            // Crea la alerta de dialogo
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
