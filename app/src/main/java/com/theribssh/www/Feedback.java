package com.theribssh.www;

/**
 * Created by echobiel on 01/12/2017.
 */

public class Feedback {

    private int id_avaliacao;
    private String nome;
    private String imagem;

    public Feedback (int id_avaliacao, String nome, String imagem){
        setNome(nome);
        setId_avaliacao(id_avaliacao);
        setImagem(imagem);
    }

    public int getId_avaliacao() {
        return id_avaliacao;
    }

    public void setId_avaliacao(int id_avaliacao) {
        this.id_avaliacao = id_avaliacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return nome;
    }
}
