package com.theribssh.www;

/**
 * Created by echobiel on 01/12/2017.
 */

public class Pedidos {
    private int id_pedido;
    private String restaurante;
    private String qtd;
    private String data;

    public Pedidos(int id_pedido, String restaurante, String qtd, String data){
        setId_pedido(id_pedido);
        setData(data);
        setQtd(qtd);
        setRestaurante(restaurante);
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
