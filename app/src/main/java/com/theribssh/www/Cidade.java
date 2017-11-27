package com.theribssh.www;

/**
 * Created by 16165846 on 16/11/2017.
 */

public class Cidade {
    private int id_cidade;
    private String nome;

    public Cidade(int id_cidade, String nome){
        setId_cidade(id_cidade);
        setNome(nome);
    }

    public int getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(int id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
