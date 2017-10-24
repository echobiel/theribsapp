package com.theribssh.www;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by biiac on 08/10/2017.
 */

public class FragmentHistoricoReservas extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.historico_reservas_listview, container, false);
        return rootView;
    }
}
