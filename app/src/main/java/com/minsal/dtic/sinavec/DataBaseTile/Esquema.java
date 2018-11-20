package com.minsal.dtic.sinavec.DataBaseTile;

import android.content.res.AssetManager;
import android.provider.BaseColumns;

import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Esquema {
    public Esquema() {
    }
    /**
     * Campos y nombre de tabla Tiles
     */
    public static class Tile implements BaseColumns {
        public static final String TABLE_NAME = "tile";
        public static final String COLUMN_NAME_X = "latitud";
        public static final String COLUMN_NAME_Y = "longitud";
        public static final String COLUMN_NAME_Z = "zoom";
        public static final String COLUMN_NAME_MAP = "map";
        public static final String COLUMN_NAME_DATE_SAVE = "expiratedSave";
    }

    /**
     * Campos y nombre de tabla de puntos de sincronización
     */
    public static class SyncPoints implements  BaseColumns{
        public static final String TABLE_NAME = "sync_points";
        public static final String COLUMN_LAT = "latitud";
        public static final String COLUMN_LNG = "longitud";
    }



}
