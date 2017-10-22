package br.com.theribssteakhouse.www;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by echobiel on 22/10/2017.
 */

public class FragmentNovoPedido extends Fragment {

    ImageView img_qrcode;
    Button btn_pedido_qrcode_gerar;
    Button btn_client_sem_cadastro;
    Button btn_client_com_cadastro;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_qrcode,container,false);

        img_qrcode = (ImageView) view.findViewById(R.id.img_qrcode);
        btn_pedido_qrcode_gerar = (Button) view.findViewById(R.id.btn_pedido_qrcode_gerar);
        btn_client_sem_cadastro = (Button) view.findViewById(R.id.btn_client_sem_cadastro);
        btn_client_com_cadastro = (Button) view.findViewById(R.id.btn_client_com_cadastro);

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
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,800,600);
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
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,800,600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            img_qrcode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        return view;
    }
}
