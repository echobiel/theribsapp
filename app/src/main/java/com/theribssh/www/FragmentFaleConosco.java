package com.theribssh.www;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class FragmentFaleConosco extends Fragment {

    Spinner spinner_unidade;
    Spinner spinner_tipo_info;
    List<Unidade> unidades = new ArrayList<>();
    List<TipoInfo> tiposinf = new ArrayList<>();
    Mensagem mensagem;
    EditText edit_text_nome;
    EditText edit_text_email;
    EditText edit_text_celular;
    EditText edit_text_telefone;
    EditText edit_text_obs;
    TextWatcher celularMask;
    TextWatcher telefoneMask;
    String urlStrings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fale_conosco, container, false);

        spinner_unidade = (Spinner)view.findViewById(R.id.spinner_unidade);
        spinner_tipo_info = (Spinner)view.findViewById(R.id.spinner_tipo_info);
        edit_text_nome = (EditText) view.findViewById(R.id.edit_text_nome);
        edit_text_email = (EditText) view.findViewById(R.id.edit_text_email);
        edit_text_celular = (EditText) view.findViewById(R.id.edit_text_celular);
        edit_text_telefone = (EditText) view.findViewById(R.id.edit_text_telefone);
        edit_text_obs = (EditText) view.findViewById(R.id.edit_text_obs);

        celularMask = Mask.insert("(##)#####-####", edit_text_celular);
        telefoneMask = Mask.insert("(##)####-####", edit_text_telefone);

        edit_text_celular.addTextChangedListener(celularMask);
        edit_text_telefone.addTextChangedListener(telefoneMask);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlStrings = String.format("nome=%s&email=%s&celular=%s&telefone=%s&obs=%s&id_unidade=%d&id_tipoinfo=%d",
                        edit_text_nome.getText().toString().replace(" ", "+"),
                        edit_text_email.getText().toString().replace(" ", "+"),
                        edit_text_celular.getText().toString().replace(" ", "+"),
                        edit_text_telefone.getText().toString().replace(" ", "+"),
                        edit_text_obs.getText().toString().replace(" ", "+"),
                        ((Unidade)spinner_unidade.getSelectedItem()).getId_restaurante(),
                        ((TipoInfo)spinner_tipo_info.getSelectedItem()).getId_tipoInfo());

                new InserirTask().execute();
            }
        });

        new PegadorUnidadeTask().execute();
        new PegadorTipoInfTask().execute();

        return view;
    }

    public class InserirTask extends AsyncTask<Void, Void, Void> {

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/inserirFaleconosco?%s",getResources().getString(R.string.ip_node), urlStrings);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                mensagem = gson.fromJson(json, new TypeToken<Mensagem>() {
                }.getType());

                Toast.makeText(getActivity(),mensagem.getMensagem(), Toast.LENGTH_SHORT).show();

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
                Toast.makeText(getActivity(), "Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        }
    }
    public class PegadorUnidadeTask extends AsyncTask<Void, Void, Void> {

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/selectRestaurante",getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                unidades = gson.fromJson(json, new TypeToken<List<Unidade>>() {
                }.getType());

                if (unidades.size() > 0){
                    ArrayAdapter<Unidade> mesaArrayAdapter =
                            new ArrayAdapter<>(((MainActivity)getActivity()), R.layout.spinner_padrao, unidades);

                    spinner_unidade.setAdapter(mesaArrayAdapter);
                }

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
                Toast.makeText(getActivity(), "Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class PegadorTipoInfTask extends AsyncTask<Void, Void, Void> {

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/selectTipoInfo",getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                tiposinf = gson.fromJson(json, new TypeToken<List<TipoInfo>>() {
                }.getType());

                if (tiposinf.size() > 0){
                    ArrayAdapter<TipoInfo> mesaArrayAdapter =
                            new ArrayAdapter<>(((MainActivity)getActivity()), R.layout.spinner_padrao, tiposinf);

                    spinner_tipo_info.setAdapter(mesaArrayAdapter);
                }

            }catch(Exception e){
                Log.d("Script", "catch onPostExecute");
                e.printStackTrace();
                Toast.makeText(getActivity(), "Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
