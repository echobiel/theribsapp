package com.theribssh.www;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class FragmentLogin extends Fragment {

    TextInputEditText edit_text_login;
    TextInputEditText edit_text_senha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);



        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_text_login = (TextInputEditText) view.findViewById(R.id.edit_text_login);
                edit_text_senha = (TextInputEditText) view.findViewById(R.id.edit_text_senha);
            }
        });

        return view;
    }

    private class autenticadorTask extends AsyncTask <Void, Void, Void>{

        String json;

        @Override
        protected Void doInBackground(Void... params) {
            String href = "http://10.0.2.2:8888/selectRestaurante";
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Gson gson = new Gson();
            /*lstRestaurantes = gson.fromJson(json, new TypeToken<List<NossosRestaurantesListView>>(){
            }.getType());*/


        }
    }
}
