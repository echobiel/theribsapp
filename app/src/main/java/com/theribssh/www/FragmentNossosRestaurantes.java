package com.theribssh.www;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FragmentNossosRestaurantes extends Fragment{

    ListView list_view_nossos_restaurantes;
    List<NossosRestaurantesListView> lstRestaurantes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nossos_restaurantes, container, false);

        list_view_nossos_restaurantes = (ListView)view.findViewById(R.id.list_view_nossos_restaurantes);

        configurarListView();

        return view;
    }

    private void configurarListView() {

        lstRestaurantes = new ArrayList<NossosRestaurantesListView>();

        lstRestaurantes.add(new NossosRestaurantesListView(R.drawable.restaurante, "São Paulo - SP", "Rua José Bonifácio, 1010, Centro","(11)4521-9654", "Lorem ipsum dolor sit amet, consectetur"));
        lstRestaurantes.add(new NossosRestaurantesListView(R.drawable.restaurante, "São Paulo - SP", "Rua José Bonifácio, 1010, Centro","(11)4521-9654", "Lorem ipsum dolor sit amet, consectetur"));

        NossosRestaurantesAdapter adapter = new NossosRestaurantesAdapter(lstRestaurantes, ((MainActivity)getActivity()));

        list_view_nossos_restaurantes.setAdapter(adapter);
    }

}
