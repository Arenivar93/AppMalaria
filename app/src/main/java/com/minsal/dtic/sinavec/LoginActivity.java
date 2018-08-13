package com.minsal.dtic.sinavec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import HelperDB.DbHelpers;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnLogin;
    DbHelpers objBaseDeDatos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser = (EditText)findViewById(R.id.edtUser);
        edtPass = (EditText)findViewById(R.id.edtPass);
        btnLogin = (Button)findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String elUser = edtUser.getText().toString().trim();
                String elPass = edtPass.getText().toString().trim();
                objBaseDeDatos = new DbHelpers(getApplicationContext());
                boolean check = objBaseDeDatos.checkDataBase();
                if (check){
                    boolean existe = objBaseDeDatos.validateLogin(elUser,elPass);
                    if(existe){
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"el usuario no exixte",Toast.LENGTH_LONG).show();
                    }
                }else{
                    createDataBase();

                }


            }
        });

    }

    public void createDataBase()  {
        try {
            objBaseDeDatos = new DbHelpers(getApplicationContext());
            objBaseDeDatos.createDataBase();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
