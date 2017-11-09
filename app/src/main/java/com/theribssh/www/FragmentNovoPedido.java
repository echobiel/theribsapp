package com.theribssh.www;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echobiel on 22/10/2017.
 */

public class FragmentNovoPedido extends Fragment {

    ImageView img_qrcode;
    Button btn_pedido_qrcode_gerar;
    Button btn_client_sem_cadastro;
    Button btn_client_com_cadastro;
    int id_funcionario;
    SalaPedido resultado;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedido_qrcode, container, false);

        img_qrcode = (ImageView) view.findViewById(R.id.img_qrcode);
        btn_pedido_qrcode_gerar = (Button) view.findViewById(R.id.btn_pedido_qrcode_gerar);
        btn_client_sem_cadastro = (Button) view.findViewById(R.id.btn_client_sem_cadastro);
        btn_client_com_cadastro = (Button) view.findViewById(R.id.btn_client_com_cadastro);

        Intent intent = ((MainActivity)getActivity()).getIntent();

        id_funcionario = intent.getIntExtra("id_usuario",0);

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
                String text2Qr = "RIBS1020";
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 400, 300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    img_qrcode.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

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

        return view;
    }

    public class InformacoesTask extends AsyncTask<Void, Void, Void> {

        String json;
        String href;

        @Override
        protected Void doInBackground(Void... params) {
            href = String.format("http://%s/criacaoSala?id_funcionario%d",getResources().getString(R.string.ip_node), id_funcionario);
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

                if (resultado.getMensagem().equals("Usuário ou senha incorretos. Verifique e tente novamente.")) {
                    Snackbar.make(view, resultado.getMensagem(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Toast.makeText(((MainActivity)getActivity()),resultado.getMensagem(),Toast.LENGTH_LONG).show();


                }
            }catch(Exception e){
                Toast.makeText(((MainActivity)getActivity()),"Ocorreu um erro de conexão. Tente novamente mais tarde.",Toast.LENGTH_LONG).show();
            }

        }
    }
}
