package com.theribssh.www;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentPerfilCliente extends Fragment {

    CircleImageView img_user;
    TextView text_view_login;
    int id_usuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil_cliente, container, false);

        img_user = (CircleImageView)view.findViewById(R.id.img_user);
        text_view_login = (TextView) view.findViewById(R.id.text_view_login);

        Intent intent = ((MainActivity)getActivity()).getIntent();

        id_usuario = intent.getIntExtra("id_usuario",0);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new PegadorTask().execute();

        return view;
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String json;
        String href;

        @Override
        protected Void doInBackground(Void... params) {
            href = String.format("http://%s/selectCliente?id_usuario%d",getResources().getString(R.string.ip_node), id_usuario);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
/*
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
            }*/

        }
    }
}
