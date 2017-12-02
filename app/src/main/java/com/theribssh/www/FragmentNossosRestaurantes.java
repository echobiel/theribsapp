package com.theribssh.www;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class FragmentNossosRestaurantes extends Fragment{

    ListView list_view_nossos_restaurantes;
    List<NossosRestaurantesListView> lstRestaurantes = new ArrayList<>();
    NossosRestaurantesAdapter adapter;
    FloatingActionButton fab;
    Activity act;
    int permissao;
    int id_restaurante;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nossos_restaurantes, container, false);

        list_view_nossos_restaurantes = (ListView)view.findViewById(R.id.list_view_nossos_restaurantes);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        act = ((MainActivity)getActivity());

        configurarListView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarListView();
            }
        });

        Intent intent = act.getIntent();

        permissao = intent.getIntExtra("permissao",0);

        if (permissao == 1) {
            list_view_nossos_restaurantes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    id_restaurante = lstRestaurantes.get(i).getId_restaurante();
                    openReserva();
                    return false;
                }
            });
        }else{
            list_view_nossos_restaurantes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getActivity(), "É necessário ter um cadastro para reservar. Cadastre-se e faça sua reserva.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

        return view;
    }

    public void openReserva(){
        try {
            FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            DialogFragmentReserva dfr = new DialogFragmentReserva(1, 3, id_restaurante);
            dfr.show(ft, "dialog");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void configurarListView() {

        lstRestaurantes = new ArrayList<>();

        new PegadorTask().execute();
    }

    private class PegadorTask extends AsyncTask<Void, Void, Void>
    {
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            String href = String.format("http://%s/selectRestaurante", getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new Gson();
            lstRestaurantes = gson.fromJson(json, new TypeToken<List<NossosRestaurantesListView>>(){
            }.getType());

            adapter = new NossosRestaurantesAdapter(lstRestaurantes, act);
            try {
                list_view_nossos_restaurantes.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
