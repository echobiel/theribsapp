package com.theribssh.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
public class DialogFragmentFeedback extends DialogFragment {

    private int numStyle;
    private int numTheme;
    private int id_pedido;
    private Spinner spinner_feedback;
    private Button btn_salvar_feedback;
    private TextView text_titulo_pg;
    private int id_avaliacao;
    private List<Feedback> feedbacks = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public DialogFragmentFeedback(int numStyle, int numTheme, int id_pedido){
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
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.dialog_feedback, container);

        spinner_feedback = (Spinner) view.findViewById(R.id.spinner_feedback);
        btn_salvar_feedback = (Button) view.findViewById(R.id.btn_salvar_feedback);
        text_titulo_pg = (TextView) view.findViewById(R.id.text_titulo_pg);

        new PegadorTask().execute();

        setupBotao();

        return view;
    }

    private void setupBotao() {
        btn_salvar_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_avaliacao = ((Feedback)spinner_feedback.getSelectedItem()).getId_avaliacao();

                new AvaliarTask().execute();
            }
        });
    }

    public void openBrinde(){
        FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        DialogFragmentBrinde dfb = new DialogFragmentBrinde(1,2,id_pedido);
        dfb.show(ft, "dialog");
    }

    public class AvaliarTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/avaliar?id_pedido=%d&id_avaliacao=%d",getResources().getString(R.string.ip_node), id_pedido, id_avaliacao);
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

                String mensagem_text = mensagem.getMensagem();

                Toast.makeText(getActivity(), mensagem_text, Toast.LENGTH_LONG).show();

                openBrinde();

                dismiss();

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
            }
        }
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/feedbacks",getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                feedbacks = gson.fromJson(json, new TypeToken<List<Feedback>>() {
                }.getType());

                if (feedbacks.size() > 0){
                    ArrayAdapter<Feedback> mesaArrayAdapter =
                            new ArrayAdapter<>(((MainActivity)getActivity()), R.layout.spinner_padrao, feedbacks);

                    spinner_feedback.setAdapter(mesaArrayAdapter);
                }

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
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
