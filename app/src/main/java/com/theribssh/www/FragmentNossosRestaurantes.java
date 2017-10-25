package com.theribssh.www;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
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
    Activity act;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nossos_restaurantes, container, false);

        list_view_nossos_restaurantes = (ListView)view.findViewById(R.id.list_view_nossos_restaurantes);

        act = ((MainActivity)getActivity());

        configurarListView();

        return view;
    }

    private void configurarListView() {

        lstRestaurantes = new ArrayList<>();

        new PegadorTask().execute();

        Socket socket = ((MainActivity)getActivity()).conectarSocket();

        socket.on("novo_usuario", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                if (args.length > 0){

                    Log.d("socket", args[0].toString());
                    String json = args[0].toString();

                    final NossosRestaurantesListView item = new Gson().fromJson(json, NossosRestaurantesListView.class);

                    act.runOnUiThread(((MainActivity)getActivity()).getRunnable(lstRestaurantes, item, adapter));

                }
            }
        });

        socket.connect();
    }

    int notification_id = 1;

    public void enviarNotificacao(NossosRestaurantesListView item){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(act);

        builder.setSmallIcon(R.drawable.logo_icon)
                .setContentTitle(item.getNome_restaurante() + "")
                .setContentText(item.getEndereco_restaurante())
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager nManager = (NotificationManager)
                (act).getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.notify( notification_id , builder.build() );

    }

    private class PegadorTask extends AsyncTask<Void, Void, Void>
    {
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            String href = "http://10.0.2.2:8888/select";
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
            list_view_nossos_restaurantes.setAdapter(adapter);

        }
    }

}
