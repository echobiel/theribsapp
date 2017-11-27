package com.theribssh.www;

/**
 * Created by 16165846 on 16/11/2017.
 */

public class Estado {
    private int id_estado;
    private String nome;

    public Estado(int id_estado, String nome){
        setId_estado(id_estado);
        setNome(nome);
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
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
