package com.theribssh.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16165846 on 16/10/2017.
 */

public class FragmentPesquisa extends Fragment {

    EditText edit_text_pesquisa;
    TextView text_view_erro;
    ImageView img_swipe;
    ImageButton img_clicked;
    ListView list_pesquisa;
    List<NossosRestaurantesListView> lstRestaurantes = new ArrayList<>();
    NossosRestaurantesAdapter adapter;
    String texto;
    Activity act;
    private int verificadorFoco;
    PegadorTask pet;
    FiltroTask ft;
    int id_restaurante;
    int permissao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Seleciona o layout
        View view = inflater.inflate(R.layout.fragment_pesquisa_layout, container, false);

        act = getActivity();

        edit_text_pesquisa = (EditText) view.findViewById(R.id.edit_text_pesquisa);
        text_view_erro = (TextView) view.findViewById(R.id.text_view_erro);
        list_pesquisa = (ListView) view.findViewById(R.id.list_pesquisa);
        img_swipe = (ImageView) view.findViewById(R.id.img_swipe);
        img_clicked = (ImageButton) view.findViewById(R.id.img_clicked);

        img_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_clicked.setVisibility(View.GONE);
                img_swipe.setVisibility(View.GONE);
            }
        });

        Intent intent = act.getIntent();

        permissao = intent.getIntExtra("permissao",0);

        if (permissao == 1) {
            list_pesquisa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    id_restaurante = lstRestaurantes.get(i).getId_restaurante();
                    openReserva();
                    return false;
                }
            });
        }else{
            list_pesquisa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getActivity(), "É necessário ter um cadastro para reservar. Cadastre-se e faça sua reserva.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

        edit_text_pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0){
                    texto = (editable + "").replace(" ", "+");
                    ft = new FiltroTask();
                    ft.execute();
                }else{
                    pet = new PegadorTask();
                    pet.execute();
                }
            }
        });

        img_clicked.getBackground().setAlpha(165);

        //Pegando o contexto da imagem
        Context context = img_swipe.getContext();
        //Pegando id da imagem via nome
        int id = context.getResources().getIdentifier("swipe_helper", "drawable", context.getPackageName());
        //Colocando Imagem de fundo
        Glide.with(view).load(id).thumbnail(Glide.with(view).load(R.drawable.loading)).into(img_swipe);

        if (verificadorFoco == 1){
            edit_text_pesquisa.requestFocus();
        }

        pet = new PegadorTask();
        ft = new FiltroTask();

        pet.execute();

        return view;
    }

    public void openReserva(){
        try {
            FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            DialogFragmentReserva dfr = new DialogFragmentReserva(1, 3, id_restaurante);
            dfr.show(ft, "dialog");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setFocus(){
        verificadorFoco = 1;
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                href = String.format("http://%s/selectRestaurante", getResources().getString(R.string.ip_node));
                json = HttpConnection.get(href);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try{
                super.onPostExecute(aVoid);
                Gson gson = new Gson();
                lstRestaurantes = gson.fromJson(json, new TypeToken<List<NossosRestaurantesListView>>(){
                }.getType());

                if (lstRestaurantes.size() > 0) {
                    adapter = new NossosRestaurantesAdapter(lstRestaurantes, act);
                    list_pesquisa.setAdapter(adapter);
                }else{
                    text_view_erro.setText((getResources().getString(R.string.msg_erro_pesquisa)));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class FiltroTask extends AsyncTask<Void, Void, Void>{

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                href = String.format("http://%s/filtroRestaurante?texto=%s", getResources().getString(R.string.ip_node), texto);
                json = HttpConnection.get(href);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                super.onPostExecute(aVoid);
                Gson gson = new Gson();
                lstRestaurantes = gson.fromJson(json, new TypeToken<List<NossosRestaurantesListView>>(){
                }.getType());

                if (lstRestaurantes.size() > 0) {
                    adapter = new NossosRestaurantesAdapter(lstRestaurantes, act);
                    list_pesquisa.setAdapter(adapter);
                    text_view_erro.setText("");
                }else{
                    adapter = new NossosRestaurantesAdapter(lstRestaurantes, act);
                    list_pesquisa.setAdapter(adapter);
                    text_view_erro.setText((getResources().getString(R.string.msg_erro_pesquisa)));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
