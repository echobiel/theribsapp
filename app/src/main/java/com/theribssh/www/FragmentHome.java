package com.theribssh.www;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echobiel on 08/10/2017.
 */

public class FragmentHome extends Fragment {
    private static final String TAG = "FragmentHome";

    private ImageButton btn_slider_left;
    private ImageButton btn_slider_right;
    private ImageButton img_btn_pesquisa;
    private MyListView list_menu;
    private TextView txt_desc_sobrenos;
    private TextView txt_pesquisar;
    private ViewPager slider_home;
    private ScrollView scroll_view;

    private int contadorSlider;
    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Seleciona o layout
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        //Guardando variáveis da interface
        btn_slider_left = (ImageButton) view.findViewById(R.id.btnSliderLeft);
        btn_slider_right = (ImageButton) view.findViewById(R.id.btnSliderRight);
        img_btn_pesquisa = (ImageButton) view.findViewById(R.id.img_btn_pesquisa);
        slider_home = (ViewPager) view.findViewById(R.id.sliderHome);
        list_menu = (MyListView) view.findViewById(R.id.list_menu);
        txt_desc_sobrenos = (TextView) view.findViewById(R.id.txt_desc_sobrenos);
        txt_pesquisar = (TextView) view.findViewById(R.id.txt_pesquisar);

        list_menu.setFocusable(false);

        img_btn_pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).nextPageHome();
            }
        });

        txt_pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).nextPageHome();
            }
        });

        //Configurando o menu(ou cardápio)
        setupMenu();

        new PegadorTask().execute();

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getChildFragmentManager());
        contadorSlider = 0;

        final SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getChildFragmentManager());

        setupViewPager(slider_home, adapter);

        setupClickSlider(adapter);

        return view;
    }
    //Configura os cliques nos botões do slider
    private void setupClickSlider(final SectionsStatePagerAdapter adapter) {
        //Botão esquerdo
        btn_slider_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verificador do slider
                if (contadorSlider - 1 < 0){
                    //Atribuindo o próximo slide
                    contadorSlider = adapter.getCount() - 1;
                    setViewPager(contadorSlider);
                }else {
                    //Atribuindo o próximo slide
                    contadorSlider = contadorSlider - 1;
                    setViewPager(contadorSlider);
                }
            }
        });
        //Botão direito
        btn_slider_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verificador do slider
                if (contadorSlider + 1 > adapter.getCount() - 1){
                    //Atribuindo o próximo slide
                    contadorSlider = 0;
                    setViewPager(contadorSlider);
                }else {
                    //Atribuindo o próximo slide
                    contadorSlider = contadorSlider + 1;
                    setViewPager(contadorSlider);
                }
            }
        });
    }

    private void setupMenu() {
        List<Menu> menus = new ArrayList<>();

        Menu menu;

        menu = new Menu("Vegetariano", "vegetariano_categoria");
        menus.add(menu);
        menu = new Menu("Steakhouse", "steakhouse_categoria");
        menus.add(menu);
        menu = new Menu("Saudável", "saudavel_categoria");
        menus.add(menu);

        MenuAdapter menuAdapter = new MenuAdapter(menus,((MainActivity)getActivity()));

        list_menu.setAdapter(menuAdapter);

        list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(((MainActivity)getActivity()),"Test " + i,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager, SectionsStatePagerAdapter adapter){
        FragmentSlider fragment;

        //Criando fragments
        fragment = new FragmentSlider();
        //Atribuindo a imagem do slider/fragment
        fragment.setDrawableImg("test");
        //Adicionando o fragment na lista
        adapter.addFragment(fragment, "FragmentSlider1");

        //Criando fragments
        fragment = new FragmentSlider();
        //Atribuindo a imagem do slider/fragment
        fragment.setDrawableImg("restaurante2");
        //Adicionando o fragment na lista
        adapter.addFragment(fragment, "FragmentSlider2");

        //Criando fragments
         fragment = new FragmentSlider();
        //Atribuindo a imagem do slider/fragment
        fragment.setDrawableImg("restaurante");
        //Adicionando o fragment na lista
        adapter.addFragment(fragment, "FragmentSlider3");

        viewPager.setAdapter(adapter);
    }

    public void setViewPager (int fragmentNumber){
        slider_home.setCurrentItem(fragmentNumber);
    }

    public class PegadorTask extends AsyncTask<Void, Void, Void> {

        String href;
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                href = String.format("http://%s/selectSobrenos", getResources().getString(R.string.ip_node));
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
                Mensagem m = gson.fromJson(json, new TypeToken<List<Mensagem>>(){
                }.getType());

                txt_desc_sobrenos.setText(m.getMensagem());
            }catch (Exception e){
                e.printStackTrace();
                txt_desc_sobrenos.setText(getResources().getString(R.string.sobrenos_default));
            }
        }
    }
}
