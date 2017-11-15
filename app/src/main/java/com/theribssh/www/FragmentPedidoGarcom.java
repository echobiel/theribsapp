package com.theribssh.www;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by 16165846 on 17/10/2017.
 */

public class FragmentPedidoGarcom extends Fragment {

    ListView list_pedidos;
    PedidoGarcom pedido;
    Button btn_criar_pedido;
    Activity act;
    private List<PedidoGarcom> pedidoGarcomList = new ArrayList<>();
    Socket socket;
    List<PedidoGarcom> resultado;
    int id_usuario;
    PegadorTask pet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View VIEW = inflater.inflate(R.layout.fragment_pedido_garcom, container, false);

        act = ((MainActivity)getActivity());

        if (act != null) {

            Intent intent = act.getIntent();

            id_usuario = intent.getIntExtra("id_usuario", 0);

            btn_criar_pedido = (Button) VIEW.findViewById(R.id.btn_criar_pedido);

            setupListPedidos(VIEW);

            btn_criar_pedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).backPagePedidoGarcom();
                }
            });

            socket = conectarSocket();

            socket.on("novo_pedido", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {

                    if (args.length > 0) {

                        Log.d("socket", args[0].toString());
                        String json = args[0].toString();

                        final NossosRestaurantesListView item = new Gson().fromJson(json, NossosRestaurantesListView.class);

                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (pet != null){
                                    pet = new PegadorTask();
                                    pet.execute();
                                }
                            }
                        });

                    }
                }
            });

            socket.connect();
        }

        return VIEW;

    }

    public Socket conectarSocket(){
        try{
            socket = IO.socket(String.format("http://%s",getResources().getText(R.string.ip_node)));
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return socket;
    }

    private void setupListPedidos(View view) {
        list_pedidos = (ListView) view.findViewById(R.id.list_pedidos);

        list_pedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int id_pedido = pedidoGarcomList.get(position).getId_sala();

                ((MainActivity)getActivity()).detalhesPedidoGarcom(id_pedido);
            }
        });

        pet = new PegadorTask();
        pet.execute();
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {

            href = String.format("http://%s/salas?id_funcionario=%d",getResources().getString(R.string.ip_node), id_usuario);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(isAdded()){
                try {
                    Gson gson = new Gson();
                    resultado = gson.fromJson(json, new TypeToken<List<PedidoGarcom>>() {
                    }.getType());

                    pedidoGarcomList = resultado;

                    PedidosGarcomAdapter adapter = new PedidosGarcomAdapter(((MainActivity)getActivity()), pedidoGarcomList);

                    list_pedidos.setAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onStop() {
        pet.cancel(true);
        pet = null;
        super.onStop();
    }

    /*
    int notification_id;

    public void enviarNotificacao(NossosRestaurantesListView item){

        Intent intent = act.getIntent();

        intent.putExtra("novo_pedido", 1);

        PendingIntent p = PendingIntent.getActivity(act, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(act);

        builder.setSmallIcon(R.drawable.logo_icon)
                .setContentTitle(item.getNome_restaurante() + "")
                .setContentText(item.getEndereco_restaurante())
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(p);

        NotificationManager nManager = (NotificationManager)
                (act).getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.notify( notification_id , builder.build() );
        Notification n = builder.build();

        n.vibrate = new long[]{150,300,150,600};

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(act,som);
            toque.play();

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

}
