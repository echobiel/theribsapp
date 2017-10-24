package com.theribssh.www;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 16165824 on 10/10/2017.
 */

public class CardapioTodosAdapter extends BaseAdapter {

    private final List<CardapioTodosListView> card_todos;
    private final Activity activity;

    public CardapioTodosAdapter(List<CardapioTodosListView> card_todos, Activity activity){
        this.card_todos= card_todos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return card_todos.size();
    }

    @Override
    public Object getItem(int position) {
        return card_todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CardapioTodosListView cardapio_todos = card_todos.get(position);

        View view = activity.getLayoutInflater().inflate(R.layout.cardapio_todos_listview, parent, false);

        TextView nome_produto = (TextView)view.findViewById(R.id.text_view_nome_produto);
        nome_produto.setText(cardapio_todos.getNome_produto());

        TextView categoria_produto = (TextView)view.findViewById(R.id.text_view_categoria_produto);
        categoria_produto.setText(cardapio_todos.getCategoria_produto());

        ImageView imagem_produto = (ImageView)view.findViewById(R.id.image_view_produto);
        imagem_produto.setImageResource(cardapio_todos.getFoto_produto());

        return view;
    }
}
