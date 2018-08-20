package com.minsal.dtic.sinavec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

import java.io.IOException;

import HelperDB.DbHelpers;

public class SettingActivity extends AppCompatActivity {
    Button btnSetting;
    private DbHelpers objBaseDeDatos;
    DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnSetting = (Button) findViewById(R.id.btnConfigurar);
        objBaseDeDatos = new DbHelpers(getApplicationContext());
        final boolean check = objBaseDeDatos.checkDataBase();
        //este evento debe ocurrir solo cuando se instala la aplicacioj por primera vez o por si se borra la base
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!check) {
                    //createDataBase();
                    daoSession = ((MyMalaria) getApplication()).getDaoSession();
                    btnSetting.setEnabled(false);
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //lo anterio es para que la app no vuelva a esta pantalla si presion en bonton de atras edel dispositivo
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Esta Tablet ya ha sido configurada", Toast.LENGTH_LONG).show();

                }

            }
        });
    }


    public void createDataBase() {
        try {
            objBaseDeDatos = new DbHelpers(getApplicationContext());
            objBaseDeDatos.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
