package br.com.theribssteakhouse.www;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 16165846 on 09/10/2017.
 */

public class MenuAdapter extends BaseAdapter {

    private final List<Menu> menuList;
    private final Activity act;

    public MenuAdapter(List<Menu> menuList, Activity act){
        this.menuList = menuList;
        this.act = act;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.menu_list_item, parent, false);

        Menu menu = menuList.get(position);

        ImageButton imagem = (ImageButton) view.findViewById(R.id.btn_img_abaixo);
        TextView titulo = (TextView) view.findViewById(R.id.txt_acima);

        //Guardando o nome da imagem
        String nameImage = menu.getImagem();
        //Pegando o contexto da imagem
        Context context = imagem.getContext();
        //Pegando id da imagem via nome
        int id = context.getResources().getIdentifier(nameImage, "drawable", context.getPackageName());
        //Colocando Imagem de fundo
        Glide.with(view).load(id).thumbnail(Glide.with(view).load(R.drawable.loading)).into(imagem);


        titulo.setText(menu.getTitulo());

        return view;
    }
}
