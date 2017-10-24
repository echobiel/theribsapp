package com.theribssh.www;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by biiac on 08/10/2017.
 */

public class FragmentCardapioPrincipais extends Fragment {

    Context context;

    private ListView list_view_cardapio_principais;
    private CardapioPrincipaisAdapter cardapioPrincipaisAdapter;
    ArrayList<CardapioPrincipaisListView> card_principais;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.cardapio_principais_listview, container, false);
        return rootView;
    }

}
