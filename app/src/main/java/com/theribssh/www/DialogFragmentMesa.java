package com.theribssh.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
public class DialogFragmentMesa extends DialogFragment {

    private int numStyle;
    private int numTheme;
    private int id_pedido;
    private int id_funcionario;
    private Spinner dialog_mesa;
    private Button btn_salvar_mesa;
    private Mesa mesaSelecionada;
    private Mesa resultado;
    private List<Mesa> mesas = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public DialogFragmentMesa(int numStyle, int numTheme, int id_pedido, int id_funcionario){
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
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.dialog_mesa, container);

        dialog_mesa = (Spinner) view.findViewById(R.id.spinner_mesa);
        btn_salvar_mesa = (Button) view.findViewById(R.id.btn_salvar_mesa);

        new PegadorTask().execute();

        setupBotao();

        return view;
    }

    private void setupBotao() {
        btn_salvar_mesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesaSelecionada = (Mesa)dialog_mesa.getSelectedItem();
                new SelecionarMesaTask().execute();
            }
        });
    }

    public class SelecionarMesaTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/adicionarMesa?id_mesa=%d&nome=%s&id_pedido=%d",
                    getResources().getString(R.string.ip_node),
                    mesaSelecionada.getId_mesa(),
                    mesaSelecionada.getNome(),
                    id_pedido);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<Mesa>() {
                }.getType());

                if (resultado.getNome().equals("Ocorreu um erro. Tente novamente mais tarde.")){
                    Toast.makeText(((MainActivity)getActivity()), resultado.getNome(), Toast.LENGTH_LONG).show();
                }else{
                    dismiss();
                    trocaDeTela();
                }

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
            }
        }
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/mesas?id_funcionario=%d",getResources().getString(R.string.ip_node), id_funcionario);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                mesas = gson.fromJson(json, new TypeToken<List<Mesa>>() {
                }.getType());

                if (mesas.size() > 0){
                    ArrayAdapter<Mesa> mesaArrayAdapter =
                            new ArrayAdapter<>(((MainActivity)getActivity()), R.layout.support_simple_spinner_dropdown_item, mesas);

                    dialog_mesa.setAdapter(mesaArrayAdapter);
                }

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
            }
        }
    }

    public void trocaDeTela(){
        ((MainActivity)getActivity()).detalhesPedidoGarcom(id_pedido);
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
