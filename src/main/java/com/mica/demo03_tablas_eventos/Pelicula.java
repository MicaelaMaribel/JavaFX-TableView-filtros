package com.mica.demo03_tablas_eventos;

import java.util.List;

public class Pelicula {

    private final int id;
    private final String titulo;
    private final String director;
    private final List<String> generos;
    private final int anio;

    public Pelicula(int id, String titulo, String director, List<String> generos, int anio) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.generos = generos;
        this.anio = anio;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDirector() { return director; }
    public List<String> getGeneros() { return generos; }
    public int getAnio() { return anio; }
}