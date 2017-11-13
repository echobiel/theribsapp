package com.theribssh.www;

import java.util.Date;

/**
 * Created by 16165846 on 17/10/2017.
 */

public class PedidoGarcom {
    private int id_sala;
    private String qr_code;
    private String codigoMesa;
    private String status_nome;
    private Date data;

    public PedidoGarcom(int id_sala, String qr_code, String codigoMesa, String status_nome,Date data){
        setId_sala(id_sala);
        setQr_code(qr_code);
        setCodigoMesa(codigoMesa);
        setData(data);
        setStatus_nome(status_nome);
    }

    public String getStatus_nome() {
        return status_nome;
    }

    public void setStatus_nome(String status_nome) {
        this.status_nome = status_nome;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
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
