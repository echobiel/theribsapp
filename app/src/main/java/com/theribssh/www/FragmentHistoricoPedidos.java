package com.theribssh.www;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by biiac on 08/10/2017.
 */

public class FragmentHistoricoPedidos extends Fragment {

    ListView list_view_historico_pedidos;
    List<HistoricoPedidosListView> listPedidos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historicos_pedido, container, false);

        list_view_historico_pedidos = (ListView) rootView.findViewById(R.id.list_view_historico_pedidos);

        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 1", new Date()));
        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 2", new Date()));
        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 3", new Date()));
        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 4", new Date()));

        HistoricoPedidosAdapter adapter = new HistoricoPedidosAdapter(listPedidos, ((MainActivity)getActivity()));

        list_view_historico_pedidos.setAdapter(adapter);

        return rootView;
    }
}
