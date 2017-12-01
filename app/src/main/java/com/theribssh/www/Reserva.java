package com.theribssh.www;

/**
 * Created by echobiel on 01/12/2017.
 */

public class Reserva {

    private int id_restaurante;
    private String nome;
    private String foto;
    private String desc;
    private String data;
    private String periodo;
    private String status;

    public Reserva(int id_restaurante, String nome, String desc, String foto, String status, String data, String periodo){
        setId_restaurante(id_restaurante);
        setNome(nome);
        setData(data);
        setDesc(desc);
        setFoto(foto);
        setPeriodo(periodo);
        setStatus(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(int id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
