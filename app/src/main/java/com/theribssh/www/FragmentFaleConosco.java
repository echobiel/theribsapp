package com.theribssh.www;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class FragmentFaleConosco extends Fragment {

    Spinner spinner_periodo;
    Spinner spinner_unidade;
    Spinner spinner_tipo_info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fale_conosco, container, false);

        spinner_periodo = (Spinner)view.findViewById(R.id.spinner_periodo);
        spinner_unidade = (Spinner)view.findViewById(R.id.spinner_unidade);
        spinner_tipo_info = (Spinner)view.findViewById(R.id.spinner_tipo_info);

        ArrayAdapter periodo = ArrayAdapter.createFromResource(((MainActivity)getActivity()), R.array.periodos, android.R.layout.simple_spinner_item);
        spinner_periodo.setAdapter(periodo);

        ArrayAdapter unidade = ArrayAdapter.createFromResource(((MainActivity)getActivity()), R.array.unidades, android.R.layout.simple_spinner_item);
        spinner_unidade.setAdapter(unidade);

        ArrayAdapter tipo_info = ArrayAdapter.createFromResource(((MainActivity)getActivity()), R.array.tipos_info, android.R.layout.simple_spinner_item);
        spinner_tipo_info.setAdapter(tipo_info);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
