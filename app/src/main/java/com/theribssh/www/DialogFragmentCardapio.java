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
public class DialogFragmentCardapio extends DialogFragment {

    private int numStyle;
    private int numTheme;
    private CardapioPrincipaisAdapter cardapioPrincipaisAdapter;
    List<CardapioPrincipaisListView> card_principais = new ArrayList<>();
    ListView list_produtos;
    Button btn_selecionar_produto;
    Activity act;
    private int id_produto;
    private int id_pedido;
    private int qtd;

    @SuppressLint("ValidFragment")
    public DialogFragmentCardapio(int numStyle, int numTheme){
        this.numStyle = numStyle;
        this.numTheme = numTheme;
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

        View view = inflater.inflate(R.layout.dialog_cardapio, container);

        list_produtos = (ListView) view.findViewById(R.id.list_produtos);
        btn_selecionar_produto = (Button) view.findViewById(R.id.btn_selecionar_produto);

        list_produtos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id_produto = card_principais.get(i).getId_produto();

                int contador = 0;
                while (contador < list_produtos.getChildCount()){
                    list_produtos.getChildAt(contador).setBackgroundColor(getResources().getColor(R.color.corBranco));
                    contador = contador + 1;
                }
                list_produtos.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.corCinza));
                openQuantidade();
            }
        });
        btn_selecionar_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_produto != 0) {
                    ((MainActivity) getActivity()).setId_produto(id_produto);

                    id_produto = ((MainActivity)getActivity()).getId_produto();
                    id_pedido = ((MainActivity)getActivity()).getId_pedido();
                    qtd = ((MainActivity)getActivity()).getQtd_produto();

                    if (id_pedido >= 0 && id_produto != 0 && qtd != 0) {
                        new AdicionarTask().execute();
                    }else{
                        Toast.makeText(act, "Selecione uma quantidade ",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(act, "Selecione um produto", Toast.LENGTH_LONG).show();
                }

            }
        });

        new PegadorTask().execute();

        return view;
    }

    public void openQuantidade(){
        try {
            closeQuantidade();
            FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            DialogFragmentQtd dfq = new DialogFragmentQtd(1, 2);
            dfq.show(ft, "dialog2");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeQuantidade(){
        try {
            FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
            DialogFragmentQtd dfq = (DialogFragmentQtd) ((MainActivity)getActivity())
                    .getSupportFragmentManager().findFragmentByTag("dialog2");

            if (dfq != null){
                dfq.dismiss();
                ft.remove(dfq);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class AdicionarTask extends AsyncTask<Void, Void, Void>
    {
        String href;
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/adicionarProduto?id_produto=%d&qtd=%d&id_pedido=%d",
                    getResources().getString(R.string.ip_node), id_produto, qtd,id_pedido);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dismiss();
        }
    }

    private class PegadorTask extends AsyncTask<Void, Void, Void>
    {
        String href;
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/selectCardapio", getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new Gson();

            card_principais = gson.fromJson(json, new TypeToken<List<CardapioPrincipaisListView>>(){
            }.getType());

            cardapioPrincipaisAdapter = new CardapioPrincipaisAdapter(card_principais, act);
            try {
                list_produtos.setAdapter(cardapioPrincipaisAdapter);
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
