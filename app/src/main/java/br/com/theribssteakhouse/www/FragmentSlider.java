package br.com.theribssteakhouse.www;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by echobiel on 08/10/2017.
 */

public class FragmentSlider extends Fragment {
    private static final String TAG = "FragmentSlider1";

    private ImageView imgSlider;

    private String drawableImg;

    public void setDrawableImg(String drawableImg){
        this.drawableImg = drawableImg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider_layout, container, false);

        imgSlider = (ImageView) view.findViewById(R.id.imgSlider);

        //Guardando o nome da imagem
        String nameImage = drawableImg;
        //Pegando o contexto da imagem
        Context context = imgSlider.getContext();
        //Pegando id da imagem via nome
        int id = context.getResources().getIdentifier(nameImage, "drawable", context.getPackageName());
        //Colocando Imagem de fundo
        Glide.with(view).load(id).thumbnail(Glide.with(view).load(R.drawable.loading)).into(imgSlider);

        return view;
    }
}