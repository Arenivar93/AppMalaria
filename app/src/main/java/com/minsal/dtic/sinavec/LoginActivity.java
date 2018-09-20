package com.minsal.dtic.sinavec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamento;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamentoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.utilidades.MetodosGlobales;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import Utils.Util;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtPass;
    TextInputLayout textUSer,textPass;
    Switch swRemember;
    Button btnLogin;

    SharedPreferences prefs;
    MetodosGlobales metodoGlobal;
    DaoSession daoSession;

    // final static int idDepartamento=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        edtUser = (TextInputEditText) findViewById(R.id.edtUser);
        edtPass = (TextInputEditText) findViewById(R.id.edtPass);
        textPass=(TextInputLayout)findViewById(R.id.edtPassText);
        textUSer=(TextInputLayout)findViewById(R.id.edtUserText);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        swRemember = (Switch) findViewById(R.id.swRemeber);
        setCredential(); // si tiene datos los pondre en los editext
        daoSession = ((MyMalaria) getApplication()).getDaoSession();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String elUser = edtUser.getText().toString().trim();
                String elPass = edtPass.getText().toString().trim();
                metodoGlobal = new MetodosGlobales(daoSession);

                textPass.setError("");
                textUSer.setError("");
                int respuesta = 0;//0---> usuario no encontrado 1--->contraseña incorrecta
                                  //2---> datos validos
                try {
                    respuesta = metodoGlobal.validateLogin(elUser, elPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (respuesta==2) {
                    saveOnPreferences(elUser, elPass);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("bandera_pref","guardar_ids");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(getApplicationContext(), "Bienvenido!!!", Toast.LENGTH_LONG).show();
                    startActivity(i);
                }else if(respuesta==1) {
                    textPass.setError("Contraseña incorrecta");
                }else{
                    textUSer.setError("Usuario no encontrado");
                }


            }
        });

    }

    private void setCredential() {
        String user = Util.getUserPrefs(prefs);
        String pass = Util.getPassPrefs(prefs);
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
            edtUser.setText(user);
            edtPass.setText(pass);
        }
    }

    private void saveOnPreferences(String user, String pass) {
        if (!swRemember.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user", user);
            editor.putString("pass", pass);
            editor.apply();

        }
        else{
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("userRem", user);
            editor.putString("passRem", pass);
            editor.putString("user", user);
            editor.putString("pass", pass);
            editor.apply();

        }
    }


}