package com.minsal.dtic.sinavec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.fragment.ContenedorFragment;

import com.minsal.dtic.sinavec.fragment.CapturaFragment;
import com.minsal.dtic.sinavec.fragment.FebrilFragment;
import com.minsal.dtic.sinavec.fragment.LenguajesFragment;
import com.minsal.dtic.sinavec.fragment.MainFragment;
import com.minsal.dtic.sinavec.fragment.MapFragment;
import com.minsal.dtic.sinavec.fragment.PesquisaFragment;

import Utils.Util;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ContenedorFragment.OnFragmentInteractionListener, PesquisaFragment.OnFragmentInteractionListener,
        LenguajesFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener,
        MenuCriaderoFragment.OnFragmentInteractionListener {

    private SharedPreferences prefs;
  //  TextView tvUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       // String user= Util.getUserPrefs(prefs);



        //if (Utilidades.valida==true){
        Fragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragment).commit();
        // Utilidades.valida=false;
        //}

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View headerView = navigationView.getHeaderView(0);


        //instancia de la bd. se tendra acceso a los metodos de la bd
        //OpenHelperBd bdHelper = new OpenHelperBd(this);
        //OpenHelperBd helper=new OpenHelperBd(this);
        // SQLiteDatabase db = helper.getWritableDatabase();

        Toast.makeText(this, "Exito", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.menu_logout:
                removeSharedPreferences();
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.inicio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MainFragment()).commit();
        } else if (id == R.id.gotas) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new FebrilFragment()).commit();
        } else if (id == R.id.captura) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new CapturaFragment()).commit();
        } else if (id == R.id.pesquisa) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new PesquisaFragment()).commit();
        } else if (id == R.id.criadero) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuCriaderoFragment()).commit();
        } else if (id == R.id.colvol) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MapFragment()).commit();
        } else if (id == R.id.salir) {
            Intent siguiente = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(siguiente);
        } else if (id == R.id.tab) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ContenedorFragment()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Elimnara de la preferences los calores guardados para esta sesion
    private void removeSharedPreferences() {
        prefs.edit().clear().apply();

    }

    //no llevara a la pantalla del login
    private void logOut() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}


