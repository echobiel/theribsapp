package com.theribssh.www;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FragmentLogin extends Fragment {

    TextInputEditText edit_text_login;
    TextInputEditText edit_text_senha;
    String urlStrings;
    String email;
    String senha;
    AutenticarUsuario resultado;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);

        edit_text_login = (TextInputEditText) view.findViewById(R.id.edit_text_login);
        edit_text_senha = (TextInputEditText) view.findViewById(R.id.edit_text_senha);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                email = edit_text_login.getText().toString();
                senha = edit_text_senha.getText().toString();

                email = email.replaceAll(" ", "+");
                senha = senha.replaceAll(" ", "+");

                urlStrings = String.format("email=%s&senha=%s", email, senha);

                new AutenticadorTask().execute();
            }
        });

        return view;
    }

    private class AutenticadorTask extends AsyncTask <Void, Void, Void>{

        String json;
        String href;

        @Override
        protected Void doInBackground(Void... params) {
            href = String.format("http://%s/autenticarUsuario?%s",getResources().getString(R.string.ip_node),urlStrings);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                resultado = gson.fromJson(json, new TypeToken<AutenticarUsuario>() {
                }.getType());

                if (resultado.getMensagem().equals("Usuário ou senha incorretos. Verifique e tente novamente.")) {
                    Snackbar.make(view, resultado.getMensagem(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, resultado.getMensagem(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    final Intent INTENT = new Intent(((MainActivity) getActivity()), MainActivity.class);

                    INTENT.putExtra("permissao", resultado.getPermissao());
                    INTENT.putExtra("id_usuario", resultado.getId_cliente());
                    INTENT.putExtra("nome", resultado.getNome());
                    INTENT.putExtra("foto", resultado.getFoto());

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity) getActivity()).finish();
                            ((MainActivity)getActivity()).startActivity(INTENT);
                        }
                    }, 1000);
                }
            }catch(Exception e){
                Toast.makeText(((MainActivity)getActivity()),"Ocorreu um erro de conexão. Tente novamente mais tarde.",Toast.LENGTH_LONG).show();
            }

        }
    }
}
