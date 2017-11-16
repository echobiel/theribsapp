package com.theribssh.www;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by biiac on 08/10/2017.
 */

public class FragmentCardapioPrincipais extends Fragment {

    Context context;

    private ListView list_view_cardapio_principais;
    private CardapioPrincipaisAdapter cardapioPrincipaisAdapter;
    List<CardapioPrincipaisListView> card_principais;
    Activity act;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cardapios_principais, container, false);

        act = ((MainActivity)getActivity());

        list_view_cardapio_principais = (ListView) rootView.findViewById(R.id.list_view_cardapio_principais);

        configurarListView();



        return rootView;
    }

    private void configurarListView() {

        card_principais = new ArrayList<>();

        new PegadorTask().execute();
    }

    private class PegadorTask extends AsyncTask<Void, Void, Void>
    {
        String json;
        @Override
        protected Void doInBackground(Void... voids) {
            String href = String.format("http://%s/selectCardapio", getResources().getString(R.string.ip_node));
            json = HttpConnection.get(href);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new Gson();

            card_principais = gson.fromJson(json, new TypeToken<List<CardapioPrincipaisListView>>(){
            }.getType());

            cardapioPrincipaisAdapter = new CardapioPrincipaisAdapter(card_principais, act);
            try {
                list_view_cardapio_principais.setAdapter(cardapioPrincipaisAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
