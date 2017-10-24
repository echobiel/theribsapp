package com.theribssh.www;

/**
 * Created by biiac on 14/10/2017.
 */

public class HistoricoReservasListView {

    private String nome_restaurante;
    private int foto_restaurante;
    private String desc_restaurante;
    private String data_reserva;
    private String periodo_reserva;

    public String getDesc_restaurante() {
        return desc_restaurante;
    }

    public void setDesc_restaurante(String desc_restaurante) {
        this.desc_restaurante = desc_restaurante;
    }

    public String getNome_restaurante() {
        return nome_restaurante;
    }

    public void setNome_restaurante(String nome_restaurante) {
        this.nome_restaurante = nome_restaurante;
    }

    public int getFoto_restaurante() {
        return foto_restaurante;
    }

    public void setFoto_restaurante(int foto_restaurante) {
        this.foto_restaurante = foto_restaurante;
    }


    public String getData_reserva() {
        return data_reserva;
    }

    public void setData_reserva(String data_reserva) {
        this.data_reserva = data_reserva;
    }

    public String getPeriodo_reserva() {
        return periodo_reserva;
    }

    public void setPeriodo_reserva(String periodo_reserva) {
        this.periodo_reserva = periodo_reserva;
    }
}
