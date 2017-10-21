package br.com.theribssteakhouse.www;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by 16165846 on 16/10/2017.
 */

public class FragmentPesquisa extends Fragment {

    EditText edit_text_pesquisa;
    ImageView img_swipe;
    ImageButton img_clicked;
    private int verificadorFoco;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Seleciona o layout
        View view = inflater.inflate(R.layout.fragment_pesquisa_layout, container, false);

        edit_text_pesquisa = (EditText) view.findViewById(R.id.edit_text_pesquisa);
        img_swipe = (ImageView) view.findViewById(R.id.img_swipe);
        img_clicked = (ImageButton) view.findViewById(R.id.img_clicked);

        img_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_clicked.setVisibility(View.GONE);
                img_swipe.setVisibility(View.GONE);
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

        return view;
    }

    public void setFocus(){
        verificadorFoco = 1;
    }
}
