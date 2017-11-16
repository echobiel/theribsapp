package com.theribssh.www;

/**
 * Created by 16165846 on 16/11/2017.
 */

public class Banco {
    private int id_banco;
    private String nome;

    public Banco(int id_banco, String nome){
        setId_banco(id_banco);
        setNome(nome);
    }

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
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
