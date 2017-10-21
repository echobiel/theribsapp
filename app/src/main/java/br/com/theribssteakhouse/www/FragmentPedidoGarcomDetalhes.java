package br.com.theribssteakhouse.www;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16165846 on 17/10/2017.
 */

public class FragmentPedidoGarcomDetalhes extends Fragment {
    TextView text_mesa;
    TextView text_codigo;
    TextView text_status;
    ImageView img_swipe;
    ImageButton img_clicked;
    MyListView list_pedidos_produtos;
    List<PedidoGarcomProduto> listPedidosDetalhes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_garcom_detalhes, container, false);

        list_pedidos_produtos = (MyListView) view.findViewById(R.id.list_pedidos_produtos);

        listPedidosDetalhes.add(new PedidoGarcomProduto(5, 5,"test", "test","test"));
        listPedidosDetalhes.add(new PedidoGarcomProduto(5, 5,"test", "test","test"));
        listPedidosDetalhes.add(new PedidoGarcomProduto(5, 5,"test", "test","test"));
        listPedidosDetalhes.add(new PedidoGarcomProduto(5, 5,"test", "test","test"));

        ProdutosGarcomAdapter adapter = new ProdutosGarcomAdapter(((MainActivity)getActivity()), listPedidosDetalhes);

        list_pedidos_produtos.setAdapter(adapter);


        return view;
    }

    public void setPedido(int idPedido){

    }
}
