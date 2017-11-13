package com.theribssh.www;

/**
 * Created by 16165846 on 07/11/2017.
 */

public class AutenticarUsuario extends Usuario {

    private String mensagem;
    private String foto;

    public AutenticarUsuario(int id_usuario, String nome, int permissao, String foto, String mensagem){
        setId_usuario(id_usuario);
        setPermissao(permissao);
        setFoto(foto);
        setMensagem(mensagem);
        setNome(nome);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
