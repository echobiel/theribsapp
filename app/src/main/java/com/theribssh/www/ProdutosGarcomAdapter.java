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
        ImageView img_produto = (ImageView) view.findViewById(R.id.img_produto);

        txt_nome.setText(menu.getNome());
        txt_obs.setText(menu.getObs());
        txt_preco.setText(String.format("R$%.2f",menu.getPreco()));

        Glide.with(view).load("https://www.google.com.br/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwi2uoKi6vrWAhUBlpAKHXMuA6sQjRwIBw&url=https%3A%2F%2Fwww.mcdonalds.com%2Fus%2Fen-us%2Fnew-big-mac.html&psig=AOvVaw2p8TVNcgPF6fKZ9-9cVizY&ust=1508438678569186").thumbnail(Glide.with(view).load(R.drawable.loading)).into(img_produto);

        return view;
    }
}
