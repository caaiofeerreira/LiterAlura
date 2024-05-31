package br.com.alura.literalura.models;

import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String autor;

    private Year anoDeNascimento;
    private Year anoDeFalecimento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    List<Livro> livros = new ArrayList<>();

    public Autor(){}

    public Autor(AutorDados autorDados) {
        this.autor = autorDados.autor();
        this.anoDeNascimento = Year.of(autorDados.anoDeNascimento());
        this.anoDeFalecimento = Year.of(autorDados.anoDeFalecimento());
    }

    public Autor(String autor, Year anoDeNascimento, Year anoDeFalecimento) {
        this.autor = autor;
        this.anoDeNascimento = anoDeNascimento;
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Year getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Year anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Year getAnoDeFalecimento() {
        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Year anoDeFalecimento) {
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public String toString() {
        return "\n*****      LiterAlura - Autor      *****"+
                "\nAutor: "+autor+
                "\nAno de nascimento: "+anoDeNascimento+
                "\nAno de falecimento: "+anoDeFalecimento+
                "\nLivros: " +livros.stream().map(Livro::getTitulo).toList()+
                "\n****************************************";
    }
}
