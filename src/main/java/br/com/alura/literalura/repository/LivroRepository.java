package br.com.alura.literalura.repository;

import br.com.alura.literalura.models.Autor;
import br.com.alura.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface LivroRepository  extends JpaRepository<Livro, Long> {

    List<Livro> findByIdioma(String idioma);

    Integer countByIdioma(String idioma);

    @Query("SELECT l.totalDownload FROM Livro l")
    List<Double> buscarTotalDownload();

    @Query("SELECT a FROM Livro l JOIN l.autor a")
    List<Autor> buscarAutores();

    @Query ("SELECT a FROM Livro l JOIN l.autor a WHERE a.anoDeNascimento <= :ano and a.anoDeFalecimento >= :ano")
    List<Autor> buscarAutoresVivosNoAno(Year ano);

    @Query("SELECT a FROM Livro l JOIN l.autor a WHERE a.autor = :autor")
    Autor buscarAutorPeloNome(String autor);
}
