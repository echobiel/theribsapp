package com.theribssh.www;

/**
 * Created by echobiel on 01/12/2017.
 */

public class Funcionario {
    private int id_funcionario;
    private String cpf;
    private String nome_completo;
    private String num_registro;
    private String telefone;
    private String celular;
    private String foto;
    private String restaurante;
    private String email;

    public Funcionario (int id_funcionario, String cpf, String email, String nome_completo, String num_registro, String telefone, String celular, String foto, String restaurante){
        setRestaurante(restaurante);
        setCelular(celular);
        setCpf(cpf);
        setFoto(foto);
        setId_funcionario(id_funcionario);
        setNome_completo(nome_completo);
        setNum_registro(num_registro);
        setTelefone(telefone);
        setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome_completo() {
        return nome_completo;
    }

    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }

    public String getNum_registro() {
        return num_registro;
    }

    public void setNum_registro(String num_registro) {
        this.num_registro = num_registro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }
}
