package com.minsal.dtic.sinavec;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.List;

import HelperDB.DbHelpers;

public class BuscarCriaderoSinActivity extends AppCompatActivity {
    Spinner spMunicipio, spCanton,spCaserio;
    private SQLiteDatabase conDb;
    private DbHelpers helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burcar_criadero);
        spMunicipio = (Spinner)findViewById(R.id.spMun);
        spCanton = (Spinner)findViewById(R.id.spCan);
        spCaserio = (Spinner)findViewById(R.id.spCas);
    }
    private void loadSpinerMun(){



    }
}
