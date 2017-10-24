package com.theribssh.www;

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

public class FragmentCardapioTodos extends Fragment {

    private ListView list_view_cardapio_todos;
    private CardapioTodosAdapter cardapioTodosAdapter;
    ArrayList<CardapioTodosListView> card_todos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cardapio_todos_listview, container, false);

        return rootView;


    }
}
