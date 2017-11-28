package com.theribssh.www;

/**
 * Created by echobiel on 28/11/2017.
 */

public class Periodo {
    private int id_periodo;
    private String nome;

    public Periodo(int id_periodo, String nome){
        setNome(nome);
        setId_periodo(id_periodo);
    }

    public int getId_periodo() {
        return id_periodo;
    }

    public void setId_periodo(int id_periodo) {
        this.id_periodo = id_periodo;
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
