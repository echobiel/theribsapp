package com.theribssh.www;

/**
 * Created by echobiel on 14/11/2017.
 */

public class Mesa {

    private int id_mesa;
    private String nome;

    public Mesa (int id_mesa, String nome){
        setId_mesa(id_mesa);
        setNome(nome);
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
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
