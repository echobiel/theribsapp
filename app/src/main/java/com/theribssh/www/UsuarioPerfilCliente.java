package com.theribssh.www;

/**
 * Created by echobiel on 26/11/2017.
 */

public class UsuarioPerfilCliente extends Usuario {
    private String endereco;
    private String sobrenome;
    private String telefone;
    private String celular;
    private String numero;
    private String logradouro;
    private String bairro;
    private String rua;
    private String foto;
    private String bandeira;
    private String nomereg;
    private String numerocartao;
    private String cvv;
    private String vencimento;
    private int id_cartaocredito;
    private int id_estado;
    private int id_cidade;

    public UsuarioPerfilCliente(String login, String senha, String nome, String sobrenome, String email, String endereco, String telefone,
                                String celular, String numero, String bairro, String rua, String bandeira, String nomereg, String numerocartao,
                                String cvv, String vencimento, String foto, String logradouro, int id_cartaocredito, int id_cidade, int id_estado){

        setLogin(login);
        setSenha(senha);
        setNome(nome);
        setSobrenome(sobrenome);
        setEmail(email);
        setEndereco(endereco);
        setTelefone(telefone);
        setCelular(celular);
        setNumero(numero);
        setBairro(bairro);
        setRua(rua);
        setBandeira(bandeira);
        setNomereg(nomereg);
        setNumerocartao(numerocartao);
        setCvv(cvv);
        setVencimento(vencimento);
        setId_cartaocredito(id_cartaocredito);
        setFoto(foto);
        setLogradouro(logradouro);
        setId_estado(id_estado);
        setId_cidade(id_cidade);
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public int getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(int id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getNomereg() {
        return nomereg;
    }

    public void setNomereg(String nomereg) {
        this.nomereg = nomereg;
    }

    public String getNumerocartao() {
        return numerocartao;
    }

    public void setNumerocartao(String numerocartao) {
        this.numerocartao = numerocartao;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public int getId_cartaocredito() {
        return id_cartaocredito;
    }

    public void setId_cartaocredito(int id_cartaocredito) {
        this.id_cartaocredito = id_cartaocredito;
    }
}
