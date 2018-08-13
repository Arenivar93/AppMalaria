package com.minsal.dtic.sinavec;

import android.app.Application;
import android.os.SystemClock;

//esta clase la usamos para que al cargar tarde dos segundos en el splash
public class MyMalaria extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
