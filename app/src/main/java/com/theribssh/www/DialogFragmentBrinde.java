package com.theribssh.www;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by echobiel on 13/11/2017.
 */

@SuppressLint("ValidFragment")
public class DialogFragmentBrinde extends DialogFragment {

    private int numStyle;
    private int numTheme;
    Button btn_ok;
    TextView text_view_brinde;
    Activity act;
    private int id_pedido;

    @SuppressLint("ValidFragment")
    public DialogFragmentBrinde(int numStyle, int numTheme, int id_pedido){
        this.numStyle = numStyle;
        this.numTheme = numTheme;
        this.id_pedido = id_pedido;
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

        act = ((MainActivity)getActivity());

        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.dialog_brinde, container);

        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        text_view_brinde = (TextView) view.findViewById(R.id.text_view_brinde);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ((MainActivity)getActivity()).pedidoFinalizado();
            }
        });

        new BrindeTask().execute();

        return view;
    }

    private class BrindeTask extends AsyncTask<Void, Void, Void>
    {
        String href;
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/brinde?id_pedido=%d", getResources().getString(R.string.ip_node), id_pedido);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                Gson gson = new Gson();
                Mensagem mensagem = gson.fromJson(json, new TypeToken<Mensagem>() {
                }.getType());

                    text_view_brinde.setText(mensagem.getMensagem());

            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
