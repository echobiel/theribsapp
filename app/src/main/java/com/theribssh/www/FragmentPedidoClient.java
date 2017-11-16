package com.theribssh.www;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

/**
 * Created by echobiel on 13/10/2017.
 */

public class FragmentPedidoClient extends Fragment {

    SurfaceView camera_preview;
    TextView txt_result;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    String qrcodevalue;
    String codigo;
    SalaPedido resultado;
    View view;
    int verificadorDialog, id_usuario;
    static final int RequestCameraPermissionID = 1001;
    int id_pedido;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedido_client, container, false);

        intent = ((MainActivity)getActivity()).getIntent();

        id_usuario = intent.getIntExtra("id_usuario",0);

        camera_preview = (SurfaceView) view.findViewById(R.id.camera_preview);
        txt_result = (TextView) view.findViewById(R.id.txt_result);

        barcodeDetector = new BarcodeDetector
                .Builder(((MainActivity) getActivity()))
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(((MainActivity) getActivity()), barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        //Add event
        camera_preview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(((MainActivity) getActivity()), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request Permission

                    requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                            RequestCameraPermissionID);
                    Log.d("START", ActivityCompat.checkSelfPermission(((MainActivity) getActivity()), android.Manifest.permission.CAMERA) + " _/\\_ " + PackageManager.PERMISSION_GRANTED);
                    return;
                }
                Log.d("START", ActivityCompat.checkSelfPermission(((MainActivity) getActivity()), android.Manifest.permission.CAMERA) + " _/\\_ " + PackageManager.PERMISSION_GRANTED);
                try {
                    cameraSource.start(camera_preview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                verificadorDialog = ((MainActivity) getActivity()).getVerificadorDialog();

                if (verificadorDialog != 1) {
                    try {
                        if (ActivityCompat.checkSelfPermission(((MainActivity)getActivity()), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        cameraSource.start(camera_preview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                    if (qrcodes.size() != 0) {
                        if (qrcodevalue != qrcodes.valueAt(0).displayValue) {
                            txt_result.post(new Runnable() {
                                @Override
                                public void run() {

                                    codigo = qrcodes.valueAt(0).displayValue;

                                    new AutenticacaoSala().execute();
                                }
                            });
                        }
                    }
                }else{
                    cameraSource.stop();
                }
            }
        });



        return view;
    }

    public void openPedido(){
        ((MainActivity)getActivity()).setVerificadorDialog(1);
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentPedidoCliente dfpc = new DialogFragmentPedidoCliente(4,3, id_pedido);
        dfpc.show(ft, "dialog");
    }

    public class AutenticacaoSala extends AsyncTask<Void, Void, Void>{

        String json;
        String href;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/autenticarSala?qr=%s&id_cliente=%d",getResources().getString(R.string.ip_node),codigo,id_usuario);
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

                if (resultado.getMensagem().equals("Código QR inválido.")) {
                    txt_result.setText(resultado.getMensagem());
                } else {

                    int id_sala = resultado.getId_sala();

                    id_pedido = id_sala;

                    intent.putExtra("id_sala",id_sala);

                    cameraSource.stop();

                    openPedido();

                }
            }catch(Exception e){
                Toast.makeText(((MainActivity)getActivity()),"Ocorreu um erro de conexão. Tente novamente mais tarde.",Toast.LENGTH_LONG)
                        .show();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case RequestCameraPermissionID:

                if (ActivityCompat.checkSelfPermission(((MainActivity)getActivity()), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                try {
                    cameraSource.start(camera_preview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
        }
    }
}
