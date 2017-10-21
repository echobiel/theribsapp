package br.com.theribssteakhouse.www;

/**
 * Created by 16165846 on 09/10/2017.
 */

public class Menu {
    private String titulo;
    private String imagem;

    public Menu(String titulo, String imagem){
        setImagem(imagem);
        setTitulo(titulo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
