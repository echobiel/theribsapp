package com.theribssh.www;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 16165824 on 09/10/2017.
 */

public class CardapioPrincipaisAdapter extends BaseAdapter {

    private final List<CardapioPrincipaisListView> card_principais;
    private final Activity activity;

    public CardapioPrincipaisAdapter(List<CardapioPrincipaisListView> card_principais, Activity activity){
        this.card_principais = card_principais;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return card_principais.size();
    }

    @Override
    public Object getItem(int position) {
        return card_principais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CardapioPrincipaisListView cardapio_principais = card_principais.get(position);


        View view = activity.getLayoutInflater().inflate(R.layout.cardapio_principais_listview, parent, false);

        TextView nome_produto = (TextView)view.findViewById(R.id.text_view_nome_produto);
        nome_produto.setText(cardapio_principais.getNome_produto());

        TextView desc_produto = (TextView)view.findViewById(R.id.text_view_desc_produto);
        desc_produto.setText(cardapio_principais.getDesc_produto());

        TextView preco_produto = (TextView)view.findViewById(R.id.text_view_preco_produto);
        preco_produto.setText(cardapio_principais.getPreco_produto());

        ImageView imagem_produto = (ImageView)view.findViewById(R.id.image_view_produto);
        imagem_produto.setImageResource(cardapio_principais.getFoto_produto());

        return view;
    }
}