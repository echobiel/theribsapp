package com.theribssh.www;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    Emitter.Listener emitterListener;
    FloatingActionButton fab;
    Socket socket;
    Activity act;

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

        return view;
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
            String href = "http://10.0.2.2:8888/selectRestaurante";
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
