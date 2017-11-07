package com.theribssh.www;

/**
 * Created by 16165846 on 07/11/2017.
 */

public class Usuario {
    private int id_cliente;
    private String login;
    private String senha;
    private int permissao;
    private String email;

    public void UsuarioLogin (String login, String senha){

        setLogin(login);
        setSenha(senha);

    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPermissao() {
        return permissao;
    }

    public void setPermissao(int permissao) {
        this.permissao = permissao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
