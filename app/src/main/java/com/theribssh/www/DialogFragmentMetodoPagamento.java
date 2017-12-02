package com.theribssh.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echobiel on 13/11/2017.
 */

@SuppressLint("ValidFragment")
public class DialogFragmentMetodoPagamento extends DialogFragment {

    private int numStyle;
    private int numTheme;
    private int id_pedido;
    private Spinner spinner_cartao;
    private Button btn_salvar_mesa;
    private TextView text_titulo_pg;
    private TextView text_view_saldo;
    private String metodo;
    private MetodoFisico resultadoF;

    @SuppressLint("ValidFragment")
    public DialogFragmentMetodoPagamento(int numStyle, int numTheme, int id_pedido){
        this.numStyle = numStyle;
        this.numTheme = numTheme;
        this.id_pedido = id_pedido;
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

        View view = inflater.inflate(R.layout.dialog_pagamento, container);

        spinner_cartao = (Spinner) view.findViewById(R.id.spinner_mesa);
        btn_salvar_mesa = (Button) view.findViewById(R.id.btn_salvar_mesa);
        text_titulo_pg = (TextView) view.findViewById(R.id.text_titulo_pg);
        text_view_saldo = (TextView) view.findViewById(R.id.text_view_saldo);

        text_titulo_pg.setText("Indique a forma de pagamento:");

        ArrayAdapter bandeira = ArrayAdapter.createFromResource(((MainActivity)getActivity()), R.array.metodos_pagamento, R.layout.spinner_padrao);
        spinner_cartao.setAdapter(bandeira);

        new SaldoTask().execute();

        setupBotao();

        return view;
    }

    private void setupBotao() {
        btn_salvar_mesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodo = spinner_cartao.getSelectedItem().toString();

                if (metodo.equals("Cartão (Virtual)")){
                    new FinalizarVirtualTask().execute();
                }else{
                    new FinalizarFisicoTask().execute();
                }
            }
        });
    }

    public void openFeedback(){
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentFeedback dff = new DialogFragmentFeedback(1,2,id_pedido);
        dff.show(ft, "dialog");
    }

    private class SaldoTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/saldoCliente?id_sala=%d",getResources().getString(R.string.ip_node),id_pedido);
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

                text_view_saldo.setText(mensagem.getMensagem());

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class FinalizarFisicoTask extends AsyncTask<Void,Void,Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/finalizarPedidoFisico?id_sala=%d",getResources().getString(R.string.ip_node),id_pedido);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                resultadoF = gson.fromJson(json, new TypeToken<MetodoFisico>() {
                }.getType());

                id_pedido = resultadoF.getId_pedido();

                dismiss();

                openFeedback();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class FinalizarVirtualTask extends AsyncTask<Void,Void,Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/finalizarPedidoVirtual?id_sala=%d",getResources().getString(R.string.ip_node),id_pedido);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                resultadoF = gson.fromJson(json, new TypeToken<MetodoFisico>() {
                }.getType());

                String msg = resultadoF.getMensagem();

                if (msg.equals("Esta opção não é possível a este usuário.")){
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                }else {

                    id_pedido = resultadoF.getId_pedido();

                    dismiss();

                    openFeedback();
                }

            }catch (Exception e){
                e.printStackTrace();
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
