package com.theribssh.www;

/**
 * Created by biiac on 23/10/2017.
 */

public class NossosRestaurantesListView {

    private String imagem;
    private String nome;
    private String nome_restaurante;
    private String endereco;
    private String descricao;
    private String telefone_restaurante;

    public NossosRestaurantesListView(String imagem, String nome, String endereco, String descricao, String telefone_restaurante){
        setFoto_restaurante(imagem);
        setNome_restaurante(nome);
        setEndereco_restaurante(endereco);
        setDesc_restaurante(descricao);
        //setTelefone_restaurante(telefone);
    }

    public String getNome_restaurante() {
        return nome_restaurante;
    }

    public void setNome_restaurante(String nome_restaurante) {
        this.nome_restaurante = nome_restaurante;
    }

    public String getFoto_restaurante() {
        return imagem;
    }

    public void setFoto_restaurante(String caminho_imagem) {
        this.imagem = caminho_imagem;
    }

    public String getUnidade_restaurante() {
        return nome;
    }

    public void setUnidade_restaurante(String unidade_restaurante) {
        this.nome = unidade_restaurante;
    }

    public String getTelefone_restaurante() {
        return telefone_restaurante;
    }

    public void setTelefone_restaurante(String telefone_restaurante) {
        this.telefone_restaurante = telefone_restaurante;
    }

    public String getEndereco_restaurante() {
        return endereco;
    }

    public void setEndereco_restaurante(String endereco_restaurante) {
        this.endereco = endereco_restaurante;
    }

    public String getDesc_restaurante() {
        return descricao;
    }

    public void setDesc_restaurante(String desc_restaurante) {
        this.descricao = desc_restaurante;
    }
}
