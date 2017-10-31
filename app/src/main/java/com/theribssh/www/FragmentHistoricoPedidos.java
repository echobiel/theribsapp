package com.theribssh.www;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by biiac on 08/10/2017.
 */

public class FragmentHistoricoPedidos extends Fragment {

    ListView list_view_historico_pedidos;
    List<HistoricoPedidosListView> listPedidos = new ArrayList<>();
    HistoricoPedidosAdapter historicoPedidosAdapter;
    Activity act;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historicos_pedido, container, false);

        act = ((MainActivity)getActivity());

        list_view_historico_pedidos = (ListView) rootView.findViewById(R.id.list_view_historico_pedidos);

        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 1", new Date()));
        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 2", new Date()));
        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 3", new Date()));
        listPedidos.add(new HistoricoPedidosListView(4,"The Ribs Steakhosue 4", new Date()));

        HistoricoPedidosAdapter adapter = new HistoricoPedidosAdapter(listPedidos, ((MainActivity)getActivity()));

        list_view_historico_pedidos.setAdapter(adapter);

        return rootView;
    }

    private class PegadorTask extends AsyncTask<Void, Void, Void>
    {
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            String href = "http://10.0.2.2:8888/selectCardapio";
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new Gson();

            listPedidos = gson.fromJson(json, new TypeToken<List<CardapioPrincipaisListView>>(){
            }.getType());

            historicoPedidosAdapter = new HistoricoPedidosAdapter(listPedidos, act);
            try {
                list_view_historico_pedidos.setAdapter(historicoPedidosAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
