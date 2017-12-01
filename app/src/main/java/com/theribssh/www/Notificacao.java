package com.theribssh.www;

/**
 * Created by 16165846 on 01/12/2017.
 */

public class Notificacao {
    private int id_funcionario;
    private String mesa;

    public Notificacao(int id_funcionario, String mesa){
        setId_funcionario(id_funcionario);
        setMesa(mesa);
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }
}
