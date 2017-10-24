package com.theribssh.www;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by biiac on 23/10/2017.
 */

public class NossosRestaurantesAdapter extends BaseAdapter {

    private final List<NossosRestaurantesListView> nossos_rest;
    private final Activity activity;

    public NossosRestaurantesAdapter(List<NossosRestaurantesListView> nossos_rest, Activity activity){
        this.nossos_rest = nossos_rest;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return nossos_rest.size();
    }

    @Override
    public Object getItem(int position) {
        return nossos_rest.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NossosRestaurantesListView nossos_restaurantes = nossos_rest.get(position);

        View view = activity.getLayoutInflater().inflate(R.layout.list_view_nossos_restaurantes, parent, false);

        TextView unidade_restaurante = (TextView)view.findViewById(R.id.text_view_unidade_restaurante);
        unidade_restaurante.setText(nossos_restaurantes.getUnidade_restaurante());

        TextView endereco_restaurante = (TextView)view.findViewById(R.id.text_view_endereco_restaurante);
        endereco_restaurante.setText(nossos_restaurantes.getEndereco_restaurante());

        TextView desc_restaurante = (TextView)view.findViewById(R.id.text_view_desc_restaurante);
        desc_restaurante.setText(nossos_restaurantes.getDesc_restaurante());

        TextView telefone_restaurante = (TextView)view.findViewById(R.id.text_view_telefone_restaurante);
        telefone_restaurante.setText(nossos_restaurantes.getTelefone_restaurante());

        ImageView foto_restaurante = (ImageView)view.findViewById(R.id.image_view_restaurante);
        foto_restaurante.setImageResource(nossos_restaurantes.getFoto_restaurante());

        return view;
    }
}
