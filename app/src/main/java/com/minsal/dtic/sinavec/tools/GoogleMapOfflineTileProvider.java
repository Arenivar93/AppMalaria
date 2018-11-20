package com.minsal.dtic.sinavec.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import com.minsal.dtic.sinavec.DataBaseTile.SQLiteMapCache;
import com.minsal.dtic.sinavec.R;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleMapOfflineTileProvider implements TileProvider {
    private static final String UPPER_ZOOM_TILE_URL;
    private Context c;
    private Bitmap not;
    private boolean internet = true;
    /**
     * Plantilla de URL de descarga de los Tiles obtenidos desde internet
     */
    static {
        UPPER_ZOOM_TILE_URL = "http://mt0.google.com/vt/lyrs=y&hl=es&x=%d&y=%d&z=%d"; //&scale=1&s=Galileo";
    }
    /**
     * Provee las imagenes Tile cuando un Map los solicita, tambien valida si se pueden utilizar o
     * no las imagenes
     * @param context es el contexto bajo el cual va a funcionar
     */
    public GoogleMapOfflineTileProvider(Context context) {
        sqLiteMapCache = new SQLiteMapCache(context);
        c = context;
        not = BitmapFactory.decodeResource(c.getResources(), R.drawable.no_tile);
        //internet = Validator.canUseMapOnline(context);
    }

    private static final String TAG = GoogleMapOfflineTileProvider.class.getName();
    private SQLiteMapCache sqLiteMapCache;
    @Override
    public Tile getTile(int x, int y, int z) {
        Tile tile = NO_TILE;

        byte[] title = sqLiteMapCache.getTile(x, y, z);

        if(title != null){
            tile = new Tile(256, 256, title);
        } else {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Boolean b = true;
            Bitmap imageMap = getBitmapFromURL(x, y, z);
            if(imageMap == null && !internet ){
                imageMap = not;
                b = false;
            }
            if(internet && imageMap == null){
                return NO_TILE;
            }
            imageMap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] image =  stream.toByteArray();
            if(b == true ) {
                sqLiteMapCache.saveTile(x, y, z, image);
            }
            tile = new Tile(256, 256, image);
        }
        return tile;
    }
    /**
     * Descarga una imagen de mapa de la URL, dadas las coordenadas X, Y, Z
     * @param x coordenada en x
     * @param y coordenada en y
     * @param z zoom que se desea obtener
     * @return una imagen en formato de Bitmap, retorna null si no se puede descargar la imagen.
     */
    public static Bitmap getBitmapFromURL(int x, int y, int z) {
        Log.d(TAG, String.format(UPPER_ZOOM_TILE_URL, x, y, z));
        Bitmap b;
        try {
            URL url = new URL(String.format(UPPER_ZOOM_TILE_URL, x, y, z));
            Log.d("Descargando", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(100);
            connection.setDoInput(true);
            connection.connect();
            b =  BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            Log.d(TAG, "exception when retrieving bitmap from internet" + e.toString());
            b = null;
        }
        return b;
    }
}
