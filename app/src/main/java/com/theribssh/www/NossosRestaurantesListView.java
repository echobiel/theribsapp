package com.theribssh.www;

/**
 * Created by biiac on 23/10/2017.
 */

public class NossosRestaurantesListView {

    private int foto_restaurante;
    private String unidade_restaurante;
    private String endereco_restaurante;
    private String desc_restaurante;
    private String telefone_restaurante;

    public NossosRestaurantesListView(int foto_restaurante, String unidade_restaurante, String endereco_restaurante, String desc_restaurante, String telefone_restaurante){

        this.foto_restaurante = foto_restaurante;
        this.unidade_restaurante = unidade_restaurante;
        this.endereco_restaurante = endereco_restaurante;
        this.desc_restaurante = desc_restaurante;
        this.telefone_restaurante = telefone_restaurante;
    }

    public int getFoto_restaurante() {
        return foto_restaurante;
    }

    public void setFoto_restaurante(int foto_restaurante) {
        this.foto_restaurante = foto_restaurante;
    }

    public String getUnidade_restaurante() {
        return unidade_restaurante;
    }

    public void setUnidade_restaurante(String unidade_restaurante) {
        this.unidade_restaurante = unidade_restaurante;
    }

    public String getTelefone_restaurante() {
        return telefone_restaurante;
    }

    public void setTelefone_restaurante(String telefone_restaurante) {
        this.telefone_restaurante = telefone_restaurante;
    }

    public String getEndereco_restaurante() {
        return endereco_restaurante;
    }

    public void setEndereco_restaurante(String endereco_restaurante) {
        this.endereco_restaurante = endereco_restaurante;
    }

    public String getDesc_restaurante() {
        return desc_restaurante;
    }

    public void setDesc_restaurante(String desc_restaurante) {
        this.desc_restaurante = desc_restaurante;
    }
}
