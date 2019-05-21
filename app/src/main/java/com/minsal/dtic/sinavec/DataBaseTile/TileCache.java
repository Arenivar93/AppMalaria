package com.minsal.dtic.sinavec.DataBaseTile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.maps.android.SphericalUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.minsal.dtic.sinavec.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TileCache extends AsyncTask<Object, Integer, Void> {
    private static final int tileSize = 256;
    private static final String UPPER_ZOOM_TILE_URL;
    private static final String TAG = ".titleDownloader";

    private ProgressDialog progress;
    private int radius;
    private int minZoom;
    private int maxZoom;
    private int cantDescargar;
    private Integer cantDescagado;
    private SQLiteMapCache database;
    private Activity activity;
    private ExecutorService executorService;
    boolean verificarImagen = true;

    /**
     * Plantilla de URL de descarga de los Tiles obtenidos desde internet
     */
    static {
        UPPER_ZOOM_TILE_URL = "http://mt0.google.com/vt/lyrs=y&hl=es&x=%d&y=%d&z=%d"; //&scale=1&s=Galileo";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progress != null) {
            progress.setMax(1);
            progress.setProgress(0);
            progress.setCancelable(true);
            progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(final DialogInterface dialogInterface) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setMessage("Si cancela la cache el mapa quedará incompleto, esto provocará que partes del mapa no se muestren de forma offline")
                            .setTitle("¿Cancelar cache de mapa?");
                    // guarda el punto de la ficha actual
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            cancel(true);
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(activity, "La descarga continuara", Toast.LENGTH_SHORT).show();
                            progress.show();
                        }
                    });
                    // Crea la alerta de dialogo
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
            progress.setTitle("Almacenamiento de cache...");
            progress.setMessage("Zona: " + 0);
            progress.show();
        }
    }

    @Override
    protected void onPostExecute(Void dataTiles) {
        super.onPostExecute(dataTiles);
        progress.setMessage("Cache guardada");
        progress.setOnCancelListener(null);
        progress.dismiss();

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        executorService.shutdownNow();
    }

    @Override
    protected synchronized void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i("aaaaaaa",String.valueOf(values[0]));
        progress.setMessage("se esta Guardando cache: " + values[0] + " / " + cantDescargar);
    }

    @Override
    protected Void doInBackground(Object... point) {
        Log.d("ver", String.valueOf(point.length));
        cantDescargar = point.length * 51;
        if (point.length == 0) {
            Log.d(TAG, "No se limito el area de cache de forma adecuada");
            return null;
        }

        cantDescagado = 0;
        boolean salir = false;

        executorService = Executors.newFixedThreadPool(point.length);
        for (int i = 0; i < point.length; i++) {
            Log.i("ver2", "primer for");
            LatLngBounds limitesGPS = toBounds((LatLng) point[i]);
            //Descarga las imagenes del papa para cada zoom que se desea tenear
            for (int z = minZoom; z <= maxZoom; z++) {
                Log.i("ver3", "segundo for");
                int limeteXY1[] = getTile(limitesGPS.northeast, z);
                int limeteXY2[] = getTile(limitesGPS.southwest, z);
                for (int x = limeteXY2[0]; x <= limeteXY1[0]; x++) {
                    Log.i("ver3", "tercer for");
                    for (int y = limeteXY1[1]; y <= limeteXY2[1]; y++) {
                        if (verificarImagen) {
                            Bitmap imageMap = getBitmapFromURL(x, y, z);
                            if (imageMap == null) {
                                salir = true;
                                Log.i("nooo", "No  hay imagen");

                            } else {
                                Log.i("Sii", "si  hay imagen");
                                verificarImagen = false;
                            }
                        }
                        Log.d(TAG, x + " " + y + " " + z);
                        Runnable downloader = new SaveCache(x, y, z);

                        executorService.execute(downloader);

                    }
                }
            }
            if (salir){break;}
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Calcula los puntos soutwest y northwest en un radio radius
     * aldededor de un punto en el espacio
     * @param center
     * @return
     */
    private LatLngBounds toBounds(final LatLng center) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    /**
     * Proyecta las coordenadas de lat y lng a puntos x y y que son coordenadas
     * para los tiles
     *
     * @param point son las coordenadas gps (latitud y longitud = coordenadas x, y)
     * @param zoom  nivel de zoom
     * @return un arreglo de entero, donde en la primera posicion esta x y en la segunda y
     */
    private int[] getTile(LatLng point, int zoom) {
        int[] pointXY = new int[2];
        int numTiles = 1 << zoom;

        //Calulate the X coorindate
        pointXY[0] = (int) ((tileSize / 2 + point.longitude * tileSize / 360.0) * numTiles / tileSize);

        //Calulate the y coorindate
        double sin_y = Math.sin(point.latitude * (Math.PI / 180.0));
        pointXY[1] = (int) (((tileSize / 2) + 0.5 * Math.log((1 + sin_y) / (1 - sin_y)) * -(tileSize / (2 * Math.PI))) * numTiles / tileSize);

        return pointXY;
    }

    /**
     * Descarga una imagen de mapa de la URL, dadas las coordenadas X, Y, Z
     *
     * @param x coordenada en x
     * @param y coordenada en y
     * @param z zoom que se desea obtener
     * @return una imagen en formato de Bitmap, retorna null si no se puede descargar la imagen.
     */
    public static Bitmap getBitmapFromURL(int x, int y, int z) {
        Log.d(TAG, String.format(UPPER_ZOOM_TILE_URL, x, y, z));
        try {
            URL url = new URL(String.format(UPPER_ZOOM_TILE_URL, x, y, z));
            Log.d(TAG, url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           // connection.setDoInput(true);
            connection.connect();
            Bitmap myBitmap = BitmapFactory.decodeStream(connection.getInputStream());
            return myBitmap;
        } catch (IOException e) {
            Log.d(TAG, "exception when retrieving bitmap from internet" + e.toString());
            return null;
        }
    }

    /**
     * Contrucctor de la clase, exige los parametros del contexto
     *
     * @param activity
     * @param radius   radio
     * @param minZoom  zoom minimo a descargar
     * @param maxZoom  zoom maximo a descargar
     */
    public TileCache(Activity activity, int radius, int minZoom, int maxZoom) {
        Log.d(TAG, "Se creo instancia de " + TileCache.class.getName());
        progress = new ProgressDialog(activity);
        this.radius = radius;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.activity = activity;
        if (this.minZoom > this.maxZoom) {
            int aux = minZoom;
            this.minZoom = this.maxZoom;
            this.maxZoom = aux;
        }
        this.database = new SQLiteMapCache(activity);
    }

    /**
     * Es un hilo que descarga un Tile, omite la descarga si  el Tile se encuentra en la base de datos
     * y suma al contador la cantidad de Tiles descargados.
     */
    class SaveCache implements Runnable {
        private int x, y, z;

        public SaveCache(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /********
         * Descarga una imagen
         */
        @Override
        public void run() {
            synchronized (database) {
                if (database.getTile(x, y, z) == null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap imageMap = getBitmapFromURL(x, y, z);
                    if (imageMap != null) {

                        imageMap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] image = stream.toByteArray();
                        database.saveTile(x, y, z, image);

                    }else{
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                TextView txtView = (TextView) activity.findViewById(R.id.msgmap);
                                txtView.setText("No es posible descargar la imagen de mapa, Si usa intranet vefique estar logueado en: \n  http://passthrough.fw-notify.net/static/auth_transparent.html");

                            }
                        });
                    }
                }
            }

            synchronized (cantDescagado) {
                Log.i("cantidad descarga",String.valueOf(cantDescagado));
                publishProgress(cantDescagado);
                cantDescagado++;
            }

        }
    }
}
