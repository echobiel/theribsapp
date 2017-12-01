package com.theribssh.www;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echobiel on 13/11/2017.
 */

@SuppressLint("ValidFragment")
public class DialogFragmentClientUpdate extends DialogFragment {

    private int numStyle;
    private int numTheme;
    Activity act;
    Spinner spinner_bandeira_cartao;
    Spinner spinner_estado;
    Spinner spinner_cidade;
    EditText edit_text_login;
    EditText edit_text_senha;
    EditText edit_text_cfmsenha;
    EditText edit_text_telefone;
    EditText edit_text_celular;
    EditText edit_text_logradouro;
    EditText edit_text_numero;
    EditText edit_text_bairro;
    EditText edit_text_nome_cartao;
    EditText edit_text_numero_cartao;
    EditText edit_text_cvv;
    EditText edit_text_rua;
    EditText edit_text_senhaatual;
    EditText edit_text_vencimento_cartao;
    List<Banco> bancos = new ArrayList<>();
    List<Estado> estados = new ArrayList<>();
    List<Cidade> cidades = new ArrayList<>();
    TextWatcher dtValMask;
    TextWatcher cvvMask;
    TextWatcher celularMask;
    TextWatcher telefoneMask;
    TextWatcher cartaoMask;
    int id_estado;
    AutenticarUsuario resultado;
    EditText edit_text_email;
    EditText edit_text_nome;
    EditText edit_text_sobrenome;
    String urlStrings;
    String nome;
    int id_cliente;
    List<UsuarioPerfilCliente> user;
    View view;

    @SuppressLint("ValidFragment")
    public DialogFragmentClientUpdate(int numStyle, int numTheme, List<UsuarioPerfilCliente> user){
        this.numStyle = numStyle;
        this.numTheme = numTheme;
        this.user = user;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreate()");

        int style;
        int theme;

        switch(numStyle){
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_INPUT; break;
            case 3: style = DialogFragment.STYLE_NO_FRAME; break;
            default: style = DialogFragment.STYLE_NORMAL; break;
        }

        switch(numTheme){
            case 1: theme = android.R.style.Theme_Holo_Light; break;
            case 2: theme = android.R.style.Theme_Holo_Dialog; break;
            default: theme = android.R.style.Theme_Holo_Light_DarkActionBar; break;
        }

        setStyle(style, theme);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Script", "onCreateView()");

        act = ((MainActivity)getActivity());

        Intent intent = act.getIntent();

        id_cliente = intent.getIntExtra("id_usuario",0);

        view = inflater.inflate(R.layout.activity_update_cliente, container);

        spinner_bandeira_cartao = (Spinner)view.findViewById(R.id.spinner_bandeira_cartao);
        spinner_estado = (Spinner)view.findViewById(R.id.spinner_estado);
        spinner_cidade = (Spinner)view.findViewById(R.id.spinner_cidade);
        edit_text_login = (EditText)view.findViewById(R.id.edit_text_login);
        edit_text_nome = (EditText)view.findViewById(R.id.edit_text_nome);
        edit_text_sobrenome = (EditText)view.findViewById(R.id.edit_text_sobrenome);
        edit_text_senha = (EditText)view.findViewById(R.id.edit_text_senha);
        edit_text_senhaatual = (EditText)view.findViewById(R.id.edit_text_senhaatual);
        edit_text_cfmsenha = (EditText)view.findViewById(R.id.edit_text_cfmsenha);
        edit_text_celular = (EditText)view.findViewById(R.id.edit_text_celular);
        edit_text_telefone = (EditText)view.findViewById(R.id.edit_text_telefone);
        edit_text_email = (EditText)view.findViewById(R.id.edit_text_email);
        edit_text_logradouro = (EditText)view.findViewById(R.id.edit_text_logradouro);
        edit_text_numero = (EditText)view.findViewById(R.id.edit_text_numero);
        edit_text_rua = (EditText)view.findViewById(R.id.edit_text_rua);
        edit_text_bairro = (EditText)view.findViewById(R.id.edit_text_bairro);
        edit_text_nome_cartao = (EditText)view.findViewById(R.id.edit_text_nome_cartao);
        edit_text_numero_cartao = (EditText)view.findViewById(R.id.edit_text_numero_cartao);
        edit_text_cvv = (EditText)view.findViewById(R.id.edit_text_cvv);
        edit_text_vencimento_cartao = (EditText)view.findViewById(R.id.edit_text_vencimento_cartao);

        dtValMask = Mask.insert("##/####",edit_text_vencimento_cartao);
        cvvMask = Mask.insert("####",edit_text_cvv);
        celularMask = Mask.insert("(##)#####-####",edit_text_celular);
        telefoneMask = Mask.insert("(##)####-####",edit_text_telefone);
        cartaoMask = Mask.insert("################",edit_text_numero_cartao);

        edit_text_vencimento_cartao.addTextChangedListener(dtValMask);
        edit_text_cvv.addTextChangedListener(cvvMask);
        edit_text_celular.addTextChangedListener(celularMask);
        edit_text_telefone.addTextChangedListener(telefoneMask);
        edit_text_numero_cartao.addTextChangedListener(cartaoMask);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String senha = edit_text_senha.getText().toString();
                    String cfmsenha = edit_text_cfmsenha.getText().toString();

                    if (senha.equals(cfmsenha)) {

                        urlStrings = String.format("?login=%s&senha=%s&nome=%s&sobrenome=%s&celular=%s&telefone=%s&email=%s&logradouro=%s&numero=%s&bairro=%s&nome_cartao=%s&numero_cartao=%s&cvv=%s&vencimento=%s&rua=%s&id_banco=%d&id_cidade=%d&senhaatual=%s&id_cliente=%d",
                                edit_text_login.getText().toString().replace(" ", "+"),
                                edit_text_senha.getText().toString().replace(" ", "+"),
                                edit_text_nome.getText().toString().replace(" ", "+"),
                                edit_text_sobrenome.getText().toString().replace(" ", "+"),
                                edit_text_celular.getText().toString().replace(" ", "+"),
                                edit_text_telefone.getText().toString().replace(" ", "+"),
                                edit_text_email.getText().toString().replace(" ", "+"),
                                edit_text_logradouro.getText().toString().replace(" ", "+"),
                                edit_text_numero.getText().toString().replace(" ", "+"),
                                edit_text_bairro.getText().toString().replace(" ", "+"),
                                edit_text_nome_cartao.getText().toString().replace(" ", "+"),
                                edit_text_numero_cartao.getText().toString().replace(" ", "+"),
                                edit_text_cvv.getText().toString().replace(" ", "+"),
                                edit_text_vencimento_cartao.getText().toString().replace(" ", "+"),
                                edit_text_rua.getText().toString().replace(" ", "+"),
                                ((Banco) spinner_bandeira_cartao.getSelectedItem()).getId_banco(),
                                ((Cidade) spinner_cidade.getSelectedItem()).getId_cidade(),
                                edit_text_senhaatual.getText().toString().replace(" ", "+"),
                                id_cliente);

                        Log.d("urlStrings", urlStrings);

                        new UpdateTask().execute();

                    }else{
                        Toast.makeText(getActivity(), "Confirmação de senha incorreta.", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        new PegadorBancoTask().execute();
        new PegadorEstadoTask().execute();

        PreencherDados();

        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_estado = estados.get(i).getId_estado();

                new PegadorCidadeTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void PreencherDados() {
        edit_text_bairro.setText(user.get(0).getBairro());
        edit_text_celular.setText(user.get(0).getCelular());
        edit_text_cvv.setText(user.get(0).getCvv());
        edit_text_email.setText(user.get(0).getEmail());
        edit_text_login.setText(user.get(0).getLogin());
        edit_text_logradouro.setText(user.get(0).getLogradouro());
        edit_text_nome.setText(user.get(0).getNome());
        edit_text_sobrenome.setText(user.get(0).getSobrenome());
        edit_text_nome_cartao.setText(user.get(0).getNomereg());
        edit_text_numero.setText(user.get(0).getNumero());
        edit_text_numero_cartao.setText(user.get(0).getNumerocartao());
        edit_text_rua.setText(user.get(0).getRua());
        edit_text_telefone.setText(user.get(0).getTelefone());
        edit_text_vencimento_cartao.setText(user.get(0).getVencimento());
    }

    public void setSpinnerItemByIdEstado(Spinner spinner, int _id)
    {
        int spinnerCount = spinner.getCount();
        for (int i = 0; i < spinnerCount; i++)
        {
            Estado value = (Estado) spinner.getItemAtPosition(i);
            int id = value.getId_estado();

            if (id == _id)
            {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void setSpinnerItemByIdCidade(Spinner spinner, int _id)
    {
        int spinnerCount = spinner.getCount();
        for (int i = 0; i < spinnerCount; i++)
        {
            Cidade value = (Cidade) spinner.getItemAtPosition(i);
            long id = value.getId_cidade();
            if (id == _id)
            {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public class UpdateTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... params) {
            String href = String.format("http://%s/updateUsuario%s", getResources().getString(R.string.ip_node), urlStrings);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                Mensagem mensagem = gson.fromJson(json, new TypeToken<Mensagem>() {
                }.getType());

                Toast.makeText(getActivity(), mensagem.getMensagem(), Toast.LENGTH_SHORT).show();

                dismiss();

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
                Toast.makeText(getActivity(), "Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class PegadorBancoTask extends AsyncTask<Void, Void, Void>{

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
                Toast.makeText(getActivity(), "Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class PegadorEstadoTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/selectEstados",getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                estados = gson.fromJson(json, new TypeToken<List<Estado>>() {
                }.getType());

                if (estados.size() > 0){
                    ArrayAdapter<Estado> mesaArrayAdapter =
                            new ArrayAdapter<>(((MainActivity)getActivity()), R.layout.spinner_padrao, estados);

                    spinner_estado.setAdapter(mesaArrayAdapter);
                }

                id_estado = estados.get(0).getId_estado();

                new PegadorCidadeTask().execute();
            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
                Toast.makeText(getActivity(), "Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        }
    }
    public class PegadorCidadeTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/selectCidades?id_estado=%d",getResources().getString(R.string.ip_node), id_estado);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                cidades = gson.fromJson(json, new TypeToken<List<Cidade>>() {
                }.getType());

                if (cidades.size() > 0){
                    ArrayAdapter<Cidade> mesaArrayAdapter =
                            new ArrayAdapter<>(((MainActivity)getActivity()), R.layout.spinner_padrao, cidades);

                    spinner_cidade.setAdapter(mesaArrayAdapter);
                }

                setSpinnerItemByIdEstado(spinner_estado, user.get(0).getId_estado());
                setSpinnerItemByIdCidade(spinner_cidade, user.get(0).getId_cidade());

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
                Toast.makeText(getActivity(), "Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Script", "onActivityCreated()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Script", "onAttach()");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i("Script", "onCancel()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Script", "onDestroy()");
    }
}
