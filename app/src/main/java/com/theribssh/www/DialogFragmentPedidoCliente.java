package com.theribssh.www;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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
public class DialogFragmentPedidoCliente extends DialogFragment {

    private int numStyle;
    private int numTheme;
    Activity act;
    Socket socket;
    MyListView list_pedidos_produtos;
    SalaPedido resultado;
    TextView text_precoTotal;
    Intent intent;
    int id_pedido;
    float total;
    Button btn_add_produto;
    Button btn_chamar_garcom;
    PegadorTask pet;

    @SuppressLint("ValidFragment")
    public DialogFragmentPedidoCliente(int numStyle, int numTheme, int id_pedido){
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
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Script", "onCreateView()");

        act = ((MainActivity)getActivity());

        intent = act.getIntent();

        total = 0;

        View view = inflater.inflate(R.layout.dialog_pedido_cliente, container);

        list_pedidos_produtos = (MyListView)view.findViewById(R.id.list_produtos);
        text_precoTotal = (TextView)view.findViewById(R.id.text_precoTotal);
        btn_add_produto = (Button) view.findViewById(R.id.btn_add_produto);
        btn_chamar_garcom = (Button) view.findViewById(R.id.btn_chamar_garcom);

        text_precoTotal.setText(String.format("R$ %.2f", total));

        setupBotoes();

        pet= new PegadorTask();
        pet.execute();

        socket = conectarSocket();

        socket.on("novo_produto", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                if (args.length > 0) {

                    String json = args[0].toString();

                    Gson gson = new Gson();
                    VerificacaoId inf = gson.fromJson(json, new TypeToken<VerificacaoId>() {
                    }.getType());

                    int id_cliente = inf.getId_cliente();
                    int id_usuario = intent.getIntExtra("id_usuario",0);

                    if (id_cliente == id_usuario) {

                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (pet != null) {
                                    pet = new PegadorTask();
                                    pet.execute();
                                }
                            }
                        });
                    }

                }
            }
        });

        socket.on("pedido_finalizado", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                if (args.length > 0){

                    int id_cliente = Integer.parseInt(args[0].toString());

                    if (id_cliente == intent.getIntExtra("id_usuario",0)) {
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                startActivity(intent);
                            }
                        });
                    }

                }
            }
        });

        socket.connect();

        return view;
    }



    public Socket conectarSocket(){
        try{
            socket = IO.socket(String.format("http://%s",getResources().getText(R.string.ip_node)));
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return socket;
    }

    public void openPedido(){
        try {
            FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            DialogFragmentCardapio dfpc = new DialogFragmentCardapio(4, 3);
            dfpc.show(ft, "dialog");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setupBotoes() {

        btn_add_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setId_pedido(id_pedido);
                openPedido();
            }
        });
        btn_chamar_garcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChamarGarcomTask().execute();
            }
        });
    }

    public class ChamarGarcomTask extends AsyncTask<Void, Void ,Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                href = String.format("http://%s/chamarGarcom?id_sala=%d", getResources().getString(R.string.ip_node), id_pedido);
                json = HttpConnection.get(href);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void> {

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                href = String.format("http://%s/listarDetalhesPedido?id_sala=%d", getResources().getString(R.string.ip_node), id_pedido);
                json = HttpConnection.get(href);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<SalaPedido>() {
                }.getType());

                List<PedidoGarcomProduto> lpgp = resultado.getProdutos();

                int contador = 0;

                total = 0;

                while (contador < lpgp.size()){

                    total = total + lpgp.get(contador).getPreco() * lpgp.get(contador).getQtd();

                    contador = contador + 1;
                }

                text_precoTotal.setText(String.format("R$ %.2f", total));

                ProdutosGarcomAdapter adapter = new ProdutosGarcomAdapter(((MainActivity) getActivity()), lpgp);

                list_pedidos_produtos.setAdapter(adapter);
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
