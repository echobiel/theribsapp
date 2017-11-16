package com.theribssh.www;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private List<Integer> listTelas = new ArrayList<>();
    FragmentManager manage;
    FragmentTransaction txn;
    NavigationView navigationView;
    Socket socket;
    Runnable runnable;
    Intent intent;
    int permissao, idUser, novo_pedido;
    CircleImageView perfil;
    TextView title_bem_vindo;
    String foto, nomeUser;
    int verificadorDialog = 0;
    int id_pedido;
    int id_produto;
    int qtd_produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NotificationManager nManager = (NotificationManager)
                (this).getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.cancel(1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        intent = getIntent();

        idUser = intent.getIntExtra("id_usuario",0);
        permissao = intent.getIntExtra("permissao",0);

        if (permissao == 0){
            navigationView.getMenu().findItem(R.id.nav_perfil_cliente).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_perfil_garcom).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_scan_pedido).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_pedido_garcom).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_historicos).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);

            perfil = (CircleImageView)headerView.findViewById(R.id.img_perfil);

            final int idImagem;

            try {
                //Pegando id da imagem via nome
                idImagem = navigationView.getResources().getIdentifier("standarduser", "drawable", getPackageName());
                //Colocando Imagem de fundo
                Glide.with(this).load(idImagem).thumbnail(Glide.with(this).load(idImagem)).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                        perfil.setBackgroundResource(idImagem);
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }

        }else if (permissao == 1){
            nomeUser = intent.getStringExtra("nome");

            navigationView.getMenu().findItem(R.id.nav_perfil_garcom).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_pedido_garcom).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_cadastro_cliente).setVisible(false);

            perfil = (CircleImageView)headerView.findViewById(R.id.img_perfil);
            title_bem_vindo = (TextView)headerView.findViewById(R.id.title_bem_vindo);

            String titulo = String.format("%s %s",getResources().getString(R.string.title_bem_vindo), nomeUser);

            title_bem_vindo.setText(titulo);

            //Guardando o nome da imagem
            foto = intent.getStringExtra("foto");

            final int idImagem;

            try {
                //Pegando id da imagem via nome
                idImagem = navigationView.getResources().getIdentifier("standarduser", "drawable", getPackageName());
                //Formatando o caminho da foto
                String url = String.format("%s%s",getResources().getString(R.string.url_serverFotoCliente),foto);

                Log.d("test", url);
                //Colocando Imagem de fundo. Exemplo feito por: Gabriel Augusto
                Glide.with(this).load(url).thumbnail(Glide.with(this).load(idImagem)).into(perfil);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (permissao == 2){
            nomeUser = intent.getStringExtra("nome");

            navigationView.getMenu().findItem(R.id.nav_perfil_cliente).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_fale_conosco).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_scan_pedido).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_historicos).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_cadastro_cliente).setVisible(false);

            perfil = (CircleImageView)headerView.findViewById(R.id.img_perfil);
            title_bem_vindo = (TextView)headerView.findViewById(R.id.title_bem_vindo);

            String titulo = String.format("%s %s",getResources().getString(R.string.title_bem_vindo), nomeUser);

            title_bem_vindo.setText(titulo);

            //Guardando o nome da imagem
            foto = intent.getStringExtra("foto");

            final int idImagem;

            try {
                //Pegando id da imagem via nome
                idImagem = navigationView.getResources().getIdentifier("standarduser", "drawable", getPackageName());
                //Formatando o caminho da foto
                String url = String.format("%s%s",getResources().getString(R.string.url_serverFotoFuncionario),foto);
                //Colocando Imagem de fundo. Exemplo feito por: Gabriel Augusto
                Glide.with(this).load(url).thumbnail(Glide.with(this).load(idImagem)).into(perfil);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        manage = getSupportFragmentManager();
        txn = manage.beginTransaction();
        //Cria o fragment
        FragmentViewPager fragment = new FragmentViewPager();

        fragment.addFragment("Home",new FragmentHome());

        txn.replace(R.id.container, fragment);

        txn.commit();

        listTelas.add(0);



        novo_pedido = intent.getIntExtra("novo_pedido",0);

        if (novo_pedido == 1){
            navigationView.getMenu().getItem(3).setChecked(true);
        }else{
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getQtd_produto() {
        return qtd_produto;
    }

    public void setQtd_produto(int qtd_produto) {
        this.qtd_produto = qtd_produto;
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
                itemId = R.id.nav_home;
            }else if (itemMenu == 1){
                itemId = R.id.nav_scan_pedido;
            }else if (itemMenu == 2){
                itemId = R.id.nav_pedido_garcom;
            }else if (itemMenu == 3){
                itemId = R.id.nav_perfil_garcom;
            }else if (itemMenu == 4){
                itemId = R.id.nav_perfil_cliente;
            }else if (itemMenu == 5){
                itemId = R.id.nav_cadastro_cliente;
            }else if (itemMenu == 6){
                itemId = R.id.nav_cardapio;
            }else if (itemMenu == 7) {
                itemId = R.id.nav_fale_conosco;
            }else if (itemMenu == 8){
                itemId = R.id.nav_historicos;
            }else if (itemMenu == 9){
                itemId = R.id.nav_login;
            }else if (itemMenu == 10){
                itemId = R.id.nav_nossos_restaurantes;
            }else{
                itemId = R.id.nav_home;
            }

            previouslyFragment(itemId);

        } else{
            finish();
            super.onBackPressed();
        }
    }

    public void setVerificadorDialog(int verificadorDialog) {
        this.verificadorDialog = verificadorDialog;
    }

    public int getVerificadorDialog(){
        return this.verificadorDialog;
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
        socket = null;

        runnable = null;

        if (id == R.id.nav_home) {
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
        } else if (id == R.id.nav_scan_pedido) {
            //Define o label da activity
            this.setTitle("Scanner");
            listTelas.add(1);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();
            //Substitui o Fragment na tela
            txn.replace(R.id.container, new FragmentPedidoClient());

            txn.commit();
        } else if (id == R.id.nav_pedido_garcom) {
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
        } else if (id == R.id.nav_perfil_garcom) {
            //Define o label da activity
            this.setTitle("Perfil");

            listTelas.add(3);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Perfil",new FragmentPerfilGarcom());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_perfil_cliente) {
            //Define o label da activity
            this.setTitle("Perfil");

            listTelas.add(4);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Perfil",new FragmentPerfilCliente());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_cadastro_cliente) {
            //Define o label da activity
            this.setTitle("Cadastro Cliente");

            listTelas.add(5);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Cadastro Cliente",new FragmentCadastroCliente());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_cardapio) {
            //Define o label da activity
            this.setTitle("Cardápio");

            listTelas.add(6);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Cardápios",new FragmentCardapios());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_fale_conosco) {
            //Define o label da activity
            this.setTitle("Fale Conosco");

            listTelas.add(7);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Fale Conosco",new FragmentFaleConosco());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_historicos) {
            //Define o label da activity
            this.setTitle("Históricos");

            listTelas.add(8);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Históricos",new FragmentHistoricos());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_login) {
            //Define o label da activity
            this.setTitle("Entrar");

            listTelas.add(9);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Entrar",new FragmentLogin());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_nossos_restaurantes) {
            //Define o label da activity
            this.setTitle("Nossos Restaurantes");

            listTelas.add(10);

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Nossos Restaurantes",new FragmentNossosRestaurantes());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_logout) {
            //Refresh
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    //Muda o fragment de acordo com o item do menu
    public void previouslyFragment(int id){
        if (id == R.id.nav_home) {
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
        } else if (id == R.id.nav_scan_pedido) {
            //Define o label da activity
            this.setTitle("Scanner");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();
            //Substitui o Fragment na tela
            txn.replace(R.id.container, new FragmentPedidoClient());

            txn.commit();
        } else if (id == R.id.nav_pedido_garcom) {
            //Define o label da activity
            this.setTitle("Pedidos");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Pedido Garçom",new FragmentPedidoGarcom());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_perfil_garcom) {
            //Define o label da activity
            this.setTitle("Perfil");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Perfil Garçom",new FragmentPerfilGarcom());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_perfil_cliente) {
            //Define o label da activity
            this.setTitle("Pedidos");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Perfil Cliente",new FragmentPerfilCliente());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_cadastro_cliente) {
            //Define o label da activity
            this.setTitle("Cadastro Cliente");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Cadastro Cliente",new FragmentCadastroCliente());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_cardapio) {
            //Define o label da activity
            this.setTitle("Cardapio");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Cardápio",new FragmentCardapios());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_fale_conosco) {
            //Define o label da activity
            this.setTitle("Fale Conosco");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Fale Conosco",new FragmentFaleConosco());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_historicos) {
            //Define o label da activity
            this.setTitle("Históricos");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Históricos",new FragmentHistoricos());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_login) {
            //Define o label da activity
            this.setTitle("Entrar");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Login",new FragmentLogin());

            txn.replace(R.id.container, fragment);

            txn.commit();
        } else if (id == R.id.nav_nossos_restaurantes) {
            //Define o label da activity
            this.setTitle("Nossos Restaurantes");

            manage = getSupportFragmentManager();
            txn = manage.beginTransaction();

            //Cria o fragment
            FragmentViewPager fragment = new FragmentViewPager();

            fragment.addFragment("Nossos Restaurantes",new FragmentNossosRestaurantes());

            txn.replace(R.id.container, fragment);

            txn.commit();
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

    public void backPagePedidoGarcom(){
        manage = getSupportFragmentManager();
        txn = manage.beginTransaction();

        //Cria o fragment
        FragmentViewPager fragment = new FragmentViewPager();

        fragment.addFragment("Novo Pedido",new FragmentNovoPedido());
        fragment.addFragment("Pedido Garçom",new FragmentPedidoGarcom());

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
