package com.theribssh.www;

/**
 * Created by echobiel on 28/11/2017.
 */

public class TipoInfo {
    private int id_tipoInfo;
    private String nome;

    public TipoInfo(int id_tipoInfo, String nome){
        setNome(nome);
        setId_tipoInfo(id_tipoInfo);
    }

    public int getId_tipoInfo() {
        return id_tipoInfo;
    }

    public void setId_tipoInfo(int id_tipoInfo) {
        this.id_tipoInfo = id_tipoInfo;
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
