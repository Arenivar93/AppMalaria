package com.minsal.dtic.sinavec.CRUD.pesquisaLarvaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.minsal.dtic.sinavec.R;

public class ListPesquisaActivity extends AppCompatActivity {
    Button btnNueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pesquisa);
        btnNueva = (Button)findViewById(R.id.btnNuevaPesquisa);


        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),pesquisaLarvaria.class);
                startActivity(i);finish();
            }
        });
    }
}
