package com.theribssh.www;

/**
 * Created by echobiel on 26/11/2017.
 */

public class Mensagem {
    private String mensagem;

    public Mensagem (String mensagem){
        setMensagem(mensagem);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
