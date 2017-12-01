package com.theribssh.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView txt_precoTotal;
    int id_pedido;
    float total;
    MyListView list_pedidos_produtos;
    List<PedidoGarcomProduto> lpgp = new ArrayList<>();
    SalaPedido resultado;
    Button btn_add_produto;
    Button btn_finalizar_pedido;
    Socket socket;
    Activity act;
    PegadorTask pet;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_garcom_detalhes, container, false);

        act = ((MainActivity)getActivity());

        intent = ((MainActivity)getActivity()).getIntent();

        list_pedidos_produtos = (MyListView) view.findViewById(R.id.list_pedidos_produtos);
        text_mesa = (TextView) view.findViewById(R.id.text_mesa);
        text_codigo = (TextView) view.findViewById(R.id.text_codigo);
        text_status = (TextView) view.findViewById(R.id.text_status);
        text_nome_cliente = (TextView) view.findViewById(R.id.text_nome_cliente);
        txt_precoTotal = (TextView) view.findViewById(R.id.txt_precoTotal);
        btn_add_produto = (Button) view.findViewById(R.id.btn_add_produto);
        btn_finalizar_pedido = (Button) view.findViewById(R.id.btn_finalizar_pedido);

        setupBotoes();

        list_pedidos_produtos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                openOptProduto(lpgp.get(i).getId_produto_pedido());
                return false;
            }
        });

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

                    int id_funcionario = inf.getId_funcionario();
                    int id_usuario = intent.getIntExtra("id_usuario",0);

                    if (id_funcionario == id_usuario) {

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

        socket.connect();

        return view;
    }

    private void setupBotoes() {
        btn_finalizar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPagamento();
            }
        });

        btn_add_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setId_pedido(id_pedido);
                openPedido();
            }
        });
    }

    public void openPedido(){
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentCardapio dfpc = new DialogFragmentCardapio(4,3);
        dfpc.show(ft, "dialog");
    }

    public void openOptProduto(int id_produto_pedido){
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentOptProduto dfop = new DialogFragmentOptProduto(1,2,id_pedido,id_produto_pedido);
        dfop.show(ft, "dialog");
    }


    public void openPagamento(){
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentMetodoPagamento dfmp = new DialogFragmentMetodoPagamento(1,2,id_pedido);
        dfmp.show(ft, "dialog");
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

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

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

                text_mesa.setText(resultado.getMesa());
                text_codigo.setText(resultado.getQr_code());
                text_status.setText(resultado.getStatus_nome());
                text_nome_cliente.setText(resultado.getNome_cliente());

                lpgp = resultado.getProdutos();

                int contador = 0;

                total = 0;

                while (contador < lpgp.size()){
                    if (lpgp.get(contador).getId_produto() != 0) {
                        total = total + lpgp.get(contador).getPreco() * lpgp.get(contador).getQtd();
                    }

                    contador = contador + 1;
                }

                txt_precoTotal.setText(String.format("R$ %.2f", total));

                ProdutosGarcomAdapter adapter = new ProdutosGarcomAdapter(((MainActivity) getActivity()), lpgp);

                list_pedidos_produtos.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
