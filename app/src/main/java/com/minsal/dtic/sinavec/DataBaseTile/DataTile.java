package com.minsal.dtic.sinavec.DataBaseTile;

import android.graphics.Bitmap;

public class DataTile {
    private int x,y,z;
    private Bitmap tile;

    public DataTile(int x, int y, int z, Bitmap tile) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.tile = tile;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
