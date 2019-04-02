package com.minsal.dtic.sinavec.DataBaseTile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.minsal.dtic.sinavec.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SQLiteMapCache extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MapsTitle.db";
    public static final String TAG = ".dataBaseTile";
    Context context;
    int depto = MainActivity.depto;
    public SQLiteMapCache(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context= context;
    }

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TILE =
            "CREATE TABLE " + Esquema.Tile.TABLE_NAME + " (" +
                    Esquema.Tile.COLUMN_NAME_X + INTEGER_TYPE + COMMA_SEP +
                    Esquema.Tile.COLUMN_NAME_Y + INTEGER_TYPE + COMMA_SEP +
                    Esquema.Tile.COLUMN_NAME_Z + INTEGER_TYPE + COMMA_SEP +
                    Esquema.Tile.COLUMN_NAME_MAP + BLOB_TYPE + COMMA_SEP +
                    Esquema.Tile.COLUMN_NAME_DATE_SAVE + TEXT_TYPE + " ); " ;

    private static final String SQL_CREATE_POINT =
            "CREATE TABLE " + Esquema.SyncPoints.TABLE_NAME + " (" +
                    Esquema.SyncPoints.COLUMN_LAT + DOUBLE_TYPE + COMMA_SEP +
                    Esquema.SyncPoints.COLUMN_LNG + DOUBLE_TYPE + " );";

    private static final String SQL_PK_TILE =
            "CREATE UNIQUE INDEX pk_index_tile ON \"" + Esquema.Tile.TABLE_NAME +
                    "\"(\"" + Esquema.Tile.COLUMN_NAME_X + "\",\"" +
                    Esquema.Tile.COLUMN_NAME_Y + "\",\"" +
                    Esquema.Tile.COLUMN_NAME_Z + "\");";

    private static final String SQL_PK_point =
            "CREATE UNIQUE INDEX pk_index_point ON \"" + Esquema.SyncPoints.TABLE_NAME +
                    "\"(\"" + Esquema.SyncPoints.COLUMN_LAT + "\",\"" +
                    Esquema.SyncPoints.COLUMN_LNG + "\");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Esquema.Tile.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TILE);
        db.execSQL(SQL_CREATE_POINT);
        db.execSQL(SQL_PK_TILE);
        db.execSQL(SQL_PK_point);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public byte[] getTile(int x, int y, int z) {
        Log.i(TAG, "Se esta solicitando aqui en Tile X: " + x + " Y: " + y + " Z: " + z);
        SQLiteDatabase db = this.getReadableDatabase();
        String[] resultComlumns = {
                Esquema.Tile.COLUMN_NAME_X,
                Esquema.Tile.COLUMN_NAME_Y,
                Esquema.Tile.COLUMN_NAME_Z,
                Esquema.Tile.COLUMN_NAME_MAP,
                Esquema.Tile.COLUMN_NAME_DATE_SAVE
        };

        String where = Esquema.Tile.COLUMN_NAME_X + " = ? " + " AND " +
                Esquema.Tile.COLUMN_NAME_Y + " = ?" + " AND " +
                Esquema.Tile.COLUMN_NAME_Z + " = ?";

        String[] whereValue = {x + "", y + "", z + ""};
        Cursor c = db.query(
                Esquema.Tile.TABLE_NAME,   // The table to query
                resultComlumns,             // The columns to return
                where,                      // The columns for the WHERE clause
                whereValue,                 // The values for the WHERE clause
                null,                       // don't group the rows
                null,                       // don't filter by row groups
                null                        // The sort order
        );

        try {
            if (c.moveToFirst()) {
                Log.i(TAG, "Se econtro Tile X: " + x + " Y:" + y + " Z:" + z);

                byte[] titleImage = null;
                titleImage = c.getBlob(c.getColumnIndexOrThrow(Esquema.Tile.COLUMN_NAME_MAP));
                String dateText = c.getString(c.getColumnIndexOrThrow(Esquema.Tile.COLUMN_NAME_DATE_SAVE));


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(dateText);
                Date hoy = new Date();

                //Obliga la descarga
                if (date.getTime() < hoy.getTime()) {
                    deleteAllTile();
                    Log.d("AAA", "Supero la cantidad de tiempo en cache 30 d");
                    Toast.makeText(context,"El mapa ha vencido por favor descargue uno nuevo",Toast.LENGTH_LONG).show();
                    return null;
                }
                if (titleImage == null) {
                    //Log.e(TAG, "NULL Tile", new NullPointerException());
                }
                return titleImage;
            } else {
                //Log.e(TAG,"No se encontro title", new NullPointerException());
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            c.close();
        }
    }
    public void saveTile(int x, int y, int z, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sumarRestarDiasFecha(new Date(),30);
        cv.put(Esquema.Tile.COLUMN_NAME_X, x);
        cv.put(Esquema.Tile.COLUMN_NAME_Y, y);
        cv.put(Esquema.Tile.COLUMN_NAME_Z, z);
        cv.put(Esquema.Tile.COLUMN_NAME_DATE_SAVE, sdf.format(d));
        cv.put(Esquema.Tile.COLUMN_NAME_MAP, image);

        long id = database.insert(Esquema.Tile.TABLE_NAME, null, cv);

        if (id <= 0) {
            Log.d(TAG, "No se puede guardar");
        }
        //database.close();
    }
    /**
     * Suma los días recibidos a la fecha
     */
    public Date sumarRestarDiasFecha(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }
    /**
     * Elimina los puntos de soncronización del mapa offline en la configuración
     * @param latLng coordenadas de longitud y latitud
     */
    public void deleteSyncPoint(LatLng latLng){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Esquema.SyncPoints.TABLE_NAME
                        + " WHERE " +Esquema.SyncPoints.COLUMN_LNG +"= ? and "+ Esquema.SyncPoints.COLUMN_LAT +"= ?"
                ,new Double[]{latLng.longitude , latLng.latitude });
        db.close();
    }

    public void saveSyncPoint(LatLng latLng){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Esquema.SyncPoints.COLUMN_LAT, latLng.latitude);
        cv.put(Esquema.SyncPoints.COLUMN_LNG, latLng.longitude);
        long id = database.insert(Esquema.SyncPoints.TABLE_NAME, null, cv);
        if (id <= 0) {
            Log.d(TAG, "No se puede guardar");
        }
        database.close();
    }
    /**
     * Cuenta la cantidad de Tile existentes en la base de datos
     * @return un entero con la cantidad de Tiles o 0 si no hay.
     */
    public int countTiles() {
        int cantidad=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) FROM " + Esquema.Tile.TABLE_NAME, null);
        if (c.moveToFirst()) {
            cantidad= c.getInt(0);
            Log.i("***",String.valueOf(cantidad));
        }
        return cantidad;
    }
    /**
     * Obtiene los puntos de sincronización del mapa offline
     * @return un ArrayList con los puntos
     */
    public ArrayList<LatLng> getSyncPoints(){
        ArrayList<LatLng> points = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] resultComlumns = {
                Esquema.SyncPoints.COLUMN_LAT,
                Esquema.SyncPoints.COLUMN_LNG
        };

        Cursor c = db.query(
                Esquema.SyncPoints.TABLE_NAME,  // The table to query
                resultComlumns,                 // The columns to return
                null,                           // The columns for the WHERE clause
                null,                           // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                null                            // The sort order
        );

        while (c.moveToNext()) {
            points.add(new LatLng(c.getDouble(0),c.getDouble(1)));
        }
        db.close();
        c.close();
        return points;
    }
    /**
     *Elimina el contenido de la tabla con los tiles
     */
    public void deleteAllTile(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Esquema.Tile.TABLE_NAME);
        db.close();
    }

    public  int countPoint() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) FROM " + Esquema.SyncPoints.TABLE_NAME, null);
        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }

}
