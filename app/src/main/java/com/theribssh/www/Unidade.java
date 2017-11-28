package com.theribssh.www;

/**
 * Created by echobiel on 28/11/2017.
 */

public class Unidade {
    private int id_restaurante;
    private String nome;

    public Unidade (int id_restaurante, String nome){
        setId_restaurante(id_restaurante);
        setNome(nome);
    }

    public int getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(int id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
