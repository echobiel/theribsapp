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
import java.util.List;

/**
 * Created by biiac on 08/10/2017.
 */

public class FragmentHistoricoReservas extends Fragment {

    ListView list_view_historico_reservas;
    List<Reserva> reservaList = new ArrayList<>();
    int id_cliente;
    Activity act;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historico_reservas_listview, container, false);

        act = ((MainActivity)getActivity());

        Intent intent = act.getIntent();

        id_cliente = intent.getIntExtra("id_usuario",0);

        list_view_historico_reservas = (ListView)view.findViewById(R.id.list_view_historico_reservas);

        new PegadorTask().execute();

        return view;
    }

    private class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/historicoReservas?id_cliente=%d",getResources().getString(R.string.ip_node),id_cliente);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                reservaList = gson.fromJson(json, new TypeToken<List<Reserva>>() {
                }.getType());

                HistoricoReservasAdapter adapter = new HistoricoReservasAdapter(reservaList, act);

                list_view_historico_reservas.setAdapter(adapter);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
