package com.theribssh.www;

import java.util.List;

/**
 * Created by 16165846 on 09/11/2017.
 */

public class SalaPedido {
    private int id_sala;
    private int id_funcionario;
    private int id_cliente;
    private int status;
    private int tamanho;
    private String qr_code;
    private String nome_cliente;
    private String status_nome;
    private String mensagem;
    private String mesa;
    private List<PedidoGarcomProduto> produtos;

    public SalaPedido(int id_sala, int id_funcionario, int tamanho, String qr_code, String mensagem){
        setId_sala(id_sala);
        setId_funcionario(id_funcionario);
        setTamanho(tamanho);
        setQr_code(qr_code);
        setMensagem(mensagem);
    }

    public SalaPedido(int id_sala, int id_funcionario, int tamanho, int status,String status_nome,
                      String nome_cliente, int id_cliente,String qr_code, String mensagem, String mesa, List<PedidoGarcomProduto> produtos){
        setId_sala(id_sala);
        setId_funcionario(id_funcionario);
        setId_cliente(id_cliente);
        setTamanho(tamanho);
        setQr_code(qr_code);
        setMensagem(mensagem);
        setMesa(mesa);
        setProdutos(produtos);
        setStatus(status);
        setNome_cliente(nome_cliente);
        setStatus_nome(status_nome);
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getStatus_nome() {
        return status_nome;
    }

    public void setStatus_nome(String status_nome) {
        this.status_nome = status_nome;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public List<PedidoGarcomProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<PedidoGarcomProduto> produtos) {
        this.produtos = produtos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
}
