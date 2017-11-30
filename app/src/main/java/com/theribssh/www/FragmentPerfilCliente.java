package com.theribssh.www;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentPerfilCliente extends Fragment {

    ImageView img_user;
    TextView text_view_login;
    TextView text_view_nome;
    TextView text_view_celular;
    TextView text_view_telefone;
    TextView text_view_email;
    TextView text_view_logradouro;
    TextView text_view_numero;
    TextView text_view_bairro;
    TextView text_view_rua;
    TextView text_view_bandeira_cartao;
    TextView text_view_nome_cartao;
    TextView text_view_numero_cartao;
    TextView text_view_cvv_cartao;
    TextView text_view_vencimento_cartao;
    List<UsuarioPerfilCliente> resultado = new ArrayList<>();
    int id_usuario;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_perfil_cliente, container, false);

        img_user = (ImageView)view.findViewById(R.id.img_user);
        text_view_login = (TextView) view.findViewById(R.id.text_view_login);
        text_view_nome = (TextView) view.findViewById(R.id.text_view_nome);
        text_view_celular = (TextView) view.findViewById(R.id.text_view_celular);
        text_view_telefone = (TextView) view.findViewById(R.id.text_view_telefone);
        text_view_email = (TextView) view.findViewById(R.id.text_view_email);
        text_view_logradouro = (TextView) view.findViewById(R.id.text_view_logradouro);
        text_view_numero = (TextView) view.findViewById(R.id.text_view_numero);
        text_view_bairro = (TextView) view.findViewById(R.id.text_view_bairro);
        text_view_rua = (TextView) view.findViewById(R.id.text_view_rua);
        text_view_bandeira_cartao = (TextView) view.findViewById(R.id.text_view_bandeira_cartao);
        text_view_nome_cartao = (TextView) view.findViewById(R.id.text_view_nome_cartao);
        text_view_numero_cartao = (TextView) view.findViewById(R.id.text_view_numero_cartao);
        text_view_cvv_cartao = (TextView) view.findViewById(R.id.text_view_cvv_cartao);
        text_view_vencimento_cartao = (TextView) view.findViewById(R.id.text_view_vencimento_cartao);

        Intent intent = ((MainActivity)getActivity()).getIntent();

        id_usuario = intent.getIntExtra("id_usuario",0);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEdit();
            }
        });

        new PegadorTask().execute();

        return view;
    }

    public void openEdit(){
        try {
            FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            DialogFragmentClientUpdate dfm = new DialogFragmentClientUpdate(1, 2, resultado);
            dfm.show(ft, "dialog");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String json;
        String href;

        @Override
        protected Void doInBackground(Void... params) {
            href = String.format("http://%s/selectCliente?id_usuario=%d",getResources().getString(R.string.ip_node), id_usuario);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<List<UsuarioPerfilCliente>>() {
                }.getType());

                text_view_bairro.setText(resultado.get(0).getBairro());
                text_view_bandeira_cartao.setText(resultado.get(0).getBandeira());
                text_view_celular.setText(resultado.get(0).getCelular());
                text_view_cvv_cartao.setText(resultado.get(0).getCvv());
                text_view_email.setText(resultado.get(0).getEmail());
                text_view_login.setText(resultado.get(0).getLogin());
                text_view_logradouro.setText(resultado.get(0).getLogradouro());
                text_view_nome.setText(resultado.get(0).getNome() + " " + resultado.get(0).getSobrenome());
                text_view_nome_cartao.setText(resultado.get(0).getNomereg());
                text_view_numero.setText(resultado.get(0).getNumero());
                text_view_numero_cartao.setText(resultado.get(0).getNumerocartao());
                text_view_rua.setText(resultado.get(0).getRua());
                text_view_telefone.setText(resultado.get(0).getTelefone());
                text_view_vencimento_cartao.setText(resultado.get(0).getVencimento());

                //Guardando o nome da imagem
                String nameImage = resultado.get(0).getFoto();

                try {
                    //Colocando Imagem de fundo
                    Glide.with(view).load(String.format("%s%s", ((MainActivity)getActivity()).getResources().getString(R.string.url_serverFotoFuncionario), nameImage)).thumbnail(Glide.with(view).load(R.drawable.loading)).into(img_user);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(((MainActivity)getActivity()),"Ocorreu um erro de conexão. Tente novamente mais tarde.",Toast.LENGTH_LONG).show();
                Log.d("href", href);
            }

        }
    }
}
