package br.com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDados (@JsonAlias("name") String autor,
                          @JsonAlias("birth_year") Integer anoDeNascimento,
                          @JsonAlias("death_year") Integer anoDeFalecimento){
}
