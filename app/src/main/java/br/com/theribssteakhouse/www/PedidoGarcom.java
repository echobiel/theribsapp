package br.com.theribssteakhouse.www;

import java.util.Date;

/**
 * Created by 16165846 on 17/10/2017.
 */

public class PedidoGarcom {
    private int idPedido;
    private String codigoSala;
    private String codigoMesa;
    private Date data;

    public PedidoGarcom(int idPedido, String codigoSala, String codigoMesa, Date data){
        setIdPedido(idPedido);
        setCodigoSala(codigoSala);
        setCodigoMesa(codigoMesa);
        setData(data);
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public void setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
    }
}
