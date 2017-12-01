package com.theribssh.www;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class FragmentPerfilGarcom extends Fragment {

    ImageView img_garcom;
    TextView text_view_nome_garcom;
    TextView text_view_numero_registro_garcom;
    TextView text_view_cpf_garcom;
    TextView text_view_celular_garcom;
    TextView text_view_telefone_garcom;
    TextView text_view_email_garcom;
    TextView text_view_cargo_garcom;
    Activity act;
    View view;
    int id_funcionario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_perfil_garcom, container, false);

        act = ((MainActivity)getActivity());

        Intent intent = act.getIntent();

        id_funcionario = intent.getIntExtra("id_usuario",0);

        img_garcom = (ImageView)view.findViewById(R.id.img_garcom);
        text_view_nome_garcom = (TextView)view.findViewById(R.id.text_view_nome_garcom);
        text_view_numero_registro_garcom = (TextView)view.findViewById(R.id.text_view_numero_registro_garcom);
        text_view_cpf_garcom = (TextView)view.findViewById(R.id.text_view_cpf_garcom);
        text_view_celular_garcom = (TextView)view.findViewById(R.id.text_view_celular_garcom);
        text_view_telefone_garcom = (TextView)view.findViewById(R.id.text_view_telefone_garcom);
        text_view_email_garcom = (TextView)view.findViewById(R.id.text_view_email_garcom);
        text_view_cargo_garcom = (TextView)view.findViewById(R.id.text_view_cargo_garcom);

        new PegadorTask().execute();

        return view;
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            href = String.format("http://%s/perfilFuncionario?id_funcionario=%d",getResources().getString(R.string.ip_node),id_funcionario);
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                Gson gson = new Gson();
                List<Funcionario> f = gson.fromJson(json, new TypeToken<List<Funcionario>>() {
                }.getType());

                if (f.size() > 0) {
                    text_view_nome_garcom.setText(f.get(0).getNome_completo());
                    text_view_numero_registro_garcom.setText(f.get(0).getNum_registro());
                    text_view_cpf_garcom.setText(f.get(0).getCpf());
                    text_view_celular_garcom.setText(f.get(0).getCelular());
                    text_view_telefone_garcom.setText(f.get(0).getTelefone());
                    text_view_email_garcom.setText(f.get(0).getEmail());
                    text_view_cargo_garcom.setText(f.get(0).getRestaurante());

                    //Guardando o nome da imagem
                    String nameImage = f.get(0).getFoto();

                    try {
                        //Colocando Imagem de fundo
                        Glide.with(view).load(String.format("%s%s", act.getResources().getString(R.string.url_serverFotoFuncionario), nameImage)).thumbnail(Glide.with(view).load(R.drawable.loading)).into(img_garcom);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
