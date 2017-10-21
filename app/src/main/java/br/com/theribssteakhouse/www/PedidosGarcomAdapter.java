package br.com.theribssteakhouse.www;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 16165846 on 17/10/2017.
 */

public class PedidosGarcomAdapter extends BaseAdapter {

    private final List<PedidoGarcom> pedidoGarcomList;
    private final Activity act;

    public PedidosGarcomAdapter(Activity act, List<PedidoGarcom> pedidoGarcomList){
        this.pedidoGarcomList = pedidoGarcomList;
        this.act = act;
    }

    @Override
    public int getCount() {
        return pedidoGarcomList.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidoGarcomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.pedido_garcom_list_item, parent, false);

        TextView txt_codigo_pedido = (TextView) view.findViewById(R.id.txt_codigo_pedido);


        //Resgata o item
        PedidoGarcom pedido = pedidoGarcomList.get(position);

        txt_codigo_pedido.setText(pedido.getCodigoSala());

        return view;
    }
}
