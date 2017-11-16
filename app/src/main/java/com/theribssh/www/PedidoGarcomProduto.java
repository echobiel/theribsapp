package com.theribssh.www;

/**
 * Created by 16165846 on 18/10/2017.
 */

public class PedidoGarcomProduto {

    private int id_produto;
    private int qtd;
    private String imagem;
    private String nome;
    private String nome_status;
    private String obs;
    private float preco;

    public PedidoGarcomProduto(int id_produto, int qtd,float preco, String nome_status,String nome, String imagem,String obs) {
        setId_produto(id_produto);
        setQtd(qtd);
        setPreco(preco);
        setNome(nome);
        setObs(obs);
        setImagem(imagem);
        setNome_status(nome_status);
    }

    public String getNome_status() {
        return nome_status;
    }

    public void setNome_status(String nome_status) {
        this.nome_status = nome_status;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getImagem() {
        return this.imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return this.preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getObs() {
        return this.obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
