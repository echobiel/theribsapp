package com.theribssh.www;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by echobiel on 13/11/2017.
 */

@SuppressLint("ValidFragment")
public class DialogFragmentClientLogin extends DialogFragment {

    private int numStyle;
    private int numTheme;
    Activity act;
    int id_pedido;
    int id_funcionario;
    AutenticarUsuario resultado;
    EditText edit_text_login;
    EditText edit_text_senha;
    String urlStrings;
    String email;
    String senha;
    FloatingActionButton fab;
    View view;

    @SuppressLint("ValidFragment")
    public DialogFragmentClientLogin(int numStyle, int numTheme, int id_pedido, int id_funcionario){
        this.numStyle = numStyle;
        this.numTheme = numTheme;
        this.id_pedido = id_pedido;
        this.id_funcionario = id_funcionario;
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

        view = inflater.inflate(R.layout.activity_login, container);

        edit_text_login = (EditText)view.findViewById(R.id.edit_text_login);
        edit_text_senha = (EditText)view.findViewById(R.id.edit_text_senha);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edit_text_login.getText().toString();
                senha = edit_text_senha.getText().toString();

                email = email.replaceAll(" ", "+");
                senha = senha.replaceAll(" ", "+");

                urlStrings = String.format("email=%s&senha=%s&id_pedido=%d", email, senha, id_pedido);

                new AutenticadorTask().execute();
            }
        });

        return view;
    }

    public void openMesa(){
        try {
            FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            DialogFragmentMesa dfm = new DialogFragmentMesa(1, 2, id_pedido, id_funcionario);
            dfm.show(ft, "dialog");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeMesa(){
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentMesa dfm = (DialogFragmentMesa) ((MainActivity)getActivity())
                .getSupportFragmentManager().findFragmentByTag("dialog");

        if (dfm != null){
            dfm.dismiss();
            ft.remove(dfm);
        }
    }

    private class AutenticadorTask extends AsyncTask <Void, Void, Void>{

        String json;
        String href;

        @Override
        protected Void doInBackground(Void... params) {
            href = String.format("http://%s/autenticarUsuarioSala?%s",getResources().getString(R.string.ip_node),urlStrings);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<AutenticarUsuario>() {
                }.getType());

                if (resultado.getMensagem().equals("Usuário ou senha incorretos. Verifique e tente novamente.")) {
                    Snackbar.make(view, resultado.getMensagem(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, resultado.getMensagem(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    dismiss();

                    openMesa();
                }
            }catch(Exception e){
                Toast.makeText(((MainActivity)getActivity()),"Ocorreu um erro de conexão. Tente novamente mais tarde.",Toast.LENGTH_LONG).show();
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
