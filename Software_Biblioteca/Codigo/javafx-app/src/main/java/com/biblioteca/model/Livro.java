package com.biblioteca.model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String caminhoArquivo;
    private String caminhoCapa;

    public Livro(int id, String titulo, String autor, String caminhoArquivo, String caminhoCapa) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.caminhoArquivo = caminhoArquivo;
        this.caminhoCapa = caminhoCapa;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCaminhoArquivo() { return caminhoArquivo; }
    public String getCaminhoCapa() { return caminhoCapa; }
}
