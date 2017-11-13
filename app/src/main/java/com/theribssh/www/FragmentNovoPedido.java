package com.theribssh.www;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by echobiel on 22/10/2017.
 */

public class FragmentNovoPedido extends Fragment {

    ImageView img_qrcode;
    Button btn_pedido_qrcode_gerar;
    Button btn_client_sem_cadastro;
    Button btn_client_com_cadastro;
    int id_funcionario;
    int id_pedido;
    SalaPedido resultado;
    String qr_code = "";
    View view;
    Activity act;
    Socket socket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedido_qrcode, container, false);

        act = ((MainActivity)getActivity());

        img_qrcode = (ImageView) view.findViewById(R.id.img_qrcode);
        btn_pedido_qrcode_gerar = (Button) view.findViewById(R.id.btn_pedido_qrcode_gerar);
        btn_client_sem_cadastro = (Button) view.findViewById(R.id.btn_client_sem_cadastro);
        btn_client_com_cadastro = (Button) view.findViewById(R.id.btn_client_com_cadastro);

        Intent intent = ((MainActivity)getActivity()).getIntent();

        id_funcionario = intent.getIntExtra("id_usuario",0);

        configurandoBotoesFlutuantes();

        String text2Qr = "Não foi gerado um código ainda";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 400, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            img_qrcode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        socket = conectarSocket();

        socket.on("novo_pedido", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                if (args.length > 0){

                    Log.d("socket", args[0].toString());
                    String json = args[0].toString();

                    final NossosRestaurantesListView item = new Gson().fromJson(json, NossosRestaurantesListView.class);

                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            trocaDeTela();
                        }
                    });

                }
            }
        });

        socket.connect();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.close();
    }

    public Socket conectarSocket(){
        try{
            socket = IO.socket(String.format("http://%s",getResources().getText(R.string.ip_node)));
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return socket;
    }

    public void trocaDeTela(){
        ((MainActivity)getActivity()).detalhesPedidoGarcom(id_pedido);
    }

    private void configurandoBotoesFlutuantes() {
        btn_client_sem_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_client_com_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_pedido_qrcode_gerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QrCodeGenerator().execute();
            }
        });
    }
/*
    int notification_id = 1;

    public void enviarNotificacao(NossosRestaurantesListView item){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(((MainActivity)getActivity()));

        builder.setSmallIcon(R.drawable.logo_icon)
                .setContentTitle(item.getNome_restaurante() + "")
                .setContentText(item.getEndereco_restaurante())
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager nManager = (NotificationManager)
                (((MainActivity)getActivity())).getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.notify( notification_id , builder.build() );

    }*/

    public class QrCodeGenerator extends AsyncTask<Void, Void, Void> {

        String json;
        String href;

        @Override
        protected Void doInBackground(Void... params) {
            href = String.format("http://%s/criacaoSala?id_funcionario=%d",getResources().getString(R.string.ip_node), id_funcionario);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<SalaPedido>() {
                }.getType());

                if (resultado.getMensagem().equals("Ocorreu um erro durante a conexão. Tente novamente mais tarde.")) {
                    Snackbar.make(view, resultado.getMensagem() + " | " + href, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    qr_code = resultado.getQr_code();
                    id_pedido = resultado.getId_sala();

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    BitMatrix bitMatrix = multiFormatWriter.encode(qr_code, BarcodeFormat.QR_CODE, 400, 300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    img_qrcode.setImageBitmap(bitmap);
                }
            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
            }

        }
    }
}
