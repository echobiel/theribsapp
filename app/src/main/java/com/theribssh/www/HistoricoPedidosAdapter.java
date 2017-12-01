package com.theribssh.www;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

/**
 * Created by 16165824 on 17/10/2017.
 */

public class HistoricoPedidosAdapter extends BaseAdapter{

    List<Pedidos> listPedidos;
    Activity act;

    public HistoricoPedidosAdapter(List<Pedidos> listPedidos, Activity act){
        this.act = act;
        this.listPedidos = listPedidos;
    }

    @Override
    public int getCount() {
        return listPedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return listPedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.historico_pedidos_list_item, parent, false);

        Pedidos pedido = listPedidos.get(position);

        TextView txt_restaurante = (TextView) view.findViewById(R.id.txt_restaurante);
        TextView txt_qtd_produtos = (TextView) view.findViewById(R.id.txt_qtd_produtos);
        TextView txt_data = (TextView) view.findViewById(R.id.txt_data);

        txt_restaurante.setText(pedido.getRestaurante());
        txt_qtd_produtos.setText(pedido.getQtd() + " Produtos");
        txt_data.setText(pedido.getData() + "");

        return view;
    }
}
