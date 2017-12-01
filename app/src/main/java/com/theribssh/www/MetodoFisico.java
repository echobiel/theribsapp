package com.theribssh.www;

/**
 * Created by echobiel on 16/11/2017.
 */

public class MetodoFisico {

    private String mensagem;
    private int id_pedido;

    public MetodoFisico (String mensagem, int id_pedido){

        setMensagem(mensagem);
        setId_pedido(id_pedido);
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
