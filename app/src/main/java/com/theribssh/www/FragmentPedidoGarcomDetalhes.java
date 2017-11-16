package com.theribssh.www;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by 16165846 on 17/10/2017.
 */

public class FragmentPedidoGarcomDetalhes extends Fragment {
    TextView text_mesa;
    TextView text_codigo;
    TextView text_status;
    TextView text_nome_cliente;
    int id_pedido;
    MyListView list_pedidos_produtos;
    List<PedidoGarcomProduto> listPedidosDetalhes = new ArrayList<>();
    SalaPedido resultado;
    Button btn_add_produto;
    Socket socket;
    Activity act;
    PegadorTask pet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_garcom_detalhes, container, false);

        act = ((MainActivity)getActivity());

        list_pedidos_produtos = (MyListView) view.findViewById(R.id.list_pedidos_produtos);
        text_mesa = (TextView) view.findViewById(R.id.text_mesa);
        text_codigo = (TextView) view.findViewById(R.id.text_codigo);
        text_status = (TextView) view.findViewById(R.id.text_status);
        text_nome_cliente = (TextView) view.findViewById(R.id.text_nome_cliente);
        btn_add_produto = (Button) view.findViewById(R.id.btn_add_produto);

        btn_add_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setId_pedido(id_pedido);
                openPedido();
            }
        });

        pet = new PegadorTask();
        pet.execute();

        socket = conectarSocket();

        socket.on("novo_produto", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                if (args.length > 0) {

                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pet != null){
                                pet = new PegadorTask();
                                pet.execute();
                            }
                        }
                    });

                }
            }
        });

        socket.connect();

        return view;
    }

    public void openPedido(){
        closePedido();
        ((MainActivity)getActivity()).setVerificadorDialog(1);
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentCardapio dfpc = new DialogFragmentCardapio(4,3);
        dfpc.show(ft, "dialog");
    }

    public void closePedido(){
        ((MainActivity)getActivity()).setVerificadorDialog(0);
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentCardapio dfm = (DialogFragmentCardapio) ((MainActivity)getActivity())
                .getSupportFragmentManager().findFragmentByTag("dialog");

        if (dfm != null){
            dfm.dismiss();
            ft.remove(dfm);
        }
    }

    public Socket conectarSocket(){
        try{
            socket = IO.socket(String.format("http://%s",getResources().getText(R.string.ip_node)));
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return socket;
    }

    public void setPedido(int idPedido){
        this.id_pedido = idPedido;
    }

    public class NovoProdutoTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/novoProduto?id_sala=%d",getResources().getString(R.string.ip_node),id_pedido);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<SalaPedido>() {
                }.getType());

                text_mesa.setText(resultado.getMesa());
                text_codigo.setText(resultado.getQr_code());
                text_status.setText(resultado.getStatus_nome());
                text_nome_cliente.setText(resultado.getNome_cliente());

                List<PedidoGarcomProduto> lpgp = resultado.getProdutos();

                ProdutosGarcomAdapter adapter = new ProdutosGarcomAdapter(((MainActivity) getActivity()), lpgp);

                list_pedidos_produtos.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/listarDetalhesPedido?id_sala=%d",getResources().getString(R.string.ip_node),id_pedido);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<SalaPedido>() {
                }.getType());

                text_mesa.setText(resultado.getMesa());
                text_codigo.setText(resultado.getQr_code());
                text_status.setText(resultado.getStatus_nome());
                text_nome_cliente.setText(resultado.getNome_cliente());

                List<PedidoGarcomProduto> lpgp = resultado.getProdutos();

                ProdutosGarcomAdapter adapter = new ProdutosGarcomAdapter(((MainActivity) getActivity()), lpgp);

                list_pedidos_produtos.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
