package br.com.alura.literalura.principal;

import br.com.alura.literalura.models.Autor;
import br.com.alura.literalura.models.Livro;
import br.com.alura.literalura.models.LivroDados;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.services.ConsumoAPI;
import br.com.alura.literalura.services.ConverteDados;

import java.time.Year;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner scanner = new Scanner(System.in);
    private LivroRepository repository;
    private String endereco = "https://gutendex.com/books/?search=";

    public Principal(LivroRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {

        int opcao = -1;

        while (opcao != 0) {
            String menu = """
                    \n*****             LiterAlura             *****
                    
                    1 - Buscar livros por título
                    2 - Listar livros cadastrados
                    3 - Listar Autores cadastrados
                    4 - Listar Autores vivos em determinado ano
                    5 - Listar Livros em determinado idioma
                    
                    0 - Sair
                    
                    **********************************************
                    """;
            try {
                System.out.println(menu);
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao){
                    case 1:
                        buscarLivro();
                        break;
                    case 2:
                        listarLivros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivosNoAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }catch (InputMismatchException e){
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.nextLine();
            }
        }
    }

    private void buscarLivro() {
        System.out.println("Digite o nome do livro:");
        var nomeLivro = scanner.nextLine();

        String enderecoBusca = endereco.concat(nomeLivro.replace(" ", "+").toLowerCase().trim());

        String json = consumoAPI.consumo(enderecoBusca);
        String jsonLivro = converteDados.extraiObjetoJson(json, "results");

        List<LivroDados> livros = converteDados.obterLista(jsonLivro, LivroDados.class);


        if (!livros.isEmpty()) {
            Livro livro = new Livro((livros.get(0)));
            Autor autor = repository.buscarAutorPeloNome(livro.getAutor().getAutor());

            if (autor != null) {
                livro.setAutor(null);
                repository.save(livro);
                livro.setAutor(autor);
            }
            livro = repository.save(livro);
            System.out.println(livro);

        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    private void listarLivros() {
        List<Livro> livros = repository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = repository.buscarAutores();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosNoAno() {

        try{
            System.out.println("Digite o ano:");
            int ano = scanner.nextInt();
            scanner.nextLine();

            List<Autor> autores = repository.buscarAutoresVivosNoAno(Year.of(ano));
            autores.forEach(System.out::println);

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            scanner.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Digite o idioma para busca
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        String idioma = scanner.nextLine();
        List<Livro> livros = repository.findByIdioma(idioma);

        if (!livros.isEmpty()) {
            livros.forEach(System.out::println);
        } else {
            System.out.println("Não existe livros nesse idioma cadastrado");
        }
    }

}
