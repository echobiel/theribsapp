package com.theribssh.www;

import android.app.Activity;

import java.util.List;

/**
 * Created by 16165824 on 09/10/2017.
 */

public class CardapioPrincipaisListView {

    private int id_produto;
    private String nome_produto;
    private String foto_produto;
    private String preco_produto;
    private String desc_produto;

    public CardapioPrincipaisListView(int id_produto, String nome_produto, String foto_produto, String preco_produto, String desc_produto){
        setNome_produto(nome_produto);
        setDesc_produto(desc_produto);
        setFoto_produto(foto_produto);
        setPreco_produto(preco_produto);
        setId_produto(id_produto);
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getDesc_produto() {
        return desc_produto;
    }

    public void setDesc_produto(String desc_produto) {
        this.desc_produto = desc_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getFoto_produto() {
        return foto_produto;
    }

    public void setFoto_produto(String foto_produto) {
        this.foto_produto = foto_produto;
    }

    public String getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(String preco_produto) {
        this.preco_produto = preco_produto;
    }

}
