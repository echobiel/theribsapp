package com.theribssh.www;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by echobiel on 13/11/2017.
 */

public class DialogFragmentPedidoCliente extends DialogFragment {

    private int numStyle;
    private int numTheme;

    @SuppressLint("ValidFragment")
    public DialogFragmentPedidoCliente(int numStyle, int numTheme){
        this.numStyle = numStyle;
        this.numTheme = numTheme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreate()");

        int style;
        int theme;

        switch(numStyle){
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_INPUT; break;
            case 3: style = DialogFragment.STYLE_NO_FRAME; break;
            default: style = DialogFragment.STYLE_NORMAL; break;
        }

        switch(numTheme){
            case 1: theme = android.R.style.Theme_Holo_Light; break;
            case 2: theme = android.R.style.Theme_Holo_Dialog; break;
            default: theme = android.R.style.Theme_Holo_Light_DarkActionBar; break;
        }

        setStyle(style, theme);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.dialog_pedido_cliente, container);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Script", "onActivityCreated()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Script", "onAttach()");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i("Script", "onCancel()");
    }
/*
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Log.i("Script", "onCreateDialog()");

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                .setTitle("Pedido")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Ok pressed", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        return alert.show();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Script", "onDestroy()");
    }
}
