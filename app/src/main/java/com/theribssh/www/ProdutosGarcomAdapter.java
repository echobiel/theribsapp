package com.theribssh.www;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 16165846 on 18/10/2017.
 */

public class ProdutosGarcomAdapter extends BaseAdapter {

    List<PedidoGarcomProduto> pedidoGarcomProdutos;
    Activity act;

    public ProdutosGarcomAdapter(Activity act, List<PedidoGarcomProduto> pedidoGarcomProdutos){
        this.pedidoGarcomProdutos = pedidoGarcomProdutos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return pedidoGarcomProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidoGarcomProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.pedido_garcom_produto_item, parent, false);

        PedidoGarcomProduto menu = pedidoGarcomProdutos.get(position);

        TextView txt_nome = (TextView) view.findViewById(R.id.txt_nome);
        TextView txt_preco = (TextView) view.findViewById(R.id.txt_preco);
        TextView txt_obs = (TextView) view.findViewById(R.id.txt_obs);
        TextView text_nome_status = (TextView) view.findViewById(R.id.text_nome_status);
        TextView text_qtd = (TextView) view.findViewById(R.id.text_qtd);
        ImageView img_produto = (ImageView) view.findViewById(R.id.img_produto);

        txt_nome.setText(String.format("Produto: %s",menu.getNome()));
        txt_obs.setText(String.format("Obs: %s",menu.getObs()));
        txt_preco.setText(String.format("R$%.2f",menu.getPreco()));
        text_nome_status.setText(menu.getNome_status());
        text_qtd.setText(String.format("Qtd. %d",menu.getQtd()));

        if (menu.getNome_status().equals("Preparado")) {
            text_nome_status.setTextColor(act.getResources().getColor(R.color.corVerde));
        }else{
            text_nome_status.setTextColor(act.getResources().getColor(R.color.corLaranja));
        }
        Glide.with(view).load(String.format("%s%s", act.getResources().getString(R.string.url_serverFotoFuncionario), menu.getImagem())).thumbnail(Glide.with(view).load(R.drawable.loading)).into(img_produto);

        return view;
    }
}
