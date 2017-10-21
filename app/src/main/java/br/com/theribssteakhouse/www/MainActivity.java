package br.com.theribssteakhouse.www;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private List<Integer> listTelas = new ArrayList<>();
    FragmentManager manage;
    FragmentTransaction txn;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        manage = getSupportFragmentManager();
        txn = manage.beginTransaction();
        //Cria o fragment
        FragmentViewPager fragment = new FragmentViewPager();

        fragment.addFragment("Home",new FragmentHome());
        fragment.addFragment("Pesquisa",new FragmentPesquisa());

        txn.replace(R.id.container, fragment);

        txn.commit();

        listTelas.add(0);

        navigationView.getMenu().getItem(0).setChecked(true);

        //setup the pager
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (listTelas.size() - 1 > 0){
            //Armazenando o index o qual será redirecionado
            int indexNextItem = listTelas.size() - 2;
            //Armazenando o index o qual será excluido da lista
            int indexRemoveItem = listTelas.size() - 1;
            //Armazenando o numero representativo do item menu
            int itemMenu = listTelas.get(indexNextItem);
            //Selecionando o item do menu
            navigationView.setCheckedItem(itemMenu);
            listTelas.remove(indexRemoveItem);

            int itemId;

            if (itemMenu == 0){
                itemId = R.id.nav_camera;
            }else if (itemMenu == 1){
                itemId = R.id.nav_gallery;
            }else if (itemMenu == 2){
                itemId = R.id.nav_gallery;
            }else{
                itemId = R.id.nav_slideshow;
            }

            previouslyFragment(itemId);
        } else{
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

        changeFragment(id);

        return true;
    }
    //Muda o fragment de acordo com o item do menu
    public void changeFragment(int id){
        if (id == R.id.nav_camera) {
            //Define o label da activity
            this.setTitle("Home");
            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();
            listTelas.add(0);

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Home",new FragmentHome());
            fragment.addFragment("Pesquisa",new FragmentPesquisa());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_gallery) {
            //Define o label da activity
            this.setTitle("Scanner");
            listTelas.add(1);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();
            //Substitui o Fragment na tela
            txn.replace(R.id.container, new FragmentPedidoClient());

            txn.commit();
        } else if (id == R.id.nav_slideshow) {
            //Define o label da activity
            this.setTitle("Pedidos");

            listTelas.add(2);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Pedido Garçom",new FragmentPedidoGarcom());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    //Muda o fragment de acordo com o item do menu
    public void previouslyFragment(int id){
        if (id == R.id.nav_camera) {
            //Define o label da activity
            this.setTitle("Home");
            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Home",new FragmentHome());
            fragment.addFragment("Pesquisa",new FragmentPesquisa());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_gallery) {
            //Define o label da activity
            this.setTitle("Scanner");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();
            //Substitui o Fragment na tela
            txn.replace(R.id.container, new FragmentPedidoClient());

            txn.commit();
        } else if (id == R.id.nav_slideshow) {
            //Define o label da activity
            this.setTitle("Pedidos");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Pedido Garçom",new FragmentPedidoGarcom());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void nextPageHome(){
        manage = getSupportFragmentManager();
        txn = manage.beginTransaction();

        //Cria o fragment
        FragmentViewPager fragment = new FragmentViewPager();

        fragment.addFragment("Home",new FragmentHome());
        fragment.addFragment("Pesquisa",new FragmentPesquisa());
        fragment.setFragmentNumber(1);
        txn.replace(R.id.container, fragment);

        txn.commit();
    }

    public void detalhesPedidoGarcom(int id){
        manage = getSupportFragmentManager();
        txn = manage.beginTransaction();

        //Cria o fragment
        FragmentViewPager fragment = new FragmentViewPager();

        FragmentPedidoGarcomDetalhes fragmentPedido = new FragmentPedidoGarcomDetalhes();

        fragmentPedido.setPedido(id);

        fragment.addFragment("Pedidos", new FragmentPedidoGarcom());
        fragment.addFragment("Detalhes pedido", fragmentPedido);
        fragment.setFragmentNumber(2);

        txn.replace(R.id.container, fragment);

        txn.commit();
    }
}
