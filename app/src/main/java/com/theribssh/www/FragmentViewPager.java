package com.theribssh.www;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16165846 on 16/10/2017.
 */

public class FragmentViewPager extends Fragment {

    private final static int TAG_PESQUISA = 1;
    private final static int TAG_PEDIDO = 2;

    private ViewPager view_pager;
    List<Fragment> listFragment = new ArrayList<>();
    List<String> listFragmentNome = new ArrayList<>();
    private int fragmentNumber;

    private void setupViewPager(ViewPager viewPager, SectionsStatePagerAdapter adapter){
        int contador = 0;
        while(contador < listFragment.size()){
            //Pegando o fragment
            adapter.addFragment(listFragment.get(contador),listFragmentNome.get(contador));

            contador++;
        }
        viewPager.setAdapter(adapter);
    }
    //Função para atribuição das listas
    public void addFragment(String nomeFragment, Fragment fragment){
        //Adicionando as informações as listas
        listFragment.add(fragment);
        listFragmentNome.add(nomeFragment);
    }
    //Função para adicionar o adapter
    public void setAdapter(ViewPager view){
        final SectionsStatePagerAdapter mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getChildFragmentManager());

        setupViewPager(view, mSectionsStatePagerAdapter);
    }
    //Função para mudar o fragment visto
    public void setViewPager (){
        view_pager.post(new Runnable() {
            @Override
            public void run() {

                if (fragmentNumber == TAG_PESQUISA){
                    FragmentPesquisa fragment = (FragmentPesquisa) listFragment.get(1);
                    fragment.setFocus();
                }else if (fragmentNumber == TAG_PEDIDO){
                    FragmentPedidoGarcomDetalhes fragment = (FragmentPedidoGarcomDetalhes) listFragment.get(1);
                }

                view_pager.setCurrentItem(fragmentNumber, true);
            }
        });
    }

    //Função para setar a variável 'fragmentNumber'
    public void setFragmentNumber(int fragmentNumber){
        this.fragmentNumber = fragmentNumber;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Seleciona o layout
        View view = inflater.inflate(R.layout.fragment_viewpager_layout, container, false);

        view_pager = (ViewPager)view.findViewById(R.id.view_pager);

        setAdapter(view_pager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setViewPager();
    }
}
