package com.theribssh.www;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by biiac on 15/10/2017.
 */

public class HistoricoReservasAdapter extends BaseAdapter {

        private final List<HistoricoReservasListView> hist_reservas;
        private final Activity activity;

        public HistoricoReservasAdapter(List<HistoricoReservasListView> hist_reservas, Activity activity) {
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

        HistoricoReservasListView historico_reservas = hist_reservas.get(position);

        View view = activity.getLayoutInflater().inflate(R.layout.historico_reservas_listview, parent, false);

        TextView nome_restaurante = (TextView)view.findViewById(R.id.text_view_nome_restaurante);
        nome_restaurante.setText(historico_reservas.getNome_restaurante());

        ImageView foto_restaurante = (ImageView)view.findViewById(R.id.image_view_restaurante);
        foto_restaurante.setImageResource(historico_reservas.getFoto_restaurante());

        TextView desc_restaurante = (TextView)view.findViewById(R.id.text_view_desc_restaurante);
        desc_restaurante.setText(historico_reservas.getDesc_restaurante());

        TextView data_reserva = (TextView)view.findViewById(R.id.text_view_data_reserva);
        data_reserva.setText(historico_reservas.getData_reserva());

        TextView periodo_reserva = (TextView)view.findViewById(R.id.text_view_periodo_reserva);
        periodo_reserva.setText(historico_reservas.getPeriodo_reserva());

        return view;
    }


}
