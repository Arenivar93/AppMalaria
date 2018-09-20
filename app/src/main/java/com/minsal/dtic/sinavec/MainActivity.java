package com.minsal.dtic.sinavec;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.minsal.dtic.sinavec.CRUD.Colvol.fragmentColvol.MenuColvolFragment;
import com.minsal.dtic.sinavec.CRUD.Criaderos.fragmentCriadero.MenuCriaderoFragment;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamentoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlTablet;
import com.minsal.dtic.sinavec.EntityDAO.CtlTabletDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.Sincronizar.SubirDatos;
import com.minsal.dtic.sinavec.fragment.ContenedorFragment;
import com.minsal.dtic.sinavec.fragment.CapturaFragment;
import com.minsal.dtic.sinavec.fragment.FebrilFragment;
import com.minsal.dtic.sinavec.fragment.LenguajesFragment;
import com.minsal.dtic.sinavec.fragment.MainFragment;
import com.minsal.dtic.sinavec.fragment.MapFragment;
import com.minsal.dtic.sinavec.fragment.PesquisaFragment;
import com.minsal.dtic.sinavec.utilidades.Utilidades;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ContenedorFragment.OnFragmentInteractionListener, PesquisaFragment.OnFragmentInteractionListener,
        LenguajesFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener,
        MenuCriaderoFragment.OnFragmentInteractionListener,
        MenuColvolFragment.OnFragmentInteractionListener{

    private SharedPreferences prefs;
    public static int depto;
    private DaoSession daoSession;
    static final int GET_IMEI = 100;
    //  TextView tvUser;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daoSession = ((MyMalaria) getApplication()).getDaoSession();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(toolbar);
        String elUser = prefs.getString("user", "");
        long idSibasiUser = idSibasiUser();
        long idTablet     = getIdTablet();
        long iduser       = getIdUser();
        depto             = deptoUser(elUser); // este id lo usaremos para conocer el departamento al que pertence el usaurio
        if (bundle!=null){
            if (bundle.get("bandera_pref")!=null){
                saveOnPreferences(idSibasiUser,idTablet,iduser,depto);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (Utilidades.fragment == 0) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MainFragment()).commit();
        } else if (Utilidades.fragment == 1) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuCriaderoFragment()).commit();
        }else if(Utilidades.fragment == 2) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuColvolFragment()).commit();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);



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
        } else if (id == R.id.criadero) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuCriaderoFragment()).commit();
        } else if (id == R.id.colvol) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuColvolFragment()).commit();
        } else if (id == R.id.salir) {
            Intent siguiente = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(siguiente);
        } else if(id== R.id.subir_datos){
            Intent i = new Intent(getApplicationContext(), SubirDatos.class);
            startActivity(i);
        }else if (id == R.id.tab) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ContenedorFragment()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Elimnara de la preferences los valores guardados para esta sesion
    private void removeSharedPreferences() {
        prefs.edit().clear().apply();

    }

    //nos llevara a la pantalla del login
    private void logOut() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * estos metodos los los usaremos para guardar valores que se usaran en toda la aplicacion
     * en los diferentes formularios
     *
     */
    public int deptoUser(String username) {
        CtlDepartamentoDao departamentoDao = daoSession.getCtlDepartamentoDao();
        String sqlQUERY = "SELECT d.id FROM ctl_departamento d " +
                "INNER JOIN ctl_municipio m on (m.id_departamento = d.id)\n" +
                "INNER JOIN ctl_establecimiento es on(es.id_municipio= m.id)\n" +
                "INNER JOIN fos_user_user f on (f.id_sibasi= es.id)\n" +
                "WHERE f.username ='" + username + "'";
        Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY, null);

        int idDepartamento = 0;
        if (cursor.moveToFirst()) {
            idDepartamento = cursor.getInt(0);
        }
        return idDepartamento;
    }

    public long idSibasiUser() {
        long idSibasi = 0;
        String username = prefs.getString("user", "");
        if (!username.equals("")) {
            List<FosUserUser> ids = null;
            FosUserUserDao userDao = daoSession.getFosUserUserDao();
            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
            qb.where(FosUserUserDao.Properties.Username.eq(username));
            ids = qb.list();
            for (FosUserUser f : ids) {
                idSibasi = f.getIdSibasi();
            }
        }
        return idSibasi;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getIdTablet() {
        String imei = getIMEINumber();
        long idtablet = 0;
        if (!imei.equals("")) {
            List<CtlTablet> ids = null;
            CtlTabletDao tabDao = daoSession.getCtlTabletDao();
            QueryBuilder<CtlTablet> qb = tabDao.queryBuilder();
            qb.where(CtlTabletDao.Properties.Serie.eq(imei)); //el imei se esta guardando en el campo serie
            ids = qb.list();
            for (CtlTablet t : ids) {
                idtablet = t.getId();
            }
        }
        return idtablet;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getIMEINumber() {
        String myAndroidDeviceId = "";
        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //comprobar version de android usando
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, GET_IMEI);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                ;
            myAndroidDeviceId = mTelephony.getDeviceId();
            return myAndroidDeviceId;
        } else {
            myAndroidDeviceId = mTelephony.getImei();

        }
        return myAndroidDeviceId;
    }
    private void saveOnPreferences(long idSibasiUser, long idTablet, long idUser,long idDeptoUser) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("idSibasiUser",idSibasiUser);
            editor.putLong("idTablet",idTablet);
            editor.putLong("idUser",idUser);
            editor.putLong("idDeptoUser",idDeptoUser);
            editor.apply();
    }
    public long getIdUser() {
        String username = prefs.getString("user", "");
        long id = 0;
        if (!username.equals("")) {
            List<FosUserUser> ids = null;
            FosUserUserDao userDao = daoSession.getFosUserUserDao();
            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
            qb.where(FosUserUserDao.Properties.Username.eq(username));
            ids = qb.list();
            for (FosUserUser f : ids) {
                id = f.getId();
            }
        }
        return id;
    }



}


