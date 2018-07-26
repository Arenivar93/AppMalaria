package com.example.administrador.sinavec;

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

import com.example.administrador.sinavec.clases.Utilidades;
import com.example.administrador.sinavec.fragment.ColvolFragment;
import com.example.administrador.sinavec.fragment.ContenedorFragment;
import com.example.administrador.sinavec.fragment.CriaderoFragment;
import com.example.administrador.sinavec.fragment.CapturaFragment;
import com.example.administrador.sinavec.fragment.FebrilFragment;
import com.example.administrador.sinavec.fragment.LenguajesFragment;
import com.example.administrador.sinavec.fragment.MainFragment;
import com.example.administrador.sinavec.fragment.PesquisaFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ContenedorFragment.OnFragmentInteractionListener,PesquisaFragment.OnFragmentInteractionListener,
        LenguajesFragment.OnFragmentInteractionListener,MainFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //if (Utilidades.valida==true){
            Fragment fragment=new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
           // Utilidades.valida=false;
        //}

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        FragmentManager fragmentManager=getSupportFragmentManager();


        if (id == R.id.inicio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new MainFragment()).commit();
        }else if (id == R.id.gotas) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FebrilFragment()).commit();
        }else if (id == R.id.captura) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new CapturaFragment()).commit();
        }else if (id == R.id.pesquisa) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new PesquisaFragment()).commit();
        } else if (id == R.id.criadero) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new CriaderoFragment()).commit();
        } else if (id == R.id.colvol) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new ColvolFragment()).commit();
        } else if (id == R.id.salir) {
            //Intent siguiente= new Intent(MainActivity.this,prueba.class);
            //startActivity(siguiente);

            finish();
            //fragmentManager.beginTransaction().replace(R.id.contenedor,new LenguajesFragment()).commit();
        }else if (id == R.id.tab) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new ContenedorFragment()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

   /* public void onClick(View v) {
        //LenguajesFragment objeto=new LenguajesFragment();
        switch (v.getId()){
            case R.id.idGota:
                //Utilidades.visualizacion=Utilidades.LIST;
                Toast.makeText(this,"Gestion Gotas Gruesas",Toast.LENGTH_SHORT).show();
                //objeto.construirRecycle();
                break;
            case R.id.idPesquisa:
                Toast.makeText(this,"Gestion Pesquisas Larvarias",Toast.LENGTH_SHORT).show();
                break;
            case R.id.idCaptura:
                Toast.makeText(this,"Gestion Captura Anopheles",Toast.LENGTH_SHORT).show();
                break;
            case R.id.idBotiquin:
                Toast.makeText(this,"Gestion Seguimiento Botiquin",Toast.LENGTH_SHORT).show();
                break;
        }
        //LenguajesFragment.construirRecycle(view);
    }*/


}


/*public class StartFragment extends Fragment implements OnClickListener
{ @Override public View onCreateView(LayoutInflater inflater,
                                     ViewGroup container,
                                     Bundle savedInstanceState)
{
    View v = inflater.inflate(R.layout.fragment_start, container,
            false);
    Button b = (Button) v.findViewById(R.id.StartButton);
    b.setOnClickListener(this);
    return v;
} @Override
public void onClick(View v)
{ switch (v.getId())
{ case R.id.StartButton: ... break;
} } } */