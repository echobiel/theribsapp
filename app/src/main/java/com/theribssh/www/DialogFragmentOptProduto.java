package com.theribssh.www;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echobiel on 13/11/2017.
 */

@SuppressLint("ValidFragment")
public class DialogFragmentOptProduto extends DialogFragment {

    private int numStyle;
    private int numTheme;
    Button btn_sim;
    Button btn_nao;
    Activity act;
    private int id_pedido;
    private int id_produto_pedido;

    @SuppressLint("ValidFragment")
    public DialogFragmentOptProduto(int numStyle, int numTheme, int id_pedido, int id_produto_pedido){
        this.numStyle = numStyle;
        this.numTheme = numTheme;
        this.id_pedido = id_pedido;
        this.id_produto_pedido = id_produto_pedido;
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

        act = ((MainActivity)getActivity());

        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.dialog_excluir, container);

        btn_sim = (Button) view.findViewById(R.id.btn_sim);
        btn_nao = (Button) view.findViewById(R.id.btn_nao);

        btn_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ExcluirTask().execute();
            }
        });

        btn_nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    private class ExcluirTask extends AsyncTask<Void, Void, Void>
    {
        String href;
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/excluirProduto?id_pedido=%d&id_produto_pedido=%d", getResources().getString(R.string.ip_node), id_pedido, id_produto_pedido);
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

                Toast.makeText(getActivity(), mensagem.getMensagem(), Toast.LENGTH_LONG).show();

                dismiss();
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
