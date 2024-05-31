package br.com.alura.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    private String idioma;
    private Double totalDownload;

    public Livro() {}

    public Livro(LivroDados livroDados) {
        this.titulo = livroDados.titulo();
        Autor autor = new Autor(livroDados.authors().get(0));
        this.autor = autor;
        this.idioma = livroDados.idioma().get(0);
        this.totalDownload = livroDados.totalDownload();
    }

    public Livro(Long idAPI, String titulo, Autor autor, String idioma, Double totalDownload) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.totalDownload = totalDownload;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getTotalDownload() {
        return totalDownload;
    }

    public void setTotalDownload(Double totalDownload) {
        this.totalDownload = totalDownload;
    }

    public String toString() {
        return "\n*****      LiterAlura - Livro      *****"+
                "\nTítulo: "+titulo+
                "\nAutor: "+autor.getAutor()+
                "\nIdioma: "+idioma+
                "\nTotal download: " +totalDownload+
                "\n****************************************";
    }
}
