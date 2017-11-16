package com.theribssh.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echobiel on 13/11/2017.
 */

@SuppressLint("ValidFragment")
public class DialogFragmentQtd extends DialogFragment {

    private int numStyle;
    private int numTheme;
    private int qtd;
    ImageButton btn_aumentar;
    ImageButton btn_diminuir;
    Button btn_confirmar;
    TextView text_qtd;

    @SuppressLint("ValidFragment")
    public DialogFragmentQtd(int numStyle, int numTheme){
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
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Script", "onCreateView()");

        qtd = 0;

        View view = inflater.inflate(R.layout.dialog_qtd, container);

        btn_aumentar = (ImageButton) view.findViewById(R.id.btn_aumentar);
        btn_diminuir = (ImageButton) view.findViewById(R.id.btn_diminuir);
        btn_confirmar = (Button) view.findViewById(R.id.btn_confirmar);
        text_qtd = (TextView) view.findViewById(R.id.text_qtd);

        text_qtd.setText(qtd + "");

        btn_aumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtd = qtd + 1;
                text_qtd.setText(qtd + "");

            }
        });

        btn_diminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qtd - 1 >= 0) {
                    qtd = qtd - 1;
                    text_qtd.setText(qtd + "");
                }
            }
        });

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtd = Integer.parseInt(text_qtd.getText().toString());
                ((MainActivity)getActivity()).setQtd_produto(qtd);
                dismiss();
            }
        });

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Script", "onDestroy()");
    }
}
