package com.theribssh.www;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FragmentCadastroCliente extends Fragment {

    Spinner spinner_bandeira_cartao;
    EditText edit_text_login;
    EditText edit_text_nome;
    EditText edit_text_sobrenome;
    EditText edit_text_telefone;
    EditText edit_text_celular;
    EditText edit_text_email;
    EditText edit_text_logradouro;
    EditText edit_text_numero;
    EditText edit_text_bairro;
    EditText edit_text_nome_cartao;
    EditText edit_text_numero_cartao;
    EditText edit_text_cvv;
    EditText edit_text_vencimento_cartao;
    String urlStrings;
    List<Banco> bancos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente, container, false);

        spinner_bandeira_cartao = (Spinner)view.findViewById(R.id.spinner_bandeira_cartao);
        edit_text_login = (EditText)view.findViewById(R.id.edit_text_login);
        edit_text_nome = (EditText)view.findViewById(R.id.edit_text_nome);
        edit_text_sobrenome = (EditText)view.findViewById(R.id.edit_text_sobrenome);
        edit_text_celular = (EditText)view.findViewById(R.id.edit_text_celular);
        edit_text_telefone = (EditText)view.findViewById(R.id.edit_text_telefone);
        edit_text_email = (EditText)view.findViewById(R.id.edit_text_email);
        edit_text_logradouro = (EditText)view.findViewById(R.id.edit_text_logradouro);
        edit_text_numero = (EditText)view.findViewById(R.id.edit_text_numero);
        edit_text_bairro = (EditText)view.findViewById(R.id.edit_text_bairro);
        edit_text_nome_cartao = (EditText)view.findViewById(R.id.edit_text_nome_cartao);
        edit_text_numero_cartao = (EditText)view.findViewById(R.id.edit_text_numero_cartao);
        edit_text_cvv = (EditText)view.findViewById(R.id.edit_text_cvv);
        edit_text_vencimento_cartao = (EditText)view.findViewById(R.id.edit_text_vencimento_cartao);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlStrings = String.format("?login=%s&nome=%s&sobrenome=%s&celular=%s&telefone=%s&email=%s&logradouro=%s&numero=%s&bairro=%s&nome_cartao=%s&numero_cartao=%s&cvv=%s&vencimento=%s&id_banco=%s",
                        edit_text_login.getText().toString(),
                        edit_text_nome.getText().toString(),
                        edit_text_sobrenome.getText().toString(),
                        edit_text_celular.getText().toString(),
                        edit_text_telefone.getText().toString(),
                        edit_text_email.getText().toString(),
                        edit_text_logradouro.getText().toString(),
                        edit_text_numero.getText().toString(),
                        edit_text_bairro.getText().toString(),
                        edit_text_nome_cartao.getText().toString(),
                        edit_text_numero_cartao.getText().toString(),
                        edit_text_cvv.getText().toString(),
                        edit_text_vencimento_cartao.getText().toString(),
                        ((Banco)spinner_bandeira_cartao.getSelectedItem()).getId_banco());

                new InserirTask().execute();
            }
        });

        new PegadorTask().execute();

        return view;
    }

    public class InserirTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... params) {
            String href = String.format("http://%s/cadastrarUsuario%s", getResources().getString(R.string.ip_node), urlStrings);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/selectBancos",getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                bancos = gson.fromJson(json, new TypeToken<List<Banco>>() {
                }.getType());

                if (bancos.size() > 0){
                    ArrayAdapter<Banco> mesaArrayAdapter =
                            new ArrayAdapter<>(((MainActivity)getActivity()), R.layout.spinner_padrao, bancos);

                    spinner_bandeira_cartao.setAdapter(mesaArrayAdapter);
                }

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
            }
        }
    }
}
