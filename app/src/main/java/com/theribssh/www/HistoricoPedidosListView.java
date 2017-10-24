package com.theribssh.www;

import java.util.Date;

/**
 * Created by 16165824 on 17/10/2017.
 */

public class HistoricoPedidosListView {

    int qtdProdutos;
    String restaurante;
    Date data;

    public HistoricoPedidosListView(int qtdProdutos, String restaurante, Date data){
        setQtdProdutos(qtdProdutos);
        setRestaurante(restaurante);
        setData(data);
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    public void setQtdProdutos(int qtdProdutos) {
        this.qtdProdutos = qtdProdutos;
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
