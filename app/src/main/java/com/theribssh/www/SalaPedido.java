package com.theribssh.www;

/**
 * Created by 16165846 on 09/11/2017.
 */

public class SalaPedido {
    private int id_sala;
    private int id_funcionario;
    private int id_cliente;
    private String qrCodeSala;
    private String mensagem;

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

    public String getQrCodeSala() {
        return qrCodeSala;
    }

    public void setQrCodeSala(String qrCodeSala) {
        this.qrCodeSala = qrCodeSala;
    }
}
