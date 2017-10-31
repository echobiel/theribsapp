package com.theribssh.www;

import java.util.Date;

/**
 * Created by 16165824 on 17/10/2017.
 */

public class HistoricoPedidosListView {

    int qtd_produtos;
    String restaurante;
    String status;
    Date data;

    public HistoricoPedidosListView(int qtdProdutos, String restaurante, Date data){
        setQtd_produtos(qtdProdutos);
        setRestaurante(restaurante);
        setData(data);
    }

    public int getQtd_produtos() {
        return qtd_produtos;
    }

    public void setQtd_produtos(int qtd_produtos) {
        this.qtd_produtos = qtd_produtos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
