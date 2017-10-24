package com.theribssh.www;

/**
 * Created by 16165824 on 10/10/2017.
 */

public class CardapioTodosListView {

    private String nome_produto;
    private String categoria_produto;
    private int foto_produto;

    public String getNome_produto() {
        return nome_produto;
    }

    public String getCategoria_produto() {
        return categoria_produto;
    }

    public void setCategoria_produto(String categoria_produto) {
        this.categoria_produto = categoria_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;

    }

    public int getFoto_produto() {
        return foto_produto;
    }

    public void setFoto_produto(int foto_produto) {
        this.foto_produto = foto_produto;
    }


}
