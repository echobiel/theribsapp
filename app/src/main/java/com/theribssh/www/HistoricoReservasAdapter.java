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
 * Created by biiac on 15/10/2017.
 */

public class HistoricoReservasAdapter extends BaseAdapter {

        private final List<Reserva> hist_reservas;
        private final Activity activity;

        public HistoricoReservasAdapter(List<Reserva> hist_reservas, Activity activity) {
            this.hist_reservas = hist_reservas;
            this.activity = activity;
        }

    @Override
    public int getCount() {
        return hist_reservas.size();
    }

    @Override
    public Object getItem(int position) {
        return hist_reservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Reserva historico_reservas = hist_reservas.get(position);

        View view = activity.getLayoutInflater().inflate(R.layout.list_historico_reserva, parent, false);

        TextView nome_restaurante = (TextView)view.findViewById(R.id.text_view_nome_restaurante);
        nome_restaurante.setText(historico_reservas.getNome());

        ImageView foto_restaurante = (ImageView)view.findViewById(R.id.image_view_restaurante);

        TextView desc_restaurante = (TextView)view.findViewById(R.id.text_view_desc_restaurante);
        desc_restaurante.setText(historico_reservas.getDesc());

        TextView data_reserva = (TextView)view.findViewById(R.id.text_view_data_reserva);
        data_reserva.setText(historico_reservas.getData());

        TextView periodo_reserva = (TextView)view.findViewById(R.id.text_view_periodo_reserva);
        periodo_reserva.setText(historico_reservas.getPeriodo());

        //Guardando o nome da imagem
        String nameImage = historico_reservas.getFoto();

        try {
            //Colocando Imagem de fundo
            Glide.with(view).load(String.format("%s%s", activity.getResources().getString(R.string.url_serverFotoFuncionario), nameImage)).thumbnail(Glide.with(view).load(R.drawable.loading)).into(foto_restaurante);

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }


}
