package com.theribssh.www;

import android.app.Activity;
import android.content.Intent;
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
    List<Pedidos> listPedidos = new ArrayList<>();
    HistoricoPedidosAdapter historicoPedidosAdapter;
    Activity act;
    int id_cliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historicos_pedido, container, false);

        act = ((MainActivity)getActivity());

        Intent intent = act.getIntent();

        id_cliente = intent.getIntExtra("id_usuario", 0);

        list_view_historico_pedidos = (ListView) rootView.findViewById(R.id.list_view_historico_pedidos);

        new PegadorTask().execute();

        return rootView;
    }

    private class PegadorTask extends AsyncTask<Void, Void, Void>
    {
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            String href = String.format("http://%s/historicoPedidos?id_cliente=%d", getResources().getString(R.string.ip_node), id_cliente);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new Gson();

            try{
                listPedidos = gson.fromJson(json, new TypeToken<List<Pedidos>>(){
                }.getType());

                historicoPedidosAdapter = new HistoricoPedidosAdapter(listPedidos, act);

                list_view_historico_pedidos.setAdapter(historicoPedidosAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
