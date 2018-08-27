package com.minsal.dtic.sinavec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;
import Utils.Util;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Switch swRemember;
    Button btnLogin;

    SharedPreferences prefs;
    MetodosGlobales metodoGlobal;
    DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        edtUser = (EditText)findViewById(R.id.edtUser);
        edtPass = (EditText)findViewById(R.id.edtPass);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        swRemember = (Switch)findViewById(R.id.swRemeber);
        setCredential();
        daoSession=((MyMalaria)getApplication()).getDaoSession();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String elUser = edtUser.getText().toString().trim();
                String elPass = edtPass.getText().toString().trim();

                metodoGlobal = new MetodosGlobales(daoSession);
                boolean check = metodoGlobal.checkDataBase();// si exixte la base de datos
                if (check){
                    //boolean existe = daoSession.validateLogin(elUser,elPass);
                    boolean existe = metodoGlobal.validateLogin(elUser,elPass);
                    if(existe){
                        saveOnPreferences(elUser,elPass);
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Usuario no encontrado",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Esta Dispositivo aun no ha sido configurado",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void setCredential(){
        String user = Util.getUserPrefs(prefs);
        String pass = Util.getPassPrefs(prefs);
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){
            edtUser.setText(user);
            edtPass.setText(pass);
        }
    }

    private void saveOnPreferences(String user, String pass){
        if(swRemember.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user",user);
            editor.putString("pass",pass);
            editor.apply();
        }
    }

}