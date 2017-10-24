package com.theribssh.www;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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
    static final int RequestCameraPermissionID = 1001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_client, container, false);

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
                    Log.d("START", ActivityCompat.checkSelfPermission(((MainActivity)getActivity()), android.Manifest.permission.CAMERA) + " _/\\_ " + PackageManager.PERMISSION_GRANTED);
                    return;
                }
                Log.d("START", ActivityCompat.checkSelfPermission(((MainActivity)getActivity()), android.Manifest.permission.CAMERA) + " _/\\_ " + PackageManager.PERMISSION_GRANTED);
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
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                if(qrcodes.size() != 0){
                    if (qrcodevalue != qrcodes.valueAt(0).displayValue){
                        txt_result.post(new Runnable() {
                            @Override
                            public void run() {
                                //Create vibrate
                                Vibrator vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(500);
                                txt_result.setText(qrcodes.valueAt(0).displayValue);
                            }
                        });
                    }
                }
            }
        });



        return view;
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
