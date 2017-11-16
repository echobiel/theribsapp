package com.theribssh.www;

/**
 * Created by echobiel on 16/11/2017.
 */

public class VerificacaoId {

    private int id_cliente;
    private int id_funcionario;
    private int id_sala;

    public VerificacaoId(int id_cliente, int id_funcionario, int id_sala){
        setId_cliente(id_cliente);
        setId_funcionario(id_funcionario);
        setId_sala(id_sala);
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }
}
