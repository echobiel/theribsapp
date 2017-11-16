package com.theribssh.www;

/**
 * Created by echobiel on 16/11/2017.
 */

public class MetodoFisico {

    private String mensagem;

    public MetodoFisico (String mensagem){
        setMensagem(mensagem);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
